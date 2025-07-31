package com.example.bussinsess;

import com.example.buisseness.Elevator;
import com.example.buisseness.ElevatorManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.buisseness.Elevator.ElevatorState.*;
import static org.assertj.core.api.Assertions.assertThat;

class TestElevatorShould {
    private static final ElevatorManager elevatorManager = new ElevatorManager();

    @BeforeAll
    static void setUp() {
        elevatorManager.placeElevatorOnFloorWithState(5, MOVING_UP);
        elevatorManager.placeElevatorOnFloorWithState(4, MOVING_UP);
        elevatorManager.placeElevatorOnFloorWithState(3, MOVING_UP);
        elevatorManager.placeElevatorOnFloorWithState(2, MOVING_UP);
        elevatorManager.placeElevatorOnFloorWithState(1, MOVING_UP);

        elevatorManager.placeElevatorOnFloorWithState(5, MOVING_DOWN);
        elevatorManager.placeElevatorOnFloorWithState(4, MOVING_DOWN);
        elevatorManager.placeElevatorOnFloorWithState(3, MOVING_DOWN);
        elevatorManager.placeElevatorOnFloorWithState(2, MOVING_DOWN);
        elevatorManager.placeElevatorOnFloorWithState(1, MOVING_DOWN);
    }

    @Test
    void callUp() {
        elevatorManager.printState();

        Elevator.ElevatorState direction = MOVING_DOWN;
        int callingFloor = 3;
        System.out.println("Callig " + direction.name() + " from " + callingFloor);
        Elevator closestElevator = elevatorManager.getClosestElevator(direction, callingFloor);

        System.out.println("Chosen: " + closestElevator);
        assertThat(closestElevator).isNotNull();
        assertThat(closestElevator.getIdentificator()).isEqualTo(7);
    }

    @Test
    void callDown() {
        elevatorManager.printState();

        Elevator.ElevatorState direction = MOVING_UP;
        int callingFloor = 3;
        System.out.println("Callig " + direction.name() + " from " + callingFloor);
        Elevator closestElevator = elevatorManager.getClosestElevator(direction, callingFloor);

        System.out.println("Chosen: " + closestElevator);
        assertThat(closestElevator).isNotNull();
        assertThat(closestElevator.getIdentificator()).isEqualTo(4);
    }
}
