import { useState } from 'react'
import { formatDate, daysLeft, totalDays, percentLeft } from '../../utils/dates.js'
import { cancelPremium } from '../../api/premiumApi.js'

function getStatus(user) {
    if (user.premium) return user.cancelledAt ? 'cancelled' : 'active'
    if (user.startedAt) return 'expired'
    return 'never'
}

function CurrentPlanTab({ user, onOpenPlans, onChanged, showNotification }) {
    const status = getStatus(user)
    const [confirming, setConfirming] = useState(false)
    const [cancelling, setCancelling] = useState(false)

    const handleCancel = async () => {
        setCancelling(true)
        try {
            await cancelPremium()
            showNotification('success', `Subscription cancelled. Premium stays active until ${formatDate(user.expiresAt)}.`)
            setConfirming(false)
            onChanged()
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setCancelling(false)
        }
    }

    const isPremium = status === 'active' || status === 'cancelled'

    return (
        <section className="planSection">
            <div className="planHead">
                <div className="planHeadTitle">
                    <h2 className="planSectionTitle">{isPremium ? 'Premium' : 'Free plan'}</h2>
                    {status === 'active' && <span className="planBadge success">Active</span>}
                    {status === 'cancelled' && <span className="planBadge">Cancelled</span>}
                    {status === 'expired' && <span className="planBadge muted">Expired</span>}
                </div>

                {status === 'active' && !confirming && (
                    <button type="button" className="btn btnSecondary btnSmall" onClick={() => setConfirming(true)}>
                        Cancel subscription
                    </button>
                )}
                {status === 'expired' && (
                    <button type="button" className="btn btnSmall" onClick={onOpenPlans}>
                        Extend Premium
                    </button>
                )}
                {status === 'never' && (
                    <button type="button" className="btn btnSmall" onClick={onOpenPlans}>
                        Get Premium
                    </button>
                )}
            </div>

            {confirming && (
                <div className="planConfirm">
                    <p className="planConfirmText">
                        Cancel your subscription? Premium will stay active until {formatDate(user.expiresAt)}.
                    </p>
                    <div className="planConfirmActions">
                        <button type="button" className="btn btnSecondary btnSmall" onClick={() => setConfirming(false)}>
                            Keep Premium
                        </button>
                        <button type="button" className="btn btnDanger btnSmall" disabled={cancelling} onClick={handleCancel}>
                            {cancelling ? 'Cancelling...' : 'Yes, cancel'}
                        </button>
                    </div>
                </div>
            )}

            {isPremium && (
                <>
                    <div className="planDates">
                        <div>
                            <span className="planDateLabel">Started</span>
                            <span className="planDateValue">{formatDate(user.startedAt)}</span>
                        </div>
                        <div>
                            <span className="planDateLabel">{status === 'cancelled' ? 'Active until' : 'Expires'}</span>
                            <span className="planDateValue">{formatDate(user.expiresAt)}</span>
                        </div>
                    </div>
                    <div>
                        <div className="planBar">
                            <div
                                className="planBarFill"
                                style={{ width: `${percentLeft(user.startedAt, user.expiresAt)}%` }}
                            />
                        </div>
                        <p className="planLeft">
                            {daysLeft(user.expiresAt)} days left of {totalDays(user.startedAt, user.expiresAt)}
                            {status === 'cancelled' && ' · cancelled, will not be renewed'}
                        </p>
                    </div>
                </>
            )}

            {status === 'expired' && (
                <div className="planDates">
                    <div>
                        <span className="planDateLabel">Premium expired on</span>
                        <span className="planDateValue">{formatDate(user.expiresAt)}</span>
                    </div>
                </div>
            )}

            {status === 'never' && (
                <p className="planLeft">
                    You don't have Premium yet. Pick a plan to get custom products, statistics and weight tracking.
                </p>
            )}
        </section>
    )
}

export default CurrentPlanTab
