$(function () {

    window.onbeforeunload = function () {
        window.scrollTo(0, 0);
    };

    $('#logout').click(function () {
        $.ajax({
            type: 'POST',
            url: '/login',
            data: {
                action: 'seDeconnecter'
            },
            success: function (data) {
                if (JSON.parse(data)) {
                    jQuery('#modal').click();
                    $('#lambdaNav').show();
                    $("#profil-view-page").hide();
                    $("#devis-view-page").hide();
                    $("#mesDevis-view-page").hide();
                    $("#adminList-view-page").hide();
                    $("#devisDetails-view-page").hide();
                    $('#monDevisDetaille-view-page').hide();
                    $('#wrapper').removeAttr('class');
                    $('#footer').removeAttr('style');
                    $('#footer').attr('style', 'width:100%');
                    localStorage.clear();
                    sessionStorage.clear();
                    FrontEnd.displayPage('accueil-view-page');
                    sessionStorage.setItem('page', 'accueil-view-page');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errorToaster(jqXHR.responseText)
            }
        });
        return false;
    })

});
