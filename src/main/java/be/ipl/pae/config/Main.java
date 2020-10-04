package be.ipl.pae.config;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.bizz.ucc.InscritUcc;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.bizz.ucc.TypeAmenagementUcc;
import be.ipl.pae.ihm.src.MainServlet;
import be.ipl.pae.persistance.dal.DalServices;
import be.ipl.pae.persistance.dao.ClientDao;
import be.ipl.pae.persistance.dao.DevisDao;
import be.ipl.pae.persistance.dao.InscritDao;
import be.ipl.pae.persistance.dao.PhotoDao;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

  /**
   * Demarrage de l'application.
   * 
   * @param args arguments.
   * @throws Exception s
   */
  public static void main(String[] args) throws Exception {
    InjectionService inj = new InjectionService("configuration/prod.properties");

    DalServices dalServ = (DalServices) inj.getDependency("DalServices", inj);

    BizzFactory bizzFactory = (BizzFactory) inj.getDependency("BizzFactory");

    /// ** DAO **/
    InscritDao inscritDao = (InscritDao) inj.getDependency("InscritDao", dalServ, bizzFactory);
    TypeAmenagementDao typeDao =
        (TypeAmenagementDao) inj.getDependency("TypeAmenagementDao", dalServ, bizzFactory);
    DevisDao devisDao = (DevisDao) inj.getDependency("DevisDao", dalServ, bizzFactory, typeDao);
    PhotoDao photoDao = (PhotoDao) inj.getDependency("PhotoDao", dalServ, bizzFactory);
    ClientDao clientDao = (ClientDao) inj.getDependency("ClientDao", dalServ, bizzFactory);

    /// ** UCC **/
    InscritUcc inscritUcc = (InscritUcc) inj.getDependency("InscritUcc", inscritDao, dalServ);
    DevisUcc devisUcc = (DevisUcc) inj.getDependency("DevisUcc", devisDao, dalServ);
    PhotoUcc photoUcc = (PhotoUcc) inj.getDependency("PhotoUcc", photoDao, dalServ);
    TypeAmenagementUcc typeUcc =
        (TypeAmenagementUcc) inj.getDependency("TypeAmenagementUcc", typeDao, dalServ);
    ClientUcc clientUcc = (ClientUcc) inj.getDependency("ClientUcc", clientDao, dalServ);

    startServer(new MainServlet(inj.getValue("JwtSecret"), bizzFactory, inscritUcc, devisUcc,
        photoUcc, typeUcc, clientUcc), inj);
  }

  private static void startServer(MainServlet mainServlet, InjectionService inj) throws Exception {

    WebAppContext context = new WebAppContext();

    context.addServlet(new ServletHolder(mainServlet), "/");
    context.setResourceBase("www");
    context.setWelcomeFiles(new String[] {"index.html"});
    context.setMaxFormContentSize(10000000);
    context.setMaxFormKeys(10000000);
    Server server = new Server(Integer.parseInt(inj.getValue("port")));
    server.setHandler(context);
    server.start();
  }

}
