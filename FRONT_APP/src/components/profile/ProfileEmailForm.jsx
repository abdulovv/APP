import { useState } from "react"
import { updateEmail } from "../../api/ProfileApi.jsx"

const ProfileEmailForm = ({ email, onSaved, onNotify }) => {
    const [value, setValue] = useState(email)
    const [isSaving, setIsSaving] = useState(false)

    const handleChange = (e) => setValue(e.target.value)

    const handleSubmit = async (e) => {
        e.preventDefault()

        // Простая проверка формата почты на клиенте — сервер всё равно
        // должен провалидировать её ещё раз, это только для UX.
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        if (!emailRegex.test(value)) {
            onNotify({ type: "warning", text: "Введите корректный email." })
            return
        }

        setIsSaving(true)
        try {
            const data = await updateEmail({ email: value })
            onNotify({ type: "success", text: data.message || "Email обновлён." })
            onSaved(value)
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

            <button className="submitBtn" type="submit" disabled={isSaving}>
                {isSaving ? "Saving..." : "Save"}
            </button>
        </form>
    )
}

export default ProfileEmailForm
