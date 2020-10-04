package be.ipl.pae.bizz.dto;

public interface PhotoDto {

  /**
   * Retourne l'id du devis lié à la photo
   * 
   * @return l'id du devis lié à la photo
   */
  int getIdDevis();

  /**
   * Met à jour l'id du devis lié à la photo
   * 
   * @param idDevis, l'id du nouveau devis lié à la photo
   */
  void setIdDevis(int idDevis);

  /**
   * Retourne l'id de la photo
   * 
   * @return l'id de la photo
   */
  int getIdPhoto();

  /**
   * Met à jour l'id de la photo
   * 
   * @param idPhoto, le nouvel id de la photo
   */
  void setIdPhoto(int idPhoto);

  /**
   * Retourne l'id du type d'aménagement lié à la photo
   * 
   * @return l'id du type d'aménagement lié à la photo
   */
  int getIdTypeAmenagement();

  /**
   * Met à jour l'id du type d'aménagement lié à la photo
   * 
   * @param idType, l'id du nouveau type d'aménagement lié à la photo
   */
  void setIdTypeAmenagement(int idType);

  /**
   * 
   * @return true si la photo est visible, false sinon
   */
  boolean isVisible();

  /**
   * Met à jour l'attribut 'visible' de la photo
   * 
   * @param visible, la nouvelle valeur de l'attribut 'visible' de la photo
   */
  void setVisible(boolean visible);

  /**
   * Retourne l'adresse de la photo
   * 
   * @return l'adresse de la photo
   */
  String getPhoto();

  /**
   * Met à jour l'adresse de la photo
   * 
   * @param photo, la nouvelle adresse de la photo
   */
  void setPhoto(String photo);

}
