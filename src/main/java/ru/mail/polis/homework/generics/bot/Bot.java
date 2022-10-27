package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.List;

public class Bot {

    private final HashMap<State, StateHandler<State>> handlersMap = new HashMap<>();

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     * <p>
     * 1 тугрик
     */
    public Bot(List<StateHandler<State>> handlers) {
        for (StateHandler<State> stateHandler : handlers) {
            handlersMap.put(stateHandler.getHandlingStateClass(), stateHandler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     * <p>
     * 1 тугрик
     */
    public void handleState(State state) {
        if (handlersMap.containsKey(state)) {
            handlersMap.get(state).handle();
        }
    }
}
