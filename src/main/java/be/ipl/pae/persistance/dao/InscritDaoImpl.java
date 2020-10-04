package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.Inscrit;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class InscritDaoImpl implements InscritDao {

  private RequetesSql rs;
  private BizzFactory factory;

  public InscritDaoImpl(RequetesSql rs, BizzFactory fact) {
    this.rs = rs;
    this.factory = fact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto recupererInscrit(String email) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_INSCRIT_BY_EMAIL);
    try {
      ps.setString(1, email);
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        InscritDto idto = recupererInscritRes(res);
        res.close();
        return idto;
      }
      res.close();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto recupererInscrit(int id) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_INSCRIT_BY_ID);
    try {
      ps.setInt(1, id);
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        return recupererInscritRes(res);
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<InscritDto> recupererInscrits(String mot) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_INSCRITS);
    List<InscritDto> liste = new ArrayList<>();
    try {
      ps.setString(1, '%' + mot.toLowerCase() + '%');
      ps.setString(2, '%' + mot.toLowerCase() + '%');
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        liste.add(recupererInscritSecure(res));
      }
      res.close();
      return liste;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto insererInscrit(InscritDto inscritDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_INSCRIT);
    try {
      ps.setString(1, inscritDto.getNom());
      ps.setString(2, inscritDto.getPrenom());
      ps.setString(3, inscritDto.getPseudo());
      ps.setString(4, inscritDto.getEmail());
      ps.setString(5, inscritDto.getVille());
      ps.setDate(6, Date.valueOf(inscritDto.getDateInscription()));
      if (inscritDto.getIdClient() == 0) {
        ps.setNull(7, 0);
      } else {
        ps.setInt(7, inscritDto.getIdClient());
      }
      ps.setBoolean(8, inscritDto.getOuvrier());
      ps.setBoolean(9, inscritDto.getValide());
      ps.setString(10, inscritDto.getMdp());
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        inscritDto.setIdInscrit(res.getInt(1));
        res.close();
        return inscritDto;
      } else {
        throw new FatalException("Insertion inscrit échouée");
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private InscritDto recupererInscritRes(ResultSet res) {
    Inscrit ins = (Inscrit) factory.getInscrit();
    try {
      remplirInscrit(res, ins);
      ins.setMdp(res.getString(11));
      return ins;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private InscritDto recupererInscritSecure(ResultSet res) {
    Inscrit ins = (Inscrit) factory.getInscrit();
    try {
      remplirInscrit(res, ins);
      ins.setMdp(null);
      return ins;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private void remplirInscrit(ResultSet res, Inscrit ins) throws SQLException {
    ins.setIdInscrit(res.getInt(1));
    ins.setNom(res.getString(2));
    ins.setPrenom(res.getString(3));
    ins.setPseudo(res.getString(4));
    ins.setEmail(res.getString(5));
    ins.setVille(res.getString(6));
    ins.setDateInscription(res.getDate(7).toLocalDate());
    ins.setIdClient(res.getInt(8));
    ins.setOuvrier(res.getBoolean(9));
    ins.setValide(res.getBoolean(10));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto validerInscrit(String email, String estOuvrier) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.UPDATE_OUVRIER_VALIDE_INSCRIT);
    try {
      ps.setBoolean(1, estOuvrier.equals("true"));
      ps.setBoolean(2, true);
      ps.setString(3, email);
      ps.executeUpdate();
      return recupererInscrit(email);
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto lierInscritClient(String email) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_CLIENT_BY_EMAIL);
    try {
      ps.setString(1, email);
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        PreparedStatement ps2 = rs.preparedStatement(RequetesSql.UPDATE_IDCLIENT_INSCRIT);
        ps2.setInt(1, res.getInt(1));
        ps2.setString(2, res.getString(6));
        ps2.executeUpdate();
        return recupererInscrit(email);
      } else {
        throw new FatalException("Aucun lien effectué");
      }
    } catch (SQLException excetion) {
      throw new FatalException(excetion.getMessage());
    }
  }

}


