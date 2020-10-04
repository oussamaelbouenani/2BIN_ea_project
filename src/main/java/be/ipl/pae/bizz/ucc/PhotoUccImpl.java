package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.List;

class PhotoUccImpl implements PhotoUcc {

  private PhotoDao photoDao;
  private DalServices dalServ;

  /**
   * Creer une instance d'une photo
   * 
   * @param photoDao
   * @param dalServ
   * @throws IllegalArgumentException si parametre null
   */
  public PhotoUccImpl(PhotoDao photoDao, DalServices dalServ) {
    if (photoDao == null || dalServ == null) {
      throw new IllegalArgumentException("Constructeur non valide");
    }
    this.photoDao = photoDao;
    this.dalServ = dalServ;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoDto insererPhoto(PhotoDto photoDto) {
    if (photoDto == null) {
      throw new BizzException("Photo non conforme");
    }
    try {
      dalServ.startTransaction();
      return photoDao.insererPhoto(photoDto);
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
  public List<PhotoDto> recupererPhoto(List<Integer> listeIdPhoto) {
    if (listeIdPhoto == null) {
      throw new BizzException("Photo non conforme");
    }
    try {
      dalServ.startTransaction();
      return photoDao.recupererPhotosParListeId(listeIdPhoto);
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
  public PhotoDto recupererPhotoByTypes(int type) {
    if (type < 0) {
      throw new BizzException("Photo non conforme");
    }
    try {
      dalServ.startTransaction();
      return photoDao.recupererPhotosByTypes(type);
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
      throw new BizzException(exception.getMessage());
    } finally {
      dalServ.commitTransaction();
    }
  }
}
