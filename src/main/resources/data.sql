-- -- TODO(Jovan): Move to separate .sql files

insert into pharmacies("name", city, postcode, street, street_no, score) values('Schnabel Liman', 'Novi Sad', '21000', 'Balzakova', '24a', 4);
insert into pharmacies("name", city, postcode, street, street_no, score) values('Schnabel Grbavica', 'Novi Sad', '21000', 'Aleksa Santica', '2', 3);
insert into pharmacies("name", city, postcode, street, street_no, score) values('Schnabel Beograd', 'Beograd', '11000', 'Proleterska', '4b', 4);

insert into drugs values(111, 'anestetik', 'lidokain 50mg');
insert into drugs values(222, 'anestetik', 'prokain 100mg');
insert into drugs values(333, 'anestetik', 'tetrakain 100mg');
insert into drugs values(444, 'sedativ', 'bensedin 100mg');
insert into drugs values(555, 'sedativ', 'xanax 100mg');

insert into warehouseitem(id, available, quantity, drug_id, pharmacy_id) values(111, 10, 20, 111, 1);
insert into warehouseitem(id, available, quantity, drug_id, pharmacy_id) values(222, 10, 20, 222, 1);
insert into warehouseitem(id, available, quantity, drug_id, pharmacy_id) values(333, 10, 20, 333, 1);
insert into warehouseitem(id, available, quantity, drug_id, pharmacy_id) values(444, 10, 20, 444, 1);
insert into warehouseitem(id, available, quantity, drug_id, pharmacy_id) values(555, 10, 20, 555, 1);

insert into drugprice(id, price, price_end_date, price_start_date, warehouseitem_id) values(111, 100, '2022-06-01', '2020-01-01', 111);
insert into drugprice(id, price, price_end_date, price_start_date, warehouseitem_id) values(222, 200, '2022-06-01', '2020-01-01', 222);
insert into drugprice(id, price, price_end_date, price_start_date, warehouseitem_id) values(333, 300, '2022-06-01', '2020-01-01', 333);
insert into drugprice(id, price, price_end_date, price_start_date, warehouseitem_id) values(444, 400, '2022-06-01', '2020-01-01', 444);
insert into drugprice(id, price, price_end_date, price_start_date, warehouseitem_id) values(555, 500, '2022-06-01', '2020-01-01', 555);

insert into patients(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated, phone_no) values (1, 'Jovan', 'Ivosevic', 'ivos.jovan@protonmail.ch', '$2a$10$IevzxrzynxfElyhc1zuUferMhXfAWbDRsob0cXmCc6jEETM7xhCiG', '21000', 'Novi Sad', 'Balzakova', '69', true, '0607671370');
insert into refresh_tokens(id, email, token) values(1, 'ivos.jovan@protonmail.ch', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdm9zLmpvdmFuQHByb3Rvbm1haWwuY2giLCJleHAiOjE2MzgxOTAzOTl9.G_Z0-9Q5keOYK3RuZnrhEAF3a23d8rhYY3fowYV9GbO7owggRkQ1M4A4oq6th9p1UDBDBDl8jFqJpf5K0cyJ1Q');

insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated) values (2, 'Marija', 'Maric', 'marija@gmail.com', '123', '11000', 'Beograd', 'Bulevar', '1', true);
insert into dermatologists(id, "name", surname, email, "password", city, postcode, street, street_no, is_activated) values (6, 'Milan', 'Milanovic', 'milan@gmail.com', '321', '11000', 'Beograd', 'Bulevar', '1', true);

insert into suppliers(id, "name", surname, email, "password", city, postcode, street, street_no, firm, is_activated) values (3, 'Zoki', 'Zokic', 'zokidoo@gmail.com', '123', '11000', 'Beograd', 'Bulevar', '1', 'Dobavljac Zoki', true);


