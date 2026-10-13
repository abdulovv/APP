import { Outlet } from 'react-router-dom'
import Navbar from './Navbar.jsx'
import '../../css/components/AppLayout.css'

function AppLayout() {
    return (
        <div className="appLayout">
            <Navbar />
            <main className="appContent">
                <Outlet />
            </main>
        </div>
    )
}

export default AppLayout
