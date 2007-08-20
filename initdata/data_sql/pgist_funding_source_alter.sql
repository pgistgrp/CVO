--
-- PostgreSQL database dump
--

-- Started on 2007-07-06 02:45:30 Pacific Daylight Time

SET client_encoding = 'SQL_ASCII';
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2196 (class 0 OID 33079)
-- Dependencies: 1777
-- Data for Name: pgist_funding_source_alter; Type: TABLE DATA; Schema: public; Owner: pgist
--

INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (3407, '', 0, 0, 'FundAlt222', 100, 9, 3398, 100, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (3410, '', 0, 0, 'FundAlt333', 110, 10, 3401, 110, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (3413, '', 0, 0, 'FundAlt444', 120, 12, 3401, 120, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (3404, '', 0, 0, 'FundAlt111', 80, 0.029999999, 3398, 80, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8599, '', 0, 0, '0.1 percentage-point increase', 2400, 0.1, 8596, 7, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8608, '', 0, 0, '0.3 percentage-point increase', 7015, 0.30000001, 8596, 22, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8614, '', 0, 0, '0.5 percentage-point increase', 11815, 0.5, 8596, 37, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8617, '', 0, 0, '0.7 percentage-point increase', 16431, 0.69999999, 8596, 51, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8620, '', 0, 0, '1.0 percentage-point increase', 23446, 1, 8596, 73, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8642, '', 0, 0, '$25 per vehicle', 700, 25, 8623, 30, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8645, '', 0, 0, '$75 per vehicle', 2100, 75, 8623, 90, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8648, '', 0, 0, '$100 per vehicle', 2800, 100, 8623, 120, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (6613, 'http://www.google.com', 0, 0, 'Toll on new Alaskan Way viaduct alternative', 10000, 5.25, 18, 600, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8690, '', 0, 0, '$150 per vehicle', 4200, 150, 8623, 180, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8699, '', 0, 0, '0.2 percentage-point increase', 1268, 0.2, 8696, 24, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8702, '', 0, 0, '0.4 percentage-point increase', 2535, 0.40000001, 8696, 48, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8705, '', 0, 0, '0.6 percentage-point increase', 3803, 0.60000002, 8696, 72, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8708, '', 0, 0, '0.8 percentage-point increase', 5070, 0.80000001, 8696, 96, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8711, '', 0, 0, '1.0 percentage-point increase', 6338, 1, 8696, 120, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8717, '', 0, 0, '2.3 cents (indexed to increase annually with inflation)', 400, 0.023, 8714, 8, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8720, '', 0, 0, '4 cents (indexed to increase annually with inflation)', 2535, 0.039999999, 8714, 13, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8723, '', 0, 0, '6 cents (indexed to increase annually with inflation)', 1400, 0.059999999, 8714, 20, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8726, '', 0, 0, '10 cents (indexed to increase annually with inflation)', 2400, 0.1, 8714, 33, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8729, '', 0, 0, '15 cents (indexed to increase annually with inflation)', 3600, 0.15000001, 8714, 50, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8732, '', 0, 0, '20 cents (indexed to increase annually with inflation)', 4800, 0.2, 8714, 67, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8738, '', 0, 0, '8.8% sales tax on gas', 2200, 8.8000002, 8735, 73, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8750, '', 0, 0, '$5 per month payed by employer for each full-time worker', 1300, 5, 8744, 60, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8747, '', 0, 0, '$2 per month payed by employer for each full-time worker', 500, 2, 8744, 24, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8759, '', 0, 0, '$1.50 per parking visit', 400, 1.5, 8756, 30, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8762, '', 0, 0, '$2.00 per parking visit', 600, 2, 8756, 40, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8771, 'www.google.com', 0, 0, 'Fixed rate', 400, 2, 8765, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8775, '', 0, 0, 'Variable rate (congestion pricing)', 400, 2, 8765, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8789, '', 0, 0, 'Fixed rate', 400, 2, 8785, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8795, '', 0, 0, 'Variable rate (congestion pricing)', 400, 2, 8785, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8838, '', 0, 2, 'Variable rate (congestion pricing)', 400, 2, 8832, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8804, '', 0, 0, 'Fixed rate', 400, 2, 8801, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8816, '', 0.5, 2.5, 'Variable', 400, 2, 8801, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8826, '', 1.75, 1.75, 'Fixed rate', 400, 2, 8823, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8835, '', 1, 1, 'Fixed rate', 400, 2, 8832, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8844, '', 0.75, 0.75, 'Fixed rate', 400, 2, 8841, 50, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8847, '', 0, 1, 'Variable rate (congestion pricing)', 400, 2, 8841, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8829, '', 0.5, 2.5, 'Variable rate (congestion pricing)', 400, 2, 8823, 30, true, 0, 0);


-- Completed on 2007-07-06 02:45:30 Pacific Daylight Time

--
-- PostgreSQL database dump complete
--

