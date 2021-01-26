--------------------------------------------------------
--  Fichier créé - dimanche-mai-31-2020   
--------------------------------------------------------

CREATE TABLE Moyen_De_Decouverte (
    ID_Moyen INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Titre VARCHAR(15) NOT NULL
    );
    
CREATE TABLE Patient (
    ID_Patient INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Nom VARCHAR(25) NOT NULL,
    Prenom VARCHAR(25) NOT NULL,
    Email VARCHAR(50),
    Numero_De_Telephone INT,
    Mot_De_Passe VARCHAR(30),
    Date_De_Naissance DATE NOT NULL,
    ID_Moyen INT NOT NULL,
    Adresse VARCHAR(50) NOT NULL,
    Ville VARCHAR(25) NOT NULL,
    CodePostal INT NOT NULL,
        CONSTRAINT FK_Moyen1
            FOREIGN KEY(ID_Moyen)
                REFERENCES Moyen_De_Decouverte(ID_Moyen)
    );

CREATE TABLE Genre (
    ID_Genre INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Titre VARCHAR(10)
    );
    
CREATE TABLE Classification_Patient (
    ID_Genre INT NOT NULL,
    ID_Patient1 INT NOT NULL,
    ID_Patient2 INT,
    Date_Debut DATE NOT NULL,
    Date_Fin DATE,
        PRIMARY KEY(ID_Genre, ID_Patient1),
            CONSTRAINT FK_Genre1
                FOREIGN KEY(ID_Genre)
                    REFERENCES Genre(ID_Genre),
            CONSTRAINT FK_Patient1
                FOREIGN KEY(ID_Patient1)
                    REFERENCES Patient(ID_Patient),
            CONSTRAINT FK_Patient2
                FOREIGN KEY(ID_Patient2)
                    REFERENCES Patient(ID_Patient)
    );
    
CREATE TABLE Profession (
    ID_Profession INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Titre VARCHAR(30) NOT NULL
    );
    
CREATE TABLE Profession_Patient (
    ID_Patient INT NOT NULL,
    ID_Profession INT NOT NULL,
    Date_Debut DATE NOT NULL,
    Date_Fin DATE,
        PRIMARY KEY(ID_Patient, ID_Profession),
            CONSTRAINT FK_Patient3
                FOREIGN KEY(ID_Patient)
                    REFERENCES Patient(ID_Patient),
            CONSTRAINT FK_Profession1 
                FOREIGN KEY(ID_Profession)
                    REFERENCES Profession(ID_Profession)
    );
    
CREATE TABLE Consultation (
    ID_Consultation INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Date_Debut_RDV DATE NOT NULL,
    Fin_RDV DATE NOT NULL
    );
    
CREATE TABLE Type_Consultation (
    ID_Type INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Titre VARCHAR(30)
    );
    
CREATE TABLE Consultation_Patient (
    ID_Patient1 INT NOT NULL,
    ID_Patient2 INT,
    ID_Patient3 INT,
    ID_Type INT NOT NULL,
    ID_Consultation INT NOT NULL,
    Comportements VARCHAR(200),
    Gestes VARCHAR(255),
    Postures VARCHAR(255),
    Note_Anxiete DECIMAL,
        PRIMARY KEY(ID_Patient1, ID_Type, ID_Consultation),
            CONSTRAINT FK_Patient4
                FOREIGN KEY(ID_Patient1)
                    REFERENCES Patient(ID_Patient),
	    CONSTRAINT FK_Patient5
                FOREIGN KEY(ID_Patient2)
                    REFERENCES Patient(ID_Patient),
            CONSTRAINT FK_Patient6
                FOREIGN KEY(ID_Patient3)
                    REFERENCES Patient(ID_Patient),
            CONSTRAINT FK_Type1
                FOREIGN KEY(ID_Type)
                    REFERENCES Type_Consultation(ID_Type),
            CONSTRAINT FK_Consultation1
                FOREIGN KEY(ID_Consultation)
                    REFERENCES Consultation(ID_Consultation)
    );
    
