package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import java.util.List;

class TypeAmenagementUccImpl implements TypeAmenagementUcc {

  private TypeAmenagementDao typeDao;
  private DalServices dalServ;

  /**
   * Creer une instance d'un type amenagement.
   * 
   * @param typeDao
   * @param dalServ
   * @throws IllegalArgumentException si parametre null
   */
  public TypeAmenagementUccImpl(TypeAmenagementDao typeDao, DalServices dalServ) {
    if (typeDao == null || dalServ == null) {
      throw new IllegalArgumentException("Constructeur invalide");
    }
    this.typeDao = typeDao;
    this.dalServ = dalServ;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeAmenagementDto insererTypeAmenagement(TypeAmenagementDto typeDto) {
    if (typeDto == null) {
      throw new BizzException("Type non conforme");
    }
    try {
      dalServ.startTransaction();
      return typeDao.insererTypeAmenagement(typeDto);
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
  public List<TypeAmenagementDto> recupererTypesAmenagement() {
    try {
      dalServ.startTransaction();
      return typeDao.recupererTypesAmenagement();
    } catch (Exception exception) {
      dalServ.rollbackTransaction();
    } finally {
      dalServ.commitTransaction();
    }
    return null;
  }
}
