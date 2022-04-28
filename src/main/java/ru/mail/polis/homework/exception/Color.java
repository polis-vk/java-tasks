package ru.mail.polis.homework.exception;

public enum Color {

    YELLOW(0),
    RED(1),
    GREEN(2),
    BLUE(3),
    GRAY(4),
    CYAN(5),
    BLACK(6),
    MAGENTA(7);

    private int colorCode;

    Color(int colorCode) {
        this.colorCode = colorCode;
    }

    public int getColorCode() {
        return this.colorCode;
    }

    public static Color getColor(int colorCode) {
        for (Color c : Color.values()) {
            if (c.getColorCode() == colorCode)
                return c;
        }
        return BLACK;
    }
}
