import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { signIn } from '../api/authApi.js'
import { useNotice } from '../hooks/useNotice.js'
import Notification from '../components/Notification.jsx'
import SignInForm from '../components/SignInForm.jsx'
import '../css/pages/AuthPage.css'

function SignInPage() {
    const navigate = useNavigate()
    const [notice, showNotice] = useNotice()
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
            showNotice('error', error.message)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <main className="authPage">
            <Notification notice={notice} />
            <div className="authSide" />
            <div className="authMain">
                <SignInForm onSubmit={handleSubmit} isLoading={isLoading} />
            </div>
        </main>
    )
}

export default SignInPage
