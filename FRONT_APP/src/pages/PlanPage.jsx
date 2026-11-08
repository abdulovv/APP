import { useEffect, useState } from 'react'
import { getCurrentUser } from '../api/profileApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/Notification.jsx'
import CurrentPlanTab from '../components/subscription/CurrentPlanTab.jsx'

const TABS = {
    current: 'Your plan',
    plans: 'Plans',
}

const MONTHS = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December',
]

const formatDate = (isoDate) => {
    if (!isoDate) return '—';
    
    const year = Number(isoDate.slice(0, 4));   
    const month = Number(isoDate.slice(5, 7));  
    const day = Number(isoDate.slice(8, 10));   
    
    return `${day} ${MONTHS[Number(month) - 1]} ${year}`;
};

function PlanPage() {
    console.log('SUBSCRIPTION PAGE v2')
    const [user, setUser] = useState(null)
    const [tab, setTab] = useState('current')
    const [loadError, setLoadError] = useState('')
    const [notification, showNotification] = useNotification()

    useEffect(() => {
        document.title = 'plan'
        getCurrentUser()
            .then(setUser)
            .catch((error) => setLoadError(error.message))
    }, [])

    if (loadError) {
        return (
            <div className="card planCard">
                <h1 className="cardTitle">plan</h1>
                <p className="planMessage">{loadError}</p>
            </div>
        )
    }

    if (!user) {
        return <p className="planMessage">Loading plan...</p>
    }

    return (
        <div className="planPage">
            <Notification notification={notification} />
            
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
                <section>
                    <CurrentPlanTab user={user} showNotification={showNotification} />
                </section>
            )}

            {tab === 'plans' && (
                <p></p>
            )}

            

            
        </div>
    )
}

export default PlanPage
