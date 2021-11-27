package com.polytech.si5.al.dronedelivery.team.g.truck.repositories;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DroneRepository extends JpaRepository<Drone,Integer> {
}
