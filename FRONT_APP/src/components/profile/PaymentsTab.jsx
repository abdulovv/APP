import { useEffect, useState } from 'react'
import { getCards, makeDefaultCard, removeCard } from '../../api/paymentsApi.js'
import AddCardModal from './AddCardModal.jsx'

const pad = (n) => String(n).padStart(2, '0')

function PaymentsTab({ showNotification }) {
    const [cards, setCards] = useState([])
    const [loading, setLoading] = useState(true)
    const [showModal, setShowModal] = useState(false)

    const loadCards = async () => {
        try {
            setCards(await getCards())
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        loadCards()
    }, [])

    const handleMakeDefault = async (id) => {
        try {
            await makeDefaultCard(id)
            showNotification('success', 'Default card changed.')
            loadCards()
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const handleRemove = async (id) => {
        try {
            await removeCard(id)
            showNotification('success', 'Card removed.')
            loadCards()
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const handleAdded = () => {
        setShowModal(false)
        showNotification('success', 'Card added.')
        loadCards()
    }

    return (
        <section className="profileSection">
            <div className="paymentsHead">
                <div>
                    <h2 className="profileSectionTitle">Payment methods</h2>
                    <p className="profileHint">Cards are used to buy Premium.</p>
                </div>
                <button type="button" className="btn btnSmall" onClick={() => setShowModal(true)}>
                    Add card
                </button>
            </div>

            {loading && <p className="profileEmpty">Loading cards...</p>}

            {!loading && cards.length === 0 && (
                <p className="profileEmpty">You have no saved cards yet.</p>
            )}

            {cards.map((card) => (
                <div className="paymentCard" key={card.id}>
                    <div className="paymentCardBrand">{card.brand.slice(0, 4).toUpperCase()}</div>
                    <div className="paymentCardInfo">
                        <div className="paymentCardTitle">
                            {card.brand} •••• {card.last4}
                            {card.isDefault && <span className="badge success">Default</span>}
                        </div>
                        <div className="paymentCardMeta">
                            {card.holderName} · Expires {pad(card.expMonth)}/{String(card.expYear).slice(-2)}
                        </div>
                    </div>
                    {!card.isDefault && (
                        <button type="button" className="linkBtn" onClick={() => handleMakeDefault(card.id)}>
                            Make as default
                        </button>
                    )}
                    <button type="button" className="linkBtn danger" onClick={() => handleRemove(card.id)}>
                        Remove
                    </button>
                </div>
            ))}

            {showModal && (
                <AddCardModal onClose={() => setShowModal(false)} onAdded={handleAdded} />
            )}
        </section>
    )
}

export default PaymentsTab
