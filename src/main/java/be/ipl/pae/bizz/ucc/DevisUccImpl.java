package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.DevisDao;

import java.time.LocalDate;
import java.util.List;

class DevisUccImpl implements DevisUcc {

  private DevisDao devisDao;
  private DalServices dalServ;

  /**
   * Instancie un objet devis.
   * 
   * @param devisDao Dao du devis
   * @param dalServ
   * @throws IllegalArgumentException si parametre null
   */
  public DevisUccImpl(DevisDao devisDao, DalServices dalServ) {
    if (devisDao == null || dalServ == null) {
      throw new IllegalArgumentException("Constructeur invalide");
    }
    this.devisDao = devisDao;
    this.dalServ = dalServ;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto insererDevis(DevisDto devisDto) {
    if (devisDto == null) {
      throw new BizzException("Devis non conforme");
    }
    try {
      dalServ.startTransaction();
      return devisDao.insererDevis(devisDto);
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
  public boolean insererDevisTypesAmenagements(int[] id) {
    if (id.length == 0 || id[0] == 0) {
      throw new BizzException("Aucun type à inserer");
    }
    try {
      dalServ.startTransaction();
      return devisDao.insererDevisTypesAmenagement(id);
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
  public List<DevisDto> recupererMesDevis(int id, LocalDate date, int nombre1, int nombre2,
      int typeAmen) {
    if (id <= 0 || date == null || nombre1 > nombre2 || typeAmen < 0) {
      throw new BizzException("Parametre invalide");
    }
    try {
      dalServ.startTransaction();
      List<DevisDto> listeDevisDto =
          devisDao.recupererMesDevis(id, date, nombre1, nombre2, typeAmen);
      if (listeDevisDto == null) {
        throw new BizzException();
      }
      return listeDevisDto;
    } catch (BizzException exception) {
      dalServ.rollbackTransaction();
      throw new BizzException();
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DevisDto> recupererLesDevis(String mot, LocalDate date, int nombre1, int nombre2,
      int idAmen) {
    if (mot == null || date == null || nombre1 > nombre2 || idAmen < 0) {
      throw new BizzException("Parametre invalide");
    }
    try {
      dalServ.startTransaction();
      List<DevisDto> listeDevisDto =
          devisDao.recupererLesDevis(mot, date, nombre1, nombre2, idAmen);
      if (listeDevisDto == null) {
        throw new BizzException();
      }
      return listeDevisDto;
    } catch (BizzException exception) {
      dalServ.rollbackTransaction();
      throw new BizzException();
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto recupererUnDevis(int idDevis) {
    if (idDevis <= 0) {
      throw new BizzException("Parametre invalide");
    }
    try {
      dalServ.startTransaction();
      DevisDto devisDto = devisDao.recupererUnDevis(idDevis);
      if (devisDto == null) {
        throw new BizzException();
      }
      return devisDto;
    } catch (BizzException exception) {
      dalServ.rollbackTransaction();
      throw new BizzException();
    } finally {
      dalServ.commitTransaction();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto changerEtatDevis(int idDevis, String etat, String date) {
    try {
      dalServ.startTransaction();
      return devisDao.changerEtatDevis(idDevis, etat, date);
    } catch (BizzException exception) {
      dalServ.rollbackTransaction();
      throw new BizzException();
    } finally {
      dalServ.commitTransaction();
    }
  }

}
