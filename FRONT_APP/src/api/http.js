const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

export async function request(path, { method = 'GET', body } = {}) {
    let response
    try {
        response = await fetch(API_URL + path, {
            method,
            headers: body ? { 'Content-Type': 'application/json' } : undefined,
            body: body ? JSON.stringify(body) : undefined,
            credentials: 'include'
        })
    } catch {
        throw new Error('Server is unreachable')
    }

    const text = await response.text()
    let data = null
    try {
        data = text ? JSON.parse(text) : null
    } catch {
        data = { message: text }
    }

    if (response.status === 401 && !path.includes('/auth/')) {
        window.location.href = '/sign-in'
        throw new Error('Unauthorized')
    }

    if (!response.ok) {
        throw new Error(data?.message || data?.error || `Request failed: ${response.status}`)
    }
    return data
}
