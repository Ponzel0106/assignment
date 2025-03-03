package nl.gerimedica.assignment.repository;

import nl.gerimedica.assignment.model.Appointment;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AppointmentRepository extends R2dbcRepository<Appointment, Long> {
    Flux<Appointment> findAllByPatientId(Long patientId);
    Mono<Appointment> findTopByPatientIdOrderByDateDesc(Long patientId);
    Mono<Void> deleteAllByPatientId(Long patientId);
}
