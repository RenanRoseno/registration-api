package com.queonetics.registration.repositories;

import com.queonetics.registration.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(value = " SELECT COUNT(*) > 0 FROM tb_vehicles WHERE plate = ?1 ", nativeQuery = true)
    Boolean existsVehiclesByPlate(String plate);

    @Query(" SELECT v FROM Vehicle v WHERE v.id <> ?1 AND v.plate = ?2 ")
    Vehicle getExistingVehicleExceptId(Long id, String plate);
}
