insert into drugs("description", "name", score) values('anestetik', 'Lidokain 50mg', 0.0);
insert into drugs("description", "name", score) values('anestetik', 'Prokain 100mg', 0.0);
insert into drugs("description", "name", score) values('anestetik', 'Tetrakain 100mg', 0.0);
insert into drugs("description", "name", score) values('sedativ', 'Bensedin 100mg', 0.0);
insert into drugs("description", "name", score) values('sedativ', 'Xanax 100mg', 0.0);

insert into pharmacies("name", city, postcode, street, street_no, latitude, longitude, score, consult_price) values('Schnabel Liman', 'Novi Sad', '21000', 'Balzakova', '24a', 45.2378, 19.8297, 4, 2000);
insert into pharmacies("name", city, postcode, street, street_no, latitude, longitude, score, consult_price) values('Schnabel Grbavica', 'Novi Sad', '21000', 'Aleksa Santica', '2', 45.2452, 19.8365, 3, 3000);
insert into pharmacies("name", city, postcode, street, street_no, latitude, longitude, score, consult_price) values('Schnabel Beograd', 'Beograd', '11000', 'Proleterska', '4b', 44.6806, 20.4476, 4, 1570);

insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 40, 1, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(40, 40, 2, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 40, 3, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 40, 4, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(10, 40, 5, 1);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(20, 40, 3, 2);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(20, 40, 4, 2);
insert into warehouseitem(available, quantity, drug_id, pharmacy_id) values(20, 40, 5, 2);

insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(100, '2021-06-12', '2020-01-01', 1);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(200, '2021-06-20', '2020-01-01', 2);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(300, '2022-06-01', '2020-01-01', 3);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(250, '2022-06-01', '2020-01-01', 4);
insert into drugprice(price, price_end_date, price_start_date, warehouseitem_id) values(200, '2022-06-01', '2020-01-01', 5);

insert into patients(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, phone_no) values (1, 'Jovan', 'Ivosevic', 'ivos.jovan@protonmail.ch', '$2a$10$IevzxrzynxfElyhc1zuUferMhXfAWbDRsob0cXmCc6jEETM7xhCiG', '21000', 'Novi Sad', 'Balzakova', '69', 0.0, 0.0, true, '0607671370'); --password:Sifra1337
insert into patients(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, phone_no) values (2, 'Mile', 'Knezevic', 'lemara98@gmail.com', '$2y$10$VS2RQAcHBqO.aLSEGlaYGe2b2605tXpUxAKCeZfXYTnMGriXGguoy', '21000', 'Novi Sad', 'Bulevar Oslobodjenja', '42', 0.0, 0.0, true, '0631931345'); --password:Sifra1337

insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, pharmacy_id, is_activated, score) values (3, 'Ana', 'Anic', 'rzupunski@gmail.com', '$2a$10$8mp7f8oEHCVXnv8jkfBvtOxn/FPASROZJQ10Bi3820tf0qzlCs4Pm', '11000', 'Beograd', 'Bulevar', '1', 0.0, 0.0, 1, true, 4);--password:Radovan123
insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, pharmacy_id, is_activated, score) values (4, 'Marina', 'Ludajic', 'madrina@gmail.com', '$2y$10$leO4PmaV5fPPfuEMVNCVjOqUwOHWrsLB5Ylnoyhs3MFewSFLZssGy', '371136', 'Kikinda', 'Generala Milutinovica', '5', 0.0, 0.0, 2, true, 3.4);--password:Sifra1337
insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, pharmacy_id, is_activated, score) values (5, 'Sanja', 'Dudas', 'sanjaa.d98@gmail.com', '$2y$10$vzEuI84K9v1pnVnDGmdcZOw35RPpcvn.dYymkbc0mvfuonWGOSx1y', '21315', 'Lug', 'Marsala Tita 48', '5', 0.0, 0.0, 1, true, 5);--password:Sifra1337


insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, score) values (6, 'Marija', 'Maric', 'radovan.zupunski@uns.ac.rs', '$2y$10$TmwHZ68VGAFjrozSbdbFce3AS0uB9Bnj2.AkMrPXhG27z1QpJrb9y', '11000', 'Beograd', 'Jurija Gagarina', '4', 0.0, 0.0, true, 3);--password:Sifra1337
insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, score) values (7, 'Milan', 'Milanovic', 'milan@gmail.com', '$2y$10$XbX38l.s9p1k2cPZrSQQSe657AajL.n..RVmXlDrmm4Bg3E.1Au8i', '11000', 'Beograd', 'Jurija Gagarina', '4', 0.0, 0.0, true, 3.4);--password:Sifra1337
insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, score) values (12, 'Natasa', 'Natasic', 'natasa@gmail.com', '$2y$10$XbX38l.s9p1k2cPZrSQQSe657AajL.n..RVmXlDrmm4Bg3E.1Au8i', '11000', 'Beograd', 'Jurija Gagarina', '4', 0.0, 0.0, true, 3.4);--password:Sifra1337
insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, is_activated, score) values (13, 'Luka', 'Lukic', 'luka@gmail.com', '$2y$10$XbX38l.s9p1k2cPZrSQQSe657AajL.n..RVmXlDrmm4Bg3E.1Au8i', '11000', 'Beograd', 'Jurija Gagarina', '4', 0.0, 0.0, true, 3.4);--password:Sifra1337

