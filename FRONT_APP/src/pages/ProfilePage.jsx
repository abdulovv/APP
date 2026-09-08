import { useEffect, useState } from "react"
import Notification from "../components/Notification.jsx"
import ProfileNameForm from "../components/profile/ProfileNameForm.jsx"
import ProfileEmailForm from "../components/profile/ProfileEmailForm.jsx"
import ProfilePhoneForm from "../components/profile/ProfilePhoneForm.jsx"
import ProfilePasswordForm from "../components/profile/ProfilePasswordForm.jsx"
import { fetchCurrentUser } from "../api/ProfileApi.jsx"
import "../css/pages/ProfilePage.css"
import "../css/components/profile/ProfileForm.css"

function ProfilePage() {
    useEffect(() => {
        document.title = "Profile"
    }, [])

    // Данные пользователя — единый источник правды для всех форм ниже.
    // Каждая форма получает свой кусочек (name/email/phone) и сообщает
    // об успешном сохранении через onSaved, чтобы этот стейт обновился.
    const [user, setUser] = useState(null)
    const [isLoading, setIsLoading] = useState(true)
    const [notice, setNotice] = useState(null)

    useEffect(() => {
        const loadUser = async () => {
            setIsLoading(true)
            try {
                const data = await fetchCurrentUser()
                setUser(data)
            } catch (error) {
                setNotice({ type: "error", text: error.message })
            } finally {
                setIsLoading(false)
            }
        }
        loadUser()
    }, [])

    if (isLoading) {
        return (
            <main className="profile-page">
                <p className="profileLoading">Loading profile...</p>
            </main>
        )
    }

    // Если профиль не загрузился (например, сессия истекла) — форм не показываем,
    // просто выводим уведомление, оно уже отрисовано ниже через notice.
    if (!user) {
        return (
            <main className="profile-page">
                {notice && (
                    <Notification
                        type={notice.type}
                        text={notice.text}
                        onClose={() => setNotice(null)}
                    />
                )}
            </main>
        )
    }

    return (
        <main className="profile-page">
            {notice && (
                <Notification
                    type={notice.type}
                    text={notice.text}
                    onClose={() => setNotice(null)}
                />
            )}

            <div className="profileCard">
                <p className="profileTitle">Profile</p>

                <ProfileNameForm
                    firstname={user.firstname}
                    lastname={user.lastname}
                    onSaved={({ firstname, lastname }) =>
                        setUser(prev => ({ ...prev, firstname, lastname }))
                    }
                    onNotify={setNotice}
                />

                <hr className="profileDivider" />

                <ProfileEmailForm
                    email={user.email}
                    onSaved={(email) => setUser(prev => ({ ...prev, email }))}
                    onNotify={setNotice}
                />

                <hr className="profileDivider" />

                <ProfilePhoneForm
                    phoneNumber={user.phoneNumber}
                    onSaved={(phoneNumber) => setUser(prev => ({ ...prev, phoneNumber }))}
                    onNotify={setNotice}
                />

                <hr className="profileDivider" />

                <ProfilePasswordForm onNotify={setNotice} />
            </div>
        </main>
    )
}

export default ProfilePage
