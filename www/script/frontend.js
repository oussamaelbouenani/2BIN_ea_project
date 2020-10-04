/*!
 * Framework Front-End minimaliste
 *
 * Copyright 2017 Donatien Grolaux
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 *
 * Date: 2017-03-12
 */
var FrontEnd = (function () {

    var onInits = {};
    var onLeaves = {};
    var onDisplays = {};
    var inited = {};
    var currentPage = null;
    var currentPageArticle = null;

    function onInitPage(page, f) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }
        if (page.length != 1)
            return false;
        onInits[page.attr('id')] = f;
        return true;
    }

    function onLeavePage(page, f) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }
        if (page.length != 1)
            return false;
        onLeaves[page.attr('id')] = f;
        return true;
    }

    function onDisplayPage(page, f) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }
        if (page.length != 1)
            return false;
        onDisplays[page.attr('id')] = f;
        return true;
    }

    function getCurrentPage() {
        return currentPage;
    }

    function canDisplayPage(page) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }
        if (page.length != 1)
            return false;

        if (currentPage !== null) {
            if (onLeaves[currentPage.attr('id')] != undefined) {
                if (onLeaves[currentPage.attr('id')](page) === false) {
                    return false;
                }
            }
        }

        if (inited[page.attr('id')] !== true) {
            if (onInits[page.attr('id')] != undefined) {
                if (onInits[page.attr('id')](page) === false) {
                    return false;
                }
            }
            inited[page.attr('id')] = true;
        }

        if (onDisplays[page.attr('id')] != undefined) {
            if (onDisplays[page.attr('id')](page) === false) {
                return false;
            }
        }

        return true;
    }

    // pour ajouter une deuxième page
    function visiblePage(page) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }
        var str = page.attr('id');
        var n = str.search('view-page');
        if (n != -1 && page.css('display') !== 'block') {
            $('article:visible').css('display', 'none');
            page.css('display', 'block');
        }
        currentPageArticle = page;
        // console.log(currentPageArticle[0].id)
    }

    function getCurrentPageArticle() {
        return currentPageArticle;
    }

    function displayPage(page) {
        if (page instanceof String || typeof page == 'string') {
            page = $('#' + page);
        }

        if (!canDisplayPage(page))
            return false;

        if (currentPage !== null) {
            currentPage.css('display', 'none');
        }
        page.css('display', 'block');
        currentPage = page;

    }

    function alert(title, question, message, callback) {
        var modal = $('<div class="modal"><div class="modal-content"><div class="modal-header"><span class="close">&times;</span><h2>M</h2></div><div class="modal-body"></div><div class="modal-footer"></div>  </div></div>');
        modal.find('.modal-header h2').text(title);
        modal.find('.modal-body').text(message);
        var btn = question.split("|");
        modal.find('.modal-header span').css('display', 'none');
        jQuery.each(btn, function (i, item) {
            var btn = $('<button type="button">');
            switch (item) {
                case 'YES':
                    btn.text("Oui");
                    break;
                case 'NO':
                    btn.text("Non");
                    break;
                case 'ALWAYSYES':
                    btn.text("Toujours oui");
                    break;
                case 'ALWAYSNO':
                    btn.text("Toujours non");
                    break;
                case 'CANCEL':
                    btn.text("Annuler");
                    modal.find('.modal-header span').css('display', 'block').on(
                        'click',
                        function () {
                            btn.trigger('click');
                        });
                    break;
            }
            btn.on('click', function () {
                modal.remove();
                modal = null;
                if (callback !== undefined) {
                    callback(item);
                }
            });
            modal.find('.modal-footer').append(btn);
        });
        $('body').append(modal);
        modal.css('display', 'block');
    }

    function ajax(config) {
        var fail = config.error;
        config.error = function (jqxhr, status, error) {
            if (fail !== undefined) {
                if (fail(jqxhr, status, error) === true) {
                    return;
                }
            }
            if (jqxhr.status == 400) {
                FrontEnd.alert("Erreur", "CANCEL", jqxhr.responseText);
            } else {
                FrontEnd.alert("Erreur grave", "CANCEL", jqxhr.responseText,
                    function () {
                        location.reload();
                    });
            }
        }
        if (config.type === undefined)
            config.type = 'POST';
        if (config.url === undefined)
            config.url = '/dispatch';
        if (config.action !== undefined) {
            if (config.data === undefined)
                config.data = {};
            config.data.action = config.action;
            delete config.action;
        }

        $.ajax(config);
    }

    $('body>.container>.row').css('display', 'none');

    return {
        onInitPage: onInitPage,
        onLeavePage: onLeavePage,
        onDisplayPage: onDisplayPage,
        displayPage: displayPage,
        canDisplayPage: canDisplayPage,
        currentPage: getCurrentPage,
        visiblePage: visiblePage,
        currentPageArticle: getCurrentPageArticle,
        ajax: ajax,
        alert: alert
    }

})();
