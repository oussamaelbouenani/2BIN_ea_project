package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

  private RequetesSql rs;
  private BizzFactory factory;

  public ClientDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.rs = rs;
    this.factory = factory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto insererClient(ClientDto clientDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_CLIENT);
    try {
      ps.setString(1, clientDto.getNom());
      ps.setString(2, clientDto.getPrenom());
      ps.setString(3, clientDto.getCodePostal());
      ps.setString(4, clientDto.getVille());
      ps.setString(5, clientDto.getEmail());
      ps.setString(6, clientDto.getTelephone());
      ps.setString(7, clientDto.getRue());
      ps.setString(8, clientDto.getNumero());
      ps.setString(9, clientDto.getBoite());

      ResultSet res = ps.executeQuery();
      if (res.next()) {
        clientDto.setIdClient(res.getInt(1));
        res.close();
        return clientDto;
      } else {
        throw new FatalException("Insertion client échouée");
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int recupererIdClient(ClientDto clientDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_CLIENT_BY_EMAIL);
    try {
      ps.setString(1, clientDto.getEmail());
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        return res.getInt(1);
      }
      res.close();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto recupererClientParEmail(String email) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_CLIENT_BY_EMAIL);
    try {
      ps.setString(1, email);
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        ClientDto clDto = recupererClient(res);
        res.close();
        return clDto;
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
  public ClientDto lierClientInscrit(ClientDto clientDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.UPDATE_IDCLIENT_INSCRIT);
    try {
      int idClient = recupererIdClient(clientDto);
      ps.setInt(1, idClient);
      ps.setString(2, clientDto.getEmail());
      int idClientLie = ps.executeUpdate();
      if (idClientLie == 0) {
        throw new FatalException("Aucun lien effectué");
      }
      return clientDto;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientDto> recupererClients(String mot) {

    List<ClientDto> liste = new ArrayList<>();
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_CLIENTS);
    try {
      ps.setString(1, '%' + mot.toLowerCase() + '%');
      ps.setString(2, '%' + mot.toLowerCase() + '%');
      ps.setString(3, '%' + mot.toLowerCase() + '%');
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        liste.add(recupererClient(res));
      }
      return liste;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto recupererClient(int idClient) {
    ClientDto client = factory.getClient();
    boolean estRentre = false;
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_CLIENT_BY_ID);
    try {
      ps.setInt(1, idClient);
      ResultSet res = ps.executeQuery();
      while (res.next()) {
        estRentre = true;
        client = recupererClient(res);
      }
      if (estRentre) {
        return client;
      }
      throw new FatalException("Pas de client avec cet id");
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private ClientDto recupererClient(ResultSet res) {
    ClientDto client = factory.getClient();
    try {
      client.setIdClient(res.getInt(1));
      client.setNom(res.getString(2));
      client.setPrenom(res.getString(3));
      client.setCodePostal(res.getString(4));
      client.setVille(res.getString(5));
      client.setEmail(res.getString(6));
      client.setTelephone(res.getString(7));
      client.setRue(res.getString(8));
      client.setNumero(res.getString(9));
      client.setBoite(res.getString(10));
      return client;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }

  }


}
