import { useState } from 'react'
import InputField from '../InputField.jsx'

// Превращаем продукт с сервера в состояние формы.
// Все значения — строки: input всегда отдаёт строку, числа соберём при отправке.
function toFormState(product) {
    const nutrientValues = {}
    product?.nutrients.forEach(n => {
        nutrientValues[n.nutrientId] = String(n.value)
    })

    return {
        name: product?.name ?? '',
        portionName: product?.portionName ?? '',
        portionWeight: product?.portionWeight != null ? String(product.portionWeight) : '',
        categoryId: product?.category?.id != null ? String(product.category.id) : '',
        nutrientValues,
    }
}

function ProductForm({ product, categories, nutrients, onSubmit, onDelete, onToggleActive }) {
    const [form, setForm] = useState(() => toFormState(product))
    const [isSaving, setIsSaving] = useState(false)
    const [error, setError] = useState('')

    const isEditing = Boolean(product)

    const setField = (field, value) => {
        setForm({ ...form, [field]: value })
    }

    const setNutrientValue = (nutrientId, value) => {
        setForm({ ...form, nutrientValues: { ...form.nutrientValues, [nutrientId]: value } })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        const filledNutrients = Object.entries(form.nutrientValues).filter(([, value]) => value !== '')
        if (filledNutrients.length === 0) {
            setError('Fill in at least one nutrient')
            return
        }

        setError('')
        setIsSaving(true)
        try {
            await onSubmit({
                name: form.name.trim(),
                portionName: form.portionName.trim(),
                portionWeight: Number(form.portionWeight),
                category: { id: Number(form.categoryId) },
                nutrients: filledNutrients.map(([nutrientId, value]) => ({
                    nutrientId: Number(nutrientId),
                    value: Number(value),
                })),
            })
        } catch (err) {
            setError(err.message)
        } finally {
            setIsSaving(false)
        }
    }

    const foodCategories = categories.filter(c => c.type === 'FOOD')
    const drinkCategories = categories.filter(c => c.type === 'DRINK')

    return (
        <form className="productForm" onSubmit={handleSubmit}>
            <div className="productFormFields">
                <InputField
                    label="Name"
                    value={form.name}
                    onChange={e => setField('name', e.target.value)}
                    required
                />

                <div className="formRow">
                    <InputField
                        label="Portion name"
                        placeholder="шт, стакан, 100 г..."
                        value={form.portionName}
                        onChange={e => setField('portionName', e.target.value)}
                        required
                    />
                    <InputField
                        label="Portion weight (g)"
                        type="number"
                        min="0.01"
                        step="0.01"
                        value={form.portionWeight}
                        onChange={e => setField('portionWeight', e.target.value)}
                        required
                    />
                </div>

                <label className="field">
                    <span className="fieldLabel">Category</span>
                    <select
                        className="fieldInput"
                        value={form.categoryId}
                        onChange={e => setField('categoryId', e.target.value)}
                        required
                    >
                        <option value="" disabled>Choose category</option>
                        <optgroup label="Food">
                            {foodCategories.map(c => (
                                <option key={c.id} value={c.id}>{c.name}</option>
                            ))}
                        </optgroup>
                        <optgroup label="Drinks">
                            {drinkCategories.map(c => (
                                <option key={c.id} value={c.id}>{c.name}</option>
                            ))}
                        </optgroup>
                    </select>
                </label>

                <fieldset className="nutrientFieldset">
                    <legend>Nutrients per 100 g (leave empty if unknown)</legend>
                    <div className="nutrientGrid">
                        {nutrients.map(n => (
                            <InputField
                                key={n.id}
                                label={`${n.name}, ${n.unit}`}
                                type="number"
                                min="0"
                                step="0.01"
                                placeholder="—"
                                value={form.nutrientValues[n.id] ?? ''}
                                onChange={e => setNutrientValue(n.id, e.target.value)}
                            />
                        ))}
                    </div>
                </fieldset>
            </div>

            {error && <p className="formError">{error}</p>}

            <div className="formActions">
                {onDelete && (
                    <button className="dangerBtn" type="button" onClick={onDelete} disabled={isSaving}>
                        Delete
                    </button>
                )}
                {onToggleActive && (
                    <button
                        className={product.isActive ? 'resetBtn' : 'activateBtn'}
                        type="button"
                        onClick={onToggleActive}
                        disabled={isSaving}
                    >
                        {product.isActive ? 'Deactivate' : 'Activate'}
                    </button>
                )}
                <button className="btn" type="submit" disabled={isSaving}>
                    {isSaving ? 'Saving...' : isEditing ? 'Save changes' : 'Create product'}
                </button>
            </div>
        </form>
    )
}

export default ProductForm
