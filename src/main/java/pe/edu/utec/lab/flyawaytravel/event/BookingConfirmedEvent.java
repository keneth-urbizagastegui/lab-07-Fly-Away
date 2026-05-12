package pe.edu.utec.lab.flyawaytravel.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pe.edu.utec.lab.flyawaytravel.model.Booking;

@Getter
public class BookingConfirmedEvent extends ApplicationEvent {
    private final Booking booking;

    public BookingConfirmedEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }
}
