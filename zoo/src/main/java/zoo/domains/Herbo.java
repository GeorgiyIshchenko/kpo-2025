package zoo.domains;

import lombok.Getter;

/**
 * Базовый класс для всех травоядных. Добавляет свойство Kindness к животному.
 */
public class Herbo extends Animal {

    public Herbo(Integer foodPerDay, int kindness) {
        super(foodPerDay);
        this.kindness = kindness;
    }

    /**
     * Устанавливает Kindness.
     *
     * @param kindness - уровень доброты, возможные значения от 0 до 10
     */
    void setKindness(int kindness) {
        if (kindness > 10 || kindness < 0) {
            throw new IllegalArgumentException("Kindness must be between 0 and 10");
        }
        this.kindness = kindness;
    }

    @Getter
    Integer kindness;

}
