--delete existing data
DELETE from calendar_entry;

--init db by new records
INSERT INTO calendar_entry(id, description, date) VALUES (1, 'BD party (Alice)',  '2012-09-17');
INSERT INTO calendar_entry(id, description, date) VALUES (2, 'New Year', '2018-12-31');
INSERT INTO calendar_entry(id, description, date) VALUES (3, 'Appointment with doctor' , '2018-03-12');
INSERT INTO calendar_entry(id, description, date) VALUES (4, 'Party'  ,'2018-09-17');
INSERT INTO calendar_entry(id, description, date) VALUES (5, 'Training','2012-01-12');
INSERT INTO calendar_entry(id, description, date) VALUES (6, 'Business trip', '2018-11-15');
INSERT INTO calendar_entry(id, description, date) VALUES (7, 'BD party (Mark)', '2012-01-11');
INSERT INTO calendar_entry(id, description, date) VALUES (8, 'Book exhibition' ,'2018-11-17');
INSERT INTO calendar_entry(id, description, date) VALUES (9, 'Shopping', '2016-02-13');
INSERT INTO calendar_entry(id, description, date) VALUES (10, 'Conference', '2011-02-16');