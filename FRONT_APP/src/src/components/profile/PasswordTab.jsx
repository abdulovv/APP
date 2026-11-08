// Вкладка «Password»: смена пароля.
import { useState } from 'react'
import InputField from '../common/InputField.jsx'
import { updatePassword } from '../../api/profileApi.js'

const EMPTY_FIELDS = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
}

function PasswordTab({ showNotification }) {
    const [fields, setFields] = useState(EMPTY_FIELDS)

    const handleChange = (e) => {
        const { name, value } = e.target
        setFields((prev) => ({ ...prev, [name]: value }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (!fields.currentPassword) {
            showNotification('error', 'Enter your current password.')
            return
        }
        if (fields.newPassword.length < 8) {
            showNotification('error', 'New password must be at least 8 characters.')
            return
        }
        if (fields.newPassword !== fields.confirmPassword) {
            showNotification('error', 'Passwords do not match.')
            return
        }

        try {
            await updatePassword(fields.currentPassword, fields.newPassword)
            setFields(EMPTY_FIELDS)
            showNotification('success', 'Password updated.')
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    return (
        <section className="profileSection profileSectionPadded">
            <h2 className="profileSectionTitle">Change password</h2>
            <p className="profileHint">Use at least 8 characters.</p>

            <form className="profileForm" onSubmit={handleSubmit}>
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

                <div className="profileFormActions">
                    <button type="submit" className="btn btnSmall">Update password</button>
                </div>
            </form>
        </section>
    )
}

export default PasswordTab
