function MealSection({ title, items = [] }) {
    return (
        <section className="mealSection">
            <h2 className="mealTitle">{title}</h2>

            {items.length === 0 ? (
                <p className="mealEmpty">No products yet</p>
            ) : (
                <ul className="mealItems">
                    {items.map((item) => (
                        <li key={item.id} className="mealItem">
                            <span>{item.name}</span>
                            <span>{item.weight} g</span>
                        </li>
                    ))}
                </ul>
            )}
        </section>
    )
}

export default MealSection
