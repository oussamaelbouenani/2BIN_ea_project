package be.ipl.pae.config;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class InjectionService {

  private static final Properties props = new Properties();

  /**
   * Recupere le fichier en parametre.
   * 
   * @param path
   */
  public InjectionService(String path) {
    try {
      FileInputStream fis = new FileInputStream(path);
      props.load(fis);
      fis.close();
    } catch (Throwable exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Cherche la proriété via la clé.
   * 
   * @param cle
   * @return
   */
  public String getValue(String cle) {
    return props.getProperty(cle);
  }

  /**
   * Creer une instance de la classe passé en parametre avec 0 ou pls parametres.
   * 
   * @param nomClasse
   * @param params
   * @return
   */
  public Object getDependency(String nomClasse, Object... params) {
    try {
      Class<?> classe = Class.forName(getValue(nomClasse));
      for (Constructor<?> constructeur : classe.getDeclaredConstructors()) {
        boolean ok = true;
        constructeur.setAccessible(true);
        Class<?>[] paramsConstruct = constructeur.getParameterTypes();
        if (params.length != paramsConstruct.length) {
          continue;
        }
        for (int i = 0; i < params.length; i++) {
          if (!paramsConstruct[i].isAssignableFrom(params[i].getClass())) {
            ok = false;
            break;
          }
        }
        if (ok) {
          return constructeur.newInstance(params);
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

}
