INSERT INTO station (name, address, description, latitude, longitude)
VALUES ('Grun station', 'Tavlays st. 34/3', 'Good servicestation', 23.824951, 53.678337);

INSERT INTO mechanic (nickname, profile, station_id)
VALUES ('vladimirapolaiko', row('Vladimir', 'Apolaiko', 'Sergeevich', 'KH23069993','24445669697K3N', 'g.Grodno, Tavlaya st. 34/3-27', '+375336878957'), 1);

INSERT INTO "order" (status) VALUES ('init');

INSERT INTO mechanic_order (order_id, mechanic_id) VALUES (1, 1);
