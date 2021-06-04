insert into drugs("description", "name") values('anestetik', 'Lidokain 50mg');
insert into drugs("description", "name") values('anestetik', 'Prokain 100mg');
insert into drugs("description", "name") values('anestetik', 'Tetrakain 100mg');
insert into drugs("description", "name") values('sedativ', 'Bensedin 100mg');
insert into drugs("description", "name") values('sedativ', 'Xanax 100mg');

insert into pharmacies("name", city, postcode, street, street_no, score, consult_price) values('Schnabel Liman', 'Novi Sad', '21000', 'Balzakova', '24a', 4, 2000);
insert into pharmacies("name", city, postcode, street, street_no, score, consult_price) values('Schnabel Grbavica', 'Novi Sad', '21000', 'Aleksa Santica', '2', 3, 3000);
insert into pharmacies("name", city, postcode, street, street_no, score, consult_price) values('Schnabel Beograd', 'Beograd', '11000', 'Proleterska', '4b', 4, 1570);

insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 20, 1, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 20, 2, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 20, 3, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 20, 4, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 20, 5, 1);

insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2022-06-01', '2020-01-01', 1);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2022-06-01', '2020-01-01', 2);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2022-06-01', '2020-01-01', 3);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2022-06-01', '2020-01-01', 4);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2022-06-01', '2020-01-01', 5);

insert into patients(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated, phone_no) values (1, 'Jovan', 'Ivosevic', 'ivos.jovan@protonmail.ch', '$2a$10$IevzxrzynxfElyhc1zuUferMhXfAWbDRsob0cXmCc6jEETM7xhCiG', '21000', 'Novi Sad', 'Balzakova', '69', true, '0607671370'); --password:Sifra1337
insert into patients(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated, phone_no) values (2, 'Mile', 'Knezevic', 'lemara98@gmail.com', '$2y$10$VS2RQAcHBqO.aLSEGlaYGe2b2605tXpUxAKCeZfXYTnMGriXGguoy', '21000', 'Novi Sad', 'Bulevar Oslobodjenja', '42', true, '0631931345'); --password:Sifra1337

insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (3, 'Ana', 'Anic', 'rzupunski@gmail.com', '$2a$10$8mp7f8oEHCVXnv8jkfBvtOxn/FPASROZJQ10Bi3820tf0qzlCs4Pm', '11000', 'Beograd', 'Bulevar', '1', 1, true);--password:Radovan123
insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (4, 'Marina', 'Ludajic', 'madrina@gmail.com', '$2y$10$leO4PmaV5fPPfuEMVNCVjOqUwOHWrsLB5Ylnoyhs3MFewSFLZssGy', '371136', 'Kikinda', 'Generala Milutinovica', '5', 2, true);--password:Sifra1337
insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (5, 'Sanja', 'Dudas', 'sanjaa.d98@gmail.com', '$2y$10$vzEuI84K9v1pnVnDGmdcZOw35RPpcvn.dYymkbc0mvfuonWGOSx1y', '21315', 'Lug', 'Marsala Tita 48', '5', 1, true);--password:Sifra1337

insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated) values (6, 'Marija', 'Maric', 'marija@gmail.com', '$2y$10$TmwHZ68VGAFjrozSbdbFce3AS0uB9Bnj2.AkMrPXhG27z1QpJrb9y', '11000', 'Beograd', 'Jurija Gagarina', '4', true);--password:Sifra1337
insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated) values (7, 'Milan', 'Milanovic', 'milan@gmail.com', '$2y$10$XbX38l.s9p1k2cPZrSQQSe657AajL.n..RVmXlDrmm4Bg3E.1Au8i', '11000', 'Beograd', 'Jurija Gagarina', '4', true);--password:Sifra1337

