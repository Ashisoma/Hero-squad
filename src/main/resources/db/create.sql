SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS heroes(
id int PRIMARY KEY auto_increment,
heroesName VARCHAR,
superPower VARCHAR,
weakness VARCHAR,
age INTEGER,
squadId INTEGER,
);

CREATE TABLE IF NOT EXISTS squads(
id int PRIMARY KEY auto_increment,
squadName VARCHAR,
squadCause VARCHAR,
squadSize INTEGER,
);