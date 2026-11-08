import { useCallback, useEffect, useState } from 'react'

export function useNotice(duration = 3000) {
    const [notice, setNotice] = useState(null)

    useEffect(() => {
        if (!notice) return
        const timer = setTimeout(() => setNotice(null), duration)
        return () => clearTimeout(timer)
    }, [notice, duration])

    const showNotice = useCallback((type, text) => {
        setNotice({ type, text, id: Date.now() })
    }, [])

    return [notice, showNotice]
}
