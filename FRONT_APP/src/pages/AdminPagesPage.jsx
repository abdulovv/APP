import { useEffect, useState } from 'react'
import { useOutletContext } from 'react-router-dom'
import '../css/pages/AdminPage.css'
import { updatePageSetting } from '../api/pageSettingsApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/common/Notification.jsx'
import AdminGuard from '../components/admin/AdminGuard.jsx'

const PAGES = [
    { key: 'diary', path: '/diary', label: 'Diary', alwaysOn: true },
    { key: 'plan', path: '/plan', label: 'My plan' },
    { key: 'products', path: '/products', label: 'Products' },
    { key: 'statistics', path: '/statistics', label: 'Statistics' },
    { key: 'profile', path: '/profile', label: 'Profile' },
]

function PageCard({ page, setting, onToggle }) {
    const [reason, setReason] = useState(setting?.reason ?? '')
    const [isSaving, setIsSaving] = useState(false)

    const active = page.alwaysOn || setting?.isActive !== false

    const handleToggle = async () => {
        setIsSaving(true)
        try {
            await onToggle(page.key, !active, reason)
        } finally {
            setIsSaving(false)
        }
    }

    return (
        <div className={active ? 'pageCard' : 'pageCard inactive'}>
            <div className="pageCardHeader">
                <span className="pageCardTitle">{page.label}</span>
                <span className={active ? 'pageStatus on' : 'pageStatus off'}>
                    {page.alwaysOn ? 'Always on' : active ? 'Active' : 'Disabled'}
                </span>
            </div>

            <div className="pagePreview">
                <iframe src={page.path} title={page.label} loading="lazy" tabIndex={-1} />
            </div>

            {!page.alwaysOn && (
                <>
                    <input
                        className="searchInput pageReasonInput"
                        placeholder="Reason"
                        value={reason}
                        onChange={e => setReason(e.target.value)}
                        maxLength={255}
                    />

                    <button
                        className={active ? 'dangerBtn pageToggle' : 'successBtn pageToggle'}
                        onClick={handleToggle}
                        disabled={isSaving}
                    >
                        {isSaving ? '...' : active ? 'Deactivate' : 'Activate'}
                    </button>
                </>
            )}
        </div>
    )
}

function AdminPagesPage() {
    const { pageSettings, setPageSettings } = useOutletContext()
    const [notification, showNotification] = useNotification()

    useEffect(() => {
        document.title = 'Admin · Pages'
    }, [])

    const handleToggle = async (pageKey, isActive, reason) => {
        try {
            await updatePageSetting(pageKey, { isActive, reason })
            setPageSettings(pageSettings.map(p =>
                p.pageKey === pageKey ? { ...p, isActive, reason: reason.trim() || null } : p
            ))
            showNotification('success', isActive ? 'Page activated' : 'Page deactivated')
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    return (
        <AdminGuard>
            <div className="adminPage">
                <Notification notification={notification} />

                <h1>Pages</h1>

                <div className="pagesGrid">
                    {PAGES.map(page => {
                        const setting = pageSettings.find(p => p.pageKey === page.key)
                        return (
                            <PageCard
                                key={setting ? page.key : `${page.key}-loading`}
                                page={page}
                                setting={setting}
                                onToggle={handleToggle}
                            />
                        )
                    })}
                </div>
            </div>
        </AdminGuard>
    )
}

export default AdminPagesPage
