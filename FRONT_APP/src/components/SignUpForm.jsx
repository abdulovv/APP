import { useState } from "react"
import { Link } from "react-router-dom" 
import InputField from "./InputField"
import CountrySelect from "./CountrySelect.jsx"
import "../css/components/SignUpForm.css"
import Notification from "./Notification.jsx"

const SignUpForm = ({ onSubmit }) => {
    const [step, setStep] = useState(1);
    const [error, setError] = useState("");
    const [currentCountryCode, setCurrentCountryCode] = useState("");
    const [formData, setFormData] = useState({
        firstname: "",
        lastname: "",
        birthdate: "",
        country: "",
        countryId: "",
        email: "",
        phone: "",
        password: "",
        confirmPassword: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;

        // 1. ВЫБОР СТРАНЫ
        if (name === "country") {
            try {
                const countryData = JSON.parse(value);
                setCurrentCountryCode(countryData.code); 
                setFormData(prev => ({
                    ...prev,
                    country: countryData.name,
                    countryId: countryData.id,
                    phone: countryData.code 
                }));
            } catch (error) {
                setCurrentCountryCode("");
                setFormData(prev => ({ ...prev, country: "", countryId: "", phone: "" }));
            }
            return;
        }

        // 2. В ИМЕНИ И ФАМИЛИИ — ТОЛЬКО БУКВЫ
        if (name === "firstname" || name === "lastname") {
            const onlyLettersRegex = /^[a-zA-Zа-яА-ЯёЁ\s-]*$/;
            if (!onlyLettersRegex.test(value)) return;
        }

        // 3. В ТЕЛЕФОНЕ — ЗАЩИТА КОДА И ТОЛЬКО ЦИФРЫ
        if (name === "phone") {
            if (currentCountryCode && !value.startsWith(currentCountryCode)) return;
            const userEnteredPart = value.slice(currentCountryCode.length);
            const onlyDigitsRegex = /^\d*$/;
            if (!onlyDigitsRegex.test(userEnteredPart)) return;
        }

        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const nextStep = (e) => {
        e.preventDefault(); 
        if (step === 1) {
            if (!formData.birthdate) {
                setError("Please enter your Date of Birth.");
                return;
            }
            
            const birthDate = new Date(formData.birthdate);
            const today = new Date();
            let age = today.getFullYear() - birthDate.getFullYear();
            const monthDiff = today.getMonth() - birthDate.getMonth();
            if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }

            if (age < 12) {
                setError("You must be at least 12 years old to register.");
                return;
            }

            if (age > 100) {
                setError("Age cannot be more than 100 years.");
                return;
            }
        }
        setStep(prev => prev + 1);
    };

    const prevStep = (e) => {
        e.preventDefault();
        setStep(prev => prev - 1);
    };
    
    const handleSubmit = (e) => {
        e.preventDefault();
        setError("");

        if (formData.password !== formData.confirmPassword) {
            setError("Passwords do not match!");
            return;
        }
        
        if (onSubmit) {
            onSubmit(formData); 
        }
    };

    // Ограничения для календаря на основе текущего 2026 года
    const currentYear = new Date().getFullYear();
    const maxDate = `${currentYear - 12}-12-31`; 
    const minDate = `${currentYear - 100}-01-01`;

    return (
        <form className="signUpForm" onSubmit={step === 3 ? handleSubmit : nextStep}>
            
            {/* Скрытый инпут для корректной передачи ID страны */}
            <input type="hidden" name="countryId" value={formData.countryId} />
            
            <div className="stepIndicator">Step {step} of 3</div>

            {error && (
                <Notification 
                    type="warning" 
                    text={error} 
                    onClose={() => setError("")} 
                />
            )}  

            {step === 1 && (
                <>
                    <InputField label="First Name" type="text" name="firstname" value={formData.firstname} onChange={handleChange} placeholder="Enter your first name" required />
                    <InputField label="Last Name" type="text" name="lastname" value={formData.lastname} onChange={handleChange} placeholder="Enter your last name" required />
                    <InputField label="Date of Birth" type="date" name="birthdate" value={formData.birthdate} onChange={handleChange} min={minDate} max={maxDate} required />
                    <div className="formLinks">
                        <Link to="/sign-in">Sign In</Link>
                        <a href="https://google.com" target="_blank" rel="noreferrer">Forgot password ?</a>
                    </div>
                    <button className="submitBtn" type="submit">Next</button>
                </>
            )}

            {step === 2 && (
                <>
                    <CountrySelect value={formData.country} onChange={handleChange} />
                    <InputField label="Email" type="email" name="email" value={formData.email} onChange={handleChange} placeholder="example@gmail.com" required />
                    <InputField label="Phone" type="tel" name="phone" value={formData.phone} onChange={handleChange} placeholder="Enter your phone" required />
                    <div className="formLinks">
                        <Link to="/sign-in">Sign In</Link>
                        <a href="https://google.com" target="_blank" rel="noreferrer">Forgot password ?</a>
                    </div>
                    <div className="formButtonsLine">
                        <button className="backBtn" type="button" onClick={prevStep}>Back</button>
                        <button className="submitBtn" type="submit">Next</button>
                    </div>
                </>
            )}

            {step === 3 && (
                <>
                    <InputField label="Password" type="password" name="password" value={formData.password} onChange={handleChange} placeholder="Enter your password" required />
                    <InputField label="Confirm Password" type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} placeholder="Confirm your password" required />
                    <div className="formLinks">
                        <Link to="/sign-in">Sign In</Link>
                        <a href="https://google.com" target="_blank" rel="noreferrer">Forgot password ?</a>
                    </div>
                    <div className="formButtonsLine">
                        <button className="backBtn" type="button" onClick={prevStep}>Back</button>
                        <button className="submitBtn" type="submit">Register</button>
                    </div>
                </>
            )}
        </form>
    );
};

export default SignUpForm;