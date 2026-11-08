import { useEffect, useState } from 'react'
import { getMyPremiumHistory } from '../../api/premiumApi.js'

function PurchasesTab({ showNotification }) {
    const [items, setItems] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
    }, [])

    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Purchases</h2>
            <p className="profileHint">History of your Premium payments and grants.</p>
        </section>
    )
}

export default PurchasesTab
