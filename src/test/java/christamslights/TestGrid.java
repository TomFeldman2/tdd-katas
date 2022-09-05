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

    //    @Test
//    void newGrid_hasZeroLights() {
//        assertAllLightsAreOff();
//    }
//
//    @Test
//    void turnOnSameCoordinate_turnsOnOneLight() {
//        grid.turnOn(0, 0, 0, 0);
//        assertThatNumberOfLights().isOne();
//    }
//
//    @Test
//    void turnOnCoordinateTwice_turnsOnOneLight() {
//        grid
//                .turnOn(0, 0, 0, 0)
//                .turnOn(0, 0, 0, 0);
//        assertThatNumberOfLights().isOne();
//
//    }
//
//    @Test
//    void turnOnGrid_turnsOnAllLightsInGrid() {
//        turnOnAllGrid();
//        assertAllLightsAreOn();
//    }
//
//    private void assertAllLightsAreOn() {
//        assertThatNumberOfLights().isEqualTo(GRID_HEIGHT * GRID_WIDTH);
//    }
//
//    @Test
//    void turnOnGridTwice_turnsOnAllLightsOnce() {
//        turnOnAllGrid();
//        turnOnAllGrid();
//        assertAllLightsAreOn();
//    }
//
    private AbstractIntegerAssert<?> assertThatBrightnessOfLights() {
        return assertThat(grid.getLightNumber());
    }

    @Test
    void turnOnOneLight_returnsBrightnessOfOne() {
        grid.turnOn(0, 0 ,0, 0);
        assertThatBrightnessOfLights().isEqualTo(1);
    }

    @Test
    void toggleOfEmptyBoard_increaseBrightnessOfEachLightByTwo() {
        grid.toggle(0,0 , GRID_HEIGHT - 1,GRID_WIDTH - 1);
        assertThatBrightnessOfLights().isEqualTo(2 * GRID_HEIGHT * GRID_WIDTH);
    }
}
