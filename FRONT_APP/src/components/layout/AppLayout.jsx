import { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from './Navbar.jsx'
import { getCurrentUser } from '../../api/profileApi.js'
import { getPageSettings } from '../../api/pageSettingsApi.js'
import '../../css/components/AppLayout.css'

function AppLayout() {
    // Текущий пользователь нужен навбару (имя, тариф, роль) и странице админки
    const [currentUser, setCurrentUser] = useState(null)
    // Статусы страниц (включена/выключена + причина) — нужны PageAvailabilityGuard и админке
    const [pageSettings, setPageSettings] = useState([])

    useEffect(() => {
        getCurrentUser()
            .then(setCurrentUser)
            .catch(() => {
                // при 401 http.js сам перекинет на вход, остальное просто не покажем
            })

        getPageSettings()
            .then(setPageSettings)
            .catch(() => {
                // не загрузились — считаем, что все страницы открыты
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
