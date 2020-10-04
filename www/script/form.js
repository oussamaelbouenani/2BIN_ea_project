function formToJSON(f) {
    var o = {};
    var inputs = f.find('input:not(:radio):not(:checkbox), textarea').each(
        function (i, el) {
            var nameEl = $(el).attr('name');
            var valEl = $(el).val();
            if ($(el).attr('type') === 'number') {
                valEl = parseFloat(valEl);
            }
            o[nameEl] = valEl;
        });
    /*
     * inputs = f.find('select').each(function(i, el) { var nameEl =
     * $(el).attr('name'); if ($(el).attr('multiple') === undefined) { o[nameEl] =
     * ($(el).val() === '') ? null : $(el).val(); } else { o[nameEl] =
     * ($(el).val() === null) ? [] : $(el).val(); } });
     */
    inputs = f.find(':radio, :checkbox').each(function (i, el) {
        var nameEl = $(el).attr('name');
        if ($(el).attr('type') === 'radio') {
            o[nameEl] = $(el).is(':checked') ? $(el).val() : null;
        } else if ($(el).attr('type') === 'checkbox') {
            o[nameEl] = $(el).prop('checked');
        }
    });
    return JSON.stringify(o);
}

function FormToJSONCheckbox(f){
    var o = {};
    inputs = f.find(':checkbox').each(function (i, el) {
        if($(el).prop('checked')){
            var nameEl = $(el).attr('name');
            o[nameEl] = "";
        }
        
    });
    return JSON.stringify(o);

}

function JSONToForm(f, texte) {
    var o = (typeof texte === 'string' || texte instanceof String) ? JSON.parse(texte) : texte;
    for (var key in o) {
        var v = o[key];
        var input = f.find(':input[name=' + key + ']');
        /*
         * else if ($(input).is('select')) { if ($(input).attr('multiple') ===
         * undefined) { $(input).val(v.length === 0 ? null : v); } else {
         * $(input).val(v === null ? "" : v); } }
         */
        if ($(input).attr('type') === 'checkbox') {
            $(input).prop('checked', v);
        } else if ($(input).attr('type') === 'radio') {
            if (v == null) {
                $(input).prop('checked', false);
            } else {
                $(input).filter('[value="' + v + '"]').prop('checked', true);
            }
        } else {
            $(input).val(v);
        }
    }
    var inputs = f.find(':input').each(function (i, el) {
        var nameEl = $(el).attr('name');
        var valEl = $(el).val();
        if (o[nameEl] === undefined) {
            $(el).val(valEl);
        }
    });
}

function clearForm(f) {
    var inputs = f.find(':input').each(function (i, el) {
        if (($(el).attr('type') === 'radio' || $(el).attr('type') === 'checkbox')) {
            $(el).attr('checked', false);
        } else {
            if (!$(el).is('select[multiple]')) {
                $(el).val($(el).find('option:first').val());
            } else {
                $(el).val('');
            }
        }
    });
}
