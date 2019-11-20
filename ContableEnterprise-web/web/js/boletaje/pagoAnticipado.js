$('#tblNotas').on('click', '.clickable-row', function (event) {
    $(this).addClass('bg-selected').siblings().removeClass('bg-selected');
});

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString('en-GB');
let today = new Date().toLocaleDateString('en-GB');
let todayDate = new Date();
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function PagoAnticipado() {
    this.idPagoAnticipado = null;
    this.idCliente = null;
    this.factorCambiario = null;
    this.moneda = null;
    this.montoAnticipado = null;
    this.montoTotalAcreditado = null;
    this.concepto = null;
    this.formaPago = null;
    this.nroCheque = null;
    this.idBanco = null;
    this.idCuentaDeposito = null;
    this.nroDeposito = null;
    this.idTarjetaCredito = null;
    this.nroTarjeta = null;
    this.idUsuarioCreador = null;
    this.fechaInsert = null;
    this.estado = null;
}

function PagoAnticipadoTransaccion() {
    this.idPagoAnticipadoTransaccion = null;
    this.idPagoAnticipado = null;
    this.descripcion = null;
    this.moneda = null;
    this.monto = null;
    this.fechaInsert = null;
    this.idNotaTransaccion = null;
    this.idNotaDebito = null;
    this.estado = null;
    this.montoCancelarUsd = null;
    this.montoCancelarBs = null;
}

function Devolucion() {
    this.idDevolucion = null;
    this.idCliente = null;
    this.nombreCliente = null;
    this.idNotaDebito = null;
    this.idPagoAnticipado = null;
    this.fechaEmision = null;
    this.moneda = null;
    this.factor = null;
    this.monto = null;
    this.concepto = null;
    this.tipoDevolucion = null;
    this.nroCheque = null;
    this.nroDeposito = null;
    this.idCuentaDeposito = null;
    this.idBanco = null;
    this.fechaInsert = null;
    this.idUsuariocreador = null;
    this.estado = null;
}

PagoAnticipado.prototype = Object.create(PagoAnticipado.prototype);
PagoAnticipado.prototype.constructor = PagoAnticipado;

PagoAnticipadoTransaccion.prototype = Object.create(PagoAnticipadoTransaccion.prototype);
PagoAnticipadoTransaccion.prototype.constructor = PagoAnticipadoTransaccion;

Devolucion.prototype = Object.create(Devolucion.prototype);
Devolucion.prototype.constructor = Devolucion;

var app = angular.module("jsPagoAnticipado", ['jsPagoAnticipado.controllers',
    'smart-table', 'ui.bootstrap', 'jcs-autoValidate']);

