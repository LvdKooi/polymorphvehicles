create table bus_entity
(
    ID                      BIGINT,
    BUS_TYPE                VARCHAR(10) NOT NULL,
    LITERS_LUGGAGE_CAPACITY BIGINT
);

ALTER TABLE bus_entity
    ADD CONSTRAINT bus_vehicle_id_fk FOREIGN KEY (id) REFERENCES vehicle_entity(id);


create table car_entity
(
    ID      BIGINT,
    BODY_STYLE      VARCHAR(20) NOT NULL,
    NUMBER_OF_DOORS BIGINT
);

ALTER TABLE car_entity
    ADD CONSTRAINT car_vehicle_id_fk FOREIGN KEY (id) REFERENCES vehicle_entity (id);