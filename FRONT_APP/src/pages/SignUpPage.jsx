import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { signUp } from '../api/authApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/Notification.jsx'
import SignUpForm from '../components/SignUpForm.jsx'
import '../css/pages/AuthPage.css'

function SignUpPage() {
    const navigate = useNavigate()
    const [notification, showNotification] = useNotification()
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        document.title = 'Sign Up'
    }, [])

    const handleSubmit = async (user) => {
        setIsLoading(true)
        try {
            const data = await signUp(user)
            showNotification('success', data?.message || 'Registration successful!')
            setTimeout(() => navigate('/sign-in', { replace: true }), 1500)
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
                <SignUpForm
                    onSubmit={handleSubmit}
                    onError={(text) => showNotification('warning', text)}
                    isLoading={isLoading}
                />
            </div>
        </main>
    )
}

export default SignUpPage
