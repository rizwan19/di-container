package src;

import src.di_container.DiContainer;
import src.di_container.annotations.ComponentScan;
import src.vehicle.*;
@ComponentScan
public class App {
    public static void main(String[] args) throws Exception {
        DiContainer.scan(DiContainer.getMainClassPackage(App.class));
        DiContainer.initializeContainer(Vehicle.class, Car.class, Bus.class, Truck.class, VehicleService.class);
        VehicleService service = DiContainer.get(VehicleService.class);
        service.start();
    }
}
