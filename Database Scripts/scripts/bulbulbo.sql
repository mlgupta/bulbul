--
-- PostgreSQL database dump
--

\connect - bulbul

--
-- TOC entry 1 (OID 0)
-- Name: bulbulbo; Type: DATABASE; Schema: -; Owner: bulbul
--

CREATE DATABASE bulbulbo WITH TEMPLATE = template0 ENCODING = 5;


\connect bulbulbo bulbul

--
-- TOC entry 2 (OID 207329)
-- Name: bulbul; Type: SCHEMA; Schema: -; Owner: bulbul
--

CREATE SCHEMA bulbul;


\connect - postgres

SET search_path = public, pg_catalog;

--
-- TOC entry 89 (OID 207330)
-- Name: plpgsql_call_handler (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler () RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


--
-- TOC entry 88 (OID 207331)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler;


--
-- TOC entry 90 (OID 207332)
-- Name: dblink_connect (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_connect (text) RETURNS text
    AS '$libdir/dblink', 'dblink_connect'
    LANGUAGE c STRICT;


--
-- TOC entry 91 (OID 207333)
-- Name: dblink_disconnect (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_disconnect () RETURNS text
    AS '$libdir/dblink', 'dblink_disconnect'
    LANGUAGE c STRICT;


--
-- TOC entry 92 (OID 207334)
-- Name: dblink_open (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_open (text, text) RETURNS text
    AS '$libdir/dblink', 'dblink_open'
    LANGUAGE c STRICT;


--
-- TOC entry 93 (OID 207335)
-- Name: dblink_fetch (text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_fetch (text, integer) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_fetch'
    LANGUAGE c STRICT;


--
-- TOC entry 94 (OID 207336)
-- Name: dblink_close (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_close (text) RETURNS text
    AS '$libdir/dblink', 'dblink_close'
    LANGUAGE c STRICT;


--
-- TOC entry 95 (OID 207337)
-- Name: dblink (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink (text, text) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_record'
    LANGUAGE c STRICT;


--
-- TOC entry 96 (OID 207338)
-- Name: dblink (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink (text) RETURNS SETOF record
    AS '$libdir/dblink', 'dblink_record'
    LANGUAGE c STRICT;


--
-- TOC entry 97 (OID 207339)
-- Name: dblink_exec (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec (text, text) RETURNS text
    AS '$libdir/dblink', 'dblink_exec'
    LANGUAGE c STRICT;


--
-- TOC entry 98 (OID 207340)
-- Name: dblink_exec (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_exec (text) RETURNS text
    AS '$libdir/dblink', 'dblink_exec'
    LANGUAGE c STRICT;


--
-- TOC entry 3 (OID 207342)
-- Name: dblink_pkey_results; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE dblink_pkey_results AS ("position" integer,
	colname text);


--
-- TOC entry 99 (OID 207343)
-- Name: dblink_get_pkey (text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_get_pkey (text) RETURNS SETOF dblink_pkey_results
    AS '$libdir/dblink', 'dblink_get_pkey'
    LANGUAGE c STRICT;


--
-- TOC entry 100 (OID 207344)
-- Name: dblink_build_sql_insert (text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_insert (text, int2vector, integer, text[], text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_insert'
    LANGUAGE c STRICT;


--
-- TOC entry 101 (OID 207345)
-- Name: dblink_build_sql_delete (text, int2vector, integer, text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_delete (text, int2vector, integer, text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_delete'
    LANGUAGE c STRICT;


--
-- TOC entry 102 (OID 207346)
-- Name: dblink_build_sql_update (text, int2vector, integer, text[], text[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_build_sql_update (text, int2vector, integer, text[], text[]) RETURNS text
    AS '$libdir/dblink', 'dblink_build_sql_update'
    LANGUAGE c STRICT;


--
-- TOC entry 103 (OID 207347)
-- Name: dblink_current_query (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dblink_current_query () RETURNS text
    AS '$libdir/dblink', 'dblink_current_query'
    LANGUAGE c;


\connect - bulbul

SET search_path = public, pg_catalog;

--
-- TOC entry 24 (OID 207348)
-- Name: pga_queries; Type: TABLE; Schema: public; Owner: bulbul
--

CREATE TABLE pga_queries (
    queryname character varying(64),
    querytype character(1),
    querycommand text,
    querytables text,
    querylinks text,
    queryresults text,
    querycomments text
);


--
-- TOC entry 25 (OID 207348)
-- Name: pga_queries; Type: ACL; Schema: public; Owner: bulbul
--

REVOKE ALL ON TABLE pga_queries FROM PUBLIC;
GRANT ALL ON TABLE pga_queries TO PUBLIC;


--
-- TOC entry 26 (OID 207353)
-- Name: pga_forms; Type: TABLE; Schema: public; Owner: bulbul
--

CREATE TABLE pga_forms (
    formname character varying(64),
    formsource text
);


--
-- TOC entry 27 (OID 207353)
-- Name: pga_forms; Type: ACL; Schema: public; Owner: bulbul
--

REVOKE ALL ON TABLE pga_forms FROM PUBLIC;
GRANT ALL ON TABLE pga_forms TO PUBLIC;


--
-- TOC entry 28 (OID 207358)
-- Name: pga_scripts; Type: TABLE; Schema: public; Owner: bulbul
--

CREATE TABLE pga_scripts (
    scriptname character varying(64),
    scriptsource text
);


--
-- TOC entry 29 (OID 207358)
-- Name: pga_scripts; Type: ACL; Schema: public; Owner: bulbul
--

REVOKE ALL ON TABLE pga_scripts FROM PUBLIC;
GRANT ALL ON TABLE pga_scripts TO PUBLIC;


--
-- TOC entry 30 (OID 207363)
-- Name: pga_reports; Type: TABLE; Schema: public; Owner: bulbul
--

CREATE TABLE pga_reports (
    reportname character varying(64),
    reportsource text,
    reportbody text,
    reportprocs text,
    reportoptions text
);


--
-- TOC entry 31 (OID 207363)
-- Name: pga_reports; Type: ACL; Schema: public; Owner: bulbul
--

REVOKE ALL ON TABLE pga_reports FROM PUBLIC;
GRANT ALL ON TABLE pga_reports TO PUBLIC;


--
-- TOC entry 32 (OID 207368)
-- Name: pga_schema; Type: TABLE; Schema: public; Owner: bulbul
--

CREATE TABLE pga_schema (
    schemaname character varying(64),
    schematables text,
    schemalinks text
);


--
-- TOC entry 33 (OID 207368)
-- Name: pga_schema; Type: ACL; Schema: public; Owner: bulbul
--

REVOKE ALL ON TABLE pga_schema FROM PUBLIC;
GRANT ALL ON TABLE pga_schema TO PUBLIC;


SET search_path = bulbul, pg_catalog;

--
-- TOC entry 34 (OID 207373)
-- Name: inet_banking_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE inet_banking_tbl (
    inet_banking_tbl_pk integer DEFAULT nextval('inet_banking_tbl_seq'::text) NOT NULL,
    bank_name character varying(25) NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL,
    bank_account_number character varying(20)
);


--
-- TOC entry 35 (OID 207376)
-- Name: outlet_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE outlet_tbl (
    outlet_tbl_pk integer DEFAULT nextval('outlet_tbl_seq'::text) NOT NULL,
    outlet_id character varying(10) NOT NULL,
    outlet_name character varying(25) NOT NULL,
    outlet_description character varying(50) NOT NULL,
    address1 character varying(25) NOT NULL,
    address2 character varying(25),
    address3 character varying(25),
    city character varying(25) NOT NULL,
    state character varying(25) NOT NULL,
    country character varying(25) NOT NULL,
    pincode character varying(10) NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 36 (OID 207379)
-- Name: user_profile_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE user_profile_tbl (
    user_profile_tbl_pk integer DEFAULT nextval('user_profile_tbl_seq'::text) NOT NULL,
    user_id character varying(10) NOT NULL,
    user_first_name character varying(25) NOT NULL,
    user_last_name character varying(25),
    user_password character varying(10) NOT NULL,
    user_category_id character(3) NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 37 (OID 207382)
-- Name: user_access_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE user_access_tbl (
    user_access_tbl_pk integer DEFAULT nextval('user_access_tbl_seq'::text) NOT NULL,
    user_profile_tbl_pk integer NOT NULL,
    module_id character(3) NOT NULL,
    permission_ids character varying NOT NULL,
    permission_values character varying NOT NULL
);


--
-- TOC entry 38 (OID 207388)
-- Name: clipart_category_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE clipart_category_tbl (
    clipart_category_tbl_pk integer DEFAULT nextval('clipart_category_tbl_seq'::text) NOT NULL,
    clipart_category_tbl_fk integer,
    clipart_category character varying(10) NOT NULL,
    clipart_category_description character varying(50),
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 39 (OID 207391)
-- Name: clipart_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE clipart_tbl (
    clipart_tbl_pk integer DEFAULT nextval('clipart_tbl_seq'::text) NOT NULL,
    clipart_category_tbl_pk integer NOT NULL,
    clipart_name character varying(10) NOT NULL,
    clipart_keywords character varying(50) NOT NULL,
    clipart_image oid NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL,
    content_type character varying(50) NOT NULL,
    content_size numeric NOT NULL
);


--
-- TOC entry 40 (OID 207394)
-- Name: font_category_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE font_category_tbl (
    font_category_tbl_pk integer DEFAULT nextval('font_category_tbl_seq'::text) NOT NULL,
    font_category_tbl_fk integer,
    font_category character varying(15) NOT NULL,
    font_category_description character varying(50),
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 41 (OID 207397)
-- Name: font_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE font_tbl (
    font_tbl_pk integer DEFAULT nextval('font_tbl_seq'::text) NOT NULL,
    font_category_tbl_pk integer NOT NULL,
    font_name character varying(15) NOT NULL,
    font_description character varying(50),
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 42 (OID 207400)
-- Name: merchandise_category_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE merchandise_category_tbl (
    merchandise_category_tbl_pk integer DEFAULT nextval('merchandise_category_tbl_seq'::text) NOT NULL,
    merchandise_category_tbl_fk integer,
    merchandise_category character varying(15) NOT NULL,
    merchandise_category_description character varying(25),
    merchandise_category_image oid NOT NULL,
    user_profile_tbl_pk integer NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 43 (OID 207403)
-- Name: merchandise_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE merchandise_tbl (
    merchandise_tbl_pk integer DEFAULT nextval('merchandise_tbl_seq'::text) NOT NULL,
    merchandise_name character varying(15) NOT NULL,
    merchandise_description character varying(25),
    merchandise_comment character varying(50),
    material_description character varying(50),
    threshold_quantity integer NOT NULL,
    merchandise_display_image oid NOT NULL,
    merchandise_design_image oid NOT NULL,
    delivery_note character varying(50),
    user_profile_tbl_pk integer NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 44 (OID 207406)
-- Name: merchandise_parents_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE merchandise_parents_tbl (
    merchandise_parents_tbl_pk integer DEFAULT nextval('merchandise_parents_tbl_seq'::text) NOT NULL,
    merchandise_tbl_pk integer NOT NULL,
    merchandise_category_tbl_pk integer NOT NULL,
    is_active integer DEFAULT 1 NOT NULL
);


--
-- TOC entry 45 (OID 207409)
-- Name: merchandise_color_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE merchandise_color_tbl (
    merchandise_color_tbl_pk integer DEFAULT nextval('merchandise_color_tbl_seq'::text) NOT NULL,
    merchandise_tbl_pk integer NOT NULL,
    color_one_value character(7),
    color_two_value character(7),
    is_active integer DEFAULT 1,
    created_on timestamp without time zone,
    merchandise_category_tbl_pk integer NOT NULL
);


--
-- TOC entry 46 (OID 207412)
-- Name: size_type_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE size_type_tbl (
    size_type_tbl_pk integer DEFAULT nextval('size_type_tbl_seq'::text) NOT NULL,
    merchandise_category_tbl_pk integer NOT NULL,
    size_type_id character varying(10) NOT NULL,
    size_type_description character varying(25),
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 47 (OID 207415)
-- Name: size_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE size_tbl (
    size_tbl_pk integer DEFAULT nextval('size_tbl_seq'::text) NOT NULL,
    size_type_tbl_pk integer NOT NULL,
    size_id character(3) NOT NULL,
    size_description character varying(25),
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 48 (OID 207418)
-- Name: merchandise_size_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE merchandise_size_tbl (
    merchandise_size_tbl_pk integer DEFAULT nextval('merchandise_size_tbl_seq'::text) NOT NULL,
    merchandise_color_tbl_pk integer NOT NULL,
    size_tbl_pk integer NOT NULL,
    merchandise_price_per_qty numeric(10,2) NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 49 (OID 207421)
-- Name: price_promotion_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE price_promotion_tbl (
    price_promotion_tbl_pk integer DEFAULT nextval('price_promotion_tbl_seq'::text) NOT NULL,
    merchandise_size_tbl_pk integer NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    threshold_quantity integer NOT NULL,
    discount numeric(4,2) NOT NULL,
    exclusive_flag integer NOT NULL,
    bypass_flag integer NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 50 (OID 207424)
-- Name: order_header_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE order_header_tbl (
    order_header_tbl_pk integer DEFAULT nextval('order_header_tbl_seq'::text) NOT NULL,
    payment_mode integer NOT NULL,
    order_code character(6) DEFAULT 'change' NOT NULL,
    order_date timestamp without time zone NOT NULL,
    order_amount numeric(10,2),
    order_status integer DEFAULT 1 NOT NULL,
    reciever_first_name character varying(25) NOT NULL,
    reciever_last_name character varying(25),
    shipping_address1 character varying(25) NOT NULL,
    shipping_address2 character varying(25),
    shipping_address3 character varying(25),
    shipping_city character varying(25) NOT NULL,
    shipping_state character varying(25) NOT NULL,
    is_shipping_state_listed integer NOT NULL,
    shipping_country character varying(25) NOT NULL,
    is_shipping_country_listed integer NOT NULL,
    shipping_pincode character varying(10) NOT NULL,
    creditcard_no character varying(20),
    creditcard_validity timestamp without time zone,
    creditcard_cvv_no character varying(20),
    name_on_credit_card character varying(25),
    cc_address_1 character varying(25),
    cc_address_2 character varying(25),
    cc_address_3 character varying(25),
    cc_city character varying(25),
    cc_state character varying(25),
    is_cc_state_listed integer,
    cc_country character varying(25),
    is_cc_country_listed integer,
    cc_pincode character varying(10),
    cheque_no character varying(25),
    cheque_date timestamp without time zone,
    account_no character varying(25),
    bank_routing_no character varying(25),
    bank_name character varying(25),
    bank_branch character varying(25),
    outlet_tbl_pk integer,
    inet_banking_tbl_pk integer,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 51 (OID 207427)
-- Name: order_detail_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE order_detail_tbl (
    order_detail_tbl_pk integer DEFAULT nextval('order_detail_tbl_seq'::text) NOT NULL,
    order_header_tbl_pk integer NOT NULL,
    customer_design_tbl_pk integer,
    design_tbl_pk integer,
    merchandise_color_tbl_pk integer NOT NULL,
    order_detail_no integer DEFAULT 1 NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 52 (OID 207430)
-- Name: order_item_detail_tbl; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE order_item_detail_tbl (
    order_item_detail_tbl_pk integer DEFAULT nextval('order_item_detail_tbl_seq'::text) NOT NULL,
    order_detail_tbl_pk integer NOT NULL,
    merchandise_size_tbl_pk integer NOT NULL,
    order_item_no integer DEFAULT 1 NOT NULL,
    quantity integer NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 4 (OID 207435)
-- Name: inet_banking_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE inet_banking_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 5 (OID 207437)
-- Name: user_profile_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE user_profile_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 6 (OID 207439)
-- Name: user_access_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE user_access_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 7 (OID 207441)
-- Name: user_permission_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE user_permission_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 8 (OID 207443)
-- Name: clipart_category_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE clipart_category_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 9 (OID 207445)
-- Name: clipart_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE clipart_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 10 (OID 207447)
-- Name: font_category_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE font_category_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 11 (OID 207449)
-- Name: font_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE font_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 12 (OID 207451)
-- Name: merchandise_category_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE merchandise_category_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 13 (OID 207453)
-- Name: merchandise_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE merchandise_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 14 (OID 207455)
-- Name: merchandise_parents_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE merchandise_parents_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 15 (OID 207463)
-- Name: merchandise_size_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE merchandise_size_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 16 (OID 207465)
-- Name: price_promotion_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE price_promotion_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 17 (OID 207467)
-- Name: order_header_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE order_header_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 18 (OID 207469)
-- Name: order_detail_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE order_detail_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 19 (OID 207471)
-- Name: order_item_detail_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE order_item_detail_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 105 (OID 207474)
-- Name: sp_upd_outlet_tbl (integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_outlet_tbl (integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
    AS 'declare
intOutletTblPk  alias for $1;
txtOutletId     alias for $2;
txtOutletName   alias for $3; 
txtOutletDesc   alias for $4;
txtAddress1     alias for $5;
txtAddress2     alias for $6;
txtAddress3     alias for $7;
txtCity         alias for $8;
txtState        alias for $9; 
txtCountry      alias for $10;
txtPincode      alias for $11;

begin
update outlet_tbl set outlet_id=txtOutletId,outlet_name=txtOutletName,outlet_description=txtOutletDesc,address1=txtAddress1,address2=txtAddress2,address3=txtAddress3,city=txtCity,state=txtState,country=txtCountry,pincode=txtPincode where outlet_tbl_pk=intOutletTblPk;
if found then 
return intOutletTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 104 (OID 207475)
-- Name: sp_del_outlet_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_outlet_tbl (integer) RETURNS integer
    AS '
declare
intOutletTblPk alias for $1;
begin
delete from outlet_tbl where outlet_tbl_pk=intOutletTblPk;        
if found then 
return intOutletTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 106 (OID 207476)
-- Name: sp_act_deact_outlet_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_outlet_tbl (integer, integer) RETURNS integer
    AS 'declare
 intOutletTblPk alias for $1;
 intIsActive    alias for $2;
begin
update outlet_tbl set is_active=intIsActive where outlet_tbl_pk=intOutletTblPk;
if found then 
return intOutletTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 107 (OID 207651)
-- Name: sp_upd_inet_bank_tbl (integer, character varying, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_inet_bank_tbl (integer, character varying, integer) RETURNS integer
    AS 'declare
intInetBankTblPk  alias for $1;
txtBankName       alias for $2;
intBankAcNo       alias for $3; 
begin
UPDATE inet_banking_tbl SET bank_name=txtBankName, bank_account_number=intBankAcNo where inet_banking_tbl_pk=intInetBankTblPk;
if found then 
return intInetBankTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 108 (OID 207652)
-- Name: sp_del_inet_bank_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_inet_bank_tbl (integer) RETURNS integer
    AS '
declare
intInetBankTblPk alias for $1;
begin
DELETE FROM inet_banking_tbl where inet_banking_tbl_pk=intInetBankTblPk;        
if found then 
return intInetBankTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 109 (OID 207653)
-- Name: sp_act_deact_inet_bank_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_inet_bank_tbl (integer, integer) RETURNS integer
    AS 'declare
 intInetBankTblPk alias for $1;
 intIsActive      alias for $2;
begin
UPDATE inet_banking_tbl SET is_active=intIsActive where inet_banking_tbl_pk=intInetBankTblPk;
if found then 
return intInetBankTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 181 (OID 215193)
-- Name: sp_del_user_profile_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_user_profile_tbl (integer) RETURNS integer
    AS '
declare
  intUserProfileTblPk         alias for $1;
intMerchandiseTblPk         varchar;
intMerchandiseCategoryTblPk varchar;
varUserAccessTblPk          record;

begin
  --select into intMerchandiseTblPk merchandise_tbl_pk from merchandise_tbl where user_profile_tbl_pk=intUserProfileTblPk;
--if found then
 --raise notice ''Deletion Failed: intUserProfileTblPk % is refered in merchandise_tbl'',intUserProfileTblPk;
 --return -1;
--end if;
--select into intMerchandiseCategoryTblPk merchandise_category_tbl_pk from merchandise_category_tbl where user_profile_tbl_pk=intUserProfileTblPk;
--if found then
 --raise notice ''Deletion Failed: intUserProfileTblPk % is refered in merchandise_category_tbl'',intUserProfileTblPk;
 --return -1;
--end if;
for varUserAccessTblPk in select user_access_tbl_pk from user_access_tbl where user_profile_tbl_pk=intUserProfileTblPk loop
 perform sp_del_user_access_tbl(varUserAccessTblPk.user_access_tbl_pk);
end loop;
  DELETE FROM user_profile_tbl where user_profile_tbl_pk=intUserProfileTblPk;        
  if found then 
  return intUserProfileTblPk;
  else
  return -1;
  end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 110 (OID 215194)
-- Name: sp_act_deact_user_profile_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_user_profile_tbl (integer, integer) RETURNS integer
    AS '
declare
 intUserProfileTblPk alias for $1;
 intIsActive      alias for $2;
begin
UPDATE user_profile_tbl SET is_active=intIsActive where user_profile_tbl_pk=intUserProfileTblPk;
if found then 
  return intUserProfileTblPk;
  else
  return -1;
  end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 111 (OID 215207)
-- Name: sp_del_user_access_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_user_access_tbl (integer) RETURNS integer
    AS '
declare
intUserAccessTblPk alias for $1;
begin
DELETE FROM user_access_tbl where user_access_tbl_pk=intUserAccessTblPk;        
if found then 
  return intUserAccessTblPk;
  else
  return -1;
  end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 182 (OID 215217)
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
  UPDATE user_profile_tbl set user_password=txtNewPassword where user_profile_tbl_pk=intUserProfileTblPk;
  if not found then
    intUserProfileTblPk_var:=-1;
  end if;
 else
  intUserProfileTblPk_var :=-1;
  return intUserProfileTblPk_var; 
  raise notice ''Wrong Old Password'';
 end if;
else
 intUserProfileTblPk_var :=-1;
end if;
return intUserProfileTblPk_var;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 186 (OID 223395)
-- Name: sp_upd_clipart_category_tbl (integer, integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_clipart_category_tbl (integer, integer, character varying, character varying) RETURNS integer
    AS '
declare
intCategoryTblPk alias for $1;
intCategoryTblFk alias for $2;
txtCategory      alias for $3;
txtCategoryDesc  alias for $4;
varCategoryTblPk integer;
begin
if(intCategoryTblFk=-1) then
  select into varCategoryTblPk clipart_category_tbl_pk from clipart_category_tbl where clipart_category_tbl_fk is null and clipart_category=txtCategory and clipart_category_tbl_pk!=intCategoryTblPk;
if not found then
    UPDATE clipart_category_tbl set clipart_category=txtCategory,clipart_category_description=txtCategoryDesc where clipart_category_tbl_pk=intCategoryTblPk;
else
  raise exception ''User Exception: Can not add duplicate unique key.'';
  return -1;
end if;
else
  UPDATE clipart_category_tbl set clipart_category_tbl_fk=intCategoryTblFk,clipart_category=txtCategory,clipart_category_description=txtCategoryDesc where clipart_category_tbl_pk=intCategoryTblPk;
end if;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 112 (OID 223396)
-- Name: sp_del_clipart_category_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_clipart_category_tbl (integer) RETURNS integer
    AS '
declare
intCategoryTblPk alias for $1;
begin
delete from clipart_category_tbl where clipart_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 113 (OID 223397)
-- Name: sp_act_deact_clipart_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_clipart_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk alias for $1;
 intIsActive      alias for $2;
begin
UPDATE clipart_category_tbl SET is_active=intIsActive where clipart_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 114 (OID 231577)
-- Name: sp_mov_clipart_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_clipart_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk    alias for $1;
 intCategoryTblFk    alias for $2;
 intg integer;
begin
update clipart_category_tbl set clipart_category_tbl_Fk=intCategoryTblFk where clipart_category_tbl_Pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 115 (OID 231629)
-- Name: sp_del_clipart_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_clipart_tbl (integer) RETURNS integer
    AS '
declare
intClipartTblPk alias for $1;
begin
delete from clipart_tbl where clipart_tbl_pk=intClipartTblPk;
if found then
return intClipartTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 116 (OID 231630)
-- Name: sp_act_deact_clipart_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_clipart_tbl (integer, integer) RETURNS integer
    AS '
declare
 intClipartTblPk alias for $1;
 intIsActive     alias for $2;

begin
UPDATE clipart_tbl SET is_active=intIsActive where clipart_tbl_pk=intClipartTblPk;
if found then
return intClipartTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 117 (OID 231631)
-- Name: sp_mov_clipart_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_clipart_tbl (integer, integer) RETURNS integer
    AS '
declare
 intClipartTblPk  alias for $1;
 intCategoryTblPk alias for $2;
begin
update clipart_tbl set clipart_category_tbl_Pk=intCategoryTblPk where clipart_tbl_Pk=intClipartTblPk;
if found then
return intClipartTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 185 (OID 231638)
-- Name: sp_upd_font_category_tbl (integer, integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_font_category_tbl (integer, integer, character varying, character varying) RETURNS integer
    AS '
declare
intCategoryTblPk alias for $1;
intCategoryTblFk alias for $2;
txtCategory      alias for $3;
txtCategoryDesc  alias for $4;
varCategoryTblPk integer;
begin
if(intCategoryTblFk=-1) then
  select into varCategoryTblPk font_category_tbl_pk from font_category_tbl where font_category_tbl_fk is null and font_category=txtCategory and font_category_tbl_pk!=intCategoryTblPk;
if not found then
    UPDATE font_category_tbl set font_category=txtCategory,font_category_description=txtCategoryDesc where font_category_tbl_pk=intCategoryTblPk;
else
  raise exception ''User Exception: Can not add duplicate unique key.'';
  return -1;
end if;
else
 UPDATE font_category_tbl set font_category_tbl_fk=intCategoryTblFk,font_category=txtCategory,font_category_description=txtCategoryDesc where font_category_tbl_pk=intCategoryTblPk;
end if;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 118 (OID 231640)
-- Name: sp_del_font_category_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_font_category_tbl (integer) RETURNS integer
    AS '
declare
intCategoryTblPk alias for $1;
begin
delete from font_category_tbl where font_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 119 (OID 231641)
-- Name: sp_act_deact_font_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_font_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk alias for $1;
 intIsActive      alias for $2;
begin
UPDATE font_category_tbl SET is_active=intIsActive where font_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 120 (OID 231642)
-- Name: sp_mov_font_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_font_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk    alias for $1;
 intCategoryTblFk    alias for $2;
 begin
update font_category_tbl set font_category_tbl_Fk=intCategoryTblFk where font_category_tbl_Pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 121 (OID 231650)
-- Name: sp_upd_font_tbl (integer, integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_font_tbl (integer, integer, character varying, character varying) RETURNS integer
    AS '
declare
intFontTblPk        alias for $1;
intCategoryTblPk    alias for $2;
txtFontName         alias for $3;
txtFontDesc         alias for $4;
begin
update font_tbl set font_category_tbl_pk=intCategoryTblPk,font_name=txtFontName,font_description=txtFontDesc where font_tbl_pk=intFontTblPk;
if found then
return intFontTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 122 (OID 231652)
-- Name: sp_del_font_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_font_tbl (integer) RETURNS integer
    AS '
declare
intFontTblPk alias for $1;
begin
delete from font_tbl where font_tbl_pk=intFontTblPk;
if found then
return intFontTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 123 (OID 231653)
-- Name: sp_act_deact_font_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_font_tbl (integer, integer) RETURNS integer
    AS '
declare
 intFontTblPk alias for $1;
 intIsActive     alias for $2;

begin
UPDATE font_tbl SET is_active=intIsActive where font_tbl_pk=intFontTblPk;
if found then
return intFontTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 124 (OID 231654)
-- Name: sp_mov_font_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_font_tbl (integer, integer) RETURNS integer
    AS '
declare
 intFontTblPk      alias for $1;
 intCategoryTblPk  alias for $2;
begin
update font_tbl set font_category_tbl_Pk=intCategoryTblPk where font_tbl_Pk=intFontTblPk;
if found then
return intFontTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 125 (OID 231676)
-- Name: sp_upd_merchandise_category_tbl (integer, integer, character varying, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_category_tbl (integer, integer, character varying, character varying, character varying) RETURNS integer
    AS '
declare
intCategoryTblPk   alias for $1;
intCategoryTblFk   alias for $2;
txtCategory        alias for $3;
txtCategoryDesc    alias for $4;
txtCategoryImgPath alias for $5;
begin
update merchandise_category_tbl set merchandise_category_tbl_fk=intCategoryTblFk,merchandise_category=txtCategory,merchandise_category_description=txtCategoryDesc,merchandise_category_image=lo_import(txtCategoryImgPath) where merchandise_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 126 (OID 231679)
-- Name: sp_del_merchandise_category_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_category_tbl (integer) RETURNS integer
    AS '
declare
intCategoryTblPk alias for $1;
begin
delete from merchandise_category_tbl where merchandise_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 127 (OID 231680)
-- Name: sp_act_deact_merchandise_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_merchandise_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk alias for $1;
 intIsActive      alias for $2;
begin
UPDATE merchandise_category_tbl SET is_active=intIsActive where merchandise_category_tbl_pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 128 (OID 231681)
-- Name: sp_mov_merchandise_category_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_merchandise_category_tbl (integer, integer) RETURNS integer
    AS '
declare
 intCategoryTblPk    alias for $1;
 intCategoryTblFk    alias for $2;
 begin
update merchandise_category_tbl set merchandise_category_tbl_Fk=intCategoryTblFk where merchandise_category_tbl_Pk=intCategoryTblPk;
if found then
return intCategoryTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 129 (OID 231704)
-- Name: sp_ins_merchandise_parents_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_parents_tbl (integer, integer) RETURNS integer
    AS '
declare          
intMerchandiseTblPk alias for $1;
intCategoryTblPk    alias for $2;
intParentTblPk      integer;
begin
 INSERT INTO merchandise_parents_tbl (merchandise_tbl_pk,merchandise_category_tbl_pk) VALUES (intMerchandiseTblPk,intCategoryTblPk);
 if found then
 select into intParentTblPk currval(''merchandise_parents_tbl_seq'') ;   
 return intParentTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 169 (OID 231709)
-- Name: sp_del_merchandise_parents_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_parents_tbl (integer) RETURNS integer
    AS '
declare
intMerchandiseParentsTblPk alias for $1;
begin
delete from merchandise_parents_tbl where merchandise_parents_tbl_pk=intMerchandiseParentsTblPk;
if found then
return intMerchandiseParentsTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 53 (OID 231711)
-- Name: pga_layout; Type: TABLE; Schema: bulbul; Owner: bulbul
--

CREATE TABLE pga_layout (
    tablename character varying(64),
    nrcols smallint,
    colnames text,
    colwidth text
);


--
-- TOC entry 54 (OID 231711)
-- Name: pga_layout; Type: ACL; Schema: bulbul; Owner: bulbul
--

REVOKE ALL ON TABLE pga_layout FROM PUBLIC;
GRANT ALL ON TABLE pga_layout TO PUBLIC;


--
-- TOC entry 130 (OID 231741)
-- Name: sp_cop_merchandise (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_cop_merchandise (integer, integer) RETURNS integer
    AS '
declare
intMerchandiseTblPk alias for $1;
intCategoryTblPk    alias for $2;
intParentTblPk      integer;
begin
perform sp_ins_merchandise_parents_tbl(intMerchandiseTblPk,intCategoryTblPk);
if found then
select into intParentTblPk currval(''merchandise_parents_tbl_seq'');
return intParentTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 20 (OID 239840)
-- Name: size_type_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE size_type_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 21 (OID 239842)
-- Name: size_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE size_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 131 (OID 239852)
-- Name: sp_upd_size_type_tbl (integer, integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_size_type_tbl (integer, integer, character varying, character varying) RETURNS integer
    AS '
declare
intSizeTypeTblPk            alias for $1;
intMerchandiseCategoryTblPk alias for $2;
txtSizeTypeId               alias for $3;
txtSizeTypeDesc             alias for $4;
begin
update size_type_tbl set merchandise_category_tbl_pk=intMerchandiseCategoryTblPk,size_type_id=txtSizeTypeId,size_type_description=txtSizeTypeDesc  where size_type_tbl_pk=intSizeTypeTblPk ;
if found then
return intSizeTypeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 132 (OID 239853)
-- Name: sp_del_size_type_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_size_type_tbl (integer) RETURNS integer
    AS '
declare
intSizeTypeTblPk alias for $1;
begin
delete from size_type_tbl where size_type_tbl_pk=intSizeTypeTblPk;
if found then
return intSizeTypeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 133 (OID 239860)
-- Name: sp_upd_size_tbl (integer, integer, character, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_size_tbl (integer, integer, character, character varying) RETURNS integer
    AS '
declare
intSizeTblPk        alias for $1;
intSizeTypeTblPK    alias for $2;
txtSizeId           alias for $3;
txtSizeDesc         alias for $4;
begin
update size_tbl set size_type_tbl_pk=intSizeTypeTblPK,size_id=txtSizeId,size_description=txtSizeDesc  where size_tbl_pk=intSizeTblPk ;
if found then
return intSizeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 134 (OID 239861)
-- Name: sp_del_size_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_size_tbl (integer) RETURNS integer
    AS '
declare
intSizeTblPk alias for $1;
begin
delete from size_tbl where size_tbl_pk=intSizeTblPk;
if found then
return intSizeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 136 (OID 239862)
-- Name: sp_act_deact_size_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_size_tbl (integer, integer) RETURNS integer
    AS 'declare
 intSizeTblPk alias for $1;
 intIsActive              alias for $2;
begin
UPDATE size_tbl SET is_active=intIsActive where size_tbl_pk=intSizeTblPk;
if found then 
return intSizeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 135 (OID 239863)
-- Name: sp_ins_price_promotion_tbl (integer, timestamp without time zone, timestamp without time zone, integer, numeric, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_price_promotion_tbl (integer, timestamp without time zone, timestamp without time zone, integer, numeric, integer, integer, timestamp without time zone) RETURNS integer
    AS '
declare          
intmerchandisesizetblpk      alias for $1;
dtStartDate                  alias for $2;
dtEndDate                    alias for $3;
intThresholdQty              alias for $4;
fltdiscount                  alias for $5;
intExclusiveFlag             alias for $6;
intByPassFlag                alias for $7;
dtCreatedOn                  alias for $8;
intPricePromotionTblPk       integer; 
begin
 INSERT INTO price_promotion_tbl (merchandise_size_tbl_pk,start_date,end_date,threshold_quantity,discount,exclusive_flag,bypass_flag,created_on) VALUES (intmerchandisesizetblpk,dtStartDate,dtEndDate,intThresholdQty,fltdiscount,intExclusiveFlag,intByPassFlag,dtCreatedOn);
 if found then
 select into intPricePromotionTblPk currval(''price_promotion_tbl_seq'') ;   
 return intPricePromotionTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 137 (OID 239913)
-- Name: sp_ins_merchandise_category_tbl (integer, character varying, character varying, character varying, integer, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_category_tbl (integer, character varying, character varying, character varying, integer, timestamp without time zone) RETURNS integer
    AS '
declare          
  intCategoryTblFk         alias for $1;
txtCategory              alias for $2;
txtCategoryDesc          alias for $3;
txtCategoryImgPath       alias for $4;
intCreatedBy             alias for $5;
dtCreatedOn              alias for $6;
intCategoryTblPk integer;
begin
 INSERT INTO merchandise_category_tbl(merchandise_category_tbl_fk,merchandise_category,merchandise_category_description,merchandise_category_image,user_profile_tbl_pk,created_on) VALUES (intCategoryTblFk,txtCategory,txtCategoryDesc,lo_import(txtCategoryImgPath),intCreatedBy,dtCreatedOn);
 if found then
 select into intCategoryTblPk currval(''merchandise_category_tbl_seq'') ;   
return intCategoryTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 138 (OID 239915)
-- Name: sp_ins_size_type_tbl (integer, character varying, character varying, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_size_type_tbl (integer, character varying, character varying, timestamp without time zone) RETURNS integer
    AS '
declare          
intMerchandiseCategoryTblPk alias for $1;
txtSizeTypeId               alias for $2;
txtSizeTypeDesc             alias for $3;
dtCreatedOn                 alias for $4;
intSizeTypeTblPk            integer; 
begin
 INSERT INTO size_type_tbl (merchandise_category_tbl_pk,size_type_id,size_type_description,created_on) VALUES (intMerchandiseCategoryTblPk,txtSizeTypeId,txtSizeTypeDesc,dtCreatedOn);
 if found then
 select into intSizeTypeTblPk currval(''size_type_tbl_seq'') ;   
 return intSizeTypeTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 139 (OID 239916)
-- Name: sp_ins_size_tbl (integer, character, character varying, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_size_tbl (integer, character, character varying, timestamp without time zone) RETURNS integer
    AS '
declare          
intSizeTypeTblPK    alias for $1;
txtSizeId           alias for $2;
txtSizeDesc         alias for $3;
dtCreatedOn         alias for $4;
intSizeTblPk        integer; 
begin
 INSERT INTO size_tbl (size_type_tbl_pk,size_id,size_description,created_on) VALUES (intSizeTypeTblPK,txtSizeId,txtSizeDesc,dtCreatedOn);
 if found then
 select into intSizeTblPk currval(''size_tbl_seq'') ;   
 return intSizeTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 142 (OID 239917)
-- Name: sp_upd_price_promotion_tbl (integer, integer, timestamp without time zone, timestamp without time zone, integer, numeric, integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_price_promotion_tbl (integer, integer, timestamp without time zone, timestamp without time zone, integer, numeric, integer, integer) RETURNS integer
    AS '
declare
intPricePromotionTblPk     alias for $1;
intmerchandisesizetblpk    alias for $2;
dtStartDate                alias for $3;
dtEndDate                  alias for $4;
intThresholdQty            alias for $5;
fltdiscount                alias for $6;
intExclusiveFlag           alias for $7;
intByPassFlag              alias for $8;
begin
update price_promotion_tbl set merchandise_size_tbl_pk=intmerchandisesizetblpk,start_date=dtStartDate,end_date=dtEndDate,threshold_quantity=intThresholdQty,discount=fltdiscount,exclusive_flag=intExclusiveFlag,bypass_flag=intByPassFlag  where price_promotion_tbl_pk=intPricePromotionTblPk;
if found then
return intPricePromotionTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 140 (OID 239919)
-- Name: sp_del_price_promotion_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_price_promotion_tbl (integer) RETURNS integer
    AS '
declare
intPricePromotionTblPk alias for $1;
begin
delete from price_promotion_tbl where price_promotion_tbl_pk=intPricePromotionTblPk;
if found then
return intPricePromotionTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 141 (OID 239920)
-- Name: sp_act_deact_price_promotion_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_price_promotion_tbl (integer, integer) RETURNS integer
    AS '
declare
 intPricePromotionTblPk   alias for $1;
 intIsActive              alias for $2;
begin
UPDATE price_promotion_tbl SET is_active=intIsActive where price_promotion_tbl_pk=intPricePromotionTblPk;
if found then
return intPricePromotionTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 149 (OID 248133)
-- Name: sp_ins_order_header_tbl (character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_order_header_tbl (character varying[]) RETURNS integer
    AS '
declare          
a alias for $1;

intPaymentMode              integer;
dtOrderDate                 timestamp;
fltorderAmount              numeric;
txtRecFirstName             varchar;
txtRecLastName              varchar;
txtShipAddress1             varchar;
txtShipAddress2             varchar;
txtShipAddress3             varchar;
txtShipCity                 varchar;
txtShipState                varchar;
intIsShipStateListed        integer;
txtShipCountry              varchar;
intIsShipCountryListed      integer;
txtShipPincode              varchar;
txtCCNo                     varchar;
dtCCValidity                timestamp;
txtCCCvvNo                  varchar;
txtNameOnCC                 varchar;
txtCCAddress1               varchar;
txtCCAddress2               varchar;
txtCCAddress3               varchar;
txtCCCity                   varchar;
txtCCState                  varchar;
intIsCCStateListed          integer;
txtCCCountry                varchar;
intIsCCCountryListed        integer;
txtCCPincode                varchar;
txtChqNo                    varchar;
dtChqDate                   timestamp;
txtBankAcNo                 varchar;
txtBankRoutingNo            varchar;
txtBankName                 varchar;
txtBankBranch               varchar;
intOutletTblPk              integer;
intInetBankTblPk            integer;
dtCreatedOn                 timestamp;

intOrderHeaderTblPk       integer; 
begin
 intPaymentMode           := a[1];
 dtOrderDate              := a[2];
 fltorderAmount           := a[3];
 txtRecFirstName          := a[4];
 txtRecLastName           := a[5];
 txtShipAddress1          := a[6];
 txtShipAddress2          := a[7];
 txtShipAddress3          := a[8];
 txtShipCity              := a[9]; 
 txtShipState             := a[10];
 intIsShipStateListed     := a[11];
 txtShipCountry           := a[12];
 intIsShipCountryListed   := a[13];
 txtShipPincode           := a[14];
 txtCCNo                  := a[15];
 dtCCValidity             := a[16];
 txtCCCvvNo               := a[17];
 txtNameOnCC              := a[18];
 txtCCAddress1            := a[19];
 txtCCAddress2            := a[20];
 txtCCAddress3            := a[21];
 txtCCCity                := a[22];
 txtCCState               := a[23];
 intIsCCStateListed       := a[24];
 txtCCCountry             := a[25];
 intIsCCCountryListed     := a[26];
 txtCCPincode             := a[27];
 txtChqNo                 := a[28];
 dtChqDate                := a[29];
 txtBankAcNo              := a[30];
 txtBankRoutingNo         := a[31];
 txtBankName              := a[32];
 txtBankBranch            := a[33];
 intOutletTblPk           := a[34];
 intInetBankTblPk         := a[35];
 dtCreatedOn              := a[36];
                 --REMINDER: Default value of order_code needs to be changed;
                 --REMINDER: Default value of order_status needs to be changed;

 INSERT INTO order_header_tbl (payment_mode,order_date,order_amount,reciever_first_name,reciever_last_name,shipping_address1,shipping_address2,shipping_address3,shipping_city,shipping_state,is_shipping_state_listed,shipping_country,is_shipping_country_listed,shipping_pincode,creditcard_no,creditcard_validity,creditcard_cvv_no,name_on_credit_card,cc_address_1,cc_address_2,cc_address_3,cc_city,cc_state,is_cc_state_listed,cc_country,is_cc_country_listed,cc_pincode,cheque_no,cheque_date,account_no,bank_routing_no,bank_name,bank_branch,outlet_tbl_pk,inet_banking_tbl_pk,created_on) VALUES (intPaymentMode,dtOrderDate,fltorderAmount,txtRecFirstName,txtRecLastName,txtShipAddress1,txtShipAddress2,txtShipAddress3,txtShipCity,txtShipState,intIsShipStateListed,txtShipCountry,intIsShipCountryListed,txtShipPincode,txtCCNo,dtCCValidity,txtCCCvvNo,txtNameOnCC,txtCCAddress1,txtCCAddress2,txtCCAddress3,txtCCCity,txtCCState,intIsCCStateListed,txtCCCountry,intIsCCCountryListed,txtCCPincode,txtChqNo,dtChqDate,txtBankAcNo,txtBankRoutingNo,txtBankName,txtBankBranch,intOutletTblPk,intInetBankTblPk,dtCreatedOn);
 if found then
 select into intOrderHeaderTblPk currval(''order_header_tbl_seq'') ;   
 return intOrderHeaderTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 143 (OID 248144)
-- Name: sp_upd_order_header_tbl (character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_order_header_tbl (character varying[]) RETURNS integer
    AS '
declare
a alias for $1;

intOrderHeaderTblPk         integer;
intPaymentMode              integer;
dtOrderDate                 timestamp;
fltorderAmount              numeric;
txtRecFirstName             varchar;
txtRecLastName              varchar;
txtShipAddress1             varchar;
txtShipAddress2             varchar;
txtShipAddress3             varchar;
txtShipCity                 varchar;
txtShipState                varchar;
intIsShipStateListed        integer;
txtShipCountry              varchar;
intIsShipCountryListed      integer;
txtShipPincode              varchar;
txtCCNo                     varchar;
dtCCValidity                timestamp;
txtCCCvvNo                  varchar;
txtNameOnCC                 varchar;
txtCCAddress1               varchar;
txtCCAddress2               varchar;
txtCCAddress3               varchar;
txtCCCity                   varchar;
txtCCState                  varchar;
intIsCCStateListed          integer;
txtCCCountry                varchar;
intIsCCCountryListed        integer;
txtCCPincode                varchar;
txtChqNo                    varchar;
dtChqDate                   timestamp;
txtBankAcNo                 varchar;
txtBankRoutingNo            varchar;
txtBankName                 varchar;
txtBankBranch               varchar;
intOutletTblPk              integer;
intInetBankTblPk            integer;

begin
 intOrderHeaderTblPk      := a[1];
 intPaymentMode           := a[2];
 dtOrderDate              := a[3];
 fltorderAmount           := a[4];
 txtRecFirstName          := a[5];
 txtRecLastName           := a[6];
 txtShipAddress1          := a[7];
 txtShipAddress2          := a[8];
 txtShipAddress3          := a[9];
 txtShipCity              := a[10];
 txtShipState             := a[11];
 intIsShipStateListed     := a[12];
 txtShipCountry           := a[13];
 intIsShipCountryListed   := a[14];
 txtShipPincode           := a[15];
 txtCCNo                  := a[16];
 dtCCValidity             := a[17];
 txtCCCvvNo               := a[18];
 txtNameOnCC              := a[19];
 txtCCAddress1            := a[20];
 txtCCAddress2            := a[21];
 txtCCAddress3            := a[22];
 txtCCCity                := a[23];
 txtCCState               := a[24];
 intIsCCStateListed       := a[25];
 txtCCCountry             := a[26];
 intIsCCCountryListed     := a[27];
 txtCCPincode             := a[28];
 txtChqNo                 := a[29];
 dtChqDate                := a[30];
 txtBankAcNo              := a[31];
 txtBankRoutingNo         := a[32];
 txtBankName              := a[33];
 txtBankBranch            := a[34];
 intOutletTblPk           := a[35];
 intInetBankTblPk         := a[36];
                                      
update order_header_tbl set payment_mode=intPaymentMode,order_date=dtOrderDate,order_amount=fltorderAmount,reciever_first_name=txtRecFirstName,reciever_last_name=txtRecLastName,shipping_address1=txtShipAddress1,shipping_address2=txtShipAddress2,shipping_address3=txtShipAddress3,shipping_city=txtShipCity,shipping_state=txtShipState,is_shipping_state_listed=intIsShipStateListed,shipping_country=txtShipCountry,is_shipping_country_listed=intIsShipCountryListed,shipping_pincode=txtShipPincode,creditcard_no=txtCCNo,creditcard_validity=dtCCValidity,creditcard_cvv_no=txtCCCvvNo,name_on_credit_card=txtNameOnCC,cc_address_1=txtCCAddress1,cc_address_2=txtCCAddress2,cc_address_3=txtCCAddress3,cc_city=txtCCState,cc_state=txtCCState,is_cc_state_listed=intIsCCStateListed,cc_country=txtCCCountry,is_cc_country_listed=intIsCCCountryListed,cc_pincode=txtCCPincode,cheque_no=txtChqNo,cheque_date=dtChqDate,account_no=txtBankAcNo,bank_routing_no=txtBankRoutingNo,bank_name=txtBankName,bank_branch=txtBankBranch,outlet_tbl_pk=intOutletTblPk,inet_banking_tbl_pk=intInetBankTblPk  where  order_header_tbl_pk=intOrderHeaderTblPk;
if found then
return intOrderHeaderTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 144 (OID 248145)
-- Name: sp_del_order_header_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_order_header_tbl (integer) RETURNS integer
    AS '
declare
intOrderHeaderTblPk alias for $1;
begin
delete from order_header_tbl where order_header_tbl_pk=intOrderHeaderTblPk;
if found then
return intOrderHeaderTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 150 (OID 248146)
-- Name: sp_ins_order_detail_tbl (integer, integer, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_order_detail_tbl (integer, integer, integer, integer, timestamp without time zone) RETURNS integer
    AS '
declare
intOrderHeaderTblPk      alias for $1; 
intCustomerDesignTblPk   alias for $2;
intDesignTblPk           alias for $3;
intMechandiseColorTblPk  alias for $4;
dtCreatedOn              alias for $5;
intOrderDetailTblPk integer;
begin
                           -- REMINDER: Default value of order_detail_no needs to be changed
 INSERT INTO order_detail_tbl(order_header_tbl_pk,customer_design_tbl_pk,design_tbl_pk,merchandise_color_tbl_pk,created_on) VALUES (intOrderHeaderTblPk,intCustomerDesignTblPk,intDesignTblPk,intMechandiseColorTblPk,dtCreatedOn);
 if found then
 select into intOrderDetailTblPk currval(''order_detail_tbl_seq'');
 return intOrderDetailTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 145 (OID 248158)
-- Name: sp_upd_order_detail_tbl (integer, integer, integer, integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_order_detail_tbl (integer, integer, integer, integer, integer) RETURNS integer
    AS '
declare
intOrderDetailTblPk      alias for $1;
intOrderHeaderTblPk      alias for $2;
intCustomerDesignTblPk   alias for $3;
intDesignTblPk           alias for $4;
intMechandiseColorTblPk  alias for $5;
begin
UPDATE order_detail_tbl SET order_header_tbl_pk=intOrderHeaderTblPk,customer_design_tbl_pk=intCustomerDesignTblPk,design_tbl_pk=intDesignTblPk,merchandise_color_tbl_pk=intMechandiseColorTblPk where order_detail_tbl_pk=intOrderDetailTblPk;
if found then 
return intOrderDetailTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 146 (OID 248159)
-- Name: sp_del_order_detail_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_order_detail_tbl (integer) RETURNS integer
    AS '
declare
intOrderDetailTblPk alias for $1;
begin
DELETE FROM order_detail_tbl where order_detail_tbl_pk=intOrderDetailTblPk;        
if found then 
return intOrderDetailTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 151 (OID 248160)
-- Name: sp_ins_order_item_detail_tbl (integer, integer, integer, numeric, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_order_item_detail_tbl (integer, integer, integer, numeric, timestamp without time zone) RETURNS integer
    AS '
declare
intOrderDetailTblPk      alias for $1;
intMechandiseSizeTblPk   alias for $2;
intQuantity              alias for $3;
fltTotalAmount           alias for $4;
dtCreatedOn              alias for $5;
intOrderItemDetailTblPk  integer;
begin
                                    -- REMINDER:Default value of order_item_no needs to be changed  
 INSERT INTO order_item_detail_tbl(order_detail_tbl_pk,merchandise_size_tbl_pk,quantity,total_amount,created_on) VALUES (intOrderDetailTblPk,intMechandiseSizeTblPk,intQuantity,fltTotalAmount,dtCreatedOn);
 if found then
 select into intOrderItemDetailTblPk currval(''order_item_detail_tbl_seq'');
 return intOrderItemDetailTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 147 (OID 248169)
-- Name: sp_upd_order_item_detail_tbl (integer, integer, integer, integer, numeric); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_order_item_detail_tbl (integer, integer, integer, integer, numeric) RETURNS integer
    AS '
declare
intOrderItemDetailTblPk   alias for $1;
intOrderDetailTblPk       alias for $2;
intMechandiseSizeTblPk    alias for $3;
intQuantity               alias for $4;
fltTotalAmount            alias for $5;
begin
UPDATE order_item_detail_tbl SET order_detail_tbl_pk=intOrderDetailTblPk,merchandise_size_tbl_pk=intMechandiseSizeTblPk,quantity=intQuantity,total_amount=fltTotalAmount where order_item_detail_tbl_pk=intOrderItemDetailTblPk;
if found then 
return intOrderItemDetailTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 148 (OID 248170)
-- Name: sp_del_order_item_detail_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_order_item_detail_tbl (integer) RETURNS integer
    AS '
declare
intOrderItemDetailTblPk alias for $1;
begin
DELETE FROM order_item_detail_tbl where order_item_detail_tbl_pk=intOrderItemDetailTblPk;        
if found then 
return intOrderItemDetailTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 152 (OID 264501)
-- Name: sp_upd_merchandise_color_tbl (integer, integer, character, character); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_color_tbl (integer, integer, character, character) RETURNS integer
    AS '
declare
 intMerchandiseColorTblPk alias for $1;
 intMerchandiseTblPk      alias for $2;
 txtColorOneValue         alias for $3;
 txtColorTwoValue         alias for $4;
begin
UPDATE merchandise_color_tbl SET  merchandise_tbl_pk=intMerchandiseTblPk,color_one_value=txtColorOneValue,color_two_value=txtColorTwoValue  where merchandise_color_tbl_pk=intMerchandiseColorTblPk;
if found then 
return intMerchandiseColorTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 168 (OID 264504)
-- Name: sp_act_deact_merchandise_color_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_merchandise_color_tbl (integer, integer) RETURNS integer
    AS '
declare
 intMerchandiseColorTblPk    alias for $1;
 intIsActive                 alias for $2;
 intMerchandiseParentsTblPk  integer;
 varMerchandiseColorTblPk    record;--merchandise_color_tbl%rowtype;
 varMerchandiseSizeTblPk     merchandise_size_tbl%rowtype;
 varPricePromotionTblPk      price_promotion_tbl%rowtype;
begin
 for varMerchandiseSizeTblPk in select merchandise_size_tbl_pk from merchandise_size_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk loop
  for varPricePromotionTblPk in select price_promotion_tbl_pk from price_promotion_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk loop
 perform sp_act_deact_price_promotion_tbl(varPricePromotionTblPk.price_promotion_tbl_pk,intIsActive);
   raise notice''varPricePromotionTblPk: %    Status: %'',varPricePromotionTblPk.price_promotion_tbl_pk,intIsActive ;
end loop;
perform sp_act_deact_merchandise_size_tbl(varMerchandiseSizeTblPk.merchandise_size_tbl_pk,intIsActive);
raise notice''varMerchandiseSizeTblPk: %    Status: %'',varMerchandiseSizeTblPk.merchandise_size_tbl_pk,intIsActive;
 end loop;
 update merchandise_color_tbl set is_active=intIsActive where merchandise_color_tbl_pk=intMerchandiseColorTblPk;
 raise notice''intMerchandiseColorTblPk: %    Status: %'',intMerchandiseColorTblPk,intIsActive;
 if found then
  if intIsActive=1 then 
 select into varMerchandiseColorTblPk * from merchandise_color_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk;
  --for varMerchandiseColorTblPk in select * from  merchandise_color_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk loop 
 select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_tbl_pk=varMerchandiseColorTblPk.merchandise_tbl_pk and merchandise_category_tbl_pk=varMerchandiseColorTblPk.merchandise_category_tbl_pk;
 if not found then
  perform sp_ins_merchandise_parents_tbl(varMerchandiseColorTblPk.merchandise_tbl_pk,varMerchandiseColorTblPk.merchandise_category_tbl_pk);
 end if;
--end loop; 
end if;
return intMerchandiseColorTblPk;
 else
   return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 153 (OID 264505)
-- Name: sp_ins_merchandise_size_tbl (integer, integer, numeric, timestamp without time zone); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_size_tbl (integer, integer, numeric, timestamp without time zone) RETURNS integer
    AS '
declare
 intMerchandiseColorTblPk      alias for $1;
 intSizeTblPk                  alias for $2;
 fltMerhcandisePricePerQty     alias for $3;
 dtCreatedOn                   alias for $4;
 intMerchandiseSizeTblPk integer;
begin
 INSERT INTO merchandise_size_tbl(merchandise_color_tbl_pk,size_tbl_pk,merchandise_price_per_qty,created_on) VALUES(intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty,dtCreatedOn);
 if found then
  select into intMerchandiseSizeTblPk currval(''merchandise_size_tbl_seq'');
  return intMerchandiseSizeTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 154 (OID 264514)
-- Name: sp_upd_merchandise_size_tbl (integer, integer, character, character); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_size_tbl (integer, integer, character, character) RETURNS integer
    AS '
declare
 intMerchandiseSizeTblPk       alias for $1;
 intMerchandiseColorTblPk      alias for $2;
 intSizeTblPk                  alias for $3;
 fltMerhcandisePricePerQty     alias for $4;
begin
 UPDATE merchandise_size_tbl SET merchandise_color_tbl_pk=intMerchandiseColorTblPk,size_tbl_pk=intSizeTblPk,merhcandise_price_per_qty=fltMerhcandisePricePerQty where merchandise_size_tbl_pk=intMerchandiseSizeTblPk;
 if found then 
  return intMerchandiseSizeTblPk;
 else
  return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 155 (OID 264515)
-- Name: sp_upd_merchandise_size_tbl (integer, integer, integer, numeric); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_size_tbl (integer, integer, integer, numeric) RETURNS integer
    AS '
declare
 intMerchandiseSizeTblPk       alias for $1;
 intMerchandiseColorTblPk      alias for $2;
 intSizeTblPk                  alias for $3;
 fltMerhcandisePricePerQty     alias for $4;
begin
 UPDATE merchandise_size_tbl SET merchandise_color_tbl_pk=intMerchandiseColorTblPk,size_tbl_pk=intSizeTblPk,merchandise_price_per_qty=fltMerhcandisePricePerQty where merchandise_size_tbl_pk=intMerchandiseSizeTblPk;
 if found then 
  return intMerchandiseSizeTblPk;
 else
  return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 156 (OID 264516)
-- Name: sp_del_merchandise_size_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_size_tbl (integer) RETURNS integer
    AS '
declare
intMerchandiseSizeTblPk alias for $1;
begin
DELETE FROM merchandise_size_tbl where merchandise_size_tbl_pk=intMerchandiseSizeTblPk;        
if found then 
return intMerchandiseSizeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 158 (OID 264521)
-- Name: sp_act_deact_merchandise_size_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_merchandise_size_tbl (integer, integer) RETURNS integer
    AS 'declare
 intMerchandiseSizeTblPk  alias for $1;
 intIsActive              alias for $2;
begin
UPDATE merchandise_size_tbl SET is_active=intIsActive where merchandise_size_tbl_pk=intMerchandiseSizeTblPk;
if found then 
return intMerchandiseSizeTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 157 (OID 280885)
-- Name: sp_split (character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_split (character varying) RETURNS character varying[]
    AS '
declare
varString2Split alias for $1;
varPosOfDelimiter integer;
varDelimiterStr constant varchar :=''/'';
varEachStr varchar;
varRemainingStr varchar;
begin
varRemainingStr:=varString2Split;
varEachStr:=''{''; 
varPosOfDelimiter:= strpos(varRemainingStr,varDelimiterStr);
while varPosOfDelimiter!=0 loop
 varEachStr:=varEachStr||substring(varRemainingStr,0,varPosOfDelimiter);
 varRemainingStr:=substring(varRemainingStr,varPosOfDelimiter+1);
 varPosOfDelimiter:=strpos(varRemainingStr,varDelimiterStr);
 if varPosOfDelimiter!=0 then
  varEachStr:=varEachStr||'','';
 end if;
end loop;
varEachStr:=varEachStr||''}''; 
return varEachStr;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 159 (OID 289112)
-- Name: sp_ins_merchandise_color_tbl (integer, character, character, timestamp without time zone, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_color_tbl (integer, character, character, timestamp without time zone, integer) RETURNS integer
    AS '
declare
 intMerchandiseTblPk         alias for $1;
 txtColorOneValue            alias for $2;
 txtColorTwoValue            alias for $3;
 dtCreatedOn                 alias for $4;
 intMerchandiseCategoryTblPk alias for $5;             
 intMerchandiseColorTblPk integer;
begin
 INSERT INTO merchandise_color_tbl(merchandise_tbl_pk,color_one_value,color_two_value,created_on,merchandise_category_tbl_pk) VALUES(intMerchandiseTblPk,txtColorOneValue,txtColorTwoValue,dtCreatedOn,intMerchandiseCategoryTblPk);
 if found then
  select into intMerchandiseColorTblPk currval(''merchandise_color_tbl_seq'');
  return intMerchandiseColorTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 160 (OID 305459)
-- Name: sp_upd_merchandise_color_tbl (integer, integer, integer, character, character); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_color_tbl (integer, integer, integer, character, character) RETURNS integer
    AS '
declare
 intMerchandiseColorTblPk    alias for $1;
 intMerchandiseCategoryTblPk alias for $2;
 intMerchandiseTblPk         alias for $3;
 txtColorOneValue            alias for $4;
 txtColorTwoValue            alias for $5;
begin
UPDATE merchandise_color_tbl SET  merchandise_tbl_pk=intMerchandiseTblPk,merchandise_category_tbl_pk=intMerchandiseCategoryTblPk,color_one_value=txtColorOneValue,color_two_value=txtColorTwoValue  where merchandise_color_tbl_pk=intMerchandiseColorTblPk;
if found then 
return intMerchandiseColorTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 163 (OID 305470)
-- Name: sp_ins_merchandise_tbl (integer, character varying, character varying, character varying, character varying, integer, character varying, character varying, character varying, integer, timestamp without time zone, character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_tbl (integer, character varying, character varying, character varying, character varying, integer, character varying, character varying, character varying, integer, timestamp without time zone, character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare      
 intMerchandiseCategoryTblPk  alias for $1;   
 txtMerchandiseName           alias for $2;
 txtMerchandiseDesc           alias for $3;
 txtMerchandiseComm           alias for $4;
 txtMaterialDesc              alias for $5;
 intThresholdQty              alias for $6;
 txtDisplayImgPath            alias for $7;
 txtDesignImgPath             alias for $8;
 txtDeliveryNote              alias for $9;
 intCreatedBy                 alias for $10;
 dtCreatedOn                  alias for $11;
 txtColor                     alias for $12;
 txtSize                      alias for $13;
 fltPrice                     alias for $14; 
 --varMerchanidseTblPk        integer;                                  
 intMerchandiseColorTblPk     integer;
 txtClrCntr integer;
 txtClrComb varchar[];
 txtColor1 varchar;
 txtColor2 varchar;
 intSizeCntr integer;
 txtSizeArray varchar[];
 txtPriceArray varchar[]; 
 intSizeTblPk integer;
 fltMerhcandisePricePerQty numeric;
 intMerchandiseTblPk      integer;
begin
 INSERT INTO merchandise_tbl (merchandise_name,merchandise_description,merchandise_comment,material_description,threshold_quantity,merchandise_display_image,merchandise_design_image,delivery_note,user_profile_tbl_pk,created_on) VALUES (txtMerchandiseName,txtMerchandiseDesc,txtMerchandiseComm,txtMaterialDesc,intThresholdQty,lo_import(txtDisplayImgPath),lo_import(txtDesignImgPath),txtDeliveryNote,intCreatedBy,dtCreatedOn);
 if found then
  select into intMerchandiseTblPk currval(''merchandise_tbl_seq'') ;   
  perform sp_ins_merchandise_color_and_size(intMerchandiseTblPk,intMerchandiseCategoryTblPk,txtColor,txtSize,fltPrice);
perform sp_ins_merchandise_parents_tbl(intMerchandiseTblPk,intMerchandiseCategoryTblPk);
  return intMerchandiseTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 161 (OID 314138)
-- Name: sp_del_merchandise_tbl_trial (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_tbl_trial (integer, integer) RETURNS integer
    AS '
declare
intMerchandiseTblPk           alias for $1;
intMerchandiseCategoryTblPk   alias for $2;
varMerchandiseColorTblPk      merchandise_color_tbl%rowtype;
varMerchandiseSizeTblPk       merchandise_size_tbl%rowtype;
varPricePromotionTblPk        price_promotion_tbl%rowtype;
varMerchandiseParentsTblPk    merchandise_parents_tbl%rowtype;
intOrderDetailTblPk           integer;
intMerchandiseParentsTblPk    integer;
begin
 
 for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk loop
  
   select into intOrderDetailTblPk order_detail_tbl_pk from order_detail_tbl where merchandise_color_tbl_pk=varMerchandiseColorTblPk.merchandise_color_tbl_pk; 
   if found then
 return -1;
 end if;  
 end loop;
 
 -- delete from merchandise_parents_tbl
 delete from merchandise_parents_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk;
 
-- delete from merchandise_color_tbl 
 for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk loop
  for varMerchandiseSizeTblPk in select merchandise_size_tbl_pk from merchandise_size_tbl where merchandise_color_tbl_pk=varMerchandiseColorTblPk.merchandise_color_tbl_pk loop
   for varPricePromotionTblPk in select price_promotion_tbl_pk from price_promotion_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk loop
    delete from price_promotion_tbl where price_promotion_tbl_pk=varPricePromotionTblPk.price_promotion_tbl_pk;
   end loop;
   delete from merchandise_size_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk;
  end loop;
  delete from merchandise_color_tbl where merchandise_color_tbl_pk=varMerchandiseColorTblPk.merchandise_color_tbl_pk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk;
 end loop;
--delete from merchandise_tbl 
 select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_tbl_pk=intMerchandiseTblPk;   
 if intMerchandiseParentsTblPk is  null then
  delete from merchandise_tbl where merchandise_tbl_pk=intMerchandiseTblPk ;
 else
  return -2;
 end if;
 
if found then
return intMerchandiseTblPk;
else
return -1;
end if;

end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 162 (OID 314139)
-- Name: sp_upd_merchandise_tbl (integer, character varying, character varying, character varying, character varying, integer, character varying, character varying, character varying, integer[], character varying[], character varying[], character varying[], character varying[], character varying[], character varying[], integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_tbl (integer, character varying, character varying, character varying, character varying, integer, character varying, character varying, character varying, integer[], character varying[], character varying[], character varying[], character varying[], character varying[], character varying[], integer) RETURNS integer
    AS '
declare      
 intMerchandiseTblPk          alias for $1;   
 txtMerchandiseName           alias for $2;
 txtMerchandiseDesc           alias for $3;
 txtMerchandiseComm           alias for $4;
 txtMaterialDesc              alias for $5;
 intThresholdQty              alias for $6;
 txtDisplayImgPath            alias for $7;
 txtDesignImgPath             alias for $8;
 txtDeliveryNote              alias for $9;
 intMerClrTblPkArr            alias for $10;
 txtColor                     alias for $11;
 txtColorFlag                 alias for $12;
 txtMerSizePkArr              alias for $13;
 txtSize                      alias for $14;
 fltPrice                     alias for $15; 
 txtSizePriceFlag             alias for $16; 
 intMerchandiseCategoryTblPk  alias for $17; 
 intMerchandiseColorTblPk     integer;
 txtClrCntr integer;
 txtClrComb varchar[];
 txtColor1 varchar;
 txtColor2 varchar;
 txtColorFlagVal varchar;
 intSizeCntr integer;
 intMerSizePkArr integer[];
 txtSizeArray varchar[];
 txtPriceArray varchar[]; 
 txtSizePriceFlagArray varchar[];
 txtSizePriceFlagVal varchar;
 intSizeTblPk integer;
 intMerSizePkVal integer;
 fltMerhcandisePricePerQty numeric;
 intOrderDetailTblPk integer;
 varMerchandiseSizeTblPk  merchandise_size_tbl%rowtype;
 varPricePromotionTblPk   price_promotion_tbl%rowtype;
 INS constant varchar  :=''I'';
 UPD constant varchar  :=''U'';
 DEL constant varchar  :=''D'';
 testval integer;
begin
 --Check
 update merchandise_tbl set merchandise_name=txtMerchandiseName,merchandise_description=txtMerchandiseDesc,merchandise_comment=txtMerchandiseComm,material_description=txtMaterialDesc,threshold_quantity=intThresholdQty,merchandise_display_image=lo_import(txtDisplayImgPath),merchandise_design_image=lo_import(txtDesignImgPath),delivery_note=txtDeliveryNote where merchandise_tbl_pk=intMerchandiseTblPk;
 
 if found then
  txtClrCntr:=1;
  while txtColor [txtClrCntr] loop
   txtClrComb:=sp_split(txtColor [txtClrCntr]);
   txtColor1:=txtClrComb[1];
   txtColor2:=txtClrComb[2];
   intMerchandiseColorTblPk:=intMerClrTblPkArr[txtClrCntr];
   txtColorFlagVal:=txtColorFlag[txtClrCntr];
   txtSizeArray:=sp_split(txtSize[txtClrCntr]);
   txtPriceArray:=sp_split(fltPrice[txtClrCntr]);
   intMerSizePkArr:=sp_split(txtMerSizePkArr[txtClrCntr]);
   txtSizePriceFlagArray=sp_split(txtSizePriceFlag[txtClrCntr]);
   intSizeCntr:=1;
   --raise notice ''Pk % Color1 % Color2 % '', intMerchandiseTblPk,txtColor1,txtColor2;
 
  if txtColorFlagVal=INS then
   intMerchandiseColorTblPk:=sp_ins_merchandise_color_tbl(intMerchandiseTblPk,txtColor1,txtColor2,''now'',intMerchandiseCategoryTblPk);
   while txtSizeArray[intSizeCntr] loop
    intSizeTblPk:=txtSizeArray[intSizeCntr];
    fltMerhcandisePricePerQty:=txtPriceArray[intSizeCntr];
    perform sp_ins_merchandise_size_tbl(intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty,''now'');
    raise notice ''intSizeTblPk is %'',intSizeTblPk;
    intSizeCntr:=intSizeCntr+1;
   end loop;
  else if txtColorFlagVal=UPD then
   perform sp_upd_merchandise_color_tbl(intMerchandiseColorTblPk,intMerchandiseCategoryTblPk,intMerchandiseTblPk,txtColor1,txtColor2); --Check
   raise notice''before while'';
   while txtSizeArray[intSizeCntr] loop
    raise notice''after while'';
    intMerSizePkVal:=intMerSizePkArr[intSizeCntr];
    raise notice''intMerSizePkVal is %'',intMerSizePkVal;
    intSizeTblPk:=txtSizeArray[intSizeCntr];
    fltMerhcandisePricePerQty:=txtPriceArray[intSizeCntr];
    txtSizePriceFlagVal:=txtSizePriceFlagArray[intSizeCntr];
    if txtSizePriceFlagVal=INS then
     raise notice''INS:after if intMerSizePkVal is %'',intMerSizePkVal;
     raise notice''INS:after if intMerchandiseColorTblPk is %'',intMerchandiseColorTblPk;
     raise notice''INS:after if intSizeTblPk is %'',intSizeTblPk;
     perform sp_ins_merchandise_size_tbl(intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty,''now'');
     --raise notice ''Pk % Size % Price %'',intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty;
    else if txtSizePriceFlagVal=UPD then
     raise notice''UPD:after if intMerSizePkVal is %'',intMerSizePkVal;
     raise notice''UPD:intSizeTblPk is %'',intSizeTblPk;
     raise notice''UPD:fltMerhcandisePricePerQty is %'',fltMerhcandisePricePerQty;
     perform sp_upd_merchandise_size_tbl(intMerSizePkVal,intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty); --Check
     if found then
       raise notice''after sp_upd_merchandise_size_tbl testval is %'',testval;
     end if;
    else if txtSizePriceFlagVal=DEL then
     raise notice''DEL:after if intMerSizePkVal is %'',intMerSizePkVal;
     perform sp_del_merchandise_size_tbl(intMerSizePkVal); --Check
     if found then
       raise notice''sp_del_merchandise_size_tbl :Deleted %'',intMerSizePkVal;
     end if;
    end if;
    end if;
    end if;
    intSizeCntr:=intSizeCntr+1;
   end loop;
        
  else if txtColorFlagVal=DEL then
   select into intOrderDetailTblPk order_detail_tbl_pk from order_detail_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk ;
   if not found then
    for varMerchandiseSizeTblPk in select merchandise_size_tbl_pk from merchandise_size_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk loop
     for varPricePromotionTblPk in select price_promotion_tbl_pk from price_promotion_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk loop
      --delete from price_promotion_tbl where price_promotion_tbl_pk=varPricePromotionTblPk.price_promotion_tbl_pk;
      perform sp_del_price_promotion_tbl(varPricePromotionTblPk.price_promotion_tbl_pk);
     end loop;
     --delete from merchandise_size_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk;
          perform sp_del_merchandise_size_tbl(varMerchandiseSizeTblPk.merchandise_size_tbl_pk);
    end loop;
    perform sp_del_merchandise_color_tbl(intMerchandiseColorTblPk); --Check
   else 
    return -1;
   end if;   
  end if;
  end if;
  end if;
 txtClrCntr:=txtClrCntr+1;
end loop;
 
 return intMerchandiseTblPk;
 else
  return -2;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 166 (OID 330047)
-- Name: sp_ins_merchandise_color_and_size (integer, integer, character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_merchandise_color_and_size (integer, integer, character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare
 intMerchandiseTblPk          alias for $1;
 intMerchandiseCategoryTblPk  alias for $2;
 txtColor                     alias for $3;
 txtSize                      alias for $4;
 fltPrice                     alias for $5;
 intMerchandiseColorTblPk     integer;
 intMerchandiseSizeTblPk      integer;
 txtClrCntr integer;
 txtClrComb varchar[];
 txtColor1 varchar;
 txtColor2 varchar;
 intSizeCntr integer;
 txtSizeArray varchar[];
 txtPriceArray varchar[]; 
 intSizeTblPk integer;
 
 fltMerhcandisePricePerQty numeric;
begin
 txtClrCntr:=1;
while txtColor [txtClrCntr] loop
 txtClrComb:=sp_split(txtColor [txtClrCntr]);
 txtColor1:=txtClrComb[1];
 txtColor2:=txtClrComb[2];
 intMerchandiseColorTblPk:=sp_ins_merchandise_color_tbl(intMerchandiseTblPk,txtColor1,txtColor2,''now'',intMerchandiseCategoryTblPk);
 if intMerchandiseColorTblPk < 0 then
  raise notice''Insertion in merchandise_color_tbl failed'';
  return -1;
 end if;
 txtSizeArray:=sp_split(txtSize[txtClrCntr]);
 txtPriceArray:=sp_split(fltPrice[txtClrCntr]);
 intSizeCntr:=1;
 while txtSizeArray[intSizeCntr] loop
  intSizeTblPk:=txtSizeArray[intSizeCntr];
  fltMerhcandisePricePerQty:=txtPriceArray[intSizeCntr];
  intMerchandiseSizeTblPk:=sp_ins_merchandise_size_tbl(intMerchandiseColorTblPk,intSizeTblPk,fltMerhcandisePricePerQty,''now'');
  if intMerchandiseSizeTblPk < 0 then
 raise notice''Insertion in merchandise_size_tbl failed'';
 return -1;
end if;
intSizeCntr:=intSizeCntr+1;
 end loop;
 txtClrCntr:=txtClrCntr+1;
end loop;
 return intMerchandiseTblPk;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 164 (OID 330090)
-- Name: sp_del_merchandise_tbl (integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_tbl (integer, integer) RETURNS integer
    AS '
declare
intMerchandiseTblPk           alias for $1;
intMerchandiseCategoryTblPk   alias for $2;
varMerchandiseColorTblPk      merchandise_color_tbl%rowtype;
varMerchandiseParentsTblPk    merchandise_parents_tbl%rowtype;
intMerchandiseParentsTblPk    integer;
retval                        integer;
begin
 
-- delete from merchandise_color_tbl 
 for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk loop
  retval:= sp_del_merchandise_color_tbl(varMerchandiseColorTblPk.merchandise_color_tbl_pk);
  if retval < 0 then
   raise notice''retval =%'',retval;
   return retval;
  end if;
 end loop;
  
-- delete from merchandise_parents_tbl
 
 delete from merchandise_parents_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk;

--delete from merchandise_tbl 
 select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_tbl_pk=intMerchandiseTblPk;   
 if intMerchandiseParentsTblPk is  null then
  delete from merchandise_tbl where merchandise_tbl_pk=intMerchandiseTblPk ;
if found then
   return intMerchandiseTblPk;
  else
   return -1;
  end if;
 else
  return -2;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 167 (OID 330091)
-- Name: sp_del_merchandise_color_tbl (integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_del_merchandise_color_tbl (integer) RETURNS integer
    AS '
declare
intMerchandiseColorTblPk    alias for $1;
intOrderDetailTblPk         integer;
varMerchandiseSizeTblPk     merchandise_size_tbl%rowtype;
varPricePromotionTblPk      price_promotion_tbl%rowtype;
begin
 select into intOrderDetailTblPk order_detail_tbl_pk from order_detail_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk; 
 if found then
   raise notice ''DELETION FAILED: intMerchandiseColorTblPk % is being refered from order_detail_tbl.You can DEACTIVATE it.'',intMerchandiseColorTblPk;
 return -1;
 end if;  
 for varMerchandiseSizeTblPk in select merchandise_size_tbl_pk from merchandise_size_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk loop
  for varPricePromotionTblPk in select price_promotion_tbl_pk from price_promotion_tbl where merchandise_size_tbl_pk=varMerchandiseSizeTblPk.merchandise_size_tbl_pk loop
 perform sp_del_price_promotion_tbl(varPricePromotionTblPk.price_promotion_tbl_pk);
  end loop;
perform sp_del_merchandise_size_tbl(varMerchandiseSizeTblPk.merchandise_size_tbl_pk);
 end loop;
 delete from merchandise_color_tbl where merchandise_color_tbl_pk=intMerchandiseColorTblPk;
 if found then 
  return intMerchandiseColorTblPk;
 else
  return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 171 (OID 338265)
-- Name: sp_mov_merchandise (integer, integer, integer, character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_mov_merchandise (integer, integer, integer, character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare
 intMerchandiseTblPk         alias for $1;
 intSourceCategoryTblPk      alias for $2;
 intTargetCategoryTblPk      alias for $3;
 txtColor                    alias for $4;
 txtSize                     alias for $5;
 fltPrice                    alias for $6;
 intMerchandiseParentsTblPk  integer;
 intMerchandiseColorTblPk    integer;
 intOrderDetailTblPk         integer;
 varMerchandiseColorTblPk    record;--merchandise_color_tbl%rowtype;
 --txtColorArray               varchar[];
 --intcolorCntr                integer;
 --ColorOneValue               char(7);
 --ColorTwoValue               char(7);
 intIsActive integer :=0;
begin
--intcolorCntr:=1;
--while txtColor[intcolorCntr] loop
 --txtColorArray:=sp_split(txtColor[intcolorCntr]);
 --ColorOneValue:=txtColorArray[1];
 --ColorTwoValue:=txtColorArray[2];
 
 --Check whether the merchandise is already existing in the target category.
 select into intMerchandiseColorTblPk merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intTargetCategoryTblPk ;
 raise notice''intMerchandiseColorTblPk: %'',intMerchandiseColorTblPk;
 if found then
  raise notice''The category to which you are moving the merchandise is already existing in activated or deactivated state'';
  raise notice''%'',intMerchandiseColorTblPk;
  return -1;
 end if;
 --intcolorCntr:=intcolorCntr+1;
--end loop;
 
 
--first delete previous colors and sizes for that merchandise
select into varMerchandiseColorTblPk merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk and is_active=1;
 if not found then
  raise notice''intMerchandiseTblPk is either not present in intSourceCategoryTblPk or it has been deactivated'';
  return -1;
 end if;
for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk loop
 select into intOrderDetailTblPk order_detail_tbl_pk from order_detail_tbl where merchandise_color_tbl_pk=varMerchandiseColorTblPk.merchandise_color_tbl_pk;
 if found then
 for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk loop  
  perform sp_act_deact_merchandise_color_tbl(varMerchandiseColorTblPk.merchandise_color_tbl_pk,intIsActive);
 end loop;
 end if;
end loop;
for varMerchandiseColorTblPk in select * from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk loop  
 if varMerchandiseColorTblPk.is_active=1 then
perform sp_del_merchandise_color_tbl(varMerchandiseColorTblPk.merchandise_color_tbl_pk);
 end if;
end loop;
--insert new colors and sizes for that merchandise
perform sp_ins_merchandise_color_and_size(intMerchandiseTblPk,intTargetCategoryTblPk,txtColor,txtSize,fltPrice);
select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_category_tbl_Pk=intTargetCategoryTblPk and merchandise_tbl_pk=intMerchandiseTblPk; 
if not found then
 select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_category_tbl_Pk=intSourceCategoryTblPk and merchandise_tbl_pk=intMerchandiseTblPk;
 if intMerchandiseParentsTblPk is not null then  
  perform sp_upd_merchandise_parents_tbl(intMerchandiseParentsTblPk,intMerchandiseTblPk,intTargetCategoryTblPk);
 else
  raise notice ''intMerchandiseTblPk not found in intSourceCategoryTblPk'';
  return -1; 
 end if;
else 
 raise notice''Cant"t move.Merchandise is already present in that category.'';
 return -1;
 /*select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_category_tbl_Pk=intSourceCategoryTblPk and merchandise_tbl_pk=intMerchandiseTblPk;
 if intMerchandiseParentsTblPk is not null then  
  perform sp_del_merchandise_parents_tbl(intMerchandiseParentsTblPk);
 else
  raise notice ''intMerchandiseTblPk not found in intSourceCategoryTblPk'';
  return -1; 
 end if;*/
end if; 
return intMerchandiseTblPk;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 165 (OID 346430)
-- Name: sp_upd_merchandise_parents_tbl (integer, integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_merchandise_parents_tbl (integer, integer, integer) RETURNS integer
    AS ' 
declare
intMerchandiseParentsTblPk   alias for $1;
intMerchandiseTblPk          alias for $2;
intMerchandiseCategoryTblPk  alias for $3;
begin
 update merchandise_parents_tbl set merchandise_tbl_pk=intMerchandiseTblPk,merchandise_category_tbl_pk=intMerchandiseCategoryTblPk where merchandise_parents_tbl_pk=intMerchandiseParentsTblPk;
 if found then 
  return intMerchandiseParentsTblPk;
 else
  return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 22 (OID 346539)
-- Name: merchandise_color_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE merchandise_color_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 170 (OID 362874)
-- Name: sp_act_deact_merchandise_tbl (integer, integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_merchandise_tbl (integer, integer, integer) RETURNS integer
    AS '
declare
 intMerchandiseTblPk           alias for $1;
 intMerchandiseCategoryTblPk   alias for $2;
 intIsActive                   alias for $3;
 varMerchandiseColorTblPk      record;
begin 
 for varMerchandiseColorTblPk in select * from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk loop
perform sp_act_deact_merchandise_color_tbl(varMerchandiseColorTblPk.merchandise_color_tbl_pk,intIsActive);
 end loop;
 if intIsActive=0 then
 select into varMerchandiseColorTblPk merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk!=intMerchandiseCategoryTblPk and is_active=1;
 if not found then
 UPDATE merchandise_tbl SET is_active=intIsActive where merchandise_tbl_pk=intMerchandiseTblPk;
if found then
 return intMerchandiseTblPk;
else
return -1;
end if;
 else
  raise notice''Could not deactivate merchandise as it is present in other category also'';
return -1;
 end if;
 else 
  UPDATE merchandise_tbl SET is_active=intIsActive where merchandise_tbl_pk=intMerchandiseTblPk;
if found then
 return intMerchandiseTblPk;
else
 return -1;
end if;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 172 (OID 362894)
-- Name: sp_act_deact_merchandise_parents_tbl (integer, integer, integer); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_act_deact_merchandise_parents_tbl (integer, integer, integer) RETURNS integer
    AS '
declare
 intMerchandiseTblPk           alias for $1;
 intMerchandiseCategoryTblPk   alias for $2;
 intIsActive                   alias for $3;
 varMerchandiseColorTblPk      record;
begin 
 for varMerchandiseColorTblPk in select * from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk loop
perform sp_act_deact_merchandise_color_tbl(varMerchandiseColorTblPk.merchandise_color_tbl_pk,intIsActive);
 end loop;
 UPDATE merchandise_parents_tbl SET is_active=intIsActive where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intMerchandiseCategoryTblPk;
 if found then
 return intMerchandiseTblPk;
 else
  return -1;
 end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 173 (OID 371059)
-- Name: sp_cop_merchandise (integer, integer, integer, character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_cop_merchandise (integer, integer, integer, character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare
 intMerchandiseTblPk         alias for $1;
 intSourceCategoryTblPk      alias for $2;
 intTargetCategoryTblPk      alias for $3;
 txtColor                    alias for $4;
 txtSize                     alias for $5;
 fltPrice                    alias for $6;
 intMerchandiseParentsTblPk  integer;
 intMerchandiseColorTblPk    integer;
 --intOrderDetailTblPk         integer;
 varMerchandiseColorTblPk    record;
 --intIsActive integer :=0;
begin
 --Check whether the merchandise is already existing in the target category.
 select into intMerchandiseColorTblPk merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intTargetCategoryTblPk ;
 raise notice''intMerchandiseColorTblPk: %'',intMerchandiseColorTblPk;
 if found then
  raise notice''The category to which you are moving the merchandise is already existing in activated or deactivated state'';
  raise notice''%'',intMerchandiseColorTblPk;
  return -1;
 end if;
 
-- check whether the merchandise is present or not in the merchandise_color_tbl
select into varMerchandiseColorTblPk merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk and is_active=1;
 if not found then
  raise notice''intMerchandiseTblPk is either not present in intSourceCategoryTblPk or it has been deactivated'';
  return -1;
 end if;
--insert new colors and sizes for that merchandise
--for varMerchandiseColorTblPk in select merchandise_color_tbl_pk from merchandise_color_tbl where merchandise_tbl_pk=intMerchandiseTblPk and merchandise_category_tbl_pk=intSourceCategoryTblPk loop
 perform sp_ins_merchandise_color_and_size(intMerchandiseTblPk,intTargetCategoryTblPk,txtColor,txtSize,fltPrice);
 select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_category_tbl_Pk=intTargetCategoryTblPk and merchandise_tbl_pk=intMerchandiseTblPk; 
 if not found then
  select into intMerchandiseParentsTblPk merchandise_parents_tbl_pk from merchandise_parents_tbl where merchandise_category_tbl_Pk=intSourceCategoryTblPk and merchandise_tbl_pk=intMerchandiseTblPk;
  if intMerchandiseParentsTblPk is not null then  
   perform sp_ins_merchandise_parents_tbl(intMerchandiseTblPk,intTargetCategoryTblPk);
  else
   raise notice ''intMerchandiseTblPk not found in intSourceCategoryTblPk'';
   return -1; 
  end if;
 else 
  raise notice''Cant"t move.Merchandise is already present in that category.'';
  return -1;
 end if; 
 return intMerchandiseTblPk;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 174 (OID 379264)
-- Name: sp_ins_outlet_tbl (character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_outlet_tbl (character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
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
--dtCreatedOn   alias for $11;
intOutletTblPk integer;
right_now timestamp;
begin
 right_now:=''now'';
 INSERT into outlet_tbl(outlet_id,outlet_name,outlet_description,address1,address2,address3,city,state,country,pincode,CREATEd_on) values(txtOutletId,txtOutletName,txtOutletDesc,txtAddress1,txtAddress2,txtAddress3,txtCity,txtState,txtCountry,txtPincode,right_now);
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
-- TOC entry 23 (OID 395651)
-- Name: outlet_tbl_seq; Type: SEQUENCE; Schema: bulbul; Owner: bulbul
--

CREATE SEQUENCE outlet_tbl_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 175 (OID 461245)
-- Name: sp_ins_inet_bank_tbl (character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_inet_bank_tbl (character varying, character varying) RETURNS integer
    AS '
declare
 txtBankName  alias for $1;
 intBankAcNo  alias for $2;
 --dtCreatedOn  alias for $3;
 right_now    timestamp;
 intInetBankTblPk integer;
begin
 right_now:=''now'';
 INSERT INTO inet_banking_tbl(bank_name,bank_account_number,CREATEd_on) VALUES(txtBankName,intBankAcNo,right_now);
 if found then
  select into intInetBankTblPk currval(''inet_banking_tbl_seq'');
  return intInetBankTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 176 (OID 461252)
-- Name: sp_upd_inet_bank_tbl (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_inet_bank_tbl (integer, character varying, character varying) RETURNS integer
    AS 'declare
intInetBankTblPk  alias for $1;
txtBankName       alias for $2;
txtBankAcNo       alias for $3; 
begin
UPDATE inet_banking_tbl SET bank_name=txtBankName, bank_account_number=txtBankAcNo where inet_banking_tbl_pk=intInetBankTblPk;
if found then 
return intInetBankTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 177 (OID 494023)
-- Name: sp_ins_user_access_tbl (integer, character, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_user_access_tbl (integer, character, character varying, character varying) RETURNS integer
    AS '
declare          
intUserProfileTblPk alias for $1;
txtModuleId         alias for $2;
txtPermissionIds    alias for $3;
txtPermissionValues alias for $4;
intUserAccessTblPk integer;
begin
 INSERT INTO user_access_tbl(user_profile_tbl_pk,module_id,permission_ids,permission_values) VALUES(intUserProfileTblPk,txtModuleId,txtPermissionIds,txtPermissionValues);
 if found then
 select into intUserAccessTblPk currval(''user_access_tbl_seq'');
 return intUserAccessTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 178 (OID 494026)
-- Name: sp_upd_user_access_tbl (integer, integer, character, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_user_access_tbl (integer, integer, character, character varying, character varying) RETURNS integer
    AS '
declare
intUserAccessTblPk   alias for $1;
intUserProfileTblPk  alias for $2;
txtModuleId          alias for $3;
txtPermissionIds     alias for $4;
txtPermissionValues  alias for $5;
begin
UPDATE user_access_tbl SET user_profile_tbl_pk=intUserProfileTblPk,module_id=txtModuleId,permission_ids=txtPermissionIds,permission_values=txtPermissionValues where user_access_tbl_pk=intUserAccessTblPk;
if found then 
  return intUserAccessTblPk;
  else
  return -1;
  end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 179 (OID 494027)
-- Name: sp_ins_user_profile_tbl (character varying, character varying, character varying, character varying, character, character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_user_profile_tbl (character varying, character varying, character varying, character varying, character, character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare          
txtUserId                alias for $1;
txtFirstName             alias for $2;
txtLastName              alias for $3;
txtPassword              alias for $4;
txtUserCategory          alias for $5;
txtModuleIdArray         alias for $6;
txtPermissionIdsArray    alias for $7;
txtPermissionValuesArray alias for $8; 
--dtCreatedOn            alias for $6;
intUserProfileTblPk      integer;
right_now                timestamp;
intModuleCount           integer:=1;
txtModuleId              varchar;
txtPermissionIds         varchar;
txtPermissionValues      varchar;
begin
 right_now=''now'';
 INSERT INTO user_profile_tbl(user_id,user_first_name,user_last_name,user_password,user_category_id,created_on) VALUES(txtUserId,txtFirstName,txtLastName,txtPassword,txtUserCategory,right_now);
 if found then
  
 select into intUserProfileTblPk currval(''user_profile_tbl_seq'');
 while txtModuleIdArray[intModuleCount] loop
txtModuleId:=txtModuleIdArray[intModuleCount];
txtPermissionIds:=txtPermissionIdsArray[intModuleCount];
txtPermissionValues:=txtPermissionValuesArray[intModuleCount];
perform sp_ins_user_access_tbl(intUserProfileTblPk,txtModuleId,txtPermissionIds,txtPermissionValues);
raise notice ''Module Details :  % % % '',txtModuleId,txtPermissionIds,txtPermissionValues;
intModuleCount:=intModuleCount+1;
 end loop;
 return intUserProfileTblPk;
 else 
 return-1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 180 (OID 494048)
-- Name: sp_upd_user_profile_tbl (integer, character varying, character varying, character varying, character varying, character, integer[], character varying[], character varying[], character varying[]); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_user_profile_tbl (integer, character varying, character varying, character varying, character varying, character, integer[], character varying[], character varying[], character varying[]) RETURNS integer
    AS '
declare
  intUserProfileTblPk             alias for $1;
  txtUserId                       alias for $2;
  txtFirstName                    alias for $3;
  txtLastName                     alias for $4;
  txtPassword                     alias for $5;
  txtUserCategory                 alias for $6;
  intUserAccessTblPkArray         alias for $7;
  txtModuleIdArray                alias for $8;
  txtPermissionIdsArray           alias for $9;
  txtPermissionValuesArray        alias for $10; 
  intCount                        integer:=1;
  intUserAccessTblPk              integer;
  txtModuleId                     varchar;
  txtPermissionIds                varchar;
  txtPermissionValues             varchar;
 
begin
  UPDATE user_profile_tbl SET user_id=txtUserId, user_first_name=txtFirstName, user_last_name=txtLastName, user_password=txtPassword, user_category_id=txtUserCategory where user_profile_tbl_pk=intUserProfileTblPk;
  if found then 
   while intUserAccessTblPkArray[intCount] loop
    intUserAccessTblPk=intUserAccessTblPkArray[intCount];
    txtModuleId:=txtModuleIdArray[intCount];
    txtPermissionIds:=txtPermissionIdsArray[intCount];
    txtPermissionValues:=txtPermissionValuesArray[intCount];
    raise notice ''txtModuleId :  % '',txtModuleId;
    if (intUserAccessTblPk=-1) then
      perform sp_ins_user_access_tbl(intUserProfileTblPk,txtModuleId,txtPermissionIds,txtPermissionValues);
    else
     if(txtModuleId) then
       perform sp_upd_user_access_tbl(intUserAccessTblPk,intUserProfileTblPk,txtModuleId,txtPermissionIds,txtPermissionValues);      
     else
       perform sp_del_user_access_tbl(intUserAccessTblPk);
     end if;
    end if;
    raise notice ''Module Details :  % % % '',txtModuleId,txtPermissionIds,txtPermissionValues;
    intCount:=intCount+1;
   end loop;
   
   return intUserProfileTblPk;
  else
  return -1;
  end if;
  end;
  '
    LANGUAGE plpgsql;


--
-- TOC entry 183 (OID 526814)
-- Name: sp_ins_font_tbl (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_font_tbl (integer, character varying, character varying) RETURNS integer
    AS '
declare          
 intCategoryTblPk  alias for $1;
 txtFontName       alias for $2;
 txtFontDesc       alias for $3;
 --dtCreatedOn       alias for $4;
 right_now         timestamp;
 intFontTblPk      integer;
begin
 right_now:=''now'';
 INSERT INTO font_tbl (font_category_tbl_pk,font_name,font_description,CREATEd_on) VALUES (intCategoryTblPk,txtFontName,txtFontDesc,right_now);
 if found then
 select into intFontTblPk currval(''font_tbl_seq'') ;   
 return intFontTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 184 (OID 526892)
-- Name: sp_ins_font_category_tbl (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_font_category_tbl (integer, character varying, character varying) RETURNS integer
    AS '
declare          
  intCategoryTblFk alias for $1;
txtCategory      alias for $2;
txtCategoryDesc  alias for $3;
--dtCreatedOn      alias for $4;
right_now        timestamp;
intCategoryTblPk integer;
begin
 right_now:=''now'';
 if(intCategoryTblFk=-1) then
  select into intCategoryTblPk font_category_tbl_pk from font_category_tbl where font_category_tbl_fk is null and font_category=txtCategory;
if not found then
    INSERT INTO font_category_tbl(font_category,font_category_description,CREATEd_on) VALUES(txtCategory,txtCategoryDesc,right_now);
else
  raise exception ''User Exception: Can not add duplicate unique key.'';
  return -1;
end if;
 else
  INSERT INTO font_category_tbl(font_category_tbl_fk,font_category,font_category_description,CREATEd_on) VALUES(intCategoryTblFk,txtCategory,txtCategoryDesc,right_now);
 end if;
 
 if found then
 select into intCategoryTblPk currval(''font_category_tbl_seq'') ;   
return intCategoryTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 187 (OID 584300)
-- Name: sp_ins_clipart_category_tbl (integer, character varying, character varying); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_clipart_category_tbl (integer, character varying, character varying) RETURNS integer
    AS '
declare          
intCategoryTblFk alias for $1;
txtCategory      alias for $2;
txtCategoryDesc  alias for $3;
--dtCreatedOn      alias for $4;
intCategoryTblPk integer;
right_now        timestamp;
begin
 right_now=''now'';
 if(intCategoryTblFk=-1) then
  select into intCategoryTblPk clipart_category_tbl_pk from clipart_category_tbl where clipart_category_tbl_fk is null and clipart_category=txtCategory;
if not found then
    INSERT INTO clipart_category_tbl(clipart_category,clipart_category_description,CREATEd_on) VALUES(txtCategory,txtCategoryDesc,right_now);
else
  raise exception ''User Exception: Can not add duplicate unique key.'';
  return -1;
end if;
 else
   INSERT INTO clipart_category_tbl(clipart_category_tbl_fk,clipart_category,clipart_category_description,CREATEd_on) VALUES(intCategoryTblFk,txtCategory,txtCategoryDesc,right_now);
 end if;
 if found then
 select into intCategoryTblPk currval(''clipart_category_tbl_seq'') ;   
return intCategoryTblPk;
 else
 return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 188 (OID 584304)
-- Name: sp_ins_clipart_tbl (integer, character varying, character varying, oid, character varying, numeric); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_ins_clipart_tbl (integer, character varying, character varying, oid, character varying, numeric) RETURNS integer
    AS '
declare          
 intCategoryTblPk    alias for $1;
 txtClipartName      alias for $2;
 txtClipartKeywords  alias for $3;
 imgOid              alias for $4;
 txtContentType      alias for $5;
 numContentSize      alias for $6;
 --dtCreatedOn         alias for $5;
 intClipartTblPk integer;
 right_now           timestamp;
begin
 right_now:=''now'';
 INSERT INTO clipart_tbl(clipart_category_tbl_pk,clipart_name,clipart_keywords,clipart_image,content_type,content_size,CREATEd_on) VALUES(intCategoryTblPk,txtClipartName,txtClipartKeywords,imgOid,txtContentType,numContentSize,right_now);
 if found then
 select into intClipartTblPk currval(''clipart_tbl_seq'') ;   
 return intClipartTblPk;
 else
  return -1;
 end if;
end; 
'
    LANGUAGE plpgsql;


--
-- TOC entry 189 (OID 592480)
-- Name: sp_upd_clipart_tbl (integer, integer, character varying, character varying, character varying, numeric); Type: FUNCTION; Schema: bulbul; Owner: bulbul
--

CREATE FUNCTION sp_upd_clipart_tbl (integer, integer, character varying, character varying, character varying, numeric) RETURNS integer
    AS '
declare
intClipartTblPk     alias for $1;
intCategoryTblPk    alias for $2;
txtClipartName      alias for $3;
txtClipartKeywords  alias for $4;
--imgOid              alias for $5;
txtContentType      alias for $5;
numContentSize      alias for $6;

begin
UPDATE clipart_tbl set clipart_category_tbl_pk=intCategoryTblPk,clipart_name=txtClipartName,clipart_keywords=txtClipartKeywords,content_type=txtContentType,content_size=numContentSize where clipart_tbl_pk=intClipartTblPk;
if found then
return intClipartTblPk;
else
return -1;
end if;
end;
'
    LANGUAGE plpgsql;


--
-- TOC entry 57 (OID 207482)
-- Name: outlet_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY outlet_tbl
    ADD CONSTRAINT outlet_tbl_pkey PRIMARY KEY (outlet_tbl_pk);


--
-- TOC entry 59 (OID 207484)
-- Name: user_profile_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY user_profile_tbl
    ADD CONSTRAINT user_profile_tbl_pkey PRIMARY KEY (user_profile_tbl_pk);


--
-- TOC entry 60 (OID 207486)
-- Name: user_profile_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY user_profile_tbl
    ADD CONSTRAINT user_profile_tbl_ukey UNIQUE (user_id);


--
-- TOC entry 61 (OID 207488)
-- Name: user_access_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY user_access_tbl
    ADD CONSTRAINT user_access_tbl_pkey PRIMARY KEY (user_access_tbl_pk);


--
-- TOC entry 190 (OID 207490)
-- Name: user_profile_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY user_access_tbl
    ADD CONSTRAINT user_profile_tbl_fkey FOREIGN KEY (user_profile_tbl_pk) REFERENCES user_profile_tbl(user_profile_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 63 (OID 207500)
-- Name: clipart_category_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_category_tbl
    ADD CONSTRAINT clipart_category_tbl_pkey PRIMARY KEY (clipart_category_tbl_pk);


--
-- TOC entry 191 (OID 207504)
-- Name: clipart_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_category_tbl
    ADD CONSTRAINT clipart_category_tbl_fkey FOREIGN KEY (clipart_category_tbl_fk) REFERENCES clipart_category_tbl(clipart_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 65 (OID 207508)
-- Name: clipart_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_tbl
    ADD CONSTRAINT clipart_tbl_pkey PRIMARY KEY (clipart_tbl_pk);


--
-- TOC entry 64 (OID 207510)
-- Name: clipart_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_tbl
    ADD CONSTRAINT clipart_tbl_ckey UNIQUE (clipart_category_tbl_pk, clipart_name);


--
-- TOC entry 192 (OID 207512)
-- Name: clipart_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_tbl
    ADD CONSTRAINT clipart_category_tbl_fkey FOREIGN KEY (clipart_category_tbl_pk) REFERENCES clipart_category_tbl(clipart_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 67 (OID 207516)
-- Name: font_category_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_category_tbl
    ADD CONSTRAINT font_category_tbl_pkey PRIMARY KEY (font_category_tbl_pk);


--
-- TOC entry 193 (OID 207520)
-- Name: font_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_category_tbl
    ADD CONSTRAINT font_category_tbl_fkey FOREIGN KEY (font_category_tbl_fk) REFERENCES font_category_tbl(font_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 69 (OID 207524)
-- Name: font_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_tbl
    ADD CONSTRAINT font_tbl_pkey PRIMARY KEY (font_tbl_pk);


--
-- TOC entry 194 (OID 207528)
-- Name: font_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_tbl
    ADD CONSTRAINT font_category_tbl_fkey FOREIGN KEY (font_category_tbl_pk) REFERENCES font_category_tbl(font_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 70 (OID 207532)
-- Name: merchandise_category_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_category_tbl
    ADD CONSTRAINT merchandise_category_tbl_pkey PRIMARY KEY (merchandise_category_tbl_pk);


--
-- TOC entry 71 (OID 207534)
-- Name: merchandise_category_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_category_tbl
    ADD CONSTRAINT merchandise_category_tbl_ukey UNIQUE (merchandise_category);


--
-- TOC entry 195 (OID 207536)
-- Name: merchandise_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_category_tbl
    ADD CONSTRAINT merchandise_category_tbl_fkey FOREIGN KEY (merchandise_category_tbl_fk) REFERENCES merchandise_category_tbl(merchandise_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 196 (OID 207540)
-- Name: user_profile_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_category_tbl
    ADD CONSTRAINT user_profile_tbl_fkey FOREIGN KEY (user_profile_tbl_pk) REFERENCES user_profile_tbl(user_profile_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 72 (OID 207544)
-- Name: merchandise_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_tbl
    ADD CONSTRAINT merchandise_tbl_pkey PRIMARY KEY (merchandise_tbl_pk);


--
-- TOC entry 73 (OID 207546)
-- Name: merchandise_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_tbl
    ADD CONSTRAINT merchandise_tbl_ukey UNIQUE (merchandise_name);


--
-- TOC entry 197 (OID 207548)
-- Name: user_profile_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_tbl
    ADD CONSTRAINT user_profile_tbl_fkey FOREIGN KEY (user_profile_tbl_pk) REFERENCES user_profile_tbl(user_profile_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 75 (OID 207552)
-- Name: merchandise_parents_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_parents_tbl
    ADD CONSTRAINT merchandise_parents_tbl_pkey PRIMARY KEY (merchandise_parents_tbl_pk);


--
-- TOC entry 198 (OID 207554)
-- Name: merchandise_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_parents_tbl
    ADD CONSTRAINT merchandise_tbl_fkey FOREIGN KEY (merchandise_tbl_pk) REFERENCES merchandise_tbl(merchandise_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 199 (OID 207558)
-- Name: merchandise_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_parents_tbl
    ADD CONSTRAINT merchandise_category_tbl_fkey FOREIGN KEY (merchandise_category_tbl_pk) REFERENCES merchandise_category_tbl(merchandise_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 77 (OID 207562)
-- Name: merchandise_color_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_color_tbl
    ADD CONSTRAINT merchandise_color_tbl_pkey PRIMARY KEY (merchandise_color_tbl_pk);


--
-- TOC entry 200 (OID 207566)
-- Name: merchandise_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_color_tbl
    ADD CONSTRAINT merchandise_tbl_fkey FOREIGN KEY (merchandise_tbl_pk) REFERENCES merchandise_tbl(merchandise_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 79 (OID 207570)
-- Name: size_type_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_type_tbl
    ADD CONSTRAINT size_type_tbl_pkey PRIMARY KEY (size_type_tbl_pk);


--
-- TOC entry 78 (OID 207572)
-- Name: size_type_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_type_tbl
    ADD CONSTRAINT size_type_tbl_ckey UNIQUE (merchandise_category_tbl_pk, size_type_id);


--
-- TOC entry 202 (OID 207574)
-- Name: merchandise_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_type_tbl
    ADD CONSTRAINT merchandise_category_tbl_fkey FOREIGN KEY (merchandise_category_tbl_pk) REFERENCES merchandise_category_tbl(merchandise_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 81 (OID 207578)
-- Name: size_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_tbl
    ADD CONSTRAINT size_tbl_pkey PRIMARY KEY (size_tbl_pk);


--
-- TOC entry 80 (OID 207580)
-- Name: size_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_tbl
    ADD CONSTRAINT size_tbl_ckey UNIQUE (size_type_tbl_pk, size_id);


--
-- TOC entry 203 (OID 207582)
-- Name: size_type_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY size_tbl
    ADD CONSTRAINT size_type_tbl_fkey FOREIGN KEY (size_type_tbl_pk) REFERENCES size_type_tbl(size_type_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 83 (OID 207586)
-- Name: merchandise_size_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_size_tbl
    ADD CONSTRAINT merchandise_size_tbl_pkey PRIMARY KEY (merchandise_size_tbl_pk);


--
-- TOC entry 204 (OID 207588)
-- Name: merchandise_color_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_size_tbl
    ADD CONSTRAINT merchandise_color_tbl_fkey FOREIGN KEY (merchandise_color_tbl_pk) REFERENCES merchandise_color_tbl(merchandise_color_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 205 (OID 207592)
-- Name: size_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_size_tbl
    ADD CONSTRAINT size_tbl_fkey FOREIGN KEY (size_tbl_pk) REFERENCES size_tbl(size_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 84 (OID 207596)
-- Name: price_promotion_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY price_promotion_tbl
    ADD CONSTRAINT price_promotion_tbl_pkey PRIMARY KEY (price_promotion_tbl_pk);


--
-- TOC entry 206 (OID 207598)
-- Name: merchandise_size_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY price_promotion_tbl
    ADD CONSTRAINT merchandise_size_tbl_fkey FOREIGN KEY (merchandise_size_tbl_pk) REFERENCES merchandise_size_tbl(merchandise_size_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 55 (OID 207602)
-- Name: inet_banking_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY inet_banking_tbl
    ADD CONSTRAINT inet_banking_tbl_pkey PRIMARY KEY (inet_banking_tbl_pk);


--
-- TOC entry 85 (OID 207604)
-- Name: order_header_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_header_tbl
    ADD CONSTRAINT order_header_tbl_pkey PRIMARY KEY (order_header_tbl_pk);


--
-- TOC entry 207 (OID 207606)
-- Name: outlet_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_header_tbl
    ADD CONSTRAINT outlet_tbl_fkey FOREIGN KEY (outlet_tbl_pk) REFERENCES outlet_tbl(outlet_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 208 (OID 207610)
-- Name: inet_banking_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_header_tbl
    ADD CONSTRAINT inet_banking_tbl_fkey FOREIGN KEY (inet_banking_tbl_pk) REFERENCES inet_banking_tbl(inet_banking_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 86 (OID 207614)
-- Name: order_detail_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_detail_tbl
    ADD CONSTRAINT order_detail_tbl_pkey PRIMARY KEY (order_detail_tbl_pk);


--
-- TOC entry 209 (OID 207616)
-- Name: order_header_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_detail_tbl
    ADD CONSTRAINT order_header_tbl_fkey FOREIGN KEY (order_header_tbl_pk) REFERENCES order_header_tbl(order_header_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 210 (OID 207620)
-- Name: merchandise_color_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_detail_tbl
    ADD CONSTRAINT merchandise_color_tbl_fkey FOREIGN KEY (merchandise_color_tbl_pk) REFERENCES merchandise_color_tbl(merchandise_color_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 87 (OID 207624)
-- Name: order_item_detail_tbl_pkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_item_detail_tbl
    ADD CONSTRAINT order_item_detail_tbl_pkey PRIMARY KEY (order_item_detail_tbl_pk);


--
-- TOC entry 211 (OID 207626)
-- Name: order_detail_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_item_detail_tbl
    ADD CONSTRAINT order_detail_tbl_fkey FOREIGN KEY (order_detail_tbl_pk) REFERENCES order_detail_tbl(order_detail_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 212 (OID 207630)
-- Name: merchandise_size_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY order_item_detail_tbl
    ADD CONSTRAINT merchandise_size_tbl_fkey FOREIGN KEY (merchandise_size_tbl_pk) REFERENCES merchandise_size_tbl(merchandise_size_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 58 (OID 207634)
-- Name: outlet_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY outlet_tbl
    ADD CONSTRAINT outlet_tbl_ukey UNIQUE (outlet_id);


--
-- TOC entry 74 (OID 231738)
-- Name: merchandise_parents_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_parents_tbl
    ADD CONSTRAINT merchandise_parents_tbl_ckey UNIQUE (merchandise_tbl_pk, merchandise_category_tbl_pk);


--
-- TOC entry 201 (OID 289103)
-- Name: merchandise_category_tbl_fkey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_color_tbl
    ADD CONSTRAINT merchandise_category_tbl_fkey FOREIGN KEY (merchandise_category_tbl_pk) REFERENCES merchandise_category_tbl(merchandise_category_tbl_pk) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 82 (OID 346651)
-- Name: merchandise_size_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_size_tbl
    ADD CONSTRAINT merchandise_size_tbl_ckey UNIQUE (merchandise_color_tbl_pk, size_tbl_pk);


--
-- TOC entry 76 (OID 346655)
-- Name: merchandise_color_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY merchandise_color_tbl
    ADD CONSTRAINT merchandise_color_tbl_ckey UNIQUE (merchandise_tbl_pk, color_one_value, color_two_value, merchandise_category_tbl_pk);


--
-- TOC entry 56 (OID 461236)
-- Name: inet_banking_tbl_ukey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY inet_banking_tbl
    ADD CONSTRAINT inet_banking_tbl_ukey UNIQUE (bank_name);


--
-- TOC entry 68 (OID 526873)
-- Name: font_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_tbl
    ADD CONSTRAINT font_tbl_ckey UNIQUE (font_category_tbl_pk, font_name);


--
-- TOC entry 66 (OID 526893)
-- Name: font_category_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY font_category_tbl
    ADD CONSTRAINT font_category_tbl_ckey UNIQUE (font_category_tbl_fk, font_category);


--
-- TOC entry 62 (OID 543303)
-- Name: clipart_category_tbl_ckey; Type: CONSTRAINT; Schema: bulbul; Owner: bulbul
--

ALTER TABLE ONLY clipart_category_tbl
    ADD CONSTRAINT clipart_category_tbl_ckey UNIQUE (clipart_category_tbl_fk, clipart_category);


--
-- TOC entry 213 (OID 231627)
-- Name: clipart_tbl_upd_rules; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE clipart_tbl_upd_rules AS ON UPDATE TO clipart_tbl DO SELECT lo_unlink(old.clipart_image) AS lo_unlink WHERE (old.clipart_image <> new.clipart_image);


--
-- TOC entry 214 (OID 231628)
-- Name: clipart_tbl_del_rules; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE clipart_tbl_del_rules AS ON DELETE TO clipart_tbl DO SELECT lo_unlink(old.clipart_image) AS lo_unlink;


--
-- TOC entry 215 (OID 231655)
-- Name: merchandise_category_tbl_upd_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_category_tbl_upd_rule AS ON UPDATE TO merchandise_category_tbl DO SELECT lo_unlink(old.merchandise_category_image) AS lo_unlink WHERE (old.merchandise_category_image <> new.merchandise_category_image);


--
-- TOC entry 216 (OID 231656)
-- Name: merchandise_category_tbl_del_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_category_tbl_del_rule AS ON DELETE TO merchandise_category_tbl DO SELECT lo_unlink(old.merchandise_category_image) AS lo_unlink;


--
-- TOC entry 217 (OID 231686)
-- Name: merchandise_tbl_display_upd_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_tbl_display_upd_rule AS ON UPDATE TO merchandise_tbl DO SELECT lo_unlink(old.merchandise_display_image) AS lo_unlink WHERE (old.merchandise_display_image <> new.merchandise_display_image);


--
-- TOC entry 218 (OID 231687)
-- Name: merchandise_tbl_design_upd_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_tbl_design_upd_rule AS ON UPDATE TO merchandise_tbl DO SELECT lo_unlink(old.merchandise_design_image) AS lo_unlink WHERE (old.merchandise_design_image <> new.merchandise_design_image);


--
-- TOC entry 219 (OID 314094)
-- Name: merchandise_tbl_del_design_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_tbl_del_design_rule AS ON DELETE TO merchandise_tbl DO SELECT lo_unlink(old.merchandise_design_image) AS lo_unlink;


--
-- TOC entry 220 (OID 314095)
-- Name: merchandise_tbl_del_display_rule; Type: RULE; Schema: bulbul; Owner: bulbul
--

CREATE RULE merchandise_tbl_del_display_rule AS ON DELETE TO merchandise_tbl DO SELECT lo_unlink(old.merchandise_display_image) AS lo_unlink;


