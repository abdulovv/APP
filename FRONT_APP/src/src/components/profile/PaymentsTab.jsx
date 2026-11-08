// Вкладка «Payment methods»: банковские карты пользователя.
//
// Что здесь будет:
//   - список карт (бренд, последние 4 цифры, имя держателя, срок, метка Default);
//   - действия: «Make default», «Remove»;
//   - кнопка «Add card», открывает окно с формой (номер, имя, срок, CVC, галочка Default).
//
// Нужен бэкенд (его ещё нет): список карт, добавление, удаление, смена карты по умолчанию.
// CVC в БД не храним: оплата у нас имитация.
//
// Классы из ProfilePage.css: profileToolbar, profileSection, paymentCard, paymentCardBrand,
//   paymentCardInfo, paymentCardTitle, paymentCardMeta, badge, linkBtn (+ danger), profileEmpty;
//   окно: profileModalOverlay, profileModal, profileModalActions, checkboxLine
//
// Получает: showNotification(type, text).

function PaymentsTab({ showNotification }) {
    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Payment methods</h2>
            <p className="profileHint">Cards are used to buy Premium.</p>
            {/* TODO: список карт и кнопка Add card */}
        </section>
    )
}

export default PaymentsTab
