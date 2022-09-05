package christamslights;

import java.util.Arrays;
import java.util.function.Function;

public class Grid {
    private final int[][] lights;

    private static final int OFF = 0;
    private static final int ON = 1;
    private enum Action {
        TURN_ON(x -> ON),
        TURN_OFF(x -> OFF),
        TOGGLE(x -> ON - x);

        private final Function<Integer, Integer> action;

        Action(Function<Integer, Integer> action) {
            this.action = action;
        }

        int apply(int light) {
            return action.apply(light);
        }
    }

    private Grid(int height, int width) {
        lights = new int[width][height];
        Arrays.stream(lights)
                .forEach(lightsRow -> Arrays.fill(lightsRow, OFF));
    }

    public static GridBuilder getBuilder() {
        return new GridBuilder();
    }

    public int getLightNumber() {
        return Arrays.stream(lights)
                .flatMapToInt(Arrays::stream)
                .sum();
    }

    public Grid turnOn(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        return transform(Action.TURN_ON, topRowLeft, topColLeft, bottomRowRight, bottomColRight);
    }

    public Grid turnOff(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        return transform(Action.TURN_OFF, topRowLeft, topColLeft, bottomRowRight, bottomColRight);
    }

    public Grid toggle(int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        return transform(Action.TOGGLE, topRowLeft, topColLeft, bottomRowRight, bottomColRight);
    }

    private Grid transform(Action action, int topRowLeft, int topColLeft, int bottomRowRight, int bottomColRight) {
        for (int row = topRowLeft; row <= bottomRowRight; row++) {
            for (int col = topColLeft; col <= bottomColRight; col++) {
                lights[row][col] = action.apply(lights[row][col]);
            }
        }

        return this;
    }

    public static class GridBuilder {
        private int width = 0;
        private int height = 0;

        public GridBuilder withWidth(int width) {
            this.width = width;
            return this;
        }

        public GridBuilder withHeight(int height) {
            this.height = height;
            return this;
        }

        public Grid build() {
            return new Grid(height, width);
        }
    }
}
