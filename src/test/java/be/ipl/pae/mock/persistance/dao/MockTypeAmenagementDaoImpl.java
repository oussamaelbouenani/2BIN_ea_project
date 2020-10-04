package be.ipl.pae.mock.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.persistance.dal.RequetesSql;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import java.util.ArrayList;
import java.util.List;

public class MockTypeAmenagementDaoImpl implements TypeAmenagementDao {

  public MockTypeAmenagementDaoImpl(RequetesSql rs, BizzFactory factory) {}

  @Override
  public TypeAmenagementDto insererTypeAmenagement(TypeAmenagementDto type) {
    return type;
  }

  @Override
  public List<TypeAmenagementDto> recupererTypesAmenagement() {
    return new ArrayList<TypeAmenagementDto>();
  }

  @Override
  public List<String> recupererTypesAmenagement(int idDevis) {
    return new ArrayList<String>();
  }

}
