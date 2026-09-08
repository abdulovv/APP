import { useState } from "react"
import { updatePhone } from "../../api/ProfileApi.jsx"

const ProfilePhoneForm = ({ phoneNumber, onSaved, onNotify }) => {
    const [value, setValue] = useState(phoneNumber)
    const [isSaving, setIsSaving] = useState(false)

    const handleChange = (e) => {
        const newValue = e.target.value
        // Разрешаем цифры и ведущий "+" (код страны), как и на регистрации.
        const phoneRegex = /^\+?\d*$/
        if (!phoneRegex.test(newValue)) return
        setValue(newValue)
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (value.replace(/\D/g, "").length < 5) {
            onNotify({ type: "warning", text: "Введите корректный номер телефона." })
            return
        }

        setIsSaving(true)
        try {
            const data = await updatePhone({ phoneNumber: value })
            onNotify({ type: "success", text: data.message || "Телефон обновлён." })
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
                    <span className="inputLabel">Phone</span>
                    <input
                        className="InputField"
                        type="tel"
                        name="phone"
                        value={value}
                        onChange={handleChange}
                        placeholder="Enter your phone"
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

export default ProfilePhoneForm
