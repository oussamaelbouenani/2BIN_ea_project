package be.ipl.pae.mock.persistance.dao;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.persistance.dal.RequetesSql;
import be.ipl.pae.persistance.dao.DevisDao;
import be.ipl.pae.persistance.dao.TypeAmenagementDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockDevisDaoImpl implements DevisDao {
  private BizzFactory factory;

  public MockDevisDaoImpl(RequetesSql rs, BizzFactory factory, TypeAmenagementDao typeDao) {
    this.factory = factory;
  }

  @Override
  public DevisDto insererDevis(DevisDto devisDto) {
    return devisDto;
  }

  @Override
  public boolean insererDevisTypesAmenagement(int[] id) {
    return true;
  }

  @Override
  public List<DevisDto> recupererMesDevis(int id, LocalDate date, int nombre1, int nombre2,
      int typeAmen) {
    return new ArrayList<DevisDto>();
  }

  @Override
  public List<DevisDto> recupererLesDevis(String mot, LocalDate date, int nombre1, int nombre2,
      int idAmen) {
    return new ArrayList<DevisDto>();
  }

  @Override
  public DevisDto recupererUnDevis(int idDevis) {
    return factory.getDevis();
  }

  @Override
  public DevisDto changerEtatDevis(int idDevis, String etat, String date) {
    return factory.getDevis();
  }

}
