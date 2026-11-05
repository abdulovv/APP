import { useEffect, useState } from 'react'
import { getCurrentUser } from '../api/profileApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/Notification.jsx'
import PersonalTab from '../components/profile/PersonalTab.jsx'
import PasswordTab from '../components/profile/PasswordTab.jsx'
import PaymentsTab from '../components/profile/PaymentsTab.jsx'
import PremiumTab from '../components/profile/PremiumTab.jsx'
import '../css/pages/ProfilePage.css'

const TABS = {
    personal: 'Personal data',
    password: 'Password',
    payments: 'Payment methods',
    premium: 'Premium',
}

function ProfilePage() {
    const [user, setUser] = useState(null)
    const [tab, setTab] = useState('personal')
    const [loadError, setLoadError] = useState('')
    const [notification, showNotification] = useNotification()

    useEffect(() => {
        document.title = 'Profile'
        getCurrentUser()
            .then(setUser)
            .catch((error) => setLoadError(error.message))
    }, [])

    if (loadError) {
        return (
            <div className="card profileCard">
                <h1 className="cardTitle">Profile</h1>
                <p className="profileMessage">{loadError}</p>
            </div>
        )
    }

    if (!user) {
        return <p className="profileMessage">Loading profile...</p>
    }

    return (
        <div className="profilePage">
            <Notification notification={notification} />
            <h1 className="profileTitle">Profile</h1>

            <div className="profileTabs">
                {Object.entries(TABS).map(([key, label]) => (
                    <button
                        key={key}
                        type="button"
                        className={tab === key ? 'profileTab active' : 'profileTab'}
                        onClick={() => setTab(key)}
                    >
                        {label}
                    </button>
                ))}
            </div>

            {tab === 'personal' && <PersonalTab user={user} setUser={setUser} showNotification={showNotification} />}
            {tab === 'password' && <PasswordTab showNotification={showNotification} />}
            {tab === 'payments' && <PaymentsTab showNotification={showNotification} />}
            {tab === 'premium' && <PremiumTab user={user} showNotification={showNotification} />}
        </div>
    )
}

export default ProfilePage
