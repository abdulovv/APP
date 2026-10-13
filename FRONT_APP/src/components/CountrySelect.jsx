import { useEffect, useState } from 'react'
import { getCountries } from '../api/countriesApi.js'

const PLACEHOLDERS = {
    loading: 'Loading countries...',
    error: 'Failed to load countries',
    ready: 'Select your country',
}

function CountrySelect({ value, onChange }) {
    const [countries, setCountries] = useState([])
    const [status, setStatus] = useState('loading')

    useEffect(() => {
        getCountries()
            .then((data) => {
                setCountries(data)
                setStatus('ready')
            })
            .catch(() => setStatus('error'))
    }, [])

    const handleChange = (e) => {
        const country = countries.find((c) => c.id === Number(e.target.value))
        onChange(country)
    }

    return (
        <label className="field">
            <span className="fieldLabel">Country</span>
            <select
                className="fieldInput selectField"
                value={value ?? ''}
                onChange={handleChange}
                disabled={status !== 'ready'}
                required
            >
                <option value="" disabled>{PLACEHOLDERS[status]}</option>
                {countries.map((country) => (
                    <option key={country.id} value={country.id} disabled={!country.availability}>
                        {country.name} ({country.phoneCode})
                    </option>
                ))}
            </select>
        </label>
    )
}

export default CountrySelect
