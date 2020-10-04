package be.ipl.pae.bizz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.DevisDao;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DevisUccImplTest {
  private BizzFactory factory;
  private DevisUcc ucc;

  @BeforeEach
  public void setUp() throws Exception {
    InjectionService is = new InjectionService("configuration/test.properties");
    DalServices dalServ = (DalServices) is.getDependency("DalServices", is);
    factory = (BizzFactory) is.getDependency("BizzFactory");
    TypeAmenagementDao typeDao =
        (TypeAmenagementDao) is.getDependency("TypeAmenagementDao", dalServ, factory);
    DevisDao dao = (DevisDao) is.getDependency("DevisDao", dalServ, factory, typeDao);
    ucc = (DevisUcc) is.getDependency("DevisUcc", dao, dalServ);
  }

  @DisplayName("Constructeur null")
  @Test
  void testDevisUccImpl() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new DevisUccImpl(null, null));
  }

  @DisplayName("Inserer devis null")
  @Test
  void testInsererDevis1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.insererDevis(null));
  }

  @DisplayName("Inserer devis correct")
  @Test
  void testInsererDevis2() {
    DevisDto devisDto = factory.getDevis();
    Assertions.assertNotNull(ucc.insererDevis(devisDto));
  }

  @DisplayName("Inserer devisTypes tab length=0")
  @Test
  void testInsererDevisTypesAmenagements1() {
    int[] tab = new int[0];
    assertThrows(BizzException.class, () -> ucc.insererDevisTypesAmenagements(tab));
  }

  @DisplayName("Inserer devisTypes tab aucun element")
  @Test
  void testInsererDevisTypesAmenagements2() {
    int[] tab = new int[10];
    assertThrows(BizzException.class, () -> ucc.insererDevisTypesAmenagements(tab));
  }

  @DisplayName("Inserer devisTypes tab correct")
  @Test
  void testInsererDevisTypesAmenagements3() {
    int[] tab = new int[10];
    tab[0] = 1;
    assertTrue(ucc.insererDevisTypesAmenagements(tab));
  }

  @DisplayName("Recuperer Mes devis parametre id client incorrect")
  @Test
  void testRecupererMesDevis1() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererMesDevis(0, LocalDate.parse("2020-12-10"), 8, 10, 1));
  }

  @DisplayName("Recuperer Mes devis parametre date incorrect")
  @Test
  void testRecupererMesDevis2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererMesDevis(2, null, 8, 10, 1));
  }

  @DisplayName("Recuperer Mes devis parametre nombre1<>nombre2 incorrect")
  @Test
  void testRecupererMesDevis3() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererMesDevis(2, LocalDate.parse("2020-12-10"), 15, 10, 1));
  }

  @DisplayName("Recuperer Mes devis parametre idTypeAmen incorrect")
  @Test
  void testRecupererMesDevis4() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererMesDevis(2, LocalDate.parse("2020-12-10"), 1, 10, -1));
  }

  @DisplayName("Recuperer Mes devis parametres correct")
  @Test
  void testRecupererMesDevis5() {
    Assertions.assertNotNull(ucc.recupererMesDevis(2, LocalDate.parse("2020-12-10"), 1, 10, 0));
  }


  @DisplayName("Recuperer Les devis parametre mot incorrect")
  @Test
  void testRecupererLesDevis1() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererLesDevis(null, LocalDate.parse("2020-12-10"), 8, 10, 1));
  }

  @DisplayName("Recuperer Les devis parametre date incorrect")
  @Test
  void testRecupererLesDevis2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererLesDevis("", null, 8, 10, 1));
  }

  @DisplayName("Recuperer Les devis parametre nombre1<>nombre2 incorrect")
  @Test
  void testRecupererLesDevis3() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererLesDevis("", LocalDate.parse("2020-12-10"), 15, 10, 1));
  }

  @DisplayName("Recuperer Les devis parametre idTypeAmen incorrect")
  @Test
  void testRecupererLesDevis4() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.recupererLesDevis("", LocalDate.parse("2020-12-10"), 1, 10, -1));
  }

  @DisplayName("Recuperer Les devis parametres correct")
  @Test
  void testRecupererLesDevis5() {
    Assertions.assertNotNull(ucc.recupererLesDevis("", LocalDate.parse("2020-12-10"), 1, 10, 0));
  }

  @DisplayName("Recuperer Un devis id incorrect")
  @Test
  void testRecupererUnDevis1() {
    assertThrows(BizzException.class, () -> ucc.recupererUnDevis(0));
  }

  @DisplayName("Recuperer Un devis id incorrect")
  @Test
  void testRecupererUnDevis2() {
    assertThrows(BizzException.class, () -> ucc.recupererUnDevis(-1));
  }

  @DisplayName("Recuperer Mes devis parametres correct")
  @Test
  void testRecupererUnDevis3() {
    assertNotNull(ucc.recupererUnDevis(1));
  }

}
