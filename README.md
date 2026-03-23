# Schedule Manager

Небольшой Spring Boot сервис для управления расписаниями, шаблонами, слотами и сотрудниками.

Сервис умеет:
- создавать сотрудников, расписания, шаблоны, слоты и периоды;
- получать сущности по `id`;
- искать периоды по фильтру;
- получать полное расписание по `id` или по имени;
- находить расписание по имени с опечаткой до 2 символов;
- проверять, чтобы периоды внутри одного расписания не пересекались по времени.

## Стек

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Maven


По умолчанию приложение ожидает базу данных с такими настройками:

```yml
url: jdbc:postgresql://localhost:5433/schedule_db
username: postgres
password: postgres
port: 8082

Основные сущности
Employee - сотрудник.
Schedule - расписание с названием и тегами.
ScheduleTemplate - шаблон расписания.
ScheduleSlot - временной слот внутри шаблона.
SchedulePeriod - слот, привязанный к конкретному расписанию.

# Основные эндпоинты
Сотрудники
POST /api/employees
GET /api/employees/{id}
Пример создания:

POST /api/employees
Content-Type: application/json

{
  "employeeName": "Ivan Petrov",
  "status": "WORKING",
  "position": "EMPLOYEE"
}
Расписания
POST /api/schedules
GET /api/schedules/{id}
GET /api/schedules/full?id={id}
GET /api/schedules/full?name={name}
Пример создания:

POST /api/schedules
Content-Type: application/json

{
  "scheduleName": "Main Schedule",
  "scheduleTags": ["office", "team-a"]
}
Шаблоны расписания
POST /api/schedule-templates
GET /api/schedule-templates/{id}
Пример создания:

POST /api/schedule-templates
Content-Type: application/json

{
  "templateType": "DF"
}
Слоты расписания
POST /api/schedule-slots
GET /api/schedule-slots/{id}
Пример создания:

POST /api/schedule-slots
Content-Type: application/json

{
  "scheduleTemplateId": "template_id",
  "beginTime": "09:00:00+03:00",
  "endTime": "18:00:00+03:00",
  "priority": "NORMAL"
}
Периоды расписания
POST /api/schedule-periods
GET /api/schedule-periods/{id}
POST /api/schedule-periods/search
При создании периода обязателен заголовок x-current-user.

Пример создания:

POST /api/schedule-periods
Content-Type: application/json
x-current-user: employee_id

{
  "slotId": "slot_id",
  "scheduleId": "schedule_id",
  "slotType": "LOCAL",
  "executorId": "employee_id"
}
Пример поиска:

POST /api/schedule-periods/search
Content-Type: application/json

{
  "scheduleId": "schedule_id",
  "administratorId": "employee_id",
  "page": 0,
  "size": 10,
  "sort": "id"
}
