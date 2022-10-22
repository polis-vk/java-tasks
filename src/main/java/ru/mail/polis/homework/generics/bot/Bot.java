package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot {

    private final Map<Class<? extends State>, StateHandler<? extends State>> stateHandlersMap;

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     *
     * 1 тугрик
     */
    public Bot(List<StateHandler<? extends State>> handlers) {
        stateHandlersMap = new HashMap<>();
        for (StateHandler<? extends State> stateHandler : handlers) {
            stateHandlersMap.put(stateHandler.getHandlingStateClass(), stateHandler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     *
     * 1 тугрик
     */
    public void handleState(State state) {
        stateHandlersMap.get(state.getClass()).handle();
    }
}
