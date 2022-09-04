package christamslights;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.min;

public class Grid {
    private final int height;
    private final int width;

    private Set<Square> turnedOnCords = new HashSet<>();

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getLightNumber() {
        return turnedOnCords.stream()
                .mapToInt(Square::getArea)
                .sum();
    }

    public void turnOn(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        turnOn(Cord.of(topRowLeft, topColLeft), Cord.of(bottomRowRight + 1, bottomColRight + 1));
    }

    public void turnOn(Cord topLeftCorner, Cord bottomRightCorner) {
        turnOn(Square.of(topLeftCorner, bottomRightCorner));
    }

    public void  turnOn(Square square) {
        turnOff(square);
        turnedOnCords.add(square);
    }

    public void turnOff(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        turnOff(Cord.of(topRowLeft, topColLeft), Cord.of(bottomRowRight + 1, bottomColRight + 1));
    }

    public void turnOff(Cord topLeftCorner, Cord bottomRightCorner) {
        turnOff(Square.of(topLeftCorner, bottomRightCorner));
    }

    public void turnOff(Square square) {
        turnedOnCords = turnedOnCords.stream()
                .flatMap(square::intersectWith)
                .collect(Collectors.toSet());
    }

    public void toggle(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        toggle(Cord.of(topRowLeft, topColLeft), Cord.of(bottomRowRight + 1, bottomColRight + 1));
    }

    private void toggle(Cord topLeftCorner, Cord bottomRightCorner) {
        toggle(Square.of(topLeftCorner, bottomRightCorner));
    }

    private void toggle(Square square) {
        turnOn(square);
    }

    static class Square {
        private final Cord topLeft;
        private final Cord bottomRight;

        private Square(Cord topLeft, Cord bottomRight) {
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        static Square of(Cord topLeft, Cord bottomRight) {
            return new Square(topLeft, bottomRight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Square square = (Square) o;
            return Objects.equals(topLeft, square.topLeft) && Objects.equals(bottomRight, square.bottomRight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(topLeft, bottomRight);
        }

        @Override
        public String toString() {
            return "Square{" +
                    "topLeft=" + topLeft +
                    ", bottomRight=" + bottomRight +
                    '}';
        }

        public int getArea() {
            return getHeightLength() * getWidthLength();
        }

        private int getHeightLength() {
            return distance(bottomRight.row, topLeft.row);
        }

        private int getWidthLength() {
            return distance(bottomRight.col, topLeft.col);
        }

        private int distance(int large, int small) {
            return large - small;
        }

        public Stream<Square> intersectWith(Square square) {
            if (isContaining(square)) return Stream.empty();

            if (hasNoIntersectionWith(square)) return Stream.of(square);

            Square intersectionSquare = computeIntersectionSquare(square);
            return square.drop(intersectionSquare);
        }

        private Stream<Square> drop(Square square) {
            return Stream.of(
                    Square.of(topLeft, Cord.of(square.topLeft.row, bottomRight.col)),
                    Square.of(Cord.of(square.bottomRight.row, topLeft.col), bottomRight),
                    Square.of(Cord.of(square.topLeft.row, topLeft.col), Cord.of(square.bottomRight.row, square.topLeft.col)),
                    Square.of(Cord.of(square.topLeft.row, square.bottomRight.col), Cord.of(square.bottomRight.row, bottomRight.col))
            ).filter(inner -> inner.getArea() != 0);
        }

        private boolean isContaining(Square square) {
            return topLeft.row <= square.topLeft.row && square.bottomRight.row <= bottomRight.row
                    && topLeft.col <= square.topLeft.col && square.bottomRight.col <= bottomRight.col;
        }

        private boolean hasNoIntersectionWith(Square square) {
            return square.bottomRight.row <= topLeft.row
                    || square.topLeft.row >= bottomRight.row
                    || square.bottomRight.col <= topLeft.col
                    || square.topLeft.col >= bottomRight.col;
        }

        private Square computeIntersectionSquare(Square square) {
            if (square.topLeft.row < topLeft.row) return computeIntersectionOfHigherWithLower(square, this);

            return computeIntersectionOfHigherWithLower(this, square);
        }

        private static Square computeIntersectionOfHigherWithLower(Square highSquare, Square lowSquare) {
            int intersectionBottomRow = min(highSquare.bottomRight.row, lowSquare.bottomRight.row);
            int intersectionBottomCol = min(highSquare.bottomRight.col, lowSquare.bottomRight.col);
            Cord intersectionBottomRight = Cord.of(intersectionBottomRow, intersectionBottomCol);
            return Square.of(lowSquare.topLeft, intersectionBottomRight);
        }
    }

    public static class Cord {
        private final int row;
        private final int col;

        private Cord(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static Cord of(int row, int col) {
            return new Cord(row, col);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cord cord = (Cord) o;
            return row == cord.row && col == cord.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public String toString() {
            return "Cord{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}
