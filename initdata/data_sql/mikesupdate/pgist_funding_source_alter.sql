--
-- PostgreSQL database dump
--

-- Started on 2007-08-19 17:47:46

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 2571 (class 0 OID 19407)
-- Dependencies: 2146
-- Data for Name: pgist_funding_source_alter; Type: TABLE DATA; Schema: public; Owner: pgist
--

COPY pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) FROM stdin;
6613	http://www.google.com	0	0	Toll on new Alaskan Way viaduct alternative	10000	5.25	18	600	t	0	0
8599		0	0	0.1%	1.5230001e+009	0.001	8596	21	f	0	0
71924		0	0	$0.50 per parking visit	2.76e+008	0.5	8756	12	t	0	0
71970		1	1	$1.00 per parking visit	5.28e+008	1	8756	24	t	0	0
8759		0	0	$1.50 per parking visit	7.56e+008	1.5	8756	36	t	0	0
8762		0	0	$2.00 per parking visit	9.6e+008	2	8756	48	t	0	0
8747		0	0	$2 per month paid by employer for each full-time worker	6.68e+008	2	8744	24	f	0	0
72052		0	0	$1 per month paid by employer for each full-time worker	3.42e+008	1	8744	12	f	0	0
72061		0	0	$3 per month paid by employer for each full-tmie worker	9.78e+008	3	8744	36	f	0	0
72064		0	0	$4 per month paid by employer for each full-time worker	1.272e+009	4	8744	48	f	0	0
8750		0	0	$5 per month paid by employer for each full-time worker	1.55e+009	5	8744	60	f	0	0
8717		0	0	2 cents per gallon	5.988e+008	0.02	8714	11	f	0	0
8720		0	0	4 cents per gallon	1.1904e+009	0.039999999	8714	22	f	0	0
8723		0	0	6 cents per gallon	1.7676e+009	0.059999999	8714	33	f	0	0
72097		0	0	8 cents per gallon	2.3232e+009	0.079999998	8714	44	f	0	0
8726		0	0	10 cents per gallon	2.8499999e+009	0.1	8714	55	f	0	0
8729		0	0	12 cents per gallon	3.3408e+009	0.12	8714	65	f	0	0
72106		0	0	14 cents per gallon	3.7884001e+009	0.14	8714	76	f	0	0
72109		0	0	16 cents per gallon	4.1856e+009	0.16	8714	87	f	0	0
72112		0	0	18 cents per gallon	4.5251999e+009	0.18000001	8714	98	f	0	0
8732		0	0	20 cents per gallon	4.8e+009	0.2	8714	109	f	0	0
64864		0	0	0.2%	3.024e+009	0.0020000001	8596	47	f	0	0
8608		0	0	0.3%	4.4610002e+009	0.003	8596	73	f	0	0
64867		0	0	0.4%	5.808e+009	0.0040000002	8596	99	f	0	0
8614		0	0	0.5%	7.5000003e+009	0.0049999999	8596	125	f	0	0
64870		0	0	0.6%	8.112e+009	0.0060000001	8596	151	f	0	0
8617		0	0	0.7%	9.0090004e+009	0.0070000002	8596	177	f	0	0
64873		0	0	0.8%	9.696e+009	0.0080000004	8596	203	f	0	0
64876		0	0	0.9%	1.0143e+010	0.0089999996	8596	229	f	0	0
8620		0	0	1%	1.032e+010	0.0099999998	8596	255	f	0	0
72166		0	0	0.1%	4.45e+008	0.001	8696	14	f	0	0
8699		0	0	0.2%	8.8e+008	0.0020000001	8696	29	f	0	0
72178		0	0	0.3%	1.3049999e+009	0.003	8696	43	f	0	0
8702		0	0	0.4%	1.72e+009	0.0040000002	8696	57	f	0	0
72184		0	0	0.5%	2.1249999e+009	0.0049999999	8696	72	f	0	0
8705		0	0	0.6%	2.52e+009	0.0060000001	8696	86	f	0	0
72192		0	0	0.7%	2.9049999e+009	0.0070000002	8696	100	f	0	0
8708		0	0	0.8%	3.2129999e+009	0.0080000004	8696	115	f	0	0
72201		0	0	0.9%	3.6449999e+009	0.0089999996	8696	129	f	0	0
8738		0	0	6.5% sales tax on gas	2.5413911e+009	0.064999998	8735	124	f	0	0
72207		0	0	8.5% sales tax on gas	3.6486316e+009	0.085000001	8735	162	f	0	0
72210		0	0	8.8% sales tax on gas	3.8e+009	0.088	8735	170	f	0	0
64698		0	0	Fixed rate, $0.20 one way	68250000	0.2	8765	4	t	0	0
8771	www.google.com	0	0	Fixed rate, $0.75 one way	1.0575e+008	0.75	8765	14	t	0	0
8775		0	0	Variable rate (congestion pricing),     $0.40 peak hour and free off-peak one way	91000000	2	8765	2	t	0	0
64713		0	0	Variable rate (congestion pricing), $1 peak hour and $0.20 off-peak one way	1.41e+008	1	8765	8	t	0	0
8789		0	0	Fixed rate, $1.25 one way	4.5824998e+008	1.25	8785	30	t	0	0
64722		0	0	Fixed rate, $0.50 one way	2.79e+008	0.5	8785	12	t	0	0
8795		0	0	Variable rate (congestion pricing), $1.25 peak hour and 0.25 off-peak	4.5824998e+008	2	8785	30	t	0	0
72238		0.5	2.25	Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way	6.11e+008	1	8785	26	t	0	0
8804		0	0	Fixed rate, $0.50 one way	2.79e+008	0.5	8801	12	t	0	0
72244		1.25	1.25	Fixed rate, $1.25 one way	4.5824998e+008	1.25	8801	30	t	0	0
8816		0	0	Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way	3.72e+008	2	8801	14	t	0	0
64725		0	0	Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way	6.11e+008	1	8801	26	t	0	0
8835		0	0	Fixed rate, $1.50 one way	3.15e+008	1.5	8832	36	t	0	0
72269		0.40000001	2.5	Variable rate (congestion pricing), $2.50 peak hour and $0.40 off-peak one way	4.2e+008	1	8832	26	t	0	0
8838		0	0	Variable rate (congestion pricing), $1.75 peak hour and $0.25 off-peak one way	2.64e+008	2	8832	18	t	0	0
8844		0	0	Fixed rate, $0.40 one way	1.4475e+008	2	8841	6	t	0	0
64749		0	0	Fixed rate, $0.50 one way	1.98e+008	0.5	8832	12	t	0	0
72283		1.25	1.25	Fixed rate, $1.25 one way	2.34e+008	1.25	8841	20	t	0	0
8847		0	0	Variable rate (congestion pricing), $1.50 peak hour and free off-peak one way	1.93e+008	2	8841	6	t	0	0
72289		0.40000001	2	Variable rate (congestion pricing), $2.00 peak hour and $0.40 off-peak one way	3.12e+008	1	8841	13	t	0	0
8826		0	0	Fixed rate $0.50 one way	1.995e+008	2	8823	12	t	0	0
72295		2.5	2.5	Fixed rate, $2.50 one way	3.12e+008	2.5	8823	60	t	0	0
8829		0	0	Variable rate (congestion pricing) $2.75 peak hour and free off-peak one way	2.66e+008	2	8823	22	t	0	0
72301		0.75	4	Variable rate (congestion pricing) $4.00 peak hour and $0.75 off-peak one way	4.16e+008	1	8823	44	t	0	0
72304		0	0	$15 per vehicle	4.3875002e+008	15	8623	15	f	0	0
8642		0	0	$25 per vehicle	7.1875002e+008	25	8623	25	f	0	0
72326		0	0	$50 per vehicle	1.3750001e+009	50	8623	50	f	0	0
8648		0	0	$100 per vehicle	2.5e+009	100	8623	100	f	0	0
72350		0	0	$125 per vehicle	2.9687501e+009	125	8623	125	f	0	0
8690		0	0	$150 per vehicle	3.3750001e+009	150	8623	150	f	0	0
89403		0.5	0.5	Fixed rate, $0.50 one way	3e+008	0.5	89400	10	t	0	0
89406		1	1	Fixed rate, $1.00 one way	5e+008	1	89400	30	t	0	0
89409		0.25	1.25	Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way	3e+008	1	89400	9	t	0	0
89412		1	1	Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way	5e+008	1	89400	9	t	0	0
89418		1	1	Fixed rate, $0.50 one way	3000000	1	89415	1	t	0	0
89421		1	1	Fixed rate, $1.00 one way	3000000	1	89415	1	t	0	0
89424	1	1	1	Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way	4000000	1	89415	1	t	0	0
89427		1	1	Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way	4000000	1	89415	1	t	0	0
8711		0	0	1.0%	4e+009	0.0099999998	8696	143	f	1	1
8645		0	0	$75 per vehicle	1.96875e+009	75	8623	75	f	1	1
\.


-- Completed on 2007-08-19 17:47:46

--
-- PostgreSQL database dump complete
--

