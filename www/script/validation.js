$(function() {

    $.validator.addMethod('strongPassword', function(value, element) {
        return value.length >= 6;
    }, 'Le mot de passe doit comporter au moins 6 caractères');

    $.validator
        .addMethod(
            'email',
            function(value, element) {
                var filter = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return this.optional(element) || filter.test(value);
            }, 'Cet email n\'est pas valide.');

    $('.login-form').validate({
        rules: {
            email: {
                required: true,
                email : true
            },
            mdp: {
                required: true,
                strongPassword: true
            }
        },
        messages: {
            email: {
                required: 'Veuillez insérer votre pseudo',
            },
            mdp: {
                required: 'Veuillez insérer votre mot de passe'
            }
        }
    });

    $('.amenagement-form').validate({
        rules: {
            titre: {
                required: true,
            },
            description: {
                required: true,
            }
        },
        messages: {
            titre: {
                required: 'Veuillez insérer un titre',
            },
            description: {
                required: 'Veuillez insérer une description',
            }
        }
    });

    $('.photo-form').validate({
        rules: {
            inputPhotoAvantAmen: {
                required: true,
            }
        },
        messages: {
            inputPhotoAvantAmen: {
                required: 'Veuillez insérer au moins une photo',
            }
        }
    });

    $('.inscription-form').validate({
        normalizer: function(value) {
            return $.trim(value);
        },
        rules: {
            pseudo: {
                required: true,
                minlength: 4,
            },
            mdp: {
                required: true,
                strongPassword: true,
            },
            mdpConfirm: {
                required: true,
                strongPassword: true,
                equalTo: '#form-mdp-inscription',
            },
            prenom: 'required',
            nom: 'required',
            email: {
                required: true,
                email: true,
            },
            ville: {
                required: true,
            },
        },
        messages: {
            pseudo: {
                required: 'Veuillez insérer votre pseudo',
                minlength: 'Votre pseudo doit comporter au minimum 4 caractères',
            },
            mdp: {
                required: 'Veuillez insérer votre mot de passe',
            },
            mdpConfirm: {
                required: 'Confirmer votre mot de passe',
                equalTo: 'Vos deux mots de passe ne concordent pas!',
            },
            nom: {
                required: 'Veuillez insérer votre nom',
            },
            prenom: {
                required: 'Veuillez insérer votre prénom',
            },
            email: {
                required: 'Veuillez insérer votre mail',
            },
            ville: {
                required: 'Veuillez insérer votre ville',
            },
        },
    });

    $('.client-form').validate({
        rules: {
            nom: 'required',
            prenom: 'required',
            rue: 'required',
            numero: 'required',
            codePostal: 'required',
            ville: 'required',
            email: 'required',
            telephone: {
                required: true,
            }
        },
        messages: {
            nom: {
                required: 'Veuillez insérer votre nom',
            },
            prenom: {
                required: 'Veuillez insérer votre prénom',
            },
            rue: {
                required: 'Veuillez insérer votre rue',
            },
            numero: {
                required: 'Veuillez insérer votre numéro',
            },
            codePostal: {
                required: 'Veuillez insérer votre code postal',
            },
            ville: {
                required: 'Veuillez insérer votre ville',
            },
            email: {
                required: 'Veuillez insérer votre email',
            },
            telephone: {
                required: 'Veuillez insérer votre numéro de téléphone',
            },
        }
    });

    $('.devis-form').validate({
        rule:{
            dateDevis: {
                required:true
            },
            montantTotal: {
                required :true,
            },
            dureeTravaux: {
                required: true,
            },
            formTypesAmegamenet: {
                required: true,
            },
    },
    messages: {
        dateDevis: {
            required: 'Veuillez insérer une date de devis'
        },
        montantTotal: {
            required: 'Veuillez insérer un montant total',
        },
        dureeTravaux: {
            required: 'Veuillez insérer une durée des travaux',
        },
        formTypesAmegamenet: {
            required: 'Veuillez cocher au moins 1 type d amenagements',
        },
    }
    });
});
