import { request } from './http.js'

export const signIn = (credentials) => request('/auth/sign-in', { method: 'POST', body: credentials })

export const signUp = (user) => request('/auth/sign-up', { method: 'POST', body: user })

export const signOut = () => request('/auth/sign-out', {method: 'POST'}) 