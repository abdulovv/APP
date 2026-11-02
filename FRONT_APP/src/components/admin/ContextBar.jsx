function ContextBar({ selectedCount, allSelectedInactive, onAdd, onOpen, onActivate, onDeactivate, onReset }) {
    const nothingSelected = selectedCount === 0

    return (
        <div className="contextBar">
            <div className="contextActions">
                <span className="contextCounter">Selected: {selectedCount}</span>

                <div className="contextCenter">
                    <button className="modeBtn" onClick={onAdd}>Add</button>
                    <button className="modeBtn" onClick={onOpen} disabled={nothingSelected}>Open</button>
                    <button className="resetBtn" onClick={onReset} disabled={nothingSelected}>Reset</button>
                </div>

                <div className="contextRight">
                    {allSelectedInactive ? (
                        <button className="activateBtn" onClick={onActivate}>Activate</button>
                    ) : (
                        <button className="resetBtn" onClick={onDeactivate} disabled={nothingSelected}>
                            Deactivate
                        </button>
                    )}
                </div>
            </div>
        </div>
    )
}

export default ContextBar
