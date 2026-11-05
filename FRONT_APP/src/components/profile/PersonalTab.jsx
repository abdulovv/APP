import { useState } from 'react'
import { updateEmail, updateName } from '../../api/profileApi.js'

const MONTHS = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December',
]

// '2000-05-20' -> '20-May-2000'
const formatDate = (isoDate) => {
    if (!isoDate) return '—'
    const [year, month, day] = isoDate.split('-')
    return `${day}-${MONTHS[Number(month) - 1]}-${year}`
}

const toForm = (user) => ({
    firstname: user.firstname ?? '',
    lastname: user.lastname ?? '',
    email: user.email ?? '',
    phoneNumber: user.phoneNumber ?? '',
    countryName: user.countryName ?? '',
})

// Вкладка «Personal data»: имя, email (редактируются), телефон, страна, дата рождения (только просмотр)
function PersonalTab({ user, setUser, showNotification }) {
    const [form, setForm] = useState(() => toForm(user))
    const [editing, setEditing] = useState(null)

    const handleChange = (e) => {
        const { name, value } = e.target
        if ((name === 'firstname' || name === 'lastname') && !/^[a-zA-Zа-яА-ЯёЁ\s-]*$/.test(value)) return
        setForm((prev) => ({ ...prev, [name]: value }))
    }

    const handleCancel = () => {
        setForm(toForm(user))
        setEditing(null)
    }

    const updateUser = () => {
        setUser({
            ...user,
            firstname: form.firstname,
            lastname: form.lastname,
            email: form.email,
            phoneNumber: form.phoneNumber,
            countryName: form.countryName,
        })
    }

    const saveName = async () => {
        try {
            await updateName(form.firstname, form.lastname)
            setEditing(null)
            showNotification('success', 'Name updated.')
            updateUser()
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const saveEmail = async () => {
        try {
            await updateEmail(form.email)
            setEditing(null)
            showNotification('success', 'Email updated.')
            updateUser()
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    return (
        <section className="profileSection">
            {editing === 'name' ? (
                <div className="profileRowEdit">
                    <div className="profileRowEditFields">
                        <input
                            className="fieldInput"
                            name="firstname"
                            value={form.firstname}
                            onChange={handleChange}
                            aria-label="First name"
                        />
                        <input
                            className="fieldInput"
                            name="lastname"
                            value={form.lastname}
                            onChange={handleChange}
                            aria-label="Last name"
                        />
                    </div>
                    <div className="profileFormActions">
                        <button type="button" className="btn btnSmall" onClick={saveName}>Save</button>
                        <button type="button" className="btn btnSecondary btnSmall" onClick={handleCancel}>Cancel</button>
                    </div>
                </div>
            ) : (
                <div className="profileRow">
                    <span className="profileRowLabel">Name</span>
                    <span className="profileRowValue">{user.firstname} {user.lastname}</span>
                    <button className="linkBtn" type="button" onClick={() => setEditing('name')}>Change</button>
                </div>
            )}

            {editing === 'email' ? (
                <div className="profileRowEdit">
                    <div className="profileRowEditFields">
                        <input
                            className="fieldInput"
                            name="email"
                            type="email"
                            value={form.email}
                            onChange={handleChange}
                            aria-label="Email"
                        />
                    </div>
                    <div className="profileFormActions">
                        <button type="button" className="btn btnSmall" onClick={saveEmail}>Save</button>
                        <button type="button" className="btn btnSecondary btnSmall" onClick={handleCancel}>Cancel</button>
                    </div>
                </div>
            ) : (
                <div className="profileRow">
                    <span className="profileRowLabel">Email</span>
                    <span className="profileRowValue">{user.email}</span>
                    <button className="linkBtn" type="button" onClick={() => setEditing('email')}>Change</button>
                </div>
            )}

            <div className="profileRow">
                <span className="profileRowLabel">Phone</span>
                <span className="profileRowValue">{user.phoneNumber}</span>
            </div>

            {/* Country: кнопка Change появится, когда будет endpoint на бэкенде */}
            <div className="profileRow">
                <span className="profileRowLabel">Country</span>
                <span className="profileRowValue">{user.countryName}</span>
            </div>

            <div className="profileRow">
                <span className="profileRowLabel">Date of birth</span>
                <span className="profileRowValue">{formatDate(user.birthDate)}</span>
            </div>
        </section>
    )
}

export default PersonalTab
