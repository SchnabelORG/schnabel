-- -- TODO(Jovan): Move to separate .sql files



insert into pharmacies values(1,'aaa', '12321', 'aaa', 6, 'aaaa');

insert into warehouse values(1,1);


insert into drugs values(1, 'anestetik', 'lidokain');
insert into drugs values(2, 'anestetik', 'prokain');
insert into drugs values(3, 'anestetik', 'tetrakain');
insert into drugs values(4, 'sedativ', 'bensedin');
insert into drugs values(5, 'sedativ', 'xanax');



insert into drugprice values(1, 100, '2022-06-01', '2020-01-01', 1);
insert into drugprice values(2, 100, '2022-06-01', '2020-01-01', 2);
insert into drugprice values(3, 100, '2022-06-01', '2020-01-01', 3);
insert into drugprice values(4, 100, '2022-06-01', '2020-01-01', 4);
insert into drugprice values(5, 100, '2022-06-01', '2020-01-01', 5);


insert into warehouseitem values(1, 20, 10, 1, 1);
insert into warehouseitem values(2, 20, 10, 2, 1);
insert into warehouseitem values(3, 20, 10, 3, 1);
insert into warehouseitem values(4, 20, 10, 4, 1);
insert into warehouseitem values(5, 20, 10, 5, 1);



--insert into patients values(1, 'Petar', 'Petrovic', '2000-06-01', 'petar@gmail.com', '123', '21000', 'Novi Sad', 'Bulevar', 1);









-- INSERT INTO hospitals("name", api_key) VALUES('sacred_heart_hospital', 'apishh1234');
-- INSERT INTO hospitals("name", api_key) VALUES('princeton_plainsboro', 'apipp1234');
-- INSERT INTO hospitals("name", api_key) VALUES('limanska_ambulanta', 'apila1234');
--
-- INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'Brufen 600mg', 'Analgetik Brufen 600mg film tableta', 1, 250.0, '2020-07-01', '2021-07-01');
-- INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'Vitamin C 1000mg', 'Vitaminski suplement 1000mg film tableta', 2, 55.0, '2020-07-01', '2021-08-01');
-- INSERT INTO drugs( "name", description, quantity, price, start_time, end_time) VALUES( 'B12 1000mcg', 'Vitaminski supleent 1000mcg B12, cijankobalamin', 5, 99.0, '2020-07-01', '2021-09-01');
-- INSERT INTO pharmacies(city, postcode, street, street_no, avg_rating, "name")
-- VALUES('Novi Sad', '21000', 'Balzakova', 44, 4.5, 'Cvjetkovic');
--
-- INSERT INTO patients(city, postcode, street, street_no, "name", surname, email)
-- VALUES('Novi Sad', '21000', 'Balzakova', 69, 'Jovan', 'Ivosevic', 'ivosevic.jovan@uns.ac.rs');
-- INSERT INTO patients(city, postcode, street, street_no, "name", surname, email)
-- VALUES('Beograd', '11000', 'Carlija Caplina', 12, 'Petar', 'Petrovic', 'petrovic.petar@bc.ac.rs');
--
-- INSERT INTO drugreservations(valid_until, drug_id, patient_id)
-- VALUES('2021-02-03', 1, 1);
-- INSERT INTO drugreservations(valid_until, drug_id, patient_id)
-- VALUES('2021-02-01', 1, 2);
--
-- INSERT INTO employed_users(dtype, city, postcode, street, street_no, email, "name", "password", surname)
-- VALUES('Dermatologist', 'Novi Sad', '21000', 'Balzakova', 69, 'ivosevic.jovan@uns.ac.rs', 'Jovan', 'mojasifralol', 'Ivosevic');
--
-- INSERT INTO dermatologist_pharmacy(dermatologist_id, pharmacy_id)
-- VALUES(1, 1);
--
-- INSERT INTO shifts(employed_user_id, pharmacy_id, start_time, end_time)
-- VALUES (1, 1, '08:00:00', '12:00:00');

--INSERT INTO terms(start_time, end_time, pharmacy_id, employee_id, patient_id)
--VALUES ('2020-12-22 09:10:00', '2020-12-22 09:40:00', 1, 1, 1);
--INSERT INTO terms(start_time, end_time, pharmacy_id, employee_id, patient_id)
--VALUES ('2020-12-23 10:00:00', '2020-12-23 10:20:00', 1, 1, 2);

-- INSERT INTO orders("description", deadline)
-- VALUES ('Ordering drugs', '2021-02-20');
-- INSERT INTO orders("description", deadline)
-- VALUES ('Ordering brufen', '2021-03-20');
--
-- INSERT INTO orderitems(order_id, drug_id, quantity)
-- VALUES (1, 1, 2);
-- INSERT INTO orderitems(order_id, drug_id, quantity)
-- VALUES (1, 2, 3);
-- INSERT INTO orderitems(order_id, drug_id, quantity)
-- VALUES (2, 3, 1);
