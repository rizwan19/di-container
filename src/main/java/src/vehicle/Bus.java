package src.vehicle;

import src.di_container.annotations.Component;

@Component
public class Bus implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a bus");
    }
}
