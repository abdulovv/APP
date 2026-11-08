// Вкладка «Plans»: планы для покупки.
//
// Что здесь будет:
//   - заголовок «Choose a plan» и подсказка «Paid with a saved card from Profile → Payment methods»;
//   - сетка карточек: PLANS.map(...) -> <PlanOption key={plan.id} plan={plan} onBuy={...} />;
//   - выбор карты (список из getCards) или сообщение «Add a card first», если карт нет;
//   - покупка: purchasePremium(cardId, months) из api/premiumApi.js (пока не написана на бэкенде),
//     после успеха — showNotification('success', ...) и обновить user.
//
// Классы из SubscriptionPage.css: planSectionTitle, planSubtitle, plansGrid
//
// Получает: user, showNotification(type, text).

import { PLANS } from '../../constants/plans.js'
import PlanOption from './PlanOption.jsx'

function PlansTab({ user, showNotification }) {
    return (
        <section>
            <h2 className="planSectionTitle">Choose a plan</h2>
            <div className="plansGrid">
                {PLANS.map((plan) => (
                    <PlanOption key={plan.id} plan={plan} onBuy={() => {}} />
                ))}
            </div>
        </section>
    )
}

export default PlansTab
