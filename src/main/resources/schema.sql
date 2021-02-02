-- TODO(Jovan): Use annotations

DROP TABLE IF EXISTS hospitals;
CREATE TABLE hospitals(api_key VARCHAR(255) PRIMARY KEY, "name" VARCHAR(255));

DROP TABLE IF EXISTS specialoffers;
CREATE TABLE specialoffers(id SERIAL PRIMARY KEY, "name" VARCHAR(255), content VARCHAR(255), valid_from DATE, valid_until DATE, pharmacy_id VARCHAR(255));

DROP TABLE IF EXISTS drugs CASCADE;
CREATE TABLE drugs(id SERIAL PRIMARY KEY, "name" VARCHAR(255), description VARCHAR(255));

DROP TABLE IF EXISTS usagereportnotifications;
CREATE TABLE usagereportnotifications("filename" VARCHAR(255) PRIMARY KEY, "endpoint" VARCHAR(255), "message" VARCHAR(255));

DROP TABLE IF EXISTS pharmacies CASCADE;
CREATE TABLE pharmacies(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER, avg_rating REAL, "name" VARCHAR(255));

DROP TABLE IF EXISTS patients CASCADE;
CREATE TABLE patients(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER,
    "name" VARCHAR(255), surname VARCHAR(255), email VARCHAR(255));

DROP TABLE IF EXISTS pharmacyadmins CASCADE;
CREATE TABLE pharmacyadmins(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER,
    "name" VARCHAR(255), surname VARCHAR(255), email VARCHAR(255), pharmacy_id INTEGER);