function InputField({ label, ...inputProps }) {
    return (
        <label className="field">
            <span className="fieldLabel">{label}</span>
            <input className="fieldInput" {...inputProps} />
        </label>
    )
}

export default InputField
