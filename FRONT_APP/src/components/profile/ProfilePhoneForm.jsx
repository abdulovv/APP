import { forwardRef, useImperativeHandle, useState } from "react"
import { updatePhone } from "../../api/ProfileApi.jsx"

const ProfilePhoneForm = forwardRef(({ phoneNumber, onSaved, onNotify }, ref) => {
    const [value, setValue] = useState(phoneNumber)

    const handleChange = (e) => {
        const newValue = e.target.value
        // Разрешаем цифры и ведущий "+" (код страны), как и на регистрации.
        const phoneRegex = /^\+?\d*$/
        if (!phoneRegex.test(newValue)) return
        setValue(newValue)
    }

    useImperativeHandle(ref, () => ({
        save: async () => {
            if (value.replace(/\D/g, "").length < 5) {
                onNotify({ type: "warning", text: "Введите корректный номер телефона." })
                throw new Error("validation")
            }

            try {
                const data = await updatePhone({ phoneNumber: value })
                onSaved(value)
                return data.message || "Телефон обновлён."
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
        </div>
    )
})

export default ProfilePhoneForm
