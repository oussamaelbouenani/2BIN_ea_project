$(function () {

    $('#nav-profil').click(function () {
        FrontEnd.visiblePage('profil-view-page');
        sessionStorage.setItem('page', 'profil-view-page');
        document.title = "Profil | PAE";
    });
    
});
