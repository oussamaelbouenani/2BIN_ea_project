package be.ipl.pae.mock.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.exception.FatalException;
import be.ipl.pae.persistance.dal.RequetesSql;
import be.ipl.pae.persistance.dao.InscritDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockInscritDaoImpl implements InscritDao {

  private BizzFactory factory;

  public MockInscritDaoImpl(RequetesSql rs, BizzFactory factory) {
    this.factory = factory;
  }

  @Override
  public InscritDto recupererInscrit(String email) {
    if ("a@b.c".equals(email) || "c@b.a".equals(email)) {
      InscritDto inscrit = factory.getInscrit();
      if ("a@b.c".equals(email)) {
        inscrit.setIdInscrit(1);
        inscrit.setNom("uyar");
        inscrit.setPrenom("bera");
        inscrit.setPseudo("itche");
        inscrit.setEmail("a@b.c");
        inscrit.setVille("brux");
        inscrit.setDateInscription(LocalDate.of(2020, 03, 9));
        inscrit.setOuvrier(false);
        inscrit.setValide(true);
        inscrit.setMdp("$2a$10$2yBn/mEFcln3zN2jEIEVTOxunnCk6Oa2pdfR3jX3bBvfuCJzglEI2");
      } else {
        inscrit.setIdInscrit(2);
        inscrit.setNom("elboue");
        inscrit.setPrenom("ouss");
        inscrit.setPseudo("saiya");
        inscrit.setEmail("c@b.a");
        inscrit.setVille("Bruxelles");
        inscrit.setDateInscription(LocalDate.of(2020, 03, 16));
        inscrit.setOuvrier(false);
        inscrit.setValide(true);
        inscrit.setMdp("$2a$10$k70Tekt8LzyyUZIVrk1SHe2o2GImV5qhjqng0aHYargnQm6FqPV62");
      }
      return inscrit;
    }
    return null;
  }

  @Override
  public InscritDto recupererInscrit(int id) {
    if (id < 1) {
      throw new FatalException();
    }
    InscritDto inscrit = factory.getInscrit();
    inscrit.setNom("test");
    return inscrit;
  }

  @Override
  public InscritDto insererInscrit(InscritDto inscritDto) {
    String email = inscritDto.getEmail();
    return recupererInscrit(email);
  }

  @Override
  public List<InscritDto> recupererInscrits(String mot) {
    return new ArrayList<InscritDto>();
  }

  @Override
  public InscritDto validerInscrit(String email, String estOuvrier) {
    return recupererInscrit(email);
  }

  @Override
  public InscritDto lierInscritClient(String email) {
    return recupererInscrit(email);
  }

}
