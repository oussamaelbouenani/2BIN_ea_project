package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class DevisDaoImpl implements DevisDao {

  private RequetesSql rs;
  private BizzFactory factory;
  private TypeAmenagementDao typeAmenDao;

  public DevisDaoImpl(RequetesSql rs, BizzFactory factory, TypeAmenagementDao typeAmenDao) {
    this.rs = rs;
    this.factory = factory;
    this.typeAmenDao = typeAmenDao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto insererDevis(DevisDto devisDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_DEVIS);
    try {
      ps.setDate(1, Date.valueOf(devisDto.getDateDevis()));
      ps.setDouble(2, devisDto.getMontantTotal());
      ps.setInt(3, devisDto.getDureeTravaux());
      ps.setInt(4, devisDto.getIdClient());
      ps.setString(5, "Introduit");
      if (devisDto.getDateDebutTravaux() == null) {
        ps.setNull(6, 0);
      } else {
        ps.setDate(6, Date.valueOf(devisDto.getDateDebutTravaux()));
      }
      if (devisDto.getIdPhotoFav() == 0) {
        ps.setNull(7, 0);
      } else {
        ps.setInt(7, devisDto.getIdPhotoFav());
      }
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        devisDto.setIdDevis(res.getInt(1));
        res.close();
        return devisDto;
      } else {
        throw new FatalException("Insertion devis echouée");
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean insererDevisTypesAmenagement(int[] id) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_DEVIS_TYPE_AMENAGEMENT);
    try {
      int idDevis = id[id.length - 1];
      for (int i = 0; i < id.length; i++) {
        if (i != id.length - 1 && id[i] != 0) {
          ps.setInt(1, idDevis);
          ps.setInt(2, id[i]);
          ps.executeQuery();
        } else {
          break;
        }
      }
      return true;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto recupererUnDevis(int idDevis) {
    DevisDto devisDto = factory.getDevis();
    PreparedStatement ps;
    try {
      ps = rs.preparedStatement(RequetesSql.GET_DEVIS_BY_ID_DEVIS);
      ps.setInt(1, idDevis);
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        devisDto = recupererDevis(res);
      }
      res.close();
      return devisDto;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DevisDto> recupererMesDevis(int idClient, LocalDate date, int nombre1, int nombre2,
      int idAmen) {
    List<DevisDto> liste = new ArrayList<>();
    PreparedStatement ps;
    try {
      if (date.equals(LocalDate.MIN) && idAmen == 0) { // si y a pas de date + pas d'amenagement
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_BY_ID_CLIENT);
        ps.setInt(1, idClient);
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
      } else if (date.equals(LocalDate.MIN) && idAmen != 0) { // si y a pas de date + y a type amen
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_BY_ID_CLIENT_TYPE_AMEN);
        ps.setInt(1, idClient);
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setInt(4, idAmen);
      } else if (!date.equals(LocalDate.MIN) && idAmen == 0) { // Si y a date + pas type amen
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_BY_ID_CLIENT_DATE);
        ps.setInt(1, idClient);
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setDate(4, Date.valueOf(date));
      } else { // si y a tous les parametres
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_BY_ID_CLIENT_DATE_TYPE_AMEN);
        ps.setInt(1, idClient);
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setInt(4, idAmen);
        ps.setDate(5, Date.valueOf(date));
      }
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        liste.add(recupererDevis(res));
      }
      res.close();
      return liste;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DevisDto> recupererLesDevis(String mot, LocalDate date, int nombre1, int nombre2,
      int idAmen) {
    List<DevisDto> liste = new ArrayList<>();
    PreparedStatement ps;
    try {
      if (date.equals(LocalDate.MIN) && idAmen == 0) { // si y a pas de date + pas d'amenagement
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS);
        ps.setString(1, '%' + mot.toLowerCase() + '%');
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
      } else if (date.equals(LocalDate.MIN) && idAmen != 0) { // si y a pas de date + y a type amen
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_TYPE_AMEN);
        ps.setString(1, '%' + mot.toLowerCase() + '%');
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setInt(4, idAmen);
      } else if (!date.equals(LocalDate.MIN) && idAmen == 0) { // Si y a date + pas type amen
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_DATE);
        ps.setString(1, '%' + mot.toLowerCase() + '%');
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setDate(4, Date.valueOf(date));
      } else { // si y a tous les parametres
        ps = rs.preparedStatement(RequetesSql.GET_DEVIS_DATE_TYPE_AMEN);
        ps.setString(1, '%' + mot.toLowerCase() + '%');
        ps.setInt(2, nombre1);
        ps.setInt(3, nombre2);
        ps.setInt(4, idAmen);
        ps.setDate(5, Date.valueOf(date));
      }
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        liste.add(recupererDevis(res));
      }
      res.close();
      return liste;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  private DevisDto recupererDevis(ResultSet res) {
    DevisDto ins = factory.getDevis();
    try {
      ins.setIdDevis(res.getInt(1));
      ins.setDateDevis(res.getDate(2).toLocalDate());
      ins.setMontantTotal(res.getDouble(3));
      ins.setDureeTravaux(res.getInt(4));
      ins.setIdClient(res.getInt(5));
      ins.setEtat(res.getString(6));
      if (res.getDate(7) != null) {
        ins.setDateDebutTravaux(res.getDate(7).toLocalDate());
      }
      ins.setIdPhotoFav(res.getInt(8));
      ins.setListeTypeAmen(typeAmenDao.recupererTypesAmenagement(ins.getIdDevis()));
      return ins;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto changerEtatDevis(int idDevis, String etat, String date) {
    PreparedStatement ps;
    try {
      if (!date.equals("")) {
        ps = rs.preparedStatement(RequetesSql.UPDATE_ETAT_DATE_DEVIS);
        ps.setString(1, etat);
        if (date.equals("supp")) {
          ps.setNull(2, 0);
        } else {
          LocalDate dateParse = LocalDate.parse(date);
          ps.setDate(2, Date.valueOf(dateParse));
        }
        ps.setInt(3, idDevis);
      } else {
        ps = rs.preparedStatement(RequetesSql.UPDATE_ETAT_DEVIS);
        ps.setString(1, etat);
        ps.setInt(2, idDevis);
      }
      ps.executeQuery();
      return recupererUnDevis(idDevis);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }
}
