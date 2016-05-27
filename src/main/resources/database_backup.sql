CREATE TYPE order_status AS ENUM (
    'INIT',
    'ACCEPTED',
    'IN_PROGRESS',
    'DONE'
);


CREATE TABLE authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);

CREATE TABLE car (
    brand character varying(50),
    model character varying(50),
    engine_volume double precision,
    vin character varying(30),
    registration_number character varying(10),
    username character varying(50) NOT NULL,
    id integer NOT NULL
);

CREATE SEQUENCE cars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2614 (class 0 OID 0)
-- Dependencies: 187
-- Name: cars_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE cars_id_seq OWNED BY car.id;


--
-- TOC entry 189 (class 1259 OID 2879073)
-- Name: email_verification; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE email_verification (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    token character varying(64) NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 188 (class 1259 OID 2879071)
-- Name: email_verification_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE email_verification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2615 (class 0 OID 0)
-- Dependencies: 188
-- Name: email_verification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE email_verification_id_seq OWNED BY email_verification.id;


--
-- TOC entry 177 (class 1259 OID 2736112)
-- Name: mechanic; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mechanic (
    id integer NOT NULL,
    station_id integer NOT NULL,
    username character varying(50)
);


--
-- TOC entry 175 (class 1259 OID 2736108)
-- Name: mechanic_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mechanic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2616 (class 0 OID 0)
-- Dependencies: 175
-- Name: mechanic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mechanic_id_seq OWNED BY mechanic.id;


--
-- TOC entry 181 (class 1259 OID 2736147)
-- Name: mechanic_order; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mechanic_order (
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    mechanic_id bigint NOT NULL
);


--
-- TOC entry 180 (class 1259 OID 2736145)
-- Name: mechanic_order_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mechanic_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2617 (class 0 OID 0)
-- Dependencies: 180
-- Name: mechanic_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mechanic_order_id_seq OWNED BY mechanic_order.id;


--
-- TOC entry 176 (class 1259 OID 2736110)
-- Name: mechanic_station_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mechanic_station_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2618 (class 0 OID 0)
-- Dependencies: 176
-- Name: mechanic_station_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mechanic_station_id_seq OWNED BY mechanic.station_id;


--
-- TOC entry 182 (class 1259 OID 2813763)
-- Name: oauth_access_token; Type: TABLE; Schema: public; Owner: -
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


--
-- TOC entry 183 (class 1259 OID 2813769)
-- Name: oauth_refresh_token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE oauth_refresh_token (
    token_id character varying(256),
    token bytea,
    authentication bytea
);


--
-- TOC entry 179 (class 1259 OID 2736135)
-- Name: order; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "order" (
    id bigint NOT NULL,
    initial_date timestamp with time zone DEFAULT now() NOT NULL,
    work_description character varying(10000),
    status order_status NOT NULL,
    planned_end_date timestamp with time zone,
    end_date timestamp with time zone,
    station_id bigint,
    planned_cost double precision,
    total_cost double precision
);


--
-- TOC entry 178 (class 1259 OID 2736133)
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2619 (class 0 OID 0)
-- Dependencies: 178
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE order_id_seq OWNED BY "order".id;


--
-- TOC entry 191 (class 1259 OID 2879087)
-- Name: password_recovery; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE password_recovery (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    token character varying(64) NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 2879085)
-- Name: password_recovery_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE password_recovery_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2620 (class 0 OID 0)
-- Dependencies: 190
-- Name: password_recovery_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE password_recovery_id_seq OWNED BY password_recovery.id;


--
-- TOC entry 174 (class 1259 OID 2736096)
-- Name: station; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE station (
    id integer NOT NULL,
    address character varying(255) NOT NULL,
    description character varying(255),
    latitude double precision,
    longitude double precision,
    station_name character varying(50)
);


--
-- TOC entry 173 (class 1259 OID 2736094)
-- Name: station_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE station_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2621 (class 0 OID 0)
-- Dependencies: 173
-- Name: station_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE station_id_seq OWNED BY station.id;


--
-- TOC entry 184 (class 1259 OID 2823606)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    username character varying(50) NOT NULL,
    password character varying(256) NOT NULL,
    enabled boolean NOT NULL,
    firstname character varying(50),
    lastname character varying(50),
    phone_number character varying(50)
);


