DROP TABLE IF EXISTS hospitals;
CREATE TABLE hospitals("name" VARCHAR(255) PRIMARY KEY, api_key VARCHAR(255));
ALTER TABLE hospitals ADD CONSTRAINT chkNoSpaces CHECK ("name" NOT LIKE '% %');