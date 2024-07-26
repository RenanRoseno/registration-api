package com.queonetics.registration.repositories;

import com.queonetics.registration.models.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    @Query(value = " SELECT COUNT(*) > 0 FROM tb_conductors WHERE registration = ?1 ", nativeQuery = true)
    Boolean existsConductorBYRegistration(String registration);

    @Query(" SELECT c FROM Conductor c WHERE c.id <> ?1 AND c.registration = ?2 ")
    Conductor getExistingRegistrationExceptId(Long id, String registration);
}
