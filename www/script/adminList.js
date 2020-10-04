$(function () {
    let admin;
    $('#nav-recherches-admin').click(function () {
        setTimeout(() => {
            FrontEnd.visiblePage('adminList-view-page');    
        }, 1300);
        sessionStorage.setItem('page', 'adminList-view-page');
        admin = sessionStorage.getItem('admin');
        if (admin === "admin") {
            appelerToutesLesFonctionsAdminList();
        }
    });

    if (sessionStorage.getItem('page') ==='adminList-view-page' && window.PerformanceNavigation.TYPE_RELOAD == 1) {
        admin = sessionStorage.getItem('admin');
        if (admin === "admin") {
            appelerToutesLesFonctionsAdminList();
        }
    }

    $('#submit-recherche-les-devis').on("click", function () {
        let mot = $('#recherche-les-devis').val();
        let date = $('#recherche-les-devis-date').val();
        let nombre1 = $('#recherche-les-devis-nombre1').val();
        let nombre2 = $('#recherche-les-devis-nombre2').val();
        let typeAmen = $('#recherche-les-devis-type-amenagement option:selected').attr('id');
        console.log(typeAmen);
        recupererLesDevis(mot, date, nombre1, nombre2, typeAmen);
        recupererLesPhotosFav(mot, date, nombre1, nombre2, typeAmen);
    });

    $('#submit-recherche-inscrits').on("click", function () {
        let valeur = $('#recherche-inscrits').val();
        recupererInscrits(valeur);
    });

    $('#submit-recherche-clients').on("click", function () {
        let valeur = $('#recherche-clients').val();
        recupererClientsParMot(valeur);
    });

    $('#submit-reset-recherche-les-devis').on("click", function () {
        $('#recherche-les-devis').val("");
        $('#recherche-les-devis-date').val("");
        $('#recherche-les-devis-nombre1').val('');
        $('#recherche-les-devis-nombre2').val('');
        $('#recherche-les-devis-type-amenagement option:first').prop('selected', true);
        appelerToutesLesFonctionsAdminList();
    });

});

function remplireTypesAmenagements(liste) {
    let ptrSelect = $("select[id=recherche-les-devis-type-amenagement]");
    ptrSelect.html("");
    ptrSelect.append('<option name="recherche-les-devis-type-amenagement-option" id="0" >Tous</option>')
    for (let i = 0; i < liste.length; i++) {
        ptrSelect.append('<option name="recherche-les-devis-type-amenagement-option" id=' + liste[i].idTypeAmenagement + ' >' + liste[i].titre + '</option>');
    }
}

