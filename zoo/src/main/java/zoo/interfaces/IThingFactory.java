package zoo.interfaces;

import zoo.domains.Thing;

/**
 * Интерфейс фабрики инвентаря.
 */
public interface IThingFactory<T> {

    public Thing createThing(String entity, T params);

}
