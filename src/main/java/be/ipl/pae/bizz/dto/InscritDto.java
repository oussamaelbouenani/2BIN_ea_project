package be.ipl.pae.bizz.dto;

import java.time.LocalDate;

public interface InscritDto {

  /**
   * Retourne l'id de l'inscrit
   * 
   * @return l'id de l'inscrit
   */
  int getIdInscrit();

  /**
   * Retourne le nom de l'inscrit
   * 
   * @return le nom de l'inscrit, sous forme de chaîne de caractères
   */
  String getNom();

  /**
   * Retourne le prénom de l'inscrit
   * 
   * @return le prénom de l'inscrit, sous forme de chaîne de caractères
   */
  String getPrenom();

  /**
   * Retourne le pseudo de l'inscrit
   * 
   * @return le pseudo de l'inscrit, sous forme de chaîne de caractères
   */
  String getPseudo();

  /**
   * Retourne l'email de l'inscrit
   * 
   * @return l'email de l'inscrit, sous forme de chaîne de caractères
   */
  String getEmail();

  /**
   * Retourne la ville de l'inscrit
   * 
   * @return la ville de l'inscrit, sous forme de chaîne de caractères
   */
  String getVille();

  /**
   * Retourne le mot de passe de l'inscrit
   * 
   * @return le mot de passe de l'inscrit, sous forme de chaîne de caractères
   */
  String getMdp();

  /**
   * Retourne la date d'inscription de l'inscrit
   * 
   * @return la date d'inscription de l'inscrit
   */
  LocalDate getDateInscription();

  /**
   * Retourne l'id du client lié à l'inscrit
   * 
   * @return l'id du client lié à l'inscrit
   */
  int getIdClient();

  /**
   * 
   * @return true si l'inscrit est un ouvrier, false sinon
   */
  boolean getOuvrier();

  /**
   * 
   * @return true si l'inscription de l'inscrit est validée, false sinon
   */
  boolean getValide();

  /**
   * Met à jour l'id de l'inscrit
   * 
   * @param id, le nouvel id de l'inscrit
   */
  void setIdInscrit(int id);

  /**
   * Met à jour le nom de l'inscrit
   * 
   * @param nom, le nouveau nom de l'inscrit
   */
  void setNom(String nom);

  /**
   * Met à jour le prénom de l'inscrit
   * 
   * @param prenom, le nouveau prénom de l'inscrit
   */
  void setPrenom(String prenom);

  /**
   * Met à jour le pseudo de l'inscrit
   * 
   * @param pseudo, le nouveau pseudo de l'inscrit
   */
  void setPseudo(String pseudo);

  /**
   * Met à jour l'email de l'inscrit
   * 
   * @param email, le nouvel email de l'inscrit
   */
  void setEmail(String email);

  /**
   * Met à jour la ville de l'inscrit
   * 
   * @param ville, la nouvelle ville de l'inscrit
   */
  void setVille(String ville);

  /**
   * Met à jour l'attribut 'estOuvrier' de l'inscrit
   * 
   * @param estOuvrier, la nouvelle valeur de l'attribut 'estOuvrier' de l'inscrit
   */
  void setOuvrier(boolean estOuvrier);

  /**
   * Met à jour l'attribut 'estValide' de l'inscrit
   * 
   * @param estValide, la nouvelle valeur de l'attribut 'estValide' de l'inscrit
   */
  void setValide(boolean estValide);

  /**
   * Met à jour la date d'inscription de l'inscrit
   * 
   * @param ldt, la nouvelle date d'inscription de l'inscrit
   */
  void setDateInscription(LocalDate ldt);

  /**
   * Met à jour l'id du client lié à l'inscrit
   * 
   * @param idClient, l'id du nouveau client lié à l'inscrit
   */
  void setIdClient(int idClient);

  /**
   * Met à jour le mot de passe de l'inscrit
   * 
   * @param mdp, la nouveau mot de passe de l'inscrit
   */
  void setMdp(String mdp);

}
