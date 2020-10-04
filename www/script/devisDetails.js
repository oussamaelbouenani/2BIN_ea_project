let pictureAfterAmenagement = [];

function encodeImagetoBase64Details(file) {
    var reader = new FileReader();

    reader.onloadend = function () {
        pictureAfterAmenagement.push(reader.result);
        let imgPreview = document.createElement("img");
        imgPreview.style.width = "100px";
        imgPreview.style.height = "80px";
        imgPreview.style.border = "2px";
        imgPreview.setAttribute("src", reader.result);
        $("#pictures-previews-details").append(imgPreview);
    };
    console.log(pictureAfterAmenagement);
    reader.readAsDataURL(file);
}

$(function () {
    if (sessionStorage.getItem('page') !== 'devisDetails-view-page') {
        sessionStorage.removeItem('idDevis');
        sessionStorage.removeItem('idPhotoFav');
        sessionStorage.removeItem('nomPrenomClient');
        sessionStorage.removeItem('typesAmenagementDevisDetail');
    }
    $('#tbodyLesDevis').on("click", function () {
        sessionStorage.setItem('idPhotoFav', $('#tbodyLesDevis tr[id=' + sessionStorage.getItem('idDevis') + '] td[name=idPhotoFav] img[name=photoFav]').attr('src'));
        sessionStorage.setItem('nomPrenomClient', $('#tbodyLesDevis tr[id=' + sessionStorage.getItem('idDevis') + '] td[name=idClient]').html());
        sessionStorage.setItem('typesAmenagementDevisDetail', $('#tbodyLesDevis tr[id=' + sessionStorage.getItem('idDevis') + '] td[name=typesAmenagement]').html());

        setTimeout(() => {
            FrontEnd.visiblePage('devisDetails-view-page');
        }, 550);
        recupererTypesAmenagementDetails();
        sessionStorage.setItem('page', 'devisDetails-view-page');
    });
    if (sessionStorage.getItem('idDevis') != null) {
        recupererIdDevisListe(sessionStorage.getItem('idDevis'));
        recupererTypesAmenagementDetails();
    }
    $("#picturesAfterAmenagement")[0].onchange = function (e) {
        encodeImagetoBase64Details(e.target.files[0]);
    };
    $('#photo-detail-clear').click(function () {
        $('#pictures-previews-details').html("");
        recupererTypesAmenagementDetails();
        $('#clear-detail').html("<input type=\"checkbox\" name=\"visible\" id=\"visible-details\">");
        pictureAfterAmenagement = [];
    });

    $('#send-pictures-details').click(function () {
        let id = $('#select-type-amenagement-detail option:selected').attr('id');
        if (id == undefined) {
            infoToaster("Choisissez un type d'aménagement");
            return;
        }
        let typeJSON = {
            "idTypeAmenagement": id
        };
        let type = JSON.stringify(typeJSON);
        let devis = sessionStorage.getItem("idDevis");
        let visible = $('#visible-details').prop('checked');
        ajouterPhotosDetails(type, JSON.stringify(pictureAfterAmenagement), devis, visible);
    })

    $('.changeEtatBouton').on("click", function (e) {
        changerLEtat($(e.target).attr('id'));
        location.reload();
    });

});

function changerLEtat(idBouton) {
    let dateDebutTr = $('#devis-date-debut-travaux').val();
    let idDevis = sessionStorage.getItem('idDevis');
    switch (idBouton) {
        case "indiquer-commande-confirme":
            if (dateDebutTr === "") {
                errorToaster("Veuillez inserer une date de debut des travaux");
                return;
            }
            sendChangeEtat("Commande confirmée", idDevis, dateDebutTr);
            break;
        case "modifier-date-debut":
            sendChangeEtat("Commande confirmée", idDevis, dateDebutTr);
            break;
        case "supprimer-date-debut":
            sendChangeEtat("Commande confirmée", idDevis, "supp");
            break;
        case "confirmer-date-debut-travaux":
            if (dateDebutTr === "") {
                errorToaster("Veuillez inserer une date de debut des travaux");
                return;
            }
            sendChangeEtat("Date de début des travaux confirmée", idDevis, dateDebutTr);
            break;
        case "indiquer-facture-milieu":
            sendChangeEtat("Facture de milieu de chantier envoyée", idDevis, null);
            break;
        case "indiquer-facture-fin":
            sendChangeEtat("Facture de fin de chantier envoyée", idDevis, null);
            break;
        case "indiquer-visible":
            sendChangeEtat("Visible", idDevis, null);
            break;
        case "indiquer-non-visible":
            sendChangeEtat("Non visible", idDevis, null);
        default:
            break;
    }
}

