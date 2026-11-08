import { useState } from 'react'
import InputField from '../common/InputField.jsx'
import { addCard } from '../../api/paymentsApi.js'

const EMPTY_FORM = {
    cardNumber: '',
    holderName: '',
    expiry: '',
    cvc: '',
    makeDefault: false,
}

const formatCardNumber = (value) =>
    value.replace(/\D/g, '').slice(0, 16).replace(/(.{4})/g, '$1 ').trim()

const formatExpiry = (value) => {
    let digits = value.replace(/\D/g, '').slice(0, 4)
    if (digits.length >= 2) {
        let month = Number(digits.slice(0, 2))
        if (month > 12) month = 12
        if (month < 1) month = 1
        digits = String(month).padStart(2, '0') + digits.slice(2)
    }
    return digits.length > 2 ? `${digits.slice(0, 2)}/${digits.slice(2)}` : digits
}

const formatHolderName = (value) =>
    value.toUpperCase().replace(/[^A-Z ]/g, '').replace(/^ +/, '').replace(/ {2,}/g, ' ')

function AddCardModal({ onClose, onAdded }) {
    const [form, setForm] = useState(EMPTY_FORM)
    const [error, setError] = useState('')
    const [saving, setSaving] = useState(false)

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target
        let next = value
        if (name === 'cardNumber') next = formatCardNumber(value)
        if (name === 'expiry') next = formatExpiry(value)
        if (name === 'holderName') next = formatHolderName(value)
        if (name === 'cvc') next = value.replace(/\D/g, '').slice(0, 4)
        setForm((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : next }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setError('')

        const [month, year] = form.expiry.split('/')
        if (form.cardNumber.replace(/\s/g, '').length !== 16) {
            setError('Card number must contain 16 digits.')
            return
        }
        if (form.holderName.trim().split(' ').length < 2) {
            setError('Enter first and last name as on the card.')
            return
        }
        if (!month || !year || year.length !== 2 || Number(month) < 1 || Number(month) > 12) {
            setError('Enter the expiry date as MM/YY.')
            return
        }
        if (form.cvc.length < 3) {
            setError('Enter the CVC.')
            return
        }

        setSaving(true)
        try {
            const card = await addCard({
                cardNumber: form.cardNumber,
                holderName: form.holderName.trim(),
                expMonth: Number(month),
                expYear: 2000 + Number(year),
                cvc: form.cvc,
                makeDefault: form.makeDefault,
            })
            onAdded(card)
        } catch (err) {
            setError(err.message)
            setSaving(false)
        }
    }

    return (
        <div className="profileModalOverlay">
            <form className="profileModal" onSubmit={handleSubmit}>
                <h2 className="profileSectionTitle">Add card</h2>
                <p className="profileHint">Enter data about your payment method</p>

                <InputField
                    label="Card number"
                    name="cardNumber"
                    inputMode="numeric"
                    placeholder="0000 0000 0000 0000"
                    value={form.cardNumber}
                    onChange={handleChange}
                />

                <div className="profileFormGrid">
                    <InputField
                        label="Expires"
                        name="expiry"
                        inputMode="numeric"
                        placeholder="MM/YY"
                        value={form.expiry}
                        onChange={handleChange}
                    />
                    <InputField
                        label="CVC/CVV"
                        type="password"
                        name="cvc"
                        inputMode="numeric"
                        placeholder="123"
                        value={form.cvc}
                        onChange={handleChange}
                    />
                </div>

                <InputField
                    label="Name on card"
                    name="holderName"
                    placeholder="FIRSTNAME LASTNAME"
                    value={form.holderName}
                    onChange={handleChange}
                />

                <label className="checkboxLine">
                    <input
                        type="checkbox"
                        name="makeDefault"
                        checked={form.makeDefault}
                        onChange={handleChange}
                    />
                    Make this my default card
                </label>

                {error && <p className="profileError">{error}</p>}

                <div className="profileModalActions">
                    <button type="button" className="btn btnSecondary btnSmall" onClick={onClose}>
                        Cancel
                    </button>
                    <button type="submit" className="btn btnSmall" disabled={saving}>
                        {saving ? 'Adding...' : 'Add card'}
                    </button>
                </div>
            </form>
        </div>
    )
}

export default AddCardModal
