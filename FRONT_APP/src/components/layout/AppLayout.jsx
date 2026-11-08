import { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from './Navbar.jsx'
import { getCurrentUser } from '../../api/profileApi.js'
import { getPageSettings } from '../../api/pageSettingsApi.js'
import '../../css/components/AppLayout.css'

function AppLayout() {
    const [currentUser, setCurrentUser] = useState(null)
    const [pageSettings, setPageSettings] = useState([])

    useEffect(() => {
        getCurrentUser()
            .then(setCurrentUser)
            .catch(() => {
            })

        getPageSettings()
            .then(setPageSettings)
            .catch(() => {
            })
    }, [])

    return (
        <div className="appLayout">
            <Navbar user={currentUser} />
            <main className="appContent">
                <Outlet context={{ currentUser, setCurrentUser, pageSettings, setPageSettings }} />
            </main>
        </div>
    )
}

export default AppLayout
