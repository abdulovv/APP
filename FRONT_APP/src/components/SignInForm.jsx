import InputField from "./InputField"
import { Link } from "react-router-dom" 
import "../css/components/SignInForm.css"

const SignInForm = ({ onSubmit, isLoading }) => {
    return (
        <form className="signInForm" onSubmit={onSubmit}>
            <p>Welcome!</p>

            <InputField
                label="Email/Phone"
                type="text"
                name="uniqueField"
                placeholder="Enter your email/phone"
            />

            <InputField
                label="Password"
                type="password"
                name="password"
                placeholder="Enter your password"
            />

            <div className="formLinks">
                <Link to="/sign-up">Sign Up</Link>
                <a href="https://google.com" target="_blank" rel="noreferrer">Forgot password ?</a>
            </div>
           

            <button   
                className="submitBtn"
                type="submit"
                disabled={isLoading}
            >
                {isLoading ? "Signing In..." : "Sign In"}
            </button>
        </form>
    )
}

export default SignInForm