// Вкладка «Your plan»: текущий статус подписки.
//
// Что здесь будет:
//   - заголовок «Premium» + бейдж Active (или «Free plan» + бейдж Free, если user.premium === false);
//   - даты: Started и Expires (formatDate из utils/dates.js);
//   - полоска «осталось дней» и текст «19 days left of 30» (daysLeft / percentLeft из utils/dates.js);
//   - кнопка «Extend Premium» (для Free — «Get Premium»), по клику можно переключать вкладку на «Plans».
//
// Классы из SubscriptionPage.css: planSection, planHead, planHeadTitle, planSectionTitle,
//   planBadge (+ success / muted), planDates, planDateLabel, planDateValue,
//   planBar, planBarFill (ширина: style={{ width: `${percent}%` }}), planLeft; кнопка: btn btnSmall
//
// Получает: user (premium, startedAt, expiresAt), onOpenPlans() — перейти на вкладку «Plans».

function CurrentPlanTab({ user, onOpenPlans }) {
    return (
        <section className="planSection">
            <h2 className="planSectionTitle">{user.premium ? 'Premium' : 'Free plan'}</h2>
            {/* TODO: бейдж, даты, полоска, кнопка */}
        </section>
    )
}

export default CurrentPlanTab
