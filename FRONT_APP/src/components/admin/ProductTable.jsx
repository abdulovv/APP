function ProductTable({ products, selectedIds = [], onRowClick, onRowDoubleClick }) {
    return (
        <table className="adminTable">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Portion</th>
                    <th>Nutrients (100 g)</th>
                    <th>Active</th>
                </tr>
            </thead>
            <tbody>
                {products.map(product => {
                    const classes = []
                    if (selectedIds.includes(product.id)) classes.push('selected')
                    if (!product.isActive) classes.push('inactive')

                    return (
                        <tr
                            key={product.id}
                            className={classes.join(' ')}
                            onClick={onRowClick ? () => onRowClick(product.id) : undefined}
                            onDoubleClick={onRowDoubleClick ? () => onRowDoubleClick(product.id) : undefined}
                        >
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.category?.name ?? '—'}</td>
                            <td>{product.portionName} ({product.portionWeight} г)</td>
                            <td>
                                {product.nutrients.map(n => (
                                    <div key={n.nutrientId}>
                                        {n.name}: {n.value} {n.unit}
                                    </div>
                                ))}
                            </td>
                            <td>{product.isActive ? '✓' : '✗'}</td>
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}

export default ProductTable
