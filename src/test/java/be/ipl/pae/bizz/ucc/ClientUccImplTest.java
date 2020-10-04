package be.ipl.pae.bizz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClientUccImplTest {

  private BizzFactory factory;
  private ClientUcc ucc;

  @BeforeEach
  public void setUp() throws Exception {
    InjectionService is = new InjectionService("configuration/test.properties");
    DalServices dalServ = (DalServices) is.getDependency("DalServices", is);
    factory = (BizzFactory) is.getDependency("BizzFactory");
    ClientDao dao = (ClientDao) is.getDependency("ClientDao", dalServ, factory);
    ucc = (ClientUcc) is.getDependency("ClientUcc", dao, dalServ);
  }

  @DisplayName("Constructeur null")
  @Test
  void testClientUccImpl() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> new ClientUccImpl(null, null));
  }

  @DisplayName("Inserer null")
  @Test
  void testInsererClient1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.insererClient(null));
  }

  @DisplayName("Inserer un client deja inscrit")
  @Test
  void testInsererClient2() {
    ClientDto client = ucc.recupererClient(1); // a@b.c
    Assertions.assertThrows(BizzException.class, () -> ucc.insererClient(client));
  }

  @DisplayName("Inserer un client correct")
  @Test
  void testInsererClient3() {
    ClientDto client = factory.getClient();
    client.setEmail("g@g.d");
    Assertions.assertNotNull(ucc.insererClient(client));
  }

  @DisplayName("Lier client null")
  @Test
  void testLierClientInscrit1() {
    Assertions.assertThrows(BizzException.class, () -> ucc.lierClientInscrit(null));
  }

  @DisplayName("Lier client email incorrect")
  @Test
  void testLierClientInscrit2() {
    ClientDto clientDto = factory.getClient();
    clientDto.setEmail("g@g.d");
    Assertions.assertThrows(FatalException.class, () -> ucc.lierClientInscrit(clientDto));
  }

  @DisplayName("Lier client email correct")
  @Test
  void testLierClientInscrit3() {
    ClientDto clientDto = ucc.recupererClient(1);
    Assertions.assertNotNull(ucc.lierClientInscrit(clientDto));
  }

  @DisplayName("Recuperer liste de clients mot correct")
  @Test
  void testRecupererClients1() {
    assertNotNull(ucc.recupererClients("a"));
  }

  @DisplayName("Recuperer liste de clients mot null")
  @Test
  void testRecupererClients2() {
    assertThrows(BizzException.class, () -> ucc.recupererClients(null));
  }

  @DisplayName("Recuperer Client id negatif")
  @Test
  void testRecupererClient1() {
    assertThrows(BizzException.class, () -> ucc.recupererClient(-1));
  }

  @DisplayName("Recuperer Client id null")
  @Test
  void testRecupererClient2() {
    assertThrows(BizzException.class, () -> ucc.recupererClient(0));
  }

  @DisplayName("Recuperer Client id correct")
  @Test
  void testRecupererClient3() {
    assertNotNull(ucc.recupererClient(1));
  }
}
