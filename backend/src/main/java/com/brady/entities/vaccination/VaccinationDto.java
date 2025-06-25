package com.brady.entities.vaccination;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VaccinationDto implements Serializable {
    private Long id;
    private LocalDateTime vaccinationDate;
    private Long petId;
    private String vaccineDescription;
}
