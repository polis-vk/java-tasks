package ru.mail.polis.homework.io.objects;

public enum MoveType {
//    RUN("run"),
//    SWIM("swim"),
//    FLY("fly"),
//    JUMP("jump"),
//    CRAWL("crawl");

    RUN,
    SWIM,
    FLY,
    JUMP,
    CRAWL;

//    private final String type;
//
//    MoveType(String type) {
//        this.type = type;
//    }

    @Override
    public String toString() {
        return "MoveType{" +
                "type='" + this +
                '}';
    }
}
