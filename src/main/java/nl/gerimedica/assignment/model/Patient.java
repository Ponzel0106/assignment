package nl.gerimedica.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;


@Data
@Table(Patient.TABLE_NAME)
@NoArgsConstructor
public class Patient {
    public static final String TABLE_NAME = "patients";

    @Id
    private Long id;
    private String name;
    private String ssnToken;

    public Patient(String name, String ssnToken) {
        this.name = name;
        this.ssnToken = ssnToken;
    }
}
