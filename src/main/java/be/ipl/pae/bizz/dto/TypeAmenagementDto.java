package be.ipl.pae.bizz.dto;

public interface TypeAmenagementDto {

  /**
   * Retourne l'id du type d'aménagement
   * 
   * @return l'id du type d'aménagement
   */
  int getIdTypeAmenagement();

  /**
   * Met à jour l'id du type d'aménagement
   * 
   * @param idTypeAmenagement, le nouvel id du type d'aménagement
   */
  void setIdTypeAmenagement(int idTypeAmenagement);

  /**
   * Retourne le titre du type d'aménagement
   * 
   * @return le titre du type d'aménagement
   */
  String getTitre();

  /**
   * Met à jour le titre du type d'aménagement
   * 
   * @param titre, le nouveau titre du type d'aménagement
   */
  void setTitre(String titre);

  /**
   * Retourne la description du type d'aménagement
   * 
   * @return la description du type d'aménagement
   */
  String getDescription();

  /**
   * Met à jour la description du type d'aménagement
   * 
   * @param description, la nouvelle description du type d'aménagement
   */
  void setDescription(String description);

}
