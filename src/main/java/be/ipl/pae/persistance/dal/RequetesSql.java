package be.ipl.pae.persistance.dal;

import java.sql.PreparedStatement;

public interface RequetesSql {

  // ** Requetes Inscrit **/

  String GET_INSCRIT_BY_EMAIL =
      "SELECT * FROM projet.inscrits " + "WHERE lower(replace(email, ' ', '')) = lower(?);";

  String GET_INSCRIT_BY_ID = "SELECT * FROM projet.inscrits " + "WHERE id_inscrit = ?;";

  String GET_INSCRITS =
      "SELECT * FROM projet.inscrits WHERE lower(nom) LIKE ? OR lower(ville) LIKE ? "
          + "ORDER BY estValide;";

  String INSERT_INSCRIT =
      "INSERT INTO projet.inscrits(id_inscrit, nom, prenom, pseudo, email, ville, date_inscription,"
          + " id_client, estouvrier, estvalide, mdp) "
          + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_inscrit;";

  String UPDATE_IDCLIENT_INSCRIT = "UPDATE projet.inscrits SET id_client = ? WHERE email = ?;";

  String UPDATE_OUVRIER_VALIDE_INSCRIT =
      "UPDATE projet.inscrits SET estOuvrier = ?, estValide = ? WHERE email = ?;";

  // ** Requetes Client **/

  String INSERT_CLIENT = "INSERT INTO projet.clients("
      + " id_client, nom, prenom, code_postal, ville, email, tel, rue, numero, boite)"
      + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_client;";

  String GET_CLIENTS =
      "SELECT * FROM projet.clients WHERE lower(nom) LIKE ? OR lower(ville) LIKE ? "
          + "OR lower(code_postal) LIKE ? ORDER BY 1";
  String GET_CLIENT_BY_ID = "SELECT * FROM projet.clients WHERE id_client = ?";

  String GET_CLIENT_BY_EMAIL =
      "SELECT * FROM projet.clients WHERE lower(replace(email, ' ', '')) = lower(?);";

  // ** Requetes Devis **/

  String INSERT_DEVIS =
      "INSERT INTO projet.devis(" + " id_devis, date_devis, montant_total, duree_travaux, "
          + "id_client, etat, date_debut, id_photo_fav) "
          + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?) RETURNING id_devis;";

  String GET_DEVIS_BY_ID_DEVIS = "SELECT * FROM projet.devis WHERE id_devis = ?";

  String GET_DEVIS = "SELECT * FROM projet.devis d, projet.clients c "
      + "WHERE d.id_client = c.id_client AND lower(c.nom) LIKE ? "
      + "AND (d.montant_total > ? AND d.montant_total < ?) ORDER BY d.id_devis;";

  String GET_DEVIS_DATE = "SELECT * FROM projet.devis d, projet.clients c "
      + "WHERE d.id_client = c.id_client AND lower(c.nom) LIKE ? "
      + "AND (d.montant_total > ? AND d.montant_total < ?) "
      + "AND d.date_devis = ? ORDER BY d.id_devis;";

  String GET_DEVIS_TYPE_AMEN =
      "SELECT * FROM projet.devis d, projet.clients c, projet.devis_type_amenagements dta "
          + "WHERE d.id_client = c.id_client AND dta.id_devis = d.id_devis AND lower(c.nom) LIKE ? "
          + "AND (d.montant_total > ? AND d.montant_total < ?) "
          + "AND dta.id_type_amenagement = ? ORDER BY d.id_devis;";

  String GET_DEVIS_DATE_TYPE_AMEN =
      "SELECT * FROM projet.devis d, projet.clients c, projet.devis_type_amenagements dta "
          + "WHERE d.id_client = c.id_client AND dta.id_devis = d.id_devis AND lower(c.nom) LIKE ? "
          + "AND (d.montant_total > ? AND d.montant_total < ?) "
          + "AND dta.id_type_amenagement = ? AND d.date_devis = ? ORDER BY d.id_devis;";

  String GET_DEVIS_BY_ID_CLIENT = "SELECT * FROM projet.devis WHERE id_client = ? "
      + "AND (montant_total > ? AND montant_total < ?) ORDER BY id_devis;";

  String GET_DEVIS_BY_ID_CLIENT_DATE = "SELECT * FROM projet.devis WHERE id_client = ? "
      + "AND (montant_total > ? AND montant_total < ?) AND date_devis = ? ORDER BY id_devis;";

  String GET_DEVIS_BY_ID_CLIENT_TYPE_AMEN =
      "SELECT * FROM projet.devis d, projet.devis_type_amenagements dta "
          + "WHERE d.id_client = ? AND dta.id_devis = d.id_devis "
          + "AND (d.montant_total > ? AND d.montant_total < ?) "
          + "AND dta.id_type_amenagement = ? ORDER BY d.id_devis;";

  String GET_DEVIS_BY_ID_CLIENT_DATE_TYPE_AMEN =
      "SELECT * FROM projet.devis d, projet.devis_type_amenagements dta "
          + "WHERE d.id_client = ? AND dta.id_devis = d.id_devis "
          + "AND (d.montant_total > ? AND d.montant_total < ?) "
          + "AND dta.id_type_amenagement = ? AND date_devis = ? ORDER BY d.id_devis;";

  String UPDATE_ETAT_DATE_DEVIS =
      "UPDATE projet.devis SET etat=?, date_debut = ? WHERE id_devis = ? RETURNING id_devis;";
  String UPDATE_ETAT_DEVIS =
      "UPDATE projet.devis SET etat = ? WHERE id_devis = ? RETURNING id_devis;";

  String UPDATE_DATE_DEBUT_DEVIS = "UPDATE projet.devis SET date_debut = ? WHERE id_devis = ?;";

  // ** Requetes Photo **/

  String INSERT_PHOTO = "INSERT INTO projet.photos (id_devis, id_photo, "
      + "id_type_amenagement, visible, photo) VALUES (?, DEFAULT, ?, ?, ?) "
      + "RETURNING id_photo;";
  String GET_PHOTO_BY_ID_PHOTO = "SELECT * FROM projet.photos WHERE id_photo = ? ;";

  String GET_PHOTO_BY_TYPE_AMENAGEMENT =
      "SELECT * FROM projet.photos WHERE id_type_amenagement = ? AND visible=true FETCH FIRST 1 ROWS ONLY";

  // ** Requetes TypeAmenagement **/

  String INSERT_AMENAGEMENT =
      "INSERT INTO projet.types_amenagements(id_type_amenagement, titre, description)"
          + "VALUES (DEFAULT, ?, ?) RETURNING id_type_amenagement;";

  String GET_TYPES_AMENAGEMENT = "SELECT * FROM projet.types_amenagements ORDER BY 1 ASC";

  String GET_TYPES_AMENAGEMENT_BY_IDDEVIS =
      "SELECT ta.titre FROM projet.types_amenagements ta, projet.devis_type_amenagements dta "
          + "WHERE ta.id_type_amenagement = dta.id_type_amenagement AND dta.id_devis = ? "
          + "ORDER BY dta.id_devis";


  // ** Requetes DevisTypesAmenagement **/

  String INSERT_DEVIS_TYPE_AMENAGEMENT =
      "INSERT INTO projet.devis_type_amenagements (id_devis, id_type_amenagement) VALUES (?, ?)"
          + "RETURNING id_devis;";

  PreparedStatement preparedStatement(String prepare);

}
