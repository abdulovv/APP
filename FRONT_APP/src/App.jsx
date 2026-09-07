import { Routes, Route, Navigate } from 'react-router-dom'
import SignInPage from './pages/SignInPage.jsx'
import SignUpPage from './pages/SignUpPage.jsx' 

function App() {
  return (
    <Routes>
      {/* 1. Главная страница (авторизация) */}
      <Route path="/sign-in" element={<SignInPage />} />

      {/* 2. Страница регистрации (добавьте, когда создадите компонент) */}
      <Route path="/sign-up" element={<SignUpPage />} />

      {/* 3. Авто-перенаправление (Navigate) */} 
      {/* Если пользователь зашел просто на "/", его автоматически перекинет на "/login" */}
      <Route path="*" element={<Navigate to="/sign-in" replace />} />
    </Routes>
  )
}

export default App
