package ru.mail.polis.homework.concurrency.nio;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
public class ServerOperation {

    private final int resultPort;
    private final List<Operand> operands = new ArrayList<>();
    private final int numberOfOperands;
    private final int clientID;
    public final SocketChannel socketChannel;

    private ServerState serverState = ServerState.LOADING;
    private int result;

    ServerOperation(SocketChannel socketChannel, int clientID, int numberOfOperands, int resultPort) {
        this.socketChannel = socketChannel;
        this.clientID = clientID;
        this.numberOfOperands = numberOfOperands;
        this.resultPort = resultPort;
    }

    public void addOperand(byte[] data) {
        operands.add(operandFromBytes(data));
    }

    public List<Operand> getOperands() {
        return operands;
    }

    public ServerState getState() {
        return serverState;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public boolean isCompleted() {
        return numberOfOperands == operands.size();
    }

    public int getResultPort() {
        return resultPort;
    }

    public int getClientID() {
        return clientID;
    }

    private Operand operandFromBytes(byte[] data) {
        OperandType first = data[0] == -4 ? null : OperandType.values()[data[0]];
        byte[] valueBytes = new byte[8];
        System.arraycopy(data, 1, valueBytes, 0, 8);
        double a = readDouble(valueBytes);
        OperandType second = OperandType.values()[data[9]];
        return new Operand(first, a, second);
    }

    private double readDouble(byte[] bytes) {
        long value = ((bytes[0] & 0xFFL) << 56) |
                ((bytes[1] & 0xFFL) << 48) |
                ((bytes[2] & 0xFFL) << 40) |
                ((bytes[3] & 0xFFL) << 32) |
                ((bytes[4] & 0xFFL) << 24) |
                ((bytes[5] & 0xFFL) << 16) |
                ((bytes[6] & 0xFFL) << 8) |
                ((bytes[7] & 0xFFL));
        return Double.longBitsToDouble(value);
    }
}
