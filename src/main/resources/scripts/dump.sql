--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: order_status; Type: TYPE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE TYPE order_status AS ENUM (
    'INIT',
    'ACCEPTED',
    'IN_PROGRESS',
    'DONE'
);


ALTER TYPE order_status OWNER TO ybqfodjdqpjogw;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authorities; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);


ALTER TABLE authorities OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE mechanic (
    id integer NOT NULL,
    station_id integer NOT NULL,
    username character varying(50)
);


ALTER TABLE mechanic OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic_id_seq; Type: SEQUENCE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE SEQUENCE mechanic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mechanic_id_seq OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER SEQUENCE mechanic_id_seq OWNED BY mechanic.id;


--
-- Name: mechanic_order; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE mechanic_order (
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    mechanic_id bigint NOT NULL
);


ALTER TABLE mechanic_order OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic_order_id_seq; Type: SEQUENCE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE SEQUENCE mechanic_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mechanic_order_id_seq OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER SEQUENCE mechanic_order_id_seq OWNED BY mechanic_order.id;


--
-- Name: mechanic_station_id_seq; Type: SEQUENCE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE SEQUENCE mechanic_station_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mechanic_station_id_seq OWNER TO ybqfodjdqpjogw;

--
-- Name: mechanic_station_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER SEQUENCE mechanic_station_id_seq OWNED BY mechanic.station_id;


--
-- Name: oauth_access_token; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE oauth_access_token (
    token_id character varying(256),
    token bytea,
    authentication_id character varying(256) NOT NULL,
    user_name character varying(256),
    client_id character varying(256),
    authentication bytea,
    refresh_token character varying(256)
);


ALTER TABLE oauth_access_token OWNER TO ybqfodjdqpjogw;

--
-- Name: oauth_refresh_token; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE oauth_refresh_token (
    token_id character varying(256),
    token bytea,
    authentication bytea
);


ALTER TABLE oauth_refresh_token OWNER TO ybqfodjdqpjogw;

--
-- Name: order; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE "order" (
    id bigint NOT NULL,
    initial_date timestamp with time zone DEFAULT now() NOT NULL,
    work_description character varying(10000),
    status order_status NOT NULL,
    planned_cost money,
    planned_end_date timestamp with time zone,
    total_cost money,
    end_date timestamp with time zone,
    station_id bigint
);


ALTER TABLE "order" OWNER TO ybqfodjdqpjogw;

--
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE order_id_seq OWNER TO ybqfodjdqpjogw;

--
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER SEQUENCE order_id_seq OWNED BY "order".id;


--
-- Name: station; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE station (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    description character varying(255),
    latitude double precision,
    longitude double precision
);


ALTER TABLE station OWNER TO ybqfodjdqpjogw;

--
-- Name: station_id_seq; Type: SEQUENCE; Schema: public; Owner: ybqfodjdqpjogw
--

CREATE SEQUENCE station_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE station_id_seq OWNER TO ybqfodjdqpjogw;

--
-- Name: station_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER SEQUENCE station_id_seq OWNED BY station.id;


--
-- Name: user_profile; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE user_profile (
    brand character varying(50),
    model character varying(50),
    engine_volume real,
    vin character varying(30),
    registration_number character varying(10),
    id integer NOT NULL,
    username character varying(50) NOT NULL
);


ALTER TABLE user_profile OWNER TO ybqfodjdqpjogw;

--
-- Name: users; Type: TABLE; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE TABLE users (
    username character varying(50) NOT NULL,
    password character varying(256) NOT NULL,
    enabled boolean NOT NULL,
    firstname character varying(50),
    lastname character varying(50)
);


ALTER TABLE users OWNER TO ybqfodjdqpjogw;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic ALTER COLUMN id SET DEFAULT nextval('mechanic_id_seq'::regclass);


