import { useEffect, useState } from 'react'
import { getAllProducts, getNutrients, getProductCategories } from '../api/productsApi.js'
import ProductFilters from '../components/products/ProductFilters.jsx'
import ProductsTable from '../components/products/ProductsTable.jsx'
import '../css/pages/ProductsPage.css'

const INITIAL_FILTERS = { search: '', type: 'ALL', categoryId: '' }

function ProductsPage() {
    const [products, setProducts] = useState([])
    const [categories, setCategories] = useState([])
    const [nutrients, setNutrients] = useState([])
    const [filters, setFilters] = useState(INITIAL_FILTERS)
    const [loading, setLoading] = useState(true)
    const [loadError, setLoadError] = useState('')

    useEffect(() => {
        document.title = 'Products'
        Promise.all([getAllProducts(), getProductCategories(), getNutrients()])
            .then(([productsData, categoriesData, nutrientsData]) => {
                const active = productsData
                    .filter((product) => product.isActive)
                    .sort((a, b) => a.name.localeCompare(b.name))
                setProducts(active)
                setCategories(categoriesData)
                setNutrients(nutrientsData)
            })
            .catch((error) => setLoadError(error.message))
            .finally(() => setLoading(false))
    }, [])

    const query = filters.search.trim().toLowerCase()
    const visibleProducts = products.filter((product) => {
        const matchesSearch = product.name.toLowerCase().includes(query)
        const matchesType = filters.type === 'ALL' || product.category?.type === filters.type
        const matchesCategory = filters.categoryId === '' || String(product.category?.id) === filters.categoryId
        return matchesSearch && matchesType && matchesCategory
    })

    const hasFilters = filters.search.trim() !== '' || filters.type !== 'ALL' || filters.categoryId !== ''

    return (
        <div className="productsPage">
            <div>
                <h1 className="productsTitle">Products</h1>
                <p className="productsSubtitle">Nutrition values are given per 100 g.</p>
            </div>

            <ProductFilters
                filters={filters}
                onChange={setFilters}
                categories={categories}
                onClear={() => setFilters(INITIAL_FILTERS)}
                canClear={hasFilters}
            />

            {loading && <p className="productsMessage">Loading products...</p>}

            {loadError && <p className="productsMessage">{loadError}</p>}

            {!loading && !loadError && (
                <>
                    <p className="productsCount">
                        {visibleProducts.length} of {products.length} products
                    </p>

                    {visibleProducts.length === 0 ? (
                        <p className="productsMessage">
                            {products.length === 0 ? 'No products yet.' : 'Nothing found. Try another search or filter.'}
                        </p>
                    ) : (
                        <ProductsTable products={visibleProducts} nutrients={nutrients} />
                    )}
                </>
            )}
        </div>
    )
}

export default ProductsPage
