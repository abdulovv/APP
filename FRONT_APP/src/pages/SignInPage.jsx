import { useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Notification from '../components/Notification.jsx'
import SignInForm from '../components/SignInForm.jsx'
import "../css/pages/SignInPage.css"


function SignInPage() {
  useEffect(() => {
    document.title = 'Sign In'
  }, [])

  const navigate = useNavigate()
  const [notice, setNotice] = useState(null)
  const [isLoading, setIsLoading] = useState(false)  
  async function handleSubmit(event) {
    event.preventDefault()

    if (isLoading) return  

    setNotice(null)
    setIsLoading(true)  

    const form = new FormData(event.target)

    const response = await fetch('http://localhost:8080/api/auth/sign-in', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        uniqueField: form.get('uniqueField'),
        password: form.get('password'),
      }),
    })

    const data = await response.json()
    setIsLoading(false)

    if (response.status === 200) {
      setNotice({ 
        type: 'success',
        text: data.message,
      })
      setTimeout(() => {
        navigate('/home', { replace: true })
      }, 2000)
    } else {
      setNotice({  
        type: 'error',
        text: data.message,
      })
    }
  }

  return (
    <main className="sign-in-page">
      {notice && (
        <Notification 
          type={notice.type} 
          text={notice.text} 
          onClose={() => setNotice(null)}
        />
      )}
      <div className="left-half">
        
      </div>

      <div className="right-half">
        
        <SignInForm onSubmit={handleSubmit} isLoading={isLoading} />
      </div>
    </main>
  )
}

export default SignInPage