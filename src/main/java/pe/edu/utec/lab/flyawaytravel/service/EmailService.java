package pe.edu.utec.lab.flyawaytravel.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.utec.lab.flyawaytravel.event.BookingConfirmedEvent;
import pe.edu.utec.lab.flyawaytravel.model.Booking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @EventListener
    public void handleBookingConfirmed(BookingConfirmedEvent event) {
        Booking booking = event.getBooking();
        String fileName = "flight_booking_email_" + booking.getId() + ".txt";
        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        String content = String.format(
                "Confirmacion de Reserva - Fly Away Travel%n" +
                "===========================================%n%n" +
                "Nombres:          %s%n" +
                "Numero de vuelo:  %s%n" +
                "Aerolinea:        %s%n" +
                "Fecha de salida:  %s%n" +
                "Fecha de llegada: %s%n" +
                "Fecha de reserva: %s%n",
                booking.getCustomerNames(),
                booking.getFlight().getFlightNumber(),
                booking.getFlight().getAirlineName(),
                booking.getFlight().getDepartureTime().format(iso),
                booking.getFlight().getArrivalTime().format(iso),
                booking.getBookingDate().format(iso)
        );

        try {
            Path path = Paths.get(fileName);
            Files.writeString(path, content);
        } catch (IOException e) {
            System.err.println("Error al generar email de confirmacion: " + e.getMessage());
        }
    }
}