insert into pharmacyadmins(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (4, 'Mika', 'Mikic', 'jankovicpharmacy@gmail.com', '$2y$10$XX.wKVB2GvZA024rrB672OUXfZZ9c9Whqbp8qAqCTiIfVEM8h9buK', '11000', 'Beograd', 'Bulevar', '1', 1, true); --password: Mikamikic123
insert into refresh_tokens(id, email, token) values (2, 'jankovicpharmacy@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5rb3ZpY3BoYXJtYWN5QGdtYWlsLmNvbSIsImV4cCI6MTYzODExMTM5Mn0.W6yqYsldEUdznOQnXaFb0pe4sUHSC89PcsOdd5cTkDNX8mnJtO5wboT2lflmyuoSquP-vf4Z1pMLJGw1GfgqpA');

--insert into pharmacyadmins(id, "name", surname, date_of_birth, email, "password", city, postcode, street, street_no, pharmacy_id) values (4, 'Mika', 'Mikic', '2000-06-01', 'miki@gmail.com', '123', '11000', 'Beograd', 'Bulevar', 1, 1);

--insert into pharmacists(id, "name", surname, date_of_birth, email, "password", city, postcode, street, street_no, pharmacy_id) values (5, 'Ana', 'Anic', '2000-06-01', 'ana@gmail.com', '12345678', 'Beograd', '11000', 'Bulevar', 1, 1);

insert into pharmacists(id, "name", surname, email, "password", city, postcode, street, street_no, pharmacy_id, is_activated) values (6, 'Ana', 'Anic', 'rzupunski@gmail.com', '$2a$10$8mp7f8oEHCVXnv8jkfBvtOxn/FPASROZJQ10Bi3820tf0qzlCs4Pm', '11000', 'Beograd', 'Bulevar', '1', 1, true);--password:Radovan123

insert into refresh_tokens(id, email, token) values(3, 'rzupunski@gmail.com', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyenVwdW5za2lAZ21haWwuY29tIiwiZXhwIjoxNjM4MTgwNzM5fQ.CRizdEz-jRFayMYiY0JmYf9G550CHqPnm1acY6hmZjMs0qtvnz-NhmtG5JxL9Z3GjQtXzlerO1k0CPiEKidGlg');

insert into orders(id, deadline, "description", pharmacyadmin_id, pharmacy_id, order_status) values (111, '2021-05-10', 'My order', 4, 1, 'CREATED');


insert into orderitems(id, quantity, drug_id, order_id) values (111, 1, 111, 111);

insert into offers(id, price, date_of_delivery, order_id, supplier_id) values (111, 500, '2021-05-05', 111, 3);

insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (2, 1);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (2, 2);
insert into dermatologist_pharmacy(dermatologist_id, pharmacy_id) values (6, 1);

insert into promotions(id, "description", start_time, end_time, pharmacy_id) values (111, 'New promotion!!!', '2021-05-04','2021-06-04', 1);

insert into vacations(id, start_time, end_time, pharmacy_id, medical_employee_id) values (111, '2021-06-01', '2021-06-11', 1, 5);

insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (1, false, '2020-12-20 12:30:00', '2020-12-20 12:15:00', 3000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (2, false, '2020-11-20 08:45:00', '2020-11-20 08:15:00', 5000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (3, false, '2020-06-20 08:45:00', '2020-06-20 08:15:00', 15000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (4, false, '2021-03-20 08:45:00', '2021-03-20 08:15:00', 6000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (5, false, '2021-05-24 09:45:00', '2021-05-24 09:15:00', 1000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (6, false, '2021-05-24 08:45:00', '2021-05-24 08:15:00', 12000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (7, false, '2021-05-25 08:45:00', '2021-05-25 08:15:00', 6000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (8, false, '2021-05-25 09:45:00', '2021-05-25 09:15:00', 5000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (9, false, '2021-05-26 09:45:00', '2021-05-26 09:15:00', 5000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (10, false, '2021-05-26 10:30:00', '2021-05-26 10:00:00', 5000, 6, 1, 1);
insert into appointments(id, "free", end_time, start_time, price, medical_employee_id, patient_id, pharmacy_id) values (11, false, '2021-05-26 11:45:00', '2021-05-26 11:15:00', 5000, 6, 1, 1);

-- insert into appointments values (1, true, '2020-12-20 12:30:00', '2020-12-20 12:15:00', 3000, 5, 1, 1);
-- insert into appointments values (2, true, '2020-11-20 08:45:00', '2020-11-20 08:15:00', 5000, 2, 1, 1);


insert into appointments values (111, true, '2021-06-02 12:30:00', '2021-06-02 12:15:00', 3000, 5, 1, 1);
insert into appointments values (222, false, '2021-06-02 08:45:00', '2021-06-02 08:15:00', 5000, 2, 1, 1);
insert into appointments values (333, false, '2021-06-03 09:45:00', '2021-06-03 09:00:00', 5000, 2, 1, 1);
insert into appointments values (444, true, '2021-06-03 08:45:00', '2021-06-03 08:15:00', 5000, 2, null, 1);

insert into app_report values(1, 'BO', 111);
insert into app_report values(2, 'BO', 222);

insert into recommended_meds values(1, 20, 2, 555, 1);
insert into recommended_meds values(2, 30, 3, 444, 1);
insert into recommended_meds values(3, 7, 1, 111, 2);


insert into allergies values(1, 'Sta god', 444, 1);

insert into complaints values(1, null, 'Ana Anic neljubazna full', 1);
insert into complaints values(2, 'Nema se novaca. Srdacno, Mika Mikic', 'Zasto imate samo jednu apoteku pobogu?', 1);


-- insert into recipes values(1, 1, 1, 5);
-- insert into recipe_items values(1, 2, 1, 1);
-- insert into recipe_items values(2, 5, 5, 1);

insert into drugs_reservations(id, end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values(1, '2021-05-20', 2, '2021-05-04', true, 111, 1, 1);
insert into drugs_reservations(id, end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values(2, '2020-05-20', 2, '2020-05-04', false, 111, 1, 1);
insert into drugs_reservations(id, end_reservation, quantity, reservation_date, taken, reserved_drug_id, reservation_patient_id, pharmacy_reservation_id) values(3, '2021-07-20', 2, '2021-05-04', false, 111, 1, 1);


insert into availability_request values(1, 10, 111, 6, 1);
insert into availability_request values(2, 5, 222, 6, 1);
insert into availability_request values(3, 7, 333, 6, 1);



insert into shifts(id, start_time, end_time, pharmacy_id, medical_employee_id) values(111, '08:00:00', '14:00:00', 1, 2);
insert into shifts(id, start_time, end_time, pharmacy_id, medical_employee_id) values(222, '15:00:00', '16:00:00', 2, 2);
insert into shifts(id, start_time, end_time, pharmacy_id, medical_employee_id) values(333, '08:00:00', '17:00:00', 1, 6);
