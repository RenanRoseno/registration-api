package com.queonetics.registration.models.dto;

import com.queonetics.registration.models.Conductor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class ConductorDTO {
    private Long id;
    private String name;
    private String registration;

    public Conductor toEntity() {
        Conductor conductorEntity = new Conductor();
        conductorEntity.setId(this.id);
        conductorEntity.setName(this.name.toUpperCase());
        conductorEntity.setRegistration(this.registration);

        return conductorEntity;
    }

    public ConductorDTO(Conductor conductorEntity) {
        this.id = conductorEntity.getId();
        this.name = conductorEntity.getName().toUpperCase();
        this.registration = conductorEntity.getRegistration();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConductorDTO that = (ConductorDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(registration, that.registration);
    }
}
