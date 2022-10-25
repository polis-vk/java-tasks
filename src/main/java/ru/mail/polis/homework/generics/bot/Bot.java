package ru.mail.polis.homework.generics.bot;

import java.util.HashMap;
import java.util.List;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

public class Bot {
    private HashMap<Class, StateHandler> stateHandlerHashMap;

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     * <p>
     * 1 тугрик
     */
    public Bot(List<StateHandler> handlers) {
        stateHandlerHashMap = new HashMap<>();
        for (StateHandler handler : handlers) {
            stateHandlerHashMap.put(handler.getHandlingStateClass(), handler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     * <p>
     * 1 тугрик
     */
    public void handleState(State state) {
        stateHandlerHashMap.get(state).handle();
    }
}
