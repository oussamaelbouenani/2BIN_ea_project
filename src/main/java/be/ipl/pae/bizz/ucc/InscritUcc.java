package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.exception.BizzException;

import java.util.List;

public interface InscritUcc {

  /**
   * Retourne l'inscrit correspondant à l'email et au mot de passe
   * 
   * @param email, l'email de l'inscrit à connecter
   * @param mdp, le mot de passe de l'inscrit à connecter
   * @return l'inscrit correspondant à l'email et au mot de passe
   * @throws BizzException si un des paramètres est null
   */
  InscritDto seConnecter(String email, String mdp);

  /**
   * Retourne l'inscrit ayant comme email celui passé en paramètre
   * 
   * @param email, l'email de l'inscrit à retourner
   * @return l'inscrit correspondant à l'email, null si aucun inscrit ne possède cet email
   * @throws BizzException si le paramètre est null
   */
  InscritDto recupererInscrit(String email);

  /**
   * Insère un inscrit dans la base de données
   * 
   * @param inscritDto, l'inscrit à insérer
   * @return l'inscrit inséréµ
   * @throws BizzException si le paramètre est null
   */
  InscritDto insererInscrit(InscritDto inscritDto);

  /**
   * Retourne la liste des inscrits ayant 'mot' pour nom, ville ou code postal
   * 
   * @param mot, la chaîne de caractères à retrouver dans le nom, la ville ou le code postal
   * @return la liste des inscrits ayant 'mot' pour nom, ville ou code postal
   * @throws BizzException si le paramètre est null
   */
  List<InscritDto> recupererInscrits(String mot);

  /**
   * Confirme l'inscription de l'inscrit en précisant s'il est ouvrier ou non
   * 
   * @param email, l'email de l'inscrit à mettre à jour
   * @param estOuvrier, chaîne de caractères ayant comme valeur "true" ou "false"
   * @return l'inscrit, correspondant à l'email, mis à jour
   * @throws BizzException si un des paramètres est null
   */
  InscritDto validerInscrit(String email, String estOuvrier);

  /**
   * Fait le lien entre l'inscrit et le client possédant le même email
   * 
   * @param email, l'email de l'inscrit
   * @return l'inscrit ayant l'email passé en paramètre
   * @throws BizzException si le paramètre est null
   */
  InscritDto lierInscritClient(String email);

}
