-- -- TODO(Jovan): Move to separate .sql files

INSERT INTO hospitals("name", api_key) VALUES('sacred_heart_hospital', 'apishh1234');
INSERT INTO hospitals("name", api_key) VALUES('princeton_plainsboro', 'apipp1234');
INSERT INTO hospitals("name", api_key) VALUES('limanska_ambulanta', 'apila1234');

INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(1, 'Aspirin sale!', 'Aspirin sale, 50% off, buy now!', '2020-07-01', '2020-09-01','Jankovic');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(2, 'Loyalty card', '10% discount  with loyalty card!', '2020-07-01', '2020-09-01','Liman');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(3, 'Suplements', '30% of for all suplements', '2020-07-01', '2020-09-01' ,'Liman');
INSERT INTO specialoffers(id, "name", content, valid_from, valid_until, pharmacy_id) VALUES(4, 'Special gift', 'Special gift for spent over 30e', '2020-07-01', '2020-09-01','Jankovic');

INSERT INTO drugs(id, "name", description, quantity, price, start_time, end_time) VALUES(1, 'Brufen 600mg', 'Analgetik Brufen 600mg film tableta', 20, 250.0, '2020-07-01', '2021-07-01');
INSERT INTO drugs(id, "name", description, quantity, price, start_time, end_time) VALUES(2, 'Vitamin C 1000mg', 'Vitaminski suplement 1000mg film tableta', 5, 55.0, '2020-07-01', '2021-08-01');
INSERT INTO drugs(id, "name", description, quantity, price, start_time, end_time) VALUES(3, 'B12 1000mcg', 'Vitaminski supleent 1000mcg B12, cijankobalamin', 40, 99.0, '2020-07-01', '2021-09-01');

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

INSERT INTO employeduser(city, postcode, street, street_no, "name", surname, email, password, user_type)
VALUES('Novi Sad', '21000', 'Slobodana Bajica', 17, 'Radovan', 'Zupunski', 'radovan.zupunski@uns.ac.rs', '123456789', 'DERMATOLOGIST');
INSERT INTO employeduser(city, postcode, street, street_no, "name", surname, email, password, user_type)
VALUES('Beograd', '11000', 'Slobodana Bajica', 2, 'Pera', 'Peric', 'pera@gmail.com', '987654321', 'DERMATOLOGIST');
INSERT INTO employeduser(city, postcode, street, street_no, "name", surname, email, password, user_type)
VALUES('Novi Sad', '21000', 'Slobodana Bajica', 17, 'Mika', 'Mikic', 'mika@uns.ac.rs', 'mikamika123', 'PHARMACIST');
INSERT INTO employeduser(city, postcode, street, street_no, "name", surname, email, password, user_type)
VALUES('Beograd', '11000', 'Slobodana Bajica', 2, 'Pera', 'Peric', 'pera@gmail.com', '987654321', 'PHARMACIST');

INSERT INTO worksin(pharmacy_id, employee_id, start_of_shift, end_of_shift)
VALUES (1, 1, '08:00', '13:00');
INSERT INTO worksin(pharmacy_id, employee_id, start_of_shift, end_of_shift)
VALUES (1, 2, '13:00','19:00');


INSERT INTO term(term_beginning, term_end, pharmacy_id, employee_id, patient_id)
VALUES ('2020-12-22 09:10', '2020-12-22 09:40', 1, 1, 1);
INSERT INTO term(term_beginning, term_end, pharmacy_id, employee_id, patient_id)
VALUES ('2020-12-23 10:00', '2020-12-23 10:20', 1, 1, 2);
INSERT INTO term(term_beginning, term_end, pharmacy_id, employee_id, patient_id)
VALUES ('2021-02-12 19:00', '2020-12-22 19:30', 1, 2, NULL);
INSERT INTO term(term_beginning, term_end, pharmacy_id, employee_id, patient_id)
VALUES ('2021-02-12 19:30', '2020-12-22 20:00', 1, 2, NULL);

