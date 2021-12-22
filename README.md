Student: Dmytro Oliinyk

Group: CE-48

Student number: 11

Subject: CSDT

## Specialized virtual library program

### Technology stack

Backend:

    Java 8
    
    Spring boot
    
    Spring data jpa
    
    Spring security
    
    Maven
    
    MySQL
    
    JUnit

Client:

    Thymeleaf

### Run db migration scripts:

Manually run script
csdt-1822-ki48-oliinyk/src/main/resources/db-scripts/V1_0__initial.sql

Than run command from cmd:

    mvn flyway:migrate -Dflyway.configFiles=flyway.conf

### Running guide

   Run flyway in order to have the newest db schema

       mvn flyway:migrate -Dflyway.configFiles=flyway.conf

   Build and run server

       mvn clean package -DskipTests 

       If you want also to execute tests you can:
       remove flag -DskipTests or
       run mvn test
    
       mvn spring-boot:run

*Note: after first start of app, for correct work, sign-in as 'admin'
and create some fields from localhost:8080/admin/field/create:

Field id - Table name - Field type

    FIELD.ASSET.TYPE	asset_type	LINEAR
    
    FIELD.AUTHORS	authors	TABULAR
    
    FIELD.COUNTRY	country	LINEAR
    
    FIELD.DESCRIPTION	description	LINEAR
    
    FIELD.GENRE	genre	TABULAR
    
    FIELD.LANGUAGE	language	LINEAR
    
    FIELD.NAME	name	LINEAR
    
    FIELD.PSEUDONYM	pseudonym	TABULAR
    
    FIELD.SUBSCRIPTION	subscription	TABULAR
    
    FIELD.UPLOADER	uploader	LINEAR
    
    FIELD.WIKI.URL	wiki_url	LINEAR

Go to localhost:8080/admin/field/config to make sure fields are created

### Application guide

Main page:

`Всі книги` - show all books in system. Access: permit all

`Мої книги` - books, uploaded by current user. Access: authorized users

`Збережені` - saved books. Access: authorized users

`Шукати книги` - search books with filters. Access: permit all

`Рекомендовані` - books recommendations. Access: authorized users

`Створити книгу` - upload book. Access: authorized users

`Створити автора` - upload authors information. Access: admin user

Action on paginated book`s pages:

`Завантажити` - download book. Access: permit all

`Зберегти/видалити зі збережених` - add/delete to/from saved. Access: authorized users

`Click on thumbnail` - trigger redirect ot book`s preview

Action on book`s preview

`Завантажити` - download book. Access: permit all

`Зберегти/видалити зі збережених` - add/delete to/from saved. Access: authorized users

`Перейти до авторів` - go to paginated page with possible authors. Access: permit all

Book upload:

Fill in all fields, choose file and thumbnail, click `Зберегти`. Access: authorized users

Author creation:

Fill in all fields, choose thumbnail, click `Зберегти`. Access: admin user

User actions:

`Зареєструватись` - create user account

`Увійти` - sign-in
