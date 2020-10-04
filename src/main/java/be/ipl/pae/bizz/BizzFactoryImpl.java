package be.ipl.pae.bizz;

import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.TypeAmenagementDto;

class BizzFactoryImpl implements BizzFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public InscritDto getInscrit() {
    return new InscritImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientDto getClient() {
    return new ClientImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DevisDto getDevis() {
    return new DevisImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoDto getPhoto() {
    return new PhotoImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeAmenagementDto getTypeAmenagement() {
    return new TypeAmenagementImpl();
  }

}
