import { useEffect } from 'react'
import { Navigate, useOutletContext } from 'react-router-dom'
import '../css/pages/AdminPage.css'

function AdminPage() {
    const { currentUser } = useOutletContext()

    useEffect(() => {
        document.title = 'Admin panel'
    }, [])

    // Пока пользователь грузится — ничего не показываем
    if (!currentUser) return null

    // Не админ открыл /admin по адресу — отправляем в дневник.
    // Это только удобство: настоящая проверка прав — на бэкенде (403 в nutrition-service)
    if (currentUser.role !== 'ADMIN') {
        return <Navigate to="/diary" replace />
    }

    return (
        <div className="adminPage">
            <h1 className="cardTitle">Admin panel</h1>
        </div>
    )
}

export default AdminPage
