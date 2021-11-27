package com.polytech.si5.al.dronedelivery.team.g.truck.entities;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ConnectionInterface {
    private String port;
    private String host;
    public ConnectionInterface(String host,String port){
        this.port=port;
        this.host=host;
    }

    public ConnectionInterface() {

    }
}
