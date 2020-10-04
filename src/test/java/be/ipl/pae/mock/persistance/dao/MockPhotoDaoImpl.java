package be.ipl.pae.mock.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.persistance.dal.RequetesSql;
import be.ipl.pae.persistance.dao.PhotoDao;

import java.util.ArrayList;
import java.util.List;

public class MockPhotoDaoImpl implements PhotoDao {
  private BizzFactory factory;

  public MockPhotoDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.factory = factory;
  }

  @Override
  public PhotoDto insererPhoto(PhotoDto photoDto) {
    return photoDto;
  }

  @Override
  public List<PhotoDto> recupererPhotosParListeId(List<Integer> liste) {
    return new ArrayList<PhotoDto>();
  }

  @Override
  public PhotoDto recupererPhotosByTypes(int liste) {
    return factory.getPhoto();
  }

}
