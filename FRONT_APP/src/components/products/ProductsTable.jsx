const formatNumber = (value) => {
    if (value === null || value === undefined) return '—'
    return Number(value).toLocaleString('en-US', { maximumFractionDigits: 2 })
}

function ProductsTable({ products, nutrients }) {
    const valueOf = (product, nutrientId) =>
        product.nutrients.find((item) => item.nutrientId === nutrientId)?.value

    return (
        <div className="productsTableWrap">
            <table className="productsTable">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Portion</th>
                        {nutrients.map((nutrient) => (
                            <th key={nutrient.id} className="productsNum">
                                {nutrient.name}
                                <span className="productsUnit">{nutrient.unit}</span>
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td className="productsName">{product.name}</td>
                            <td>
                                <span className={product.category?.type === 'DRINK' ? 'productsTag drink' : 'productsTag'}>
                                    {product.category?.name ?? '—'}
                                </span>
                            </td>
                            <td className="productsPortion">
                                {product.portionName} · {formatNumber(product.portionWeight)} g
                            </td>
                            {nutrients.map((nutrient) => (
                                <td key={nutrient.id} className="productsNum">
                                    {formatNumber(valueOf(product, nutrient.id))}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default ProductsTable
