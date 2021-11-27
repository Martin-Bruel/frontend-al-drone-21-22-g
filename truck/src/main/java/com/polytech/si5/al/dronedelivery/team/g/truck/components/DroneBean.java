package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class DroneBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Drone insertWithTransaction(Drone drone) {
        entityManager.persist(drone);
        return drone;
    }

    public Drone insertWithoutTransaction(Drone drone) {
        entityManager.persist(drone);
        return drone;
    }

    public Drone find(long id) {
        return entityManager.find(Drone.class, id);
    }

    public List<Drone> findAll() {
        return entityManager.createQuery("from Drone").getResultList();
    }

    public Optional<Drone> findByName(String name) {
        Drone drone = entityManager.createNamedQuery("Drone.findByName", Drone.class)
                .setParameter("name", name)
                .getSingleResult();
        return drone != null ? Optional.of(drone) : Optional.empty();
    }
}
