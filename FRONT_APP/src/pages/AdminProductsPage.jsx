import { useEffect, useState } from 'react'
import '../css/pages/AdminPage.css'
import {
    createDrink,
    createFood,
    deactivateProduct,
    deleteProduct,
    getAllProducts,
    getProductCategories,
    getNutrients,
    updateProduct,
} from '../api/productsApi.js'
import { useNotification } from '../hooks/useNotification.js'
import Notification from '../components/Notification.jsx'
import AdminGuard from '../components/admin/AdminGuard.jsx'
import AdminToolbar from '../components/admin/AdminToolbar.jsx'
import SearchPanel from '../components/admin/SearchPanel.jsx'
import SelectionActionBar from '../components/admin/SelectionActionBar.jsx'
import ProductTable from '../components/admin/ProductTable.jsx'
import Modal from '../components/admin/Modal.jsx'
import ProductForm from '../components/admin/ProductForm.jsx'
import ConfirmDialog from '../components/admin/ConfirmDialog.jsx'

const MODES = ['view', 'edit']

const INITIAL_FILTERS = { type: 'ALL', categoryId: '', status: 'ALL' }

function AdminProductsPage() {
    const [notification, showNotification] = useNotification()

    const [mode, setMode] = useState('view')
    const [products, setProducts] = useState([])
    const [categories, setCategories] = useState([])
    const [nutrients, setNutrients] = useState([])
    const [selectedIds, setSelectedIds] = useState([])

    const [isSearchOpen, setIsSearchOpen] = useState(false)
    const [search, setSearch] = useState('')
    const [filters, setFilters] = useState(INITIAL_FILTERS)

    const [editingId, setEditingId] = useState(null)
    const [isCreating, setIsCreating] = useState(false)
    const [isOpenSelected, setIsOpenSelected] = useState(false)

    // { message, action } — что спросить и что сделать при «Yes»
    const [confirm, setConfirm] = useState(null)
    const [isConfirmBusy, setIsConfirmBusy] = useState(false)

    const visibleProducts = products.filter(p => {
        const matchesSearch = p.name.toLowerCase().includes(search.trim().toLowerCase())
        const matchesType = filters.type === 'ALL' || p.category?.type === filters.type
        const matchesCategory = filters.categoryId === '' || String(p.category?.id) === filters.categoryId
        const matchesStatus =
            filters.status === 'ALL' ||
            (filters.status === 'ACTIVE' && p.isActive) ||
            (filters.status === 'INACTIVE' && !p.isActive)

        return matchesSearch && matchesType && matchesCategory && matchesStatus
    })

    // Сколько фильтров включено — показываем бейджем на кнопке Search
    const activeFiltersCount =
        (search.trim() ? 1 : 0) +
        (filters.type !== 'ALL' ? 1 : 0) +
        (filters.categoryId !== '' ? 1 : 0) +
        (filters.status !== 'ALL' ? 1 : 0)

    const selectedProducts = products.filter(p =>
        selectedIds.includes(p.id)
    )

    const editingProduct = products.find(p => p.id === editingId)

    // Если выделены только неактивные — вместо Deactivate показываем Activate
    const allSelectedInactive =
        selectedProducts.length > 0 && selectedProducts.every(p => !p.isActive)

    const toggleSelect = (id) => {
        if (selectedIds.includes(id)) {
            setSelectedIds(selectedIds.filter(x => x !== id))
        } else {
            setSelectedIds([...selectedIds, id])
        }
    }

    const reset = () => {
        setSelectedIds([])
    }

    const clearFilters = () => {
        setSearch('')
        setFilters(INITIAL_FILTERS)
    }

    const loadProducts = async () => {
        const data = await getAllProducts()
        setProducts(data)
    }

    useEffect(() => {
        document.title = 'Admin · Products'
        const load = async () => {
            try {
                // Три запроса параллельно, ждём все
                const [productsData, categoriesData, nutrientsData] = await Promise.all([
                    getAllProducts(),
                    getProductCategories(),
                    getNutrients(),
                ])
                setProducts(productsData)
                setCategories(categoriesData)
                setNutrients(nutrientsData)
            } catch (error) {
                showNotification('error', error.message)
            }
        }
        load()
    }, [showNotification])

    const handleUpdate = async (product) => {
        await updateProduct(editingId, product)
        await loadProducts()
        setEditingId(null)
        showNotification('success', 'Product saved')
    }

    const handleCreate = async (product) => {
        const category = categories.find(c => c.id === product.category.id)
        const create = category?.type === 'DRINK' ? createDrink : createFood
        await create(product)
        await loadProducts()
        setIsCreating(false)
        showNotification('success', 'Product created')
    }

    const handleDeactivateSelected = async () => {
        try {
            await Promise.all(selectedIds.map(id => deactivateProduct(id)))
            await loadProducts()
            reset()
            showNotification('success', 'Products deactivated')
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const handleActivateSelected = async () => {
        try {
            // PUT только с isActive: остальные поля null, маппер их пропустит
            await Promise.all(selectedIds.map(id => updateProduct(id, { isActive: true })))
            await loadProducts()
            reset()
            showNotification('success', 'Products activated')
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const handleToggleActive = async (product) => {
        try {
            if (product.isActive) {
                await deactivateProduct(product.id)
            } else {
                // PUT только с isActive: остальные поля null, маппер их пропустит
                await updateProduct(product.id, { isActive: true })
            }
            await loadProducts()
            showNotification('success', product.isActive ? 'Product deactivated' : 'Product activated')
        } catch (error) {
            showNotification('error', error.message)
        }
    }

    const askDeleteOne = (product) => {
        setConfirm({
            message: `«${product.name}» will be deleted from the database permanently.`,
            action: async () => {
                await deleteProduct(product.id)
                setSelectedIds(selectedIds.filter(id => id !== product.id))
                setEditingId(null)
                showNotification('success', 'Product deleted')
            },
        })
    }

    const handleConfirm = async () => {
        setIsConfirmBusy(true)
        try {
            await confirm.action()
            await loadProducts()
        } catch (error) {
            showNotification('error', error.message)
        } finally {
            setIsConfirmBusy(false)
            setConfirm(null)
        }
    }

    // view — таблица только для чтения; edit — клик по строке выделяет её
    const isEditMode = mode === 'edit'

    return (
        <AdminGuard>
            <div className="adminPage">
                <Notification notification={notification} />

                <h1>Products</h1>

                <div className="adminTop">
                    <AdminToolbar
                        modes={MODES}
                        mode={mode}
                        onModeChange={setMode}
                        isSearchOpen={isSearchOpen}
                        onSearchToggle={() => setIsSearchOpen(!isSearchOpen)}
                        activeFiltersCount={activeFiltersCount}
                    />

                    {isSearchOpen && (
                        <SearchPanel
                            search={search}
                            onSearchChange={setSearch}
                            filters={filters}
                            onFiltersChange={setFilters}
                            categories={categories}
                            onClear={clearFilters}
                            canClear={activeFiltersCount > 0}
                        />
                    )}

                    {isEditMode && (
                        <SelectionActionBar
                            selectedCount={selectedIds.length}
                            allSelectedInactive={allSelectedInactive}
                            onActivate={handleActivateSelected}
                            onAdd={() => setIsCreating(true)}
                            onOpen={() => setIsOpenSelected(true)}
                            onDeactivate={handleDeactivateSelected}
                            onReset={reset}
                        />
                    )}
                </div>

                <ProductTable
                    products={visibleProducts}
                    selectedIds={isEditMode ? selectedIds : []}
                    onRowClick={isEditMode ? toggleSelect : undefined}
                />

                {isOpenSelected && (
                    <Modal
                        title={
                            selectedProducts.length === 0
                                ? 'Nothing selected'
                                : `Selected products (${selectedProducts.length})`
                        }
                        hint={selectedProducts.length > 0 ? 'Double-click a product to edit it' : null}
                        onClose={() => setIsOpenSelected(false)}
                    >
                        {selectedProducts.length > 0 && (
                            <div className="tableScroll">
                                <ProductTable
                                    products={selectedProducts}
                                    onRowDoubleClick={setEditingId}
                                />
                            </div>
                        )}
                    </Modal>
                )}

                {editingProduct && (
                    <Modal
                        title={`Edit: ${editingProduct.name}`}
                        onClose={() => setEditingId(null)}
                        isTop
                    >
                        <ProductForm
                            key={editingProduct.id}
                            product={editingProduct}
                            categories={categories}
                            nutrients={nutrients}
                            onSubmit={handleUpdate}
                            onDelete={() => askDeleteOne(editingProduct)}
                            onToggleActive={() => handleToggleActive(editingProduct)}
                        />
                    </Modal>
                )}

                {isCreating && (
                    <Modal
                        title="New product"
                        onClose={() => setIsCreating(false)}
                        isTop
                    >
                        <ProductForm
                            categories={categories}
                            nutrients={nutrients}
                            onSubmit={handleCreate}
                        />
                    </Modal>
                )}

                {confirm && (
                    <ConfirmDialog
                        message={confirm.message}
                        onConfirm={handleConfirm}
                        onCancel={() => setConfirm(null)}
                        isBusy={isConfirmBusy}
                    />
                )}
            </div>
        </AdminGuard>
    )
}

export default AdminProductsPage
