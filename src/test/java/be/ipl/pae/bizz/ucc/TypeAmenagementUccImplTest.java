package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeAmenagementUccImplTest {
  private BizzFactory factory;
  private TypeAmenagementUcc ucc;

  @BeforeEach
  public void setUp() throws Exception {
    InjectionService is = new InjectionService("configuration/test.properties");
    DalServices dalServ = (DalServices) is.getDependency("DalServices", is);
    factory = (BizzFactory) is.getDependency("BizzFactory");
    TypeAmenagementDao dao =
        (TypeAmenagementDao) is.getDependency("TypeAmenagementDao", dalServ, factory);
    ucc = (TypeAmenagementUcc) is.getDependency("TypeAmenagementUcc", dao, dalServ);
  }

  @DisplayName("Constructeur null")
  @Test
  void testTypeAmenagementUccImpl() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new TypeAmenagementUccImpl(null, null));
  }

  @DisplayName("Inserer type amenagement parametre null")
  @Test
  void testInsererTypeAmenagement1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.insererTypeAmenagement(null));
  }

  @DisplayName("Inserer type amenagement correct")
  @Test
  void testInsererTypeAmenagement2() {
    Assertions.assertNotNull(ucc.insererTypeAmenagement(factory.getTypeAmenagement()));
  }

  @DisplayName("Recuperer type amenagement correct")
  @Test
  void testRecupererTypesAmenagement1() {
    Assertions.assertNotNull(ucc.recupererTypesAmenagement());
  }

}
