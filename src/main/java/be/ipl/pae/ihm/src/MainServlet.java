package be.ipl.pae.ihm.src;

import be.ipl.pae.bizz.BizzFactory;
import be.ipl.pae.bizz.dto.ClientDto;
import be.ipl.pae.bizz.dto.DevisDto;
import be.ipl.pae.bizz.dto.InscritDto;
import be.ipl.pae.bizz.dto.PhotoDto;
import be.ipl.pae.bizz.dto.TypeAmenagementDto;
import be.ipl.pae.bizz.ucc.ClientUcc;
import be.ipl.pae.bizz.ucc.DevisUcc;
import be.ipl.pae.bizz.ucc.InscritUcc;
import be.ipl.pae.bizz.ucc.PhotoUcc;
import be.ipl.pae.bizz.ucc.TypeAmenagementUcc;
import be.ipl.pae.exception.BizzException;
import be.ipl.pae.exception.FatalException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.annotation.JsonDateFormat;
import com.owlike.genson.convert.ContextualFactory;
import com.owlike.genson.reflect.BeanProperty;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.servlet.DefaultServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class MainServlet extends DefaultServlet {
  private final InscritUcc inscritUcc;
  private final DevisUcc devisUcc;
  private final PhotoUcc photoUcc;
  private final TypeAmenagementUcc typeUcc;
  private final ClientUcc clientUcc;
  private final BizzFactory factory;
  private final Genson genson;
  private final Algorithm algo;

  /**
   * Constructeur qui initialise les attributs
   * 
   * @param les attributs à initialiser
   */
  public MainServlet(String secretJ, BizzFactory factory, InscritUcc inscritUcc, DevisUcc devisUcc,
      PhotoUcc photoUcc, TypeAmenagementUcc typeUcc, ClientUcc clientUcc) {
    this.inscritUcc = inscritUcc;
    this.devisUcc = devisUcc;
    this.photoUcc = photoUcc;
    this.typeUcc = typeUcc;
    this.clientUcc = clientUcc;
    this.factory = factory;
    this.algo = Algorithm.HMAC256(secretJ);
    this.genson =
        new GensonBuilder().useDateFormat(new SimpleDateFormat("yyyy-MM-dd")).useIndentation(true)
            .useConstructorWithArguments(true).withContextualFactory(new DateFactory()).create();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String folder = "www/";
    resp.setCharacterEncoding("UTF-8");
    PrintWriter writer = resp.getWriter();
    if ("/".equals(req.getRequestURI())) {
      String[] chemin = {"header", "profil", "index", "login", "devis", "adminList", "devisDetails",
          "monDevisDetaille", "mesDevis", "types", "bottom", "footer"};
      for (String s : chemin) {
        writer.println(new String(
            Files
                .readAllBytes(Paths.get(folder + ("index".equals(s) ? "" : "html/") + s + ".html")),
            StandardCharsets.UTF_8));
      }

    } else {
      super.doGet(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("UTF-8");
    String action = req.getParameter("action");
    System.out.println("DEBUG ACTION " + action);
    Object result = null;
    boolean actionTraitee = true;

    try {
      switch (action) {
        case "login":
          InscritDto dto = login(req, resp);
          result = genson.serialize(dto);
          break;
        case "estConnecte":
          result = genson.serialize(estConnecte(req));
          break;
        case "inscrire":
          result = genson.serialize(inscrire(req));
          break;
        case "recupererTypes":
          result = genson.serialize(typeUcc.recupererTypesAmenagement());
          break;
        case "recupererPhotosByType":
          result = genson.serialize(recupererPhotosByType());
          break;
        default:
          actionTraitee = false;
      }
      if (!actionTraitee) {
        actionTraitee = true;
        InscritDto inscritDto = estConnecte(req);
        if (inscritDto == null) {
          resp.setStatus(HttpStatus.UNAUTHORIZED_401);
          resp.getWriter().print("Utilisateur non connecté");
        }
        if ("seDeconnecter".equals(action)) {
          seDeconnecter(req, resp);
          result = true;
        } else {
          actionTraitee = false;
        }
        if (!actionTraitee && !inscritDto.getOuvrier()) {
          switch (action) {
            case "recupererMesDevis":
              result = genson.serialize(recupererMesDevis(req));
              break;
            case "recupererMonDevis":
              result = genson.serialize(recupererMonDevis(req));
              break;
            case "recupererMaPhotoFav":
              result = genson.serialize(recupererMaPhotoFav(req));
              break;
            default:
              actionTraitee = false;
          }
        }
        if (!actionTraitee) {
          actionTraitee = true;
          if (inscritDto.getOuvrier()) {
            switch (action) {
              case "recupererClients":
                result = genson.serialize(recupererClients(req));
                break;
              case "insererDevis":
                result = genson.serialize(insererDevis(req));
                break;
              case "recupererUnDevis":
                result = genson.serialize(recupererUnDevis(req));
                break;
              case "recupererLesDevis":
                result = genson.serialize(recupererLesDevis(req));
                break;
              case "recupererLesPhotosFav":
                result = genson.serialize(recupererLesPhotosFav(req));
                break;
              case "recupererInscrits":
                result = genson.serialize(recupererInscrits(req));
                break;
              case "insererClient":
                result = genson.serialize(insererClient(req));
                break;
              case "validerInscrit":
                result = genson.serialize(validerInscrit(req));
                break;
              case "lierClientInscrit":
                result = genson.serialize(lierClientInscrit(req));
                break;
              case "lierInscritClient":
                result = genson.serialize(lierInscritClient(req));
                break;
              case "ajouterPhotosFin":
                result = genson.serialize(ajouterPhotosFin(req));
                break;
              case "insererType":
                result = genson.serialize(insererType(req));
                break;
              case "changerEtat":
                result = genson.serialize(changerEtatDevis(req));
                break;
              default:
                actionTraitee = false;
            }
          }
        }
      }
      if (!actionTraitee) {
        resp.setStatus(HttpStatus.BAD_REQUEST_400);
        resp.getWriter().print("Action inconnue !");
        return;
      }
      if (Objects.nonNull(result)) {
        resp.setStatus(HttpStatus.OK_200);
        resp.setContentType("application/json");
        resp.getWriter().print(result);
      }
    } catch (BizzException exception) {
      resp.setStatus(HttpStatus.UNPROCESSABLE_ENTITY_422);
      resp.getWriter().print(exception.getMessage());
    } catch (Exception exception) {
      resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
      resp.getWriter().print(exception.getMessage());
    }
  }

  // *********************** //
  // *** Méthodes quidam *** //
  // *********************** //

  private List<PhotoDto> recupererPhotosByType() {
    List<TypeAmenagementDto> liste = typeUcc.recupererTypesAmenagement();
    List<PhotoDto> listePhoto = new ArrayList<>();
    for (TypeAmenagementDto type : liste) {
      PhotoDto photo = photoUcc.recupererPhotoByTypes(type.getIdTypeAmenagement());
      if (photo != null) {
        listePhoto.add(photo);
      }
    }
    return listePhoto;
  }

  private InscritDto inscrire(HttpServletRequest req) {
    String inscription = req.getParameter("inscription");
    InscritDto inscritDto = genson.deserializeInto(inscription, factory.getInscrit());
    System.out.println("Servlet " + inscritDto);
    return inscritUcc.insererInscrit(inscritDto);
  }

  private InscritDto login(HttpServletRequest req, HttpServletResponse resp) {
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");
    InscritDto inscritDto = inscritUcc.seConnecter(email, mdp);
    if (Objects.nonNull(inscritDto)) {
      if (!inscritDto.getValide()) {
        return inscritDto;
      } else {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("sessionUtilisateur", inscritDto);
        creerCookies(resp, inscritDto);
        inscritDto.setMdp(null);

      }
    }
    inscritDto.setMdp(null);
    return inscritDto;
  }

  private InscritDto estConnecte(HttpServletRequest req) {
    HttpSession session = req.getSession();
    InscritDto inscritDto = (InscritDto) session.getAttribute("sessionUtilisateur");
    if (inscritDto == null) {
      Cookie[] cookies = req.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("authentificationUtil".equals(cookie.getName())) {
            String token = cookie.getValue();
            try {
              DecodedJWT decode = JWT.decode(token);
              String email = decode.getClaim("email").asString();
              inscritDto = inscritUcc.recupererInscrit(email);
              session.setAttribute("sessionUtilisateur", inscritDto);
              System.out.println(inscritDto.getEmail());
              return inscritDto;
            } catch (Exception exception) {
              throw new FatalException("Erreur dans isConnected");
            }
          }
        }
      }
    }
    return inscritDto;
  }

  // *********************** //
  // ** Méthodes connecté ** //
  // *********************** //

  private void seDeconnecter(HttpServletRequest req, HttpServletResponse resp) {
    Cookie cookie = new Cookie("authentificationUtil", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    resp.addCookie(cookie);
    HttpSession session = req.getSession(false);
    if (session != null) {
      session.invalidate();
    }
  }

  private Object recupererMonDevis(HttpServletRequest req) {
    String idDevis = req.getParameter("id");
    DevisDto devis = devisUcc.recupererUnDevis(Integer.parseInt(idDevis));

    InscritDto id = (InscritDto) req.getSession().getAttribute("sessionUtilisateur");
    if (id.getIdClient() != devis.getIdClient()) {
      throw new BizzException("Devis non accessible");
    }

    System.out.println(devis);
    return devis;
  }

  private List<PhotoDto> recupererMaPhotoFav(HttpServletRequest req) {

    List<DevisDto> listeDevis = recupererMesDevis(req);
    List<Integer> listeIdPhoto = new ArrayList<>();

    return getPhotoDtos(listeDevis, listeIdPhoto);
  }

  private List<PhotoDto> getPhotoDtos(List<DevisDto> listeDevis, List<Integer> listeIdPhoto) {
    for (DevisDto dd : listeDevis) {
      if (dd.getIdPhotoFav() != 0) {
        listeIdPhoto.add(dd.getIdPhotoFav());
      }
    }
    return photoUcc.recupererPhoto(listeIdPhoto);
  }

  private List<DevisDto> recupererMesDevis(HttpServletRequest req) {
    String reqDate = req.getParameter("date");
    if (reqDate == null || reqDate.equals("")) {
      reqDate = LocalDate.MIN.toString();
    }
    String reqNombre1 = req.getParameter("nombre1");
    if (reqNombre1 == null || reqNombre1.equals("")) {
      reqNombre1 = "0";
    }
    String reqNombre2 = req.getParameter("nombre2");
    if (reqNombre2 == null || reqNombre2.equals("")) {
      reqNombre2 = "9999999";
    }
    String reqTypeAmen = req.getParameter("typeAmen");
    if (reqTypeAmen == null) {
      reqTypeAmen = "0";
    }
    InscritDto id = (InscritDto) req.getSession().getAttribute("sessionUtilisateur");
    return devisUcc.recupererMesDevis(id.getIdClient(), LocalDate.parse(reqDate),
        Integer.parseInt(reqNombre1), Integer.parseInt(reqNombre2), Integer.parseInt(reqTypeAmen));
  }

  // *********************** //
  // *** Méthodes admins *** //
  // *********************** //

  private TypeAmenagementDto insererType(HttpServletRequest req) {

    TypeAmenagementDto typeAmenagementDto =
        genson.deserializeInto(req.getParameter("type"), factory.getTypeAmenagement());

    return typeUcc.insererTypeAmenagement(typeAmenagementDto);
  }

  private List<PhotoDto> ajouterPhotosFin(HttpServletRequest request) {
    List<String> list = new Genson().deserialize(request.getParameter("photos"),
        new GenericType<ArrayList<String>>() {});
    int idType = genson.deserializeInto(request.getParameter("idType"), factory.getPhoto())
        .getIdTypeAmenagement();
    boolean visible = Boolean.parseBoolean(request.getParameter("visible"));
    int idDevis = Integer.parseInt(request.getParameter("idDevis"));
    List<PhotoDto> listeRetour = new ArrayList<>();

    for (String s : list) {
      PhotoDto photoDto = factory.getPhoto();
      photoDto.setPhoto(s);
      photoDto.setIdTypeAmenagement(idType);
      photoDto.setIdDevis(idDevis);
      photoDto.setVisible(visible);
      PhotoDto photoRetour = photoUcc.insererPhoto(photoDto);
      photoDto.setIdPhoto(photoRetour.getIdPhoto());
      listeRetour.add(photoDto);
    }
    return listeRetour;
  }

  private DevisDto recupererUnDevis(HttpServletRequest req) {
    String idDevis = req.getParameter("id");
    return devisUcc.recupererUnDevis(Integer.parseInt(idDevis));
  }

  private List<PhotoDto> recupererLesPhotosFav(HttpServletRequest req) {

    List<DevisDto> listeDevis = recupererLesDevis(req);
    List<Integer> listeIdPhoto = new ArrayList<>();

    return getPhotoDtos(listeDevis, listeIdPhoto);
  }

  private List<DevisDto> recupererLesDevis(HttpServletRequest req) {

    String reqMot = req.getParameter("mot");
    if (reqMot == null || reqMot.equals("")) {
      reqMot = "";
    }
    String reqDate = req.getParameter("date");
    if (reqDate == null || reqDate.equals("")) {
      reqDate = LocalDate.MIN.toString();
    }
    String reqNombre1 = req.getParameter("nombre1");
    if (reqNombre1 == null || reqNombre1.equals("")) {
      reqNombre1 = "0";
    }
    String reqNombre2 = req.getParameter("nombre2");
    if (reqNombre2 == null || reqNombre2.equals("")) {
      reqNombre2 = "9999999";
    }
    String reqIdAmen = req.getParameter("idAmen");
    if (reqIdAmen == null) {
      reqIdAmen = "0";
    }
    return devisUcc.recupererLesDevis(reqMot, LocalDate.parse(reqDate),
        Integer.parseInt(reqNombre1), Integer.parseInt(reqNombre2), Integer.parseInt(reqIdAmen));
  }

  private List<ClientDto> recupererClients(HttpServletRequest req) {
    String mot = req.getParameter("mot");
    if (mot == null) {
      mot = "";
    }
    return clientUcc.recupererClients(mot);
  }

  private List<InscritDto> recupererInscrits(HttpServletRequest req) {
    String mot = req.getParameter("mot");
    if (mot == null) {
      mot = "";
    }
    return inscritUcc.recupererInscrits(mot);
  }

  private DevisDto insererDevis(HttpServletRequest request) {
    String types = request.getParameter("typesAmenagement");
    // 1 Extraction des id types amenagements du Json
    String[] tab = types.split("\\{*:*,*\"\\}*");
    int[] typeAmenIndice = new int[30];// creation d'un tableau de 30 pour 15types+ id_devis
    int indice = 0;
    for (String value : tab) {
      if (value.matches("[0-9]+")) {
        typeAmenIndice[indice] = Integer.parseInt(value);
        indice++;
      }
    }
    // 1 FIN
    String devis = request.getParameter("devis");
    DevisDto devisDto = genson.deserializeInto(devis, factory.getDevis());

    String client = request.getParameter("client");
    ClientDto clientDto = genson.deserializeInto(client, factory.getClient());

    devisDto.setIdClient(clientDto.getIdClient());
    devisDto = devisUcc.insererDevis(devisDto);

    // Insertion de l'id_devis à la derniere place du tableau
    typeAmenIndice[typeAmenIndice.length - 1] = devisDto.getIdDevis();

    // Insertion devisTypesAmenagement en base de données
    devisUcc.insererDevisTypesAmenagements(typeAmenIndice);

    List<String> list = new Genson().deserialize(request.getParameter("photos"),
        new GenericType<ArrayList<String>>() {});

    for (String s : list) {
      PhotoDto photoDto = factory.getPhoto();
      photoDto.setPhoto(s);
      photoDto.setIdDevis(devisDto.getIdDevis());
      photoUcc.insererPhoto(photoDto);
    }
    return devisDto;
  }

  private ClientDto insererClient(HttpServletRequest request) {
    String client = request.getParameter("client");
    ClientDto clientDto = genson.deserializeInto(client, factory.getClient());
    return clientUcc.insererClient(clientDto);
  }

  private ClientDto lierClientInscrit(HttpServletRequest request) {
    String client = request.getParameter("client");
    System.out.println(client);
    ClientDto clientDto = genson.deserializeInto(client, factory.getClient());
    System.out.println(clientDto);
    return clientUcc.lierClientInscrit(clientDto);
  }

  private InscritDto lierInscritClient(HttpServletRequest request) {
    String email = request.getParameter("email");
    System.out.println(email);
    return inscritUcc.lierInscritClient(email);
  }

  private InscritDto validerInscrit(HttpServletRequest req) {
    String email = req.getParameter("email");
    String estOuvrier = req.getParameter("ouvrier");
    System.out.println("email " + email + " ouvrier " + estOuvrier);
    return inscritUcc.validerInscrit(email, estOuvrier);
  }

  private DevisDto changerEtatDevis(HttpServletRequest req) {
    String etat = req.getParameter("etat");
    int idDevis = Integer.parseInt(req.getParameter("idDevis"));
    String date = req.getParameter("dateTravaux");
    if (date == null) {
      date = "";
    }
    DevisDto dev = devisUcc.changerEtatDevis(idDevis, etat, date);
    return dev;
  }

  // *********************** //
  // ***** Utilitaires ***** //
  // *********************** //

  private void creerCookies(HttpServletResponse resp, InscritDto inscritDto) {
    Map<String, Object> infosCookie = new HashMap<>();
    infosCookie.put("email", inscritDto.getEmail());
    String token = JWT.create().withClaim("email", inscritDto.getEmail()).sign(algo);
    Cookie cookie = new Cookie("authentificationUtil", token);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24);
    resp.addCookie(cookie);
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  private static class DateFactory implements ContextualFactory<LocalDate> {
    @Override
    public Converter<LocalDate> create(BeanProperty property, Genson genson) {
      JsonDateFormat jsonDateFormat = property.getAnnotation(JsonDateFormat.class);
      if (jsonDateFormat != null) {
        return new DateConverter();
      }
      return null;
    }
  }

  private static class DateConverter implements Converter<LocalDate> {
    @Override
    public LocalDate deserialize(ObjectReader reader, Context ctx) {
      LocalDate date;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        date = LocalDate.parse(reader.valueAsString(), formatter);
      } catch (DateTimeParseException exception) {
        throw new BizzException("Erreur de date");
      }
      return date;
    }

    @Override
    public void serialize(LocalDate object, ObjectWriter writer, Context ctx) {
      writer.writeString(object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
  }

}
