CREATE DATABASE 'demo';
USE demo;

create table patientTable (
	id  int(3) NOT NULL AUTO_INCREMENT,
	first_name varchar(120) NOT NULL,
    last_name varchar(120) NOT NULL,
	age int(220) NOT NULL,
	PRIMARY KEY (id)
);

SELECT * FROM patient.patienttable;