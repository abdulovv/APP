import { request } from './http.js'

export const getAllProducts = () => request('/products')

export const getProductCategories = () => request('/products/categories')

export const getNutrients = () => request('/products/nutrients')

export const createFood = (product) => request('/products/foods', { method: 'POST', body: product })

export const createDrink = (product) => request('/products/drinks', { method: 'POST', body: product })

export const updateProduct = (id, product) => request(`/products/${id}`, { method: 'PUT', body: product })

export const deleteProduct = (id) => request(`/products/${id}`, { method: 'DELETE' })

export const deactivateProduct = (id) => request(`/products/${id}/deactivate`, { method: 'PATCH' })
