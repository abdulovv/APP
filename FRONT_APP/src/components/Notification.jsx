import '../css/components/Notification.css'

function Notification({ notification }) {
    if (!notification) return null

    return (
        <div key={notification.id} className={`notification ${notification.type}`}>
            {notification.text}
        </div>
    )
}

export default Notification
