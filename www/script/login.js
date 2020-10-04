$(function () {
    $('#CONNEXION').on("click", function () {
        FrontEnd.visiblePage('login-view-page');
        sessionStorage.setItem('page', 'login-view-page');
        document.title = "Login | PAE";
    })

    clearForm($('.login-form'));
    clearForm($('.inscription-form'));

    sendVerify();

    $('#submit-login').click(function () {
        if ($('.login-form').valid()) {
            let email = $('#form-email-login').val();
            let mdp = $('#form-mdp-login').val();
            console.log("email = " + email);
            sendLogin(email, mdp);
        }
        return false;
    });

    $('#submit-inscription').click(function () {
        if ($('.inscription-form').valid()) {
            let inscription = formToJSON($('.inscription-form'));
            console.log(inscription);
            sendInscription(inscription);
        }
        return false;
    });

});

function sendVerify() {
    $.ajax({
        url: '/login',
        type: 'POST',
        data: {
            action: 'estConnecte'
        },
        success: function (data) {
            let result = JSON.stringify(data);
            let r = JSON.parse(result);
            if (r != null && r.length != 0) {
                FrontEnd.displayPage('header-view-page');
                let admin = fillDataProfil(r);
                let tAdmin = $('.admin-view'); // view true admin
                let fAdmin = $('.notadmin-view'); // view false admin
                if (admin === "admin") {
                    tAdmin.show();
                    fAdmin.hide();
                } else {
                    tAdmin.hide();
                    fAdmin.show();
                }
                let page = (sessionStorage.getItem('page') == null) ? 'profil-view-page' :
                    sessionStorage.getItem('page');
                FrontEnd.visiblePage(page);
                document.title = 'Profil | La Rose et le Lilas';
            } else {
                if (sessionStorage.getItem('page') === "accueil-view-page" || sessionStorage.getItem('page') === "login-view-page") {
                    FrontEnd.displayPage(sessionStorage.getItem('page'));
                } else {
                    FrontEnd.displayPage('accueil-view-page');
                    sessionStorage.setItem('page', 'accueil-view-page');
                }
                document.title = 'La Rose et le Lilas | PAE';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    })
};

function sendLogin(email, mdp) {
    $.ajax({
        url: '/login',
        type: 'POST',
        data: {
            action: 'login',
            email: email,
            mdp: mdp
        },
        success: function (data) {
            let result = JSON.stringify(data);
            let res = JSON.parse(result);
            if (res != null && res.length != 0) {
                let valide = estValide(res);
                if (!valide) {
                    errorToaster("Vous n'avez pas encore les autorisations");
                } else {
                    let admin = fillDataProfil(res);
                    let tAdmin = $('.admin-view'); // view true admin
                    let fAdmin = $('.notadmin-view'); // view false admin
                    console.log(admin);
                    if (admin === "admin") {
                        sessionStorage.setItem('admin', "admin");
                    } else {
                        sessionStorage.setItem('admin', "non-admin");
                    }
                    if (admin === "admin") {
                        tAdmin.show();
                        fAdmin.hide();
                    } else {
                        tAdmin.hide();
                        fAdmin.show();
                    }
                    $('#footer').removeAttr('style');
                    $('#wrapper').attr("class", "content-wrapper");
                    $('#lambdaNav').hide();
                    $('#login-view-page').hide();
                    FrontEnd.displayPage('header-view-page');
                    FrontEnd.visiblePage('profil-view-page');
                    sessionStorage.setItem('page', 'profil-view-page');
                    document.title = 'Profil | La Main Verte';
                    $('#danger-login').hide();
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
};

function sendInscription(inscription) {
    $.ajax({
        type: 'POST',
        url: '/login',
        data: {
            action: 'inscrire',
            inscription: inscription
        },
        success: function (data) {
            let result = JSON.stringify(data);
            let res = JSON.parse(result);
            if (res != null && res.length != 0) {
                successToaster('Inscription effectuée avec succès !');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorToaster(jqXHR.responseText);
        }
    });
};

function estValide(json) {
    return JSON.parse(json.valide);
}

function fillDataProfil(json) {
    $('#profilFullName').html(json.nom.toUpperCase() + ' ' + json.prenom);
    JSONToForm($('.profil-form'), json);
    if (json.ouvrier) {
        return "admin";
    }
    return "non-admin";
};

function errorToaster(messages) {
    $.toast({
        heading: 'Erreur',
        text: messages,
        position: 'bottom-right',
        showHideTransition: 'fade',
        icon: 'error'
    })
};

function successToaster(messages) {
    $.toast({
        heading: 'Success',
        text: messages,
        position: 'bottom-right',
        showHideTransition: 'slide',
        icon: 'success'
    })
};

function infoToaster(messages) {
    $.toast({
        heading: 'Information',
        text: messages,
        position: 'bottom-right',
        showHideTransition: 'fade',
        icon: 'info'
    })
}

function warningToaster(messages) {
    $.toast({
        heading: 'Attention',
        text: messages,
        position: 'bottom-right',
        showHideTransition: 'fade',
        icon: 'warning'
    })
};
