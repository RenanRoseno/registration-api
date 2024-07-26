package com.queonetics.registration.models.dto;

import com.queonetics.registration.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String plate;

    public Vehicle toEntity(){
        Vehicle vehicleEntity = new Vehicle();
        vehicleEntity.setId(this.id);
        vehicleEntity.setPlate(this.plate.toUpperCase());

        return vehicleEntity;
    }

    public VehicleDTO(Vehicle vehicle){
        this.id = vehicle.getId();
        this.plate = vehicle.getPlate();
    }

}
