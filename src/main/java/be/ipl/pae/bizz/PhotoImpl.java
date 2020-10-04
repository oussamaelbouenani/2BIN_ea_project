package be.ipl.pae.bizz;

class PhotoImpl implements Photo {

  private int idDevis;
  private int idPhoto;
  private int idTypeAmenagement;
  private boolean visible;
  private String photo;

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdDevis() {
    return idDevis;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdDevis(int idDevis) {
    this.idDevis = idDevis;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdPhoto() {
    return idPhoto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdPhoto(int idPhoto) {
    this.idPhoto = idPhoto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdTypeAmenagement() {
    return idTypeAmenagement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdTypeAmenagement(int idType) {
    this.idTypeAmenagement = idType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {
    return visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPhoto() {
    return photo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPhoto(String photo) {
    this.photo = photo;
  }

}