angular.module('jcs-autoValidate').run([
    'defaultErrorMessageResolver',
    function (defaultErrorMessageResolver) {
        defaultErrorMessageResolver.setI18nFileRootPath('../../js/lang');
        defaultErrorMessageResolver.setCulture('es-CO');
    }
]);
angular.module('jsPagoAnticipado.controllers', []).controller('frmPagoAnticipado',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlTarjeta = document.getElementsByName("hdUrlTarjetas")[0];
                var urlBancos = document.getElementsByName("hdUrlBancos")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var idEmpresa = document.getElementsByName("idEmpresa")[0].value;
                var factorMaxMin = document.getElementsByName("hdFactorMaxMin")[0];
                var urlComprobante = document.getElementsByName("hdComprobante")[0];

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.PENDIENTE = 'P';
                $scope.EMITIDO = 'E';
                $scope.ANULADO = 'A';
                $scope.CON_SALDO = 'D';
                $scope.SIN_SALDO = 'S';

                $scope.EFECTIVO = "E";
                $scope.CHEQUE = "H";
                $scope.DEPOSITO = "D";
                $scope.TARJETA = "T";

                $scope.CABECERA = "C";
                $scope.TRANSACCION = "AC";
                $scope.DEVOLUCION = "DE";
                $scope.TODO = "D";


                $scope.MONEDA_NACIONAL = 'B';
                $scope.MONEDA_EXTRANJERA = 'U';

                $scope.PAGO_ANTICIPADO = 'P';

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = [];
                $scope.modalConfirmation = {};
                $scope.search = {fechaInicio: firstDay, fechaFin: today};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;

                $scope.find = function () {
                    $scope.showClienteRequieredSearch = false;

                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;

                    if ($scope.search.idCliente !== undefined) {
                        if ($scope.search.idCliente.id === undefined) {
                            $scope.showClienteRequieredSearch = true;
                            return;
                        }
                    }

                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: `${url.value}all`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.mainGrid = [];
                        }
                    }, $scope.errorFunction);
                }

                $scope.save = function () {
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    $scope.click = true;
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return false;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    if (!$scope.hasFormaDePagos()) {
                        $scope.showAlert(ERROR_TITLE, $scope.errorFormaPagoMessage);
                        return;
                    }

                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: url.value + 'save',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showForm = false;
                            $scope.showTable = true;
                            //$scope.getData(url.value, 'all');
                            //imprimir
                            $scope.imprimir($scope.CABECERA, $scope.formData);
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                        $scope.loading = false;

                    }, function (error) {
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        $scope.showForm = false;
                    });
                };

                $scope.saveTransaccion = function () {

                    if ($scope.showErrorMontoIngresado) {
                        return;
                    }

                    $scope.trx.idPagoAnticipado = $scope.formData.idPagoAnticipado;

                    showBackground();

                    return $http({
                        url: `${url.value}save/transaccion`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.trx)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        hideModalWindow('frmBackground');
                        if (response.data.code === 201) {
                            var pago = response.data.entidad;
                            var trx = response.data.content;

                            $scope.formData.montoTotalAcreditado = pago.montoTotalAcreditado;
                            $scope.formData.montoTotalDisponible = pago.montoTotalDisponible;
                            $scope.loadTransacciones();

                            //revisar estas llamadas de aqui
                            $scope.imprimir($scope.TRANSACCION, trx);

                            hideModalWindow('#frmPagoAnticipadoTransaccion');

                        } else if (response.data.code === 200) {
                            showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                        }
                    }, $scope.errorFunction);
                }


                $scope.imprimir = function (option, formData) {
                    console.log(formData);
                    switch (option) {
                        case $scope.CABECERA://Nota Debito
                            window.open(`../../PagoAnticipadoCabeceraReport?idPago=${formData.idPagoAnticipado}`, '_blank');
                            break;

                        case $scope.TODO: //Ingreso de Caja
                            window.open(`../../PagoAnticipadoTodoReport?idPago=${formData.idPagoAnticipado}`, '_blank');
                            break;

                        case $scope.TRANSACCION: // Comprobante de Ingreso
                            window.open(`../../PagoAnticipadoTransaccionReport?idTrx=${formData.idPagoAnticipadoTransaccion}`, '_blank');
                            break;

                        case $scope.DEVOLUCION:
                            window.open(`../../DevolucionReport?idDev=${formData.idDevolucion}`, '_blank');
                            break;
                        default:
                            break;
                    }
                }

                $scope.editTransaccion = function (row) {
                    $scope.showFrmEditarTrx = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}get/transaccion`,
                        data: {token: token.value, content: angular.toJson(row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {

                            $scope.trx = response.data.content;

                            if ($scope.trx.montoTransaccionBs) {
                                $scope.trx.montoBs = $scope.trx.montoTransaccionBs;
                            }

                            if ($scope.trx.montoTransaccionUsd) {
                                $scope.trx.montoUsd = $scope.trx.montoTransaccionUsd;
                            }

                            showModalWindow('#frmPagoAnticipadoTransaccion');
                        } else {
                            showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                        }

                    }, $scope.errorFunction);
                }

                $scope.loadTransacciones = function () {
                    return $http({
                        method: 'POST',
                        url: `${url.value}all/transaccion`,
                        headers: {'Content-Type': 'application/json'},
                        data: {token: token.value,
                            content: angular.toJson($scope.formData)
                        }
                    }
                    ).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.formData.transacciones = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            },
                            $scope.errorFunction
                            );
                }

                $scope.formHasError = function () {
                    if ($scope.formData.idCliente === undefined) {
                        return true;
                    }
                    if ($scope.formData.idCliente.id === undefined) {
                        return true;
                    }

                    if ($scope.formData.moneda === undefined) {
                        return true;
                    }

                    if ($scope.formData.montoAnticipado === undefined) {
                        return true;
                    }

                    if ($scope.formData.concepto === undefined) {
                        return true;
                    }

                    return false;
                }


                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    $scope.loading = true;

                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showForm = false;
                            $scope.showTable = true;
                            //$scope.getData();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }

                    }, function (error) {
                        $scope.loading = false;
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                        //$scope.showForm = true;
                    });
                };

                $scope.devolver = function () {
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;

                    return $http({
                        url: `${url.value}crear-devolucion`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.dev = response.data.content;
                            showModalWindow('#frmDevolucion');
                        } else {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = response.data.content;
                        }
                    }, $scope.errorFunction);
                }

                $scope.saveDevolucion = function () {
                    if ($scope.hasFormDevolucionErrors()) {
                        showAlert(ERROR_RESPUESTA_TITLE, $scope.showDevolucionErrores);
                        return;
                    }

                    showBackground();

                    return $http({
                        url: `${url.value}save-devolucion`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.dev)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            hideModalWindow('#frmDevolucion');
                            $scope.showForm = false;
                            $scope.showTable = true;
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.mainGrid = {};
                            $scope.imprimir($scope.DEVOLUCION, response.data.entidad);
                        } else {
                            hideModalWindow('#frmDevolucion');
                            showWarning(WARNING_TITLE, response.data.content);
                            $scope.showForm = false;
                            $scope.showTable = true;
                        }
                        hideBackground();
                    }, $scope.errorFunction);
                }

                $scope.hasFormDevolucionErrors = function () {
                    if ($scope.dev.monto === undefined) {
                        $scope.showDevolucionErrores = "Debe ingresar un monto.";
                        return true;
                    }

                    if ($scope.dev.concepto === undefined) {
                        $scope.showDevolucionErrores = "Debe ingresar un concepto.";
                        return true;
                    }

                    if ($scope.dev.tipoDevolucion === undefined) {
                        $scope.showDevolucionErrores = "Debe seleccionar un tipo de Devolucion.";
                        return true;
                    }

                    return false;

                }

                $scope.hasFormaDePagos = function () {
                    switch ($scope.formData.formaPago) {
                        case $scope.EFECTIVO:
                            break;
                        case $scope.CHEQUE:

                            if (!$scope.formData.nroCheque) {
                                $scope.errorFormaPagoMessage = 'No definio un numero de Cheque';
                                return false;
                            }

                            if ($scope.formData.nroCheque === undefined) {
                                $scope.errorFormaPagoMessage = 'No definio un numero de Cheque';
                                return false;
                            }
                            if (!$scope.formData.idBanco) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar un Banco';
                                return false;
                            }

                            break;
                        case $scope.DEPOSITO :
                            if (!$scope.formData.nroDeposito) {
                                $scope.errorFormaPagoMessage = 'No ha ingresado un numero de deposito';
                                return false;
                            }
                            if ($scope.formData.nroDeposito === null) {
                                $scope.errorFormaPagoMessage = 'No ha ingresado un numero de deposito';
                                return false;
                            }


                            if (!$scope.formData.idCuentaDeposito) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una cuenta de deposito';
                                return false;
                            }

                            if ($scope.formData.idCuentaDeposito === null) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una cuenta de deposito';
                                return false;
                            }


                            break;

                        case $scope.TARJETA :

                            if (!$scope.formData.idTarjetaCredito) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una Tarjeta';
                                return false;
                            }

                            if ($scope.formData.idTarjetaCredito === null) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una Tarjeta';
                                return false;
                            }

                            if (!$scope.formData.nroTarjeta) {
                                $scope.errorFormaPagoMessage = 'Ingrese un numero de Tarjeta';
                                return false;
                            }

                            if ($scope.formData.nroTarjeta === null) {
                                $scope.errorFormaPagoMessage = 'Ingrese un numero de Tarjeta';
                                return false;
                            }

                            if ($scope.formData.nroTarjeta.length > 16) {
                                $scope.errorFormaPagoMessage = 'El numero ingresado no tiene los 16 digitos de la tarjeta';
                                return false;
                            }

                            break;
                    }

                    return true;
                }

                $scope.acreditar = function () {
                    $scope.trx = new PagoAnticipadoTransaccion();
                    $scope.trx.estado = $scope.PENDIENTE;
                    $scope.trx.idPagoAnticipado = $scope.formData.idPagoAnticipado;

                    $scope.showFrmBoletoNuevo = true;
                    $scope.showFrmBoletoEditar = false;
                    $scope.itemTrxSelected = false;

                    var idCliente = $scope.formData.idCliente.id;
                    return $http({
                        url: `${url.value}get/notadebito/credito/${idCliente}`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.gridNotaDebito = response.data.content;
                            showModalWindow('#frmNotasDebitos');
                        } else {
                            $scope.gridNotaDebito = [];
                            showWarning(WARNING_TITLE, response.data.content);
                        }

                    }, $scope.errorFunction);
                }

                $scope.seleccionarItemNotaDebito = function (row) {
                    $scope.trx = row;
                    $scope.itemTrxSelected = true;
                }

                $scope.seleccionarTransaccion = function () {
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showMontoBs = false;
                    $scope.showMontoUsd = false;

                    if ($scope.formData.moneda === $scope.MONEDA_EXTRANJERA) {
                        $scope.trx.montoDisponibleUsd = Number($scope.formData.montoTotalDisponible);
                        $scope.trx.montoDisponibleBs = Number(parseFloat($scope.trx.montoDisponibleUsd) * parseFloat($scope.formData.factorCambiario)).toFixed(2);
                    } else {
                        $scope.trx.montoDisponibleBs = Number($scope.formData.montoTotalDisponible);
                        $scope.trx.montoDisponibleUsd = Number(parseFloat($scope.trx.montoDisponibleBs) / parseFloat($scope.formData.factorCambiario)).toFixed(2);
                    }


                   /* $scope.trx.monedaTransaccion = $scope.trx.moneda;
                    if ($scope.trx.moneda === $scope.MONEDA_EXTRANJERA) {
                        if ($scope.trx.montoAdeudadoUsd < $scope.trx.montoDisponibleUsd) {
                            $scope.trx.monto = $scope.trx.montoAdeudadoUsd;
                        } else {
                            $scope.trx.monto = Number($scope.trx.montoDisponibleUsd);
                        }

                        $scope.trx.maxMontoUsd = $scope.trx.monto;
                        $scope.trx.maxMontoBs = Number(parseFloat($scope.trx.maxMontoUsd) * parseFloat($scope.formData.factorCambiario)).toFixed(2);
                    } else {
                        if ($scope.trx.montoAdeudadoBs < $scope.trx.montoDisponibleBs) {
                            $scope.trx.monto = $scope.trx.montoAdeudadoBs;
                        } else {
                            $scope.trx.monto = Number($scope.trx.montoDisponibleBs);
                        }

                        $scope.trx.maxMontoBs = $scope.trx.monto;
                        $scope.trx.maxMontoUsd = Number(parseFloat($scope.trx.maxMontoBs) / parseFloat($scope.formData.factorCambiario)).toFixed(2);
                    }*/
                    
                    $scope.trx.monedaTransaccion = $scope.trx.moneda;
                    if ($scope.trx.moneda === $scope.MONEDA_EXTRANJERA) {
                        $scope.trx.montoCancelarUsd = $scope.trx.montoAdeudadoUsd;
                    } else {
                        $scope.trx.montoCancelarBs = $scope.trx.montoAdeudadoBs;
                    }

                    $scope.trx.montoQueDebeIngresarUsd = $scope.trx.montoAdeudadoUsd;
                    $scope.trx.montoQueDebeIngresarBs = $scope.trx.montoAdeudadoBs;


                    hideModalWindow('#frmNotasDebitos');
                    $scope.showFrmNuevaTrx = true;
                    $scope.showFrmEditarTrx = false;
                    showModalWindow('#frmPagoAnticipadoTransaccion');

                    $scope.checkMontoIngresado();

                }

                $scope.delete = function () {

                    if ($scope.modalConfirmation.method === 'delete-comision') {
                        $scope.deleteComision();
                    }
                };

                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.edit = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.showBtnNuevo = false;
                    $scope.showBtnEditar = true;
                    $scope.hideMessagesBox();
                    $scope.formData = item;
                    return $http({
                        method: 'POST',
                        url: `${url.value}all/transaccion`,
                        data: {token: token.value, content: angular.toJson(item)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.formData.transacciones = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                                hideBackground();
                                $scope.setFactorMaxMin();
                            },
                            $scope.errorFunction
                            );
                }

                $scope.nuevo = function () {
                    $scope.showLoading = true;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = new PagoAnticipado();
                    $scope.formData.formaPago = $scope.EFECTIVO;
                    $scope.formData.fechaEmision = today;
                    $scope.formData.estado = $scope.PENDIENTE;
                    $scope.clickNuevo = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}new`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    $scope.showForm = true;
                                    $scope.showBtnNuevo = true;
                                    $scope.showBtnEditar = false;
                                    $scope.showTable = false;
                                    $scope.formData = response.data.content;
                                    //$scope.formData.factorCambiario = $scope.dollarToday.valor;
                                    $scope.setFactorMaxMin();
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.initializeMonto = function () {
                    $scope.trx.monto = undefined;
                    $scope.trx.montoCambioBs = undefined;
                    $scope.trx.montoCambioUsd = undefined;

                    if ($scope.trx.moneda === $scope.MONEDA_NACIONAL) {
                        $scope.showMontoUsd = true;
                        $scope.showMontoBs = false;

                    } else {
                        $scope.showMontoBs = true;
                        $scope.showMontoUsd = false;
                    }
                }

                $scope.checkMontoIngresadoBs = function () {
                    $scope.changeMontosTotales() ;
                    if ($scope.trx.montoCancelarBs > $scope.trx.montoAdeudadoBs) {
                        $scope.showErrorMontoIngresadoBs = true;
                    } else {
                        $scope.showErrorMontoIngresadoBs = false;
                    }
                }

                $scope.checkMontoIngresadoUsd = function () {
                    $scope.changeMontosTotales() ;
                    if ($scope.trx.montoCancelarUsd > $scope.trx.montoAdeudadoUsd) {
                        $scope.showErrorMontoIngresadoUsd = true;
                    } else {
                        $scope.showErrorMontoIngresadoUsd = false;
                    }
                }

                $scope.checkMontoIngresado = function () {

                    if ($scope.trx.moneda === $scope.MONEDA_NACIONAL) {
                        if ($scope.trx.monto !== undefined) {
                            $scope.trx.montoCambioUsd = Number($scope.trx.monto / $scope.formData.factorCambiario).toFixed(2);
                        }

                        if ($scope.formData.moneda === $scope.MONEDA_EXTRANJERA) {
                            $scope.showMontoBs = false;
                            $scope.showMontoUsd = true;

                        } else {
                            $scope.showMontoUsd = true;
                            $scope.showMontoBs = false;
                        }

                        if ($scope.trx.monto > $scope.trx.montoDisponibleBs) {
                            $scope.showErrorMontoIngresado = true;
                            $scope.montoQueDebeIngresar = $scope.trx.montoDisponibleBs;
                        } else {
                            $scope.showErrorMontoIngresado = false;
                        }
                    } else {

                        if ($scope.trx.monto !== undefined) {
                            $scope.trx.montoCambioBs = Number($scope.trx.monto * $scope.formData.factorCambiario).toFixed(2);
                        }


                        if ($scope.formData.moneda === $scope.MONEDA_NACIONAL) {
                            $scope.showMontoBs = true;
                            $scope.showMontoUsd = false;

                        } else {
                            $scope.showMontoBs = true;
                            $scope.showMontoUsd = false;
                        }

                        if ($scope.trx.monto > $scope.trx.montoDisponibleUsd) {
                            $scope.showErrorMontoIngresado = true;
                            $scope.montoQueDebeIngresar = $scope.trx.montoDisponibleUsd;
                        } else {
                            $scope.showErrorMontoIngresado = false;
                        }
                    }
                }


                $scope.checkMontoIngresadoDevolver = function () {
                    $scope.showErrorImporteIngresado = $scope.dev.monto > $scope.dev.montoMaximoDevolucion;
                }

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.search = {fechaInicio: firstDay, fechaFin: today};
                    $scope.hideMessagesBox();
                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
                }

                /*
                 *  Eventos Click Pestanhas
                 */
                $scope.efectivoClick = function () {
                    $scope.initEfectivo();
                }

                $scope.chequeClick = function () {
                    $scope.initCheque();
                }

                $scope.depositoClick = function () {
                    $scope.initDeposito();
                }

                $scope.creditoClick = function () {
                    $scope.initCredito();
                }

                $scope.contadoClick = function () {
                    $scope.initContado();
                }

                $scope.tarjetaClick = function () {
                    $scope.initTarjeta();
                }

                $scope.combinadoClick = function () {
                    $scope.initCombinado();
                }

                /**
                 * 
                 * eventos formas de pago
                 */


                $scope.initEfectivo = function () {
                    $scope.formData.formaPago = $scope.EFECTIVO;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initCheque = function () {
                    $scope.formData.formaPago = $scope.CHEQUE;
                    $scope.resetDeposito();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initDeposito = function () {
                    $scope.formData.formaPago = $scope.DEPOSITO;
                    $scope.resetCheque();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initCredito = function () {
                    $scope.formData.formaPago = $scope.CREDITO;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                    $scope.formData.creditoVencimiento = today;
                    if ($scope.cliente !== undefined) {
                        $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                        $scope.setCreditoVencimiento();
                    }
                }

                $scope.initTarjeta = function () {
                    $scope.formData.formaPago = $scope.TARJETA;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCredito();
                }

                $scope.resetCredito = function () {
                    $scope.formData.creditoDias = null;
                    $scope.formData.creditoVencimiento = null;
                }

                $scope.resetCheque = function () {
                    $scope.formData.nroCheque = null;
                    $scope.formData.idBanco = null;
                }

                $scope.resetDeposito = function () {
                    $scope.formData.nroDeposito = null;
                    $scope.formData.idCuentaDeposito = null;
                }

                $scope.resetTarjeta = function () {
                    $scope.formData.idTarjetaCredito = null;
                    $scope.formData.nroTarjeta = null;
                }


                $scope.modalAnular = function (id, title, tipo, item) {
                    $scope.itemAnular = {id: null, title: null, tipo: null, item: null};
                    $scope.itemAnular.id = id;
                    $scope.itemAnular.title = title;
                    $scope.itemAnular.tipo = tipo;
                    $scope.itemAnular.item = item;
                }

                $scope.anular = function () {
                    switch ($scope.itemAnular.tipo) {
                        case $scope.TRANSACCION :
                            $scope.anularTransaccion();
                            break;

                        case $scope.PAGO_ANTICIPADO :
                            $scope.anularPagoAnticipado();
                            break;

                        default:
                            break;
                    }
                }

                $scope.anularTransaccion = function () {
                    $scope.showLoading = true;
                    showBackground();
                    return $http({
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        url: `${url.value}anular/transaccion`,
                        data: {token: token.value, content: angular.toJson($scope.itemAnular.item)}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    showSuccess(SUCCESS_TITLE, response.data.content);
                                    var notadebito = response.data.entidad;
                                    $scope.edit(notadebito);
                                } else {
                                    showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideBackground();
                                hideModalWindow('frmBackground');
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);
                }


                $scope.anularPagoAnticipado = function () {
                    $scope.showLoading = true;
                    showBackground();
                    return $http({
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        url: `${url.value}anular`,
                        data: {token: token.value, content: angular.toJson($scope.itemAnular.item)}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    $scope.showForm = false;
                                    $scope.showTable = true;
                                    showSuccess(SUCCESS_TITLE, response.data.content);
                                } else {
                                    $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideBackground();
                                hideModalWindow('frmBackground');
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);
                }

                $scope.modalEliminar = function (idx, nombrex, methodx) {
                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
                }

                $scope.setFactorMaxMin = function () {
                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);
                }

                $scope.findCta = function (cta, input) {
                    var i = 0;
                    for (i; i < input.length; i++) {
                        if (input[i].id === cta) {
                            return input[i];
                        }
                    }
                }

                $scope.getAllBancos = function () {
                    return $http.get(`${urlBancos.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboAllBancos = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });
                }

                $scope.getAllBancosCuentas = function () {
                    return $http.get(`${urlBancos.value}getBancosCuentas/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboBancosConCuenta = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });
                }

                $scope.getTarjetas = function () {
                    return $http.get(`${urlTarjeta.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboTarjetas = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });
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

                $scope.getAllBancosCuentas = function () {
                    return $http.get(`${urlBancos.value}getBancosCuentas/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboBancosConCuenta = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });
                }
                $scope.getFactorCambiario = function () {
                    return $http({
                        url: `${urlFactores.value}dollar/today`,
                        method: 'POST',
                        data: {token: token.value},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.dollarToday = response.data.content;
                        }
                    }, $scope.errorFunction);
                }

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }

                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.ctaDevolucionMonExt.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $scope.showErrorCtaDevolucionMonExt = false;
                    }
                });

                $scope.getFactorCambiario();
                $scope.getClientes();
                $scope.getAllBancos();
                $scope.getAllBancosCuentas();
                $scope.getTarjetas();
            }
        ]);

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

