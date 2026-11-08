// Планы подписки, которые показываются на вкладке «Plans».
// Цены — примеры, подставь свои. Позже можно будет отдавать их с бэкенда.
//
// Поля: id, months (на сколько месяцев), pricePerMonth (USD), best (выделить карточку).
// Итого и скидку считай в коде: итого = months * pricePerMonth; скидка = от цены 1 месяца.

export const PLANS = [
    { id: 'm1', months: 1, pricePerMonth: 4.99, best: false },
    { id: 'm6', months: 6, pricePerMonth: 3.99, best: true },
    { id: 'm12', months: 12, pricePerMonth: 2.99, best: false },
]

export const PREMIUM_FEATURES = ['Custom products', 'Full statistics', 'Weight tracking']
