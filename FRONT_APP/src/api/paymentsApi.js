import { request } from './http.js'

export const getCards = () => request('/users/me/cards')

export const addCard = (card) => request('/users/me/cards', { method: 'POST', body: card })

export const removeCard = (id) => request(`/users/me/cards/${id}`, { method: 'DELETE' })

export const makeDefaultCard = (id) => request(`/users/me/cards/${id}/default`, { method: 'PATCH' })
