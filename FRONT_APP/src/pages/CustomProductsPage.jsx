import { useEffect } from 'react'
import '../css/pages/ProductsPage.css'

const SAMPLE_PRODUCTS = [
    { id: 1, name: 'Homemade granola', category: 'Breakfast', portion: 'Bowl · 60 g', kcal: 412 },
    { id: 2, name: 'Grandma\'s borscht', category: 'Soups', portion: 'Plate · 300 g', kcal: 54 },
    { id: 3, name: 'Protein smoothie', category: 'Drinks', portion: 'Glass · 250 g', kcal: 88 },
    { id: 4, name: 'Oat pancakes', category: 'Breakfast', portion: 'Piece · 45 g', kcal: 198 },
]

function CustomProductsPage({ preview = false }) {
    useEffect(() => {
        document.title = 'My products'
    }, [])

    return (
        <div className="productsPage">
            <div className="productsHeader">
                <div>
                    <h1 className="productsTitle">My products</h1>
                    <p className="productsSubtitle">Your own products with your own nutrition values.</p>
                </div>
                <button type="button" className="btn btnSmall" disabled={!preview} tabIndex={preview ? -1 : undefined}>
                    Add product
                </button>
            </div>

            {preview ? (
                <div className="productsTableWrap">
                    <table className="productsTable">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Category</th>
                                <th>Portion</th>
                                <th className="productsNum">Calories<span className="productsUnit">kcal</span></th>
                            </tr>
                        </thead>
                        <tbody>
                            {SAMPLE_PRODUCTS.map((product) => (
                                <tr key={product.id}>
                                    <td className="productsName">{product.name}</td>
                                    <td><span className="productsTag">{product.category}</span></td>
                                    <td className="productsPortion">{product.portion}</td>
                                    <td className="productsNum">{product.kcal}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            ) : (
                <p className="productsMessage">
                    You haven't added any products yet. Adding your own products is coming soon.
                </p>
            )}
        </div>
    )
}

export default CustomProductsPage
