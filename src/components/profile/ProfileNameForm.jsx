import { useState } from "react"
import { updateName } from "../../api/ProfileApi.jsx"

// firstname/lastname приходят из ProfilePage (текущие значения пользователя),
// onSaved — callback, чтобы обновить состояние пользователя в родителе после успеха,
// onNotify — callback для показа единого уведомления на странице (success/error).
const ProfileNameForm = ({ firstname, lastname, onSaved, onNotify }) => {
    const [values, setValues] = useState({ firstname, lastname })
    const [isSaving, setIsSaving] = useState(false)

    const handleChange = (e) => {
        const { name, value } = e.target

        // Та же валидация "только буквы", что и в SignUpForm — для единообразия.
        const onlyLettersRegex = /^[a-zA-Zа-яА-ЯёЁ\s-]*$/
        if (!onlyLettersRegex.test(value)) return

        setValues(prev => ({ ...prev, [name]: value }))
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (!values.firstname.trim() || !values.lastname.trim()) {
            onNotify({ type: "warning", text: "Имя и фамилия не могут быть пустыми." })
            return
        }

        setIsSaving(true)
        try {
            const data = await updateName(values)
            onNotify({ type: "success", text: data.message || "Имя обновлено." })
            onSaved(values) // Прокидываем новые значения наверх, в состояние ProfilePage
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

            <button className="submitBtn" type="submit" disabled={isSaving}>
                {isSaving ? "Saving..." : "Save"}
            </button>
        </form>
    )
}

export default ProfileNameForm
