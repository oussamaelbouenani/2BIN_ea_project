
package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;

import java.util.List;
import java.util.Objects;

public class ClientUccImpl implements ClientUcc {

  private ClientDao clientDao;
  private DalServices dalServ;

  /**
   * Creer un objet client.
   * 
   * @param clientDao
   * @param dalServ
   * @throws IllegalArgumentException si parametre null
   */
  public ClientUccImpl(ClientDao clientDao, DalServices dalServ) {
    if (clientDao == null || dalServ == null) {
      throw new IllegalArgumentException("Constructeur invalide");
    }
    this.clientDao = clientDao;
    this.dalServ = dalServ;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto insererClient(ClientDto clientDto) {
    if (clientDto == null) {
      throw new BizzException("Client non conforme");
    }
    try {
      dalServ.startTransaction();
      if (Objects.nonNull(clientDao.recupererClientParEmail(clientDto.getEmail()))) {
        throw new BizzException("L'email est déjà utilisé");
      }
      return clientDao.insererClient(clientDto);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw exception;
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto lierClientInscrit(ClientDto clientDto) {
    if (clientDto == null) {
      throw new BizzException("Client non conforme");
    }
    try {
      dalServ.startTransaction();
      return clientDao.lierClientInscrit(clientDto);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw exception;
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientDto> recupererClients(String mot) {
    if (mot == null) {
      throw new BizzException("Parametre mot null");
    }
    try {
      dalServ.startTransaction();
      return clientDao.recupererClients(mot);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw new BizzException(exception.getMessage());
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto recupererClient(int idClient) {
    if (idClient <= 0) {
      throw new BizzException("Parametre idClient incorrect (- ou 0)");
    }
    try {
      dalServ.startTransaction();
      return clientDao.recupererClient(idClient);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw new BizzException(exception.getMessage());
    } finally {
      dalServ.commitTransaction();
    }
  }

}
