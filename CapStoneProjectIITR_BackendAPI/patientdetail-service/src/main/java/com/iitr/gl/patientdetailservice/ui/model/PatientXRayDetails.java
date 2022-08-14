package com.iitr.gl.patientdetailservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*@Document(value = "xrayDetails")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data*/
public class PatientXRayDetails {
    @Id
    private String patientId;
    private String parameter_1;
    private String parameter_2;
}
