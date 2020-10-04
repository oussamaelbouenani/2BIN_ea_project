package be.ipl.pae.bizz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.PhotoDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PhotoUccImplTest {
  private BizzFactory factory;
  private PhotoUcc ucc;

  @BeforeEach
  public void setUp() throws Exception {
    InjectionService is = new InjectionService("configuration/test.properties");
    DalServices dalServ = (DalServices) is.getDependency("DalServices", is);
    factory = (BizzFactory) is.getDependency("BizzFactory");
    PhotoDao dao = (PhotoDao) is.getDependency("PhotoDao", dalServ, factory);
    ucc = (PhotoUcc) is.getDependency("PhotoUcc", dao, dalServ);
  }

  @DisplayName("Constructeur null")
  @Test
  void testPhotoUccImpl() {
    assertThrows(IllegalArgumentException.class, () -> new PhotoUccImpl(null, null));
  }

  @DisplayName("Inserer photo null")
  @Test
  void testInsererPhoto1() {
    assertThrows(BizzException.class, () -> ucc.insererPhoto(null));
  }

  @DisplayName("Inserer photo correct")
  @Test
  void testInsererPhoto2() {
    assertNotNull(ucc.insererPhoto(factory.getPhoto()));
  }

  @DisplayName("Recuperer photo null")
  @Test
  void testRecupererPhoto1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererPhoto(null));
  }

  @DisplayName("Recuperer Photo correct")
  @Test
  void testRecupererPhoto2() {
    Assertions.assertNotNull(ucc.recupererPhoto(new ArrayList<Integer>()));
  }

  @DisplayName("Recuperer Photo id incorrect")
  @Test
  void testRecupererPhotoByTypes1() {
    assertThrows(BizzException.class, () -> ucc.recupererPhotoByTypes(-1));
  }

  @DisplayName("Recuperer Photo id correct")
  @Test
  void testRecupererPhotoByTypes2() {
    assertNotNull(ucc.recupererPhotoByTypes(1));
  }

}
