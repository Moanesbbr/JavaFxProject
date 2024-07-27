CREATE TABLE Produits
(idProduit NUMBER, nomProduit VARCHAR(100), quantiteStock NUMBER(*,0), prixUnitaireHT NUMBER(*,2),
CONSTRAINT pk_Produit PRIMARY KEY (idProduit),
CONSTRAINT un_nomProduit_Produit UNIQUE (nomProduit),
CONSTRAINT nn_nomProduit_Produit CHECK (nomProduit IS NOT NULL),
CONSTRAINT nn_quantiteStock_Produit CHECK (quantiteStock IS NOT NULL),
CONSTRAINT nn_prix_Produit CHECK (prixUnitaireHT IS NOT NULL) );


CREATE SEQUENCE seqProduits;

CREATE OR REPLACE PROCEDURE nouveauProduit (p_nomProduit IN Produits.nomProduit%TYPE,
											p_quantite IN Produits.quantiteStock%TYPE,
											p_prix IN Produits.prixUnitaireHT%TYPE) IS
BEGIN
	INSERT INTO Produits (idProduit, nomProduit, quantiteStock, prixUnitaireHT)
	VALUES (seqProduits.NEXTVAL, p_nomProduit, p_quantite, p_prix);
END;