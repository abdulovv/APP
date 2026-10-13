import { useState } from 'react'
import { Link } from 'react-router-dom'
import InputField from './InputField.jsx'
import CountrySelect from './CountrySelect.jsx'

const MIN_AGE = 12
const MAX_AGE = 100
const MIN_PASSWORD_LENGTH = 8

const EMPTY_FORM = {
    firstname: '',
    lastname: '',
    birthDate: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: '',
}

const currentYear = new Date().getFullYear()
const minBirthDate = `${currentYear - MAX_AGE}-01-01`
const maxBirthDate = `${currentYear - MIN_AGE}-12-31`

function getAge(dateString) {
    const birth = new Date(dateString)
    const today = new Date()
    let age = today.getFullYear() - birth.getFullYear()
    const birthdayPassed =
        today.getMonth() > birth.getMonth() ||
        (today.getMonth() === birth.getMonth() && today.getDate() >= birth.getDate())
    if (!birthdayPassed) age--
    return age
}

function validateStep(step, form, country) {
    if (step === 1) {
        const age = getAge(form.birthDate)
        if (age < MIN_AGE) return `You must be at least ${MIN_AGE} years old to register.`
        if (age > MAX_AGE) return `Age cannot be more than ${MAX_AGE} years.`
    }

    if (step === 2) {
        if (!country) return 'Select your country.'
        const digits = form.phoneNumber.slice(country.phoneCode.length)
        if (digits.length < country.minPhoneLength) {
            return `Phone number for ${country.name} must have at least ${country.minPhoneLength} digits.`
        }
    }

    if (step === 3) {
        if (form.password.length < MIN_PASSWORD_LENGTH) {
            return `Password must be at least ${MIN_PASSWORD_LENGTH} characters long.`
        }
        if (form.password !== form.confirmPassword) return 'Passwords do not match!'
    }

    return ''
}

function SignUpForm({ onSubmit, onError, isLoading }) {
    const [step, setStep] = useState(1)
    const [form, setForm] = useState(EMPTY_FORM)
    const [country, setCountry] = useState(null)

    const handleChange = (e) => {
        const { name, value } = e.target

        if ((name === 'firstname' || name === 'lastname') && !/^[a-zA-Zа-яА-ЯёЁ\s-]*$/.test(value)) {
            return
        }

        if (name === 'phoneNumber') {
            const digits = value.slice(country.phoneCode.length)
            const isValid =
                value.startsWith(country.phoneCode) &&
                /^\d*$/.test(digits) &&
                digits.length <= country.maxPhoneLength
            if (!isValid) return
        }

        setForm((prev) => ({ ...prev, [name]: value }))
    }

    const handleCountryChange = (selected) => {
        setCountry(selected)
        setForm((prev) => ({ ...prev, phoneNumber: selected.phoneCode }))
    }

    const handleSubmit = (e) => {
        e.preventDefault()

        const error = validateStep(step, form, country)
        if (error) {
            onError(error)
            return
        }

        if (step < 3) {
            setStep(step + 1)
            return
        }

        onSubmit({
            firstname: form.firstname,
            lastname: form.lastname,
            birthDate: form.birthDate,
            email: form.email,
            phoneNumber: form.phoneNumber,
            password: form.password,
            countryId: country.id,
        })
    }

    return (
        <form className="card authForm" onSubmit={handleSubmit}>
            <h1 className="cardTitle">Step {step} of 3</h1>

            {step === 1 && (
                <>
                    <InputField label="First Name" name="firstname" value={form.firstname} onChange={handleChange} placeholder="Enter your first name" required />
                    <InputField label="Last Name" name="lastname" value={form.lastname} onChange={handleChange} placeholder="Enter your last name" required />
                    <InputField label="Date of Birth" type="date" name="birthDate" value={form.birthDate} onChange={handleChange} min={minBirthDate} max={maxBirthDate} required />
                </>
            )}

            {step === 2 && (
                <>
                    <CountrySelect value={country?.id} onChange={handleCountryChange} />
                    <InputField label="Email" type="email" name="email" value={form.email} onChange={handleChange} placeholder="example@gmail.com" required />
                    <InputField
                        label="Phone"
                        type="tel"
                        name="phoneNumber"
                        value={form.phoneNumber}
                        onChange={handleChange}
                        placeholder={country ? 'Enter your phone' : 'Select a country first'}
                        disabled={!country}
                        required
                    />
                </>
            )}

            {step === 3 && (
                <>
                    <InputField label="Password" type="password" name="password" value={form.password} onChange={handleChange} placeholder="Enter your password" required />
                    <InputField label="Confirm Password" type="password" name="confirmPassword" value={form.confirmPassword} onChange={handleChange} placeholder="Confirm your password" required />
                </>
            )}

            <div className="authLinks">
                <Link to="/sign-in">Sign In</Link>
            </div>

            <div className="authButtons">
                {step > 1 && (
                    <button className="btn btnSecondary" type="button" onClick={() => setStep(step - 1)}>
                        Back
                    </button>
                )}
                <button className="btn" type="submit" disabled={isLoading}>
                    {step < 3 ? 'Next' : 'Register'}
                </button>
            </div>
        </form>
    )
}

export default SignUpForm
