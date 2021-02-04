-- TODO(Jovan): Use annotations

DROP TABLE IF EXISTS hospitals;
CREATE TABLE hospitals(api_key VARCHAR(255) PRIMARY KEY, "name" VARCHAR(255));

DROP TABLE IF EXISTS specialoffers;
CREATE TABLE specialoffers(id SERIAL PRIMARY KEY, "name" VARCHAR(255), content VARCHAR(255), valid_from DATE, valid_until DATE, pharmacy_id VARCHAR(255));

DROP TABLE IF EXISTS drugs CASCADE;
CREATE TABLE drugs(id SERIAL PRIMARY KEY, "name" VARCHAR(255), description VARCHAR(255), price REAL, start_time DATE, end_time DATE);

DROP TABLE IF EXISTS usagereportnotifications;
CREATE TABLE usagereportnotifications("filename" VARCHAR(255) PRIMARY KEY, "endpoint" VARCHAR(255), "message" VARCHAR(255));

DROP TABLE IF EXISTS pharmacies CASCADE;
CREATE TABLE pharmacies(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER, avg_rating REAL, "name" VARCHAR(255));

DROP TABLE IF EXISTS patients CASCADE;
CREATE TABLE patients(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER,
    "name" VARCHAR(255), surname VARCHAR(255), email VARCHAR(255));

DROP TABLE IF EXISTS drugreservations;
CREATE TABLE drugreservations(id SERIAL PRIMARY KEY, valid_until DATE, drug_id INTEGER, patient_id INTEGER);
DROP TABLE IF EXISTS pharmacyadmins CASCADE;
CREATE TABLE pharmacyadmins(id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER,
    "name" VARCHAR(255), surname VARCHAR(255), email VARCHAR(255), pharmacy_id INTEGER);

DROP TABLE IF EXISTS employeduser CASCADE;
CREATE TABLE employeduser (id SERIAL PRIMARY KEY, city VARCHAR(255), postcode VARCHAR(255), street VARCHAR(255), street_no INTEGER,
                      "name" VARCHAR(255), surname VARCHAR(255), email VARCHAR(255), password VARCHAR(255), user_type VARCHAR(255));

DROP TABLE IF EXISTS worksin CASCADE;
CREATE TABLE worksin (id SERIAL PRIMARY KEY, pharmacy_id INTEGER, employee_id INTEGER, start_of_shift TIME, end_of_shift TIME);

DROP TABLE IF EXISTS term CASCADE;
CREATE TABLE term (id SERIAL PRIMARY KEY, term_beginning TIMESTAMP , term_end TIMESTAMP , pharmacy_id INTEGER, employee_id INTEGER, patient_id INTEGER);