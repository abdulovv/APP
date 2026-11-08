import { request } from './http.js'

// GET /users/me/premium/history -> история премиума текущего пользователя (эндпоинт ещё нужно написать)
export const getMyPremiumHistory = () => request('/users/me/premium/history')

// POST /premium/purchase -> имитация покупки / продления премиума (будет позже)
// export const purchasePremium = (cardId, months) => ...
