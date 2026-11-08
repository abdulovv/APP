import { PLANS, PREMIUM_FEATURES } from '../../constants/plans.js'

const money = (value) => `$${value.toFixed(2)}`

const basePrice = PLANS[0].pricePerMonth

function PlanOption({ plan, selected, disabled, onSelect, onBuy }) {
    const total = plan.months * plan.pricePerMonth
    const discount = Math.round((1 - plan.pricePerMonth / basePrice) * 100)

    const handleBuy = (e) => {
        e.stopPropagation()
        onBuy(plan)
    }

    return (
        <div
            className={selected ? 'planOption selected' : 'planOption'}
            onClick={() => onSelect(plan.id)}
        >
            <div className="planOptionName">
                {plan.months === 1 ? '1 month' : `${plan.months} months`}
                {plan.best && <span className="planTag">Best value</span>}
            </div>
            <div className="planPrice">
                {money(plan.pricePerMonth)} <small>/ month</small>
            </div>
            <div className="planNote">
                {discount > 0 && (
                    <>
                        <span className="planSave">Save {discount}%</span> · {money(total)} total
                    </>
                )}
            </div>
            <ul className="planFeatures">
                {PREMIUM_FEATURES.map((feature) => (
                    <li key={feature}>{feature}</li>
                ))}
            </ul>
            <button
                type="button"
                className={selected ? 'btn btnSmall' : 'btn btnSecondary btnSmall'}
                disabled={disabled}
                onClick={handleBuy}
            >
                Buy
            </button>
        </div>
    )
}

export default PlanOption
