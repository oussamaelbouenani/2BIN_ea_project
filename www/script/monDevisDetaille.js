$(function () {
    if (sessionStorage.getItem('page') !== 'monDevisDetaille-view-page') {
        sessionStorage.removeItem('monIdDevis');
        sessionStorage.removeItem('monIdPhotoFav');
        sessionStorage.removeItem('monTypesAmenagementDevisDetail');
    }
    $('#tbodyMesDevis').on("click", function () {
        sessionStorage.setItem('monIdPhotoFav', $('#tbodyMesDevis tr[id=' + sessionStorage.getItem('monIdDevis') + '] img[name=photoFav]').attr('src'));
        sessionStorage.setItem('monTypesAmenagementDevisDetail', $('#tbodyMesDevis tr[id=' + sessionStorage.getItem('monIdDevis') + '] td[name=typesAmenagement]').html());

        setTimeout(() => {
            FrontEnd.visiblePage('monDevisDetaille-view-page');
        }, 650);
        sessionStorage.setItem('page', 'monDevisDetaille-view-page');
    });

    if (sessionStorage.getItem('monIdDevis') != null) {
        recupererMonIdDevisListe(sessionStorage.getItem('monIdDevis'));
    }
});

function recupererMonIdDevisListe(id) {
    sessionStorage.setItem('monIdDevis', id);
    $.ajax({
        url: '/client',
        type: 'POST',
        data: {
            action: 'recupererMonDevis',
            id: id
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var res = JSON.parse(result);
            if (res != null) {
                console.log(res);
                JSONToForm($('#monDevisDetaille'), res);
                $('h4[name=monIdDevis]').html("Devis #" + res.idDevis);
                $('#monDevisDetaille input[name=nomPrenomClient]').val(sessionStorage.getItem('nomPrenomClient'));
                $('#monDevisDetaille input[name=typesAmenagement]').val(sessionStorage.getItem('monTypesAmenagementDevisDetail'))
                let monIdPhotoFav = sessionStorage.getItem('monIdPhotoFav');
                if (monIdPhotoFav[0] == 'd') {
                    $('#monDevisDetaille img[name=photoFav]').attr('src', sessionStorage.getItem('monIdPhotoFav'));
                } else {
                    $('#monDevisDetaille img[name=photoFav]').attr('src', '../img/croix.svg');
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}
