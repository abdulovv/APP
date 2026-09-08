import { useState } from "react"
import { updatePassword } from "../../api/ProfileApi.jsx"

const EMPTY_FORM = { currentPassword: "", newPassword: "", confirmNewPassword: "" }

// Пароль, в отличие от имени/почты/телефона, не хранится в стейте ProfilePage —
// после успешной смены просто очищаем поля формы, поэтому onSaved сюда не передаём.
const ProfilePasswordForm = ({ onNotify }) => {
    const [values, setValues] = useState(EMPTY_FORM)
    const [isSaving, setIsSaving] = useState(false)

    const handleChange = (e) => {
        const { name, value } = e.target
        setValues(prev => ({ ...prev, [name]: value }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (values.newPassword.length < 8) {
            onNotify({ type: "warning", text: "Новый пароль должен быть не короче 8 символов." })
            return
        }

        if (values.newPassword !== values.confirmNewPassword) {
            onNotify({ type: "warning", text: "Новые пароли не совпадают." })
            return
        }

        setIsSaving(true)
        try {
            const data = await updatePassword({
                currentPassword: values.currentPassword,
                newPassword: values.newPassword,
            })
            onNotify({ type: "success", text: data.message || "Пароль обновлён." })
            setValues(EMPTY_FORM) // Чистим поля — не оставляем пароли в форме после сабмита
        } catch (error) {
            onNotify({ type: "error", text: error.message })
        } finally {
            setIsSaving(false)
        }
    }

    return (
        <form className="profileForm" onSubmit={handleSubmit}>
            
            <div className="profileFormRow">
                <label className="inputWrapper">
                    <span className="inputLabel">Current Password</span>
                    <input
                        className="InputField"
                        type="password"
                        name="currentPassword"
                        value={values.currentPassword}
                        onChange={handleChange}
                        placeholder="Enter your current password"
                        required
                    />
                </label>

                <label className="inputWrapper">
                    <span className="inputLabel">New Password</span>
                    <input
                        className="InputField"
                        type="password"
                        name="newPassword"
                        value={values.newPassword}
                        onChange={handleChange}
                        placeholder="Enter new password"
                        required
                    />
                </label>

                <label className="inputWrapper">
                    <span className="inputLabel">Confirm New Password</span>
                    <input
                        className="InputField"
                        type="password"
                        name="confirmNewPassword"
                        value={values.confirmNewPassword}
                        onChange={handleChange}
                        placeholder="Confirm new password"
                        required
                    />
                </label>
            </div>

            <button className="submitBtn" type="submit" disabled={isSaving}>
                {isSaving ? "Saving..." : "Save"}
            </button>
        </form>
    )
}

export default ProfilePasswordForm
