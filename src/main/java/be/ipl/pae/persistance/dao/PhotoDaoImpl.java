package be.ipl.pae.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.Photo;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class PhotoDaoImpl implements PhotoDao {

  private RequetesSql rs;
  private BizzFactory factory;

  public PhotoDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.rs = rs;
    this.factory = factory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoDto insererPhoto(PhotoDto photoDto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.INSERT_PHOTO);
    try {
      ps.setInt(1, photoDto.getIdDevis());
      if (photoDto.getIdTypeAmenagement() == 0) {
        ps.setNull(2, 0);
      } else {
        ps.setInt(2, photoDto.getIdTypeAmenagement());
      }
      ps.setBoolean(3, photoDto.isVisible());
      ps.setString(4, photoDto.getPhoto());
      ResultSet res = ps.executeQuery();
      if (res.next()) {
        photoDto.setIdPhoto(res.getInt(1));
        res.close();
        return photoDto;
      } else {
        throw new FatalException("Insertion photo echouée");
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PhotoDto> recupererPhotosParListeId(List<Integer> listeIdPhoto) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_PHOTO_BY_ID_PHOTO);
    List<PhotoDto> listePhotoFav = new ArrayList<>();
    try {
      for (int i : listeIdPhoto) {
        ps.setInt(1, i);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
          listePhotoFav.add(recupererPhotos(res));
        }
        res.close();
      }
      return listePhotoFav;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoDto recupererPhotosByTypes(int type) {
    PreparedStatement ps = rs.preparedStatement(RequetesSql.GET_PHOTO_BY_TYPE_AMENAGEMENT);
    try {
      ps.setInt(1, type);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return recupererPhotos(rs);
      } else {
        return null;
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

  private PhotoDto recupererPhotos(ResultSet res) {
    Photo ins = (Photo) factory.getPhoto();
    try {
      ins.setIdDevis(res.getInt(1));
      ins.setIdPhoto(res.getInt(2));
      ins.setIdTypeAmenagement(res.getInt(3));
      ins.setVisible(res.getBoolean(4));
      ins.setPhoto(res.getString(5));
      return ins;
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage());
    }
  }

}