insert into pharmacyadmins(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, pharmacy_id, is_activated) values (8, 'Mika', 'Mikic', 'jankovicpharmacy@gmail.com', '$2y$10$XX.wKVB2GvZA024rrB672OUXfZZ9c9Whqbp8qAqCTiIfVEM8h9buK', 'Beograd', '11000', 'Bulevar', '1', 0.0, 0.0, 1, true); --password: Mikamikic123
insert into pharmacyadmins(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, pharmacy_id, is_activated) values (11, 'Zoran', 'Zoranovic', 'zoran@gmail.com', '$2y$10$XX.wKVB2GvZA024rrB672OUXfZZ9c9Whqbp8qAqCTiIfVEM8h9buK', 'Beograd', '11000', 'Bulevar', '1', 0.0, 0.0, 2, true); --password: Mikamikic123

insert into suppliers(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, firm, is_activated) values (9, 'Zoki', 'Zokic', 'zoki@gmail.com', '$2y$10$pTN2xTyuOLd3pyReU4QlGecab7eTQxgEevZrdbVYRYKqiU0timsga ', '11000', 'Beograd', 'Carlija Caplina', '12', 0.0, 0.0, 'Dobavljac Zoki', true);--password:Sifra1337
insert into suppliers(id, "name", surname, email, "password", city, postcode, street, street_no, latitude, longitude, firm, is_activated) values (10, 'Jovan', 'Jovanovic', 'jovan@gmail.com', '$2y$10$ll2ULAuxZbDQUg7mCVhTF.aa2Xx7Al2BNaFMyhmlOVxDoRurB52ji ', '21000', 'Novi Sad', 'Bulevar Oslobodjenja', '14a', 0.0, 0.0, 'Jova trans', true);--password:Sifra1337

insert into refresh_tokens(email, token) values('rzupunski@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyenVwdW5za2lAZ21haWwuY29tIiwiZXhwIjoxNjM4MTgwNzM5fQ.CRizdEz-jRFayMYiY0JmYf9G550CHqPnm1acY6hmZjMs0qtvnz-NhmtG5JxL9Z3GjQtXzlerO1k0CPiEKidGlg');
insert into refresh_tokens(email, token) values('ivos.jovan@protonmail.ch', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdm9zLmpvdmFuQHByb3Rvbm1haWwuY2giLCJleHAiOjE2MzgxOTAzOTl9.G_Z0-9Q5keOYK3RuZnrhEAF3a23d8rhYY3fowYV9GbO7owggRkQ1M4A4oq6th9p1UDBDBDl8jFqJpf5K0cyJ1Q');
insert into refresh_tokens(email, token) values ('jankovicpharmacy@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5rb3ZpY3BoYXJtYWN5QGdtYWlsLmNvbSIsImV4cCI6MTYzODExMTM5Mn0.W6yqYsldEUdznOQnXaFb0pe4sUHSC89PcsOdd5cTkDNX8mnJtO5wboT2lflmyuoSquP-vf4Z1pMLJGw1GfgqpA');
insert into refresh_tokens(email, token) values ('radovan.zupunski@uns.ac.rs', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWRvdmFuLnp1cHVuc2tpQHVucy5hYy5ycyIsImV4cCI6MTYzODY1ODMwNH0.PV6WKBD4GCX32sh-xxiQFJ0ccKDizKeY9b3kAuNjtvawJJ_8MvJxD8jUQmd4A_zq9ZGfCBP2gNXQKi9EvROKpw');

insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (6, 1);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (6, 2);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (7, 1);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (12, 2);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (13, 2);

insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2020-12-20 12:30:00', '2020-12-20 12:15:00', 3000, 1, 3, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2020-11-20 08:45:00', '2020-11-20 08:15:00', 5000, 1, 3, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2020-06-20 08:45:00', '2020-06-20 08:15:00', 15000, 2, 5, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-03-20 08:45:00', '2021-03-20 08:15:00', 6000, 2, 5, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-25 09:45:00', '2021-05-25 09:15:00', 5000, 1, 3, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-26 09:45:00', '2021-05-26 09:15:00', 5000, 2, 5, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-26 10:30:00', '2021-05-26 10:00:00', 5000, 1, 3, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-26 11:45:00', '2021-05-26 11:15:00', 5000, 2, 5, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-24 09:45:00', '2021-05-24 09:15:00', 1000, 2, 4, 2, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-24 08:45:00', '2021-05-24 08:15:00', 12000, 2, 4, 2, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-05-25 08:45:00', '2021-05-25 08:15:00', 6000, 1, 4, 2, true);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-06-02 08:45:00', '2021-06-02 08:15:00', 6000, 1, 3, 2, true);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-06-04 08:45:00', '2021-06-04 08:15:00', 6000, 1, 3, 2, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-06-10 16:45:00', '2021-06-10 16:15:00', 6000, 1, 3, 2, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-06-04 09:45:00', '2021-06-04 09:15:00', 6000, 1, 3, 2, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (false, '2021-06-05 09:45:00', '2021-06-05 09:15:00', 6000, 1, 6, 1, false);
insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id, missed) values (true, '2021-06-10 09:45:00', '2021-06-10 09:15:00', 6000, null, 6, 1, false);

