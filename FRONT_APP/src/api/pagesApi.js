import { request } from './http.js'

export const getPages = () => request('/pages')

export const updatePage = (pageKey, data) => request(`/pages/${pageKey}`, { method: 'PUT', body: data })
