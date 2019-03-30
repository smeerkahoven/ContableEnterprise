/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}
;

function showModal(idModal) {
    $(`#${idModal}`).modal();
}



let ZERO = "0";
let SI = "S";
let NO = "N";

function AsientoContable() {
    this.position = 0;
    this.idAsiento = '0';
    this.gestion = '0';

    this.idPlanCuenta = '';
    this.idLibro = null;
    this.idNotaTransaccion = null;
    this.idIngresoCajaTransaccion = null;
    this.idNotaCreditoTransaccion = null;
    this.tipo = null;
    this.idBoleto = null;
    this.idCargo = null;

    this.fechaMovimiento = '';
    this.debeMonNac = 0.00;
    this.haberMonNac = 0.00;
    this.debeMonExt = 0.00;
    this.haberMonExt = 0.00;
    this.estado = 'I';
    this.moneda = '';
    this.editable = true;
    this.updated = false;
    this.action = '';
    this.isOK = false;

}

function Comprobante() {
    this.idLibro = '0';
    this.idUsuarioCreador = '';
    this.idUsuarioAnulado = '';
    this.idNumeroGestion = '0';
    this.gestion = '0';
    this.fecha = '';
    this.nombre = '';
    this.estado = 'I'; // 
    this.moneda = 'B';
    this.factorCambiario = '0';
    this.factorMin = 0;
    this.factorMax = 0;
    this.tipo = '';
    this.transacciones = [];
    this.totalDebeMonNac = 0;
    this.totalHaberMonNac = 0;
    this.totalDebeMonExt = 0;
    this.totalHaberMonExt = 0;
    this.difMonNac = 0;
    this.difMonExt = 0;
    this.conErrores = SI;
    this.nombre = "";
}

AsientoContable.prototype = Object.create(AsientoContable.prototype);
AsientoContable.prototype.constructor = AsientoContable;

Comprobante.prototype = Object.create(Comprobante.prototype);
Comprobante.prototype = Comprobante;

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString('en-GB');
let today = new Date().toLocaleDateString('en-GB');
let todayDate = new Date();

let ALL_TIPO_COMPROBANTES = 'all-tipo-comprobantes';
let ALL_PLAN_CUENTAS = "combo/";
let DOLLAR_TODAY = 'dollar/today';
let UFV_TODAY = 'ufv/today';


let METHOD_ANULAR = 'anular';
let METHOD_PENDIENTE = 'pendiente';

let ACTION_CREATE = 'c';
let ACTION_UPDATE = 'u';
let ACTION_DELETE = 'd';

let MONEDA_NACIONAL = "B";
let MONEDA_EXTRANJERA = 'U';
let ELIMINAR_ASIENTO = 'eliminar-asiento';
let ELIMINAR_ASIENTO_UPDATE = 'eliminar-transaccion';

