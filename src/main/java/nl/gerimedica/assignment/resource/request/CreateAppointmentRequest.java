package nl.gerimedica.assignment.resource.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record CreateAppointmentRequest(String patientName, @NotBlank String ssnToken, @NotBlank String reason, @NotNull ZonedDateTime date) {
}
