import { useEffect, useState } from 'react'
import '../css/components/Notification.css'

function Notification({ type, text, onClose }) {
  const [isExiting] = useState(false)

  useEffect(() => {
    const timer = setTimeout(onClose, 3000)  
    return () => clearTimeout(timer)
  }, [onClose])

  return (
    <div className={`notification ${type}`}>
      <span className="text">{text}</span>
    </div>
  )
}

export default Notification