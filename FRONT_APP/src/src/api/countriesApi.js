import { request } from './http.js'

export const getCountries = () => request('/countries/all')