-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-01 09:45:00', '2021-06-01 09:15:00', 5000, 1, 3, 1);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-01 09:45:00', '2021-06-01 09:15:00', 5000, 2, 5, 1);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-01 10:30:00', '2021-06-01 10:00:00', 5000, 1, 3, 1);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-02 11:45:00', '2021-06-02 11:15:00', 5000, 2, 7, 1);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-02 09:45:00', '2021-06-02 09:15:00', 1000, 1, 6, 2);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-02 08:45:00', '2021-06-02 08:15:00', 12000, 1, 6, 2);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-02 08:45:00', '2021-06-02 08:15:00', 6000, 1, 4, 2);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-10 18:45:00', '2021-06-10 18:15:00', 6000, 1, 3, 2);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-10 08:45:00', '2021-06-10 08:15:00', 6000, 1, 7, 2);
-- insert into appointments("free", end_time, start_time, price, patient_id, medical_employee_id, pharmacy_id) values (false, '2021-06-11 08:45:00', '2021-06-17 08:15:00', 6000, 1, 6, 2);

insert into drug_reservations(end_time, start_time, quantity, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2021-05-20', '2021-05-04', 2, true, 1, 1, 1);
insert into drug_reservations(end_time, start_time, quantity, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2020-05-20', '2020-05-04', 2, false, 1, 1, 1);
insert into drug_reservations(end_time, start_time, quantity, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2021-07-20', '2021-05-04', 2, false, 1, 1, 1);
insert into drug_reservations(end_time, start_time, quantity, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values('2021-06-04', '2021-05-04', 2, false, 1, 1, 2);

insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '14:00:00', 1, 3);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '16:00:00', 2, 4);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '17:00:00', 1, 6);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('14:00:00', '18:00:00', 1, 7);
insert into shifts(start_time, end_time, pharmacy_id, medical_employee_id) values('08:00:00', '14:00:00', 1, 5);

insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-05-10', 'Urgent order!', 8, 1, 'CREATED');
insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-08-12', 'Bensedin order!', 8, 1, 'CREATED');
insert into orders(deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values ('2021-05-12', 'Xanax order!', 8, 1, 'CREATED');

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

insert into roles("name") values ('ROLE_PATIENT');
insert into roles("name") values ('ROLE_ADMIN');
insert into roles("name") values ('ROLE_PHARMACIST');
insert into roles("name") values ('ROLE_DERMATOLOGIST');

insert into user_roles(role_id, user_id) values (1, 1);
insert into user_roles(role_id, user_id) values (2, 8);
insert into user_roles(role_id, user_id) values (3, 3);
insert into user_roles(role_id, user_id) values (4, 6);

insert into vacations(start_time, end_time, pharmacy_id, medical_employee_id, vacation_status) values ('2021-07-08', '2021-07-28', 1, 3, 'CREATED');
insert into vacations(start_time, end_time, pharmacy_id, medical_employee_id, vacation_status) values ('2021-07-20', '2021-07-31', 1, 6, 'CREATED');
insert into vacations(start_time, end_time, pharmacy_id, medical_employee_id, vacation_status) values ('2021-07-09', '2021-07-25', 1, 5, 'CREATED');
insert into vacations(start_time, end_time, pharmacy_id, medical_employee_id, vacation_status) values ('2021-08-15', '2021-08-31', 1, 7, 'CREATED');
insert into vacations(start_time, end_time, pharmacy_id, medical_employee_id, vacation_status) values ('2021-06-01', '2021-06-11', 1, 3, 'ACCEPTED');

insert into pharmacy_grades("value", patient_id, pharmacy_id) values (4, 1, 1);
insert into pharmacy_grades("value", patient_id, pharmacy_id) values (5, 2, 1);
insert into pharmacy_grades("value", patient_id, pharmacy_id) values (3, 1, 2);
insert into pharmacy_grades("value", patient_id, pharmacy_id) values (2, 2, 2);
insert into pharmacy_grades("value", patient_id, pharmacy_id) values (4, 2, 3);

insert into availability_request(quantity, request_pharmacy_id, request_pharmacist_id, request_drug_id) values (2, 1, 3, 1);
insert into availability_request(quantity, request_pharmacy_id, request_pharmacist_id, request_drug_id) values (1, 1, 5, 2);