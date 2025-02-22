package src.vehicle;

import src.di_container.annotations.Autowired;
import src.di_container.annotations.Component;

@Component
public class VehicleService {
    @Autowired
    Vehicle vehicle;

    public void start() {
        vehicle.drive();
    }
}
