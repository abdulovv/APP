function ConfirmDialog({ title = 'Are you sure?', message, onConfirm, onCancel, isBusy = false }) {
    return (
        <div className="modalOverlay confirmOverlay">
            <div className="confirmDialog">
                <button className="closeBtn confirmClose" onClick={onCancel} disabled={isBusy} aria-label="Close">
                    ×
                </button>
                <h3>{title}</h3>
                {message && <p>{message}</p>}
                <div className="confirmActions">
                    <button className="successBtn" onClick={onConfirm} disabled={isBusy}>
                        {isBusy ? '...' : 'Yes'}
                    </button>
                    <button className="dangerBtn" onClick={onCancel} disabled={isBusy}>
                        No
                    </button>
                </div>
            </div>
        </div>
    )
}

export default ConfirmDialog
