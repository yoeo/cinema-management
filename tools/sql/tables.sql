/*
Le choix des tables.


Toute les tables ont un champ identifiant qui permet d'effectuer les jointures.
Nous avons 6 tables : Films, Salles, Seances, TypeCartes, CarteAbonnes, Reservation.

Chaque Film à un:
- Titre
- Duree
- Résumé
- Date de sortie
- Liste d'acteurs
- Popularité, la popularité est une valeur entre 0 et 1. Elle est entrée manuellement.
- Nom d'image, l'image elle même est enrégistré dans le dossier "./tools/images".
Les dates d'affichages sont trouvées dynamiquement en recherchant les date des séssions associées au film.

Chaque Salle à un:
- Nom
- Nombre de places
- Indicateur pour savoir si elle possede un équipement 3D ou pas.

Chaque Seance à un:
- Film qui sera projetté
- Salle oû elle sera projettée
- Date de projection
- Heure de projection
- Indicateur pour savoir si la projection est en 3D ou pas.
Il faut noter qu'une salle 2D ne peut pas projetter un film en 3D
Et une salle 3D peut bien projetter un film en 2D d'oû la redondance de l'indicateur.

Chaque Type de carte à un:
- Nom
- Tarif
Le plein tarif est aussi considéré comme un type de carte.

Chaque possessuer de Carte d'abonné à un:
- Nom
- Prénom
- Age
- Email

Chaque Réservation à un:
- Une séance associé
- Une carte d'abonnée si la réservation est effectuée par un abonné
- Un type de carte
Dans notre application nous avons décidé de ne pas enrégistrer les données personnelles des utilisateur
pour garantir le respect de leur vie privée.
C'est pour cela que nous n'avons pas créé de table Utilisateur.
Les vérifications d'identité et de validité des cartes autres que la carte d'abonnée seront effectuées dirrectement
à l'entrée de la salle de ciné.


*/
DROP TABLE IF EXISTS FILMS, SALLES, SEANCES,
TYPECARTES, CARTEABONNES, RESERVATIONS;

CREATE TABLE FILMS (
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
TITRE VARCHAR (255) NOT NULL,
DUREE TIME /*NOT NULL*/,
RESUME VARCHAR (10240),
DATESORTIE DATE,
ACTEUR VARCHAR (1024),
POPULARITE REAL,
IMAGE VARCHAR(255),
CONSTRAINT film_key PRIMARY KEY (ID)
);

CREATE TABLE SALLES(
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
NOM VARCHAR(255) NOT NULL,
NBPLACE INTEGER NOT NULL,
ESTTROISD BOOLEAN DEFAULT FALSE,
CONSTRAINT salle_key PRIMARY KEY (ID)
);

CREATE TABLE SEANCES(
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
FILMID INTEGER,
SALLEID INTEGER,
/* Date et heure de projection */
DEBUT DATE NOT NULL,
HEURE TIME NOT NULL,
/* Une salle 3D peut projetter une seance 2D */
ESTTROISD BOOLEAN DEFAULT FALSE,
CONSTRAINT seance_key PRIMARY KEY (ID),
CONSTRAINT seance_filmid FOREIGN KEY (FILMID) REFERENCES FILMS(ID) ON DELETE CASCADE,
CONSTRAINT seance_salleid FOREIGN KEY (SALLEID) REFERENCES SALLES(ID) ON DELETE CASCADE
);

CREATE TABLE TYPECARTES(
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
NOM VARCHAR(255),
TARIF REAL,
CONSTRAINT typecarte_key PRIMARY KEY (ID)
);

CREATE TABLE CARTEABONNES(
/* Seules les cartes d'abonnées */
/* peuvent etre achetées via l'application */
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
DATEACHAT DATE NOT NULL,
NOM VARCHAR(255)NOT NULL,
PRENOM VARCHAR (255) NOT NULL,
AGE INTEGER,
EMAIL VARCHAR(255),
CONSTRAINT carte_key PRIMARY KEY (ID)
);

CREATE TABLE RESERVATIONS(
/* Par respect pour la vie privee, */
/* l'application ne conserve pas de donnees personnelles! */
ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
SEANCEID INTEGER,
CARTEABONNEID INTEGER NULL,
TYPECARTEID INTEGER NULL,
CONSTRAINT reservation_key PRIMARY KEY (ID),
CONSTRAINT reservation_seanceid FOREIGN KEY (SEANCEID) REFERENCES SEANCES(ID) ON DELETE CASCADE,
CONSTRAINT reservation_carteabonneid FOREIGN KEY (CARTEABONNEID) REFERENCES CARTEABONNES(ID) ON DELETE SET NULL,
CONSTRAINT reservation_typecarteid FOREIGN KEY (TYPECARTEID) REFERENCES TYPECARTES(ID) ON DELETE CASCADE,
);
