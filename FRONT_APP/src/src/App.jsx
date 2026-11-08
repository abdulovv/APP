import { Navigate, Route, Routes } from 'react-router-dom'
import AppLayout from './components/layout/AppLayout.jsx'
import SignInPage from './pages/SignInPage.jsx'
import SignUpPage from './pages/SignUpPage.jsx'
import DiaryPage from './pages/DiaryPage.jsx'
import ProfilePage from './pages/ProfilePage.jsx'
import SubscriptionPage from './pages/SubscriptionPage.jsx'
import ComingSoonPage from './pages/ComingSoonPage.jsx'
import AdminProductsPage from './pages/AdminProductsPage.jsx'
import AdminPagesPage from './pages/AdminPagesPage.jsx'
import PageAvailabilityGuard from './components/guards/PageAvailabilityGuard.jsx'

function App() {
    return (
        <Routes>
            <Route path="/sign-in" element={<SignInPage />} />
            <Route path="/sign-up" element={<SignUpPage />} />

            <Route element={<AppLayout />}>
                <Route path="/diary" element={<DiaryPage />} />
                <Route path="/plan" element={<PageAvailabilityGuard pageKey="plan"><SubscriptionPage/></PageAvailabilityGuard>} />
                <Route path="/products" element={<PageAvailabilityGuard pageKey="products"><ComingSoonPage title="Products" /></PageAvailabilityGuard>} />
                <Route path="/statistics" element={<PageAvailabilityGuard pageKey="statistics"><ComingSoonPage title="Statistics" /></PageAvailabilityGuard>} />
                <Route path="/profile" element={<PageAvailabilityGuard pageKey="profile"><ProfilePage /></PageAvailabilityGuard>} />
                <Route path="/admin" element={<Navigate to="/admin/products" replace />} />
                <Route path="/admin/products" element={<AdminProductsPage />} />
                <Route path="/admin/pages" element={<AdminPagesPage />} />
            </Route>

            <Route path="*" element={<Navigate to="/sign-in" replace />} />
        </Routes>
    )
}

export default App
