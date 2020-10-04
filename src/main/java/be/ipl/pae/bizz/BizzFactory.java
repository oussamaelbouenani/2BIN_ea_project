package be.ipl.pae.bizz;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.TypeAmenagementDto;

public interface BizzFactory {

  /**
   * Retourne un objet de type InscritDto vide
   * 
   * @return un de type InscritDto vide
   */
  InscritDto getInscrit();

  /**
   * Retourne un objet de type ClientDto vide
   * 
   * @return un objet de type ClientDto vide
   */
  ClientDto getClient();

  /**
   * Retourne un objet de type DevisDto vide
   * 
   * @return un objet de type DevisDto vide
   */
  DevisDto getDevis();

  /**
   * Retourne un objet de type PhotoDto vide
   * 
   * @return un objet de type PhotoDto vide
   */
  PhotoDto getPhoto();

  /**
   * Retourne un objet de type TypeAmenagementDto vide
   * 
   * @return un objet de type TypeAmenagementDto vide
   */
  TypeAmenagementDto getTypeAmenagement();

}
