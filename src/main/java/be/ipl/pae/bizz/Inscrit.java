package be.ipl.pae.bizz;

import be.ipl.pae.bizz.dto.InscritDto;

public interface Inscrit extends InscritDto {
  /**
   * Vérifie si le mot de passe passé en paramètre correspond au mot de passe de l'inscrit
   * 
   * @param mdp, le mot de passe à vérifier
   * @return true si le mot de passe correspond au mot de passe de l'inscrit, false sinon
   */
  boolean checkMdp(String mdp);

}
