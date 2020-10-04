package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.exception.BizzException;

import java.util.List;

public interface ClientUcc {

  /**
   * Insère un client dans la base de données
   * 
   * @param clientDto, le client à insérer
   * @return le client inséré
   * @throws BizzException si le paramètre est null ou si l'email est dèjà existant
   */
  ClientDto insererClient(ClientDto clientDto);

  /**
   * Fait le lien entre le client et l'inscrit possédant le même email
   * 
   * @param clientDto, le client à lier à l'inscrit
   * @return le client passé en paramètre
   * @throws BizzException si le paramètre est null
   */
  ClientDto lierClientInscrit(ClientDto clientDto);

  /**
   * Retourne la liste des clients ayant 'mot' pour nom, ville ou code postal
   * 
   * @param mot, la chaîne de caractères à retrouver dans le nom, la ville ou le code postal
   * @return la liste des clients ayant 'mot' pour nom, ville ou code postal
   * @throws BizzException si le paramètre est null
   */
  List<ClientDto> recupererClients(String mot);

  /**
   * Retourne le client ayant comme id celui passé en paramètre
   * 
   * @param idClient, l'id du client à retourner
   * @return le client correspondant à l'id
   * @throws BizzException si le paramètre est <= 0
   */
  ClientDto recupererClient(int idClient);

}
