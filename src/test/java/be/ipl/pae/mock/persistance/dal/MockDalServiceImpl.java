package be.ipl.pae.mock.persistance.dal;

import be.ipl.pae.config.InjectionService;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.PreparedStatement;

public class MockDalServiceImpl implements DalServices, RequetesSql {

  public MockDalServiceImpl(InjectionService is) {}

  @Override
  public PreparedStatement preparedStatement(String prepare) {
    return null;
  }

  @Override
  public void ouvrirConnexion() {}

  @Override
  public void fermerConnexion() {}

  @Override
  public void startTransaction() {}

  @Override
  public void commitTransaction() {}

  @Override
  public void rollbackTransaction() {}

}
