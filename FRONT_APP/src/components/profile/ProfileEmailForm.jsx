import { forwardRef, useImperativeHandle, useState } from "react"
import { updateEmail } from "../../api/ProfileApi.jsx"

const ProfileEmailForm = forwardRef(({ email, onSaved, onNotify }, ref) => {
    const [value, setValue] = useState(email)

    const handleChange = (e) => setValue(e.target.value)

    useImperativeHandle(ref, () => ({
        save: async () => {
            // Простая проверка формата почты на клиенте — сервер всё равно
            // должен провалидировать её ещё раз, это только для UX.
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
            if (!emailRegex.test(value)) {
                onNotify({ type: "warning", text: "Введите корректный email." })
                throw new Error("validation")
            }

            try {
                const data = await updateEmail({ email: value })
                onSaved(value)
                return data.message || "Email обновлён."
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
                    <span className="inputLabel">Email</span>
                    <input
                        className="InputField"
                        type="email"
                        name="email"
                        value={value}
                        onChange={handleChange}
                        placeholder="example@gmail.com"
                        required
                    />
                </label>
            </div>
        </div>
    )
})

export default ProfileEmailForm
