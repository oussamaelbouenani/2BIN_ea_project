package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.exception.FatalException;

import java.util.List;

public interface ClientDao {

  /**
   * Insère un client dans la base de données
   * 
   * @param clientDto, le client à insérer
   * @return le client inséré
   * @throws FatalException si l'insertion a échouée ou si une erreur s'est produite dans une
   *         requête SQL
   */
  ClientDto insererClient(ClientDto clientDto);

  /**
   * Retourne l'id du client passé en paramètre
   * 
   * @param clientDto, le client dont l'id doit être retourné
   * @return l'id du client, -1 si le client n'existe pas
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  int recupererIdClient(ClientDto clientDto);

  /**
   * Retourne le client ayant comme email celui passé en paramètre
   * 
   * @param email, l'email du client à retourner
   * @return le client correspondant à l'email, null si aucun client ne possède cet email
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  ClientDto recupererClientParEmail(String email);

  /**
   * Fait le lien entre le client et l'inscrit possédant le même email
   * 
   * @param clientDto, le client à lier à l'inscrit
   * @return le client passé en paramètre
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  ClientDto lierClientInscrit(ClientDto clientDto);

  /**
   * Retourne la liste des clients ayant 'mot' pour nom, ville ou code postal
   * 
   * @param mot, la chaîne de caractères à retrouver dans le nom, la ville ou le code postal
   * @return la liste des clients ayant 'mot' pour nom, ville ou code postal
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  List<ClientDto> recupererClients(String mot);

  /**
   * Retourne le client ayant comme id celui passé en paramètre
   * 
   * @param idClient, l'id du client à retourner
   * @return le client correspondant à l'id
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  ClientDto recupererClient(int idClient);
}
