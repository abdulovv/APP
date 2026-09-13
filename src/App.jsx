import { Routes, Route, Navigate } from 'react-router-dom'
import SignInPage from './pages/SignInPage.jsx'
import SignUpPage from './pages/SignUpPage.jsx' 
import ProfilePage from './pages/ProfilePage.jsx'

function App() {
  return (
    <Routes>
      <Route path="/sign-in" element={<SignInPage />} />
      <Route path="/sign-up" element={<SignUpPage />} />
      {/* Раньше SignIn/SignUp делали navigate('/home'), а роута /home не было —
          пользователя мгновенно кидало обратно на /sign-in. Теперь ведём на /profile. */}
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="*" element={<Navigate to="/sign-in" replace />} />
    </Routes>
  )
}

export default App
