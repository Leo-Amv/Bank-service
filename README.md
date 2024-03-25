# ["Сервис перевода денег"](https://github.com/Leo-Amv/Bank-service)

## Описание проекта

REST-сервис предоставляет интерфейс для перевода денег с одной карты на другую по [заранее описанной спецификации](https://github.com/Leo-Amv/Bank-service/blob/main/src/main/resources/MoneyTransferServiceSpecification.yaml).
Заранее подготовленное веб-приложение [(FRONT)](https://github.com/Leo-Amv/Bank-service/tree/main/frontend) подключается к разработанному сервису без доработок и использует его функционал для перевода денег.

Изначально front доступен на порту 3000, back - на порту 5500.

## Требования к приложению

- Сервис должен предоставлять REST интерфейс для интеграции с FRONT
- Сервис должны реализовывать все методы перевода с одной банковской карты на другую описанные в протоколе [MoneyTransferServiceSpecification.yaml](https://github.com/Leo-Amv/Bank-service/blob/main/src/main/resources/MoneyTransferServiceSpecification.yaml)
- Все изменения должны записываться в файл (лог переводов в произвольном формате с указанием даты, времени, карта с которой было списание, карта зачисления, сумма, комиссия, результат операции если был)

## Требования в реализации

- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов gradle/maven
- Для запуска используется docker, docker-compose
- Код размещен на github
- Код покрыт unit тестами с использованием mockito
- Добавлены интеграционные тесты с использованием testcontainers

#### Шаги реализации:
- Изучить протокол получения и отправки сообщений
- Нарисовать схему приложений
- Описать архитектуру приложения (где хранятся настройки, описать формат хранения данных о картах)
- Создать репозиторий проекта на github
- Протестировать приложение с помощью curl/postman
- Написать dockerfile и создать контейнер
- Написать docker-compose скрипт для запуска FRONT и написанного REST-SERVICE
- Протестировать запуск с помощью docker-compose и интеграцию с FRONT
- Написать README.md к проекту, где описать команду запуска, порт и примеры запросов.
- Отправить на проверку



## ВАЖНО!!!

[Интеграционный тест](https://github.com/Leo-Amv/Bank-service/blob/main/src/test/java/ru/gb/money_transfer/MoneyTransferApplicationTests.java) 
изначально закомментирован, поскольку с ним не получится собрать docker-контейнер.
После сборки docker-контейнера требуется раскомментировать и запустить данный тест.

## Запуск
### Вариант 1 Dockerfile
- Через терминал собираем jar архив с spring boot приложением: `./mvn clean package `
- Создаем образ из нашего Dockerfile, мы должны запустить: `docker build -t transfer_service_backend:latest .  `
- Запускаем контейнер из нашего образа: `docker run --rm -p 8080:8080 -it transfer_service_backend:latest`

### Вариант 2 с помощью файла docker-compose.yml
- Черезтерминал собираем jar архив с spring boot приложением: `./mvn clean package`
- в терминале выполнить команду: `docker-compose up`

## Изначальные тестовые данные карт в файле [application.properties](https://github.com/Leo-Amv/Bank-service/blob/main/src/main/resources/application.properties)

## Проверка
http://localhost:3030
- Протестировать приложение с помощью curl/postman

POST request --> http://localhost:8080/transfer
```
{
"cardFromNumber": "1234123412341234",
"cardFromValidTill": "01/25",
"cardFromCVV": "999",
"cardToNumber": "4321432143214321",
"amount": {
"value": 10000,
"currency": "RUR"
}
}
```

------------------------------------------------
POST request --> http://localhost:8080/confirmOperation

```
{
"code": "0000"
}
```
