package com.app.account_service.mappers;

import com.app.account_service.dtos.responses.PremiumTransactionResponse;
import com.app.account_service.entities.PremiumTransaction;
import com.app.account_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PremiumTransactionMapper {

    @Mapping(target = "performedById", source = "performedBy.id")
    @Mapping(target = "performedByName", expression = "java(fullNameOrNull(transaction.getPerformedBy()))")
    PremiumTransactionResponse toResponse(PremiumTransaction transaction);

    List<PremiumTransactionResponse> toResponseList(List<PremiumTransaction> transactions);

    default String fullNameOrNull(User user) {
        return user == null ? null : user.getFirstname() + " " + user.getLastname();
    }
}
