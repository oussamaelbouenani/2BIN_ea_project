package be.ipl.pae.bizz.dto;

import java.time.LocalDate;
import java.util.List;

public interface DevisDto {

  /**
   * Retourne l'id du devis
   * 
   * @return l'id du devis
   */
  int getIdDevis();

  /**
   * Met à jour l'id du devis
   * 
   * @param idDevis, le nouvel id du devis
   */
  void setIdDevis(int idDevis);

  /**
   * Retourne la date du devis
   * 
   * @return la date du devis
   */
  LocalDate getDateDevis();

  /**
   * Met à jour la date du devis
   * 
   * @param dateDevis, la nouvelle date du devis
   */
  void setDateDevis(LocalDate dateDevis);

  /**
   * Retourne le montant total du devis
   * 
   * @return le montant total du devis
   */
  double getMontantTotal();

  /**
   * Met à jour le montant total du devis
   * 
   * @param montantTotal, le nouveau montant total du devis
   */
  void setMontantTotal(double montantTotal); // mis en double

  /**
   * Retourne la durée des travaux du devis
   * 
   * @return la durée des travaux du devis
   */
  int getDureeTravaux();

  /**
   * Met à jour la durée des travaux du devis
   * 
   * @param dureeTravaux, la nouvelle durée des travaux du devis
   */
  void setDureeTravaux(int dureeTravaux);

  /**
   * Retourne l'id du client lié au devis
   * 
   * @return l'id du client lié au devis
   */
  int getIdClient();

  /**
   * Met à jour l'id du client lié au devis
   * 
   * @param idClient, l'id du nouveau client lié au devis
   */
  void setIdClient(int idClient);

  /**
   * Retourne l'état du devis
   * 
   * @return l'état du devis, sous forme de chaîne de caractères
   */
  String getEtat();

  /**
   * Met à jour l'état du devis
   * 
   * @param etat, le nouvel état du devis
   */
  void setEtat(String etat);

  /**
   * Retourne la date de début des travaux du devis
   * 
   * @return la date de début des travaux du devis
   */
  LocalDate getDateDebutTravaux();

  /**
   * Met à jour la date de début des travaux du devis
   * 
   * @param dateDevis, la nouvelle date de début des travaux du devis
   */
  void setDateDebutTravaux(LocalDate dateDebutTravaux);

  /**
   * Retourne l'id de la photo favorite du devis
   * 
   * @return l'id de la photo favorite du devis
   */
  int getIdPhotoFav();

  /**
   * Met à jour l'id de la photo favorite du devis
   * 
   * @param idPhotoFav, l'id de la nouvelle photo favorite du devis
   */
  void setIdPhotoFav(int idPhotoFav);

  /**
   * Retourne la liste des types d'aménagement du devis
   * 
   * @return la liste des types d'aménagement du devis
   */
  List<String> getListeTypeAmen();

  /**
   * Met à jour la liste des types d'aménagement du devis
   * 
   * @param listeTypeAmen, la nouvelle liste des types d'aménagement du devis
   */
  void setListeTypeAmen(List<String> listeTypeAmen);

}
