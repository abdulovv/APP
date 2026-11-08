const MONTHS = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December',
]

export const formatDate = (isoDate) => {
    if (!isoDate) return '—'
    const year = Number(isoDate.slice(0, 4))
    const month = Number(isoDate.slice(5, 7))
    const day = Number(isoDate.slice(8, 10))
    return `${day} ${MONTHS[month - 1]} ${year}`
}

const DAY_MS = 1000 * 60 * 60 * 24

export const daysLeft = (expiresAt) => {
    if (!expiresAt) return 0
    const diff = new Date(expiresAt) - Date.now()
    return Math.max(0, Math.ceil(diff / DAY_MS))
}

export const totalDays = (startedAt, expiresAt) => {
    if (!startedAt || !expiresAt) return 0
    return Math.max(1, Math.round((new Date(expiresAt) - new Date(startedAt)) / DAY_MS))
}

export const percentLeft = (startedAt, expiresAt) => {
    const total = totalDays(startedAt, expiresAt)
    if (total === 0) return 0
    return Math.min(100, Math.round((daysLeft(expiresAt) / total) * 100))
}
