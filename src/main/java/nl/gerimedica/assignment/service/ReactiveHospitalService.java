package nl.gerimedica.assignment.service;

import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.model.Appointment;
import nl.gerimedica.assignment.model.Patient;
import nl.gerimedica.assignment.repository.AppointmentRepository;
import nl.gerimedica.assignment.repository.PatientRepository;
import nl.gerimedica.assignment.resource.request.CreateAppointmentRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveHospitalService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public Flux<Appointment> createAppointments(Flux<CreateAppointmentRequest> requests) {
        return appointmentRepository.saveAll(
                requests
                    .flatMap(request ->
                        patientRepository.findBySsnToken(request.ssnToken())
                            .switchIfEmpty((patientRepository.save(new Patient(request.patientName(), request.ssnToken()))))
                                .map(patient -> new Appointment(request.reason(), request.date(), patient.getId()))
                ));
    }

    public Mono<Void> deleteAppointments(String ssnToken) {
        return patientRepository.findBySsnToken(ssnToken)
                .map(Patient::getId)
                .flatMap(appointmentRepository::deleteAllByPatientId);
    }

    public Mono<Appointment> findLatestAppointment(String ssnToken){
        return patientRepository.findBySsnToken(ssnToken)
                .map(Patient::getId)
                .flatMap(appointmentRepository::findTopByPatientIdOrderByDateDesc);
    }


    public Flux<Appointment> findAppointments(String ssnToken) {
        return patientRepository.findBySsnToken(ssnToken)
                .map(Patient::getId)
                .flatMapMany(appointmentRepository::findAllByPatientId);
    }
}
