import { Navigate, Route, Routes } from 'react-router-dom'
import AppLayout from './components/layout/AppLayout.jsx'
import SignInPage from './pages/SignInPage.jsx'
import SignUpPage from './pages/SignUpPage.jsx'
import DiaryPage from './pages/DiaryPage.jsx'
import ProfilePage from './pages/ProfilePage.jsx'
import StubPage from './pages/StubPage.jsx'
import AdminPage from './pages/AdminPage.jsx'

function App() {
    return (
        <Routes>
            <Route path="/sign-in" element={<SignInPage />} />
            <Route path="/sign-up" element={<SignUpPage />} />

            <Route element={<AppLayout />}>
                <Route path="/diary" element={<DiaryPage />} />
                <Route path="/plan" element={<StubPage title="My plan" />} />
                <Route path="/products" element={<StubPage title="Products" />} />
                <Route path="/statistics" element={<StubPage title="Statistics" />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/admin" element={<AdminPage />} />
            </Route>

            <Route path="*" element={<Navigate to="/sign-in" replace />} />
        </Routes>
    )
}

export default App
