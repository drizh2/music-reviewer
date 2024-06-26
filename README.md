# Опис додатка та внесених змін
___
## Запуск застосунку

Для того, щоб запустити застосунок потрібно: 
- Створити і додати .env файл з параметрами підключення до email-серверу
- Запустити docker-compose файл

mail.env файл має міститися у корені мікросервісу MailSender та повинен мати такі поля:
- SPRING_MAIL_HOST= - хост email-серверу
- SPRING_MAIL_USERNAME= - пошта, з якої здійснюється відправка
- SPRING_MAIL_PASSWORD= - пароль до пошти, з якої здійснюється відправка
- SPRING_MAIL_PORT= - порт підключення до email-серверу 
- SPRING_MAIL_PROTOCOL= - протокол підключення до email-серверу
- MAIL_ADMIN_EMAIL= - email адреса адміна, на яку будуть відправлятися повідомлення
___

## Докеризація і підняття потрібних сервісів

Запускаючи docker-compose файл, буде піднято в контейнерах такі сервіси:
- zookeper (2181:2181)
- kafka (9092:9092)
- elasticsearch (9200:9200)
- kibana (5601:5601)
- postgres (5433:5432)
- mailsender (8080:8080)
- musicreviewer (8081:8080)
- frontend (3000:3000)

При запуску будуть виконані інструкції з Dockerfile'ів кожного з розроблюваних нами мікросервісів.
___

## Інструкція роботи з застосунком

При входженні на frontend потрібно виконати автентифікацію.  
Для входу треба використати такі дані: 
- login: admin
- password: admin

Далі зліва в меню обрати вкладку page.list і на цій сторінці виконувати дії над сутностями, прописані раніше.

## Налаштоване Security

Для backend частини застосунку було налаштовано gateway з Google Oauth авторизацією.  
Для перевірки потрібно, після запуску docker compose, перейти на ```http://localhost:2002/oauth/authenticate``` і 
виконати необхідні дії.  
Після цього користувачеві повернеться json з його даними, а в базу запишеться сесія з даними користувача і часом
стікання сесії.