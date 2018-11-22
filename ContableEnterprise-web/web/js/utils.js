let ERROR_VERIFICACION_TITLE = 'Error de Verificacion';
let ERROR_RESPUESTA_TITLE = 'Error en la Respuesta';

let VERIFIQUE_VALORES_REQUERIDOS = 'Verifique los mensajes de los valores requeridos';
let ERROR_NOTA_DEBITO_EMITIDA = 'La nota de debito se encuentra como Emitida, no puede finalizarla.';

function showBackground() {
    $('#frmBackground').modal('show');
}

function hideBackground() {
    $('#frmBackground').modal('hide');
}

function showModalWindow(idModal) {
    $(idModal).modal('show');
}

function hideModalWindow(idModal) {
    $(idModal).modal('hide');
}

(function($) {
    $.fn.goTo = function() {
        $('html, body').animate({
            scrollTop: $(this).offset().top + 'px'
        }, 'fast');
        return this; // for chaining...
    }
})(jQuery);

function goScrollTo(element){
    $(element).goTo();
}