function sendChangeEtat(etat, idDevis, dateTravaux) {
    $.ajax({
        url: '/admin',
        type: 'POST',
        data: {
            action: 'changerEtat',
            etat: etat,
            idDevis: idDevis,
            dateTravaux: dateTravaux,
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null) {
                console.log(res);
                fillDevis(res);
                successToaster("Etat changé");

            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function preparationBoutonEtat(etat) {
    switch (etat) {
        case "Introduit" || "Commande passée":
            $('#devis-date-debut-travaux').removeAttr('disabled');
            $('#confirmer-date-debut').removeAttr('disabled');
            $('#modifier-date-debut').removeAttr('disabled');
            $('#supprimer-date-debut').removeAttr('disabled');
            $('#indiquer-commande-confirme').removeAttr('disabled');
            break;
        case "Commande confirmée":
            $('#devis-date-debut-travaux').removeAttr('disabled');
            $('#modifier-date-debut').removeAttr('disabled');
            $('#supprimer-date-debut').removeAttr('disabled');
            $('#confirmer-date-debut-travaux').removeAttr('disabled');
            break;
        case "Date de début des travaux confirmée":
            $('#indiquer-facture-milieu').removeAttr('disabled');
            $('#indiquer-facture-fin').removeAttr('disabled');
            break;
        case "Facture de milieu de chantier envoyée":
            $('#indiquer-facture-fin').removeAttr('disabled');
            break;
        case "Facture de fin de chantier envoyée":
            $('#indiquer-visible').removeAttr('disabled');
            $('#indiquer-non-visible').removeAttr('disabled');
            break;
        case "Visible":
            $('.devisDetailFormPhoto').removeAttr("style");
            break;
        default:
            break;
    }
}

function recupererIdDevisListe(id) {
    sessionStorage.setItem('idDevis', id);
    $.ajax({
        url: '/admin',
        type: 'POST',
        data: {
            action: 'recupererUnDevis',
            id: id
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null && res.length !== 0) {
                fillDevis(res);
                let idPhotoFav = sessionStorage.getItem('idPhotoFav');
                if (idPhotoFav[0] == 'd') {
                    $('#devisDetails img[name=photoFav]').attr('src', sessionStorage.getItem('idPhotoFav'));
                } else {
                    $('#devisDetails img[name=photoFav]').attr('src', '../img/croix.svg');
                }
                if (res.dureeTravaux <= 15) {
                    $('.divFactureMilieuChantier').hide();
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function recupererTypesAmenagementDetails() {
    $.ajax({
        type: 'POST',
        url: '/devisDetails',
        data: {
            action: 'recupererTypes',
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var liste = JSON.parse(result);

            /** select **/
            let types = "<optgroup label=\"Type d'amenagement\"><option selected hidden value='-1'>-- Choisissez un type d'aménagement --</option>";
            for (let i = 0; i < liste.length; i++) {
                types += "<option name='idTypesAmenagement' id=\"" + liste[i].idTypeAmenagement + "\">" + liste[i].titre + "</option>";
            }
            types += "</optgroup>";

            $('#select-type-amenagement-detail').html(types);

        }, error: function (jqXHR) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function ajouterPhotosDetails(type, pictureAfterAmenagement, devis, visible) {
    $.ajax({
        type: 'POST',
        url: '/devisDetails',
        data: {
            action: 'ajouterPhotosFin',
            idType: type,
            photos: pictureAfterAmenagement,
            idDevis: devis,
            visible: visible
        },
        success: function (data) {
            let result = JSON.stringify(data);
            let res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Les photos ont bien été ajoutées !");
            }
        },
        error: function (jqXHR) {
            errorToaster(jqXHR.responseText);
        }

    });
}

function fillDevis(res) {
    JSONToForm($('#devisDetails'), res);
    $('h4[name=idDevis]').html("Devis #" + res.idDevis);
    $('#devisDetails input[name=nomPrenomClient]').val(sessionStorage.getItem('nomPrenomClient'));
    $('#devisDetails input[name=typesAmenagement]').val(sessionStorage.getItem('typesAmenagementDevisDetail'));
    preparationBoutonEtat(res.etat);
}