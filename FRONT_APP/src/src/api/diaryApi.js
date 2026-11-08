import { request } from './http.js'

export const getDiary = () => request('/diary/me')

export const createDiary = (diary) => request('/diary', { method: 'POST', body: diary })

export const getMealLogs = (date) => request(date ? `/diary/meal-logs?date=${date}` : '/diary/meal-logs')

export const createMealLog = (mealLog) => request('/diary/meal-logs', { method: 'POST', body: mealLog })

export const deleteMealLog = (id) => request(`/diary/meal-logs/${id}`, { method: 'DELETE' })
