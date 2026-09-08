const fetchCountries = async () => {
    try {
        const response = await fetch('http://localhost:8081/api/countries/all', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Не удалось загрузить список стран');
        }

        const data = await response.json(); // Массив из CountryResponse
        return data; // Возвращаем данные наружу
    } catch (error) {
        console.error('Ошибка при получении стран:', error);
        throw error; // Прокидываем ошибку дальше, чтобы компонент о ней знал
    }
};

export default fetchCountries;