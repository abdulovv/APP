# Рефакторинг имён

## Что сделать после распаковки

1. Удалить старые базы и volume-ы и поднять заново: `docker compose down -v`, затем `docker compose up -d`.
2. В своём `.env` переименовать ключи (пример в `.env.example`):
   - `AUTH_DB_NAME` → `ACCOUNT_DB_NAME` (значение `account`), `AUTH_DB_PORT` → `ACCOUNT_DB_PORT`
   - `NUTRITION_DB_NAME` → `PRODUCT_DB_NAME` (значение `product`), `NUTRITION_DB_PORT` → `PRODUCT_DB_PORT`
   - если в запуске IDE заданы `AUTH_DB_URL` / `NUTRITION_DB_URL`, то это теперь `ACCOUNT_DB_URL` / `PRODUCT_DB_URL`
3. В IDE: Reload Maven project, пересоздать конфигурации запуска для `account-service` и `product-service`.
4. Перед первым запуском: `mvn clean` (иначе в `target/` останутся старые классы).

## Модули

| Было | Стало |
|---|---|
| `auth-service` (`com.app.auth_service`) | `account-service` (`com.app.account_service`) |
| `nutrition-service` (`com.app.nutrition_service`) | `product-service` (`com.app.product_service`) |
| родитель `com.parent:back_app` | `com.app:back-app-parent` |

`eureka-server`, `api-gateway`, `diary-service` не переименованы.

## API

| Было | Стало |
|---|---|
| `/api/nutrition/**` | `/api/products/**` (те же подпути: `/foods`, `/drinks`, `/categories`, `/nutrients`, `/search`, `/{id}`…) |
| `GET /api/admin/users/details/{id}` | `GET /api/admin/users/{id}` |
| вход: `{ "uniqueField", "password" }` | `{ "login", "password" }` |
| позиция приёма пищи: `nutritionId` | `productId` |
| `PUT /api/pages/{key}` — пустой ответ | возвращает обновлённую настройку |

## account-service

| Было | Стало |
|---|---|
| `UserController` / `UserService` | `UserProfileController` / `UserProfileService` |
| `UserAdministrationController` / `UserAdministrationService` | `AdminUserController` / `AdminUserService` |
| `UserResponse` | `UserProfileResponse` |
| `UserSummaryResponse` | `AdminUserListItemResponse` |
| `UserCardDetailsResponse` | `AdminUserDetailsResponse` |
| `AuthResponse` | `MessageResponse` |
| `CountryDTO`, `PageSettingDTO` | `CountryResponse`, `PageSettingResponse` |
| `PremiumRequest` | `GrantPremiumRequest` |
| `Premium` / `PremiumRepository` | `PremiumSubscription` / `PremiumSubscriptionRepository` |
| `PurchaseSource`, `PurchaseStatus` | `PremiumTransactionSource`, `PremiumTransactionStatus` |
| `Role` | `UserRole` |
| `AdminInitializer` | `DefaultAdminInitializer` |
| `PageNotFoundException` | `PageSettingNotFoundException` |
| `IncorrectPasswordException` | `IncorrectCurrentPasswordException` |
| пакет `configuration`, `dtos.common` | `config`, `dtos.responses` |
| таблицы `premiums`, `payments`, `purchases_history` | `premium_subscriptions`, `payment_cards`, `premium_transactions` |

Методы маппера `UserMapper`: `toResponse` → `toProfileResponse`, `toEntity` → `toNewUser`,
`toSummary` → `toAdminListItem`, `toDetails` → `toAdminDetails`.
У `CountryMapper` и `PageSettingMapper`: `toDto` → `toResponse`.

## product-service

| Было | Стало |
|---|---|
| `Nutrition` | `Product` |
| `Category` / `CategoryType` | `ProductCategory` / `ProductCategoryType` |
| `NutritionNutrient` | `ProductNutrient` |
| `NutritionDTO`, `CategoryDTO`, `NutrientValueDTO`, `NutrientDTO` | `ProductDto`, `ProductCategoryDto`, `ProductNutrientDto`, `NutrientResponse` |
| `NutritionController` / `NutritionService` | `ProductController` / `ProductService` |
| `FoodService` + `DrinkService` | объединены в `ProductService` (тип FOOD/DRINK передаётся параметром) |
| `CategoryService` | `ProductCategoryService` |
| `NutritionNotFoundException`, `CategoryNotFoundException` | `ProductNotFoundException`, `ProductCategoryNotFoundException` |
| `WrongCategoryTypeException`, `NoNutrientsException` | `ProductCategoryTypeMismatchException`, `InvalidProductNutrientsException` |
| таблицы `category`, `nutrition`, `nutrition_nutrients` | `product_categories`, `products`, `product_nutrients` |

## diary-service

| Было | Стало |
|---|---|
| `MealLogsInfo` (таблица `meal_logs_info`) | `MealLogItem` (таблица `meal_log_items`, колонка `product_id`) |
| `MealItemRequest` | `MealLogItemRequest` |
| `MealResponse`, `MealLogsInfoResponse` | `MealLogResponse`, `MealLogItemResponse` |
| `DiaryDto`, `BodyWeightLogDto` | `DiaryResponse`, `BodyWeightLogResponse` |
| `MealLogsInfoMapper` | `MealLogItemMapper` |

## api-gateway

`SessionUserFilter` → `filters/SessionAuthenticationFilter`. Маршруты: `account-service-route`, `product-service-route`.

## Правило названий DTO

- `…Request` — только приходит в сервис;
- `…Response` — только уходит из сервиса;
- `…Dto` — и туда, и обратно (в product-service продукт с одной и той же формой JSON).

## Удалено как неиспользуемое

`MealLogDto`, `MealLogsInfoDto`, старый `DiaryResponse` (с вложенными приёмами пищи),
`MealLogsInfoRepository`, `NutritionNutrientRepository`, неиспользуемые методы `toEntity` в мапперах справочников.

## Попутные исправления

- `/api/users/me` теперь отдаёт `countryName` (раньше всегда `null`).
- Премиум на 0 дней больше не проходит валидацию (`@Min(1)`), `period_days` в БД — `SMALLINT` под `Short`.
- `PaymentCardRepository`: параметры `expMonth`/`expYear` — `Short`, как в сущности.
- Проверка «premium активен» — один метод `PremiumSubscription.isActiveAt(...)` и `PremiumService.isPremiumActive(...)` вместо копий в двух сервисах.
- Заголовки `X-User-Id` / `X-User-Role` и проверка админа — в классе `GatewayHeaders` (в каждом сервисе свой).
- `PremiumService`: `jakarta.transaction.Transactional` заменён на спринговый, как везде.
- `DefaultAdminInitializer` ищет страну по имени, а не по id 7.
