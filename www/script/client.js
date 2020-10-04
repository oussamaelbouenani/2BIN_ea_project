$(function () {
    $('#submit-inserer-client').click(function () {
        if ($('.client-form').valid()) {
            var client = formToJSON($('.client-form'));
            sendClient(client);
            setTimeout(() => {
                recupererClients();
            }, 1000);
        }
        return false;
    });

    $('#lier-client-inscrit').click(function () {
        if ($('.client-form').valid()) {
            var client = formToJSON($('.client-form'));
            linkClientInscrit(client);
        }
        return false;
    });

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
};

function sendClient(client) {
    $.ajax({
        url: '/client',
        method: 'POST',
        data: {
            action: 'insererClient',
            client: client
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Insertion du client effectuée !");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });

}

function linkClientInscrit(client) {
    $.ajax({
        url: 'client',
        method: 'POST',
        data: {
            action: 'lierClientInscrit',
            client: client
        },
        success: function (data) {
            console.log(data);
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Lien entre le client et un inscrit effectué");
            }
        },
        error: function (jqHXR, textStatus, errorThrown) {
            errorToaster(jqHXR.responseText);
        }
    });
}
