$(function () {

    if (sessionStorage.getItem('page') === null) {
        sessionStorage.setItem('page', 'accueil-view-page');
        document.title = "La Rose et le Lilas | PAE";
    }
    $('.nav-accueil').click(function () {
        remplirCarouselEtApercu();
        setTimeout(() => {
            FrontEnd.visiblePage('accueil-view-page');
            sessionStorage.setItem('page', 'accueil-view-page');
            document.title = "La Rose et le Lilas | PAE";
            $('#login-view-page').hide();
        }, 1000);
    });
    if (sessionStorage.getItem('page') === 'accueil-view-page') {
        remplirCarouselEtApercu();
    }

    if (sessionStorage.getItem('admin') === null) {
        $('#footer').removeAttr('style');
        $('#wrapper').removeAttr('class');
        $('#footer').attr('style', 'width:100%');
    }

    $('#logout').click(function () {
        remplirCarouselEtApercu();
    });

    $('#filter-submit').click(function () {
        remplirCarouselEtApercu();
    });


});

function remplirCarouselEtApercu() {
    $.ajax({
        type: 'POST',
        url: '/accueil',
        data: {
            action: 'recupererPhotosByType',
        },
        success: function (lesPhotos) {
            let result = JSON.stringify(lesPhotos);
            let resLesPhotos = JSON.parse(result);

            /** Carousel **/
            $('.carousel-inner').html("");

            let liste = "<div class='carousel-item active' id='" + resLesPhotos[0].idTypeAmenagement + "'> <img class='d-block w-100' src='" + resLesPhotos[0].photo + "' alt='slide n°" + 1 + "'></div>";
            for (let i = 1; i < resLesPhotos.length; i++) {
                liste += "<div class='carousel-item' id='" + resLesPhotos[i].idTypeAmenagement + "'> <img class='d-block w-100' src='" + resLesPhotos[i].photo + "' alt='slide n°" + i + 1 + "'> </div>";
            }
            $('.carousel-inner').append(liste);

            /** Apercu + filtre **/
            let apercu = $('#types-amenagement-apercu');
            let idTypeAmenagement = $('#filter-type-amenagement option:selected').attr('id');

            /* Creer les tableaux */
            apercu.html("");
            if (idTypeAmenagement != null) {
                for (let i = 0; i < resLesPhotos.length; i++) {
                    if (resLesPhotos[i].idTypeAmenagement != idTypeAmenagement) {
                        continue;
                    }
                    apercu.append("<table class='table table-light col-lg-6' id='" + resLesPhotos[i].idTypeAmenagement + "'><tr><td><img class='card-img-top' style=\"height: 300px; width: 100%; display: block;\" src='" + resLesPhotos[i].photo + "'></td></tr></table>");
                }
            } else {
                for (let i = 0; i < resLesPhotos.length; i++) {
                    apercu.append("<table class='table table-light col-lg-6' id='" + resLesPhotos[i].idTypeAmenagement + "'><tr><td><img class='card-img-top' style=\"height: 300px; width: 100%; display: block;\" src='" + resLesPhotos[i].photo + "'></td></tr></table>");
                }
            }
            console.log("ICI-1");
            remplirCarouselTypeEtApercu(idTypeAmenagement);

        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });

}

function remplirCarouselTypeEtApercu(idTypeAmenagement) {

    $.ajax({
        type: 'POST',
        url: '/accueil',
        data: {
            action: 'recupererTypes',
        },
        success: function (lesTypes) {
            let result = JSON.stringify(lesTypes);
            let resLesTypes = JSON.parse(result);
            console.log("ICI")
            /** Carousel + filtre **/

            for (let i = 0; i < resLesTypes.length; i++) {
                $('.carousel-item[id=' + resLesTypes[i].idTypeAmenagement + ']').append('<div class="carousel-caption d-md-block"><h3>' + resLesTypes[i].titre + '</h3><p class="text-justify text-truncate">' + resLesTypes[i].description + '</p></div>');
            }
            $('#filter-type-amenagement').html('<option selected>Type Aménagement...</option>');
            for (let i = 0; i < resLesTypes.length; i++) {
                $('#filter-type-amenagement').append("<option id='" + resLesTypes[i].idTypeAmenagement + "'>" + resLesTypes[i].titre + "</option>");
            }

            /** Apercu **/
            if (idTypeAmenagement != null) {
                idTypeAmenagement--;
                /* Remplir tous les tableaux filtres */
                for (let i = 0; i < resLesTypes.length; i++) {
                    if (i != idTypeAmenagement) {
                        continue;
                    }
                    let text =
                        "    <tr>" +
                        "     <td><h2 class='text-center'>" + resLesTypes[i].titre + "</h2></td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "     <td><p class='p-2 text-justify'>" + resLesTypes[i].description + "</p></td>" +
                        "    </tr>";
                    $('.table[id=' + resLesTypes[i].idTypeAmenagement + ']').append(text);
                }
            } else {
                /* Remplir tous les tableaux */
                for (let i = 0; i < resLesTypes.length; i++) {
                    let text =
                        "    <tr>" +
                        "     <td><h2 class='text-center'>" + resLesTypes[i].titre + "</h2></td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "     <td><p class='p-2 text-justify'>" + resLesTypes[i].description + "</p></td>" +
                        "    </tr>";
                    $('.table[id=' + resLesTypes[i].idTypeAmenagement + ']').append(text);
                }

            }

        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }

    })
}
