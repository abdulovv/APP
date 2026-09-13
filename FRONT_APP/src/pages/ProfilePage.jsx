import { useEffect, useRef, useState } from "react"
import Notification from "../components/Notification.jsx"
import ProfileNameForm from "../components/profile/ProfileNameForm.jsx"
import ProfileEmailForm from "../components/profile/ProfileEmailForm.jsx"
import ProfilePhoneForm from "../components/profile/ProfilePhoneForm.jsx"
import ProfilePasswordForm from "../components/profile/ProfilePasswordForm.jsx"
// ВРЕМЕННО: тестовая заглушка дневника, см. DiaryPlaceholder.jsx — как убрать, написано там.
import DiaryPlaceholder from "../components/diary/DiaryPlaceholder.jsx"
import { fetchCurrentUser } from "../api/ProfileApi.jsx"
import "../css/pages/ProfilePage.css"
import "../css/components/profile/ProfileForm.css"
import "../css/components/diary/Diary.css"

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
    const [isSaving, setIsSaving] = useState(false)

    // Формы больше не сохраняют себя сами — у каждой есть save() через ref,
    // и общая кнопка ниже дергает их все разом.
    const nameFormRef = useRef(null)
    const emailFormRef = useRef(null)
    const phoneFormRef = useRef(null)
    const passwordFormRef = useRef(null)

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

    const handleSaveAll = async () => {
        setIsSaving(true)
        try {
            const results = await Promise.allSettled([
                nameFormRef.current?.save(),
                emailFormRef.current?.save(),
                phoneFormRef.current?.save(),
                passwordFormRef.current?.save(),
            ])

            // Уведомление о конкретной ошибке/предупреждении уже показала
            // соответствующая форма через onNotify — тут просто не даём
            // общему success затереть его.
            const hasRejected = results.some(r => r.status === "rejected")
            if (!hasRejected) {
                setNotice({ type: "success", text: "Профиль обновлён." })
            }
        } finally {
            setIsSaving(false)
        }
    }

    if (isLoading) {
        return (
            <main className="profile-page">
                <div className="left-half">
                    <DiaryPlaceholder />
                </div>
                <div className="right-half">
                    <p className="profileLoading">Loading profile...</p>
                </div>
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
                <div className="left-half">
                    <DiaryPlaceholder />
                </div>
                <div className="right-half" />
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

            <div className="left-half">
                <DiaryPlaceholder />
            </div>

            <div className="right-half">
                <div className="profileCard">
                    <p className="profileTitle">Profile</p>

                    <ProfileNameForm
                        ref={nameFormRef}
                        firstname={user.firstname}
                        lastname={user.lastname}
                        onSaved={({ firstname, lastname }) =>
                            setUser(prev => ({ ...prev, firstname, lastname }))
                        }
                        onNotify={setNotice}
                    />

                    <hr className="profileDivider" />

                    <ProfileEmailForm
                        ref={emailFormRef}
                        email={user.email}
                        onSaved={(email) => setUser(prev => ({ ...prev, email }))}
                        onNotify={setNotice}
                    />

                    <hr className="profileDivider" />

                    <ProfilePhoneForm
                        ref={phoneFormRef}
                        phoneNumber={user.phoneNumber}
                        onSaved={(phoneNumber) => setUser(prev => ({ ...prev, phoneNumber }))}
                        onNotify={setNotice}
                    />

                    <hr className="profileDivider" />

                    <ProfilePasswordForm ref={passwordFormRef} onNotify={setNotice} />

                    <button
                        type="button"
                        className="profileSaveBtn"
                        onClick={handleSaveAll}
                        disabled={isSaving}
                    >
                        {isSaving ? "Saving..." : "Save"}
                    </button>
                </div>
            </div>
        </main>
    )
}

export default ProfilePage
