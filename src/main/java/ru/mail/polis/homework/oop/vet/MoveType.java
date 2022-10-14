package ru.mail.polis.homework.oop.vet;

/**
 * Возможные виды передвижения животных
 */
public enum MoveType {
    /*
     * Введен дефолтный тип для передвижения на случай, если разработчики не переопределят методы say(), moveType().
     */
    DEFAULT,
    RUN,
    SWIM,
    FLY,
    JUMP,
    CRAWL
}
