package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.exception.BizzException;

import java.time.LocalDate;
import java.util.List;

public interface DevisUcc {

  /**
   * Insère un devis dans la base de données
   * 
   * @param devisDto, le devis à insérer
   * @return le devis inséré
   * @throws BizzException si le paramètre est null
   */
  DevisDto insererDevis(DevisDto devisDto);

  /**
   * Insère un tuple de devis_type_amenagements dans la base de données avec les id du devis et du
   * type d'aménagement passés en paramètre
   * 
   * @param id, un tableau d'entiers correspondant aux id d'un devis et d'un type d'aménagement
   * @return true
   * @throws BizzException si la taille du paramètre est égale à 0 ou si son premier élément est
   *         égal à 0
   */
  boolean insererDevisTypesAmenagements(int[] id);

  /**
   * Retourne la liste de tous les devis remplissant toutes les conditions (id du client, date,
   * montant total min < montant total < montant total max, type d'aménagement)
   * 
   * @param idClient, l'id du client
   * @param date, la date des devis à rechercher
   * @param nombre1, le montant total minimal du devis
   * @param nombre2, le montant total maximal du devis
   * @param typeAmen, le type d'aménagement du devis
   * @return la liste de tous les devis remplissant toutes les conditions
   * @throws BizzException si id <= 0, date est null, nombre1 > nombre 2 ou typeAmen < 0
   */
  List<DevisDto> recupererMesDevis(int id, LocalDate date, int nombre1, int nombre2, int typeAmen);

  /**
   * Retourne la liste de tous les devis remplissant toutes les conditions (la variable mot doit se
   * trouver dans le nom, date, montant total min < montant total < montant total max, type
   * d'aménagement)
   * 
   * @param mot, une chaîne de caractères
   * @param date, la date des devis à rechercher
   * @param nombre1, le montant total minimal du devis
   * @param nombre2, le montant total maximal du devis
   * @param idAmen, l'id du type d'aménagement
   * @return la liste de tous les devis remplissant toutes les conditions
   * @throws BizzException si mot ou date est null, nombre1 > nombre 2, typeAmen < 0
   */
  List<DevisDto> recupererLesDevis(String mot, LocalDate date, int nombre1, int nombre2,
      int idAmen);

  /**
   * Retourne le devis ayant comme id celui passé en paramètre
   * 
   * @param idDevis, l'id du devis à retourner
   * @return le devis correspondant à l'id
   * @throws BizzException si le paramètre <= 0
   */
  DevisDto recupererUnDevis(int idDevis);

  /**
   * Change l'état du devis ayant 'idDevis' comme id et éventuellement la date si celle-ci n'est pas
   * une chaîne de caractères vide
   * 
   * @param idDevis, l'id du devis dont l'état doit changer
   * @param etat, le nouvel état du devis
   * @param date, la nouvelle date du devis ou "" si la date ne doit pas changer
   * @return le devis correspondant à l'id avec son nouvel état
   */
  DevisDto changerEtatDevis(int idDevis, String etat, String date);

}
