package nl.gerimedica.assignment.repository;

import nl.gerimedica.assignment.model.Patient;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PatientRepository extends R2dbcRepository<Patient, Long> {
    Mono<Patient> findBySsnToken(String ssnToken);
}
