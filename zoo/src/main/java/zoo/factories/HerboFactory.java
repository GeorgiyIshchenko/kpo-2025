package zoo.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import org.springframework.stereotype.Component;
import zoo.annotations.HerboRegister;
import zoo.domains.Animal;
import zoo.domains.Herbo;
import zoo.domains.Monkey;
import zoo.domains.Rabbit;
import zoo.exceptions.ZooException;
import zoo.interfaces.IAnimalFactory;


/**
 * Фабрика травоядных.
 */
@SupportedAnnotationTypes("HerboFactory")
@Component
public class HerboFactory extends AbstractProcessor implements IAnimalFactory {

    public static Map<String, Class<? extends Herbo>> herboRegistry = Map.of();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HerboRegister.class)) {
            String factoryName = element.getClass().getName();
            try {
                Class<?> herboChildClass = Class.forName(factoryName);
                herboRegistry.put(factoryName, herboChildClass.asSubclass(Herbo.class));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "Found HerboRegistry annotation on class: " + element.getSimpleName() + " with value: " + factoryName);
        }
        return true;
    }

    @Override
    public Animal createAnimal(String entity, Integer foodPerDay) {
        try {
            Class<? extends Herbo> herboChildClass = herboRegistry.get(entity);
            Class<?>[] parameterTypes = {Integer.class, Integer.class};
            Constructor<? extends Herbo> ctor = herboChildClass.getConstructor(parameterTypes);
            return ctor.newInstance(foodPerDay, 0);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // throw new ZooException(e.getMessage());
            return null;
        }
    }
}
