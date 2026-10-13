import { NavLink, useNavigate } from 'react-router-dom'
import '../../css/components/Navbar.css'
import { signOut } from '../../api/authApi'

const LINKS = [
    { to: '/diary', label: 'Diary' },
    { to: '/products', label: 'Products' },
    { to: '/statistics', label: 'Statistics' },
    { to: '/profile', label: 'Profile' },
]

function Navbar() {
    const navigate = useNavigate()
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

            {LINKS.map((link) => (
                <NavLink key={link.to} to={link.to} className="navbarLink">
                    {link.label}
                </NavLink>
            ))}

            <button type="button" className="navbarLink navbarSignOut" onClick={handleSignOut}>
                Sign out
            </button>
        </nav>
    )
}

export default Navbar
