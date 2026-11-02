import { useState } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import '../../css/components/Navbar.css'
import { signOut } from '../../api/authApi'
import { useTheme } from '../../hooks/useTheme.js'

const MAIN_LINK = { to: '/diary', label: 'Diary' }

const LINKS = [
    { to: '/plan', label: 'My plan' },
    { to: '/products', label: 'Products' },
    { to: '/statistics', label: 'Statistics' },
    { to: '/profile', label: 'Profile' },
]

const ADMIN_LINKS = [
    { to: '/admin/products', label: 'Products' },
    { to: '/admin/pages', label: 'Pages' },
]

function Navbar({ user }) {
    const navigate = useNavigate()
    const [theme, toggleTheme] = useTheme()

    const location = useLocation()
    const isAdmin = user?.role === 'ADMIN'
    const isAdminRoute = location.pathname.startsWith('/admin')
    // Если открыта админская страница — список сразу развёрнут
    const [isAdminOpen, setIsAdminOpen] = useState(isAdminRoute)

    const handleSignOut = async () => {
        try {
            await signOut()
        } finally {
            navigate('/sign-in')
        }
    }

    return (
        <nav className="navbar">
            <span className="navbarLogo">Diary</span>

            <hr className="navbarDivider"/>

            <NavLink to={MAIN_LINK.to} className="navbarLink">
                {MAIN_LINK.label}
            </NavLink>

            {LINKS.map((link) => (
                <NavLink key={link.to} to={link.to} className="navbarLink">
                    {link.label}
                </NavLink>
            ))}

            {isAdmin && (
                <div className="navbarGroup">
                    <button
                        type="button"
                        className={isAdminRoute ? 'navbarLink navbarGroupToggle current' : 'navbarLink navbarGroupToggle'}
                        onClick={() => setIsAdminOpen(!isAdminOpen)}
                    >
                        Admin panel
                        <span className={isAdminOpen ? 'navbarChevron open' : 'navbarChevron'}>▾</span>
                    </button>

                    {isAdminOpen && (
                        <div className="navbarSubLinks">
                            {ADMIN_LINKS.map((link) => (
                                <NavLink key={link.to} to={link.to} className="navbarLink navbarSubLink">
                                    {link.label}
                                </NavLink>
                            ))}
                        </div>
                    )}
                </div>
            )}

            <div className="navbarBottom">
                <button type="button" className="navbarLink navbarMuted" onClick={toggleTheme}>
                    {theme === 'dark' ? 'Light theme' : 'Dark theme'}
                </button>

                <button type="button" className="navbarLink navbarMuted" onClick={handleSignOut}>
                    Sign out
                </button>

                {user && (
                    <div className="navbarUser">
                        <span className="navbarUserName">{user.firstname}</span>
                        <span className={user.premium ? 'navbarUserPlan premium' : 'navbarUserPlan'}>
                            {' · '}
                            {user.premium ? 'Premium' : 'Free'}
                        </span>
                    </div>
                )}
            </div>
        </nav>
    )
}

export default Navbar
