function Modal({ title, hint, onClose, isTop = false, actions, children }) {
    return (
        <div className={isTop ? 'modalOverlay modalTop' : 'modalOverlay'}>
            <div className="modal">
                <div className="modalHeader">
                    <h2>{title}</h2>
                    <button className="closeBtn" onClick={onClose} aria-label="Close">×</button>
                </div>

                {hint && <p className="modalHint">{hint}</p>}

                {/* Окно само не скроллится — скроллится только содержимое внутри */}
                <div className="modalBody">{children}</div>

                {actions && <div className="modalActions">{actions}</div>}
            </div>
        </div>
    )
}

export default Modal
