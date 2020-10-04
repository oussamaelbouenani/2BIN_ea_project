package be.ipl.pae.bizz;

public class TypeAmenagementImpl
    implements TypeAmenagement, be.ipl.pae.bizz.dto.TypeAmenagementDto {

  private int idTypeAmenagement;
  private String titre;
  private String description;

  public TypeAmenagementImpl() {}

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
  public void setIdTypeAmenagement(int idTypeAmenagement) {
    this.idTypeAmenagement = idTypeAmenagement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitre() {
    return titre;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitre(String titre) {
    this.titre = titre;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(String description) {
    this.description = description;
  }

}
