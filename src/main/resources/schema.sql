DROP TABLE IF EXISTS hospitals;
CREATE TABLE hospitals("name" VARCHAR(255), api_key VARCHAR(255) PRIMARY KEY);
ALTER TABLE hospitals ADD CONSTRAINT chkNoSpaces CHECK ("name" NOT LIKE '% %');