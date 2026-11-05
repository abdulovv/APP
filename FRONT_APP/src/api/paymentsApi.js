import { request } from './http.js'

// Эндпоинты карт на бэкенде ещё не написаны. Пути ниже — предложение, подгони под свой контроллер.
// Заполняй по одной функции, когда будет готов соответствующий метод на бэкенде.

// GET /payment-cards -> список карт текущего пользователя
export const getCards = () => request('/payment-cards')

// POST /payment-cards -> добавить карту (CVC не отправляем и не храним)
// export const addCard = (card) => ...

// DELETE /payment-cards/{id} -> удалить карту
// export const removeCard = (id) => ...

// PATCH /payment-cards/{id}/default -> сделать карту основной
// export const makeDefaultCard = (id) => ...
