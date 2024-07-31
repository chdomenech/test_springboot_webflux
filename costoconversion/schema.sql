CREATE TABLE IF NOT EXISTS conversions (
	conversion_id varchar(16) NOT NULL,
	model varchar(50) NOT NULL,
	cryptocurrency varchar NOT NULL,
	time_life int4 NOT NULL,
	id int8 NOT NULL,
	CONSTRAINT conversions_un UNIQUE (conversion_id),
	CONSTRAINT convertion_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  versions (
	conversion_id int8 NOT NULL,
	"version" varchar(50) NOT NULL,
	priceusd float8 NOT NULL,
	id int8 NOT NULL,
	pricecryptocurrency float8 NOT NULL,
	CONSTRAINT versions_pkey PRIMARY KEY (id),
	CONSTRAINT fk_versions FOREIGN KEY (conversion_id) REFERENCES conversions(id)
);



CREATE TABLE IF NOT EXISTS  purchases (
	"date" timestamp NOT NULL,
	versions_id int8 NOT NULL,
	cryptocurrency varchar NOT NULL,
	model varchar(50) NOT NULL,
	conversion_id int8 NOT NULL,
	full_name varchar(100) NOT NULL,
	purchase_id varchar(16) NOT NULL,
	id int8 NOT NULL,
	CONSTRAINT purchases_pkey PRIMARY KEY (id),
	CONSTRAINT fk_purchases_conversion FOREIGN KEY (conversion_id) REFERENCES conversions(id),
	CONSTRAINT fk_purchases_versions FOREIGN KEY (versions_id) REFERENCES versions(id)
);


CREATE SEQUENCE IF NOT EXISTS seq_versions
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


CREATE SEQUENCE IF NOT EXISTS seq_conversion
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


CREATE SEQUENCE IF NOT EXISTS seq_purchases
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;