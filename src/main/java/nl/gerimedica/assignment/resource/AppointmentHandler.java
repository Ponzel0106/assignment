package nl.gerimedica.assignment.resource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.gerimedica.assignment.model.Appointment;
import nl.gerimedica.assignment.resource.request.CreateAppointmentRequest;
import nl.gerimedica.assignment.service.ReactiveHospitalService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@Log4j2
@Component
@RequiredArgsConstructor
public class AppointmentHandler {

    private static final String SSN_PARAM_KEY = "ssnToken";

    private final Validator validator;
    private final ReactiveHospitalService hospitalService;

    public Mono<ServerResponse> createAppointments(ServerRequest request) {
        return hospitalService.createAppointments(request
                .bodyToFlux(CreateAppointmentRequest.class)
                .map(validate()))
                .collectList()
                .flatMap(appointments -> ServerResponse.created(URI.create("")).body(appointments, Appointment.class));

    }



    public Mono<ServerResponse> deleteAppointments(ServerRequest request) {
        return hospitalService.deleteAppointments(request.pathVariable(SSN_PARAM_KEY))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getAppointments(ServerRequest request) {
        return hospitalService.findAppointments(request.pathVariable(SSN_PARAM_KEY))
                .collectList()
                .flatMap(appointments -> ServerResponse.ok().body(appointments, Appointment.class));

    }

    public Mono<ServerResponse> getLatestAppointment(ServerRequest request) {
        return hospitalService.findLatestAppointment(request.pathVariable(SSN_PARAM_KEY))
                .flatMap(
                        appointment -> ServerResponse.ok().body(appointment, Appointment.class));
    }

    private  <T> Function<T, T> validate() {
        return request -> {
            Set<ConstraintViolation<T>> violations = validator.validate(request);
            if (violations.isEmpty()) {
                return request;
            } else {
                log.error(
                        "Invalid request: {}",
                        violations.stream().map(ConstraintViolation::getMessage).collect(joining(". ")));
                throw new ConstraintViolationException(violations);
            }
        };
    }


}
