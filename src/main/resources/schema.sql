DROP TABLE IF EXISTS hospitals;
CREATE TABLE hospitals(api_key VARCHAR(255) PRIMARY KEY, "name" VARCHAR(255));
ALTER TABLE hospitals ADD CONSTRAINT chkNoSpaces CHECK ("name" NOT LIKE '% %');