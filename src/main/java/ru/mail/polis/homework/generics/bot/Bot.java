package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Bot {

    private final Map<Class<? extends State>, StateHandler<? extends State>> handlersMap = new HashMap<>();

    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     *
     * 1 тугрик
     */
    public Bot(List<StateHandler<? extends State>> handlers) {
        for (StateHandler<? extends State> a : handlers) {
            handlersMap.put(a.getHandlingStateClass(), a);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     *
     * 1 тугрик
     */
    public void handleState(State state) {
        handlersMap.get(state.getClass()).handle();
    }
}
