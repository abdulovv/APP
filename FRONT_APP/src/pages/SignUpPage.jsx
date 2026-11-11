import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { sendSignUpCode, signUp } from '../api/authApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/common/Notification.jsx'
import SignUpForm from '../components/auth/SignUpForm.jsx'
import '../css/pages/AuthPage.css'

function SignUpPage() {
    const navigate = useNavigate()
    const [notification, showNotification] = useNotification()
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        document.title = 'Sign Up'
    }, [])

    const handleSendCode = async (email) => {
        setIsLoading(true)
        try {
            await sendSignUpCode(email)
            showNotification('success', 'We sent a code to your email.')
            return true
        } catch (error) {
            showNotification('error', error.message)
            return false
        } finally {
            setIsLoading(false)
        }
    }

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
                    onSendCode={handleSendCode}
                    onError={(text) => showNotification('warning', text)}
                    isLoading={isLoading}
                />
            </div>
        </main>
    )
}

export default SignUpPage
