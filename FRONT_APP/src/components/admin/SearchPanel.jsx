function SearchPanel({ search, onSearchChange, filters, onFiltersChange, categories, onClear, canClear }) {
    const setFilter = (name, value) => {
        onFiltersChange({ ...filters, [name]: value })
    }

    // Категории в фильтре зависят от выбранного типа
    const categoryOptions = filters.type === 'ALL'
        ? categories
        : categories.filter(c => c.type === filters.type)

    return (
        <div className="searchPanel">
            <input
                className="searchInput"
                type="search"
                placeholder="Search by name..."
                value={search}
                onChange={e => onSearchChange(e.target.value)}
                autoFocus
            />

            <select
                className="filterSelect"
                value={filters.type}
                onChange={e => onFiltersChange({ ...filters, type: e.target.value, categoryId: '' })}
            >
                <option value="ALL">All types</option>
                <option value="FOOD">Food</option>
                <option value="DRINK">Drinks</option>
            </select>

            <select
                className="filterSelect"
                value={filters.categoryId}
                onChange={e => setFilter('categoryId', e.target.value)}
            >
                <option value="">All categories</option>
                {categoryOptions.map(c => (
                    <option key={c.id} value={c.id}>{c.name}</option>
                ))}
            </select>

            <select
                className="filterSelect"
                value={filters.status}
                onChange={e => setFilter('status', e.target.value)}
            >
                <option value="ALL">Any status</option>
                <option value="ACTIVE">Active</option>
                <option value="INACTIVE">Inactive</option>
            </select>

            <button className="resetBtn" onClick={onClear} disabled={!canClear}>Clear</button>
        </div>
    )
}

export default SearchPanel
