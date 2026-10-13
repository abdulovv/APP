import { useEffect } from 'react'
import MealSection from '../components/diary/MealSection.jsx'
import '../css/pages/DiaryPage.css'

const MEALS = [
    { type: 'BREAKFAST', title: 'Breakfast' },
    { type: 'LUNCH', title: 'Lunch' },
    { type: 'DINNER', title: 'Dinner' },
    { type: 'SNACK', title: 'Snack' },
]

function DiaryPage() {
    useEffect(() => {
        document.title = 'Diary'
    }, [])

    return (
        <div className="card diaryCard">
            <h1 className="cardTitle">Diary</h1>

            <div className="diaryMeals">
                {MEALS.map((meal) => (
                    <MealSection key={meal.type} title={meal.title} />
                ))}
            </div>
        </div>
    )
}

export default DiaryPage
