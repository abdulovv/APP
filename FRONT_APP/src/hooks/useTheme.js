import { useEffect, useState } from 'react'

const STORAGE_KEY = 'theme'

// Тема, которую пользователь выбрал раньше, или системная (светлая/тёмная в ОС)
export function getInitialTheme() {
    try {
        const saved = localStorage.getItem(STORAGE_KEY)
        if (saved === 'light' || saved === 'dark') return saved
    } catch {
        // localStorage может быть недоступен (приватный режим) — тогда берём системную
    }
    return window.matchMedia?.('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

// Тема хранится в атрибуте <html data-theme="...">, на него завязаны переменные в index.css
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
            // не сохранилось — не страшно, при следующем заходе возьмётся системная
        }
    }, [theme])

    const toggleTheme = () => setTheme((current) => (current === 'dark' ? 'light' : 'dark'))

    return [theme, toggleTheme]
}
