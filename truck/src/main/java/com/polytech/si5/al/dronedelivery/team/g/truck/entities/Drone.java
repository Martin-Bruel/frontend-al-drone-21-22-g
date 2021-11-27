package com.polytech.si5.al.dronedelivery.team.g.truck.entities;

import com.polytech.si5.al.dronedelivery.team.g.truck.dto.DroneDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Drone {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DroneStatus status;

    @OneToMany(targetEntity=Delivery.class, mappedBy="deliveryDrone",
        cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    private List<Delivery> deliveries = new ArrayList<>();
    private int capacity;

    public ConnectionInterface connectionInterface;

    public Drone(String name, ConnectionInterface connectionInterface, int capacity) {
        this();
        this.name=name;
        this.connectionInterface=connectionInterface;
        this.capacity = capacity;
    }

    public Drone() {
        this.status = DroneStatus.READY;
    }

    public Drone(DroneDto dto){
        this();
        this.name = dto.name;
        this.connectionInterface = new ConnectionInterface(dto.host, dto.port);
        this.capacity = (dto.capacity==null) ? 1 : dto.capacity;
    }


    @Override
    public String toString() {
        return String.format(
                "Drone[id = %d, name = %s, status = %s]", id,name,status.name());
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        if (! (obj instanceof Drone)) return false;
        Drone other = (Drone) obj;
        return this.id.equals(other.id) && this.status.equals(other.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,status);
    }
}