--
-- TOC entry 2470 (class 2604 OID 2837718)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY car ALTER COLUMN id SET DEFAULT nextval('cars_id_seq'::regclass);


--
-- TOC entry 2471 (class 2604 OID 2879076)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY email_verification ALTER COLUMN id SET DEFAULT nextval('email_verification_id_seq'::regclass);


--
-- TOC entry 2465 (class 2604 OID 2736115)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic ALTER COLUMN id SET DEFAULT nextval('mechanic_id_seq'::regclass);


--
-- TOC entry 2466 (class 2604 OID 2736116)
-- Name: station_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic ALTER COLUMN station_id SET DEFAULT nextval('mechanic_station_id_seq'::regclass);


--
-- TOC entry 2469 (class 2604 OID 2736150)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic_order ALTER COLUMN id SET DEFAULT nextval('mechanic_order_id_seq'::regclass);


--
-- TOC entry 2467 (class 2604 OID 2736138)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- TOC entry 2473 (class 2604 OID 2879090)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY password_recovery ALTER COLUMN id SET DEFAULT nextval('password_recovery_id_seq'::regclass);


--
-- TOC entry 2464 (class 2604 OID 2736099)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY station ALTER COLUMN id SET DEFAULT nextval('station_id_seq'::regclass);


--
-- TOC entry 2487 (class 2606 OID 2837720)
-- Name: cars_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY car
    ADD CONSTRAINT cars_pkey PRIMARY KEY (id);


--
-- TOC entry 2489 (class 2606 OID 2879079)
-- Name: email_verification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email_verification
    ADD CONSTRAINT email_verification_pkey PRIMARY KEY (id);


--
-- TOC entry 2482 (class 2606 OID 2736152)
-- Name: mechanic_order_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT mechanic_order_pkey PRIMARY KEY (id);


--
-- TOC entry 2478 (class 2606 OID 2736121)
-- Name: mechanic_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_pkey PRIMARY KEY (id);


--
-- TOC entry 2480 (class 2606 OID 2736144)
-- Name: order_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- TOC entry 2491 (class 2606 OID 2879093)
-- Name: password_recovery_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY password_recovery
    ADD CONSTRAINT password_recovery_pkey PRIMARY KEY (id);


--
-- TOC entry 2476 (class 2606 OID 2736104)
-- Name: station_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY station
    ADD CONSTRAINT station_pkey PRIMARY KEY (id);


--
-- TOC entry 2484 (class 2606 OID 2823610)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- TOC entry 2485 (class 1259 OID 2823619)
-- Name: ix_auth_username; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX ix_auth_username ON authorities USING btree (username, authority);


--
-- TOC entry 2495 (class 2606 OID 2823614)
-- Name: fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);


--
-- TOC entry 2494 (class 2606 OID 2736159)
-- Name: mechanic_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT mechanic_fkey FOREIGN KEY (mechanic_id) REFERENCES mechanic(id) ON DELETE CASCADE;


--
-- TOC entry 2492 (class 2606 OID 2836292)
-- Name: mechanic_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_users_foreign_key FOREIGN KEY (username) REFERENCES users(username);


--
-- TOC entry 2493 (class 2606 OID 2736154)
-- Name: order_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mechanic_order
    ADD CONSTRAINT order_fkey FOREIGN KEY (order_id) REFERENCES "order"(id) ON DELETE CASCADE;


--
-- TOC entry 2497 (class 2606 OID 2879080)
-- Name: user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email_verification
    ADD CONSTRAINT user_fkey FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE;


--
-- TOC entry 2498 (class 2606 OID 2879094)
-- Name: user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY password_recovery
    ADD CONSTRAINT user_fkey FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE;


--
-- TOC entry 2496 (class 2606 OID 2836287)
-- Name: username_constraints; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY car
    ADD CONSTRAINT username_constraints FOREIGN KEY (username) REFERENCES users(username);


-- Completed on 2016-05-27 21:27:57 AST

--
-- PostgreSQL database dump complete
--
