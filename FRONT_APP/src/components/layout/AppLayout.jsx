import { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from './Navbar.jsx'
import { getCurrentUser } from '../../api/profileApi.js'
import '../../css/components/AppLayout.css'

function AppLayout() {
    // Текущий пользователь нужен навбару (имя, тариф, роль) и странице админки
    const [currentUser, setCurrentUser] = useState(null)

    useEffect(() => {
        getCurrentUser()
            .then(setCurrentUser)
            .catch(() => {
                // при 401 http.js сам перекинет на вход, остальное просто не покажем
            })
    }, [])

    return (
        <div className="appLayout">
            <Navbar user={currentUser} />
            <main className="appContent">
                <Outlet context={{ currentUser, setCurrentUser }} />
            </main>
        </div>
    )
}

export default AppLayout
