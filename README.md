# Book shop
Pet проект веб-платформа, предназначенная для управления книжным онлайн магазином. Она позволяет пользователям регистрироваться, покупать книги, а также управлять счетами и информацией о книгах и авторах.

**Список используемых технологий**  
Java 17, Spring (Boot, Cloud, JPA), PostgreSQL, Liquibase, Docker, Kubernetes, Lombok, Mapstruct, RabbitMQ, Swagger/OpenAPI, Maven, Junit.

<details>
<summary><b> Архитектура и конфигурация </b></summary>

![img.png](img.png)

Более подробно ознакомиться со всеми диаграммами можно по [ссылке](https://viewer.diagrams.net/?tags=%7B%7D&title=Book-shop.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D11n4zvNIzHLZkpbMsf_7HfmgUeFIu5qYm%26export%3Ddownload "Всплывающая подсказка")

Удаленный репозиторий с конфигурацией: [book_shop_config_repo](https://github.com/LesnikLesnik/book_shop_config_repo)

Дополнительная информация о проекте находится на [гугл-диске](https://drive.google.com/drive/folders/1FPWLY1P8NDTB0QaqeQeBRdb7eHueuFWc?usp=sharing)
</details>

## Запуск
### Инициализация окружения
```shell
docker-compose up config-service registry database rabbit
```
Поднятие конфигурационного сервиса, Eureka, базы данных и RabbitMQ.  
Выполнение этой команды поднимает все критически важные сервисы и контейнеры.
После выполнения этой команды возможен локальный запуск  


### Docker
После инициализации окружения запускаются остальные сервисы
```shell
docker-compose up 
```
Точка входа в приложение через gateway
```
http://localhost:8989
```
### Kubernetes
Для запуска необходимо стартануть Minikube
```shell
minikube start
```
Эта команда запускает все необходимые деплойменты и сервисы k8s
```shell
kubectl apply -f ./k8s
```
Запустить туннель, который перенаправляет трафик на LoadBalancer, предоставляя внешний IP-адрес для доступа к сервисам внутри кластера
```shell
minikube gateway --url
```
## Переменные окружения
- `SPRING_PROFILES_ACTIVE` - профиль для запуска приложения. 
  - _**dev**_ - локальный запуск
  - _**prod**_  - запуск через docker-compose и k8s
- `NAME_SERVICE_DATABASE_HOST` - хост базы данных
  - _**dev**_ - localhost
  - _**prod**_ - database (по имени контейнера с БД)
- `RABBIT_HOST` - хост RabbitMq
  - _**prod**_ - rabbit
  - _**dev**_ - localhost 

