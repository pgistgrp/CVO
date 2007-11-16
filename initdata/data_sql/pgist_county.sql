--
-- PostgreSQL database dump
--

-- Started on 2007-08-01 15:26:12

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1878 (class 1259 OID 400782)
-- Dependencies: 4
-- Name: pgist_county; Type: TABLE; Schema: public; Owner: pgist; Tablespace: 
--


--
-- TOC entry 2345 (class 0 OID 400782)
-- Dependencies: 1878
-- Data for Name: pgist_county; Type: TABLE DATA; Schema: public; Owner: pgist
--

INSERT INTO pgist_county (id, name, quotalimit, tempquotanumber, reportstats_id) VALUES (3084, 'Snohomish', 0, 0, NULL);
INSERT INTO pgist_county (id, name, quotalimit, tempquotanumber, reportstats_id) VALUES (3089, 'Seattle', 0, 0, NULL);
INSERT INTO pgist_county (id, name, quotalimit, tempquotanumber, reportstats_id) VALUES (3112, 'South King', 0, 0, NULL);
INSERT INTO pgist_county (id, name, quotalimit, tempquotanumber, reportstats_id) VALUES (3117, 'East King', 0, 0, NULL);
INSERT INTO pgist_county (id, name, quotalimit, tempquotanumber, reportstats_id) VALUES (3122, 'Pierce', 0, 0, NULL);


--
-- TOC entry 2343 (class 2606 OID 400785)
-- Dependencies: 1878 1878
-- Name: pgist_county_pkey; Type: CONSTRAINT; Schema: public; Owner: pgist; Tablespace: 
--

--
-- TOC entry 2344 (class 2606 OID 401653)
-- Dependencies: 1878 1945
-- Name: fk7554abd68bb21e45; Type: FK CONSTRAINT; Schema: public; Owner: pgist
--



-- Completed on 2007-08-01 15:26:12

--
-- PostgreSQL database dump complete
--

