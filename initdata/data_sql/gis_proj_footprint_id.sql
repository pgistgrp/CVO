select setval('gis_proj_footprint_id', (selct max(id) from pgist_data_project_footprint));
