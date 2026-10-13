import { useEffect, useState } from 'react'
import { getCurrentUser, updateEmail, updateName, updatePassword, updatePhone } from '../api/profileApi.js'
import { useNotice } from '../hooks/useNotice.js'
import Notification from '../components/Notification.jsx'
import InputField from '../components/InputField.jsx'
import '../css/pages/ProfilePage.css'

const MIN_PASSWORD_LENGTH = 8

const toForm = (user) => ({
    firstname: user.firstname ?? '',
    lastname: user.lastname ?? '',
    email: user.email ?? '',
    phoneNumber: user.phoneNumber ?? '',
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
})

function validate(form) {
    if (!form.firstname.trim() || !form.lastname.trim()) return 'First and last name are required.'
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) return 'Enter a valid email.'
    if (form.phoneNumber.replace(/\D/g, '').length < 5) return 'Enter a valid phone number.'

    const isChangingPassword = form.currentPassword || form.newPassword || form.confirmPassword
    if (isChangingPassword) {
        if (!form.currentPassword) return 'Enter your current password.'
        if (form.newPassword.length < MIN_PASSWORD_LENGTH) {
            return `New password must be at least ${MIN_PASSWORD_LENGTH} characters long.`
        }
        if (form.newPassword !== form.confirmPassword) return 'New passwords do not match.'
    }

    return ''
}

function ProfilePage() {
    const [user, setUser] = useState(null)
    const [form, setForm] = useState(null)
    const [loadError, setLoadError] = useState('')
    const [isEditing, setIsEditing] = useState(false)
    const [isSaving, setIsSaving] = useState(false)
    const [notice, showNotice] = useNotice()

    useEffect(() => {
        document.title = 'Profile'
        getCurrentUser()
            .then((data) => {
                setUser(data)
                setForm(toForm(data))
            })
            .catch((error) => setLoadError(error.message))
    }, [])

    const handleChange = (e) => {
        const { name, value } = e.target

        if ((name === 'firstname' || name === 'lastname') && !/^[a-zA-Zа-яА-ЯёЁ\s-]*$/.test(value)) return
        if (name === 'phoneNumber' && !/^\+?\d*$/.test(value)) return

        setForm((prev) => ({ ...prev, [name]: value }))
    }

    const handleEditClick = () => {
        if (isEditing) setForm(toForm(user))
        setIsEditing(!isEditing)
    }

    const handleSave = async (e) => {
        e.preventDefault()

        const error = validate(form)
        if (error) {
            showNotice('warning', error)
            return
        }

        const requests = []
        if (form.firstname !== user.firstname || form.lastname !== user.lastname) {
            requests.push(updateName(form.firstname, form.lastname))
        }
        if (form.email !== user.email) requests.push(updateEmail(form.email))
        if (form.phoneNumber !== user.phoneNumber) requests.push(updatePhone(form.phoneNumber))
        if (form.newPassword) requests.push(updatePassword(form.currentPassword, form.newPassword))

        if (requests.length === 0) {
            setIsEditing(false)
            return
        }

        setIsSaving(true)
        try {
            await Promise.all(requests)
            const updatedUser = {
                ...user,
                firstname: form.firstname,
                lastname: form.lastname,
                email: form.email,
                phoneNumber: form.phoneNumber,
            }
            setUser(updatedUser)
            setForm(toForm(updatedUser))
            setIsEditing(false)
            showNotice('success', 'Profile updated.')
        } catch (error) {
            showNotice('error', error.message)
        } finally {
            setIsSaving(false)
        }
    }

    if (loadError) {
        return (
            <div className="card profileCard">
                <h1 className="cardTitle">Profile</h1>
                <p className="profileMessage">{loadError}</p>
            </div>
        )
    }

    if (!form) {
        return <p className="profileMessage">Loading profile...</p>
    }

    return (
        <form className="card profileCard" onSubmit={handleSave}>
            <Notification notice={notice} />
            <h1 className="cardTitle">Profile</h1>

            <fieldset className="profileFields" disabled={!isEditing || isSaving}>
                <div className="profileRow">
                    <InputField label="First Name" name="firstname" value={form.firstname} onChange={handleChange} required />
                    <InputField label="Last Name" name="lastname" value={form.lastname} onChange={handleChange} required />
                </div>

                <InputField label="Email" type="email" name="email" value={form.email} onChange={handleChange} required />
                <InputField label="Phone" type="tel" name="phoneNumber" value={form.phoneNumber} onChange={handleChange} required />

                {isEditing && (
                    <>
                        <hr className="profileDivider" />
                        <InputField label="Current Password" type="password" name="currentPassword" value={form.currentPassword} onChange={handleChange} />
                        <div className="profileRow">
                            <InputField label="New Password" type="password" name="newPassword" value={form.newPassword} onChange={handleChange} />
                            <InputField label="Confirm Password" type="password" name="confirmPassword" value={form.confirmPassword} onChange={handleChange} />
                        </div>
                    </>
                )}
            </fieldset>

            <div className="profileActions">
                <button className="btn" type="submit" disabled={!isEditing || isSaving}>
                    {isSaving ? 'Saving...' : 'Save'}
                </button>
                <button className="btn btnSecondary" type="button" onClick={handleEditClick} disabled={isSaving}>
                    {isEditing ? 'Cancel' : 'Edit'}
                </button>
            </div>
        </form>
    )
}

export default ProfilePage
