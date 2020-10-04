package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.exception.BizzException;

import java.util.List;

public interface TypeAmenagementUcc {

  /**
   * Insère un type d'aménagement dans la base de données
   * 
   * @param type, le type d'aménagement à insérer
   * @return le type d'aménagement inséré
   * @throws BizzException si le paramètre est null
   */
  TypeAmenagementDto insererTypeAmenagement(TypeAmenagementDto type);

  /**
   * Retourne la liste de tous les types d'aménagement
   * 
   * @return la liste de tous les types d'aménagement
   */
  List<TypeAmenagementDto> recupererTypesAmenagement();

}