app.filter('printLimit', function ($filter) {
    return function (input, predicate) {
        if (input.length > 64) {
            return
        }
    }
});

app.filter('printEstado', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E' :
                return 'EMITIDO';
            case 'P' :
                return 'PENDIENTE';
            case 'A' :
                return 'ANULADO';
            case 'C' :
                return 'CREADO';
            case 'M' :
                return 'EN MORA';
            case 'D' :
                return 'CANCELADO';
            default :
                return 'SIN ESTADO';
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
            default :
                return 'SIN MONEDA';
                break;
        }
    }
});

app.filter('printFormaPago', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E':
                return 'EFECTIVO';
            case 'C' :
                return 'CREDITO';
            case 'H' :
                return 'CHEQUE';
            case 'T' :
                return 'TARJETA';
            case 'D' :
                return 'DEPOSITO';
            default :
                return '-';
                break;
        }
    }
});


app.filter('printEstado', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E' :
                return 'EMITIDO';
            case 'P' :
                return 'PENDIENTE';
            case 'A' :
                return 'ANULADO';
            case 'D' :
                return 'CON SALDO';
            case 'S' :
                return 'SIN SALDO';
            default :
                return 'SIN ESTADO';
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
            default :
                return 'SIN MONEDA';
                break;
        }
    }
});

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
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
            case 'AC':
                return 'ACREDITACION';
            case 'DE':
                return 'DEVOLUCION';
            default :
                return 'SIN TIPO';
                break;
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

