CREATE SEQUENCE ninja_service_seq
    start 1
    increment 50;

CREATE TABLE ninja_service
(
    id    BIGINT       NOT NULL,
    type  VARCHAR(255) NOT NULL,
    price BIGINT       NOT NULL,
    CONSTRAINT ninja_service_pk PRIMARY KEY (id),
    CONSTRAINT ninja_service_type_unique UNIQUE (type)
);

CREATE SEQUENCE ninja_device_seq
    start 1
    increment 50;

CREATE TABLE ninja_device
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT ninja_device_pk PRIMARY KEY (id),
    CONSTRAINT ninja_device_name_unique UNIQUE (name)
);

CREATE TABLE ninja_device_service
(
    device_id  BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    CONSTRAINT ninja_device_service_device_fk FOREIGN KEY (device_id) REFERENCES ninja_device (id),
    CONSTRAINT ninja_device_service_service_fk FOREIGN KEY (service_id) REFERENCES ninja_service (id),
    CONSTRAINT device_service_unique UNIQUE (device_id, service_id)
);

