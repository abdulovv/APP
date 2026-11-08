import { useState } from 'react'

function InputField({ label, ...inputProps }) {
    const [showPassword, setShowPassword] = useState(false)

    const isPassword = inputProps.type === 'password'
    const type = isPassword && showPassword ? 'text' : inputProps.type

    return (
        <label className="field">
            <span className="fieldLabel">{label}</span>

            <div className="fieldInputWrap">
                <input
                    {...inputProps}
                    type={type}
                    className={isPassword ? 'fieldInput hasToggle' : 'fieldInput'}
                />

                {isPassword && (
                    <button
                        type="button"
                        className="fieldToggle"
                        onClick={() => setShowPassword(!showPassword)}
                        aria-label={showPassword ? 'Hide password' : 'Show password'}
                    >
                        <svg
                            width="18"
                            height="18"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="currentColor"
                            strokeWidth="1.6"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            aria-hidden="true"
                        >
                            {showPassword ? (
                                <>
                                    <path d="M10.7 5.1A10.6 10.6 0 0 1 12 5c6.5 0 10 7 10 7a17.6 17.6 0 0 1-2.2 3.2" />
                                    <path d="M6.6 6.6A17.5 17.5 0 0 0 2 12s3.5 7 10 7a10.4 10.4 0 0 0 5.4-1.5" />
                                    <path d="M9.9 9.9a3 3 0 0 0 4.2 4.2" />
                                    <path d="m2 2 20 20" />
                                </>
                            ) : (
                                <>
                                    <path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12Z" />
                                    <circle cx="12" cy="12" r="3" />
                                </>
                            )}
                        </svg>
                    </button>
                )}
            </div>
        </label>
    )
}

export default InputField
