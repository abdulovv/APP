const InputField = ({ label, type, name, placeholder, value, onChange }) => {
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
                required
            />
        </label>
    )
}

export default InputField