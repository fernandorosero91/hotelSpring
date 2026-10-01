package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.dto.response.ReservaItemResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    List<ClienteResponse> toResponseList(List<Cliente> clientes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    Cliente toEntity(CrearClienteRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    void updateClienteFromDto(ActualizarClienteRequest dto, @MappingTarget Cliente entity);

    @Mapping(target = "totalReservasRealizadas", expression = "java(cliente.getReservas().size())")
    @Mapping(target = "montoTotalGastado", expression = "java(cliente.getReservas().stream().mapToDouble(r -> r.getCostoTotal()).sum())")
    @Mapping(target = "reservasRecientes", source = "reservas", qualifiedByName = "reservasToItems")
    ClienteResumenResponse toResumen(Cliente cliente);

    @Named("reservasToItems")
    default List<ReservaItemResponse> reservasToItems(List<Reserva> reservas) {
        return reservas.stream().map(this::reservaToItem).toList();
    }

    @Mapping(target = "idReserva", source = "id")
    @Mapping(target = "numeroHabitacion", source = "habitacion.numero")
    @Mapping(target = "fechaInicio", source = "periodo.fechaInicio", qualifiedByName = "toLocalDate")
    @Mapping(target = "fechaFin", source = "periodo.fechaFin", qualifiedByName = "toLocalDate")
    @Mapping(target = "estado", expression = "java(reserva.getEstado().name())")
    ReservaItemResponse reservaToItem(Reserva reserva);

    @Named("toLocalDate")
    default LocalDate toLocalDate(LocalDateTime value) {
        return value == null ? null : value.toLocalDate();
    }
}
