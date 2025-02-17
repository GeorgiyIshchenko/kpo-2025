# Семинар 2

### Определения

1. Объектно-ориентированное программирование (ООП):
   ООП - это парадигма программирования, которая использует "объекты" для представления данных и методов. Объекты могут содержать как состояния (поля), так и поведения (методы), что позволяет моделировать реальные сущности и их взаимодействия.

2. Реализация:
   Реализация - это процесс создания конкретной версии метода или класса в коде на основе абстрактного интерфейса или базового класса. В Java это происходит через объявление методов и классов, которые предоставляют конкретное поведение.

3. Ассоциация:
   Ассоциация - это отношение между двумя объектами, при котором один объект использует или взаимодействует с другим. Это отношение может быть односторонним или двусторонним.

4. Композиция:
   Композиция - это специальный вид ассоциации, при котором один объект (контейнер) содержит другие объекты (части), которые не могут существовать вне контейнера. Например, класс Car может включать объекты Engine, который не могут существовать без автомобиля.

5. Агрегация:
   Агрегация - это более слабая форма композиции, где один объект содержит ссылки на другие объекты, которые могут существовать независимо от него. Например, класс Car может агрегировать объекты Engine, и мы можем менять engine у конкретного car.

6. Полиморфизм:
   Полиморфизм - это возможность методов действовать по-разному в зависимости от объекта, который их вызывает. В Java полиморфизм достигается через перегрузку методов и переопределение методов в подклассах, что позволяет использовать единый интерфейс для работы с разными типами данных.

7. Наследование:
   Наследование - это механизм, который позволяет создавать новые классы на основе существующих, унаследуя их свойства и методы. Это способствует повторному использованию кода и созданию иерархий классов. Например, класс SuperCar может наследовать от класса Car и композировать объект VeryPowefullEngine, который наследуется от Engine.

8. Инкапсуляция:
   Инкапсуляция - это скрытие внутренних деталей реализации объекта и предоставление доступа к ним только через публичные методы. Это поддерживает целостность данных и защищает объект от некорректного использования. В Java инкапсуляция достигается с помощью модификаторов доступа (private, protected, public).

### Уникальный факт о Java и ООП

Факт: Один из уникальных аспектов Java в контексте ООП - это то, что Java является строго объектно-ориентированным языком, в котором все коды должны быть частью классов, кроме примитивных типов (int, char, boolean и т.д.). Это означает, что каждый элемент программы, который выполняет действия, должен быть организован в виде классов и объектов, что способствует улучшению организационной структуры и поддерживаемости кода. Java также предоставляет возможности для создания интерфейсов, которые обеспечивают гибкость и разнообразие в организации полиморфного поведения.

# Семинар 3

### 1. KISS (Keep It Simple, Stupid)  
Определение:  
Принцип "Делай проще" призывает создавать системы максимально простыми и понятными. Усложнение решений ведет к росту ошибок и затрудняет поддержку.  

Пример из жизни:  
Не добавляйте сложный механизм для открывания двери, если можно использовать обычную ручку.  

Пример кода:  
// Плохо (зачем здесь цикл?)
public int add(int a, int b) {
    int result = 0;
    for (int i = 0; i < b; i++) {
        result += a;
    }
    return result;
}

// Хорошо
public int add(int a, int b) {
    return a + b;
}

---

### 2. DRY (Don't Repeat Yourself)  
Определение:  
Принцип "Не повторяйся" предполагает исключение дублирования кода или логики.  

Пример из жизни:  
Не записывайте одно и то же напоминание в календарь вручную каждый день – используйте повторяющиеся события.  

Пример кода:  
// Плохо
public double calculateAreaOfCircle(double radius) {
    return 3.14159 * radius * radius;
}

public double calculateCircumferenceOfCircle(double radius) {
    return 2 * 3.14159 * radius;
}

// Хорошо
public class CircleUtils {
    private static final double PI = 3.14159;

    public double calculateArea(double radius) {
        return PI * radius * radius;
    }

    public double calculateCircumference(double radius) {
        return 2 * PI * radius;
    }
}

