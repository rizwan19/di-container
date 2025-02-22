package src.vehicle;

import src.di_container.annotations.Component;

@Component
public class Truck implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a truck");
    }
}
