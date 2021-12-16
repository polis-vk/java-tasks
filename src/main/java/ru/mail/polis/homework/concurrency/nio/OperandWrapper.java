package ru.mail.polis.homework.concurrency.nio;

import java.io.Serializable;
import java.util.List;

public class OperandWrapper implements Serializable {
    private final int id;
    private final int order;
    private final int size;
    private final List<Operand> operandChunk;
    private final int serverPort;

    public OperandWrapper(int id, int order, int size, List<Operand> operandChunk, int serverPort) {
        this.id = id;
        this.order = order;
        this.size = size;
        this.operandChunk = operandChunk;
        this.serverPort = serverPort;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public int getSize() {
        return size;
    }

    public List<Operand> getOperandChunk() {
        return operandChunk;
    }

    public int getServerPort() {
        return serverPort;
    }
}
