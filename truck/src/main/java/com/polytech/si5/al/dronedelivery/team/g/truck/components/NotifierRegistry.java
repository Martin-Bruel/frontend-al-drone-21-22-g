package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Notification;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationModifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.NotificationRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class NotifierRegistry implements NotificationRegistration, NotificationFinder, NotificationModifier {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(NotifierRegistry.class);

    @Override
    public void createNotification(long packageId, int deliverySate) {
        logger.info("Notify packageId : " + packageId+", deliverySate : "+deliverySate);
        Notification notification = new Notification(packageId, deliverySate);
        registerNotification(notification);
    }

    @Override
    @Transactional
    public void registerNotification(Notification n) {
        entityManager.persist(n);
    }

    @Transactional
    public List<Notification> getAllNotification(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = builder.createQuery(Notification.class);
        Root<Notification> notificationRoot = cq.from(Notification.class);
        cq.select(notificationRoot);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    @Transactional
    public void deleteNotificationsByIds(List<Long> notificationsIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Notification> query = cb.createCriteriaDelete(Notification.class);
        Root<Notification> root = query.from(Notification.class);
        query.where(root.get("id").in(notificationsIds));

        entityManager.createQuery(query).executeUpdate();
    }
}