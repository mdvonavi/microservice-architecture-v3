## Простая мини-социальная сеть на Java Spring Boot с использованием PostgreSQL

###Описание:
Этот проект представляет собой небольшую социальную сеть, разработанную с использованием Java Spring Boot в качестве фреймворка и PostgreSQL в качестве базы данных. Цель проекта - предоставить простую платформу для обмена сообщениями и создания связей между пользователями.

###Основные функции:

- Регистрация и аутентификация пользователей.
- Создание профиля пользователя с базовой информацией.
- Возможность добавления друзей и обмен сообщениями между ними.
- Публикация постов с изображениями и текстом.
- Поиск пользователей и просмотр их профилей.

###Технологии:

- Java Spring Boot: Используется для создания веб-приложения и управления бизнес-логикой.
- PostgreSQL: Выбран в качестве реляционной базы данных для хранения информации о пользователях, сообщениях и постах.

###Установка:
- Клонируйте репозиторий:
```shell
git clone https://github.com/mdvonavi/microservice-architecture-v3.git
```

- Установите необходимые зависимости с помощью Gradle.
- Запустите сборку проекта с помощью Gradle.
```shell
./gradlew build
```
- Если будет использоваться существующая БД PostgreSQL, то создайте необходимые объекты БД с помощью скрипта SQL/create_schema.sql и внесите параметры в docker-compose.yml
- Запустите установку проекта в docker: 
```shell
docker-compose up
```
- Сервис доступен на 8080 порту

###Текущие возможности:
- Получение списка пользователей:
```shell
curl GET http://localhost:8080/users
```
- Получение пользователя по id:
```shell
curl GET http://localhost:8080/users/1
```
- Добавление пользователя:
```shell
curl POST http://localhost:8080/users
```
- Обновление данных о пользователя по id:
```shell
curl PUT http://localhost:8080/users/1
```
- Удаление пользователя:
```shell
curl DELETE http://localhost:8080/users/1
```

###Пример json для создания пользователя
```{
    "firstName": "Ivan",
    "lastName": "Ivanov",
    "city": 1,
    "phone": "79991234567",
    "sex": "true",
    "nickname": "ivanoff",
    "middleName": "Ivanovich",
    "id": 1,
    "avatar": "dummyLink",
    "birthDate": "10-08-1990",
    "email": "mail@mail.ru",
    "info": "some info string"
}
```

###Создание неймспейсов в кластере:
```shell
kubectl create namespace dev
kubectl create namespace preprod
kubectl create namespace prod
```

Благодарим вас за ваши усилия и интерес к нашему проекту!
