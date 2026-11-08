import { request } from './http.js'

export const getCurrentUser = () => request('/users/me')

export const updateName = (firstname, lastname) =>
    request('/users/me/name', { method: 'PATCH', body: { firstname, lastname } })

export const updateEmail = (email) =>
    request('/users/me/email', { method: 'PATCH', body: { email } })

export const updatePhone = (phoneNumber) =>
    request('/users/me/phone', { method: 'PATCH', body: { phoneNumber } })

export const updatePassword = (currentPassword, newPassword) =>
    request('/users/me/password', { method: 'PATCH', body: { currentPassword, newPassword } })
