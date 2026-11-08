import { useOutletContext } from 'react-router-dom'

function PageAvailabilityGuard({ pageKey, children }) {
    const { currentUser, pageSettings } = useOutletContext()

    const pageSetting = pageSettings.find(p => p.pageKey === pageKey)

    const isAdmin = currentUser?.role === 'ADMIN'
    const isBlocked = !isAdmin && pageSetting !== undefined && !pageSetting.isActive

    if (isBlocked) {
        return (
            <div className="card">
                <h1 className="cardTitle">This page isn't available</h1>
                {pageSetting.reason && <p className="stubText">Reason: {pageSetting.reason}</p>}
            </div>
        )
    }

    return children
}

export default PageAvailabilityGuard
