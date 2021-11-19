package de.techfak.gse.lwalkenhorst.event;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EventSupport {

    private final Map<Class<? extends Event>, Set<Consumer<Event>>> map = new HashMap<>();

    public void fireEvent(final Event event) {
        Objects.requireNonNull(map.get(event.getClass()))
                .forEach(eventConsumer -> eventConsumer.accept(event));
    }

    private void registerListener(final Class<? extends Event> clazz, Consumer<Event> handler) {
        if (map.containsKey(clazz)) {
            Objects.requireNonNull(map.get(clazz)).add(handler);
        } else {
            final Set<Consumer<Event>> handlers = new HashSet<>();
            handlers.add(handler);
            map.put(clazz, handlers);
        }
    }

    @SuppressWarnings("unchecked")
    public void registerListener(final EventListener listener) {
        List<Method> methods = Arrays.stream(listener.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(EventHandler.class))
                .collect(Collectors.toList());
        for (Method method : methods) {
             final Class<? extends Event>[] params = (Class<? extends Event>[]) method.getParameterTypes();
             if (params.length != 1) {
                 continue;
             }

             final Consumer<Event> handler = event -> {
                 try {
                     method.setAccessible(true);
                     method.invoke(listener, event);
                 } catch (IllegalAccessException | InvocationTargetException e) {
                     e.printStackTrace();
                 }
             };
            registerListener(params[0], handler);

        }
    }
}
