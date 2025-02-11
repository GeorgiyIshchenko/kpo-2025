package zoo.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.springframework.stereotype.Component;
import zoo.domains.Animal;
import zoo.domains.Herbo;
import zoo.domains.Monkey;
import zoo.domains.Rabbit;
import zoo.interfaces.IAnimalFactory;
import zoo.params.HerboParams;


/**
 * Фабрика травоядных.
 */
@Component
public class HerboFactory implements IAnimalFactory<HerboParams> {

    public static Map<String, Class<? extends Herbo>> herboRegistry = Map.of(
            "Rabbit", Rabbit.class,
            "Monkey", Monkey.class
    );

    @Override
    public Animal createAnimal(String entity, HerboParams params) {
        try {
            Class<? extends Herbo> herboChildClass = herboRegistry.get(entity);
            Class<?>[] parameterTypes = {int.class, int.class, int.class};
            Constructor<? extends Herbo> ctor = herboChildClass.getConstructor(parameterTypes);
            return ctor.newInstance(params.number(), params.foodPerDay(), params.kindness());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // throw new ZooException(e.getMessage());
            return null;
        }
    }
}
