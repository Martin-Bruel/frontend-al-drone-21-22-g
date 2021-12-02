package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.enumeration.DeliveryStatus;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageModifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.PackageRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PackageRegistryBean implements PackageFinder, PackageRegistration, PackageModifier {

    @PersistenceContext
    private EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger(PackageRegistryBean.class);


    @Override
    public List<Delivery> getAllDeliveries() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> cq = builder.createQuery(Delivery.class);
        Root<Delivery> delivery = cq.from(Delivery.class);
        cq.select(delivery);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    @Transactional
    public List<Delivery> getDeliverablePackages() {
        logger.info("Get Packages");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> cq = builder.createQuery(Delivery.class);
        Root<Delivery> deliveryRoot = cq.from(Delivery.class);
        cq.select(deliveryRoot);

        cq.where(builder.equal(deliveryRoot.get("deliveryStatus"), DeliveryStatus.PENDING));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Delivery getPackageByPackageId(Long packageId) {
        logger.info("Find delivery for id " + packageId);
        Delivery delivery = entityManager.find(Delivery.class, packageId);
        if (delivery == null) throw new IllegalArgumentException("Package " + packageId + " not found...");
        return delivery;
    }

    @Override
    public List<Delivery> getPackagesByDroneId(Long droneId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> cr = cb.createQuery(Delivery.class);
        Root<Delivery> root = cr.from(Delivery.class);
        Join<Delivery, Drone> drone = root.join("deliveryDrone");
        cr.select(root).where(cb.equal(drone.get("id"),droneId));
        TypedQuery<Delivery> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void registerDelivery(Delivery delivery) {
        entityManager.persist(delivery);
    }

    @Override
    public void setPackageStatus(Delivery delivery, DeliveryStatus status) {
        delivery.setDeliveryStatus(status);
    }

    @Override
    public void updateDeliveryDrone(Drone drone, Delivery delivery) {
        delivery.setDeliveryDrone(drone);
    }
}
