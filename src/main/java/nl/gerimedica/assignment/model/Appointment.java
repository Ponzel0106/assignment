package nl.gerimedica.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Data
@Table(Appointment.TABLE_NAME)
@NoArgsConstructor
public class Appointment {
    public static final String TABLE_NAME = "appointments";

    @Id
    public Long id;
    public String reason;
    public ZonedDateTime date;
    public Long patientId;

    public Appointment(String reason, ZonedDateTime date, Long patientId) {
        this.reason = reason;
        this.date = date;
        this.patientId = patientId;
    }
}
