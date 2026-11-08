package com.app.account_service.services;

import com.app.account_service.dtos.requests.AddCardRequest;
import com.app.account_service.dtos.responses.PaymentCardResponse;
import com.app.account_service.entities.PaymentCard;
import com.app.account_service.entities.User;
import com.app.account_service.exceptions.CardAlreadyExistsException;
import com.app.account_service.exceptions.CardNotFoundException;
import com.app.account_service.exceptions.InvalidCardException;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.repositories.PaymentCardRepository;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardService {
    private final PaymentCardRepository paymentCardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PaymentCardResponse> getCards(Long userId) {
        return paymentCardRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PaymentCardResponse addCard(Long userId, AddCardRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        String number = request.cardNumber().replace(" ", "");
        if (!number.matches("^[0-9]{16}$")) {
            throw new InvalidCardException("Card number must contain 16 digits");
        }
        if (YearMonth.of(request.expYear(), request.expMonth()).isBefore(YearMonth.now())) {
            throw new InvalidCardException("Card has expired");
        }

        String brand = detectBrand(number);
        String last4 = number.substring(number.length() - 4);

        if (paymentCardRepository.existsByUserIdAndBrandAndLast4AndExpMonthAndExpYear(
                userId, brand, last4, request.expMonth(), request.expYear())) {
            throw new CardAlreadyExistsException();
        }

        boolean makeDefault = request.makeDefault() || paymentCardRepository.countByUserId(userId) == 0;
        if (makeDefault) {
            clearDefault(userId);
        }

        PaymentCard card = paymentCardRepository.save(PaymentCard.builder()
                .user(user)
                .holderName(request.holderName().trim())
                .brand(brand)
                .last4(last4)
                .expMonth(request.expMonth())
                .expYear(request.expYear())
                .isDefault(makeDefault)
                .build());
        return toResponse(card);
    }

    @Transactional
    public void makeDefault(Long userId, Long cardId) {
        PaymentCard card = paymentCardRepository.findByIdAndUserId(cardId, userId)
                .orElseThrow(CardNotFoundException::new);
        if (Boolean.TRUE.equals(card.getIsDefault())) {
            return;
        }
        clearDefault(userId);
        card.setIsDefault(true);
        paymentCardRepository.save(card);
    }

    @Transactional
    public void removeCard(Long userId, Long cardId) {
        PaymentCard card = paymentCardRepository.findByIdAndUserId(cardId, userId)
                .orElseThrow(CardNotFoundException::new);
        boolean wasDefault = Boolean.TRUE.equals(card.getIsDefault());
        paymentCardRepository.delete(card);
        paymentCardRepository.flush();

        if (wasDefault) {
            paymentCardRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                    .findFirst()
                    .ifPresent(next -> {
                        next.setIsDefault(true);
                        paymentCardRepository.save(next);
                    });
        }
    }

    private void clearDefault(Long userId) {
        paymentCardRepository.findByUserIdAndIsDefaultTrue(userId).ifPresent(old -> {
            old.setIsDefault(false);
            paymentCardRepository.saveAndFlush(old);
        });
    }

    private PaymentCardResponse toResponse(PaymentCard card) {
        return new PaymentCardResponse(
                card.getId(),
                card.getBrand(),
                card.getLast4(),
                card.getHolderName(),
                card.getExpMonth(),
                card.getExpYear(),
                card.getIsDefault()
        );
    }

    private static String detectBrand(String number) {
        if (number.startsWith("4")) return "Visa";
        if (number.matches("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720).*")) return "Mastercard";
        if (number.startsWith("34") || number.startsWith("37")) return "Amex";
        if (number.matches("^220[0-4].*")) return "Mir";
        return "Card";
    }
}
