package zoo.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.stereotype.Component;
import zoo.domains.*;
import zoo.interfaces.IAnimalFactory;
import zoo.params.PredatorParams;

/**
 * Фабрика хищников.
 */
@Component
public class PredatorFactory implements IAnimalFactory<PredatorParams> {

    public static Map<String, Class<? extends Predator>> predatorRegistry = Map.of(
            "Tiger", Tiger.class,
            "Wolf", Wolf.class
    );

    @Override
    public Animal createAnimal(String entity, PredatorParams params) {
        try {
            Class<? extends Predator> predatorChildClass = predatorRegistry.get(entity);
            Class<?>[] parameterTypes = {int.class, int.class};
            Constructor<? extends Predator> ctor = predatorChildClass.getConstructor(parameterTypes);
            return ctor.newInstance(params.number(), params.foodPerDay());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // throw new ZooException(e.getMessage());
            return null;
        }
    }

}