---

### 3. YAGNI (You Aren't Gonna Need It)  
Определение:  
Принцип "Тебе это не понадобится" говорит, что не нужно добавлять функциональность, пока она действительно не потребуется.  

Пример из жизни:  
Не стройте второй этаж дома, если не планируете его использовать в ближайшие годы.  

Пример кода:  
// Плохо (лишний код)
public void generateReport(boolean detailed) {
    if (detailed) {
        // ... дополнительная логика для детализации
    }
    System.out.println("Simple report generated.");
}

// Хорошо (если детализация не требуется)
public void generateReport() {
    System.out.println("Simple report generated.");
}

---

### 4. BDUF (Big Design Up Front)  
Определение:  
Подход разработки, при котором вся архитектура и проектирование выполняются заранее, перед началом реализации.  

Пример из жизни:  
Перед строительством дома разрабатывают подробный проект с расчетами и чертежами.  

Пример:  
Разработка сложной системы, где заранее детально прорабатываются все модули, их взаимодействие и архитектура.  

---

### 5. SOLID  
Определение:  
SOLID – это пять принципов объектно-ориентированного проектирования:  

1. S (Single Responsibility Principle): Класс должен иметь только одну причину для изменения.  
   Пример:  
      class UserValidator {
       public boolean isValid(String username) {
           // Проверка имени пользователя
           return username != null && !username.isEmpty();
       }
   }
   

2. O (Open/Closed Principle): Класс должен быть открыт для расширения, но закрыт для изменения.  
   Пример:  
      interface Shape {
       double area();
   }
   class Circle implements Shape {
       public double area() {
           return Math.PI * radius * radius;
       }
   }
   

3. L (Liskov Substitution Principle): Объекты подклассов должны быть заменяемы объектами базового класса.  
   Пример:  
      class Rectangle { /* реализация */ }
   class Square extends Rectangle { /* корректная реализация */ }
   

4. I (Interface Segregation Principle): Интерфейсы должны быть узкоспециализированными, а не универсальными.  
   Пример:  
      interface Printer {
       void print();
   }
   interface Scanner {
       void scan();
   }
   

5. D (Dependency Inversion Principle): См. предыдущий пример с DIP.  

---

### 6. APO (Aspect-Oriented Programming)  
Определение:  
Методология программирования, в которой акцент делается на аспектах – перекрестных задачах (например, логирование, безопасность), которые выделяются из основной логики.  

Пример:  
Логирование можно вынести в отдельный аспект, чтобы не переписывать его во всех методах:  
@Aspect
class LoggingAspect {
    @Before("execution(* com.example.*.*(..))")
    public void logMethodCall() {
        System.out.println("Method called");
    }
}

---

### 7. Бритва Оккама  
Определение:  
Принцип гласит, что не стоит умножать сущности без необходимости. Выбирайте самое простое из возможных решений.  

Пример из жизни:  
Если можно добраться до пункта назначения на велосипеде, не нужно покупать вертолет.  

Пример в программировании:  
Не создавайте сложные алгоритмы там, где подойдет готовое библиотечное решение.  

---

### 8. Stream API (Java)  
Определение:  
Stream API – это инструмент для работы с потоками данных в Java, позволяющий выполнять операции над коллекциями и массивами.  

Основные методы:  
- filter: фильтрация элементов  
- map: преобразование элементов  
- sorted: сортировка  
- collect: сбор данных в коллекцию  
- forEach: выполнение действия для каждого элемента  

Пример кода:  
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Bob");
        
        List<String> uniqueNames = names.stream()
                                        .filter(name -> name.startsWith("B"))
                                        .distinct()
                                        .sorted()
                                        .collect(Collectors.toList());
        
        uniqueNames.forEach(System.out::println); // Bob
    }
}

---

### 9. Уникальный факт  
Связь SOLID и Stream API:  
Принципы SOLID упрощают использование Stream API. Например, соблюдение SRP (одна ответственность) позволяет более эффективно разбивать логику на потоки и модули, делая код проще и гибче.

# Семинар 3

