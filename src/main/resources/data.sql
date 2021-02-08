-- -- TODO(Jovan): Move to separate .sql files

INSERT INTO hospitals("name", api_key) VALUES('sacred_heart_hospital', 'apishh1234');
INSERT INTO hospitals("name", api_key) VALUES('princeton_plainsboro', 'apipp1234');
INSERT INTO hospitals("name", api_key) VALUES('limanska_ambulanta', 'apila1234');

INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'Brufen 600mg', 'Analgetik Brufen 600mg film tableta', 1, 250.0, '2020-07-01', '2021-07-01');
INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'Vitamin C 1000mg', 'Vitaminski suplement 1000mg film tableta', 2, 55.0, '2020-07-01', '2021-08-01');
INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'B12 1000mcg', 'Vitaminski supleent 1000mcg B12, cijankobalamin', 5, 99.0, '2020-07-01', '2021-09-01');
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

INSERT INTO employed_users(dtype, city, postcode, street, street_no, email, "name", "password", surname)
VALUES('Dermatologist', 'Novi Sad', '21000', 'Balzakova', 69, 'ivosevic.jovan@uns.ac.rs', 'Jovan', 'mojasifralol', 'Ivosevic');

INSERT INTO employed_users(dtype, city, postcode, street, street_no, email, "name", "password", surname, pharmacy_id)
VALUES('Pharmacist', 'Novi Sad', '21000', '1300 Kaplara', 11, 'petar.petrovic@uns.ac.rs', 'Petar', '1234', 'Petrovic', 1);

INSERT INTO dermatologist_pharmacy(dermatologist_id, pharmacy_id)
VALUES(1, 1);

INSERT INTO shifts(employee_id, pharmacy_id, start_time, end_time)
VALUES (2, 1, '08:00:00', '12:00:00');

--INSERT INTO terms(start_time, end_time, pharmacy_id, employee_id, patient_id)
--VALUES ('2020-12-22 09:10:00', '2020-12-22 09:40:00', 1, 1, 1);
--INSERT INTO terms(start_time, end_time, pharmacy_id, employee_id, patient_id)
--VALUES ('2020-12-23 10:00:00', '2020-12-23 10:20:00', 1, 1, 2);

INSERT INTO orders("description", deadline)
VALUES ('Ordering drugs', '2021-02-20');
INSERT INTO orders("description", deadline)
VALUES ('Ordering brufen', '2021-03-20');

INSERT INTO orderitems(order_id, drug_id, quantity)
VALUES (1, 1, 2);
INSERT INTO orderitems(order_id, drug_id, quantity)
VALUES (1, 2, 3);
INSERT INTO orderitems(order_id, drug_id, quantity)
VALUES (2, 3, 1);
