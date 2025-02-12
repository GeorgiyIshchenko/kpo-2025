# Zoo

По ходу данного .md файла будут описаны решения принятые по ходу выполнения
домашнего задания.

## Сущности

Базовые интерфейсы и классы с описанием, которые потребовались в реализации

### Интрфейсы

#### 1. `interface IAlive`

```java
/**
 * Интерфейс определяет живое существо, которое употребляет Food кг в день.
 */
public interface IALive { ... }
```

#### 2. `interface IInventory`

```java
/**
 * Интерфейс определяет сущность как предмет инвентаря. Каждый предмет инвентаря должен иметь Number.
 */
public interface IInventory { ... }
```

> Тут прослеживается очевидное действие буквы I - Interface Segregation Principle (Принцип разделения интерфейсов).

#### 3. `interface IZoo`

согласно ТЗ:

```java
public interface IZoo {
    Map<String, Integer> getFoodMap();
    int getFoodSum();
    List<Animal> getPettingAnimals();
}
```

наш зоопарк должен реализовывать следующий набор функций, реализация уже в `HseZoo`

### 4. `interface Clinic`

> Интерфейс клиники. Внутри функции должно приниматься решение о принятии
животного в зоопарк. `SimpleClinic` принимает решение в зависимости от
foodPerDay животного. Ничего не мешает написать другую клинику и использовать
ее `AnimalInventoryProvider`, это реализация буквы D - Dependency Inversion
Principle (Принцип инверсии зависимостей)

```java
public interface IClinic {

    boolean isSuitable(Animal animal);

}
```

### Классы

#### 1. `class Animal`
```java
/**
 * Базовый класс животного.
 */
public class Animal implements IALive {

    @Override
    public Integer getFoodPerDay() {
        return foodPerDay;
    }

    @Override
    public void setFoodPerDay(Integer foodPerDay) {
        this.foodPerDay = foodPerDay;
    }

    Integer foodPerDay = 0;
}
```
#### 2. `class Herbo`
```java
/**
 * Базовый класс для всех травоядных. Добавляет свойство Kindness к животному.
 */
public class Herbo extends Animal {

    /**
     * Устанавливает Kindness.
     *
     * @param kindness - уровень доброты, возможные значения от 0 до 10
     */
    void setKindness(int kindness) {
    }

    ...

}
```
#### 3. `class Predator`
```java
/**
 * Базовый класс для всех хищников.
 */
public class Predator extends Animal {
}
```
#### 4. Реализации
"Пустые" классы животных.

`extends Herbal`:
- `class Monkey`
- `class Rabbit`

`extends Predator`:
- `class Tiger`
- `class Wolf`

> Тут прослеживается L - Liskov Substitution Principle (Принцип подстановки Лисков). По сути, в программе нет разницы между Tiger, Predator и Animal.

## Фабрики

> Я решил сделать интерфейс фабрики, которая будет создавать объект по
строке(маппинг из строки в тип). От этой абстрактной фабрики будут наследоваться
вполне конкретные - для травоядных и хищных животных, а также для инвентаря.

1. `class HerboFactory`
2. `class PredatorFactory`
3. `class ThingFactory`

## Сервисы

1. `public interface IInventoryProvider`

- Generic функция - addInventory, в зависимости от добавляемого объекта

- Функция получения инвентаря.

2. `class AnimalInventoryProvider implements IInventoryProvider`

**DI-container**

- Зависит от клиники, потому что нельзя добавить животное просто так,
  без ее разрешения.

- Фильтрует объекты внутри себя по типу: Thing, Herbo, Animal и возвращает уже готовые данные

# Вывод

- Написано много тестов. Покрыто 80% кода, тк я не покрывал тестами консольные ввод-вывод. Без них, порядка 100%

- Много интерфейсов, классов буква `S - Single Responsibility Principle (Принцип единственной ответственности)` присутствует.

- Интерфейсы имеют простую структуру и не зависят от реализаций, а реализации в свою очередь сложные. Тут всего 2 слоя: "абстракция-реализация", поэтому `O - Open/Closed Principle (Принцип открытости/закрытости)` применить легко.