1. Service Locator
Определение:
Service Locator (Локатор сервисов) — это паттерн, который предоставляет единое место (реестр) для получения экземпляров нужных объектов (сервисов). Вместо прямой инициализации нужного класса, вы запрашиваете его у «локатора», который знает, как создавать или хранить эти объекты.

Пример из жизни:
Представьте, что у вас есть телефонная книга (Service Locator). Вам не нужно помнить все номера телефонов (зависимости). Вы просто открываете книгу и находите нужный контакт. Но если книга слишком разрастётся, то поиск может стать затруднительным, а вы останетесь завязанными на эту «книгу».

Пример кода (упрощённый):

```java
public class ServiceLocator {
    private static final Map<String, Object> services = new HashMap<>();

    public static <T> T getService(String key, Class<T> type) {
        return type.cast(services.get(key));
    }

    public static <T> void registerService(String key, T service) {
        services.put(key, service);
    }
}

// Где-то в коде:
ServiceLocator.registerService("emailService", new EmailServiceImpl());
EmailService emailService = ServiceLocator.getService("emailService", EmailService.class);
emailService.send("hello@example.com", "Привет!");
```
2. DIP (Dependency Inversion Principle)
Определение:
Принцип инверсии зависимостей гласит, что:

Модули верхнего уровня не должны зависеть от модулей нижнего уровня. Оба уровня должны зависеть от абстракций.
Абстракции не должны зависеть от деталей; детали должны зависеть от абстракций.
Пример из жизни:
Розетка (абстракция) и вилка (конкретная реализация). Мы подключаемся к стандартной розетке (интерфейсу), поэтому производитель чайника или компьютера лишь соблюдает этот стандарт (абстракцию), а не меняет домовую проводку (конкретику).

Пример кода:

```java
public interface NotificationSender {
    void send(String message);
}

public class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        // Код для отправки email
    }
}

public class NotificationService {
    private final NotificationSender sender;

    // Зависимость передается через интерфейс (абстракцию)
    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notify(String message) {
        sender.send(message);
    }
}
```
3. IoC (Inversion of Control)
Определение:
Inversion of Control (Инверсия управления) — это концепция, при которой вы передаёте контроль за созданием и управлением зависимостей (объектов) внешнему контейнеру или фреймворку. Вместо того чтобы класс сам создавал нужные ему объекты, фреймворк (например, Spring) «вкалывает» (inject) зависимости в класс.

Пример из жизни:
«Принцип Голливуда» – «Не звоните нам, мы сами вам позвоним». Это значит, что ваш код не контролирует, когда именно создаются зависимости; вы просто пишете логику, а фреймворк сам решает, когда и как это произойдёт.

Пример кода (Spring):

```java
@Service
public class OrderService {
    private final PaymentService paymentService;

    // Вместо new PaymentService() Spring сам "внедрит" зависимость
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```
4. Singleton
Определение:
Singleton (Одиночка) — это паттерн, который гарантирует, что у класса есть только один экземпляр во всём приложении и предоставляет к нему глобальную точку доступа.

Пример из жизни:
Президент (в идеале) — в стране только один президент, который выполняет уникальные функции и к которому все обращаются (хотя в реальном мире может быть сложнее, но образ понятен).

