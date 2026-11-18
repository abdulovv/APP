import { useEffect, useState } from 'react'
import { getMyPremiumHistory } from '../../api/premiumApi.js'

const SOURCE_LABELS = {
    PURCHASE: 'Premium purchase',
    ADMIN: 'Granted by admin',
}

const STATUS_BADGES = {
    PAID: 'success',
    GRANTED: 'success',
    REFUNDED: 'muted',
    FAILED: 'danger',
}

function PurchasesTab({ showNotification }) {
    const [loading, setLoading] = useState(true)
    const [historyItems, setHistoryItems] = useState([])

    const loadHistory = async () => {
        try {
            setHistoryItems(await getMyPremiumHistory())
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setLoading(false)
        }    
    }

    useEffect(() => {
        loadHistory()
    }, [])

    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Purchases</h2>
            <p className="profileHint">History of your Premium payments and grants.</p>
            {loading && <p className="profileEmpty">Loading...</p>}

            {!loading && historyItems.length === 0 && (
                <p className="profileEmpty">No purchases yet.</p>
            )}

            {historyItems.length > 0 && (
                <div className="purchaseList">
                    {historyItems.map((item) => (
                        <div className="purchaseRow" key={item.id}>
                            <div className="purchaseInfo">
                                <div className="purchaseTitle">{SOURCE_LABELS[item.source] ?? item.source}</div>
                                <div className="purchaseMeta">
                                    {new Date(item.createdAt).toLocaleDateString()}
                                </div>
                            </div>
                            <span className={`badge ${STATUS_BADGES[item.status] ?? 'muted'}`}>
                                {item.status}
                            </span>
                            <div className="purchaseAmount">
                                {item.amountLocal != null
                                    ? `${item.currencySymbol}${item.amountLocal}`
                                    : '—'}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </section>
    )
}

export default PurchasesTab
