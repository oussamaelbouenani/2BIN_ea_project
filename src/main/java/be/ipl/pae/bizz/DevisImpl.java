package be.ipl.pae.bizz;

import com.owlike.genson.annotation.JsonDateFormat;

import java.time.LocalDate;
import java.util.List;

class DevisImpl implements Devis {

  private int idDevis;
  private LocalDate dateDevis;
  private double montantTotal;
  private int dureeTravaux;
  private int idClient;
  private Etat etatEnum;
  private String etat;
  private LocalDate dateDebutTravaux;
  private int idPhotoFav;
  private List<String> listeTypeAmen;

  public DevisImpl() {}

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
  @JsonDateFormat
  public LocalDate getDateDevis() {
    return dateDevis;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @JsonDateFormat
  public void setDateDevis(LocalDate dateDevis) {
    this.dateDevis = dateDevis;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getMontantTotal() {
    return montantTotal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMontantTotal(double montantTotal) {
    this.montantTotal = montantTotal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getDureeTravaux() {
    return dureeTravaux;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDureeTravaux(int dureeTravaux) {
    this.dureeTravaux = dureeTravaux;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdClient() {
    return idClient;
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
  public String getEtat() {
    return this.etat;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEtat(String etat) {
    this.etatEnum = Etat.getValue(etat);
    this.etat = Etat.getValue(etatEnum);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @JsonDateFormat
  public LocalDate getDateDebutTravaux() {
    return dateDebutTravaux;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @JsonDateFormat
  public void setDateDebutTravaux(LocalDate dateDebutTravaux) {
    this.dateDebutTravaux = dateDebutTravaux;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getIdPhotoFav() {
    return idPhotoFav;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIdPhotoFav(int idPhotoFav) {
    this.idPhotoFav = idPhotoFav;
  }

  @Override
  public String toString() {
    return "DevisImpl [idDevis=" + idDevis + ", dateDevis=" + dateDevis + ", montantTotal="
        + montantTotal + ", dureeTravaux=" + dureeTravaux + ", idClient=" + idClient + ", etat="
        + etat + ", dateDebut=" + dateDebutTravaux + ", idPhotoFav=" + idPhotoFav + "]";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getListeTypeAmen() {
    return listeTypeAmen;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListeTypeAmen(List<String> listeTypeAmen) {
    this.listeTypeAmen = listeTypeAmen;
  }

  public enum Etat {
    INTRODUIT("Introduit"), COMMANDE_PASSEE("Commande des aménagements passée"), COMMANDE_CONFIRMEE(
        "Commande d'aménagements confirmée"), DATE_DEBUT_CONFIRMEE(
            "Date de début des travaux confirmée"), FACTURE_MID_ENVOYEE(
                "Facture de milieu de chantier envoyée"), FACTURE_FIN_ENVOYEE(
                    "Facture de fin de chantier envoyée"), VISIBLE(
                        "Visible"), NON_VISIBLE("Non visible"), ANNULEE("Annulée");

    private String etat;

    Etat(String etat) {
      this.etat = etat;
    }

    public static Etat getValue(String etat) {
      switch (etat) {
        case "Introduit":
          return INTRODUIT;
        case "Commande passée":
          return COMMANDE_PASSEE;
        case "Commande confirmée":
          return COMMANDE_CONFIRMEE;
        case "Date de début des travaux confirmée":
          return DATE_DEBUT_CONFIRMEE;
        case "Facture de milieu de chantier envoyée":
          return FACTURE_MID_ENVOYEE;
        case "Facture de fin de chantier envoyée":
          return FACTURE_FIN_ENVOYEE;
        case "Visible":
          return VISIBLE;
        case "Non visible":
          return NON_VISIBLE;
        case "Annulée":
          return ANNULEE;
        default:
          break;
      }
      return null;
    }

    public static String getValue(Etat etat) {
      switch (etat) {
        case INTRODUIT:
          return "Introduit";
        case COMMANDE_PASSEE:
          return "Commande passée";
        case COMMANDE_CONFIRMEE:
          return "Commande confirmée";
        case DATE_DEBUT_CONFIRMEE:
          return "Date de début des travaux confirmée";
        case FACTURE_MID_ENVOYEE:
          return "Facture de milieu de chantier envoyée";
        case FACTURE_FIN_ENVOYEE:
          return "Facture de fin de chantier envoyée";
        case VISIBLE:
          return "Visible";
        case NON_VISIBLE:
          return "Non visible";
        case ANNULEE:
          return "Annulée";
        default:
          break;
      }
      return null;
    }

    public String getEtat() {
      return etat;
    }
  }

}
