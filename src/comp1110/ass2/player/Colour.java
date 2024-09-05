package comp1110.ass2.player;

/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 18/5/2023
 */
public enum Colour {
    MAGENTA,
    ORANGE,
    NAVY,
    YELLOW;

    public static Colour fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'M' -> MAGENTA;
            case 'O' -> ORANGE;
            case 'N' -> NAVY;
            case 'Y' -> YELLOW;
            default -> null;
        };
    }

    public static Colour fromInt(int i) {
        return switch (i) {
            case 0 -> MAGENTA;
            case 1 -> ORANGE;
            case 2 -> NAVY;
            case 3 -> YELLOW;
            default -> null;
        };
    }

    public static int toInt(Colour colour) {
        return switch (colour) {
            case MAGENTA -> 0;
            case ORANGE -> 1;
            case NAVY -> 2;
            case YELLOW -> 3;
        };
    }
}
