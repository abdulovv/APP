import { request } from './http.js'

export const getMyPremiumHistory = () => request('/users/me/premium/history')

export const purchasePremium = (cardId, months) =>
    request('/users/me/premium/purchase', { method: 'POST', body: { cardId, months } })

export const cancelPremium = () => request('/users/me/premium/cancel', { method: 'POST' })
