import { useState } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import '../../css/components/Navbar.css'
import { signOut } from '../../api/authApi'
import { useTheme } from '../../hooks/useTheme.js'

// Иконки — простые линии (24x24), цвет берут из текста через currentColor
const ICONS = {
    diary: (
        <>
            <path d="M12 7v14" />
            <path d="M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z" />
        </>
    ),
    plan: (
        <>
            <circle cx="12" cy="12" r="10" />
            <circle cx="12" cy="12" r="6" />
            <circle cx="12" cy="12" r="2" />
        </>
    ),
    products: (
        <>
            <path d="M3 2v7c0 1.1.9 2 2 2h4a2 2 0 0 0 2-2V2" />
            <path d="M7 2v20" />
            <path d="M21 15V2a5 5 0 0 0-5 5v6c0 1.1.9 2 2 2h3Zm0 0v7" />
        </>
    ),
    statistics: (
        <>
            <path d="M3 3v16a2 2 0 0 0 2 2h16" />
            <path d="M18 17V9" />
            <path d="M13 17V5" />
            <path d="M8 17v-3" />
        </>
    ),
    profile: (
        <>
            <circle cx="12" cy="8" r="4" />
            <path d="M4 21a8 8 0 0 1 16 0" />
        </>
    ),
    admin: (
        <path d="M20 13c0 5-3.5 7.5-7.66 8.95a1 1 0 0 1-.67-.01C7.5 20.5 4 18 4 13V6a1 1 0 0 1 1-1c2 0 4.5-1.2 6.24-2.72a1.17 1.17 0 0 1 1.52 0C14.51 3.81 17 5 19 5a1 1 0 0 1 1 1z" />
    ),
    moon: <path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z" />,
    sun: (
        <>
            <circle cx="12" cy="12" r="4" />
            <path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41" />
        </>
    ),
    signOut: (
        <>
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
            <path d="m16 17 5-5-5-5" />
            <path d="M21 12H9" />
        </>
    ),
    chevron: <path d="m6 9 6 6 6-6" />,
}

function Icon({ name, size = 18, className = 'navbarIcon' }) {
    return (
        <svg
            className={className}
            width={size}
            height={size}
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.6"
            strokeLinecap="round"
            strokeLinejoin="round"
            aria-hidden="true"
        >
            {ICONS[name]}
        </svg>
    )
}

const LINKS = [
    { to: '/diary', label: 'Diary', icon: 'diary' },
    { to: '/plan', label: 'Subscription', icon: 'plan' },
    { to: '/products', label: 'Products', icon: 'products' },
    { to: '/statistics', label: 'Statistics', icon: 'statistics' },
    { to: '/profile', label: 'Profile', icon: 'profile' },
]

const ADMIN_LINKS = [
    // { to: '/admin/users', label: 'Users' },  // раскомментируй, когда появится страница и маршрут в App.jsx
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
            <div className="navbarLogo">
                <img className="navbarLogoMark" src="/logo-mark.svg" alt="" />
                Diary
            </div>

            {LINKS.map((link) => (
                <NavLink key={link.to} to={link.to} className="navbarLink">
                    <Icon name={link.icon} />
                    {link.label}
                </NavLink>
            ))}

            {isAdmin && (
                <>
                    <hr className="navbarDivider" />

                    <div className="navbarGroup">
                        <button
                            type="button"
                            className={isAdminRoute ? 'navbarLink navbarGroupToggle current' : 'navbarLink navbarGroupToggle'}
                            aria-expanded={isAdminOpen}
                            onClick={() => setIsAdminOpen(!isAdminOpen)}
                        >
                            <Icon name="admin" />
                            <span className="navbarGroupLabel">Admin panel</span>
                            <Icon name="chevron" size={16} className={isAdminOpen ? 'navbarChevron open' : 'navbarChevron'} />
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
                </>
            )}

            <div className="navbarBottom">
                <button type="button" className="navbarLink navbarMuted" onClick={toggleTheme}>
                    <Icon name={theme === 'dark' ? 'sun' : 'moon'} />
                    {theme === 'dark' ? 'Light theme' : 'Dark theme'}
                </button>

                {user && (
                    <div className="navbarUser">
                        <span className="navbarAvatar" aria-hidden="true">
                            {user.firstname?.charAt(0).toUpperCase()}
                        </span>
                        <span className="navbarUserInfo">
                            <span className="navbarUserName">{user.firstname}</span>
                            <span className={user.premium ? 'navbarUserPlan premium' : 'navbarUserPlan'}>
                                {' · '}
                                {user.premium ? 'Premium' : 'Free'}
                            </span>
                        </span>
                        <button type="button" className="navbarSignOut" onClick={handleSignOut} aria-label="Sign out">
                            <Icon name="signOut" />
                        </button>
                    </div>
                )}
            </div>
        </nav>
    )
}

export default Navbar
