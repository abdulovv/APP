import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { signIn } from '../api/authApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/common/Notification.jsx'
import SignInForm from '../components/auth/SignInForm.jsx'
import '../css/pages/AuthPage.css'

function SignInPage() {
    const navigate = useNavigate()
    const [notification, showNotification] = useNotification()
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        document.title = 'Sign In'
    }, [])

    const handleSubmit = async (credentials) => {
        setIsLoading(true)
        try {
            await signIn(credentials)
            navigate('/diary', { replace: true })
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <main className="authPage">
            <Notification notification={notification} />
            <div className="authSide" />
            <div className="authMain">
                <SignInForm onSubmit={handleSubmit} isLoading={isLoading} />
            </div>
        </main>
    )
}

export default SignInPage
