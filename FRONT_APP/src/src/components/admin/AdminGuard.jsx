import { Navigate, useOutletContext } from 'react-router-dom'

// Обёртка для админских страниц: пока юзер грузится — пусто, не админ — в дневник.
// Это только удобство, настоящая проверка прав — на бэкенде.
function AdminGuard({ children }) {
    const { currentUser } = useOutletContext()

    if (!currentUser) return null

    if (currentUser.role !== 'ADMIN') {
        return <Navigate to="/diary" replace />
    }

    return children
}

export default AdminGuard
