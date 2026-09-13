import { forwardRef, useImperativeHandle, useState } from "react"
import { updateName } from "../../api/ProfileApi.jsx"

// firstname/lastname приходят из ProfilePage (текущие значения пользователя),
// onSaved — callback, чтобы обновить состояние пользователя в родителе после успеха,
// onNotify — callback для показа единого уведомления на странице (success/error).
// Общей кнопки Save тут больше нет — родитель дергает save() через ref у всех форм разом.
const ProfileNameForm = forwardRef(({ firstname, lastname, onSaved, onNotify }, ref) => {
    const [values, setValues] = useState({ firstname, lastname })

    const handleChange = (e) => {
        const { name, value } = e.target

        // Та же валидация "только буквы", что и в SignUpForm — для единообразия.
        const onlyLettersRegex = /^[a-zA-Zа-яА-ЯёЁ\s-]*$/
        if (!onlyLettersRegex.test(value)) return

        setValues(prev => ({ ...prev, [name]: value }))
    }

    useImperativeHandle(ref, () => ({
        save: async () => {
            if (!values.firstname.trim() || !values.lastname.trim()) {
                onNotify({ type: "warning", text: "Имя и фамилия не могут быть пустыми." })
                throw new Error("validation")
            }

            try {
                const data = await updateName(values)
                onSaved(values) // Прокидываем новые значения наверх, в состояние ProfilePage
                return data.message || "Имя обновлено."
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
                    <span className="inputLabel">First Name</span>
                    <input
                        className="InputField"
                        type="text"
                        name="firstname"
                        value={values.firstname}
                        onChange={handleChange}
                        placeholder="Enter your first name"
                        required
                    />
                </label>

                <label className="inputWrapper">
                    <span className="inputLabel">Last Name</span>
                    <input
                        className="InputField"
                        type="text"
                        name="lastname"
                        value={values.lastname}
                        onChange={handleChange}
                        placeholder="Enter your last name"
                        required
                    />
                </label>
            </div>
        </div>
    )
})

export default ProfileNameForm
