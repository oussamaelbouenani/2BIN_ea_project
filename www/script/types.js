$(function () {
    $('#inserer-types-amenagement').click(function () {
        remplirTableauTypes();
        FrontEnd.visiblePage('types-view-page');
        sessionStorage.setItem('page', 'types-view-page');
    });
    
    if (sessionStorage.getItem('page') === 'types-view-page' && window.PerformanceNavigation.TYPE_RELOAD == 1) {
        admin = sessionStorage.getItem('admin');
        if (admin === "admin") {
            remplirTableauTypes();
        }
    }

    $('#form-inserer-type').click(function () {
        if($('.amenagement-form').valid()){
            let type = {
                "titre": $('#type-titre').val(),
                "description": $('#type-description').val()
            };
            let typeString = JSON.stringify(type);
            insererType(typeString);
        }
    });
});

function insererType(type) {
    $.ajax({
        type: 'POST',
        url: '/types',
        data: {
            action: 'insererType',
            type: type
        },
        success: function (data) {
            let result = JSON.stringify(data);
            let res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster("Le type d'aménagement à bien été inséré !");
                remplirTableauTypes();
                recupererTypesAmenagement();
            }
        }, error(JqXHR) {
            errorToaster(JqXHR.responseText)
        }
    })
}

function remplirTableauTypes() {
    $.ajax({
        type: 'POST',
        url: '/types',
        data: {
            action: 'recupererTypes',
        },
        success: function (lesTypes) {
            let result = JSON.stringify(lesTypes);
            let resLesTypes = JSON.parse(result);

            let type = "";
            for (let i = 0; i < resLesTypes.length; i++) {
                type += "<tr>" +
                    "<td scope='row'>" + resLesTypes[i].idTypeAmenagement + "</td>" +
                    "<td>" + resLesTypes[i].titre + "</td>" +
                    "<td>" + resLesTypes[i].description + "</td>" +
                    "</tr>";
            }
            $('#inject-types').html(type);

        }, error: function (JqXHR) {
            errorToaster(JqXHR.responseText);
        }
    });

}
