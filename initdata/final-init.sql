INSERT INTO pgist_situation_template (id, name, path) VALUES (1, 'LIT template', '/WEB-INF/config/template_LIT.xml');

ALTER TABLE pgist_travel_marker DROP COLUMN point;
ALTER TABLE pgist_travel_marker ADD COLUMN point geometry;
ALTER TABLE pgist_travel_trip DROP COLUMN route;
alter table pgist_travel_trip ADD COLUMN route geometry;

