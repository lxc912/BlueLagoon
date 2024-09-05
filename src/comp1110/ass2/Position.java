package comp1110.ass2;

import javafx.geometry.Pos;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 16/5/2023
 * Modified by Zeqi Gao on 18/5/2023
 * Modified by Yufei Huang on 19/5/2023
 */
public class Position implements Comparable<Position> {
    final int x;
    final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String positionString) {
        this.x = Integer.parseInt(positionString.split(",")[0]);
        this.y = Integer.parseInt(positionString.split(",")[1]);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public int compareTo(Position o) {
        if (this.x < o.x) {
            return -1;
        } else if (this.x > o.x) {
            return 1;
        } else if (this.y < o.y) {
            return -1;
        } else if (this.y > o.y) {
            return 1;
        }
        return 0;
    }

    public Set<Position> next(int high) {
        HashSet<Position> positions = new HashSet<>();
        if (inRange(x, y + 1, high)) {
            positions.add(new Position(x, y + 1));
        }
        if (inRange(x, y - 1, high)) {
            positions.add(new Position(x, y - 1));
        }
        if (inRange(x - 1, y, high)) {
            positions.add(new Position(x - 1, y));
        }
        if (inRange(x + 1, y, high)) {
            positions.add(new Position(x + 1, y));
        }
        if (x % 2 == 0) {
            if (inRange(x - 1, y + 1, high)) {
                positions.add(new Position(x - 1, y + 1));
            }
            if (inRange(x + 1, y + 1, high)) {
                positions.add(new Position(x + 1, y + 1));
            }
        } else {
            if (inRange(x - 1, y - 1, high)) {
                positions.add(new Position(x - 1, y - 1));
            }
            if (inRange(x + 1, y - 1, high)) {
                positions.add(new Position(x + 1, y - 1));
            }
        }
        return positions;
    }

    public static boolean inRange(int x, int y, int high){
        return x >= 0 && x <= high - 1 && y <= high - 2 + x % 2 && y >= 0;
    }
}
