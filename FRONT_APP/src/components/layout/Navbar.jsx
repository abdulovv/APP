import { NavLink, useNavigate } from 'react-router-dom'
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

const ADMIN_LINK = { to: '/admin', label: 'Admin panel' }

function Navbar({ user }) {
    const navigate = useNavigate()
    const [theme, toggleTheme] = useTheme()

    const isAdmin = user?.role === 'ADMIN'
    const links = isAdmin ? [...LINKS, ADMIN_LINK] : LINKS

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

            {links.map((link) => (
                <NavLink key={link.to} to={link.to} className="navbarLink">
                    {link.label}
                </NavLink>
            ))}

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
