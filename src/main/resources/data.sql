-- TODO(Jovan): Move to separate .sql files

INSERT INTO hospitals("name", api_key) VALUES('sacred_heart_hospital', 'apishh1234');
INSERT INTO hospitals("name", api_key) VALUES('princeton_plainsboro', 'apipp1234');
INSERT INTO hospitals("name", api_key) VALUES('limanska_ambulanta', 'apila1234');

INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(1, 'Aspirin sale!', 'Aspirin sale, 50% off, buy now!', '2020-07-01', '2020-09-01','Jankovic');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(2, 'Loyalty card', '10% discount  with loyalty card!', '2020-07-01', '2020-09-01','Liman');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(3, 'Suplements', '30% of for all suplements', '2020-07-01', '2020-09-01' ,'Liman');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(4, 'Special gift', 'Special gift for spent over 30e', '2020-07-01', '2020-09-01','Jankovic');

INSERT INTO drugs(id, "name", description) VALUES(1, 'Brufen 600mg', 'Analgetik Brufen 600mg film tableta');
INSERT INTO drugs(id, "name", description) VALUES(2, 'Vitamin C 1000mg', 'Vitaminski suplement 1000mg film tableta');
INSERT INTO drugs(id, "name", description) VALUES(3, 'B12 1000mcg', 'Vitaminski supleent 1000mcg B12, cijankobalamin');

INSERT INTO pharmacies(city, postcode, street, street_no, avg_rating, "name")
VALUES('Novi Sad', '21000', 'Balzakova', 44, 4.5, 'Cvjetkovic');

INSERT INTO patients(city, postcode, street, street_no, "name", surname, email)
VALUES('Novi Sad', '21000', 'Balzakova', 69, 'Jovan', 'Ivosevic', 'ivosevic.jovan@uns.ac.rs');
INSERT INTO patients(city, postcode, street, street_no, "name", surname, email)
VALUES('Beograd', '11000', 'Carlija Caplina', 12, 'Petar', 'Petrovic', 'petrovic.petar@bc.ac.rs');

INSERT INTO drugreservations(valid_until, drug_id, patient_id)
VALUES('2021-02-03', 1, 1);
INSERT INTO drugreservations(valid_until, drug_id, patient_id)
VALUES('2021-02-01', 1, 2);