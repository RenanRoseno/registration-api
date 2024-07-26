package com.queonetics.registration.models.dto;

import com.queonetics.registration.models.Conductor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConductorDTO {
    private Long id;
    private String name;
    private String registration;

    public Conductor toEntity(){
        Conductor conductorEntity = new Conductor();
        conductorEntity.setId(this.id);
        conductorEntity.setName(this.name.toUpperCase());
        conductorEntity.setRegistration(this.registration);

        return conductorEntity;
    }

    public ConductorDTO(Conductor conductorEntity){
        this.id = conductorEntity.getId();
        this.name = conductorEntity.getName().toUpperCase();
        this.registration = conductorEntity.getRegistration();
    }
}
