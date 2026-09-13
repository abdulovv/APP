/*
 * ВРЕМЕННЫЙ ТЕСТОВЫЙ КОМПОНЕНТ.
 * Просто заглушка для будущего Diary, заполнена рыбой для проверки скролла.
 *
 * Чтобы убрать целиком:
 *   1) удалить импорт и <DiaryPlaceholder /> из src/pages/ProfilePage.jsx
 *   2) удалить эту папку src/components/diary
 *   3) удалить импорт "../css/components/diary/Diary.css" из ProfilePage.jsx
 *      и папку src/css/components/diary
 * Больше ничего в проекте от этого не зависит.
 */

const DUMMY_ENTRIES = [
    { date: "12 сентября", title: "Первая запись", text: "Тут будет какая-то мысль дня — просто проверяю, как ведёт себя скролл внутри окна дневника, когда текста становится много." },
    { date: "11 сентября", title: "Заметка", text: "Ещё один абзац рыбного текста. Ничего важного, просто заполнение пространства, чтобы окно стало выше, чем сама карточка." },
    { date: "10 сентября", title: "План на день", text: "Сходить погулять, разобраться со стилями, проверить, что скролл работает только вниз-вверх, а не вбок." },
    { date: "9 сентября", title: "Случайная мысль", text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." },
    { date: "8 сентября", title: "Ещё одна запись", text: "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." },
    { date: "7 сентября", title: "Тест длинного блока", text: "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident." },
    { date: "6 сентября", title: "Проверка отступов", text: "Sunt in culpa qui officia deserunt mollit anim id est laborum. Ещё немного текста, чтобы точно вылезти за пределы окна." },
    { date: "5 сентября", title: "Финальная тестовая запись", text: "Если ты долистал(а) досюда — вертикальный скролл внутри окна дневника работает как надо, а горизонтального нет." },
]

const DiaryPlaceholder = () => {
    return (
        <div className="diaryCard">
            <p className="diaryTitle">Diary</p>

            <div className="diaryScroll">
                {DUMMY_ENTRIES.map((entry, i) => (
                    <div className="diaryEntry" key={i}>
                        <span className="diaryEntryDate">{entry.date}</span>
                        <p className="diaryEntryTitle">{entry.title}</p>
                        <p className="diaryEntryText">{entry.text}</p>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default DiaryPlaceholder
