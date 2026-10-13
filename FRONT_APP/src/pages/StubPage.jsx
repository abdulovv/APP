import { useEffect } from 'react'

function StubPage({ title }) {
    useEffect(() => {
        document.title = title
    }, [title])

    return (
        <div className="card">
            <h1 className="cardTitle">{title}</h1>
            <p className="stubText">Coming soon</p>
        </div>
    )
}

export default StubPage
