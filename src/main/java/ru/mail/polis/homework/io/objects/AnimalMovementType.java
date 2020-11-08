package ru.mail.polis.homework.io.objects;

public enum AnimalMovementType {
    FLYING, WALKING, SWIMMING, JUMPING;

    public String getVerb() {
        switch (this) {
            case FLYING:
                return "flies";
            case WALKING:
                return "walks";
            case SWIMMING:
                return "swims";
            case JUMPING:
                return "jumps";
        }

        return "does something weird";
    }
}
