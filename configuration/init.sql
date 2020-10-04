DROP SCHEMA IF EXISTS projet CASCADE;
CREATE SCHEMA projet;

CREATE TABLE projet.clients
(
    id_client   serial PRIMARY KEY,
    nom         varchar(50) NOT NULL,
    prenom      varchar(50) NOT NULL,
    code_postal varchar(10) NOT NULL,
    ville       varchar(50) NOT NULL,
    email       varchar(50) NOT NULL,
    tel         varchar(50) NULL,
    rue         varchar(50) NOT NULL,
    numero      varchar(10) NOT NULL,
    boite       varchar(50) NOT NULL

);

CREATE TABLE projet.inscrits
(
    id_inscrit       serial PRIMARY KEY,
    nom              varchar(50)           NOT NULL,
    prenom           varchar(50)           NOT NULL,
    pseudo           varchar(50)           NOT NULL,
    email            varchar(50)           NOT NULL,
    ville            varchar(50)           NOT NULL,
    date_inscription date                  NOT NULL,
    id_client        integer               NULL,
    estOuvrier       boolean DEFAULT FALSE NOT NULL,
    estValide        boolean DEFAULT FALSE NOT NULL,
    mdp              char(60)              NOT NULL,

    FOREIGN KEY (id_client)
        REFERENCES projet.clients (id_client)
);

CREATE TABLE projet.devis
(
    id_devis      serial      NOT NULL,
    date_devis    date        NOT NULL,
    montant_total real        NOT NULL,
    duree_travaux integer     NOT NULL,
    id_client     integer     NOT NULL,
    etat          varchar(50) NOT NULL,
    date_debut    date        NULL,
    id_photo_fav  integer     NULL,

    PRIMARY KEY (id_devis),
    FOREIGN KEY (id_client)
        REFERENCES projet.clients (id_client)
);

CREATE TABLE projet.types_amenagements
(
    id_type_amenagement serial PRIMARY KEY,
    titre               varchar(50)   NOT NULL,
    description         varchar(1000) NULL
);


CREATE TABLE projet.photos
(
    id_devis            integer NOT NULL,
    id_photo            serial  NOT NULL,
    id_type_amenagement integer NULL,
    visible             boolean NOT NULL,
    photo               varchar NOT NULL,

    PRIMARY KEY (id_devis, id_photo),
    FOREIGN KEY (id_devis)
        REFERENCES projet.devis (id_devis),
    FOREIGN KEY (id_type_amenagement)
        REFERENCES projet.types_amenagements (id_type_amenagement)
);

CREATE TABLE projet.devis_type_amenagements
(
    id_devis            integer NOT NULL,
    id_type_amenagement integer NOT NULL,
    PRIMARY KEY (id_devis, id_type_amenagement),

    FOREIGN KEY (id_devis)
        REFERENCES projet.devis (id_devis),
    FOREIGN KEY (id_type_amenagement)
        REFERENCES projet.types_amenagements (id_type_amenagement)
);

ALTER TABLE projet.devis
    ADD FOREIGN KEY (id_devis, id_photo_fav) REFERENCES projet.photos (id_devis, id_photo);
