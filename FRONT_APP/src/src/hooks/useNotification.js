import { useCallback, useEffect, useState } from 'react'

export function useNotification(duration = 3000) {
    const [notification, setNotification] = useState(null)

    useEffect(() => {
        if (!notification) return
        const timer = setTimeout(() => setNotification(null), duration)
        return () => clearTimeout(timer)
    }, [notification, duration])

    const showNotification = useCallback((type, text) => {
        setNotification({ type, text, id: Date.now() })
    }, [])

    return [notification, showNotification]
}
