alter table VEHICLE_ENTITY rename to BUS_ENTITY;

alter table BUS_ENTITY
    add column BUS_TYPE VARCHAR(10) NOT NULL;
alter table BUS_ENTITY
    add column LITERS_LUGGAGE_CAPACITY BIGINT;

create table CAR_ENTITY
(
    ID              BIGINT,
    VEHICLE_TYPE    VARCHAR(10) NOT NULL,
    BRAND           VARCHAR(30) NOT NULL,
    MODEL           VARCHAR(30) NOT NULL,
    BODY_STYLE      VARCHAR(20) NOT NULL,
    NUMBER_OF_DOORS BIGINT
);
