package mx.unam.ciencias.fbd.repository;

import mx.unam.ciencias.fbd.domain.Vehicle;
import mx.unam.ciencias.fbd.util.Safe;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Concrete Staff entities repository. Provides transformer functions from and to CSVRecords. It also sets the
 * entities' schema.
 */
public class VehicleRepository extends AbstractCSVCrudRepository<Vehicle, String> {
    private static final String VEHICLE_HOME = "vehicle.csv";

    public VehicleRepository() {
        super(VEHICLE_HOME, Schema.class);
    }

    @Override
    List<String> asRecord(Vehicle entity) {
        String zone = Safe.safeToString(entity.getZone());
        String driverId = Safe.safeToString(entity.getDriverId());
        return Arrays.asList(
                entity.getId(),
                entity.getType().toString(),
                entity.getModel(),
                zone,
                entity.getDescription(),
                driverId
        );
    }

    @Override
    String getId(Vehicle entity) {
        return entity.getId();
    }

    @Override
    public Vehicle ofRecord(CSVRecord record) {
        String id = record.get(Schema.ID);
        Vehicle.Type type = Vehicle.Type.valueOf(record.get(Schema.TYPE));
        String model = record.get(Schema.MODEL);
        String zone = record.get(Schema.ZONE);
        String description = record.get(Schema.DESCRIPTION);
        String driverIdStr = record.get(Schema.DRIVER_ID);
        Vehicle result = new Vehicle(id, type, model, description);
        if (!zone.isEmpty())
            result.setZone(zone);
        if (!driverIdStr.isEmpty())
            result.setDriverId(UUID.fromString(driverIdStr));
        return result;
    }

    public enum Schema {
        ID, TYPE, MODEL, ZONE, DESCRIPTION, DRIVER_ID
    }
}