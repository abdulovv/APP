import { useEffect, useState } from 'react'
import { getCurrentUser } from '../api/profileApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/common/Notification.jsx'
import CurrentPlanTab from '../components/subscription/CurrentPlanTab.jsx'
import PlansTab from '../components/subscription/PlansTab.jsx'
import '../css/pages/SubscriptionPage.css'

const TABS = {
    current: 'Your plan',
    plans: 'Plans',
}

function SubscriptionPage() {
    const [user, setUser] = useState(null)
    const [tab, setTab] = useState('current')
    const [loadError, setLoadError] = useState('')
    const [notification, showNotification] = useNotification()

    useEffect(() => {
        document.title = 'Subscription'
        getCurrentUser()
            .then(setUser)
            .catch((error) => setLoadError(error.message))
    }, [])

    if (loadError) {
        return (
            <div className="card planCard">
                <h1 className="cardTitle">Subscription</h1>
                <p className="planMessage">{loadError}</p>
            </div>
        )
    }

    if (!user) {
        return <p className="planMessage">Loading subscription...</p>
    }

    return (
        <div className="planPage">
            <Notification notification={notification} />
            <h1 className="planTitle">Subscription</h1>

            <div className="planTabs">
                {Object.entries(TABS).map(([key, label]) => (
                    <button
                        key={key}
                        type="button"
                        className={tab === key ? 'planTab active' : 'planTab'}
                        onClick={() => setTab(key)}
                    >
                        {label}
                    </button>
                ))}
            </div>

            {tab === 'current' && (
                <CurrentPlanTab user={user} onOpenPlans={() => setTab('plans')} />
            )}

            {tab === 'plans' && (
                <PlansTab user={user} showNotification={showNotification} />
            )}
        </div>
    )
}

export default SubscriptionPage
