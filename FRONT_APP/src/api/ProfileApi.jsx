// Базовый адрес бэкенда. Вынесен в константу, чтобы не дублировать строку
// в каждой функции (как это было сделано с localhost:8081 в остальных api-файлах).
const BASE_URL = 'http://localhost:8081/api/users/me';

// Получить данные текущего пользователя (для первичной загрузки страницы Profile).
// Предполагается, что пользователь авторизован через сессию/cookie,
// поэтому добавляем credentials: 'include', чтобы бэкенд понял, кто спрашивает.
export const fetchCurrentUser = async () => {
    try {
        const response = await fetch(BASE_URL, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Не удалось загрузить профиль');
        }

        return data; // Ожидаем объект вида { firstname, lastname, email, phoneNumber }
    } catch (error) {
        console.error('Ошибка при получении профиля:', error);
        throw error;
    }
};

// Общая внутренняя функция для PATCH-запросов, чтобы не повторять
// один и тот же fetch-код в updateName/updateEmail/updatePhone/updatePassword.
const patchProfile = async (path, body) => {
    try {
        const response = await fetch(`${BASE_URL}${path}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify(body),
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Не удалось сохранить изменения');
        }

        return data; // Ожидаем { message: "..." }
    } catch (error) {
        console.error(`Ошибка при запросе ${path}:`, error);
        throw error;
    }
};

// Изменить имя и фамилию.
// ВАЖНО: путь /name, /email, /phone, /password — предполагаемые эндпоинты.
// Поправьте их под то, что реально отдаёт бэкенд.
export const updateName = ({ firstname, lastname }) =>
    patchProfile('/name', { firstname, lastname });

// Изменить почту.
export const updateEmail = ({ email }) =>
    patchProfile('/email', { email });

// Изменить номер телефона.
export const updatePhone = ({ phoneNumber }) =>
    patchProfile('/phone', { phoneNumber });

// Изменить пароль. Отправляем и старый пароль тоже —
// бэкенд обычно требует его для подтверждения смены.
export const updatePassword = ({ currentPassword, newPassword }) =>
    patchProfile('/password', { currentPassword, newPassword });
