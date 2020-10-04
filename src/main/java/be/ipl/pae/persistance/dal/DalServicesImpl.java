package be.ipl.pae.persistance.dal;

import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.FatalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DalServicesImpl implements DalServices, RequetesSql {

  private ThreadLocal<Connection> connexion;
  private String lienDb;
  private String login;
  private String mdp;

  public DalServicesImpl(InjectionService is) {
    connexion = new ThreadLocal<>();
    lienDb = is.getValue("connectionString");
    login = is.getValue("user");
    mdp = is.getValue("password");
    try {
      Class.forName(is.getValue("driver"));
    } catch (ClassNotFoundException exception) {
      System.out.println("Driver PostegresSQL manquant!");
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ouvrirConnexion() {
    Connection conn;
    try {
      conn = DriverManager.getConnection(lienDb, login, mdp);
      connexion.set(conn);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fermerConnexion() {
    try {
      Connection conn = connexion.get();
      if (conn == null) {
        return;
      }
      connexion.remove();
      conn.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception.getMessage());
    }
  }

  @Override
  public PreparedStatement preparedStatement(String prepare) {
    PreparedStatement ps;
    try {
      Connection conn = connexion.get();
      ps = conn.prepareStatement(prepare);
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
    return ps;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startTransaction() {
    try {
      ouvrirConnexion();
      Connection conn = connexion.get();
      conn.setAutoCommit(false);
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commitTransaction() {
    try {
      Connection conn = connexion.get();
      if (conn == null) {
        return;
      }
      conn.commit();
      conn.setAutoCommit(true);
      fermerConnexion();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rollbackTransaction() {
    try {
      Connection conn = connexion.get();
      if (conn == null) {
        return;
      }
      conn.rollback();
      conn.setAutoCommit(true);
      fermerConnexion();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

}
