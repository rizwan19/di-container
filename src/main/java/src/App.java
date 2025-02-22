package src;

import src.di_container.DiContainer;
import src.vehicle.*;

public class App {
    public static void main(String[] args) throws Exception {
        DiContainer.initializeContainer(Vehicle.class, Car.class, Bus.class, Truck.class, VehicleService.class);
        VehicleService service = DiContainer.get(VehicleService.class);
        service.start();
    }
}
