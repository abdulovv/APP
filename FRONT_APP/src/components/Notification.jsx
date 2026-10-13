import '../css/components/Notification.css'

function Notification({ notice }) {
    if (!notice) return null

    return (
        <div key={notice.id} className={`notification ${notice.type}`}>
            {notice.text}
        </div>
    )
}

export default Notification
