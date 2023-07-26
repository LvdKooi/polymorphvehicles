alter table vehicle_entity
    rename to bus_entity;

alter table bus_entity
    add column BUS_TYPE VARCHAR(10) NOT NULL;
alter table bus_entity
    add column LITERS_LUGGAGE_CAPACITY BIGINT;

create table car_entity
(
    ID              BIGINT,
    VEHICLE_TYPE    VARCHAR(10) NOT NULL,
    BRAND           VARCHAR(30) NOT NULL,
    MODEL           VARCHAR(30) NOT NULL,
    BODY_STYLE      VARCHAR(20) NOT NULL,
    NUMBER_OF_DOORS BIGINT
);
