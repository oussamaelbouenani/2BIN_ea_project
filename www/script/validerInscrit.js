$(function () {
/*
    $("#confirmer-inscrit").click(function () {
        console.log("1");
        document.getElementById("validation-inscrit").style.display = "block";
    });

    $("#valider-inscrit-ouvrier").change(function () {
        console.log("2");
        if (document.getElementById("valider-inscrit-ouvrier").checked) {
            document.getElementById("lierClient").style.display = "none";
        } else {
            document.getElementById("lierClient").style.display = "inline-block";
        }
    });

    $("#valider-inscrit").click(function () {
        var email = $("#email-inscrit").val();
        if (document.getElementById("valider-inscrit-ouvrier").checked) {
            validateClient(email, "true", null);
        } else {
            var client = $("#lierClient").value;
            validateClient(email, "false", client);
        }
    });*/

});

function lierInscritClient(email) {
    $.ajax({
        url: '/inscrit',
        method: 'POST',
        data: {
            action: 'lierInscritClient',
            email: email
        },
        success: function(data) {
            var result = JSON.stringify(data);
            var res =JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Lien entre l'inscrit et un client effectué !");
            }
        },
        error: function(JqXHR, textStatus, errorThrown) {
            errorToaster(JqXHR.responseText);
        }
    });
};

function validateClient(email, ouvrier, client) {
    $.ajax({
        url: '/inscrit',
        method: 'POST',
        data: {
            action: 'validerInscrit',
            email: email,
            ouvrier: ouvrier,
            client: client
        },
        success: function(data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Mise à jour de l'inscrit effectuée !");
            }
        },
        error: function(JqXHR, textStatus, errorThrown) {
            errorToaster(JqXHR.responseText);
        }
    });
}