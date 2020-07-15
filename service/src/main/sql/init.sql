CREATE TABLE olt (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    ip     VARCHAR(15) UNIQUE NOT NULL,
    name   VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE olt_slot (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    olt_id INT,
    FOREIGN KEY (olt_id) REFERENCES olt (id)
);

CREATE TABLE olt_pon_port (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    status      VARCHAR(50) NOT NULL,
    olt_slot_id INT,
    FOREIGN KEY (olt_slot_id) REFERENCES olt_slot (id)
);

CREATE TABLE splitter (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(50) NOT NULL,
    status             VARCHAR(50) NOT NULL,
    ratio              TINYINT,
    us_splitter_id     INT,
    us_olt_pon_port_id INT,
    FOREIGN KEY (us_olt_pon_port_id) REFERENCES olt_pon_port (id),
    FOREIGN KEY (us_splitter_id) REFERENCES splitter (id)
);

CREATE TABLE onu (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(50) NOT NULL,
    status             VARCHAR(50) NOT NULL,
    pon_onu_id         VARCHAR(50) NOT NULL,
    us_splitter_id     INT,
    us_olt_pon_port_id INT,
    FOREIGN KEY (us_splitter_id) REFERENCES olt (id),
    FOREIGN KEY (us_olt_pon_port_id) REFERENCES olt_pon_port (id)
);


