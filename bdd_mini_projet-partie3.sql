CREATE TABLE Catalogue
(idCatalogue NUMBER, nomCatalogue VARCHAR(100),
CONSTRAINT pk_Catalogue PRIMARY KEY (idCatalogue),
CONSTRAINT un_nomCatalogue_Catalogue UNIQUE (nomCatalogue),
CONSTRAINT nn_nomCatalogue_Catalogue CHECK (nomCatalogue IS NOT NULL) );

CREATE TABLE Produits
(idProduit NUMBER, nomProduit VARCHAR(100), quantiteStock NUMBER(*,0), prixUnitaireHT NUMBER(*,2), idCatalogue NUMBER,
CONSTRAINT pk_Produit PRIMARY KEY (idProduit),
CONSTRAINT un_nomProduit_Produit UNIQUE (nomProduit, idCatalogue),
CONSTRAINT nn_nomProduit_Produit CHECK (nomProduit IS NOT NULL),
CONSTRAINT nn_quantiteStock_Produit CHECK (quantiteStock IS NOT NULL),
CONSTRAINT nn_prix_Produit CHECK (prixUnitaireHT IS NOT NULL),
CONSTRAINT nn_catalogue_Produit CHECK (idCatalogue IS NOT NULL),
CONSTRAINT fk_catalogue_produit FOREIGN KEY (idCatalogue) REFERENCES Catalogue(idCatalogue) ON UPDATE CASCADE ON DELETE CASCADE );


CREATE SEQUENCE seqCatalogue;
CREATE SEQUENCE seqProduits;


CREATE OR REPLACE PROCEDURE nouveauCatalogue (p_nomCatalogue IN Catalogue.nomCatalogue%TYPE) IS
BEGIN
	INSERT INTO Catalogue (idCatalogue, nomCatalogue)
	VALUES (seqCatalogue.NEXTVAL, p_nomCatalogue);
END;



CREATE OR REPLACE PROCEDURE nouveauProduit (p_nomProduit IN Produits.nomProduit%TYPE,
											p_quantite IN Produits.quantiteStock%TYPE,
											p_prix IN Produits.prixUnitaireHT%TYPE,
											p_nomCatalogue IN Catalogue.nomCatalogue%TYPE) IS

	v_idCatalogue Catalogue.idCatalogue%TYPE;
BEGIN
	SELECT idCatalogue INTO v_idCatalogue
	FROM Catalogue
	WHERE nomCatalogue = p_nomCatalogue;
	
	INSERT INTO Produits (idProduit, nomProduit, quantiteStock, prixUnitaireHT, idCatalogue)
	VALUES (seqProduits.NEXTVAL, p_nomProduit, p_quantite, p_prix, v_idCatalogue);
END;
  /
	SHOW ERRORS