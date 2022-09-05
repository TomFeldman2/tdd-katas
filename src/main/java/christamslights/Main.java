package christamslights;

public class Main {
    public static void main(String[] args) {
        Grid grid = Grid.getBuilder()
                .withHeight(1000)
                .withWidth(1000)
                .build();

        grid
                .turnOn(887, 9, 959, 629)
                .turnOn(454, 398, 844, 448)
                .turnOff(539,243, 559,965)
                .turnOff(370,819, 676,868)
                .turnOff(145,40, 370,997)
                .turnOff(301,3,  808,453)
                .turnOn(351,678,  951,908)
                .toggle(720,196, 897,994)
                .toggle(831,39, 904,860);

        System.out.println(grid.getLightNumber());
    }
}
