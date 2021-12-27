package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Операнд для вычислений.
 * Пример:
 * 10 + 23 - 123123 + sin(123) / |-123213| + tan(123.94) * e^3 / ln(25) =
 * Op(10, PLUS), Op(23, MINUS), Op(123123, PLUS), Op(SIN, 123, DIVIDE), Op(ABS, -123213, PLUS),
 * Op(TAN, 123.94, MULT), Op(EXP, 3, DIVIDE), Op(LN, 25, EQUALS)
 */

@JsonAutoDetect
public class Operand {
    private final OperandType operationFirst;
    private final double a;
    private final OperandType operationSecond;
    private int position;

    @JsonCreator
    public Operand(@JsonProperty("operationFirst") OperandType operationFirst,  @JsonProperty("a") double a,
                   @JsonProperty("operationSecond") OperandType operationSecond) {
        this.operationFirst = operationFirst;
        this.a = a;
        this.operationSecond = operationSecond;
    }

    public OperandType getOperationFirst() {
        return operationFirst;
    }

    public double getA() {
        return a;
    }

    public OperandType getOperationSecond() {
        return operationSecond;
    }

    public String serialization() {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(writer, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
