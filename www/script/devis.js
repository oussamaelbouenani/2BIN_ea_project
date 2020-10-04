let picturesBeforeAmenagement = [];

function encodeImagetoBase64(file) {
    var reader = new FileReader();
    
    reader.onloadend = function () {
        picturesBeforeAmenagement.push(reader.result);
        let imgPreview = document.createElement("img");
        imgPreview.style.width = "100px";
        imgPreview.style.height = "80px";
        imgPreview.style.border = "2px";
        imgPreview.setAttribute("src", reader.result);
        $("#pictures-previews").append(imgPreview);
    };
    //console.log(picturesBeforeAmenagement);
    reader.readAsDataURL(file);
}

$(function () {
    let admin;
    
    /** conversion **/
    $("#picturesBeforeAmenagement")[0].onchange = function (e) {
        encodeImagetoBase64(e.target.files[0]);
    };
    
    $('#photo-form-clear').click(function () {
        $('#pictures-previews').html("");
        picturesBeforeAmenagement = [];
    });
    
    /** Afficher inserer devis **/
    $('#inserer-devis').click(function () {
        recupererClients();
        recupererTypesAmenagement();
        FrontEnd.visiblePage('devis-view-page');
        sessionStorage.setItem('page', 'devis-view-page');
    });
    /**  form type **/
    $('#form-type2').hide();
    $('#hide-type-devis-form').hide();
    
    $('#show-type-devis-form').click(function () {
        $('#form-type2').show();
        $('#show-type-devis-form').hide();
        $('#hide-type-devis-form').show();
    });
    
    $('#hide-type-devis-form').click(function () {
        $('#form-type2').hide();
        $('#hide-type-devis-form').hide();
        $('#show-type-devis-form').show();
    });
    $('#form-inserer-type2').click(function () {
        let type = {
            "titre": $('#type-titre2').val(),
            "description": $('#type-description2').val()
        };
        let typeString = JSON.stringify(type);
        insererType(typeString);
    });
    
    /** show/hide client **/
    let show = $('#show-client');
    let hide = $('#hide-client');
    let formClient = $('#form-client');
    
    hide.hide();
    formClient.hide();
    
    show.click(function () {
        show.hide()
        hide.show()
        formClient.show()
    });
    hide.click(function () {
        show.show()
        hide.hide()
        formClient.hide();
    });
    if (sessionStorage.getItem('page') === 'devis-view-page' && window.PerformanceNavigation.TYPE_RELOAD == 1) {
        admin = sessionStorage.getItem('admin');
        if (admin === "admin") {
            recupererClients();
            recupererTypesAmenagement();
        }
    }
    
    clearForm($('.devis-form'));
    clearForm($('.client-form'));
    
    /**
    * Inserer devis
    * **/
    $('#submit-inserer-devis').click(function () {
        if($('.devis-form').valid()){
            if ( $('.photo-form').valid()) {
                if ($('#select-client').val() == null) {
                    infoToaster("Choisissez un client");
                    return;
                }
                let recupDevis;
                if ($('#form-date-debut-travaux').val() === "") {
                    recupDevis = {
                        "montantTotal": $('#form-montant-total').val(),
                        "dureeTravaux": $('#form-duree-travaux').val(),
                        "dateDevis": $('#form-date-devis').val(),
                    };
                } else {
                    recupDevis = {
                        "montantTotal": $('#form-montant-total').val(),
                        "dureeTravaux": $('#form-duree-travaux').val(),
                        "dateDebutTravaux": $('#form-date-debut-travaux').val(),
                        "dateDevis": $('#form-date-devis').val(),
                    };

                    if($('#form-date-devis').val() >  $('#form-date-debut-travaux').val()){
                        infoToaster("La date de debut des travaux doit etre postérieur à celle de la date de devis ");
                        return;
                    }
                }
                let devis = JSON.stringify(recupDevis);
                
                //Recuperer Types Amenagement
                let typeA = FormToJSONCheckbox($('.devis-form'));
                if(typeA.charAt(1)=== "}"){
                    infoToaster("Choisissez au moins un type d'amenagement");
                    return;
                }

                //Recuperer Client
                var clientJSON = {
                    "idClient": $('#select-client option:selected').attr('id')
                };
                var client = JSON.stringify(clientJSON);
                sendDevisClient(devis, JSON.stringify(picturesBeforeAmenagement), client, typeA);
            }
        }
        return false;
    });
    
    function sendDevisClient(devis, photos, client, typeA) {
        $.ajax({
            type: 'POST',
            url: '/devis',
            data: {
                action: 'insererDevis',
                devis: devis,
                photos: photos,
                client: client,
                typesAmenagement: typeA,
            },
            success: function (data) {
                var result = JSON.stringify(data);
                var res = JSON.parse(result);
                if (res != null && res.length != 0) {
                    successToaster("Insertion de devis effectuée !");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errorToaster(jqXHR.responseText);
            }
        });
    };
});

function recupererClients() {
    $.ajax({
        type: 'POST',
        url: '/devis',
        data: {
            action: 'recupererClients',
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var liste = JSON.parse(result);
            
            /** <Select> **/
            let types = "<optgroup label=\"Clients\"><option selected disabled hidden></option>";
            for (let i = 0; i < liste.length; i++) {
                types += "<option name='idClient' id=\"" + liste[i].idClient + "\">" + liste[i].nom + ", " + liste[i].prenom + "</option>";
            }
            types += "</optgroup>";
            $('#select-client').html(types);
            
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function recupererTypesAmenagement() {
    $.ajax({
        type: 'POST',
        url: '/devis',
        data: {
            action: 'recupererTypes',
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var liste = JSON.parse(result);
            
            /** <input type="Checkbox"> **/
            let types = "";
            for (let i = 0; i < liste.length; i++) {
                types += "<div class='col-6'><label><input type='checkbox' name=" + liste[i].idTypeAmenagement + " >" + liste[i].titre + "</label></div>";
            }
            $('#select-type-amenagement').html(types);
            return liste; /** ajouté recemment **/
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}
