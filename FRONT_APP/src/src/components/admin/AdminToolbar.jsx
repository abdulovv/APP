function AdminToolbar({ modes, mode, onModeChange, isSearchOpen, onSearchToggle, activeFiltersCount = 0 }) {
    return (
        <div className="adminToolbar">
            <h2>Tool Bar</h2>
            {modes.map(m => (
                <button
                    key={m}
                    className={m !== mode ? 'modeBtn' : 'modeBtn active'}
                    onClick={() => onModeChange(m)}
                >
                    {m}
                </button>
            ))}

            {onSearchToggle && (
                <button
                    className={isSearchOpen ? 'modeBtn searchToggle active' : 'modeBtn searchToggle'}
                    onClick={onSearchToggle}
                >
                    Search
                    {activeFiltersCount > 0 && <span className="filterBadge">{activeFiltersCount}</span>}
                </button>
            )}
        </div>
    )
}

export default AdminToolbar