insert into pharmacyadmins(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (8, 'Mika', 'Mikic', 'jankovicpharmacy@gmail.com', '$2y$10$XX.wKVB2GvZA024rrB672OUXfZZ9c9Whqbp8qAqCTiIfVEM8h9buK', '11000', 'Beograd', 'Bulevar', '1', 1, true); --password: Mikamikic123
insert into pharmacyadmins(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (11, 'Zoran', 'Zoranovic', 'zoran@gmail.com', '$2y$10$XX.wKVB2GvZA024rrB672OUXfZZ9c9Whqbp8qAqCTiIfVEM8h9buK', '11000', 'Beograd', 'Bulevar', '1', 1, true); --password: Mikamikic123

insert into suppliers(id, "name", surname, email, "password", city, postcode, street, street_no, firm, is_activated) values (9, 'Zoki', 'Zokic', 'zoki@gmail.com', '$2y$10$pTN2xTyuOLd3pyReU4QlGecab7eTQxgEevZrdbVYRYKqiU0timsga ', '11000', 'Beograd', 'Carlija Caplina', '12', 'Dobavljac Zoki', true);--password:Sifra1337
insert into suppliers(id, "name", surname, email, "password", city, postcode, street, street_no, firm, is_activated) values (10, 'Jovan', 'Jovanovic', 'jovan@gmail.com', '$2y$10$ll2ULAuxZbDQUg7mCVhTF.aa2Xx7Al2BNaFMyhmlOVxDoRurB52ji ', '21000', 'Novi Sad', 'Bulevar Oslobodjenja', '14a', 'Jova trans', true);--password:Sifra1337

insert into refresh_tokens(email, token) values('rzupunski@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyenVwdW5za2lAZ21haWwuY29tIiwiZXhwIjoxNjM4MTgwNzM5fQ.CRizdEz-jRFayMYiY0JmYf9G550CHqPnm1acY6hmZjMs0qtvnz-NhmtG5JxL9Z3GjQtXzlerO1k0CPiEKidGlg');
insert into refresh_tokens(email, token) values('ivos.jovan@protonmail.ch', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdm9zLmpvdmFuQHByb3Rvbm1haWwuY2giLCJleHAiOjE2MzgxOTAzOTl9.G_Z0-9Q5keOYK3RuZnrhEAF3a23d8rhYY3fowYV9GbO7owggRkQ1M4A4oq6th9p1UDBDBDl8jFqJpf5K0cyJ1Q');
insert into refresh_tokens(email, token) values ('jankovicpharmacy@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5rb3ZpY3BoYXJtYWN5QGdtYWlsLmNvbSIsImV4cCI6MTYzODExMTM5Mn0.W6yqYsldEUdznOQnXaFb0pe4sUHSC89PcsOdd5cTkDNX8mnJtO5wboT2lflmyuoSquP-vf4Z1pMLJGw1GfgqpA');

insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (6, 1);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (6, 2);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (7, 1);

insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2020-12-20 12:30:00', '2020-12-20 12:15:00', 3000, 1, 3, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2020-11-20 08:45:00', '2020-11-20 08:15:00', 5000, 1, 3, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2020-06-20 08:45:00', '2020-06-20 08:15:00', 15000, 2, 5, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-03-20 08:45:00', '2021-03-20 08:15:00', 6000, 2, 5, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-25 09:45:00', '2021-05-25 09:15:00', 5000, 1, 3, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-26 09:45:00', '2021-05-26 09:15:00', 5000, 2, 5, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-26 10:30:00', '2021-05-26 10:00:00', 5000, 1, 3, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-26 11:45:00', '2021-05-26 11:15:00', 5000, 2, 5, 1);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-24 09:45:00', '2021-05-24 09:15:00', 1000, 2, 4, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-24 08:45:00', '2021-05-24 08:15:00', 12000, 2, 4, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-05-25 08:45:00', '2021-05-25 08:15:00', 6000, 1, 4, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-02 08:45:00', '2021-06-02 08:15:00', 6000, 1, 3, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-04 08:45:00', '2021-06-04 08:15:00', 6000, 1, 3, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-10 16:45:00', '2021-06-10 16:15:00', 6000, 1, 3, 2);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-04 09:45:00', '2021-06-04 09:15:00', 6000, 1, 3, 2);

insert into drugs_reservations(end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2021-05-20', 2, '2021-05-04', true, 1, 1, 1);
insert into drugs_reservations(end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2020-05-20', 2, '2020-05-04', false, 1, 1, 1);
insert into drugs_reservations(end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2021-07-20', 2, '2021-05-04', false, 1, 1, 1);

insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '14:00:00', 1, 2);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('15:00:00', '16:00:00', 2, 2);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '17:00:00', 1, 6);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '17:00:00', 1, 3);

insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-05-10', 'Urgent order!', 8, 1, 'CREATED');
insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-08-12', 'Bensedin order!', 8, 1, 'CREATED');
insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-07-12', 'Xanax order!', 8, 1, 'CREATED');

insert into orderitems(quantity, drug_id, order_id) values (10, 1, 1);
insert into orderitems(quantity, drug_id, order_id) values (50, 2, 1);
insert into orderitems(quantity, drug_id, order_id) values (20, 3, 1);
insert into orderitems(quantity, drug_id, order_id) values (30, 4, 2);
insert into orderitems(quantity, drug_id, order_id) values (20, 5, 3);

insert into offers(price, date_of_delivery, order_id, supplier_id, offer_status) values (500, '2021-05-05', 1, 9, 'CREATED');
insert into offers(price, date_of_delivery, order_id, supplier_id, offer_status) values (700, '2021-05-02', 1, 10, 'CREATED');
insert into offers(price, date_of_delivery, order_id, supplier_id, offer_status) values (800, '2021-08-01', 2, 9, 'CREATED');
insert into offers(price, date_of_delivery, order_id, supplier_id, offer_status) values (900, '2021-08-03', 2, 10, 'CREATED');

insert into promotions("description", start_time, end_time, pharmacy_id) values ('New promotion!!!', '2021-05-04','2021-06-04', 1);

insert into allergies(allergy_type, allergy_drug_id, patient_id) values ('Anafilakticki sok', 1, 1);
insert into allergies(allergy_type, allergy_drug_id, patient_id) values ('Anafilakticki sok', 3, 1);