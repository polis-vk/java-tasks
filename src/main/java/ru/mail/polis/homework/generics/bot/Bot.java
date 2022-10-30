package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot {

    private Map<Class<? extends State>, StateHandler<? extends State>> handlerMap;

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     * <p>
     * 1 тугрик
     */
    public Bot(List<StateHandler<? extends State>> handlers) {
        handlerMap = new HashMap<>();
        for (StateHandler handler : handlers) {
            handlerMap.put(handler.getHandlingStateClass(), handler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     * <p>
     * 1 тугрик
     */
    public void handleState(State state) {
        handlerMap.get((state.getClass())).handle();
    }
}
