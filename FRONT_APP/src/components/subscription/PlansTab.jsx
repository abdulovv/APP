import { useEffect, useState } from 'react'
import { PLANS } from '../../constants/plans.js'
import { getCards } from '../../api/paymentsApi.js'
import { purchasePremium } from '../../api/premiumApi.js'
import { formatDate } from '../../utils/dates.js'
import PlanOption from './PlanOption.jsx'
import AddCardModal from '../profile/AddCardModal.jsx'

function PlansTab({ user, showNotification, onPurchased }) {
    const [cards, setCards] = useState([])
    const [cardId, setCardId] = useState('')
    const [selectedId, setSelectedId] = useState(null)
    const [loading, setLoading] = useState(true)
    const [buying, setBuying] = useState(false)
    const [showCardModal, setShowCardModal] = useState(false)
    const [pendingPlan, setPendingPlan] = useState(null)

    useEffect(() => {
        getCards()
            .then((data) => {
                setCards(data)
                const main = data.find((card) => card.isDefault) ?? data[0]
                setCardId(main ? String(main.id) : '')
            })
            .catch((error) => showNotification('error', error.message))
            .finally(() => setLoading(false))
    }, [])

    const buy = async (plan, payCardId) => {
        setSelectedId(plan.id)
        setBuying(true)
        try {
            await purchasePremium(Number(payCardId), plan.months)
            showNotification('success', 'Premium activated. Thank you!')
            onPurchased()
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setBuying(false)
        }
    }

    const handleBuy = (plan) => {
        if (cards.length === 0) {
            setSelectedId(plan.id)
            setPendingPlan(plan)
            setShowCardModal(true)
            return
        }
        buy(plan, cardId)
    }

    const handleCardAdded = (card) => {
        setShowCardModal(false)
        setCards((prev) => [card, ...prev])
        setCardId(String(card.id))
        showNotification('success', 'Card added.')
        if (pendingPlan) {
            buy(pendingPlan, card.id)
            setPendingPlan(null)
        }
    }

    const handleCloseModal = () => {
        setShowCardModal(false)
        setPendingPlan(null)
    }

    const noCards = !loading && cards.length === 0
    const hasActivePremium = user.premium
    const canBuy = !hasActivePremium && !loading && !buying

    return (
        <section className="planPlans">
            <div>
                <h2 className="planSectionTitle">Choose a plan</h2>
                <p className="planSubtitle">Paid with a saved card.</p>
            </div>

            {hasActivePremium && (
                <p className="planNotice">
                    You already have Premium until {formatDate(user.expiresAt)}.
                    You can buy a new plan after it ends.
                </p>
            )}

            {!hasActivePremium && noCards && (
                <p className="planNotice">
                    You have no saved cards. Press Buy on any plan to add a card and pay.
                </p>
            )}

            {!hasActivePremium && cards.length > 0 && (
                <label className="field planPayWith">
                    <span className="fieldLabel">Pay with</span>
                    <select
                        className="fieldInput"
                        value={cardId}
                        onChange={(e) => setCardId(e.target.value)}
                    >
                        {cards.map((card) => (
                            <option key={card.id} value={card.id}>
                                {card.brand} •••• {card.last4}
                            </option>
                        ))}
                    </select>
                    <button type="button" className="linkBtn planAddCard" onClick={() => setShowCardModal(true)}>
                        + Add new card
                    </button>
                </label>
            )}

            <div className="plansGrid">
                {PLANS.map((plan) => (
                    <PlanOption
                        key={plan.id}
                        plan={plan}
                        selected={selectedId === plan.id}
                        disabled={!canBuy}
                        onSelect={setSelectedId}
                        onBuy={handleBuy}
                    />
                ))}
            </div>

            {showCardModal && (
                <AddCardModal onClose={handleCloseModal} onAdded={handleCardAdded} />
            )}
        </section>
    )
}

export default PlansTab
