package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.Inscrit;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.InscritDao;

import org.mindrot.bcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

class InscritUccImpl implements InscritUcc {

  private InscritDao inscritDao;
  private DalServices dalServ;

  /**
   * Creer une instance d'un inscrit.
   * 
   * @param inscritDao
   * @param dalServ
   * @throws IllegalArgumentException si parametre null
   */
  public InscritUccImpl(InscritDao inscritDao, DalServices dalServ) {
    if (inscritDao == null || dalServ == null) {
      throw new IllegalArgumentException("Constructeur invalide");
    }
    this.inscritDao = inscritDao;
    this.dalServ = dalServ;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto seConnecter(String email, String mdp) {
    if (email == null || mdp == null) {
      throw new BizzException("Email ou mdp incorrect");
    }
    try {
      dalServ.startTransaction();
      InscritDto inscritDto = inscritDao.recupererInscrit(email);
      if (inscritDto != null) {
        if (!((Inscrit) inscritDto).checkMdp(mdp)) {
          throw new BizzException("L'utilisateur/le mot de passe est incorrect");
        }
      } else {
        throw new BizzException("L'utilisateur n'existe pas");
      }
      return inscritDto;
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
  public InscritDto recupererInscrit(String email) {
    if (email == null) {
      throw new BizzException("Parametre email null");
    }
    try {
      dalServ.startTransaction();
      InscritDto inscrit = inscritDao.recupererInscrit(email);
      if (inscrit == null) {
        throw new BizzException();
      }
      return inscrit;
    } catch (BizzException exception) {
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
  public List<InscritDto> recupererInscrits(String mot) {
    if (mot == null) {
      throw new BizzException("Parametre mot null");
    }
    try {
      dalServ.startTransaction();
      return inscritDao.recupererInscrits(mot);
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
  public InscritDto insererInscrit(InscritDto inscritDto) {
    if (inscritDto == null) {
      throw new BizzException("Utilisateur non existant");
    }
    try {
      dalServ.startTransaction();
      if (Objects.nonNull(inscritDao.recupererInscrit(inscritDto.getEmail()))) {
        throw new BizzException("L'email est déjà utilisé");
      }
      String mdp = BCrypt.hashpw(inscritDto.getMdp(), BCrypt.gensalt());
      inscritDto.setMdp(mdp);
      return inscritDao.insererInscrit(inscritDto);
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
  public InscritDto validerInscrit(String email, String estOuvrier) {
    if (email == null) {
      throw new BizzException("Email non existant");
    }
    if (estOuvrier == null) {
      throw new BizzException("Parametre estOuvrier null");
    }
    try {
      dalServ.startTransaction();
      return inscritDao.validerInscrit(email, estOuvrier);
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
  public InscritDto lierInscritClient(String email) {
    if (email == null) {
      throw new BizzException("Email non existant");
    }
    try {
      dalServ.startTransaction();
      return inscritDao.lierInscritClient(email);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw exception;
    } finally {
      dalServ.commitTransaction();
    }
  }

}
