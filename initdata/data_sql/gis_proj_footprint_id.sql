select setval('gis_proj_footprint_id', (select max(id) from pgist_data_project_footprint));
