package com.queonetics.registration.models.dto;

import com.queonetics.registration.models.Vehicle;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class VehicleDTO {
    private Long id;
    private String plate;

    public Vehicle toEntity(){
        Vehicle vehicleEntity = new Vehicle();
        vehicleEntity.setId(this.id);
        vehicleEntity.setPlate(this.plate.toUpperCase());

        return vehicleEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(plate, that.plate);
    }

    public VehicleDTO(Vehicle vehicle){
        this.id = vehicle.getId();
        this.plate = vehicle.getPlate();
    }

}
