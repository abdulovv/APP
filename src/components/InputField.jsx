const InputField = ({ label, type, name, placeholder, value, onChange, maxLength }) => {
    return (
        <label className="inputWrapper">
            <span className="inputLabel">{label}</span>
            <input
                className="InputField"
                type={type}
                name={name}
                value={value}       /* Передаем значение из стейта */
                onChange={onChange} /* Передаем функцию обновления */
                placeholder={placeholder}
                maxLength={maxLength} /* undefined, если не передан — атрибут просто не применяется */
                required
            />
        </label>
    )
}

export default InputField