INSERT INTO station (name, address, description, latitude, longitude)
VALUES ('Grun station', 'Tavlays st. 34/3', 'Good servicestation', 23.824951, 53.678337);

INSERT INTO mechanic (name, surname, patronymic, profile, station_id)
VALUES ('Vladimir', 'Apolaiko', 'Sergeevich', row('+375336878957'), 1);

INSERT INTO "order" (status) VALUES ('init');

INSERT INTO mechanic_order (order_id, mechanic_id) VALUES (1, 1);
