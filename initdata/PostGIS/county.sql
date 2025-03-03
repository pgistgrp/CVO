-- Table: pgist_data_county

-- DROP TABLE pgist_data_county;

CREATE TABLE pgist_data_county
(
  id int4 NOT NULL DEFAULT nextval('gis_proj_footprint_id'::regclass),
  name varchar,
  state_name varchar,
  state_fips varchar,
  cnty_fips varchar,
  fips varchar,
  area float8,
  pop1990 int8,
  pop1999 int8,
  pop90_sqmi int4,
  households int4,
  males int4,
  females int4,
  white int4,
  black int4,
  ameri_es int4,
  asian_pi int4,
  other int4,
  hispanic int4,
  age_under5 int4,
  age_5_17 int4,
  age_18_29 int4,
  age_30_49 int4,
  age_50_64 int4,
  age_65_up int4,
  nevermarry int4,
  married int4,
  separated int4,
  widowed int4,
  divorced int4,
  hsehld_1_m int4,
  hsehld_1_f int4,
  marhh_chd int4,
  marhh_no_c int4,
  mhh_child int4,
  fhh_child int4,
  hse_units int4,
  vacant int4,
  owner_occ int4,
  renter_occ int4,
  median_val int4,
  medianrent int4,
  units_1det int4,
  units_1att int4,
  units2 int4,
  units3_9 int4,
  units10_49 int4,
  units50_up int4,
  mobilehome int4,
  no_farms87 int4,
  avg_size87 int4,
  crop_acr87 int4,
  avg_sale87 int4,
  the_geom geometry,
  CONSTRAINT pgist_data_county_pkey PRIMARY KEY (id),
  CONSTRAINT enforce_dims_the_geom CHECK (ndims(the_geom) = 2),
  CONSTRAINT enforce_geotype_the_geom CHECK (geometrytype(the_geom) = 'MULTIPOLYGON'::text OR the_geom IS NULL),
  CONSTRAINT enforce_srid_the_geom CHECK (srid(the_geom) = -1)
) 
WITHOUT OIDS;
ALTER TABLE pgist_data_county OWNER TO pgist;




