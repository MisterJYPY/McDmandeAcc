-- SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,
-- entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,
-- fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,
-- demandeachat.id as idDemande,demandeachat.bordereaux
-- FROM `entree_produit`
-- INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur
-- INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat
-- INNER JOIN magasin ON magasin.id=entree_produit.magasin
-- INNER JOIN articles ON articles.id=entree_produit.article
-- WHERE entree_produit.date=CURRENT_DATE()

-- SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.numeroVehicule,entree_produit.derniere_modif,entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,
-- entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,
-- fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,
-- demandeachat.id as idDemande,demandeachat.bordereaux
-- FROM `entree_produit`
-- INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur
-- INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat
-- INNER JOIN magasin ON magasin.id=entree_produit.magasin
-- INNER JOIN articles ON articles.id=entree_produit.article WHERE entree_produit.date=CURRENT_DATE() AND entree_produit.article=1 AND stocks_avant=138  AND entree_produit.stocks_apres=162 AND entree_produit.demandeAchat=125 AND entree_produit.fournisseur=14 AND entree_produit.numeroVehicule='FKFKHFHHF' ORDER BY derniere_modif DESC



------------requete permettant de Trouver les RDI en fonctions des Zones et Secteur-----------------
SELECT personnel.nom, personnel.prenom, secteur_zone.secteur, zone.designation,secteur_zone.id as idsz
FROM zone_secteur_rdi
INNER JOIN personnel ON personnel.id = zone_secteur_rdi.personnel
INNER JOIN secteur_zone ON secteur_zone.id = zone_secteur_rdi.secteur_zone
INNER JOIN zone ON zone.id = secteur_zone.zone
--------  FIN DE LA REQUETE permettant de Trouver les RDI en fonctions des Zones et Secteur----------- 
-------- REQUETE PERMETTANT DE DONNER LE RESPONSABLE DE ZONE ET DU SECTEUR en fonction du secteur et de la ZONE 
SELECT personnel.nom,personnel.prenom,zone_secteur_rdi.id,secteur_zone.id as idSecteur_zone FROM `zone_secteur_rdi`
INNER JOIN personnel ON zone_secteur_rdi.personnel=personnel.id
INNER JOIN secteur_zone ON secteur_zone.id=zone_secteur_rdi.secteur_zone
WHERE  zone_secteur_rdi.secteur_zone=(SELECT id FROM secteur_zone WHERE secteur='secteur 1' AND zone=(SELECT id FROM zone WHERE designation='zone 4') )

----------------------------------fin de la requete--------------------------------------