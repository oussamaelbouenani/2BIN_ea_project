package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeAmenagementDaoImpl implements TypeAmenagementDao {

  private RequetesSql rs;
  private BizzFactory factory;

  public TypeAmenagementDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.rs = rs;
    this.factory = factory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeAmenagementDto insererTypeAmenagement(TypeAmenagementDto type) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_AMENAGEMENT);
    try {
      ps.setString(1, type.getTitre());
      ps.setString(2, type.getDescription());

      ResultSet res = ps.executeQuery();
      if (res.next()) {
        type.setIdTypeAmenagement(res.getInt(1));
        res.close();
        return type;
      } else {
        throw new FatalException("Insertion type amenagement echouée");
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TypeAmenagementDto> recupererTypesAmenagement() {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_TYPES_AMENAGEMENT);
    List<TypeAmenagementDto> liste = new ArrayList<>();
    try {
      ResultSet resultat = ps.executeQuery();
      while (resultat.next()) {
        liste.add(recupererTypesAmenagement(resultat));
      }
      resultat.close();
      return liste;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private TypeAmenagementDto recupererTypesAmenagement(ResultSet res) {
    TypeAmenagementDto type = factory.getTypeAmenagement();
    try {
      type.setIdTypeAmenagement(res.getInt(1));
      type.setTitre(res.getString(2));
      type.setDescription(res.getString(3));
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> recupererTypesAmenagement(int idDevis) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_TYPES_AMENAGEMENT_BY_IDDEVIS);
    List<String> liste = new ArrayList<>();
    try {
      ps.setInt(1, idDevis);
      ResultSet resultat = ps.executeQuery();
      while (resultat.next()) {
        liste.add(resultat.getString(1));
      }
      resultat.close();
      return liste;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

}
