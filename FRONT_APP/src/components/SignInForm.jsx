import { Link } from 'react-router-dom'
import InputField from './InputField.jsx'

function SignInForm({ onSubmit, isLoading }) {
    const handleSubmit = (e) => {
        e.preventDefault()
        const form = new FormData(e.target)
        onSubmit({
            uniqueField: form.get('uniqueField'),
            password: form.get('password'),
        })
    }

    return (
        <form className="card authForm" onSubmit={handleSubmit}>
            <h1 className="cardTitle">Welcome!</h1>

            <InputField label="Email/Phone" name="uniqueField" placeholder="Enter your email/phone" required />
            <InputField label="Password" type="password" name="password" placeholder="Enter your password" required />

            <div className="authLinks">
                <Link to="/sign-up">Sign Up</Link>
            </div>

            <button className="btn" type="submit" disabled={isLoading}>
                {isLoading ? 'Signing In...' : 'Sign In'}
            </button>
        </form>
    )
}

export default SignInForm
