package comp1110.ass2;

/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 18/5/2023
 * Modified by Zeqi Gao on 18/5/2023
 */
public enum Resource {
    Coconut("C"),
    Bamboo("B"),
    Water("W"),
    PreciousStones("P"),
    Statuette("S");

    Resource(String c) {
        this.c = c;
    }

    private String c;
    public String getC() {
        return c;
    }

    public static Resource fromChar(String c) {
        return switch (c.toUpperCase()) {
            case "C" -> Coconut;
            case "B" -> Bamboo;
            case "W" -> Water;
            case "P" -> PreciousStones;
            case "S" -> Statuette;
            default -> null;
        };
    }

    public static Resource fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'C' -> Coconut;
            case 'B' -> Bamboo;
            case 'W' -> Water;
            case 'P' -> PreciousStones;
            case 'S' -> Statuette;
            default -> null;
        };
    }

    public static Resource fromInt(int i) {
        return switch (i) {
            case 0 -> Coconut;
            case 1 -> Bamboo;
            case 2 -> Water;
            case 3 -> PreciousStones;
            case 4 -> Statuette;
            default -> null;
        };
    }

    public static int toInt(Resource r) {
        return switch (r) {
            case Coconut -> 0;
            case Bamboo -> 1;
            case Water -> 2;
            case PreciousStones -> 3;
            case Statuette -> 4;
        };
    }

    public static char toChar(Resource r) {
        return switch (r) {
            case Coconut -> 'C';
            case Bamboo -> 'B';
            case Water -> 'W';
            case PreciousStones -> 'P';
            case Statuette -> 'S';
        };
    }
}
