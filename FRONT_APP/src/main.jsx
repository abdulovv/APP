import './index.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import App from './App.jsx'
import { applyTheme, getInitialTheme } from './hooks/useTheme.js'

// Ставим тему до первой отрисовки, чтобы страница не мигнула светлой
applyTheme(getInitialTheme())

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </StrictMode>,
)