function recupererTypesAmenagementListe() {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            action: 'recupererTypes',
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var liste = JSON.parse(result);

            remplireTypesAmenagements(liste);

        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function remplirTableauLesDevis(objetDevis) {
    let pointeurTableau = $("#tbodyLesDevis");
    pointeurTableau.html("");
    for (let i = 0; i < objetDevis.length; i++) {
        pointeurTableau.append('<tr onclick="recupererIdDevisListe(this.id);" name="lesDevis" id=' + objetDevis[i].idDevis + '>');
        let pointeurTr = $('tr[name=lesDevis][id=' + objetDevis[i].idDevis + ']');
        let nomClient =$('#tbodyClients tr[id='+objetDevis[i].idClient+'] td[name=nom]').html();
        let prenomClient = $('#tbodyClients tr[id='+objetDevis[i].idClient+'] td[name=prenom]').html();
        pointeurTr.append('<td name="idClient" >' + nomClient+ ' '+ prenomClient+ '</td>');
        pointeurTr.append('<td name="dateDevis" >' + objetDevis[i].dateDevis + '</td>');
        pointeurTr.append('<td name="montantTotal" >' + objetDevis[i].montantTotal + ' €</td>');
        pointeurTr.append('<td name="dureeTravaux" >' + objetDevis[i].dureeTravaux + 'j</td>');
        pointeurTr.append('<td name="etat" >' + objetDevis[i].etat + '</td>');
        pointeurTr.append('<td name="dateDebutTravaux" >' + objetDevis[i].dateDebutTravaux + '</td>');
        pointeurTr.append('<td name="typesAmenagement" ></td>');
        pointeurTr.append('<td name="idPhotoFav"></td>');
    }
    remplirTableauLesDevisTypeAmenagements(objetDevis);
}

function remplirTableauLesDevisTypeAmenagements(objetTypeAmen){
    for(let i=0; i < objetTypeAmen.length ; i ++){
        let tab = {};
        tab = objetTypeAmen[i].listeTypeAmen;
        $('tr[id=' + objetTypeAmen[i].idDevis + '] td[name=typesAmenagement]').append(tab[0]);
        for(let j =1;  j < tab.length ; j++){
            $('tr[id=' + objetTypeAmen[i].idDevis + '] td[name=typesAmenagement]').append(' + '+tab[j]);
        }
    }
}

function remplirTableauLesPhotosFav(objetPhoto) {
    for (let i = 0; i < objetPhoto.length; i++) {
        $('#tbodyLesDevis tr[id=' + objetPhoto[i].idDevis + '] td[name=idPhotoFav]').html('<img name="photoFav" style="width: 200px; height: 130px;" src =' + objetPhoto[i].photo + '></img>');
    }
}

function recupererLesPhotosFav(mot, date, nombre1, nombre2, idAmen) {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            action: 'recupererLesPhotosFav',
            mot: mot,
            date: date,
            nombre1: nombre1,
            nombre2: nombre2,
            idAmen: idAmen,
        },
        success: function (lesPhotosFav) {
            var result = JSON.stringify(lesPhotosFav);
            var res = JSON.parse(result);
            remplirTableauLesPhotosFav(res);
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function recupererLesDevis(mot, date, nombre1, nombre2, idAmen) {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            action: 'recupererLesDevis',
            mot: mot,
            date: date,
            nombre1: nombre1,
            nombre2: nombre2,
            idAmen: idAmen,
        },
        success: function (lesDevis) {
            var result = JSON.stringify(lesDevis);
            var res = JSON.parse(result);
            if (res != null) {
                remplirTableauLesDevis(res);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function remplirTableauClients(data) {
    let pointeurTableau = $("#tbodyClients");
    pointeurTableau.html("");
    for (let i = 0; i < data.length; i++) {
        pointeurTableau.append('<tr name="client" id=' + data[i].idClient + ' >');
        let pointeurTr = $('tr[name=client][id=' + data[i].idClient + ']');
        pointeurTr.append('<td name="nom">' + data[i].nom + "</td>");
        pointeurTr.append('<td name="prenom">' + data[i].prenom + "</td>");
        pointeurTr.append('<td name="tel">' + data[i].telephone + "</td>");
        pointeurTr.append('<td name="email">' + data[i].email + "</td>");
        pointeurTr.append('<td name="codePostal">' + data[i].codePostal + "</td>");
        pointeurTr.append('<td name="ville">' + data[i].ville + "</td>");
    }
}

function recupererClientsParMot(mot) {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            action: 'recupererClients',
            mot: mot,
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null) {
                remplirTableauClients(res);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function remplirTableauInscrits(data) {
    let pointeurTableau = $("#tbodyInscrits");
    pointeurTableau.html("");
    let objet = {};
    for (let i = 0; i < data.length; i++) {
        objet[i] = 0;
        pointeurTableau.append('<tr name="inscrit" id=' + data[i].idInscrit + ' >');
        let pointeurTr = $('tr[name=inscrit][id=' + data[i].idInscrit + ']');
        pointeurTr.append('<td name="nom">' + data[i].nom + "</td>");
        pointeurTr.append('<td name="prenom">' + data[i].prenom + "</td>");
        pointeurTr.append('<td name="pseudo">' + data[i].pseudo + "</td>");
        pointeurTr.append('<td name="email">' + data[i].email + "</td>");
        pointeurTr.append('<td name="ville">' + data[i].ville + "</td>");


        if (!data[i].valide) {
            var confirmer = document.createElement("button");
            var typeConfirmer = document.createAttribute("type");
            typeConfirmer.value = "submit";
            confirmer.setAttributeNode(typeConfirmer);
            var classConfirmer = document.createAttribute("class");
            classConfirmer.value = "btn btn-primary";
            confirmer.setAttributeNode(classConfirmer);
            var idConfirmer = document.createAttribute("id");
            idConfirmer.value = "confirmer-inscrit" + i;
            confirmer.setAttributeNode(idConfirmer);
            var afficheConfirmer = document.createTextNode('Confirmer inscription');
            confirmer.appendChild(afficheConfirmer);

            pointeurTr.append('<td name="valide" id=' + i + '>');
            pointeurTr.append(confirmer);
            pointeurTr.append("</td>");

            var div = document.createElement("div");
            var idDiv = document.createAttribute("id");
            idDiv.value = "validation-inscrit" + i;
            div.setAttributeNode(idDiv);
            var styleDiv = document.createAttribute("style");
            styleDiv.value = "display: none";
            confirmer.addEventListener("click", function () {
                afficherDiv(i);
            });
            div.setAttributeNode(styleDiv);

            var label = document.createElement("label");
            var forLabel = document.createAttribute("for");
            forLabel.value = "ouvrier";
            label.setAttributeNode(forLabel);

            var checkbox = document.createElement("input");
            var typeInput = document.createAttribute("type");
            typeInput.value = "checkbox";
            checkbox.setAttributeNode(typeInput);
            var idInput = document.createAttribute("id");
            idInput.value = "valider-inscrit-ouvrier" + i;
            checkbox.setAttributeNode(idInput);
            var valueInput = document.createAttribute("value");
            valueInput.value = "ouvrier";
            checkbox.setAttributeNode(valueInput);
            var nbClick = 0;
            checkbox.addEventListener("change", function () {
                nbClick++;
                objet[i]++;
                console.log(objet[i]);
                console.log(objet);
                console.log(nbClick);
                if (objet[i] % 2 != 0) {
                    document.getElementById("lier-inscrit-client" + i).disabled = true;
                } else {
                    document.getElementById("lier-inscrit-client" + i).disabled = false;
                }
            });

            var lier = document.createElement("button");
            var typeLier = document.createAttribute("type");
            typeLier.value = "submit";
            lier.setAttributeNode(typeLier);
            var classLier = document.createAttribute("class");
            classLier.value = "btn btn-primary";
            lier.setAttributeNode(classLier);
            var idLier = document.createAttribute("id");
            idLier.value = "lier-inscrit-client" + i;
            lier.setAttributeNode(idLier);
            lier.addEventListener("click", function () {
                document.getElementById("lier-inscrit-client" + i).disabled = true;
                lierInscritClient(data[i].email);
            });

            var valider = document.createElement("button");
            var typeValider = document.createAttribute("type");
            typeValider.value = "submit";
            valider.setAttributeNode(typeValider);
            var classValider = document.createAttribute("class");
            classValider.value = "btn btn-primary";
            valider.setAttributeNode(classValider);
            var idValider = document.createAttribute("id");
            idValider.value = "valider-inscrit" + i;
            valider.setAttributeNode(idValider);
            valider.addEventListener("click", function () {
                document.getElementById("valider-inscrit" + i).disabled = true;
                validerInscription(data[i].email, objet[i]);
            });

            var afficheOuvrier = document.createTextNode('Ouvrier ? ');
            var afficheLier = document.createTextNode('Lier l\'inscrit à un client');
            var afficheValider = document.createTextNode('Valider inscription');

            label.appendChild(afficheOuvrier);
            lier.appendChild(afficheLier);
            valider.appendChild(afficheValider);

            div.appendChild(label);
            div.appendChild(checkbox);
            div.appendChild(lier);
            div.appendChild(valider);

            pointeurTableau.append(div);
        }
    }
};

function recupererInscrits(mot) {
    $.ajax({
        type: 'POST',
        url: '/admin',
        data: {
            action: 'recupererInscrits',
            mot: mot,
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null) {
                remplirTableauInscrits(res);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function afficherDiv(i) {
    document.getElementById("validation-inscrit" + i).style.display = "inline-block";
}

function lierInscritClient(email) {
    console.log("lien");
    lierInscritClient(email);
}

function validerInscription(email, nbClick) {
    if (nbClick % 2 != 0) {
        validateClient(email, "true");
    } else {
        validateClient(email, "false");
    }
}

function appelerToutesLesFonctionsAdminList() {
    recupererClientsParMot();
    recupererInscrits();
    recupererTypesAmenagementListe()
    recupererLesDevis();
    recupererLesPhotosFav();
}