import { Navigate, useOutletContext } from 'react-router-dom'

function AdminOnly({ children }) {
    const { currentUser } = useOutletContext()

    if (!currentUser) return null

    if (currentUser.role !== 'ADMIN') {
        return <Navigate to="/diary" replace />
    }

    return children
}

export default AdminOnly
