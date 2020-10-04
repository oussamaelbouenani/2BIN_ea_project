package be.ipl.pae.bizz;

import org.mindrot.bcrypt.BCrypt;

import java.time.LocalDate;

class InscritImpl implements Inscrit {

  private int idInscrit;
  private String nom;
  private String prenom;
  private String pseudo;
  private String email;
  private String ville;
  private LocalDate dateInscription;
  private int idClient;
  private boolean estOuvrier;
  private boolean estValide;
  private String mdp;

  public InscritImpl() {
    this.dateInscription = LocalDate.now();
    this.estOuvrier = false;
    this.estValide = false;

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdInscrit() {
    return this.idInscrit;
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
  public String getPrenom() {
    return this.prenom;
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
  public String getEmail() {
    return this.email;
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
  public void setPrenom(String prenom) {
    this.prenom = prenom;
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
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPseudo() {
    return this.pseudo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDate getDateInscription() {
    return this.dateInscription;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getOuvrier() {
    return this.estOuvrier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getValide() {
    return this.estValide;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOuvrier(boolean estOuvrier) {
    this.estOuvrier = estOuvrier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValide(boolean estValide) {
    this.estValide = estValide;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean checkMdp(String mdp) {
    return BCrypt.checkpw(mdp, this.mdp);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdInscrit(int id) {
    this.idInscrit = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDateInscription(LocalDate ldt) {
    this.dateInscription = ldt;
  }

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

  @Override
  public String toString() {
    return "InscritImpl [id=" + idInscrit + ", nom=" + nom + ", prenom=" + prenom + ", pseudo="
        + "pseudo, email=" + email + ", ville=" + ville + ", dateInscription=" + dateInscription
        + ", idClient=" + idClient + ", estOuvrier=" + estOuvrier + ", estValide=" + estValide
        + ", mdp=" + mdp + "]";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMdp() {
    return this.mdp;
  }

}
