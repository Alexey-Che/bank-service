## Сервис банковских операций - bank-service

- Java 17 
- Spring Boot 3 
- PostgreSQL
- TestContainers
- REST API
- Maven

### Локальный запуск проекта

Запуск осуществляется с помощью [*docker-compose.yml*](docker-compose.yml)
``` bash
docker-compose up -d
```

Тесты запускаются при помощи testcontainers

### Swagger
Swagger-ui доступен на  [*http://localhost:8080/swagger-ui/#/*](http://localhost:8080/swagger-ui/index.html#)