CREATE TABLE Facture (
    Code_Facture INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    Prix_En_Euros DECIMAL NOT NULL
    );
    
CREATE TABLE Paiement (
    ID_Patient INT NOT NULL,
    ID_Consultation INT NOT NULL,
    ID_Facture INT NOT NULL,
    MoyenPaiement VARCHAR(25),
    StatutPaye INT,
        PRIMARY KEY(ID_Patient, ID_Facture, ID_Consultation),
            CONSTRAINT FK_Patient7
                FOREIGN KEY(ID_Patient)
                    REFERENCES Patient(ID_Patient),
            CONSTRAINT FK_Facture1
                FOREIGN KEY(ID_Facture)
                    REFERENCES Facture(Code_Facture),
            CONSTRAINT FK_Consultation3
                FOREIGN KEY(ID_Consultation)
                    REFERENCES Consultation(ID_Consultation)
    );
    
    
--------------------------------------------------------
--  DDL for View AFFICHENONPAYE
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "AFFICHENONPAYE" ("NOM", "PRENOM", "DATE_DEBUT_RDV", "PRIX_EN_EUROS", "STATUTPAYE", "ID_CONSULTATION", "CODE_FACTURE") AS 
  SELECT Pat.Nom, Pat.Prenom, Cons.Date_Debut_rdv, Fact.Prix_En_Euros, Paie.StatutPaye,Cons.ID_Consultation,Fact.CODE_FACTURE 
	FROM Patient  Pat JOIN Paiement Paie ON Pat.ID_Patient = Paie.ID_Patient
	JOIN Consultation Cons ON Paie.ID_Consultation = Cons.ID_Consultation 
	JOIN Paiement Paie ON Cons.ID_Consultation = Paie.ID_Consultation
	JOIN Facture Fact ON Paie.ID_Facture = Fact.CODE_FACTURE
	WHERE Paie.StatutPaye = 0 OR Paie.StatutPaye = NULL
;
--------------------------------------------------------
--  DDL for View AFFICHERDVPATIENT
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "AFFICHERDVPATIENT" ("PRENOM", "NOM", "COMPORTEMENTS", "GESTES", "POSTURES", "NOTE_ANXIETE", "DATE_DEBUT_RDV", "FIN_RDV", "TITRE") AS 
  SELECT P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
    WHERE P.ID_Patient =  (SELECT x FROM tempo1 GROUP BY x)
;
--------------------------------------------------------
--  DDL for View DIPLSAYRDVPATIENT
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "DIPLSAYRDVPATIENT" ("PRENOM", "NOM", "COMPORTEMENTS", "GESTES", "POSTURES", "NOTE_ANXIETE", "DATE_DEBUT_RDV", "FIN_RDV", "TITRE") AS 
  SELECT P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
;
--------------------------------------------------------
--  DDL for View DISPLAYAPPOINTMENTSPATIENT
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "DISPLAYAPPOINTMENTSPATIENT" ("PRENOM", "NOM", "COMPORTEMENTS", "GESTES", "POSTURES", "NOTE_ANXIETE", "DATE_DEBUT_RDV", "FIN_RDV", "TITRE", "ID_PATIENT", "ID_CONSULTATION") AS 
  SELECT P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre,P.ID_Patient,RDVP.ID_Consultation
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
;
--------------------------------------------------------
--  DDL for View DISPLAYAPPOINTMENTSWEEK
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "DISPLAYAPPOINTMENTSWEEK" ("PRENOM", "NOM", "DATE_DEBUT_RDV", "FIN_RDV", "TITRE", "ID_CONSULTATION") AS 
  SELECT P.Prenom, P.Nom,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre,Consul.ID_Consultation
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
;
--------------------------------------------------------
--  DDL for View DISPLAYRDVPATIENT
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "DISPLAYRDVPATIENT" ("PRENOM", "NOM", "COMPORTEMENTS", "GESTES", "POSTURES", "NOTE_ANXIETE", "DATE_DEBUT_RDV", "FIN_RDV", "TITRE", "ID_PATIENT") AS 
  SELECT P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre,P.ID_Patient
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
;
--------------------------------------------------------
--  DDL for View INFO_PATIENT
--------------------------------------------------------

  CREATE OR REPLACE FORCE NONEDITIONABLE VIEW "INFO_PATIENT" ("ID_PATIENT", "NOM", "PRENOM") AS 
  SELECT ID_PATIENT,NOM,PRENOM FROM PATIENT
