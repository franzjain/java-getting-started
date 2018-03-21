CREATE SEQUENCE public.people_id_seq
    INCREMENT 1
    START 10
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.people_id_seq
    OWNER TO vmwswqcsethfvq;

-- Table: public.people

-- DROP TABLE public.people;

CREATE TABLE public.people
(
    id integer NOT NULL DEFAULT nextval('people_id_seq'::regclass),
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default",
    zipcode text COLLATE pg_catalog."default",
    phone_number text COLLATE pg_catalog."default" NOT NULL,
    color text COLLATE pg_catalog."default",
    CONSTRAINT people_pkey PRIMARY KEY (id),
    CONSTRAINT unique_people UNIQUE (firstname, lastname, phone_number, address, zipcode, color)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.people
    OWNER to vmwswqcsethfvq;