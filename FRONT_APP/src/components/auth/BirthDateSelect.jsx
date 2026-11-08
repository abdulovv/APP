import { useState } from 'react'

const MONTHS = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December',
]

const pad = (n) => String(n).padStart(2, '0')

const daysInMonth = (month, year) => new Date(year || 2000, month, 0).getDate()

function BirthDateSelect({ label = 'Date of Birth', value, onChange, minYear, maxYear }) {
    const [parts, setParts] = useState(() => {
        const [year = '', month = '', day = ''] = (value || '').split('-')
        return {
            year,
            month: month ? String(Number(month)) : '',
            day: day ? String(Number(day)) : '',
        }
    })

    const update = (patch) => {
        const next = { ...parts, ...patch }

        if (next.day && next.month && Number(next.day) > daysInMonth(Number(next.month), Number(next.year))) {
            next.day = ''
        }

        setParts(next)
        onChange(next.day && next.month && next.year ? `${next.year}-${pad(next.month)}-${pad(next.day)}` : '')
    }

    const dayCount = parts.month ? daysInMonth(Number(parts.month), Number(parts.year)) : 31
    const days = Array.from({ length: dayCount }, (_, i) => i + 1)
    const years = Array.from({ length: maxYear - minYear + 1 }, (_, i) => maxYear - i)

    return (
        <div className="field">
            <span className="fieldLabel">{label}</span>
            <div className="dateSelects">
                <select
                    className="fieldInput selectField"
                    value={parts.day}
                    onChange={(e) => update({ day: e.target.value })}
                    aria-label="Day"
                    required
                >
                    <option value="" disabled>Day</option>
                    {days.map((day) => (
                        <option key={day} value={day}>{day}</option>
                    ))}
                </select>

                <select
                    className="fieldInput selectField"
                    value={parts.month}
                    onChange={(e) => update({ month: e.target.value })}
                    aria-label="Month"
                    required
                >
                    <option value="" disabled>Month</option>
                    {MONTHS.map((name, index) => (
                        <option key={name} value={index + 1}>{name}</option>
                    ))}
                </select>

                <select
                    className="fieldInput selectField"
                    value={parts.year}
                    onChange={(e) => update({ year: e.target.value })}
                    aria-label="Year"
                    required
                >
                    <option value="" disabled>Year</option>
                    {years.map((year) => (
                        <option key={year} value={year}>{year}</option>
                    ))}
                </select>
            </div>
        </div>
    )
}

export default BirthDateSelect