;


--------------------------------------------------------
--  DDL for View V_EMP10
--------------------------------------------------------

SET DEFINE OFF;
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('1','12');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('27','12');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('41','15');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('61','12');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('81','25');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('82','25');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('83','15');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('84','25');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('87','25');
Insert into FACTURE (CODE_FACTURE,PRIX_EN_EUROS) values ('101','25');
REM INSERTING into GENRE
SET DEFINE OFF;
Insert into GENRE (ID_GENRE,TITRE) values ('2','couple');
Insert into GENRE (ID_GENRE,TITRE) values ('21','Man');
Insert into GENRE (ID_GENRE,TITRE) values ('41','Couple');
REM INSERTING into HELP

SET DEFINE OFF;
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('1','le dendiste');
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('13','le dendiste');
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('18','by internet');
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('41','don''t know');
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('81','Amies');
Insert into MOYEN_DE_DECOUVERTE (ID_MOYEN,TITRE) values ('101','internet');

Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('21','24','1','1',null);
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('21','14','27','CB','1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','101','82',null,'1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('41','82','61',null,'1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','101','81',null,'1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','101','83',null,'1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','101','84',null,'1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','101','87','espece','1');
Insert into PAIEMENT (ID_PATIENT,ID_CONSULTATION,ID_FACTURE,MOYENPAIEMENT,STATUTPAYE) values ('81','122','101','espece','1');
REM INSERTING into PATIENT
SET DEFINE OFF;

Insert into PATIENT (ID_PATIENT,NOM,PRENOM,EMAIL,NUMERO_DE_TELEPHONE,MOT_DE_PASSE,DATE_DE_NAISSANCE,ID_MOYEN,ADRESSE,VILLE,CODEPOSTAL) values ('12','Boulnois','Antonin','antoni@email','780311858','7nmXbJOA1eM3/BwJXs6MjyL5HzBs7rFh+lH+zt4sS6E=',to_date('19/02/99','DD/MM/RR'),'18','7 rue du commee','gournay','76220');
Insert into PATIENT (ID_PATIENT,NOM,PRENOM,EMAIL,NUMERO_DE_TELEPHONE,MOT_DE_PASSE,DATE_DE_NAISSANCE,ID_MOYEN,ADRESSE,VILLE,CODEPOSTAL) values ('41','test1','test','test@email.fr','1212121212','8tgaJg3qihAN1ReYTlPFanUj2WlCqDS5zcJJvU6Meqk=',to_date('12/02/99','DD/MM/RR'),'41','7 Boulevard maxicme Gorki','Gournay','94800');
Insert into PATIENT (ID_PATIENT,NOM,PRENOM,EMAIL,NUMERO_DE_TELEPHONE,MOT_DE_PASSE,DATE_DE_NAISSANCE,ID_MOYEN,ADRESSE,VILLE,CODEPOSTAL) values ('81','Dupont','Jean','jean.dupont@gmail.com','612342334','T/F7yO5fJAx5K4pBv6LFivcm2DuSXPaWrwyBFidxTIU=',to_date('12/02/96','DD/MM/RR'),'81','7 rue de la garonne','Paris','95000');
Insert into PATIENT (ID_PATIENT,NOM,PRENOM,EMAIL,NUMERO_DE_TELEPHONE,MOT_DE_PASSE,DATE_DE_NAISSANCE,ID_MOYEN,ADRESSE,VILLE,CODEPOSTAL) values ('101','Louvier','Amelie','amelie.louvier@yahoo.fr','634352345','qZFnZfKkuXjzDBnpxb0NcM0BXOBULEWHtODE/umPU2Y=',to_date('12/04/00','DD/MM/RR'),'101','4 rue des champs','gournay','76220');
REM INSERTING into PROFESSION
SET DEFINE OFF;
Insert into PROFESSION (ID_PROFESSION,TITRE) values ('1','truc');
Insert into PROFESSION (ID_PROFESSION,TITRE) values ('21','firefigther');
Insert into PROFESSION (ID_PROFESSION,TITRE) values ('61','Cuisinier');
Insert into PROFESSION (ID_PROFESSION,TITRE) values ('81','infirmière');
REM INSERTING into PROFESSION_PATIENT
SET DEFINE OFF;
Insert into PROFESSION_PATIENT (ID_PATIENT,ID_PROFESSION,DATE_DEBUT,DATE_FIN) values ('21','1',to_date('12/12/22','DD/MM/RR'),null);
Insert into PROFESSION_PATIENT (ID_PATIENT,ID_PROFESSION,DATE_DEBUT,DATE_FIN) values ('41','21',to_date('12/12/22','DD/MM/RR'),null);
Insert into PROFESSION_PATIENT (ID_PATIENT,ID_PROFESSION,DATE_DEBUT,DATE_FIN) values ('61','41',to_date('12/12/22','DD/MM/RR'),null);
Insert into PROFESSION_PATIENT (ID_PATIENT,ID_PROFESSION,DATE_DEBUT,DATE_FIN) values ('81','61',to_date('12/12/22','DD/MM/RR'),null);
Insert into PROFESSION_PATIENT (ID_PATIENT,ID_PROFESSION,DATE_DEBUT,DATE_FIN) values ('101','81',to_date('12/12/22','DD/MM/RR'),null);


REM INSERTING into TYPE_CONSULTATION
SET DEFINE OFF;
Insert into TYPE_CONSULTATION (ID_TYPE,TITRE) values ('1','work');
Insert into TYPE_CONSULTATION (ID_TYPE,TITRE) values ('2','work');
--------------------------------------------------------
--  DDL for Procedure ADDPATIENTTOAPP
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "ADDPATIENTTOAPP" (ID_Cons IN INT , ID_Pat IN INT)
AS
counta number;
countb number;
countc number;
countd number;
counte number;
BEGIN
    SELECT COUNT(*) into counta FROM Consultation_Patient WHERE ID_Consultation = ID_Cons AND ID_Patient3 IS NULL;
	IF counta <> 0
	THEN 
        SELECT COUNT(*) into countb FROM Consultation_Patient WHERE ID_Consultation = ID_Cons  AND ID_Patient2 IS NULL;
		IF countb <> 0
		THEN 
            SELECT COUNT(*) into countc FROM Consultation_Patient WHERE ID_Consultation = ID_Cons AND ID_Patient1 IS NOT NULL;
			IF countc <> 0
			THEN
                SELECT COUNT(*) into countd FROM Consultation_Patient WHERE ID_Consultation  = ID_Cons AND ID_Patient1 = ID_Pat;
				IF countd = 0
				THEN UPDATE Consultation_Patient
				SET ID_Patient2 = ID_Pat WHERE ID_Cons = ID_Consultation;
				END IF;
			END IF;			
		ELSE 
            SELECT COUNT(*) into counte FROM Consultation_Patient WHERE ID_Consultation = ID_Cons AND (ID_Patient2 = ID_Pat OR ID_Patient3 = ID_Pat);
			IF counte <> 0
			THEN UPDATE Consultation_Patient
			SET ID_Patient3 = ID_Pat WHERE ID_Cons = ID_Consultation;
			END IF;	
		END IF;
	END IF;
END;

/
--------------------------------------------------------
--  DDL for Procedure AFFICHAGERDVPATIENT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "AFFICHAGERDVPATIENT" (IDP INT)
AS
BEGIN
    SELECT INTO P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
    WHERE P.ID_Patient =  IDP;
END;

/
--------------------------------------------------------
--  DDL for Procedure AJOUTRDV
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "AJOUTRDV" (Nom1 VARCHAR, Prenom1 VARCHAR, Nom2 VARCHAR, Prenom2 VARCHAR, Nom3 VARCHAR, Prenom3 VARCHAR,TypeRDV INT, DateConsultation DATE) 
AS 
idP1 INT;
idP2 INT DEFAULT NULL;
idP3 INT DEFAULT NULL;
idRDV INT;
daya VARCHAR(30);
houra VARCHAR(30);
counter number;
BEGIN 

    SELECT TO_CHAR(SYSDATE, 'DAY') into daya FROM DUAL;
    SELECT TO_CHAR(SYSDATE, 'HH24') into houra FROM DUAL;
    SELECT COUNT(*) into counter FROM Consultation WHERE TO_CHAR(DateConsultation, 'DD') = TO_CHAR(DATE_DEBUT_RDV, 'DD');
    
    if counter < 20 OR daya != 'DIMANCHE' OR houra between 8 and 20
    then
        INSERT INTO Consultation(Date_Debut_RDV, Fin_RDV) 
            VALUES(DateConsultation, DateConsultation + INTERVAL '30' MINUTE)  returning ID_Consultation into idRDV;
        SELECT ID_Consultation INTO idRDV FROM Consultation WHERE Date_Debut_RDV = DateConsultation;
        SELECT ID_Patient INTO idP1 FROM Patient WHERE Nom = Nom1 AND Prenom = Prenom1;
        IF Prenom2 IS NOT NULL AND Nom2 IS NOT NULL THEN
            SELECT ID_Patient INTO idP2 FROM Patient WHERE Nom = Nom2 AND Prenom = Prenom2;
        END IF;
        IF Prenom3 IS NOT NULL AND Nom3 IS NOT NULL THEN
            SELECT ID_Patient INTO idP3 FROM Patient WHERE Nom = Nom3 AND Prenom = Prenom3;
        END IF;
        INSERT INTO Consultation_Patient(ID_Patient1,ID_Patient2,ID_Patient3,ID_Type,ID_Consultation) VALUES (idP1, idP2, idP3, TypeRDV, idRDV);
    END IF;
    END;

/
--------------------------------------------------------
--  DDL for Procedure CREATEUSER1
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "CREATEUSER1" ( firstName IN string,familyName IN string,email IN string,passwd IN string,classification IN string,wayOfConnection IN string,telephoneNb IN int,city IN string,adress IN string,cityCode IN string,joba IN string,dateOfBirth IN Date)
AS
    idm int; 
   genre_id int;
   patient_id int;
   profession_id int;

BEGIN 
   INSERT INTO Moyen_de_decouverte (Titre) VALUES(wayOfConnection) returning ID_Moyen into idm; 
   INSERT INTO Patient (Nom,Prenom,Email,Numero_De_Telephone,Mot_De_Passe,Date_De_Naissance,ID_Moyen,Adresse,Ville,CodePostal) VALUES (familyName,firstName,email,telephoneNb,passwd,dateOfBirth,idm,adress,city,citycode)returning ID_Patient into patient_id;

   INSERT INTO Genre (Titre) VALUES(classification) returning ID_Genre into genre_id; 
   INSERT INTO Classification_Patient (ID_GENRE,ID_Patient1,Date_Debut) VALUES (genre_id,patient_id,'12/12/1222');

   INSERT INTO Profession(Titre) VALUES(joba) returning ID_Profession into profession_id;
   INSERT INTO Profession_Patient(ID_Patient,ID_Profession,Date_Debut) VALUES (patient_id,profession_id,'12/12/1222');

END; 

/
--------------------------------------------------------
--  DDL for Procedure CREATIONDEFACTURE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "CREATIONDEFACTURE" (PrixConsul INT, IDP INT, IDConsul INT, StatutPaiement IN INT, Moyen IN VARCHAR)
AS 
idFacture INT;
BEGIN
    INSERT INTO Facture(Prix_En_Euros) VALUES (PrixConsul);
    SELECT MAX(Code_Facture) INTO idFacture 
        FROM Facture;
            INSERT INTO Paiement(ID_Patient, ID_Consultation, ID_Facture, MoyenPaiement, StatutPaye)
            VALUES(IDP, IDConsul, idFacture, Moyen, StatutPaiement);
END;




/
--------------------------------------------------------
--  DDL for Procedure MAJPAIEMENT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "MAJPAIEMENT" (IDP INT, IDConsul INT, IDFacture INT, StatutPaiement INT, Moyen IN VARCHAR)
AS 
BEGIN 
    UPDATE Paiement SET
        MoyenPaiement = Moyen, StatutPaye = StatutPaiement 
            WHERE ID_Patient = IDP AND ID_Consultation = IDConsul AND ID_Facture = IDFacture;
END;

/
--------------------------------------------------------
--  DDL for Procedure SUPPPATRDV
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "SUPPPATRDV" (IDRDV INT, IDPatient INT)
AS 
counta number;
countb number;
BEGIN 
	UPDATE Consultation_Patient 
	SET ID_Patient3 = NULL WHERE IDRDV = ID_Consultation AND ID_Patient3 = IDPatient;

	UPDATE Consultation_Patient 
	SET ID_Patient2 = NULL WHERE IDRDV = ID_Consultation AND ID_Patient2 = IDPatient AND  ID_Patient3 IS NULL;

	UPDATE Consultation_Patient 
	SET ID_Patient2 = ID_Patient3, ID_Patient3 = NULL WHERE IDRDV = ID_Consultation AND ID_Patient2 = IDPatient AND  ID_Patient3 	IS NOT NULL;

	DELETE FROM Consultation_Patient WHERE EXISTS ( SELECT * FROM Consultation_Patient WHERE ID_Patient1 = IDPatient AND ID_Consultation = IDRDV AND ID_Patient2 = NULL AND ID_Patient3 = NULL);

	SELECT COUNT(*) into counta FROM Consultation_Patient WHERE ID_Patient1 = IDPatient AND ID_Consultation = IDRDV AND ID_Patient2 IS NOT NULL;

    IF counta <> 0
		THEN 
        SELECT COUNT(*) into countb FROM Consultation_Patient WHERE ID_Patient1 = IDPatient AND ID_Consultation = IDRDV AND ID_Patient3 IS NOT NULL;
		IF  countb <> 0
			THEN 
			UPDATE Consultation_Patient SET ID_Patient1 = ID_Patient2, ID_Patient2 = ID_Patient3, ID_Patient3 = NULL
			WHERE ID_Patient1 = IDPatient;
			ELSE 

				UPDATE Consultation_Patient SET ID_Patient1 = ID_Patient2
                WHERE ID_Patient1 = IDPatient AND IDRDV = ID_Consultation;
                UPDATE Consultation_Patient SET ID_Patient2 = NULL
                WHERE ID_Patient1 = ID_Patient2  AND IDRDV = ID_Consultation;
			END IF;
	END IF;
END;


/
--------------------------------------------------------
--  DDL for Procedure SUPPRDV
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "SUPPRDV" (IDRendezVous INT)
AS 
BEGIN
    DELETE FROM Consultation_Patient WHERE ID_Consultation = IDRendezVous;
    DELETE FROM Paiement WHERE ID_Consultation = IDRendezVous;
    DELETE FROM Consultation WHERE ID_Consultation = IDRendezVous;


END;

/
--------------------------------------------------------
--  DDL for Procedure UPDATECONSULT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "UPDATECONSULT" (Comp IN VARCHAR, Gest IN VARCHAR, Post IN VARCHAR, Note_Anx DECIMAL, IDRDV INT)
AS 
BEGIN 
	IF Note_Anx = 0 THEN 
		UPDATE Consultation_Patient 
			SET Comportements = Comp, Gestes = Gest, Postures = Post WHERE IDRDV = ID_Consultation;
	ELSE 
		UPDATE Consultation_Patient 
			SET Comportements = Comp, Gestes = Gest, Postures = Post, Note_Anxiete = Note_Anx WHERE IDRDV = ID_Consultation;
	END IF;
END;

/
--------------------------------------------------------
--  DDL for Procedure UPDATEDATE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "UPDATEDATE" (ID_Con INT, NewDate DATE)
AS
BEGIN 
	UPDATE Consultation 
	SET  Date_Debut_RDV = NewDate, Fin_RDV = NewDate + INTERVAL '30' MINUTE
	WHERE ID_Consultation = ID_Con;
END;

/
--------------------------------------------------------
--  DDL for Function AFFICHAGERDVP
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "AFFICHAGERDVP" (ID_Patient INT)
RETURN TABLE
AS
table1 Table;
BEGIN
    SELECT P.Prenom, P.Nom,
    RDVP.Comportements, RDVP.Gestes, RDVP.Postures, RDVP.Note_Anxiete,
    Consul.Date_Debut_RDV, Consul.Fin_RDV,
    T.Titre
    into table1
    FROM Patient P
    JOIN Consultation_Patient RDVP ON P.ID_Patient = RDVP.ID_Patient1 OR P.ID_Patient = RDVP.ID_Patient2 OR P.ID_Patient = RDVP.ID_Patient3
    JOIN Consultation Consul ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Consultation_Patient RDVP ON RDVP.ID_Consultation = Consul.ID_Consultation
    JOIN Type_Consultation T ON RDVP.ID_Type = T.ID_Type
    WHERE P.ID_Patient =  id_patient;

    RETURN table1;

END;

/
--------------------------------------------------------
--  DDL for Function AUTHENTIFICATION
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "AUTHENTIFICATION" (EmailEntre IN VARCHAR,MDPEntre IN VARCHAR) 
    
RETURN INT AS
Monresultat INT := 2;
BEGIN


if (EmailEntre = 'admin' AND MDPEntre = 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=') THEN
    RETURN 0;
else
SELECT 
   ID_Patient INTO Monresultat FROM Patient WHERE EMAIL = EmailEntre AND Mot_De_Passe = MDPEntre;

RETURN Monresultat;
	
	EXCEPTION
   WHEN NO_DATA_FOUND THEN
    Monresultat:=-1;

END IF;


RETURN Monresultat;
END authentification;

/
--------------------------------------------------------
--  DDL for Function CREATEUSER
--------------------------------------------------------

  CREATE OR REPLACE NONEDITIONABLE FUNCTION "CREATEUSER" 
RETURN INT
AS
    idm int; 
   genre_id int;
   patient_id int;
BEGIN 
   INSERT INTO Moyen_de_decouverte (Titre) VALUES('le dendiste') returning ID_Moyen into idm; 
   INSERT INTO Patient (Nom,Prenom,Email,Numero_De_Telephone,Mot_De_Passe,Date_De_Naissance,ID_Moyen,Adresse,Ville,CodePostal) VALUES ('boulnois','antonin','email',078880808,'password','19/02/1999',idm,'adress','ville',76220)returning ID_Patient into patient_id;

   INSERT INTO Genre (Titre) VALUES('couple') returning ID_Genre into genre_id; 
   INSERT INTO Classification_Patient (ID_GENRE,ID_Patient1,Date_Debut) VALUES (genre_id,patient_id,'12/12/1222');

   return idm;
END; 

/
