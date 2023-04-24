package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private final Robot bot;
    private boolean isConnected;

    public Connection(Robot bot) {
        this.bot = bot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws NoConnectionException {
        if (!isConnected) {
            throw new NoConnectionException("There is no connection with the bot");
        }
        bot.move(x, y);
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
