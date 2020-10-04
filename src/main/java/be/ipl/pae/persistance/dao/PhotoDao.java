package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.exception.FatalException;

import java.util.List;

public interface PhotoDao {

  /**
   * Insère une photo dans la base de données
   * 
   * @param photoDto, la photo à insérer
   * @return la photo insérée
   * @throws FatalException si l'insertion a échouée ou si une erreur s'est produite dans une
   *         requête SQL
   */
  PhotoDto insererPhoto(PhotoDto photoDto);

  /**
   * Retourne une liste de photos contenant les photos ayant pour id les id présent dans la liste
   * passée en parametre
   * 
   * @param liste, liste d'id de photos
   * @return une liste de photos ayant chacune un id présent dans la liste passée en paramètre
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  List<PhotoDto> recupererPhotosParListeId(List<Integer> liste);

  /**
   * Retourne une photo ayant un type d'aménagement possédant l'id passé en paramètre
   * 
   * @param idType, un entier correspondant à l'id d'un type d'aménagement
   * @return une photo ayant un type d'aménagement qui correspond à l'id passé en paramètre
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  PhotoDto recupererPhotosByTypes(int idType);
}