Пример кода (классический):

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {
        // Приватный конструктор
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```
5. Prototype
Определение:
Prototype (Прототип) — это паттерн, который позволяет копировать уже существующие объекты, не привязываясь к конкретным классам. То есть вы имеете «прототип» и можете тиражировать его копии.

Пример из жизни:
3D-печать: у вас есть 3D-модель (прототип), и вы можете распечатать (клонировать) столько копий, сколько нужно, без создания заново с нуля.

Пример кода (упрощённый):

```java
public class Shape implements Cloneable {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
// Где-то в коде
Shape original = new Shape("Red");
Shape copy = original.clone();
```
6. Юнит-тесты (JUnit)
Определение:
JUnit — это один из наиболее популярных фреймворков для модульного тестирования в Java. Позволяет легко писать тесты, проводить их запуск и проверять корректность работы методов/классов.

Пример кода (JUnit 5):

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        int result = calculator.add(2, 3);
        Assertions.assertEquals(5, result, "Сложение должно возвращать 5");
    }
}
```
7. Mockito (mock + spy)
Определение:

Mockito — библиотека для создания фейковых объектов (mock) и шпионов (spy), чтобы изолировать тестируемый класс от реальных зависимостей.
Mock — имитация объекта, где все методы «заглушки» и возвращают запрограммированные ответы.
Spy — частично реальный объект, в котором можно переопределять (заглушать) только некоторые методы, сохраняя поведение остальных.
Пример кода (упрощённый):

```java
import static org.mockito.Mockito.*;

public class UserServiceTest {
    
    @Test
    void testUserRegistration() {
        // Создаем mock зависимости
        EmailService emailServiceMock = mock(EmailService.class);
        when(emailServiceMock.send(anyString(), anyString())).thenReturn(true);
        
        UserService userService = new UserService(emailServiceMock);
        
        userService.register("user@example.com");
        
        verify(emailServiceMock, times(1))
               .send(eq("user@example.com"), anyString());
    }

    @Test
    void testSpyExample() {
        List<String> realList = new ArrayList<>();
        List<String> spyList = spy(realList);

        spyList.add("Hello");
        // при spy реально вызывается метод add, но мы можем переопределить некоторые методы
        when(spyList.size()).thenReturn(100);

        System.out.println(spyList.size()); // Выведет 100, хотя реально в списке 1 элемент
    }
}
```
8. @SpringBootTest
Определение:
Аннотация SpringBootTest запускает весь контекст Spring Boot для теста. Это значит, что вы получаете полноценное приложение со всеми бинами и конфигурациями, как при реальном запуске, но внутри тестовой среды.

Пример кода:

```java
@SpringBootTest
class ApplicationIntegrationTest {

    @Test
    void contextLoads() {
        // Проверка, что контекст вообще поднялся
    }
}
```
9. @Autowired
Определение:
Аннотация @Autowired в Spring указывает, что нужная зависимость должна быть «автоматически связана» (инжектирована) контейнером Spring. Контейнер определяет, какой бин подходит по типу или по имени и внедряет его.

Пример кода:

```java
@Component
public class OrderService {
    
    @Autowired
    private PaymentService paymentService;

    public void createOrder() {
        paymentService.pay();
    }
}
```
10. @Component
Определение:
Аннотация @Component говорит Spring о том, что данный класс является «бином», который нужно подхватить и хранить в контейнере. Это базовая аннотация, от которой наследуются более специализированные (@Service, @Repository, @Controller).

Пример кода:

```java
@Component
public class PaymentService {
    public void pay() {
        System.out.println("Выполняем оплату...");
    }
}
```

+1 Уникальный факт
Исторически паттерн Service Locator активно использовался в старых Java EE приложениях, но со временем в большинстве проектов его стали заменять полноценными IoC-контейнерами (Spring, CDI и т.д.), потому что Service Locator иногда приводил к тому, что код становился «запутанным» и зависимым от «глобальной» точки доступа. Зато в некоторых играх (например, в архитектуре игровых движков) Service Locator до сих пор используется очень активно, чтобы управлять ресурсами (звуками, текстурами) во время игрового процесса.

# Семинар 5


## Порождающие паттерны в Java

Порождающие (creational) паттерны — это группа шаблонов проектирования, которые упрощают и структурируют процесс создания объектов в программе. Они помогают скрыть детали создания сложных объектов и позволяют гибко подменять одну реализацию другой, не изменяя остальной код. Благодаря этому достигается более лёгкая поддержка и расширяемость приложения.

---

## 1. Singleton (Одиночка)

**Определение:** Паттерн, который гарантирует существование только одного экземпляра класса и предоставляет глобальную точку доступа к этому экземпляру.

**Пример из жизни:** Представьте себе центральный сервер авторизации, который должен быть в единственном экземпляре, чтобы все запросы шли через него.

**Пример кода на Java:**
```java
public class Singleton {
    // Единственный экземпляр класса
    private static volatile Singleton instance;

    // Закрытый конструктор, чтобы запретить создание экземпляров снаружи
    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("Выполняется метод единственного экземпляра");
    }
}
```
## 2. Factory (Фабрика)
Определение: Предоставляет способ создать объекты без указания конкретных классов, т.е. в зависимости от входных данных фабрика возвращает разные объекты, но с единым интерфейсом.

Пример из жизни: Кондитерская фабрика, где по запросу «хочу сладкое» вам могут выдать любой вид десерта (конфету, печенье, торт), но вы точно знаете, что это десерт.

Пример кода на Java:

```java
// Общий интерфейс продукта
interface Dessert {
    void taste();
}

// Конкретные продукты
class Candy implements Dessert {
    @Override
    public void taste() {
        System.out.println("Конфета: сладкая и маленькая!");
    }
}

class Cake implements Dessert {
    @Override
    public void taste() {
        System.out.println("Торт: сладкий и большой!");
    }
}

// Фабрика
class DessertFactory {
    public static Dessert createDessert(String type) {
        switch (type) {
            case "candy":
                return new Candy();
            case "cake":
                return new Cake();
            default:
                throw new IllegalArgumentException("Неизвестный тип десерта");
        }
    }
}

// Пример использования
class MainFactoryExample {
    public static void main(String[] args) {
        Dessert dessert1 = DessertFactory.createDessert("candy");
        Dessert dessert2 = DessertFactory.createDessert("cake");
        
        dessert1.taste();
        dessert2.taste();
    }
}
```
## 3. Factory Method (Фабричный метод)
Определение: Предоставляет интерфейс создания объектов, позволяя подклассам решать, какой класс инстанцировать. Таким образом, фабричный метод делегирует создание объектов подклассам.

Пример из жизни: Представьте компанию по доставке товаров. У них есть разные филиалы (подклассы), каждый из которых знает, как создать «посылку» нужного типа (обычная, хрупкая, срочная).

Пример кода на Java:

```java
// Общий продукт
interface Package {
    void deliver();
}

// Конкретные продукты
class StandardPackage implements Package {
    @Override
    public void deliver() {
        System.out.println("Доставка стандартной посылки");
    }
}

class FragilePackage implements Package {
    @Override
    public void deliver() {
        System.out.println("Доставка хрупкой посылки");
    }
}

// Абстрактный класс, содержащий фабричный метод
abstract class DeliveryService {
    // Фабричный метод
    protected abstract Package createPackage();

    public void sendPackage() {
        Package aPackage = createPackage();
        aPackage.deliver();
    }
}

// Конкретные реализации фабричного метода
class StandardDeliveryService extends DeliveryService {
    @Override
    protected Package createPackage() {
        return new StandardPackage();
    }
}

class FragileDeliveryService extends DeliveryService {
    @Override
    protected Package createPackage() {
        return new FragilePackage();
    }
}

// Пример использования
class MainFactoryMethodExample {
    public static void main(String[] args) {
        DeliveryService service1 = new StandardDeliveryService();
        DeliveryService service2 = new FragileDeliveryService();

        service1.sendPackage();
        service2.sendPackage();
    }
}
```
## 4. Abstract Factory (Абстрактная фабрика)
Определение: Предоставляет интерфейс для создания семейств взаимосвязанных или взаимозависимых объектов, не уточняя их конкретных классов.

Пример из жизни: Допустим, вам нужно создать «семейство» компонентов интерфейса пользователя (кнопки, поля ввода, списки), но для разных операционных систем (Windows, macOS, Linux). Каждая ОС имеет собственную реализацию, но все компоненты совместимы друг с другом в рамках одной системы.

Пример кода на Java:

```java
// Семейство продуктов: Кнопка и Чекбокс
interface Button {
    void click();
}

interface Checkbox {
    void check();
}

// Конкретные продукты для Windows
class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("Нажата Windows-кнопка");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void check() {
        System.out.println("Выбран Windows-чекбокс");
    }
}

// Конкретные продукты для Mac
class MacButton implements Button {
    @Override
    public void click() {
        System.out.println("Нажата Mac-кнопка");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void check() {
        System.out.println("Выбран Mac-чекбокс");
    }
}

// Абстрактная фабрика
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Конкретные фабрики
class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// Пример использования
class MainAbstractFactoryExample {
    public static void main(String[] args) {
        GUIFactory factory;

        // Можно выбрать нужную фабрику в зависимости от платформы
        String osName = "Windows";

        if (osName.equalsIgnoreCase("Windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacFactory();
        }

        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        button.click();
        checkbox.check();
    }
}
```
## 5. Builder (Строитель)
Определение: Отделяет процесс конструирования сложного объекта от его представления, так что в результате одного и того же процесса конструирования могут получаться разные представления.

Пример из жизни: Постройка дома, где у вас есть план (Builder), и в зависимости от него вы можете построить деревянный дом, кирпичный дом и т.д.

Пример кода на Java:

```java
// Сложный объект
class House {
    private String walls;
    private String roof;
    private String interior;

    public void setWalls(String walls) {
        this.walls = walls;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    @Override
    public String toString() {
        return "House{" +
               "walls='" + walls + '\'' +
               ", roof='" + roof + '\'' +
               ", interior='" + interior + '\'' +
               '}';
    }
}

// Общий интерфейс строителя
interface HouseBuilder {
    void buildWalls();
    void buildRoof();
    void buildInterior();
    House getResult();
}

// Конкретный строитель
class WoodenHouseBuilder implements HouseBuilder {
    private House house = new House();

    @Override
    public void buildWalls() {
        house.setWalls("Деревянные стены");
    }

    @Override
    public void buildRoof() {
        house.setRoof("Деревянная крыша");
    }

    @Override
    public void buildInterior() {
        house.setInterior("Деревянный интерьер");
    }

    @Override
    public House getResult() {
        return house;
    }
}

// Директор, управляющий процессом строительства
class HouseDirector {
    private HouseBuilder builder;

    public HouseDirector(HouseBuilder builder) {
        this.builder = builder;
    }

    public void constructHouse() {
        builder.buildWalls();
        builder.buildRoof();
        builder.buildInterior();
    }
}

// Пример использования
class MainBuilderExample {
    public static void main(String[] args) {
        HouseBuilder builder = new WoodenHouseBuilder();
        HouseDirector director = new HouseDirector(builder);

        director.constructHouse();
        House house = builder.getResult();
        System.out.println(house);
    }
}
```
## 6. Prototype (Прототип)
Определение: Позволяет копировать объекты, не вдаваясь в подробности их реализации. При этом копия может быть создана намного быстрее, чем объект «с нуля», особенно если объект сложный.

Пример из жизни: Клонирование чертежей или дизайн-макетов: вы берёте шаблон (прототип) и создаёте новые копии, подстраивая их при необходимости.

Пример кода на Java:

```java
class Shape implements Cloneable {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    // Глубокая/поверхностная копия может различаться в зависимости от структуры класса
    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone(); // поверхностная копия
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported!", e);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

// Пример использования
class MainPrototypeExample {
    public static void main(String[] args) {
        Shape originalShape = new Shape("Красный");
        Shape clonedShape = originalShape.clone();

        System.out.println("Оригинал: " + originalShape.getColor());
        System.out.println("Копия: " + clonedShape.getColor());

        // Изменяем цвет клона
        clonedShape.setColor("Синий");
        System.out.println("После изменения цвета клона:");
        System.out.println("Оригинал: " + originalShape.getColor());
        System.out.println("Копия: " + clonedShape.getColor());
    }
}
```
## +1 Уникальный факт
Факт: В ранних версиях Java (до Java 5) многие программисты для реализации паттерна Singleton применяли двойную проверку (double-checked locking), которая могла работать нестабильно из-за особенностей модели памяти Java. Начиная с Java 5, volatile и улучшенная модель памяти гарантируют корректность такой реализации.

Таким образом, порождающие паттерны помогают упростить и структурировать логику создания объектов, скрывая избыточные детали реализации, делая систему более гибкой и расширяемой.
