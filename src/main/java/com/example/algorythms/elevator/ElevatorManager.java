package com.example.algorythms.elevator;

import com.example.algorythms.elevator.Elevator.ElevatorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;

import static com.example.algorythms.elevator.Elevator.ElevatorState.STOPPED;

public class ElevatorManager {
    private static final Logger log = LoggerFactory.getLogger(ElevatorManager.class);
    private final List<Elevator> elevators = new ArrayList<>();
    private final Queue<ElevatorOrder> elevatorOrders = new LinkedList<>();

    public void placeElevatorOnFloorWithState(int currentFlor, ElevatorState stopped) {
        Elevator e1 = new Elevator();
        e1.setIdentificator(elevators.size() + 1);
        e1.setCurrentFlor(currentFlor);
        e1.setState(stopped);
        this.elevators.add(e1);
    }

    public Elevator getClosestElevator(ElevatorState targetedState, int callingFloor) {
        Predicate<Elevator> floorPredicate = e -> switch (targetedState) {
            case MOVING_UP -> e.getCurrentFlor() < callingFloor;
            case MOVING_DOWN -> e.getCurrentFlor() > callingFloor;
            default -> true;
        };
        Predicate<Elevator> statePredicate = e -> e.getState() == STOPPED || e.getState() == targetedState;
        Comparator<Elevator> distanceComparator = Comparator.comparingInt(e -> Math.abs(e.getCurrentFlor() - callingFloor));

        try {
            return elevators.stream()
                    .sorted(Comparator.comparing(Elevator::getCurrentFlor))
                    .filter(statePredicate)
                    .filter(floorPredicate)
                    .sorted(distanceComparator)
                    .toList()
                    .getFirst();
        } catch (Exception e) {
            this.enqueue(targetedState, callingFloor);
            return null;
        }
    }

    private void enqueue(ElevatorState targetedState, int callingFloor) {
        this.elevatorOrders.add(new ElevatorOrder(targetedState, callingFloor));
    }

    public void printState() {
        elevators.forEach(elevator -> log.info(elevator.toString()));
    }

    private record ElevatorOrder(ElevatorState state, int callingFloor) {
    }
}
