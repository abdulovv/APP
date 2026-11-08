import { useEffect } from 'react'
import { useNavigate, useOutletContext } from 'react-router-dom'
import '../../css/components/PremiumGate.css'

function PremiumGate({ feature, preview, children }) {
    const { currentUser } = useOutletContext()
    const navigate = useNavigate()

    const isLocked = currentUser !== null && !currentUser.premium && currentUser.role !== 'ADMIN'

    useEffect(() => {
        if (!isLocked) return
        const handleKey = (e) => {
            if (e.key === 'Escape') navigate('/products')
        }
        window.addEventListener('keydown', handleKey)
        return () => window.removeEventListener('keydown', handleKey)
    }, [isLocked, navigate])

    if (currentUser === null) {
        return null
    }

    if (!isLocked) {
        return children
    }

    return (
        <div className="premiumGate">
            <div className="premiumGatePreview" aria-hidden="true">
                {preview}
            </div>

            <div className="premiumGateOverlay">
                <div className="premiumGateDialog" role="dialog" aria-modal="true" aria-labelledby="premiumGateTitle">
                    <span className="premiumGateIcon" aria-hidden="true">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                            strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                            <rect x="4" y="11" width="16" height="10" rx="2" />
                            <path d="M8 11V7a4 4 0 0 1 8 0v4" />
                        </svg>
                    </span>
                    <h2 id="premiumGateTitle" className="premiumGateTitle">{feature} is a Premium feature</h2>
                    <p className="premiumGateText">
                        Get Premium to add your own products with your own nutrition values and use them in the diary.
                    </p>
                    <div className="premiumGateActions">
                        <button type="button" className="btn btnSecondary btnSmall" onClick={() => navigate('/products')}>
                            Back to catalog
                        </button>
                        <button
                            type="button"
                            className="btn btnSmall"
                            onClick={() => navigate('/plan', { state: { tab: 'plans' } })}
                            autoFocus
                        >
                            View plans
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PremiumGate
