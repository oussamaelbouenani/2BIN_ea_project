package be.ipl.pae.bizz;

public class ClientImpl implements Client {
  private int idClient;
  private String nom;
  private String prenom;
  private String codePostal;
  private String ville;
  private String email;
  private String telephone;
  private String rue;
  private String numero;
  private String boite;

  public ClientImpl() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdClient() {
    return this.idClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdClient(int idClient) {
    this.idClient = idClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNom() {
    return this.nom;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrenom() {
    return this.prenom;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCodePostal() {
    return this.codePostal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVille() {
    return this.ville;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVille(String ville) {
    this.ville = ville;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getEmail() {
    return this.email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTelephone() {
    return this.telephone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRue() {
    return rue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRue(String rue) {
    this.rue = rue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNumero() {
    return numero;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNumero(String numero) {
    this.numero = numero;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getBoite() {
    return boite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBoite(String boite) {
    this.boite = boite;
  }

  @Override
  public String toString() {
    return "ClientImpl [idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom
        + ", codPostal=" + codePostal + ", ville=" + ville + ", email=" + email + ", telephone="
        + telephone + "]";
  }

}
