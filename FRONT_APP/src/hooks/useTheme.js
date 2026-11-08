import { useEffect, useState } from 'react'

const STORAGE_KEY = 'theme'

export function getInitialTheme() {
    try {
        const saved = localStorage.getItem(STORAGE_KEY)
        if (saved === 'light' || saved === 'dark') return saved
    } catch {
    }
    return window.matchMedia?.('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

export function applyTheme(theme) {
    document.documentElement.dataset.theme = theme
}

export function useTheme() {
    const [theme, setTheme] = useState(() => document.documentElement.dataset.theme || getInitialTheme())

    useEffect(() => {
        applyTheme(theme)
        try {
            localStorage.setItem(STORAGE_KEY, theme)
        } catch {
        }
    }, [theme])

    const toggleTheme = () => setTheme((current) => (current === 'dark' ? 'light' : 'dark'))

    return [theme, toggleTheme]
}
