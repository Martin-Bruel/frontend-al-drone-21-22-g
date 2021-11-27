package com.polytech.si5.al.dronedelivery.team.g.truck.constants;

public class Api {
    public static final int DRONE_RANGE = 50;
    public static final String DEFAULT_ENV = "dev";
    public static final String ENV = System.getenv("APP_ENV") != null ? System.getenv("APP_ENV") : DEFAULT_ENV;
    public static final int RESTTEMPLATE_TIMEOUT = 5000; //ms
    public static final int DRONE_TIMEOUT = 3000;
    public static final int DRONE_RETRY_CONNECTION = 30;

              //////////- Drone APIs-//////////
    public static final String DRONE_API_BASE_URL = "drone-api";

              //////////- Warehouse APIs-//////////
    public static final String WAREHOUSE_API_HOST = ENV.equals("prod") ? "warehouse" : "localhost";
    public static final String WAREHOUSE_API_PORT = "8086";
    public static final String WAREHOUSE_API_BASE_URL = "warehouse-api";

}
