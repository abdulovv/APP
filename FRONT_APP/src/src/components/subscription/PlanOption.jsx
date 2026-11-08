// Одна карточка плана в сетке «Plans».
//
// Что здесь будет:
//   - название («6 months»), цена в месяц (planPrice + <small>/ month</small>);
//   - строка с итогом и скидкой (planNote, planSave): «Save 20% · $23.94 total»; у плана на 1 месяц пусто;
//   - список возможностей: PREMIUM_FEATURES.map(...) в <ul className="planFeatures">;
//   - кнопка «Buy»: у плана с plan.best — основная (btn btnSmall), у остальных — btn btnSecondary btnSmall.
//
// Классы из SubscriptionPage.css: planOption (+ best), planOptionName, planPrice, planNote, planSave, planFeatures
//
// Получает: plan (из constants/plans.js), onBuy().

import { PREMIUM_FEATURES } from '../../constants/plans.js'

function PlanOption({ plan, onBuy }) {
    return (
        <div className={plan.best ? 'planOption best' : 'planOption'}>
            <div className="planOptionName">{plan.months} months</div>
            {/* TODO: цена, итог со скидкой, список возможностей, кнопка */}
        </div>
    )
}

export default PlanOption
