package com.hotel.Hotel.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ReservaItemResponse(
        UUID idReserva,
        String numeroHabitacion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estado,
        double costoTotal) {
}
