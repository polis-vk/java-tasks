package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Bot {

    private Map<Class<? extends State>, StateHandler> stateHandlers = new HashMap<>();

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     *
     * 1 тугрик
     */
    public Bot(List<StateHandler> handlers) {
        for (StateHandler handler : handlers) {
            stateHandlers.putIfAbsent(handler.getHandlingStateClass(), handler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     *
     * 1 тугрик
     */
    public void handleState(State state) {
        if (stateHandlers.containsKey(state.getClass())){
            stateHandlers.get(state.getClass()).handle();
        }
    }
}
