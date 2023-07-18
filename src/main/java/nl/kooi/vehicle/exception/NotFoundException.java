package nl.kooi.vehicle.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }

    public static NotFoundException vehicleNotFound(Long id) {
        return new NotFoundException(String.format("Vehicle with id %d not found.", id));
    }
}
