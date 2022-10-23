package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot {

    // Создадим Map с ключом - некоторый тип, который умеет обрабатывать handler, наследуемый от State.
    // Значение - handler, умеющий работать с этим типом.
    Map<Class<? extends State>, StateHandler<? extends State>> handlerMap;

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     * <p>
     * 1 тугрик
     */
    public Bot(List<StateHandler<? extends State>> handlers) {

        // Инициилизируем Map. Проходимся по всему списку handlers и записываем типы и сами объекты.
        handlerMap = new HashMap<>();
        for (StateHandler<? extends State> obj : handlers) {
            handlerMap.put(obj.getHandlingStateClass(), obj);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     * <p>
     * 1 тугрик
     */

    // Если передан null - выбрасываем исключение. Дальше определяем тип конкретного State и ищем подходящий handler
    // для такого типа. Вызываем метод handle для него.
    public void handleState(State state) {
        if (state == null) {
            throw new NullPointerException();
        }
        handlerMap.get(state.getClass()).handle();
    }
}
