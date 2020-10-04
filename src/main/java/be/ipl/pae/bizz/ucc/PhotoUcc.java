package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.exception.BizzException;

import java.util.List;

public interface PhotoUcc {

  /**
   * Insère une photo dans la base de données
   * 
   * @param photoDto, la photo à insérer
   * @return la photo insérée
   * @throws BizzException si le paramètre est null
   */
  PhotoDto insererPhoto(PhotoDto photoDto);

  /**
   * Retourne une liste de photos ayant chacune un id présent dans la liste passée en paramètre
   * 
   * @param liste, liste d'id de photos
   * @return une liste de photos ayant chacune un id présent dans la liste passée en paramètre
   * @throws BizzException si le paramètre est null
   */
  List<PhotoDto> recupererPhoto(List<Integer> liste);

  /**
   * Retourne une photo ayant un type d'aménagement possédant l'id passé en paramètre
   * 
   * @param liste, une entier correspondant à l'id d'un type d'aménagement
   * @return une photo ayant un type d'aménagement possédant l'id passé en paramètre
   * @throws BizzException si le paramètre est <= 0
   */
  PhotoDto recupererPhotoByTypes(int type);

}
