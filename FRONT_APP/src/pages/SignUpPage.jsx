import { useState, useEffect } from 'react'
import SignUpForm from '../components/SignUpForm.jsx'
import { useNavigate } from 'react-router-dom'

function SignUpPage() {
  useEffect(() => {
    document.title = 'Sign Up'
  }, [])

  const [notice, setNotice] = useState(null)
  const navigate = useNavigate()

  async function handleSubmit(formData) {
    try {
      const response = await fetch('http://localhost:8081/api/auth/sign-up', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        // Просто превращаем полученный объект в JSON-строку
        body: JSON.stringify({
          firstname: formData.firstname,
          lastname: formData.lastname,
          email: formData.email,
          phoneNumber: formData.phone,
          password: formData.password,
          birthDate: formData.birthdate,
          countryId: Number(formData.countryId) // Принудительно приводим к числу для Java
        }),
      })

      const data = await response.json()

      if (response.ok || data.status === '200 OK') {
        setNotice({ 
          type: 'success',
          text: data.message || "Registration successful!",
        })
        setTimeout(() => {
          navigate('/profile', { replace: true })
        }, 2000)
      } else {
        setNotice({  
          type: 'error',
          text: data.message || "Registration failed",
        })
      }
    } catch (error) {
      console.error("Network error:", error);
      setNotice({ type: 'error', text: "Server is unreachable" });
    }
  }

  return (
    <main className="sign-in-page">
      <div className="left-half"></div>
      <div className="right-half">
        {/* Выводим уведомление, если оно есть */}
        {notice && <div className={`notice ${notice.type}`}>{notice.text}</div>}
        <SignUpForm onSubmit={handleSubmit} />
      </div>
    </main>
  )
}

export default SignUpPage