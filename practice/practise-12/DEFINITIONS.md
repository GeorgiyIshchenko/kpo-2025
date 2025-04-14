# Список терминов семинара
###### Нужно написать определения с примером из жизни или кода
- 
Определения
1. Что такое Docker? зачем он нужен?  
   Docker — это инструмент, который позволяет упаковать приложение и всё его окружение (зависимости, настройки, библиотеки) в контейнер. Это как чемодан: положил туда всё, что нужно, и в любой точке мира открыл — всё работает.  
   Пример из жизни:  
   Вместо того чтобы говорить "поставь Node.js 18, PostgreSQL 15 и не забудь про curl", ты просто даёшь файл `docker-compose.yml`, и у человека всё разворачивается само.

2. Как поднять БД в Docker?  
   С помощью `docker-compose` или просто команды `docker run`. Обычно используют `docker-compose.yml`, в котором описывается образ, порты, переменные окружения.  
   Пример:
```yaml
services:
  db:
    image: postgres:15
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass
      POSTGRES_DB: mydb
```

3. Как подключить БД к приложению?  
   Нужно прописать параметры подключения (URL, имя пользователя, пароль) в конфигурации приложения. В Spring Boot это `application.properties` или `application.yml`.  
   Пример:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=pass
```

4. Что такое Repository?  
   Repository — это слой доступа к данным. В Spring Data — это интерфейс, через который мы читаем, сохраняем, обновляем и удаляем данные без написания SQL вручную.  
   Пример:
```java
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByName(String name);
}
```

5. Что такое Hibernate?  
   Hibernate — это ORM-фреймворк, который позволяет работать с базой данных как с обычными Java-объектами. Он сам переводит методы в SQL и управляет сессиями.  
   Пример из жизни:  
   Вместо того чтобы писать SQL-запрос `SELECT * FROM customer WHERE name = 'John'`, ты просто вызываешь метод `findByName("John")`.

6. Связь one-one, one-many, many-many, как делается в Java?
- One-to-One: Один человек — один паспорт.
```java
@OneToOne
private Passport passport;
```
- One-to-Many: Один автор — много книг.
```java
@OneToMany(mappedBy = "author")
private List<Book> books;
```
- Many-to-Many: Студенты и курсы (много студентов учатся на многих курсах).
```java
@ManyToMany
private Set<Course> courses;
```

7. Как сделать общую таблицу для разных объектов?  
   С помощью `@Inheritance` и аннотаций `@DiscriminatorColumn` (если нужна отдельная колонка для типа) или `@MappedSuperclass`.  
   Пример:
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Animal { }

@Entity
public class Dog extends Animal { }

@Entity
public class Cat extends Animal { }
```

8. Уникальный факт  
   В Docker можно запустить не только БД или сервер, но даже графический интерфейс, например, полноценное GUI-приложение через X11. Это значит, что ты можешь упаковать даже desktop-программу в контейнер — и она заработает у любого пользователя без установки зависимостей.
