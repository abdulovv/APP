import { useOutletContext } from 'react-router-dom'

function PageGuard({ pageKey, children }) {
    const { currentUser, pages } = useOutletContext()

    const page = pages.find(p => p.pageKey === pageKey)

    const isAdmin = currentUser?.role === 'ADMIN'
    const isBlocked = !isAdmin && page !== undefined && !page.isActive

    if (isBlocked) {
        return (
            <div className="card">
                <h1 className="cardTitle">This page isnt available</h1>
                {page.reason !== null ? <p className="stubText">Reason:{page.reason}</p> : null}
            </div>
        )
    }

    return children
}

export default PageGuard
