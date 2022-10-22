package ru.mail.polis.homework.generics.bot;

import ru.mail.polis.homework.generics.bot.handler.StateHandler;
import ru.mail.polis.homework.generics.bot.state.State;

import java.util.HashMap;
import java.util.List;

public class Bot {
    private HashMap<Class<? extends State>, StateHandler<? extends State>> stateHandlerMap;
    /**
     * Конструктор бота, которому на вход подаются хэндлеры состояний.
     * Необходимо как-то сохранить эти хэндлеры так, чтобы потом можно было вызвать нужный хэндлер из метода handleState
     *
     * 1 тугрик
     */
    public Bot(List<StateHandler<? extends State>> handlers) {
        for (StateHandler<? extends State> handler :
                handlers) {
            stateHandlerMap.put(handler.getHandlingStateClass(), handler);
        }
    }

    /**
     * Вызывает хэндлер, предназначенный для переданного State
     *
     * 1 тугрик
     */
    public void handleState(State state) {
        stateHandlerMap.get(state.getClass()).handle();
    }
}
