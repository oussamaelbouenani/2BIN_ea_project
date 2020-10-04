package be.ipl.pae.bizz.ucc;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.InscritDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InscritUccImplTest {
  private BizzFactory factory;
  private InscritUcc ucc;

  @BeforeEach
  public void setUp() throws Exception {
    InjectionService is = new InjectionService("configuration/test.properties");
    DalServices dalServ = (DalServices) is.getDependency("DalServices", is);
    factory = (BizzFactory) is.getDependency("BizzFactory");
    InscritDao dao = (InscritDao) is.getDependency("InscritDao", dalServ, factory);
    ucc = (InscritUcc) is.getDependency("InscritUcc", dao, dalServ);
  }

  @DisplayName("Constructeur null")
  @Test
  void testInscrittUccImpl() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new InscritUccImpl(null, null));
  }

  @DisplayName("L'utilisateur et le mdp sont incorrect")
  @Test
  public void testSeConnecter1() {
    Assertions.assertThrows(BizzException.class,
        () -> ucc.seConnecter("emailTest@hello.com", "mdpTest"));
  }

  @DisplayName("L'utilisateur null et le mdp correct")
  @Test
  public void testSeConnecter2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.seConnecter(null, "mdpTest"));
  }

  @DisplayName("L'utilisateur correct et le mdp null")
  @Test
  public void testSeConnecter3() {
    Assertions.assertThrows(BizzException.class, () -> ucc.seConnecter("a@b.c", null));
  }

  @DisplayName("Email correct")
  @Test
  public void testRecupererInscrit1() {
    Assertions.assertNotNull(ucc.recupererInscrit("a@b.c"));
  }

  @DisplayName("L'utilisateur n'existe pas")
  @Test
  public void testRecupererInscrit2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererInscrit("a@a.a"));
  }

  @DisplayName("Email null")
  @Test
  public void testRecupererInscrit3() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererInscrit(null));
  }

  @DisplayName("Recuperer liste inscrits Mot null")
  @Test
  public void testRecupererInscrits1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.recupererInscrits(null));
  }

  @DisplayName("Recuperer liste inscrits Mot correct")
  @Test
  public void testRecupererInscrits2() {
    Assertions.assertNotNull(ucc.recupererInscrits("a"));
  }

  @DisplayName("Inserer un inscrit a NULL")
  @Test
  public void testInsererInscrit1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.insererInscrit(null));
  }

  @DisplayName("Inserer un inscrit dont le mail existe deja")
  @Test
  public void testInsererInscrit2() {
    InscritDto inscrit = ucc.recupererInscrit("a@b.c");
    Assertions.assertThrows(BizzException.class, () -> ucc.insererInscrit(inscrit));
  }

  @DisplayName("Inserer un inscrit correct")
  @Test
  public void testInsererInscrit3() {
    InscritDto inscrit = factory.getInscrit();
    inscrit.setEmail("g@g.d");
    inscrit.setMdp("testtest");
    Assertions.assertNull(ucc.insererInscrit(inscrit));
  }

  @DisplayName("Valider un inscrit correct")
  @Test
  public void testValiderInscrit1() {
    Assertions.assertNotNull(ucc.validerInscrit("a@b.c", "false"));
  }

  @DisplayName("Valider un inscrit email null")
  @Test
  public void testValiderInscrit2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.validerInscrit(null, "false"));
  }

  @DisplayName("Valider un inscrit email correct et estOuvrier null")
  @Test
  public void testValiderInscrit3() {
    Assertions.assertThrows(BizzException.class, () -> ucc.validerInscrit("a@b.c", null));
  }

  @DisplayName("Lier un inscrit avec un email correct")
  @Test
  public void testLierInscrit1() {
    Assertions.assertNotNull(ucc.lierInscritClient("a@b.c"));
  }

  @DisplayName("Lier un inscrit avec un email null")
  @Test
  public void testLierInscrit2() {
    Assertions.assertThrows(BizzException.class, () -> ucc.lierInscritClient(null));
  }

}
