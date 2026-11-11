import { useState } from 'react'
import InputField from '../common/InputField.jsx'
import { sendPasswordCode, updatePassword } from '../../api/profileApi.js'

const EMPTY_FIELDS = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
    code: '',
}

function PasswordTab({ showNotification }) {
    const [fields, setFields] = useState(EMPTY_FIELDS)
    const [codeSent, setCodeSent] = useState(false)
    const [loading, setLoading] = useState(false)

    const handleChange = (e) => {
        const { name, value } = e.target
        setFields((prev) => ({ ...prev, [name]: value }))
    }

    const validatePasswords = () => {
        if (!fields.currentPassword) {
            showNotification('error', 'Enter your current password.')
            return false
        }
        if (fields.newPassword.length < 8) {
            showNotification('error', 'New password must be at least 8 characters.')
            return false
        }
        if (fields.newPassword !== fields.confirmPassword) {
            showNotification('error', 'Passwords do not match.')
            return false
        }
        return true
    }

    const handleSendCode = async () => {
        if (!validatePasswords()) return

        setLoading(true)
        try {
            await sendPasswordCode()
            setCodeSent(true)
            showNotification('success', 'We sent a code to your email.')
        } catch (error) {
            if (error.status === 409) {
                // код уже отправлен и ещё действует — просто переходим к вводу
                setCodeSent(true)
                showNotification('error', error.message)
            } else {
                showNotification('error', error.message)
            }
        } finally {
            setLoading(false)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (!codeSent) {
            handleSendCode()
            return
        }
        if (!/^\d{6}$/.test(fields.code)) {
            showNotification('error', 'Enter the 6-digit code.')
            return
        }

        setLoading(true)
        try {
            await updatePassword(fields.currentPassword, fields.newPassword, fields.code)
            setFields(EMPTY_FIELDS)
            setCodeSent(false)
            showNotification('success', 'Password updated.')
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setLoading(false)
        }
    }

    const handleBack = () => {
        setCodeSent(false)
        setFields((prev) => ({ ...prev, code: '' }))
    }

    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Change password</h2>
            <p className="profileHint">
                {codeSent
                    ? 'Enter the 6-digit code we sent to your email.'
                    : 'Use at least 8 characters. We will email you a confirmation code.'}
            </p>

            <form className="profileForm" onSubmit={handleSubmit}>
                {!codeSent ? (
                    <>
                        <InputField
                            label="Current password"
                            type="password"
                            name="currentPassword"
                            placeholder="Current password"
                            value={fields.currentPassword}
                            onChange={handleChange}
                        />

                        <div className="profileFormGrid">
                            <InputField
                                label="New password"
                                type="password"
                                name="newPassword"
                                placeholder="New password"
                                value={fields.newPassword}
                                onChange={handleChange}
                            />
                            <InputField
                                label="Repeat new password"
                                type="password"
                                name="confirmPassword"
                                placeholder="Repeat new password"
                                value={fields.confirmPassword}
                                onChange={handleChange}
                            />
                        </div>
                    </>
                ) : (
                    <InputField
                        label="Confirmation code"
                        type="text"
                        name="code"
                        placeholder="000000"
                        value={fields.code}
                        onChange={handleChange}
                        maxLength={6}
                        inputMode="numeric"
                        autoComplete="one-time-code"
                    />
                )}

                <div className="profileFormActions">
                    {codeSent && (
                        <button type="button" className="btn btnSmall btnSecondary" onClick={handleBack} disabled={loading}>
                            Back
                        </button>
                    )}
                    <button type="submit" className="btn btnSmall" disabled={loading}>
                        {codeSent ? 'Update password' : 'Send code'}
                    </button>
                </div>
            </form>
        </section>
    )
}

export default PasswordTab
