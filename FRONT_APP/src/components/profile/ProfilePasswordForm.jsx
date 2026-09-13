import { forwardRef, useImperativeHandle, useState } from "react"
import { updatePassword } from "../../api/ProfileApi.jsx"

const EMPTY_FORM = { currentPassword: "", newPassword: "", confirmNewPassword: "" }

// Пароль, в отличие от имени/почты/телефона, не хранится в стейте ProfilePage —
// после успешной смены просто очищаем поля формы, поэтому onSaved сюда не передаём.
// Если поля пустые — save() ничего не делает и не мешает сохранению остальных форм.
const ProfilePasswordForm = forwardRef(({ onNotify }, ref) => {
    const [values, setValues] = useState(EMPTY_FORM)

    const handleChange = (e) => {
        const { name, value } = e.target
        setValues(prev => ({ ...prev, [name]: value }))
    }

    useImperativeHandle(ref, () => ({
        save: async () => {
            const { currentPassword, newPassword, confirmNewPassword } = values

            // Пользователь не трогал поля пароля — просто пропускаем эту форму.
            if (!currentPassword && !newPassword && !confirmNewPassword) {
                return null
            }

            if (newPassword.length < 8) {
                onNotify({ type: "warning", text: "Новый пароль должен быть не короче 8 символов." })
                throw new Error("validation")
            }

            if (newPassword !== confirmNewPassword) {
                onNotify({ type: "warning", text: "Новые пароли не совпадают." })
                throw new Error("validation")
            }

            try {
                const data = await updatePassword({ currentPassword, newPassword })
                setValues(EMPTY_FORM) // Чистим поля — не оставляем пароли в форме после сабмита
                return data.message || "Пароль обновлён."
            } catch (error) {
                onNotify({ type: "error", text: error.message })
                throw error
            }
        },
    }))

    return (
        <div className="profileForm">
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
                    />
                </label>
            </div>
        </div>
    )
})

export default ProfilePasswordForm