let app = angular.module("jsComprobantes", ['jsComprobantes.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsComprobantes.controllers', []).controller('frmComprobantes',
        ['$scope', '$http', '$uibModal', '$window',

            function ($scope, $http, $window) {
                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlNotaDebito = document.getElementsByName("hdUrlNotaDebito")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var idEmpresa = document.getElementById("idEmpresa");
                var factorMaxMin = document.getElementById("hdFactorMaxMin");
                var username = document.getElementById("hdUserName");
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];

                $scope.EMITIDO = "E";
                $scope.ANULADO = "A";
                $scope.EN_MORA = "M";
                $scope.CANCELADO = "D";
                $scope.PENDIENTE = "P";
                $scope.INICIAL = "I";

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.disableAddButton = false;

                $scope.ASIENTO_DIARIO = 'AD';
                $scope.COMPROBANTE_EGRESO = 'CE';
                $scope.COMPROBANTE_TRASPASO = 'CT';
                $scope.ASIENTO_AJUSTE = 'AJ';
                $scope.COMPROBANTE_INGRESO = 'CI';

                $scope.loading = false;
                $scope.formData = {};
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;
                $scope.dollarToday = 0;

                $scope.search = {tipo: 'AD', estado: $scope.EMITIDO, fechaInicio: firstDay, fechaFin: today};

                $scope.getData = function (urls, method) {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: urls + method,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {

                            if (method === ALL_TIPO_COMPROBANTES) {
                                $scope.comboTiposComprobantes = response.data.content;
                            }

                            if (method === DOLLAR_TODAY) {
                                $scope.dollarToday = response.data.content;
                            }

                            if (method === UFV_TODAY) {
                                $scope.ufvToday = response.data.content;
                            }

                            $scope.loading = false;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }

                $scope.find = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}all`,
                        data: {token: token.value, content: $scope.search},
                        headers: {'Content-Type': 'application/json'}
                        //`${url.value}all?tipo=${$scope.search.tipo}&estado=${$scope.search.estado}&fechaI=${$scope.search.fechaInicio}&fechaF=${$scope.search.fechaFin}`
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.loading = false;
                            $scope.hideMessagesBox();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }

                $scope.edit = function (row) {
                    $scope.loading = true;
                    return $http.get(`${url.value}${row.idLibro}`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData = response.data.content;
                            $scope.loading = false;
                            $scope.showTable = false;
                            $scope.showForm = true;
                            $scope.formData.factorUfv = $scope.ufvToday.valor;
                            $scope.hideMessagesBox();
                            if ($scope.formData.transacciones !== undefined) {
                                for (var i = 0; i < $scope.formData.transacciones.length; i++) {
                                    $scope.formData.transacciones[i].idPlanCuenta = $scope.findCta($scope.formData.transacciones[i].idPlanCuenta.id, $scope.comboPlanCuentas);
                                }
                            }
                            //$scope.sumarTotales();
                            $scope.sumarDiferencias()
                            $scope.habilitarBotones();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }


                $scope.habilitarBotones = function () {
                    switch ($scope.formData.estado) {
                        case $scope.ANULADO :
                            $scope.disableAnularButton = true;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = true;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = true;
                            $scope.disableGuardarTransaccion = true;
                            $scope.disableEliminarTransaccion = true;
                            break;
                        case $scope.EMITIDO :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = true;
                            $scope.disableImprimirButton = false;
                            $scope.disableEditarTransaccion = true;
                            $scope.disableEliminarTransaccion = true;
                            $scope.disableGuardarTransaccion = true;
                            break;
                        case $scope.PENDIENTE :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = true;
                            $scope.disableAddButton = false;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = false;
                            $scope.disableEliminarTransaccion = false;
                            $scope.disableGuardarTransaccion = false;
                            if ($scope.existenTransaccionesInvalidas()) {
                                $scope.disableGuardarButton = true;
                            } else {
                                $scope.disableGuardarButton = false;
                            }
                            break;
                        case $scope.INICIAL :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = false;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = false;
                            $scope.disableEliminarTransaccion = false;
                            $scope.disableGuardarTransaccion = false;
                            break;
                    }

                }

                $scope.anular = function () {

                    if ($scope.formData.estado === $scope.INICIAL) {
                        $scope.showForm = false;
                        $scope.showTable = true;
                        return;
                    }

                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}${METHOD_ANULAR}`,
                        data: {token: token.value, content: angular.toJson($scope.modalAnularData.row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.modalAnularData.row.estado = $scope.ANULADO;
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.loading = false;
                            $scope.showForm = false;
                            $scope.showTable = true;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, $scope.errorFunction);
                }

                $scope.imprimir = function (data) {
                    window.open(`../../ComprobanteReporteServlet?idLibro=${data.idLibro}`, '_target');
                }

                $scope.formHasErrors = function () {
                    if ($scope.formData.tipo === $scope.ASIENTO_DIARIO ||
                            $scope.formData.tipo === $scope.COMPROBANTE_INGRESO) {
                        if ($scope.formData.idCliente === undefined) {
                            $scope.showErrorMessage = "Ingrese un Cliente";
                            return true;
                        }
                        if ($scope.formData.idCliente.id === undefined) {
                            $scope.showErrorMessage = "El cliente ingresado no es valido";
                            return true;
                        }
                    } else

                    if ($scope.formData.tipo === $scope.ASIENTO_AJUSTE ||
                            $scope.formData.tipo === $scope.COMPROBANTE_TRASPASO ||
                            $scope.formData.tipo === $scope.COMPROBANTE_EGRESO) {
                        if ($scope.formData.nombre === undefined) {
                            $scope.showErrorMessage = "Ingrese el Nombre del Comprobante";
                        }
                    }

                    if ($scope.formData.concepto === undefined) {
                        $scope.showErrorMessage = "Ingrese un Concepto.";
                        return true;
                    }

                    return false;
                }

                $scope.pendiente = function () {
                    //$("frmPendiente").modal() ;
                    if ($scope.formHasErrors()) {
                        showAlert(ERROR_RESPUESTA_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    if (!$scope.myForm.$valid)
                        return;

                    showModal("frmPendiente");
                }

                $scope.colocarPendiente = function () {
                    if ($scope.formData.estado === $scope.INICIAL) {
                        $scope.save($scope.PENDIENTE);
                    } else if ($scope.formData.estado === $scope.ANULADO) {
                        $scope.loading = true;
                        return $http({
                            method: 'POST',
                            url: `${url.value}${METHOD_PENDIENTE}`,
                            data: {token: token.value, content: angular.toJson($scope.modalAnularData.row)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            if (response.data.code === 201) {
                                $scope.modalAnularData.row.estado = $scope.PENDIENTE;
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulSuccess = true;
                                $scope.loading = false;
                                $scope.showForm = false;
                                $scope.showTable = true;
                                goScrollTo('#restful-success');
                            } else {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulError = true;
                                goScrollTo('#restful-success');
                            }
                        }, $scope.showErrorFunction());
                    } else if ($scope.formData.estado === $scope.PENDIENTE) {

                    }
                }

                $scope.newComprobante = function (tipo) {
                    $scope.formData = new Comprobante();
                    $scope.formData.factorCambiario = $scope.dollarToday.valor;
                    $scope.formData.estado = $scope.INICIAL;
                    $scope.formData.factorUfv = $scope.ufvToday.valor;
                    $scope.formData.fecha = $scope.dollarToday.fecha;
                    $scope.formData.tipo = tipo;
                    $scope.formData.idUsuarioCreador = username.value;
                    $scope.formData.idEmpresa = idEmpresa.value;

                    $scope.nuevo();
                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.disableAddButton = false;
                    $scope.hideMessagesBox();

                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);
                    myForm.reset();

                    $scope.habilitarBotones();


                }

                $scope.verTransaccion = function (row) {
                    if (row.tipo === 'B') {
                        //BOLETO
                        $scope.loadTransaccionBoleto(row);
                        showModalWindow('#frmTransaccion');
                    } else if (row.tipo === 'C') {
                        //CARGO
                        $scope.loadTransaccionCargo(row);
                        showModalWindow('#frmTransaccion');
                    } else if (row.tipo === 'P') {
                        //PAQUETE
                        $scope.loadTransaccionPaquete(row);
                        showModalWindow('#frmTransaccion');
                    }

                }

                $scope.loadTransaccionBoleto = function (row) {
                    return $http({
                        method: 'POST',
                        url: `${url.value}transaccion-boleto/get`,
                        data: {token: token.value, content: angular.toJson(row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.verForm = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }

                $scope.loadTransaccionCargo = function (row) {
                    return $http({
                        method: 'POST',
                        url: `${url.value}transaccion-cargo/get`,
                        data: {token: token.value, content: angular.toJson(row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.verForm = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }

                $scope.loadTransaccionPaquete = function (row) {
                    return $http({
                        method: 'POST',
                        url: `${url.value}transaccion-paquete/get`,
                        data: {token: token.value, content: angular.toJson(row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.verForm = response.data.content;
                            //console.log($scope.verForm);
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction
                            );
                }

                $scope.existenTransaccionesInvalidas = function () {

                    if ($scope.formData.transacciones === undefined) {
                        return true;
                    }

                    //si no hay transacciones
                    if ($scope.formData.transacciones.length === 0) {
                        return true;
                    }

                    //si existen transacciones
                    var disable = false;
                    if ($scope.formData.transacciones.length > 0) {
                        for (var i in $scope.formData.transacciones) {
                            if ($scope.formData.transacciones[i].idPlanCuenta === undefined) {
                                return true;
                            }

                            if ($scope.formData.transacciones[i].moneda === undefined) {
                                return true;
                            }
                            /* if (!$scope.formData.transacciones[i].isOK) {
                             retur*n true;
                             }*/
                        }
                    }
                    //existen diferencias
                    disable = $scope.existenDiferencias();
                    return disable;
                }

                $scope.existenDiferencias = function () {

                    if ($scope.formData.difMonNac != (0.00) && $scope.formData.difMonExt != (0.00)) {
                        $scope.formData.conErrores = true;
                        return true;
                    }
                    return false;
                }

                $scope.obtenerPlanCuentas = function () {
                    return $http({
                        method: 'POST',
                        url: urlPlanCuentas.value + ALL_PLAN_CUENTAS + idEmpresa.value,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboPlanCuentas = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction
                            );
                }

                $scope.save = function (estado) {
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
                        //$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    if ($scope.formHasErrors()) {
                        showAlert(ERROR_RESPUESTA_TITLE, $scope.showErrorMessage);
                        return;
                    }

                    if ($scope.formData.estado === $scope.INICIAL) {
                        $scope.loading = true;
                        if (estado === $scope.PENDIENTE)
                            $scope.formData.conErrores = SI;
                        else
                            $scope.formData.conErrores = NO;

                        $scope.formData.estado = estado;
                        $http({
                            method: 'POST',
                            url: `${url.value}save`,
                            data: {token: token.value, content: angular.toJson($scope.formData)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            if (response.data.code === 201) {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulSuccess = true;
                                $scope.formData = response.data.entidad;
                                $scope.disableGuardarButton = true;
                                $scope.disablePendienteButton = false;
                                $scope.disableAnularButton = false;
                                $scope.habilitarBotones();
                                if (estado === $scope.APROBADO) {
                                    $scope.imprimir($scope.formData);
                                }
                                goScrollTo('#restful-success');
                                //$scope.showForm = false;
                                //$scope.showTable = true;
                                //$scope.getData(url.value, 'all');
                            } else {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulError = true;
                                //$scope.showForm = false;
                            }
                            $scope.loading = false;

                        }, $scope.errorFunction);
                    } else {
                        $scope.update();
                    }
                };

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: `${url.value}update`,
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showForm = false;
                            $scope.showTable = true;
                            goScrollTo('#restful-success');
                            //$scope.getData();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }

                    }, $scope.errorFunction);
                };

                $scope.delete = function () {
                    if ($scope.modalConfirmation.method === ELIMINAR_ASIENTO) {
                        $scope.eliminarAsiento();
                    }
                };

                $scope.eliminarAsientoFromTable = function () {
                    for (var i = 0; i <= $scope.formData.transacciones.length - 1; i++) {
                        if ($scope.formData.transacciones[i] === $scope.modalConfirmation.row) {
                            $scope.formData.transacciones.splice(i, 1);
                            $scope.sumarTotales();
                            $scope.sumarDiferencias();
                            break;
                        }
                    }
                }

                $scope.eliminarAsiento = function () {
                    if ($scope.modalConfirmation.row.idAsiento === ZERO) {
                        $scope.eliminarAsientoFromTable();
                    } else {
                        $http({
                            method: 'POST',
                            url: `${url.value}delete-transaction/${$scope.modalConfirmation.row.idAsiento}`,
                            data: {token: token.value, content: angular.toJson($scope.formData)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            $scope.sumarTotales();
                            $scope.sumarDiferencias();
                            $scope.eliminarAsientoFromTable();
                            if ($scope.existenTransaccionesInvalidas()) {
                                $scope.disableGuardarButton = true;
                            } else {
                                $scope.disableGuardarButton = false;
                            }
                        }, $scope.errorFunction);
                    }
                }



                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.editTransaccion = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.hideMessagesBox();
                    item.editable = true;
                    item.superDisabled = true;
                    $scope.disableAddButton = true;
                }

                $scope.saveTransaccion = function (item) {
                    $scope.showRowError = false;
                    if (item.idPlanCuenta.id === undefined) {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe ingresar una Cuenta Valida.";
                        return false;
                    }

                    if (item.moneda === '') {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe seleccionar un tipo de moneda.";
                        return false;
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        if (item.debeMonNac === undefined || item.haberMonNac === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }

                        if (item.debeMonNac === 0 && item.haberMonNac === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_EXTRANJERA) {
                        if (item.debeMonExt === undefined || item.haberMonExt === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }

                        if (item.debeMonExt === 0 && item.haberMonExt === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        item.debeMonNac = parseFloat(Math.round(item.debeMonNac * 100) / 100).toFixed(2);
                        item.haberMonNac = parseFloat(Math.round(item.haberMonNac * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonExt === undefined || item.debeMonExt <= 0) {
                            item.debeMonExt = parseFloat(item.debeMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                            //if (item.haberMonExt === undefined || item.haberMonExt <= 0) {
                            item.haberMonExt = parseFloat(item.haberMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }
                        

                    } else if (item.moneda === MONEDA_EXTRANJERA) {
                        item.debeMonExt = parseFloat(Math.round(item.debeMonExt * 100) / 100).toFixed(2);
                        item.haberMonExt = parseFloat(Math.round(item.haberMonExt * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonNac <= 0) {
                            item.debeMonNac = parseFloat(item.debeMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}

                            //if (item.haberMonNac <= 0) {
                            item.haberMonNac = parseFloat(item.haberMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }
                        
                    }
                    $scope.sumarTotales();
                    $scope.sumarDiferencias();
                    item.editable = false;
                    item.action = ACTION_UPDATE;
                    item.isOK = true;
                    $scope.disableAddButton = false;

                    if ($scope.existenTransaccionesInvalidas()) {
                        $scope.disableGuardarButton = true;
                    } else {
                        $scope.disableGuardarButton = false;
                    }

                    if ($scope.existeDiferencias) {
                        $scope.existeDiferencias = false;
                    }

                    if ($scope.formData.estado === $scope.PENDIENTE) {
                        if (item.idAsiento === ZERO) {
                            $http({
                                method: 'POST',
                                url: `${url.value}add-transaction`,
                                data: {token: token.value, content: angular.toJson($scope.formData)},
                                headers: {'Content-Type': 'application/json'}
                            }).then(function (response) {
                                item = response.data.entidad;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idAsiento = item.idAsiento;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idLibro = item.idLibro;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].gestion = item.gestion;
                            }, $scope.errorFunction
                                    )
                        } else {
                            $http({
                                method: 'POST',
                                url: `${url.value}update-transaction`,
                                data: {token: token.value, content: angular.toJson($scope.formData)},
                                headers: {'Content-Type': 'application/json'}
                            }).then(function (response) {

                            }, $scope.errorFunction)
                        }
                    }
                }



                $scope.corregirTransaccion = function (item) {
                    $scope.showRowError = false;
                    if (item.idPlanCuenta.id === undefined) {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe ingresar una Cuenta Valida.";
                        return false;
                    }

                    if (item.moneda === '') {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe seleccionar un tipo de moneda.";
                        return false;
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        if (item.debeMonNac === undefined || item.haberMonNac === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }

                        if (item.debeMonNac === 0 && item.haberMonNac === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_EXTRANJERA) {
                        if (item.debeMonExt === undefined || item.haberMonExt === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }

                        if (item.debeMonExt === 0 && item.haberMonExt === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        item.debeMonNac = parseFloat(Math.round(item.debeMonNac * 100) / 100).toFixed(2);
                        item.haberMonNac = parseFloat(Math.round(item.haberMonNac * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonExt === undefined || item.debeMonExt <= 0) {
                            item.debeMonExt = parseFloat(item.debeMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                            //if (item.haberMonExt === undefined || item.haberMonExt <= 0) {
                            item.haberMonExt = parseFloat(item.haberMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }

                    } else if (item.moneda === MONEDA_EXTRANJERA) {
                        item.debeMonExt = parseFloat(Math.round(item.debeMonExt * 100) / 100).toFixed(2);
                        item.haberMonExt = parseFloat(Math.round(item.haberMonExt * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonNac <= 0) {
                            item.debeMonNac = parseFloat(item.debeMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}

                            //if (item.haberMonNac <= 0) {
                            item.haberMonNac = parseFloat(item.haberMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }
                    }

                    $scope.sumarTotales();
                    $scope.sumarDiferencias();
                    item.editable = false;
                    item.action = ACTION_UPDATE;
                    item.isOK = true;

                    //console.log(`$scope.existeDiferencias:${$scope.existeDiferencias}`);

                    if ($scope.existenTransaccionesInvalidas()) {
                        $scope.disableGuardarButton = true;
                    } else {
                        $scope.disableGuardarButton = false;
                    }

                    //if ($scope.formData.estado === $scope.PENDIENTE) {
                    if (item.idAsiento === ZERO) {
                        $http({
                            method: 'POST',
                            url: `${url.value}add-correccion`,
                            data: {token: token.value, content: angular.toJson($scope.formData)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            item = response.data.entidad;
                            $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idAsiento = item.idAsiento;
                            $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idLibro = item.idLibro;
                            $scope.formData.transacciones[$scope.formData.transacciones.length - 1].gestion = item.gestion;

                            //console.log(`$scope.existeDiferencias:${$scope.existeDiferencias}`);

                            if ($scope.existeDiferencias) {
                                $scope.ngDisabledBtnCorregir = false;
                            } else {
                                $scope.ngDisabledBtnCorregir = true;
                            }

                            //console.log(`$scope.showDifDebeExt:${$scope.showDifDebeExt}`);
                            //console.log(`$scope.showDifDebeNac:${$scope.showDifDebeNac}`);
                            //console.log(`$scope.showDifHaberExt:${$scope.showDifHaberExt}`);
                            //console.log(`$scope.showDifHaberNac:${$scope.showDifHaberNac}`);

                            //console.log(`$scope.ngDisabledBtnCorregir:${$scope.ngDisabledBtnCorregir}`);

                        }, $scope.errorFunction)
                    } else {
                        $http({
                            method: 'POST',
                            url: `${url.value}update-transaction`,
                            data: {token: token.value, content: angular.toJson($scope.formData)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {

                        }, $scope.errorFunction)
                    }
                    // }
                }

                $scope.sumarTotales = function () {
                    $scope.formData.totalDebeMonNac = 0;
                    $scope.formData.totalHaberMonNac = 0;
                    $scope.formData.totalHaberMonExt = 0;
                    $scope.formData.totalDebeMonExt = 0;
                    for (var i in $scope.formData.transacciones) {
                        $scope.formData.totalDebeMonNac = parseFloat((Number($scope.formData.totalDebeMonNac)) + (Number($scope.formData.transacciones[i].debeMonNac === undefined ? Number(null) : $scope.formData.transacciones[i].debeMonNac))).toFixed(2);
                        $scope.formData.totalHaberMonNac = parseFloat((Number($scope.formData.totalHaberMonNac)) + (Number($scope.formData.transacciones[i].haberMonNac === undefined ? Number(null) : $scope.formData.transacciones[i].haberMonNac))).toFixed(2);
                        $scope.formData.totalDebeMonExt = parseFloat((Number($scope.formData.totalDebeMonExt)) + (Number($scope.formData.transacciones[i].debeMonExt === undefined ? Number(null) : $scope.formData.transacciones[i].debeMonExt))).toFixed(2);
                        $scope.formData.totalHaberMonExt = parseFloat((Number($scope.formData.totalHaberMonExt)) + (Number($scope.formData.transacciones[i].haberMonExt === undefined ? Number(null) : $scope.formData.transacciones[i].haberMonExt))).toFixed(2);
                    }
                }

                $scope.sumarDiferencias = function () {
                    $scope.formData.difMonNac = parseFloat(Number($scope.formData.totalDebeMonNac) - Number($scope.formData.totalHaberMonNac)).toFixed(2);
                    $scope.formData.difMonExt = parseFloat(Number($scope.formData.totalDebeMonExt) - Number($scope.formData.totalHaberMonExt)).toFixed(2);

                    $scope.showDifHaberNac = ($scope.formData.difMonNac > 0);
                    $scope.showDifDebeNac = ($scope.formData.difMonNac < 0);

                    $scope.showDifHaberExt = ($scope.formData.difMonExt > 0);
                    $scope.showDifDebeExt = ($scope.formData.difMonExt < 0);

                    $scope.existeDiferencias = $scope.showDifDebeExt || $scope.showDifDebeNac || $scope.showDifHaberExt || $scope.showDifHaberNac;


                }

                $scope.ngChange = function (key, method, row) {
                    // TAB
                    if (key.keyCode !== 9) {
                        row.haberMonNac = (method == 'debe') ? 0 : row.haberMonNac;
                        row.haberMonExt = (method == 'debe') ? 0 : row.haberMonExt;

                        row.debeMonNac = (method == 'haber') ? 0 : row.debeMonNac;
                        row.debeMonExt = (method == 'haber') ? 0 : row.debeMonExt;
                    }
                }

                $scope.clickCorregir = function () {
                    $scope.ngDisabledBtnCorregir = true;
                    $scope.addTransaccion();
                }

                $scope.addTransaccion = function () {
                    var newAsiento = new AsientoContable;
                    newAsiento.action = ACTION_CREATE;
                    newAsiento.position = $scope.formData.transacciones.length;
                    $scope.formData.transacciones.push(newAsiento);
                    $scope.disableAddButton = true;
                    $scope.disableGuardarButton = true;
                }

                $scope.onChangeMoneda = function (row) {
                    row.debeMonNac = 0.00;
                    row.haberMonNac = 0.00;
                    row.debeMonExt = 0.00;
                    row.haberMonExt = 0.00;
                }
                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.hideMessagesBox();
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    //$scope.mainGrid = [];
                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
                }

                $scope.showConfirmation = function (title, message, method) {
                    swal({
                        title: title,
                        text: message,
                        type: 'info',
                        buttons: {},
                    }).then(method);
                }

                $scope.modalEliminarAsiento = function (row) {
                    $scope.modalConfirmation = {id: row.position, nombre: '', method: ELIMINAR_ASIENTO, row: row};
                }

                $scope.modalAnular = function (row) {
                    $scope.modalAnularData = {id: row.position, nombre: '', method: METHOD_ANULAR, row: row};
                }

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }

                $scope.findCta = function (cta, input) {
                    var i = 0;
                    if (input === undefined)
                        return;
                    for (i; i < input.length; i++) {
                        if (input[i].id == cta) {
                            return input[i];
                        }
                    }
                }

                $scope.getClientes = function () {
                    return $http({
                        url: `${urlClientes.value}all-cliente-combo`,
                        method: 'POST',
                        data: {token: token.value},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboCliente = response.data.content;
                        }
                    }, $scope.errorFunction);
                }

                $scope.getData(url.value, ALL_TIPO_COMPROBANTES);
                $scope.getData(urlFactores.value, DOLLAR_TODAY);
                $scope.getData(urlFactores.value, UFV_TODAY);
                $scope.obtenerPlanCuentas();
                $scope.getClientes();

                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.factorCambiario', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $scope.showErrorCtaDevolucionMonExt = false;
                    }
                });
            }
        ]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});

app.filter('makePositive', function () {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        return  Math.abs(new Number(input).toFixed(2));
    }
});

app.filter('tipoComprobante', function () {
    return function (tipo, scope) {
        switch (tipo) {
            case 'AD':
                return 'Asiento Diario';
            case 'CI' :
                return 'Comprobante de Ingreso';
            case 'AJ' :
                return 'Asiento de Ajuste';
            case 'CE' :
                return 'Comprobante de Egreso';
            case 'CT' :
                return 'Comprobante de Traspaso';
        }
    }
});

app.filter('planCuenta', function () {
    return function (tipo, scope) {
        let object = scope.findCta(tipo, scope.comboPlanCuentas);
        if (object !== undefined) {
            return object;
        }
    }
});

app.filter('checkStatus', function () {
    return function (estado) {
        switch (estado) {
            case 'I':
                return "INICIAL";
            case 'A' :
                return "ANULADO";
            case 'P' :
                return "PENDIENTE";
            case 'E' :
                return "EMITIDO";
            case 'R' :
                return "RECUPERADO";
            default :
                return '';
        }
    }
});

app.filter('printTipo', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'B':
                return 'BOLETO';
            case 'P' :
                return 'PAQUETE';
            case 'A' :
                return 'ALQUILER AUTO';
            case 'H' :
                return 'HOTEL';
            case 'S' :
                return 'SEGURO';
            case 'R' :
                return 'RESERVA';
            default :
                return '-';
                break;
        }
    }
});

app.filter('printMoneda', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'B':
                return 'BOB.';
            case 'U' :
                return 'USD.';
        }
    }
});

app.filter('printTipoBoleto', function () {
    return function (estado) {
        switch (estado) {
            case 'SA':
                return "SABRE";
            case 'SV' :
            case 'MV' :
            case 'AV' :
                return "VOID";
            case 'AM' :
                return "AMADEUS";
            case 'MA' :
                return "MANUAL";
            case 'R' :
                return "RECUPERADO";
            default :
                return '';
        }
    }
});

app.filter('printDiferencias', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        else {

            var difMonNac = Number(0);
            var difMonExt = Number(0);

            var showDifHaberNac = false;
            var showDifDebeNac = false;
            var showDifHaberExt = false;
            var showDifDebeExt = false;

            var existeDiferencias = false;

            difMonNac = parseFloat(Number(input.totalDebeMonNac) - Number(input.totalHaberMonNac)).toFixed(2);
            difMonExt = parseFloat(Number(input.totalDebeMonExt) - Number(input.totalHaberMonExt)).toFixed(2);

            showDifHaberNac = (difMonNac > 0);
            showDifDebeNac = (difMonNac < 0);

            showDifHaberExt = (difMonExt > 0);
            showDifDebeExt = (difMonExt < 0);

            existeDiferencias = showDifDebeExt || showDifDebeNac || showDifHaberExt || showDifHaberNac;

            return existeDiferencias ? 'table-danger' : '-'



        }
    }
});

app.filter('printNumber', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        else {
            var value = new Number(input).toFixed(2);
            return  value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    }
});

app.controller('frView', ['$scope', '$http', 'record', function ($scope, $http, record) {
        function init() {
            $scope.sucursal = record.content;
        }
        init();
    }]);

app.directive('pageSelect', function () {
    return {
        restrict: 'E',
        template: '<input type="text" class="form-control select-page" style="width:50px;" ng-model="inputPage" ng-change="selectPage(inputPage)">',
        link: function (scope, element, attrs) {
            scope.$watch('currentPage', function (c) {
                scope.inputPage = c;
            });
        }
    }
});


app.directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value);
            });
        }
    };
});
