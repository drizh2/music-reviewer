# Опис доданого мікросервісу та принципи взаємодії застосунку
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

## Докеризування і підняття потрібних сервісів

Запускаючи docker-compose файл, буде піднято в контейнерах такі сервіси:
- zookeper (2181:2181)
- kafka (9092:9092)
- elasticsearch (9200:9200)
- kibana (5601:5601)
- postgres (5433:5432)
- mailsender (8080:8080)
- musicreviewer (8081:8080)

При запуску будуть виконані інструкції з Dockerfile'ів кожного з розроблюваних нами мікросервісів.
___

## Принцип роботи застосунку

При створенні нової пісні відбуваються наступні дії:
- На стороні мікросервісу musicreviewer формується повідомлення, яке містить тему і контент для email-повідомлення.
- Повідомлення надходить у чергу kafka за заданим топіком (в нашому випадку emailReceived)
- Listener(мікросервіс mailsender) отримує повідомлення і намагається відправити повідомлення на пошту адміна.
- Якщо вийшло, то задає статус success true і зберігає в базу. Якщо не вийшло, то задає статус success false і attempt
1, після чого зберігає у базу.
- Кожні 5 хвилин сервіс перевіряє повідомлення в базі і якщо success false, то намагається повторно відправити
повідомлення
- Якщо вдалося ставить success true і зберігає, якщо ні, то збільшує attempt на 1 і зберігає.
___