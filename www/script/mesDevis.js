
$(function () {
    let admin;
    $('#nav-mes-devis').click(function () {
        admin = sessionStorage.getItem('admin');
        if (admin === "non-admin") {
            appelerToutesLesFonctionsMesDevis();
        }
        
        setTimeout(() => {
            FrontEnd.visiblePage('mesDevis-view-page');
        }, 1000);
        sessionStorage.setItem('page', 'mesDevis-view-page');
    });
    if(sessionStorage.getItem('page') ==='mesDevis-view-page' && window.PerformanceNavigation.TYPE_RELOAD ==1){
        admin = sessionStorage.getItem('admin');
        if(admin === "non-admin"){
                appelerToutesLesFonctionsMesDevis();
        }
    }
    $('#submit-recherche-mes-devis').on("click", function(){
        let date = $('#recherche-mes-devis-date').val();
        let nombre1 = $('#recherche-mes-devis-nombre1').val();
        let nombre2 = $('#recherche-mes-devis-nombre2').val();
        let typeAmen =$('#recherche-mes-devis-type-amenagement option:selected').attr('id');
        recupererMesDevis(date, nombre1, nombre2, typeAmen);
        recupererMaPhotoFav(date, nombre1, nombre2, typeAmen);
    });
    $('#submit-reset-recherche-mes-devis').on("click", function () {
        $('#recherche-mes-devis').val("");
        $('#recherche-mes-devis-date').val("");
        $('#recherche-mes-devis-nombre1').val('');
        $('#recherche-mes-devis-nombre2').val('');
        $('#recherche-mes-devis-type-amenagement option:first').prop('selected', true);
        appelerToutesLesFonctionsMesDevis();
    }); 
});

function remplireTypesAmenagementsRechercheMesDevis(liste){
        let ptrSelect = $("select[id=recherche-mes-devis-type-amenagement]");
        ptrSelect.append('<option name="recherche-mes-devis-type-amenagement-option" id="0" ></option>')
        for(let i = 0; i < liste.length ; i ++){
            ptrSelect.append('<option name="recherche-mes-devis-type-amenagement-option" id='+ liste[i].idTypeAmenagement+' >'+ liste[i].titre +'</option>');
        }
}

function recupererTypesAmenagementRechercheMesDevis() {
    $.ajax({
        type: 'POST',
        url: '/mesDevis',
        data: {
            action: 'recupererTypes',
        },
        success: function (data) {
            var result = JSON.stringify(data);
            var liste = JSON.parse(result);
            
            remplireTypesAmenagementsRechercheMesDevis(liste);
            
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
}

function remplirTableauMesDevis(objetDevis) {
    let pointeurTableau = $("#tbodyMesDevis");
    pointeurTableau.html("");
    for (let i = 0; i < objetDevis.length; i++) {
        pointeurTableau.append('<tr onclick="recupererMonIdDevisListe(this.id)" name="mesDevis" id=' + objetDevis[i].idDevis + ' >');
        let pointeurTr = $('tr[name=mesDevis][id=' + objetDevis[i].idDevis + ']');
        pointeurTr.append('<td name="dateDevis" >' + objetDevis[i].dateDevis + "</td>");
        pointeurTr.append('<td name="montantTotal" >' + objetDevis[i].montantTotal + " €</td>");
        pointeurTr.append('<td name="dureeTravaux" >' + objetDevis[i].dureeTravaux + "j</td>");
        pointeurTr.append('<td name="etat" >' + objetDevis[i].etat + "</td>");
        pointeurTr.append('<td name="dateDebutTravaux" >' + objetDevis[i].dateDebutTravaux + "</td>");
        pointeurTr.append('<td name="typesAmenagement"></td>');
        pointeurTr.append('<td name="photoFav" ></td>');
    }
    remplirTableauMesDevisTypeAmenagements(objetDevis);
}

function remplirTableauMesDevisTypeAmenagements(objetTypeAmen){
    for(let i=0; i < objetTypeAmen.length ; i ++){
        let tab = {};
        tab = objetTypeAmen[i].listeTypeAmen;
        $('tr[id=' + objetTypeAmen[i].idDevis + '] td[name=typesAmenagement]').append(tab[0]);
        for(let j =1;  j < tab.length ; j++){
            $('tr[id=' + objetTypeAmen[i].idDevis + '] td[name=typesAmenagement]').append(' + '+tab[j]);
        }
    }
}

function remplirTableauMesPhotosFav(objetPhoto) {
    for (let i = 0; i < objetPhoto.length; i++) {
        $('#tbodyMesDevis tr[id=' + objetPhoto[i].idDevis + '] td[name=photoFav]').html('<img name="photoFav" style="width: 200px; height: 130px;" src ='+objetPhoto[i].photo+'></img>');
    }
}

function recupererMesDevis(date, nombre1, nombre2, typeAmen) {
    $.ajax({
        type: 'POST',
        url: '/mesDevis',
        data: {
            action: 'recupererMesDevis',
            date: date,
            nombre1: nombre1,
            nombre2: nombre2,
            typeAmen: typeAmen,
        },
        success: function (mesDevis) {
            var result = JSON.stringify(mesDevis);
            var res = JSON.parse(result);
            if (res != null) {
                remplirTableauMesDevis(res);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function recupererMaPhotoFav(date, nombre1, nombre2,typeAmen) {
    $.ajax({
        type: 'POST',
        url: '/mesDevis',
        data: {
            action: 'recupererMaPhotoFav',
            date: date,
            nombre1: nombre1,
            nombre2: nombre2,
            typeAmen: typeAmen,
        },
        success: function (maPhotoFav) {
            var result = JSON.stringify(maPhotoFav);
            var res = JSON.parse(result);
            remplirTableauMesPhotosFav(res);
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText)
        }
    });
}

function appelerToutesLesFonctionsMesDevis(){
    recupererMesDevis();
    recupererTypesAmenagementRechercheMesDevis();
    recupererMaPhotoFav();
}
