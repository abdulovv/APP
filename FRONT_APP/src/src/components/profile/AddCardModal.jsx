// Окно «Add card» для вкладки Payment methods.
//
// Что здесь будет:
//   - поля: номер карты, имя держателя, срок (MM/YY), CVC, галочка «Make default»;
//   - кнопки «Cancel» и «Add card»;
//   - проверки до отправки (номер из 16 цифр, срок не в прошлом и т.д.).
//
// Классы из ProfilePage.css: profileModalOverlay (затемнение), profileModal (само окно),
//   profileSectionTitle, profileFormGrid, checkboxLine, profileModalActions;
//   кнопки: btn btnSmall и btn btnSecondary btnSmall; поля: <InputField />
//
// Получает: onClose() — закрыть окно, onAdded() — вызвать после успешного добавления.
// Открывать будет PaymentsTab: state `showModal` и условный рендер {showModal && <AddCardModal ... />}.

function AddCardModal({ onClose, onAdded }) {
    return (
        <div className="profileModalOverlay">
            <div className="profileModal">
                <h2 className="profileSectionTitle">Add card</h2>
                {/* TODO: форма */}
                <div className="profileModalActions">
                    <button type="button" className="btn btnSecondary btnSmall" onClick={onClose}>Cancel</button>
                </div>
            </div>
        </div>
    )
}

export default AddCardModal
