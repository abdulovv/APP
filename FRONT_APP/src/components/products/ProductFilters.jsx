const TYPES = {
    ALL: 'All',
    FOOD: 'Food',
    DRINK: 'Drinks',
}

function ProductFilters({ filters, onChange, categories, onClear, canClear }) {
    const categoryOptions = filters.type === 'ALL'
        ? categories
        : categories.filter((category) => category.type === filters.type)

    return (
        <div className="productsToolbar">
            <input
                className="fieldInput productsSearch"
                type="search"
                placeholder="Search by name..."
                value={filters.search}
                onChange={(e) => onChange({ ...filters, search: e.target.value })}
                aria-label="Search products"
            />

            <div className="productsTypeSwitch" role="group" aria-label="Product type">
                {Object.entries(TYPES).map(([key, label]) => (
                    <button
                        key={key}
                        type="button"
                        className={filters.type === key ? 'productsTypeBtn active' : 'productsTypeBtn'}
                        onClick={() => onChange({ ...filters, type: key, categoryId: '' })}
                    >
                        {label}
                    </button>
                ))}
            </div>

            <select
                className="fieldInput productsSelect"
                value={filters.categoryId}
                onChange={(e) => onChange({ ...filters, categoryId: e.target.value })}
                aria-label="Category"
            >
                <option value="">All categories</option>
                {categoryOptions.map((category) => (
                    <option key={category.id} value={category.id}>{category.name}</option>
                ))}
            </select>

            <button type="button" className="productsClear" onClick={onClear} disabled={!canClear}>
                Clear
            </button>
        </div>
    )
}

export default ProductFilters
