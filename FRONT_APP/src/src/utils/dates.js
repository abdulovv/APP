// Общие функции для работы с датами. Используются в нескольких местах (профиль, подписка).

const MONTHS = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December',
]

// '2026-11-03T10:00:00Z' или '2000-05-20' -> '3 November 2026'; пустое значение -> '—'
export const formatDate = (isoDate) => {
    if (!isoDate) return '—'
    const year = Number(isoDate.slice(0, 4))
    const month = Number(isoDate.slice(5, 7))
    const day = Number(isoDate.slice(8, 10))
    return `${day} ${MONTHS[month - 1]} ${year}`
}

// TODO (сам): daysLeft(expiresAt) — сколько целых дней осталось до даты.
// Подсказка: new Date(expiresAt) - Date.now() даёт миллисекунды; в сутках 1000 * 60 * 60 * 24 мс;
// округли вверх (Math.ceil) и не возвращай отрицательное число (Math.max).
//
// TODO (сам): percentLeft(startedAt, expiresAt) — сколько процентов срока осталось (0..100), для полоски.
