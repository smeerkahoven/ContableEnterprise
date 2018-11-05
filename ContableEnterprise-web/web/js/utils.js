function showBackground() {
    console.log("show");
    $('#frmBackground').modal('show');
}

function hideBackground() {
    console.log("hiding");
    $('#frmBackground').modal('hide');
}

function showModalWindow(idModal){
    $(idModal).modal('show');
}

function hidModalWindow(idModal){
    $(idModal).modal('hide');
}