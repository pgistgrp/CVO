--
-- PostgreSQL database dump
--

-- Started on 2007-08-20 14:57:58

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1900 (class 1259 OID 506956)
-- Dependencies: 4
-- Name: pgist_funding_source_alter; Type: TABLE; Schema: public; Owner: pgist; Tablespace: 
--

CREATE TABLE pgist_funding_source_alter (
    id bigint NOT NULL,
    sourceurl character varying(255) NOT NULL,
    offpeaktripsrate real,
    peakhourtripsrate real,
    name character varying(255) NOT NULL,
    revenue real NOT NULL,
    taxrate real NOT NULL,
    source_id bigint,
    avgcost real,
    toll boolean,
    numvotes integer NOT NULL,
    yesvotes integer NOT NULL
);


ALTER TABLE public.pgist_funding_source_alter OWNER TO pgist;

--
-- TOC entry 2325 (class 0 OID 506956)
-- Dependencies: 1900
-- Data for Name: pgist_funding_source_alter; Type: TABLE DATA; Schema: public; Owner: pgist
--

INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (6613, 'http://www.google.com', 0, 0, 'Toll on new Alaskan Way viaduct alternative', 10000, 5.25, 18, 600, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8599, '', 0, 0, '0.1%', 1.5230001e+009, 0.001, 8596, 21, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (71924, '', 0, 0, '$0.50 per parking visit', 2.76e+008, 0.5, 8756, 12, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (71970, '', 1, 1, '$1.00 per parking visit', 5.28e+008, 1, 8756, 24, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8759, '', 0, 0, '$1.50 per parking visit', 7.56e+008, 1.5, 8756, 36, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8762, '', 0, 0, '$2.00 per parking visit', 9.6e+008, 2, 8756, 48, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8747, '', 0, 0, '$2 per month paid by employer for each full-time worker', 6.68e+008, 2, 8744, 24, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72052, '', 0, 0, '$1 per month paid by employer for each full-time worker', 3.42e+008, 1, 8744, 12, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72061, '', 0, 0, '$3 per month paid by employer for each full-tmie worker', 9.78e+008, 3, 8744, 36, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72064, '', 0, 0, '$4 per month paid by employer for each full-time worker', 1.272e+009, 4, 8744, 48, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8750, '', 0, 0, '$5 per month paid by employer for each full-time worker', 1.55e+009, 5, 8744, 60, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8717, '', 0, 0, '2 cents per gallon', 5.988e+008, 0.02, 8714, 11, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8720, '', 0, 0, '4 cents per gallon', 1.1904e+009, 0.039999999, 8714, 22, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8723, '', 0, 0, '6 cents per gallon', 1.7676e+009, 0.059999999, 8714, 33, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72097, '', 0, 0, '8 cents per gallon', 2.3232e+009, 0.079999998, 8714, 44, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8726, '', 0, 0, '10 cents per gallon', 2.8499999e+009, 0.1, 8714, 55, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8729, '', 0, 0, '12 cents per gallon', 3.3408e+009, 0.12, 8714, 65, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72106, '', 0, 0, '14 cents per gallon', 3.7884001e+009, 0.14, 8714, 76, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72109, '', 0, 0, '16 cents per gallon', 4.1856e+009, 0.16, 8714, 87, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72112, '', 0, 0, '18 cents per gallon', 4.5251999e+009, 0.18000001, 8714, 98, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8732, '', 0, 0, '20 cents per gallon', 4.8e+009, 0.2, 8714, 109, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64864, '', 0, 0, '0.2%', 3.024e+009, 0.0020000001, 8596, 47, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8608, '', 0, 0, '0.3%', 4.4610002e+009, 0.003, 8596, 73, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64867, '', 0, 0, '0.4%', 5.808e+009, 0.0040000002, 8596, 99, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8614, '', 0, 0, '0.5%', 7.5000003e+009, 0.0049999999, 8596, 125, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64870, '', 0, 0, '0.6%', 8.112e+009, 0.0060000001, 8596, 151, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8617, '', 0, 0, '0.7%', 9.0090004e+009, 0.0070000002, 8596, 177, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64873, '', 0, 0, '0.8%', 9.696e+009, 0.0080000004, 8596, 203, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64876, '', 0, 0, '0.9%', 1.0143e+010, 0.0089999996, 8596, 229, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8620, '', 0, 0, '1%', 1.032e+010, 0.0099999998, 8596, 255, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72166, '', 0, 0, '0.1%', 4.45e+008, 0.001, 8696, 14, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8699, '', 0, 0, '0.2%', 8.8e+008, 0.0020000001, 8696, 29, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72178, '', 0, 0, '0.3%', 1.3049999e+009, 0.003, 8696, 43, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8702, '', 0, 0, '0.4%', 1.72e+009, 0.0040000002, 8696, 57, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72184, '', 0, 0, '0.5%', 2.1249999e+009, 0.0049999999, 8696, 72, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8705, '', 0, 0, '0.6%', 2.52e+009, 0.0060000001, 8696, 86, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72192, '', 0, 0, '0.7%', 2.9049999e+009, 0.0070000002, 8696, 100, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8708, '', 0, 0, '0.8%', 3.2129999e+009, 0.0080000004, 8696, 115, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72201, '', 0, 0, '0.9%', 3.6449999e+009, 0.0089999996, 8696, 129, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8738, '', 0, 0, '6.5% sales tax on gas', 2.5413911e+009, 0.064999998, 8735, 124, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72207, '', 0, 0, '8.5% sales tax on gas', 3.6486316e+009, 0.085000001, 8735, 162, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72210, '', 0, 0, '8.8% sales tax on gas', 3.8e+009, 0.088, 8735, 170, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64698, '', 0, 0, 'Fixed rate, $0.20 one way', 68250000, 0.2, 8765, 4, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8771, 'www.google.com', 0, 0, 'Fixed rate, $0.75 one way', 1.0575e+008, 0.75, 8765, 14, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8775, '', 0, 0, 'Variable rate (congestion pricing),     $0.40 peak hour and free off-peak one way', 91000000, 2, 8765, 2, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64713, '', 0, 0, 'Variable rate (congestion pricing), $1 peak hour and $0.20 off-peak one way', 1.41e+008, 1, 8765, 8, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8789, '', 0, 0, 'Fixed rate, $1.25 one way', 4.5824998e+008, 1.25, 8785, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64722, '', 0, 0, 'Fixed rate, $0.50 one way', 2.79e+008, 0.5, 8785, 12, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8795, '', 0, 0, 'Variable rate (congestion pricing), $1.25 peak hour and 0.25 off-peak', 4.5824998e+008, 2, 8785, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72238, '', 0.5, 2.25, 'Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way', 6.11e+008, 1, 8785, 26, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8804, '', 0, 0, 'Fixed rate, $0.50 one way', 2.79e+008, 0.5, 8801, 12, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72244, '', 1.25, 1.25, 'Fixed rate, $1.25 one way', 4.5824998e+008, 1.25, 8801, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8816, '', 0, 0, 'Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way', 3.72e+008, 2, 8801, 14, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64725, '', 0, 0, 'Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way', 6.11e+008, 1, 8801, 26, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8835, '', 0, 0, 'Fixed rate, $1.50 one way', 3.15e+008, 1.5, 8832, 36, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72269, '', 0.40000001, 2.5, 'Variable rate (congestion pricing), $2.50 peak hour and $0.40 off-peak one way', 4.2e+008, 1, 8832, 26, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8838, '', 0, 0, 'Variable rate (congestion pricing), $1.75 peak hour and $0.25 off-peak one way', 2.64e+008, 2, 8832, 18, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8844, '', 0, 0, 'Fixed rate, $0.40 one way', 1.4475e+008, 2, 8841, 6, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (64749, '', 0, 0, 'Fixed rate, $0.50 one way', 1.98e+008, 0.5, 8832, 12, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72283, '', 1.25, 1.25, 'Fixed rate, $1.25 one way', 2.34e+008, 1.25, 8841, 20, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8847, '', 0, 0, 'Variable rate (congestion pricing), $1.50 peak hour and free off-peak one way', 1.93e+008, 2, 8841, 6, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72289, '', 0.40000001, 2, 'Variable rate (congestion pricing), $2.00 peak hour and $0.40 off-peak one way', 3.12e+008, 1, 8841, 13, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8826, '', 0, 0, 'Fixed rate $0.50 one way', 1.995e+008, 2, 8823, 12, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72295, '', 2.5, 2.5, 'Fixed rate, $2.50 one way', 3.12e+008, 2.5, 8823, 60, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8829, '', 0, 0, 'Variable rate (congestion pricing) $2.75 peak hour and free off-peak one way', 2.66e+008, 2, 8823, 22, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72301, '', 0.75, 4, 'Variable rate (congestion pricing) $4.00 peak hour and $0.75 off-peak one way', 4.16e+008, 1, 8823, 44, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72304, '', 0, 0, '$15 per vehicle', 4.3875002e+008, 15, 8623, 15, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8642, '', 0, 0, '$25 per vehicle', 7.1875002e+008, 25, 8623, 25, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72326, '', 0, 0, '$50 per vehicle', 1.3750001e+009, 50, 8623, 50, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8648, '', 0, 0, '$100 per vehicle', 2.5e+009, 100, 8623, 100, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (72350, '', 0, 0, '$125 per vehicle', 2.9687501e+009, 125, 8623, 125, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8690, '', 0, 0, '$150 per vehicle', 3.3750001e+009, 150, 8623, 150, false, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89403, '', 0.5, 0.5, 'Fixed rate, $0.50 one way', 3e+008, 0.5, 89400, 10, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89406, '', 1, 1, 'Fixed rate, $1.00 one way', 5e+008, 1, 89400, 30, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89409, '', 0.25, 1.25, 'Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way', 3e+008, 1, 89400, 9, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89412, '', 1, 1, 'Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way', 5e+008, 1, 89400, 9, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89418, '', 1, 1, 'Fixed rate, $0.50 one way', 3000000, 1, 89415, 1, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89421, '', 1, 1, 'Fixed rate, $1.00 one way', 3000000, 1, 89415, 1, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89424, '1', 1, 1, 'Variable rate (congestion pricing), $1.25 peak hour and $0.25 off-peak one way', 4000000, 1, 89415, 1, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (89427, '', 1, 1, 'Variable rate (congestion pricing), $2.25 peak hour and $0.50 off-peak one way', 4000000, 1, 89415, 1, true, 0, 0);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8711, '', 0, 0, '1.0%', 4e+009, 0.0099999998, 8696, 143, false, 1, 1);
INSERT INTO pgist_funding_source_alter (id, sourceurl, offpeaktripsrate, peakhourtripsrate, name, revenue, taxrate, source_id, avgcost, toll, numvotes, yesvotes) VALUES (8645, '', 0, 0, '$75 per vehicle', 1.96875e+009, 75, 8623, 75, false, 1, 1);


--
-- TOC entry 2323 (class 2606 OID 506959)
-- Dependencies: 1900 1900
-- Name: pgist_funding_source_alter_pkey; Type: CONSTRAINT; Schema: public; Owner: pgist; Tablespace: 
--

ALTER TABLE ONLY pgist_funding_source_alter
    ADD CONSTRAINT pgist_funding_source_alter_pkey PRIMARY KEY (id);


--
-- TOC entry 2324 (class 2606 OID 507978)
-- Dependencies: 1900 1898
-- Name: fk79b66a8022312b61; Type: FK CONSTRAINT; Schema: public; Owner: pgist
--

ALTER TABLE ONLY pgist_funding_source_alter
    ADD CONSTRAINT fk79b66a8022312b61 FOREIGN KEY (source_id) REFERENCES pgist_funding_source(id);


-- Completed on 2007-08-20 14:57:59

--
-- PostgreSQL database dump complete
--

