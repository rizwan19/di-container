package src.vehicle;

import src.di_container.annotations.Component;
import src.di_container.annotations.Primary;

@Component
@Primary
public class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a car");
    }
}
