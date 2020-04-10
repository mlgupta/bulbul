--
-- PostgreSQL database dump
--

\connect - bulbul

--
-- TOC entry 1 (OID 0)
-- Name: bulbulfo; Type: DATABASE; Schema: -; Owner: bulbul
--

CREATE DATABASE bulbulfo WITH TEMPLATE = template0 ENCODING = 5;


\connect bulbulfo bulbul

--
-- TOC entry 2 (OID 191160)
-- Name: bulbul; Type: SCHEMA; Schema: -; Owner: bulbul
--

CREATE SCHEMA bulbul;


\connect - postgres

SET search_path = public, pg_catalog;

--
-- TOC entry 20 (OID 191161)
-- Name: plpgsql_call_handler (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler () RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


--
-- TOC entry 19 (OID 191162)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler;


--
-- TOC entry 21 (OID 191163)
-- Name: dblink_connect (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect (text) RETURNS text
    AS '$libdir/dblink', 'dblink_connect'
    LANGUAGE c STRICT;


--
-- TOC entry 22 (OID 191164)
-- Name: dblink_disconnect (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_disconnect () RETURNS text
    AS '$libdir/dblink', 'dblink_disconnect'
    LANGUAGE c STRICT;


--
-- TOC entry 23 (OID 191165)
-- Name: dblink_open (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open (text, text) RETURNS text
    AS '$libdir/dblink', 'dblink_open'
    LANGUAGE c STRICT;


--
-- TOC entry 24 (OID 191166)
-- Name: dblink_fetch (text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch (text, integer) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_fetch'
    LANGUAGE c STRICT;


--
-- TOC entry 25 (OID 191167)
-- Name: dblink_close (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close (text) RETURNS text
    AS '$libdir/dblink', 'dblink_close'
    LANGUAGE c STRICT;


--
-- TOC entry 26 (OID 191168)
-- Name: dblink (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink (text, text) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_record'
    LANGUAGE c STRICT;


--
-- TOC entry 27 (OID 191169)
-- Name: dblink (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink (text) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_record'
    LANGUAGE c STRICT;


--
-- TOC entry 28 (OID 191170)
-- Name: dblink_exec (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec (text, text) RETURNS text
    AS '$libdir/dblink', 'dblink_exec'
    LANGUAGE c STRICT;


--
-- TOC entry 29 (OID 191171)
-- Name: dblink_exec (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec (text) RETURNS text
    AS '$libdir/dblink', 'dblink_exec'
    LANGUAGE c STRICT;


--
-- TOC entry 3 (OID 191173)
-- Name: dblink_pkey_results; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE dblink_pkey_results AS ("position" integer,
	colname text);


--
-- TOC entry 30 (OID 191174)
-- Name: dblink_get_pkey (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_pkey (text) RETURNS SETOF dblink_pkey_results
    AS '$libdir/dblink', 'dblink_get_pkey'
    LANGUAGE c STRICT;


--
-- TOC entry 31 (OID 191175)
-- Name: dblink_build_sql_insert (text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_insert (text, int2vector, integer, text[], text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_insert'
    LANGUAGE c STRICT;


--
-- TOC entry 32 (OID 191176)
-- Name: dblink_build_sql_delete (text, int2vector, integer, text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_delete (text, int2vector, integer, text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_delete'
    LANGUAGE c STRICT;


--
-- TOC entry 33 (OID 191177)
-- Name: dblink_build_sql_update (text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_update (text, int2vector, integer, text[], text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_update'
    LANGUAGE c STRICT;


--
-- TOC entry 34 (OID 191178)
-- Name: dblink_current_query (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_current_query () RETURNS text
    AS '$libdir/dblink', 'dblink_current_query'
    LANGUAGE c;


\connect - bulbul

SET search_path = bulbul, pg_catalog;

--
-- TOC entry 10 (OID 191179)
-- Name: customer_profile_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE customer_profile_tbl (
    customer_profile_tbl_pk integer DEFAULT nextval('customer_profile_tbl_seq'::text) NOT NULL,
    customer_email_id character varying(25) NOT NULL,
    customer_password character varying(10) NOT NULL,
    customer_title character(3),
    customer_first_name character varying(20),
    customer_last_name character varying(20),
    address1 character varying(25),
    address2 character varying(25),
    address3 character varying(25),
    city character varying(25),
    state character varying(25),
    is_state_listed integer,
    country character varying(25),
    is_country_listed integer,
    pincode character varying(10),
    phone1 character varying(15),
    phone2 character varying(15),
    mobile character varying(15),
    created_on timestamp without time zone NOT NULL,
    is_active integer DEFAULT 1 NOT NULL
);


--
-- TOC entry 11 (OID 191181)
-- Name: design_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE design_tbl (
    design_tbl_pk integer DEFAULT nextval('design_tbl_seq'::text) NOT NULL,
    email_id integer NOT NULL,
    merchandise_color_tbl_pk integer NOT NULL,
    design_name character varying(15) NOT NULL,
    design_4_char_code character(4) NOT NULL,
    design oid NOT NULL,
    is_published integer NOT NULL,
    is_active integer NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 12 (OID 191183)
-- Name: customer_design_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE customer_design_tbl (
    customer_design_tbl_pk integer DEFAULT nextval('customer_design_tbl_seq'::text) NOT NULL,
    customer_profile_tbl_pk integer NOT NULL,
    merchandise_color_tbl_pk integer NOT NULL,
    customer_design_name character varying(15) NOT NULL,
    customer_design oid NOT NULL,
    is_published integer NOT NULL,
    is_active integer NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 4 (OID 248172)
-- Name: customer_design_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE customer_design_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 6 (OID 248174)
-- Name: customer_profile_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE customer_profile_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 8 (OID 248176)
-- Name: design_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE design_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 35 (OID 256307)
-- Name: sp_ins_customer_profile_tbl (character varying, character varying, character, character varying, character varying, character varying, character varying, character varying, character varying, character varying, integer, character varying, integer, character varying, character varying, character varying, character varying, date); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_customer_profile_tbl (character varying, character varying, character, character varying, character varying, character varying, character varying, character varying, character varying, character varying, integer, character varying, integer, character varying, character varying, character varying, character varying, date) RETURNS integer
    AS '
declare
txtEmailId              alias for $1;
txtCustomerPassword     alias for $2;
txtCustomerTitle        alias for $3;
txtCustomerFName        alias for $4;
txtCustomerLName        alias for $5;
txtAddress1             alias for $6;
txtAddress2             alias for $7;
txtAddress3             alias for $8;
txtCity                 alias for $9;
txtState                alias for $10;
intIsStateListed        alias for $11;
txtCountry              alias for $12;
intIsCountryListed      alias for $13;
txtPincode              alias for $14;
txtPhone1               alias for $15;
txtPhone2               alias for $16;
txtmobile               alias for $17;
dtCreatedOn             alias for $18;
intCustomerProfTblPk  integer;
begin
 INSERT INTO customer_profile_tbl(customer_email_id,customer_password,customer_title,customer_first_name,customer_last_name,address1,address2,address3,city,state,is_state_listed,country,is_country_listed,pincode,phone1,phone2,mobile,created_on) VALUES (txtEmailId,txtCustomerPassword,txtCustomerTitle,txtCustomerFName,txtCustomerLName,txtAddress1,txtAddress2,txtAddress3,txtCity,txtState,intIsStateListed,txtCountry,intIsCountryListed,txtPincode,txtPhone1,txtPhone2,txtmobile,dtCreatedOn);
 if found then
 select into intCustomerProfTblPk currval(''customer_profile_tbl_seq'');
 return intCustomerProfTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 36 (OID 256311)
-- Name: sp_upd_customer_profile_tbl (integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, integer, character varying, integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_customer_profile_tbl (integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, integer, character varying, integer, character varying, character varying, character varying, character varying) RETURNS integer
    AS '
declare
intCustomerProfTblPk      alias for $1;
txtEmailId                alias for $2;
txtCustomerPassword       alias for $3;
txtCustomerTitle          alias for $4;
txtCustomerFName          alias for $5;
txtCustomerLName          alias for $6;
txtAddress1               alias for $7;
txtAddress2               alias for $8;
txtAddress3               alias for $9;
txtCity                   alias for $10;
txtState                  alias for $11;
intIsStateListed          alias for $12;
txtCountry                alias for $13;
intIsCountryListed        alias for $14;
txtPincode                alias for $15;
txtPhone1                 alias for $16;
txtPhone2                 alias for $17;
txtmobile                 alias for $18;
begin                     
UPDATE customer_profile_tbl SET customer_email_id=txtEmailId,customer_password=txtCustomerPassword,customer_title=txtCustomerTitle,customer_first_name=txtCustomerFName,customer_last_name=txtCustomerLName,address1=txtAddress1,address2=txtAddress2,address3=txtAddress3,city=txtCity,state=txtState,is_state_listed=intIsStateListed,country=txtCountry,is_country_listed=intIsCountryListed,pincode=txtPincode,phone1=txtPhone1,phone2=txtPhone2,mobile=txtmobile  where customer_profile_tbl_pk=intCustomerProfTblPk;
if found then 
return intCustomerProfTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 37 (OID 256312)
-- Name: sp_del_customer_profile_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_customer_profile_tbl (integer) RETURNS integer
    AS '
declare
intCustomerProfTblPk alias for $1;
begin
DELETE FROM customer_profile_tbl where customer_profile_tbl_pk=intCustomerProfTblPk;        
if found then 
return intCustomerProfTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 38 (OID 256314)
-- Name: sp_act_deact_customer_profile_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_customer_profile_tbl (integer, integer) RETURNS integer
    AS 'declare
 intCustomerProfTblPk alias for $1;
 intIsActive          alias for $2;
begin
UPDATE customer_profile_tbl SET is_active=intIsActive where customer_profile_tbl_pk=intCustomerProfTblPk;
if found then 
return intCustomerProfTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 40 (OID 256315)
-- Name: sp_change_password_customer (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_change_password_customer (integer, character varying, character varying) RETURNS integer
    AS '
declare
  intCustomerProfTblPk        alias for $1;
  txtOldPassword              alias for $2;
  txtNewPassword              alias for $3;
  txtCustomerPassword         varchar;
  intCustomerProfileTblPk_var integer;
begin
intCustomerProfileTblPk_var := intCustomerProfTblPk;
select into txtCustomerPassword customer_password from customer_profile_tbl where customer_profile_tbl_pk=intCustomerProfTblPk;
if found then
 if txtCustomerPassword = txtOldPassword then
  update customer_profile_tbl set customer_password=txtNewPassword where customer_profile_tbl_pk=intCustomerProfTblPk;
  if not found then
    intCustomerProfileTblPk_var:=-1;
  end if;
 else
  intCustomerProfileTblPk_var :=-1;
  raise exception ''Wrong Old Password'';
 end if;
else
 intCustomerProfileTblPk_var :=-1;
end if;
return intCustomerProfileTblPk_var;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 39 (OID 256316)
-- Name: sp_change_password_user (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_change_password_user (integer, character varying, character varying) RETURNS integer
    AS '
declare
  intUserProfileTblPk alias for $1;
  txtOldPassword      alias for $2;
  txtNewPassword      alias for $3;
  txtUserPassword        varchar;
intUserProfileTblPk_var integer;
begin
intUserProfileTblPk_var := intUserProfileTblPk;
select into txtUserPassword user_password from user_profile_tbl where user_profile_tbl_pk=intUserProfileTblPk;
if found then
 if txtUserPassword = txtOldPassword then
  update user_profile_tbl set user_password=txtNewPassword where user_profile_tbl_pk=intUserProfileTblPk;
  if not found then
    intUserProfileTblPk_var:=-1;
  end if;
 else
  intUserProfileTblPk_var :=-1;
  raise exception ''Wrong Old Password'';
 end if;
else
 intUserProfileTblPk_var :=-1;
end if;
return intUserProfileTblPk_var;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 41 (OID 256317)
-- Name: sp_ins_outlet_tbl (character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_outlet_tbl (character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, timestamp without time zone) RETURNS integer
    AS '
declare
txtOutletId   alias for $1;
txtOutletName alias for $2;
txtOutletDesc alias for $3;
txtAddress1   alias for $4;
txtAddress2   alias for $5;
txtAddress3   alias for $6;
txtCity       alias for $7;
txtState      alias for $8;
txtCountry    alias for $9;
txtPincode    alias for $10;  
dtCreatedOn   alias for $11;
intOutletTblPk integer;
begin
 insert into outlet_tbl(outlet_id,outlet_name,outlet_description,address1,address2,address3,city,state,country,pincode,created_on) values(txtOutletId,txtOutletName,txtOutletDesc,txtAddress1,txtAddress2,txtAddress3,txtCity,txtState,txtCountry,txtPincode,dtCreatedOn);
 if found then
 select into intOutletTblPk currval(''outlet_tbl_seq'');
 return intOutletTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- Data for TOC entry 42 (OID 191179)
-- Name: customer_profile_tbl; Type: TABLE DATA; Schema: bulbul; Owner: bulbul
--

INSERT INTO customer_profile_tbl VALUES (4, 'zxc', 'werw', 'mrs', 'wer', 'were', 'wewer', 'ewee', 'sdf', 'sdfsd', 'sdfds', 4, 'sdf', 4, 'sddf', 'sdfds', 'sds', 'dd', '2004-11-06 00:00:00', 1);
INSERT INTO customer_profile_tbl VALUES (2, 'sdf', 'a', 'sdf', 'sdf', 'sdf', 'sd', 'sdf', 'sdf', 'sdf', 'sdf', 4, 'sdf', 4, 'sdf', 'sdf', 'sdf', 'df', '2004-11-06 00:00:00', 0);


--
-- Data for TOC entry 43 (OID 191181)
-- Name: design_tbl; Type: TABLE DATA; Schema: bulbul; Owner: bulbul
--



--
-- Data for TOC entry 44 (OID 191183)
-- Name: customer_design_tbl; Type: TABLE DATA; Schema: bulbul; Owner: bulbul
--



--
-- TOC entry 13 (OID 191185)
-- Name: customer_profile_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY customer_profile_tbl
    ADD CONSTRAINT customer_profile_tbl_pkey PRIMARY KEY (customer_profile_tbl_pk);


--
-- TOC entry 14 (OID 191187)
-- Name: customer_profile_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY customer_profile_tbl
    ADD CONSTRAINT customer_profile_tbl_ukey UNIQUE (customer_email_id);


--
-- TOC entry 15 (OID 191189)
-- Name: design_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY design_tbl
    ADD CONSTRAINT design_tbl_pkey PRIMARY KEY (design_tbl_pk);


--
-- TOC entry 16 (OID 191191)
-- Name: design_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY design_tbl
    ADD CONSTRAINT design_tbl_ukey UNIQUE (email_id, design_name);


--
-- TOC entry 18 (OID 191193)
-- Name: customer_design_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY customer_design_tbl
    ADD CONSTRAINT customer_design_tbl_pkey PRIMARY KEY (customer_design_tbl_pk);


--
-- TOC entry 17 (OID 191195)
-- Name: customer_design_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY customer_design_tbl
    ADD CONSTRAINT customer_design_tbl_ckey UNIQUE (customer_profile_tbl_pk, customer_design_name);


--
-- TOC entry 45 (OID 191197)
-- Name: customer_profile_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY customer_design_tbl
    ADD CONSTRAINT customer_profile_tbl_fkey FOREIGN KEY (customer_profile_tbl_pk) REFERENCES customer_profile_tbl(customer_profile_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 5 (OID 248172)
-- Name: customer_design_tbl_seq; Type: SEQUENCE SET; Schema: bulbul; Owner: bulbul
--

SELECT pg_catalog.setval ('customer_design_tbl_seq', 1, false);


--
-- TOC entry 7 (OID 248174)
-- Name: customer_profile_tbl_seq; Type: SEQUENCE SET; Schema: bulbul; Owner: bulbul
--

SELECT pg_catalog.setval ('customer_profile_tbl_seq', 5, true);


--
-- TOC entry 9 (OID 248176)
-- Name: design_tbl_seq; Type: SEQUENCE SET; Schema: bulbul; Owner: bulbul
--

SELECT pg_catalog.setval ('design_tbl_seq', 1, false);


