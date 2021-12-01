package com.polytech.si5.al.dronedelivery.team.g.truck.components;

import com.polytech.si5.al.dronedelivery.team.g.truck.constants.Api;
import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DronePositionDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.dto.PositionDto;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Drone;
import com.polytech.si5.al.dronedelivery.team.g.truck.entities.Position;
import com.polytech.si5.al.dronedelivery.team.g.truck.exceptions.UnreachableServiceException;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneFinder;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneStateNotifier;
import com.polytech.si5.al.dronedelivery.team.g.truck.interfaces.DroneWatcher;
import com.polytech.si5.al.dronedelivery.team.g.truck.scheduling.CronTaskRegister;
import com.polytech.si5.al.dronedelivery.team.g.truck.scheduling.SchedulingRunnable;
import com.polytech.si5.al.dronedelivery.team.g.truck.services.DroneService;
import com.polytech.si5.al.dronedelivery.team.g.truck.services.TabletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;


@Component("droneTracker")
@EnableAsync
public class DroneTracker implements DroneWatcher {
    private static final Logger logger = LoggerFactory.getLogger(DroneTracker.class);
    @Autowired
    DroneStateNotifier droneStateNotifier;

    @Autowired
    private CronTaskRegister cronTaskRegister;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    DroneService droneService;

    @Autowired
    DroneFinder droneFinder;

    @Autowired
    TabletService tabletService;

    private HashMap<Long,SchedulingRunnable> tasks=new HashMap<>();

    public void doTracking(Long droneId) throws UnreachableServiceException, InterruptedException {
        Drone drone= droneFinder.findDroneById(droneId);
        try{
            PositionDto position =droneService.getDronePosition(drone);
            tabletService.publish(new DronePositionDto(new Position(position.getLatitude(), position.getLongitude()), droneId));
            logger.info("Received position of drone "+droneId +": "+position);
        }catch (Exception e){
            retryDoTracking(drone);
        }


    }

    public void retryDoTracking(Drone drone) throws InterruptedException {
        long droneId = drone.getId();
        for (int i = 0; i < Api.DRONE_RETRY_CONNECTION;i++){
            try {
                logger.info("Retrying connection with drone " + droneId + ".. (" + i + ")");
                PositionDto position = droneService.getDronePosition(drone);
                logger.info("Attempted success!");
                logger.info("Received position of drone "+droneId +": "+position);
                return;
            }catch(RestClientException e){
                //logger.info(e.getMessage());
                logger.info("Attempted failed");
                Thread.sleep(Api.RESTTEMPLATE_TIMEOUT);
            }
        }
        logger.info("Unreachable service");
        droneStateNotifier.droneDown(droneId);
        untrack(droneId);

    }

    public void track(long droneId) {
        logger.info("Tracking drone "+droneId);
        Class[] paramsTypes = new Class[1];
        paramsTypes[0] = Long.class;
        Object[] params= new Object[1];
        params[0]=droneId;
        SchedulingRunnable task = new SchedulingRunnable(applicationContext,"droneTracker", "doTracking", paramsTypes ,params);
        this.tasks.put(droneId,task);
        this.cronTaskRegister.addCronTask(task, "*/5 * * * * *");
    }

    @Override
    public void untrack(long droneId) {
        logger.info("Untracking drone "+droneId);
        SchedulingRunnable task =this.tasks.get(droneId);
        if(this.tasks.containsKey(droneId)){
            this.cronTaskRegister.removeCronTask(task);
            this.tasks.remove(droneId);
        }
    }
}
