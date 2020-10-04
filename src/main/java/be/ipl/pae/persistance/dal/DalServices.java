package be.ipl.pae.persistance.dal;

import be.ipl.pae.exception.FatalException;

public interface DalServices {

  /**
   * Se connecte à la base de données
   * 
   * @throws FatalException si la connexion n'a pas pû se faire
   */
  void ouvrirConnexion();

  /**
   * Ferme la connexion à la base de données
   * 
   * @throws FatalException si la connexion n'a pas pû se fermer
   */
  void fermerConnexion();

  /**
   * Démarre une transaction
   * 
   * @throws FatalException si la transaction n'a pas pû démarrer
   */
  void startTransaction();

  /**
   * Valide la transaction
   * 
   * @throws FatalException si la transaction n'a pas pû étre validée
   */
  void commitTransaction();

  /**
   * Annule la transaction
   * 
   * @throws FatalException si la transaction n'a pas pû être annulée
   */
  void rollbackTransaction();

}
