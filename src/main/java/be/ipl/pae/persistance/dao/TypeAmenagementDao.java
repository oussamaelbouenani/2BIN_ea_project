package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.exception.FatalException;

import java.util.List;

public interface TypeAmenagementDao {

  /**
   * Insère un type d'aménagement dans la base de données
   * 
   * @param type, le type d'aménagement à insérer
   * @return le type d'aménagement inséré
   * @throws FatalException si l'insertion a échouée ou si une erreur s'est produite dans une
   *         requête SQL
   */
  TypeAmenagementDto insererTypeAmenagement(TypeAmenagementDto type);

  /**
   * Retourne la liste de tous les types d'aménagements
   * 
   * @return la liste de tous les types d'aménagement
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  List<TypeAmenagementDto> recupererTypesAmenagement();

  /**
   * Retourne la liste de tous les titres des types d'aménagements du devis ayant un id égal au
   * paramètre
   * 
   * @param idDevis, l'id d'un devis
   * @return une liste de string de tous les titres des types d'aménagements du devis ayant un id
   *         égal au paramètre
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  List<String> recupererTypesAmenagement(int idDevis);

}
