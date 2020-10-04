package be.ipl.pae.bizz.dto;

public interface ClientDto {

  /**
   * Retourne l'id du client
   * 
   * @return l'id du client
   */
  int getIdClient();

  /**
   * Met à jour l'id du client
   * 
   * @param idClient, le nouvel id du client
   */
  void setIdClient(int idClient);

  /**
   * Retourne le nom du client
   * 
   * @return le nom du client, sous forme de chaîne de caractères
   */
  String getNom();

  /**
   * Met à jour le nom du client
   * 
   * @param nom, le nouveau nom du client
   */
  void setNom(String nom);

  /**
   * Retourne le prénom du client
   * 
   * @return le prénom du client, sous forme de chaîne de caractères
   */
  String getPrenom();

  /**
   * Met à jour le prénom du client
   * 
   * @param prenom, le nouveau prénom du client
   */
  void setPrenom(String prenom);

  /**
   * Retourne le code postal du client
   * 
   * @return le code postal du client, sous forme de chaîne de caractères
   */
  String getCodePostal();

  /**
   * Met à jour le code postal du client
   * 
   * @param codePostal, le nouveau code postal du client
   */
  void setCodePostal(String codePostal);

  /**
   * Retourne la ville du client
   * 
   * @return la ville du client, sous forme de chaîne de caractères
   */
  String getVille();

  /**
   * Met à jour la ville du client
   * 
   * @param ville, la nouvelle ville du client
   */
  void setVille(String ville);

  /**
   * Retourne l'email du client
   * 
   * @return l'email du client, sous forme de chaîne de caractères
   */
  String getEmail();

  /**
   * Met à jour l'email du client
   * 
   * @param email, le nouvel email du client
   */
  void setEmail(String email);

  /**
   * Retourne le téléphone du client
   * 
   * @return le téléphone du client, sous forme de chaîne de caractères
   */
  String getTelephone();

  /**
   * Met à jour le téléphone du client
   * 
   * @param telephone, le nouveau téléphone du client
   */
  void setTelephone(String telephone);

  /**
   * Retourne la rue du client
   * 
   * @return la rue du client, sous forme de chaîne de caractères
   */
  String getRue();

  /**
   * Met à jour la rue du client
   * 
   * @param rue, la nouvelle rue du client
   */
  void setRue(String rue);

  /**
   * Retourne le numéro du client
   * 
   * @return le numéro du client, sous forme de chaîne de caractères
   */
  String getNumero();

  /**
   * Met à jour le numéro du client
   * 
   * @param numero, le nouveau numéro du client
   */
  void setNumero(String numero);

  /**
   * Retourne la boite du client
   * 
   * @return la boite du client, sous forme de chaîne de caractères
   */
  String getBoite();

  /**
   * Met à jour la boite du client
   * 
   * @param boite, la nouvelle boite du client
   */
  void setBoite(String boite);

}
