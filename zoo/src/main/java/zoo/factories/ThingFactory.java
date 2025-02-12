package zoo.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.springframework.stereotype.Component;
import zoo.domains.*;
import zoo.interfaces.IInventoryFactory;
import zoo.params.ThingParams;

/**
 * Фабрика инвентаря.
 */
@Component
public class ThingFactory implements IInventoryFactory<ThingParams> {

    public static Map<String, Class<? extends Thing>> thingRegistry = Map.of(
            "Computer", Computer.class,
            "Table", Table.class
    );

    @Override
    public Thing createInstance(String entity, ThingParams params, int number) {
        try {
            Class<? extends Thing> predatorChildClass = thingRegistry.get(entity);
            Class<?>[] parameterTypes = {int.class};
            Constructor<? extends Thing> ctor = predatorChildClass.getConstructor(parameterTypes);
            return ctor.newInstance(number);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // throw new ZooException(e.getMessage());
            return null;
        }
    }

}
