package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface HabitacionMapper {

    @SubclassMapping(source = HabitacionEstandar.class, target = HabitacionEstandarResponse.class)
    @SubclassMapping(source = SuitePresidencial.class, target = SuitePresidencialResponse.class)
    @Mapping(target = "tipo", ignore = true)
    HabitacionResponse toResponse(Habitacion habitacion);

    @Mapping(target = "tipo", constant = "ESTANDAR")
    @Mapping(target = "estado", expression = "java(habitacion.getEstado().name())")
    HabitacionEstandarResponse toEstandarResponse(HabitacionEstandar habitacion);

    @Mapping(target = "tipo", constant = "SUITE")
    @Mapping(target = "estado", expression = "java(habitacion.getEstado().name())")
    SuitePresidencialResponse toSuiteResponse(SuitePresidencial habitacion);

    List<HabitacionResponse> toResponseList(List<Habitacion> habitaciones);
}
