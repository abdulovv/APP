import { useState, useEffect } from 'react';
import fetchCountries from '../api/CountriesApi.jsx';

const CountrySelect = ({ value, onChange }) => {
    const [countries, setCountries] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        const getCountries = async () => {
            setIsLoading(true);
            try {
                const data = await fetchCountries();
                setCountries(data);
            } catch (error) {
                console.error("Ошибка загрузки стран в селекте:", error);
            } finally {
                setIsLoading(false);
            }
        };
        getCountries();
    }, []);

    if (isLoading) {
        return (
            <label className="inputWrapper">
                <span className="inputLabel">Country</span>
                <select className="selectField" disabled>
                    <option>Loading countries...</option>
                </select>
            </label>
        );
    }

    const selectedCountryObject = countries.find(c => c.name === value);
    const selectValue = selectedCountryObject 
        ? JSON.stringify({
            id: selectedCountryObject.id,
            name: selectedCountryObject.name,
            code: selectedCountryObject.phoneCode,
            // ВАЖНО: minPhoneLength/maxPhoneLength — предполагаемые имена полей
            // из CountryResponse (по аналогии с phoneCode). Если бэкенд отдаёт
            // их иначе (например, minPhoneNumberLength), поправьте здесь.
            minLength: selectedCountryObject.minPhoneLength,
            maxLength: selectedCountryObject.maxPhoneLength,
        })
        : "";

    return (
        <label className="inputWrapper">
            <span className="inputLabel">Country</span>
            <select name="country" className="selectField" value={selectValue} onChange={onChange} required>
                <option value="" disabled>Select your country</option>
                {countries.map((country) => (
                    <option
                        key={country.id}
                        value={JSON.stringify({
                            id: country.id,
                            name: country.name,
                            code: country.phoneCode,
                            minLength: country.minPhoneLength,
                            maxLength: country.maxPhoneLength,
                        })}
                    >
                        {country.name} ({country.phoneCode})
                    </option>
                ))}
            </select>
        </label>
    );
};

export default CountrySelect;