import { request } from './http.js'

export const getAllNutrition = () => request('/nutrition')

export const getCategories = () => request('/nutrition/categories')

export const getNutrients = () => request('/nutrition/nutrients')

export const createFood = (product) => request('/nutrition/foods', { method: 'POST', body: product })

export const createDrink = (product) => request('/nutrition/drinks', { method: 'POST', body: product })

export const updateNutrition = (id, product) => request(`/nutrition/${id}`, { method: 'PUT', body: product })

export const deleteNutrition = (id) => request(`/nutrition/${id}`, { method: 'DELETE' })

export const deactivateNutrition = (id) => request(`/nutrition/${id}/deactivate`, { method: 'PATCH' })
