// Вкладка «Premium»: статус подписки и история.
//
// Что здесь будет:
//   - карточка статуса: Active / не активен, дата окончания, сколько дней осталось, кнопка «Extend Premium»;
//   - таблица истории: дата, источник (Purchase / Granted by admin), период, сумма, статус.
//
// Нужен бэкенд (его ещё нет): история премиума текущего пользователя, покупка премиума.
// Статус можно показать уже сейчас: user.premium (true/false) приходит с /users/me.
//
// Классы из ProfilePage.css: profileSection, profileSectionPadded, premiumHeader, premiumHeaderTitle,
//   profileSectionTitle, badge (+ success), premiumProgress / premiumProgressFill,
//   historyScroll, historyTable
//
// Получает: user (там user.premium), showNotification(type, text).

function PurchasesTab({ user, showNotification }) {
    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Premium</h2>
            <p className="profileHint">{user.premium ? 'Premium is active.' : 'Premium is not active.'}</p>
            {/* TODO: статус и история */}
        </section>
    )
}

export default PurchasesTab