--
-- Name: station_id; Type: DEFAULT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic ALTER COLUMN station_id SET DEFAULT nextval('mechanic_station_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic_order ALTER COLUMN id SET DEFAULT nextval('mechanic_order_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY station ALTER COLUMN id SET DEFAULT nextval('station_id_seq'::regclass);


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY authorities (username, authority) FROM stdin;
marissa	USER
vladimirapolaiko@gmail.com	ROLE_USER
vladimirapolaiko@yandex.ru	ROLE_USER
vladimirapolaiko@yand.ru	ROLE_USER
vladimirapolaiko@yahoo.com	ROLE_USER
greens239@gmail.com	ROLE_USER
\.


--
-- Data for Name: mechanic; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY mechanic (id, station_id, username) FROM stdin;
23	1	\N
\.


--
-- Name: mechanic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ybqfodjdqpjogw
--

SELECT pg_catalog.setval('mechanic_id_seq', 23, true);


--
-- Data for Name: mechanic_order; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY mechanic_order (id, order_id, mechanic_id) FROM stdin;
\.


--
-- Name: mechanic_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ybqfodjdqpjogw
--

SELECT pg_catalog.setval('mechanic_order_id_seq', 13, true);


--
-- Name: mechanic_station_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ybqfodjdqpjogw
--

SELECT pg_catalog.setval('mechanic_station_id_seq', 1, false);


--
-- Data for Name: oauth_access_token; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) FROM stdin;
a58b4148609e1363ee8eb97434f7ca88	\\254\\355\\000\\005sr\\000Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken\\014\\262\\2366\\033$\\372\\316\\002\\000\\006L\\000\\025additionalInformationt\\000\\017Ljava/util/Map;L\\000\\012expirationt\\000\\020Ljava/util/Date;L\\000\\014refreshTokent\\000?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\\000\\005scopet\\000\\017Ljava/util/Set;L\\000\\011tokenTypet\\000\\022Ljava/lang/String;L\\000\\005valueq\\000~\\000\\005xpsr\\000\\036java.util.Collections$EmptyMapY6\\024\\205Z\\334\\347\\320\\002\\000\\000xpsr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T-:\\2076xsr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationq\\000~\\000\\002xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valueq\\000~\\000\\005xpt\\000$8195c960-e3f4-435f-bef5-04912f19a732sq\\000~\\000\\011w\\010\\000\\000\\001T\\233\\013\\350\\300xsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001ct\\000\\026Ljava/util/Collection;xpsr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\004?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writext\\000\\006bearert\\000$bddaa06d-b73e-4723-8370-804f5061a5db	798372184608e829938f63b1cb618853	marissa	353b302c44574f565045687e534e7d6a	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\004USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\007marissaxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\007marissa	2dda11e16b96f2000e7e7411ac2e307c
e28eaae7fb13fd5cea347f0b36d61872	\\254\\355\\000\\005sr\\000Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken\\014\\262\\2366\\033$\\372\\316\\002\\000\\006L\\000\\025additionalInformationt\\000\\017Ljava/util/Map;L\\000\\012expirationt\\000\\020Ljava/util/Date;L\\000\\014refreshTokent\\000?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\\000\\005scopet\\000\\017Ljava/util/Set;L\\000\\011tokenTypet\\000\\022Ljava/lang/String;L\\000\\005valueq\\000~\\000\\005xpsr\\000\\036java.util.Collections$EmptyMapY6\\024\\205Z\\334\\347\\320\\002\\000\\000xpsr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T-_\\250Nxsr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationq\\000~\\000\\002xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valueq\\000~\\000\\005xpt\\000$4b926206-c3e3-4369-940d-6793262af97esq\\000~\\000\\011w\\010\\000\\000\\001T\\305KBMxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001ct\\000\\026Ljava/util/Collection;xpsr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writext\\000\\006bearert\\000$4b7962e2-5356-41c4-a115-b3eca8f72755	ee09cbdc2cf4c8ea16f7830b99b8fb16	vladimirapolaiko@yahoo.com	353b302c44574f565045687e534e7d6a	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\011ROLE_USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\032vladimirapolaiko@yahoo.comxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\032vladimirapolaiko@yahoo.com	58db51a351bf73fd1ef72356beb8f067
2eb24162347acf07683e622c602f224d	\\254\\355\\000\\005sr\\000Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken\\014\\262\\2366\\033$\\372\\316\\002\\000\\006L\\000\\025additionalInformationt\\000\\017Ljava/util/Map;L\\000\\012expirationt\\000\\020Ljava/util/Date;L\\000\\014refreshTokent\\000?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\\000\\005scopet\\000\\017Ljava/util/Set;L\\000\\011tokenTypet\\000\\022Ljava/lang/String;L\\000\\005valueq\\000~\\000\\005xpsr\\000\\036java.util.Collections$EmptyMapY6\\024\\205Z\\334\\347\\320\\002\\000\\000xpsr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T7[\\001\\354xsr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationq\\000~\\000\\002xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valueq\\000~\\000\\005xpt\\000$334d6d6c-2356-455b-a76f-898df8595d92sq\\000~\\000\\011w\\010\\000\\000\\001T\\317F\\233\\354xsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001ct\\000\\026Ljava/util/Collection;xpsr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writext\\000\\006bearert\\000$3c2bf7cb-3d57-496d-9dca-c658efa84c6b	db726a35452f8bc26d37205fd01a3dce	greens239@gmail.com	353b302c44574f565045687e534e7d6a	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\011ROLE_USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\023greens239@gmail.comxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\023greens239@gmail.com	a6223abbe52c6f11f6992217bc270c84
\.


--
-- Data for Name: oauth_refresh_token; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY oauth_refresh_token (token_id, token, authentication) FROM stdin;
d56b55204520e5b6dccc60925d4a9cea	\\254\\355\\000\\005sr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationt\\000\\020Ljava/util/Date;xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valuet\\000\\022Ljava/lang/String;xpt\\000$55666c7f-9e69-40e9-ba1d-76e7feb42e08sr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T\\232\\342\\332Ax	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000!java.lang.invoke.SerializedLambdaoa\\320\\224,)6\\205\\002\\000\\012I\\000\\016implMethodKind[\\000\\014capturedArgst\\000\\023[Ljava/lang/Object;L\\000\\016capturingClasst\\000\\021Ljava/lang/Class;L\\000\\030functionalInterfaceClasst\\000\\022Ljava/lang/String;L\\000\\035functionalInterfaceMethodNameq\\000~\\000\\020L\\000"functionalInterfaceMethodSignatureq\\000~\\000\\020L\\000\\011implClassq\\000~\\000\\020L\\000\\016implMethodNameq\\000~\\000\\020L\\000\\023implMethodSignatureq\\000~\\000\\020L\\000\\026instantiatedMethodTypeq\\000~\\000\\020xp\\000\\000\\000\\006ur\\000\\023[Ljava.lang.Object;\\220\\316X\\237\\020s)l\\002\\000\\000xp\\000\\000\\000\\000vr\\000*org.servicestation.config.SecurityConfig$1g\\351\\2411.\\273\\363-\\002\\000\\001L\\000\\006this$0t\\000*Lorg/servicestation/config/SecurityConfig;xpt\\0002org/springframework/security/core/GrantedAuthorityt\\000\\014getAuthorityt\\000\\024()Ljava/lang/String;t\\000*org/servicestation/config/SecurityConfig$1t\\000 lambda$getAuthorities$19ba1de8$1q\\000~\\000\\031q\\000~\\000\\031xq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\020L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\037xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\020L\\000\\021requestParametersq\\000~\\000\\035L\\000\\005scopeq\\000~\\000\\037xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\035xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\003t\\000\\015refresh_tokent\\000$e396d82b-fffe-4f09-a274-a2cf332e075bt\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\007marissaxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\0000w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004roleq\\000~\\000\\020xpt\\000\\004USERxsq\\000~\\000%?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000/w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\003xxxxsq\\000~\\0000w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\021xq\\000~\\000?sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000%?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\003q\\000~\\000'q\\000~\\000(q\\000~\\000)q\\000~\\000*q\\000~\\000+q\\000~\\000,x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\037L\\000\\010passwordq\\000~\\000\\020L\\000\\010usernameq\\000~\\000\\020xp\\001\\001\\001\\001sq\\000~\\000-sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\021xpt\\000\\007marissa
2dda11e16b96f2000e7e7411ac2e307c	\\254\\355\\000\\005sr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationt\\000\\020Ljava/util/Date;xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valuet\\000\\022Ljava/lang/String;xpt\\000$8195c960-e3f4-435f-bef5-04912f19a732sr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T\\233\\013\\350\\300x	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\004USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\007marissaxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\007marissa
58db51a351bf73fd1ef72356beb8f067	\\254\\355\\000\\005sr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationt\\000\\020Ljava/util/Date;xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valuet\\000\\022Ljava/lang/String;xpt\\000$4b926206-c3e3-4369-940d-6793262af97esr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T\\305KBMx	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\011ROLE_USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\032vladimirapolaiko@yahoo.comxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\032vladimirapolaiko@yahoo.com
a6223abbe52c6f11f6992217bc270c84	\\254\\355\\000\\005sr\\000Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\\337Gc\\235\\320\\311\\267\\002\\000\\001L\\000\\012expirationt\\000\\020Ljava/util/Date;xr\\000Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\\341\\016\\012cT\\324^\\002\\000\\001L\\000\\005valuet\\000\\022Ljava/lang/String;xpt\\000$334d6d6c-2356-455b-a76f-898df8595d92sr\\000\\016java.util.Datehj\\201\\001KYt\\031\\003\\000\\000xpw\\010\\000\\000\\001T\\317F\\233\\354x	\\254\\355\\000\\005sr\\000Aorg.springframework.security.oauth2.provider.OAuth2Authentication\\275@\\013\\002\\026bR\\023\\002\\000\\002L\\000\\015storedRequestt\\000<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\\000\\022userAuthenticationt\\0002Lorg/springframework/security/core/Authentication;xr\\000Gorg.springframework.security.authentication.AbstractAuthenticationToken\\323\\252(~nGd\\016\\002\\000\\003Z\\000\\015authenticatedL\\000\\013authoritiest\\000\\026Ljava/util/Collection;L\\000\\007detailst\\000\\022Ljava/lang/Object;xp\\000sr\\000&java.util.Collections$UnmodifiableList\\374\\017%1\\265\\354\\216\\020\\002\\000\\001L\\000\\004listt\\000\\020Ljava/util/List;xr\\000,java.util.Collections$UnmodifiableCollection\\031B\\000\\200\\313^\\367\\036\\002\\000\\001L\\000\\001cq\\000~\\000\\004xpsr\\000\\023java.util.ArrayListx\\201\\322\\035\\231\\307a\\235\\003\\000\\001I\\000\\004sizexp\\000\\000\\000\\001w\\004\\000\\000\\000\\001sr\\000Borg.springframework.security.core.authority.SimpleGrantedAuthority\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\001L\\000\\004rolet\\000\\022Ljava/lang/String;xpt\\000\\011ROLE_USERxq\\000~\\000\\014psr\\000:org.springframework.security.oauth2.provider.OAuth2Request\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\007Z\\000\\010approvedL\\000\\013authoritiesq\\000~\\000\\004L\\000\\012extensionst\\000\\017Ljava/util/Map;L\\000\\013redirectUriq\\000~\\000\\016L\\000\\007refresht\\000;Lorg/springframework/security/oauth2/provider/TokenRequest;L\\000\\013resourceIdst\\000\\017Ljava/util/Set;L\\000\\015responseTypesq\\000~\\000\\024xr\\0008org.springframework.security.oauth2.provider.BaseRequest6(z>\\243qi\\275\\002\\000\\003L\\000\\010clientIdq\\000~\\000\\016L\\000\\021requestParametersq\\000~\\000\\022L\\000\\005scopeq\\000~\\000\\024xpt\\000 353b302c44574f565045687e534e7d6asr\\000%java.util.Collections$UnmodifiableMap\\361\\245\\250\\376t\\365\\007B\\002\\000\\001L\\000\\001mq\\000~\\000\\022xpsr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002t\\000\\012grant_typet\\000\\010passwordt\\000\\010usernamet\\000\\023greens239@gmail.comxsr\\000%java.util.Collections$UnmodifiableSet\\200\\035\\222\\321\\217\\233\\200U\\002\\000\\000xq\\000~\\000\\011sr\\000\\027java.util.LinkedHashSet\\330l\\327Z\\225\\335*\\036\\002\\000\\000xr\\000\\021java.util.HashSet\\272D\\205\\225\\226\\270\\2674\\003\\000\\000xpw\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\002t\\000\\004readt\\000\\005writex\\001sq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001sq\\000~\\000\\015t\\000\\004USERxsq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\000w\\010\\000\\000\\000\\020\\000\\000\\000\\000xppsq\\000~\\000"w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\001t\\000\\016servicestationxsq\\000~\\000#w\\014\\000\\000\\000\\020?@\\000\\000\\000\\000\\000\\000xsr\\000Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\002L\\000\\013credentialsq\\000~\\000\\005L\\000\\011principalq\\000~\\000\\005xq\\000~\\000\\003\\001sq\\000~\\000\\007sq\\000~\\000\\013\\000\\000\\000\\001w\\004\\000\\000\\000\\001q\\000~\\000\\017xq\\000~\\0001sr\\000\\027java.util.LinkedHashMap4\\300N\\\\\\020l\\300\\373\\002\\000\\001Z\\000\\013accessOrderxq\\000~\\000\\032?@\\000\\000\\000\\000\\000\\006w\\010\\000\\000\\000\\010\\000\\000\\000\\002q\\000~\\000\\034q\\000~\\000\\035q\\000~\\000\\036q\\000~\\000\\037x\\000psr\\0002org.springframework.security.core.userdetails.User\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\007Z\\000\\021accountNonExpiredZ\\000\\020accountNonLockedZ\\000\\025credentialsNonExpiredZ\\000\\007enabledL\\000\\013authoritiesq\\000~\\000\\024L\\000\\010passwordq\\000~\\000\\016L\\000\\010usernameq\\000~\\000\\016xp\\001\\001\\001\\001sq\\000~\\000 sr\\000\\021java.util.TreeSet\\335\\230P\\223\\225\\355\\207[\\003\\000\\000xpsr\\000Forg.springframework.security.core.userdetails.User$AuthorityComparator\\000\\000\\000\\000\\000\\000\\001\\220\\002\\000\\000xpw\\004\\000\\000\\000\\001q\\000~\\000\\017xpt\\000\\023greens239@gmail.com
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY "order" (id, initial_date, work_description, status, planned_cost, planned_end_date, total_cost, end_date, station_id) FROM stdin;
4	2016-03-07 20:07:01.258763+00	\N	INIT	\N	\N	\N	\N	\N
8	2016-03-07 20:13:50.966597+00	\N	INIT	\N	\N	\N	\N	\N
9	2016-03-07 20:20:01.504411+00	\N	INIT	\N	\N	\N	\N	\N
10	2016-03-07 20:25:15.392299+00	\N	INIT	\N	\N	\N	\N	\N
11	2016-03-09 21:11:49.259437+00	\N	INIT	\N	\N	\N	\N	\N
12	2016-03-09 21:24:57.369644+00	\N	INIT	\N	\N	\N	\N	\N
13	2016-03-09 21:25:47.326395+00	\N	INIT	\N	\N	\N	\N	\N
14	2016-03-09 21:43:51.753946+00	\N	INIT	\N	\N	\N	\N	\N
15	2016-03-09 21:48:12.936149+00	\N	INIT	\N	\N	\N	\N	\N
16	2016-03-09 21:52:38.334419+00	\N	INIT	\N	\N	\N	\N	\N
17	2016-03-09 21:57:09.433858+00	\N	INIT	\N	\N	\N	\N	\N
18	2016-03-09 21:59:13.955933+00	\N	DONE	\N	\N	\N	\N	5
19	2016-03-09 22:03:14.308016+00	\N	DONE	\N	\N	\N	\N	5
20	2016-03-09 22:04:39.079556+00	\N	DONE	\N	\N	\N	\N	5
21	2016-03-09 22:10:47.873101+00	\N	DONE	\N	\N	\N	\N	5
22	2016-03-09 22:12:22.341303+00	\N	DONE	\N	\N	\N	\N	5
23	2016-03-09 22:25:04.231728+00	\N	DONE	\N	\N	\N	\N	5
24	2016-03-09 22:27:16.175463+00	\N	DONE	\N	\N	\N	\N	5
25	2016-03-09 22:27:45.066982+00	\N	DONE	\N	\N	\N	\N	5
27	2016-03-09 22:29:22.150128+00	\N	DONE	\N	\N	\N	\N	5
26	2016-03-09 22:29:22.145848+00	\N	DONE	\N	\N	\N	\N	5
28	2016-03-09 22:30:11.95121+00	\N	DONE	\N	\N	\N	\N	5
29	2016-03-09 22:30:28.361373+00	\N	DONE	\N	\N	\N	\N	5
34	2016-03-10 08:21:39.505888+00	\N	INIT	\N	\N	\N	\N	\N
35	2016-03-10 08:21:40.3148+00	\N	INIT	\N	\N	\N	\N	\N
36	2016-03-18 08:03:30.956522+00	\N	INIT	\N	\N	\N	\N	\N
37	2016-03-18 08:23:54.743529+00	\N	INIT	\N	\N	\N	\N	\N
38	2016-03-18 08:34:11.676693+00	\N	INIT	\N	\N	\N	\N	\N
39	2016-03-18 08:56:51.632544+00	\N	INIT	\N	\N	\N	\N	\N
40	2016-03-18 09:09:53.118125+00	\N	INIT	\N	\N	\N	\N	\N
\.


--
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ybqfodjdqpjogw
--

SELECT pg_catalog.setval('order_id_seq', 40, true);


--
-- Data for Name: station; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY station (id, name, address, description, latitude, longitude) FROM stdin;
4	Grun station	Tavlays st. 34/3	Good servicestation	23.8249509999999987	53.6783369999999991
6	Servicestation 1	Sovetskaya st. 4	Another description	54.2999999999999972	32.3999999999999986
7	Servicestation 1	Sovetskaya st. 4	Another description	54.2999999999999972	32.3999999999999986
8	Servicestation 1	Sovetskaya st. 4	Another description	54.2999999999999972	32.3999999999999986
9	Servicestation 1	Sovetskaya st. 4	Good servicestation	54.2999999999999972	32.3999999999999986
\.


--
-- Name: station_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ybqfodjdqpjogw
--

SELECT pg_catalog.setval('station_id_seq', 9, true);


--
-- Data for Name: user_profile; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY user_profile (brand, model, engine_volume, vin, registration_number, id, username) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: ybqfodjdqpjogw
--

COPY users (username, password, enabled, firstname, lastname) FROM stdin;
marissa	koala	t	\N	\N
vladimirapolaiko@gmail.com	$2a$10$TE7/.2YkoKB8/2K.d7kO3umugCttdrkzZZECO13PjPNo6sv.t6Gce	t	Vladimir	Apolaiko
vladimirapolaiko@yandex.ru	$2a$10$QwUs71ODiSUkjM5vXpRtDecRBXYlN/u3jAomu4DxXyXcSTe3gZf7C	t	Vladimir	Apolaiko
vladimirapolaiko@yand.ru	$2a$10$SjkRJng3n7.tvdPxBBj4e.cRsZJU4WNJsbREGf6mqdENY2Q8adlCa	t	Vladimir	Apolaiko
vladimirapolaiko@yahoo.com	A987654321	t	Vladimir	Apolaiko
greens239@gmail.com	$2a$10$9oEq3tAzJt.NLTgwdTyuZ.8P25VSIkFU4qbIPf4Pm1coKhgXMqUJm	t	Vladimir	Apolaiko
\.


--
-- Name: mechanic_order_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT mechanic_order_pkey PRIMARY KEY (id);


--
-- Name: mechanic_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_pkey PRIMARY KEY (id);


--
-- Name: order_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: station_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY station
    ADD CONSTRAINT station_pkey PRIMARY KEY (id);


--
-- Name: user_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY user_profile
    ADD CONSTRAINT user_profile_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: ix_auth_username; Type: INDEX; Schema: public; Owner: ybqfodjdqpjogw; Tablespace: 
--

CREATE UNIQUE INDEX ix_auth_username ON authorities USING btree (username, authority);


--
-- Name: fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);


--
-- Name: mechanic_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT mechanic_fkey FOREIGN KEY (mechanic_id) REFERENCES mechanic(id) ON DELETE CASCADE;


--
-- Name: mechanic_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_users_foreign_key FOREIGN KEY (username) REFERENCES users(username);


--
-- Name: order_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT order_fkey FOREIGN KEY (order_id) REFERENCES "order"(id) ON DELETE CASCADE;


--
-- Name: username_constraints; Type: FK CONSTRAINT; Schema: public; Owner: ybqfodjdqpjogw
--

ALTER TABLE ONLY user_profile
    ADD CONSTRAINT username_constraints FOREIGN KEY (username) REFERENCES users(username);


--
-- PostgreSQL database dump complete
--

