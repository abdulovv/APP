import { useState } from 'react'
import { Link } from 'react-router-dom'
import InputField from '../common/InputField.jsx'
import CountrySelect from './CountrySelect.jsx'
import BirthDateSelect from './BirthDateSelect.jsx'

const MIN_AGE = 12
const MAX_AGE = 100
const MIN_PASSWORD_LENGTH = 8
const CODE_LENGTH = 6
const TOTAL_STEPS = 4

const EMPTY_FORM = {
    firstname: '',
    lastname: '',
    birthDate: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: '',
    code: '',
}

const currentYear = new Date().getFullYear()

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
        if (!form.birthDate) return 'Select your date of birth.'
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

    if (step === 4) {
        if (!new RegExp(`^\\d{${CODE_LENGTH}}$`).test(form.code)) {
            return `Enter the ${CODE_LENGTH}-digit code from the email.`
        }
    }

    return ''
}

function SignUpForm({ onSubmit, onSendCode, onError, isLoading }) {
    const [step, setStep] = useState(1)
    const [form, setForm] = useState(EMPTY_FORM)
    const [country, setCountry] = useState(null)
    const [codeSentTo, setCodeSentTo] = useState('')

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

        if (name === 'code' && !new RegExp(`^\\d{0,${CODE_LENGTH}}$`).test(value)) {
            return
        }

        setForm((prev) => ({ ...prev, [name]: value }))
    }

    const handleCountryChange = (selected) => {
        setCountry(selected)
        setForm((prev) => ({ ...prev, phoneNumber: selected.phoneCode }))
    }

    const handleSubmit = async (e) => {
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

        if (step === 3) {
            if (codeSentTo !== form.email) {
                const sent = await onSendCode(form.email)
                if (!sent) return
                setCodeSentTo(form.email)
            }
            setStep(4)
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
            code: form.code,
        })
    }

    return (
        <form className="card authForm" onSubmit={handleSubmit}>
            <h1 className="cardTitle">Step {step} of {TOTAL_STEPS}</h1>

            {step === 1 && (
                <>
                    <InputField label="First Name" name="firstname" value={form.firstname} onChange={handleChange} placeholder="Enter your first name" required />
                    <InputField label="Last Name" name="lastname" value={form.lastname} onChange={handleChange} placeholder="Enter your last name" required />
                    <BirthDateSelect
                        value={form.birthDate}
                        onChange={(birthDate) => setForm((prev) => ({ ...prev, birthDate }))}
                        minYear={currentYear - MAX_AGE}
                        maxYear={currentYear - MIN_AGE}
                    />
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

            {step === 4 && (
                <>
                    <p className="authHint">
                        We sent a {CODE_LENGTH}-digit code to <b>{form.email}</b>. Enter it to finish registration.
                    </p>
                    <InputField
                        label="Verification code"
                        name="code"
                        value={form.code}
                        onChange={handleChange}
                        placeholder="000000"
                        inputMode="numeric"
                        maxLength={CODE_LENGTH}
                        autoComplete="one-time-code"
                        autoFocus
                        required
                    />
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
                    {step < 3 ? 'Next' : step === 3 ? 'Send code' : 'Register'}
                </button>
            </div>
        </form>
    )
}

export default SignUpForm
