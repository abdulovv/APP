import { request } from './http.js'

export const getPageSettings = () => request('/pages')

export const updatePageSetting = (pageKey, data) => request(`/pages/${pageKey}`, { method: 'PUT', body: data })
