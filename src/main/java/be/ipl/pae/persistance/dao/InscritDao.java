package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.exception.FatalException;

import java.util.List;

public interface InscritDao {

  /**
   * Retourne l'inscrit ayant comme email celui passé en paramètre
   * 
   * @param email, l'email de l'inscrit à retourner
   * @return l'inscrit correspondant à l'email, null si aucun inscrit ne possède cet email
   * @throws FatalException si l'insertion a échouée ou si une erreur s'est produite dans une
   *         requête SQL
   */
  InscritDto recupererInscrit(String email);

  /**
   * Retourne l'inscrit ayant comme id celui passé en paramètre
   * 
   * @param id, l'id de l'inscrit à retourner
   * @return l'inscrit correspondant à l'id, null si aucun inscrit ne possède cet id
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  InscritDto recupererInscrit(int id);

  /**
   * Insère un inscrit dans la base de données
   * 
   * @param inscritDto, l'inscrit à insérer
   * @return l'inscrit inséré
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  InscritDto insererInscrit(InscritDto inscritDto);

  /**
   * Retourne la liste des inscrits ayant 'mot' pour nom, ville ou code postal
   * 
   * @param mot, la chaîne de caractères à retrouver dans le nom, la ville ou le code postal
   * @return la liste des inscrits ayant 'mot' pour nom, ville ou code postal
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  List<InscritDto> recupererInscrits(String mot);

  /**
   * Confirme l'inscription de l'inscrit en précisant s'il est ouvrier ou non
   * 
   * @param email, l'email de l'inscrit à mettre à jour
   * @param estOuvrier, chaîne de caractères ayant comme valeur "true" ou "false"
   * @return l'inscrit, correspondant à l'email, mis à jour
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  InscritDto validerInscrit(String email, String estOuvrier);

  /**
   * Fait le lien entre l'inscrit et le client possédant le même email
   * 
   * @param email, l'email de l'inscrit
   * @return l'inscrit ayant l'email passé en paramètre
   * @throws FatalException si une erreur s'est produite dans une requête SQL
   */
  InscritDto lierInscritClient(String email);

}
