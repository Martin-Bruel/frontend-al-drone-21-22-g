package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.constants.DeliveryStatusCode;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Delivery;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.DroneStatus;
import com.polytech.si5.al.dronedelivery.team.g.truck.enumeration.DeliveryStatus;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class DeliveryTracker implements DeliveryStateNotifier, DroneStateNotifier {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryTracker.class);
    @Autowired
    DroneWatcher droneWatcher;

    @Autowired
    NotificationRegistration notificationRegistration;

    @Autowired
    PackageFinder packageFinder;

    @Autowired
    PackageModifier packageModifier;

    @Autowired
    DroneFinder droneFinder;

    @Autowired
    DroneModifier droneModifier;

    @Autowired
    EntityManager entityManager;

    private boolean mustSendMessage=true;

    @Override
    @Transactional
    public void updateDeliverySate(long droneId, int status, long deliveryId) {
        logger.info("Updating delivery of drone "+droneId + " (Status:"+status+")");
        switch (status){
            case DeliveryStatusCode.STARTING_DELIVERY:
                logger.info("Starting delivery ");
                droneWatcher.track(droneId);
                Drone drone = droneFinder.findDroneById(droneId);
                droneModifier.setDroneStatus(drone, DroneStatus.FLYING_TO_DELIVERY);

                List<Delivery> deliveries = packageFinder.getPackagesByDroneId(droneId);
                for(Delivery delivery : deliveries) {
                    packageModifier.setPackageStatus(delivery, DeliveryStatus.BEING_DELIVERED);
                }
                break;
            case DeliveryStatusCode.PENDING_DELIVERY:
                break;

            case DeliveryStatusCode.PACKAGE_DELIVERED:
                logger.info("Package delivered");

                Delivery delivery = packageFinder.getPackageByPackageId(deliveryId);
                if(mustSendMessage){
                    sendNotification(droneId, status, deliveryId);
                }
                packageModifier.setPackageStatus(delivery, DeliveryStatus.DELIVERED);
                packageModifier.updateDeliveryDrone(null, delivery);
                entityManager.persist(delivery);
                break;
            case DeliveryStatusCode.FINISHED_DELIVERY:
                logger.info("Finishing delivery ");
                drone = droneFinder.findDroneById(droneId);
                droneModifier.setDroneStatus(drone, DroneStatus.READY);
                droneWatcher.untrack(droneId);
                break;
            default:
                break;
        }
    }

    void setMustSendMessage(boolean value){this.mustSendMessage=value;}

    void sendNotification(long droneId, int status, long deliveryId) {
        notificationRegistration.createNotification(deliveryId, status);
    }

    @Override
    @Transactional
    public void droneDown(long droneId) {
        List<Delivery> deliveries=packageFinder.getPackagesByDroneId(droneId);
        for(Delivery delivery : deliveries){
            packageModifier.setPackageStatus(delivery, DeliveryStatus.LOST);
            entityManager.merge(delivery);
        }
        Drone drone= droneFinder.findDroneById(droneId);
        drone.setStatus(DroneStatus.LOST);
    }
}
