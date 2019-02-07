let ERROR_VERIFICACION_TITLE = 'Error de Verificacion';
let ERROR_RESPUESTA_TITLE = 'Error en la Respuesta';
let ERROR_TITLE = 'Error ';

let SUCCESS_TITLE = 'Correcto!'
let WARNING_TITLE = 'Advertencia!'

let VERIFIQUE_VALORES_REQUERIDOS = 'Verifique los mensajes de los valores requeridos';
let ERROR_NOTA_DEBITO_EMITIDA = 'La nota de debito se encuentra como Emitida, no puede finalizarla.';

function showBackground() {
    $('#frmBackground').modal('show');

    setTimeout(function () {
        $('#frmBackground').modal('hide');
    }, 1000);
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

(function ($) {
    $.fn.goTo = function () {
        $('html, body').animate({
            scrollTop: $(this).offset().top + 'px'
        }, 'fast');
        return this; // for chaining...
    }
})(jQuery);

function goScrollTo(element) {
    $(element).goTo();
}

function goScrollToSuccess() {
    $('#restful-success').goTo();
}

function showAlert(title, message) {
    swal({
        title: title,
        html: message,
        type: 'error'
    });
}

function  showWarning(title, message) {
    swal({
        title: title,
        html: message,
        type: 'warning'
    });
}

function showSuccess(title, message) {
    swal({
        title: title,
        html: message,
        type: 'success'
    });
}
function  showInfo(title, message) {
    swal({
        title: title,
        html: message,
        type: 'info'
    })
}
;

function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}
;

function validateNumber(event) {
    var key = window.event ? event.keyCode : event.which;
    if (event.keyCode === 8 || event.keyCode === 46) {
        return true;
    } else if (key < 48 || key > 57) {
        return false;
    } else {
        return true;
    }
}
;

function validateOnlyNumber(event) {
    var key = window.event ? event.keyCode : event.which;
    if (key < 48 || key > 57) {
        return false;
    } else {
        return true;
    }
}
;

var specialKeys = new Array();
specialKeys.push(8); //Backspace
specialKeys.push(9); //Tab
specialKeys.push(46); //Delete
specialKeys.push(36); //Home
specialKeys.push(35); //End
specialKeys.push(37); //Left
specialKeys.push(39); //Right

function IsAlphaNumeric(e) {
    var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57) || 
            (keyCode >= 65 && keyCode <= 90) || 
            (keyCode >= 97 && keyCode <= 122) ||
            (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
    return ret;
}
