package be.ipl.pae.mock.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;
import be.ipl.pae.persistance.dao.ClientDao;

import java.util.ArrayList;
import java.util.List;

public class MockClientDaoImpl implements ClientDao {
  private BizzFactory factory;

  public MockClientDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.factory = factory;
  }

  @Override
  public ClientDto insererClient(ClientDto clientDto) {
    return clientDto;
  }

  @Override
  public int recupererIdClient(ClientDto clientDto) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ClientDto recupererClientParEmail(String email) {
    if (email.equals("a@b.c")) {
      return recupererClient(1);
    } else if (email.equals("c@b.a")) {
      return recupererClient(2);
    }
    return null;
  }

  @Override
  public ClientDto lierClientInscrit(ClientDto clientDto) {
    if (clientDto.getEmail().equals("a@b.c") || clientDto.getEmail().equals("c@b.a")) {
      return clientDto;
    }
    throw new FatalException();
  }

  @Override
  public List<ClientDto> recupererClients(String mot) {
    return new ArrayList<ClientDto>();
  }

  @Override
  public ClientDto recupererClient(int idClient) {
    if (idClient == 1 || idClient == 2) {
      ClientDto client = factory.getClient();
      if (idClient == 1) {
        client.setIdClient(1);
        client.setNom("bera");
        client.setEmail("a@b.c");
      } else {
        client.setIdClient(2);
        client.setNom("ouss");
        client.setEmail("c@b.a");
      }
      return client;
    }
    return null;
  }
}
