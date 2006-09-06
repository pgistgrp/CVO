-- Table: pgist_data_project_footprint

-- DROP TABLE pgist_data_project_footprint;

CREATE TABLE pgist_data_project_footprint
(
  id int4 NOT NULL DEFAULT nextval('gis_proj_footprint_id'::regclass),
  the_geom geometry,
  ref_prj_name text,
  CONSTRAINT gis_project_footprint_pkey PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE pgist_data_project_footprint OWNER TO pgist;




