package christamslights;

import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestGrid {
    private static final int GRID_HEIGHT = 1000;
    private static final int GRID_WIDTH = 1000;

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = Grid.getBuilder()
                .withWidth(GRID_WIDTH)
                .withHeight(GRID_HEIGHT)
                .build();
    }

    @Test
    void newGrid_hasZeroLights() {
        assertAllLightsAreOff();
    }

    @Test
    void turnOnSameCoordinate_turnsOnOneLight() {
        grid.turnOn(0, 0, 0, 0);
        assertThatNumberOfLights().isOne();
    }

    @Test
    void turnOnCoordinateTwice_turnsOnOneLight() {
        grid
                .turnOn(0, 0, 0, 0)
                .turnOn(0, 0, 0, 0);
        assertThatNumberOfLights().isOne();

    }

    @Test
    void turnOnGrid_turnsOnAllLightsInGrid() {
        turnOnAllGrid();
        assertAllLightsAreOn();
    }

    private void assertAllLightsAreOn() {
        assertThatNumberOfLights().isEqualTo(GRID_HEIGHT * GRID_WIDTH);
    }

    @Test
    void turnOnGridTwice_turnsOnAllLightsOnce() {
        turnOnAllGrid();
        turnOnAllGrid();
        assertAllLightsAreOn();
    }

    private AbstractIntegerAssert<?> assertThatNumberOfLights() {
        return assertThat(grid.getLightNumber());
    }

    private void turnOnAllGrid() {
        grid.turnOn(0, 0, GRID_HEIGHT - 1, GRID_WIDTH - 1);
    }

    @Test
    void turnOnTwoGridsWithoutIntersection_turnsOnSumOfLightsInGrids() {
        grid
                .turnOn(0, 0, 9, 9)
                .turnOn(20, 20, 29, 29);
        assertThatNumberOfLights().isEqualTo(10 * 10 + 10 * 10);
    }

    @Test
    void turnOnTwoGridsWithIntersection_turnsLightsInIntersectionOnlyOnce() {
        grid
                .turnOn(0, 0, 9, 9)
                .turnOn(8, 8, 10, 10);
        assertThatNumberOfLights().isEqualTo(10 * 10 + 3 * 3 - 2 * 2);
    }

    @Test
    void turnOnTwoGridsThatContainOnAnother_turnsLightsInContainingOnlyOnce() {
        grid
                .turnOn(0, 0, 2, 2)
                .turnOn(1, 1, 1, 1);
        assertThatNumberOfLights().isEqualTo(3 * 3);
    }

    @Test
    void turnOffWithEmptyGrid_turnsZeroLights() {
        grid.turnOff(0, 0, 999, 999);
        assertAllLightsAreOff();
    }

    private void assertAllLightsAreOff() {
        assertThatNumberOfLights().isZero();
    }

    @Test
    void turnOnThanTurnOff_turnsZeroLights() {
        grid
                .turnOn(0, 0, 100, 100)
                .turnOff(0, 0, 100, 100);
        assertAllLightsAreOff();
    }

    @Test
    void turnOnSquareThanTurnOffIntersectingSquare_turnsOffLightsInIntersection() {
        grid
                .turnOn(0, 0, 9, 9)
                .turnOff(8, 8, 10, 10);
        assertThatNumberOfLights().isEqualTo(10 * 10 - 2 * 2);
    }

    @Test
    void turnOnOffOn_turnsLightsOn() {
        grid
                .turnOn(0, 0, 99, 9)
                .turnOff(0, 0, 99, 99)
                .turnOn(0, 0, 99, 99);
        assertThatNumberOfLights().isEqualTo(100 * 100);
    }

    @Test
    void toggleEmptyGrid_turnsOnAllLights() {
        grid.toggle(0, 0, GRID_HEIGHT - 1, GRID_WIDTH - 1);
        assertAllLightsAreOn();
    }

    @Test
    void toggleFullGrid_turnsOnAllLights() {
        turnOnAllGrid();
        grid.toggle(0, 0, GRID_HEIGHT - 1, GRID_WIDTH - 1);
        assertAllLightsAreOff();
    }
}
