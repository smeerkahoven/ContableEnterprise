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
function IngresoCaja() {
    this.idIngresoCaja = null;
    this.idUsuario = null;
    this.idEmpresa = null;
    this.idCliente = null;
    this.fechaEmision = null;
    this.fechaInsert = null;
    this.montoAbonadoBs = null;
    this.montoAbonadoUsd = null;
    this.factorCambiario = null;
    this.formaPago = null;
    this.idBanco = null;
    this.nroCheque = null;
    this.idTarjetaCredito = null;
    this.nroTarjeta = null;
    this.nroDeposito = null;
    this.idCuentaDeposito = null;
    this.estado;
}

function IngresoTransaccion() {
    this.idTransaccion = null;
    this.idIngresoCaja = null;
    this.descripcion = null;
    this.moneda = null;
    this.montoBs = null;
    this.montoUsd = null;
    this.fechaInsert = null;
    this.idNotaTransaccion = null;
    this.idNotaDebito = null;
    this.estado = null;
    this.monto = null;
}

IngresoCaja.prototype = Object.create(IngresoCaja.prototype);
IngresoCaja.prototype.constructor = IngresoCaja;
IngresoTransaccion.prototype = Object.create(IngresoTransaccion.prototype);
IngresoTransaccion.prototype.constructor = IngresoTransaccion;
var app = angular.module("jsIngresoCaja", ['jsIngresoCaja.controllers', 'smart-table', 'ui.bootstrap']);
angular.module('jsIngresoCaja.controllers', []).controller('frmIngresoCaja',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlTarjeta = document.getElementsByName("hdUrlTarjetas")[0];
                var urlPlan = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlBancos = document.getElementsByName("hdUrlBancos")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var idEmpresa = document.getElementsByName("idEmpresa")[0].value;
                var factorMaxMin = document.getElementsByName("hdFactorMaxMin")[0];
                var urlIngresoCaja = document.getElementsByName("hdIngresoCaja")[0];
                var urlComprobante = document.getElementsByName("hdComprobante")[0];

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.BOLETO = 'B';
                $scope.PAQUETE = 'P';
                $scope.ALQUILER = 'A';
                $scope.HOTEL = 'H';
                $scope.SEGURO = 'S';
                $scope.RESERVA = 'R';
                $scope.CARGO = 'C';
                $scope.PENDIENTE = 'P';
                $scope.EMITIDO = 'E';
                $scope.ANULADO = 'A';
                $scope.CANCELADO = 'D';
                $scope.CREADO = 'C';
                $scope.EFECTIVO = "E";
                $scope.CHEQUE = "H";
                $scope.DEPOSITO = "D";
                
                $scope.MONEDA_NACIONAL = 'B';
                $scope.MONEDA_EXTRANJERA = 'U';
                $scope.INGRESO = 'I';
                $scope.TRANSACCION = 'T';
                $scope.showClienteRequieredSearch = false;
                $scope.loading = false;
                $scope.showErrorMontoIngresado = false;
                $scope.montoQueDebeIngresar = '';
                $scope.formData = {};
                $scope.mainGrid = [];
                $scope.modalConfirmation = {};
                $scope.showForm = false;
                $scope.showTable = true;
                $scope.itemsByPage = 15;
                $scope.search = {fechaInicio: firstDay, fechaFin: today};


                $scope.find = function () {

                    /*if ($scope.search === undefined) {
                     $scope.showClienteRequieredSearch = true;
                     showAlert(ERROR_RESPUESTA_TITLE, "Ingreso un cliente Valido para la busqueda");
                     return;
                     }
                     
                     if ($scope.search.idCliente === undefined) {
                     showAlert(ERROR_RESPUESTA_TITLE, "Ingreso un cliente Valido para la busqueda");
                     $scope.showClienteRequieredSearch = true;
                     return;
                     }
                     
                     if ($scope.search.idCliente.id === undefined) {
                     showAlert(ERROR_RESPUESTA_TITLE, "Ingreso un cliente Valido para la busqueda");
                     $scope.showClienteRequieredSearch = true;
                     return;
                     }*/


                    $scope.showClienteRequieredSearch = false;
                    $scope.loading = true;
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
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

                        case $scope.INGRESO :
                            $scope.anularIngresoCaja();
                            break;

                        default:

                            break;
                    }
                }

                $scope.anularIngresoCaja = function () {
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
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);

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
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);
                }

                $scope.setFactorMaxMin = function () {
                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);
                }

                $scope.checkMontoIngresado = function () {
                    if ($scope.trx.moneda === $scope.MONEDA_NACIONAL) {
                        if ($scope.trx.monto > $scope.trx.montoAdeudadoBs) {
                            $scope.showErrorMontoIngresado = true;
                            $scope.montoQueDebeIngresar = $scope.trx.montoAdeudadoBs;
                        } else {
                            $scope.showErrorMontoIngresado = false;
                        }
                    } else {
                        if ($scope.trx.monto > $scope.trx.montoAdeudadoUsd) {
                            $scope.showErrorMontoIngresado = true;
                            $scope.montoQueDebeIngresar = $scope.trx.montoAdeudadoUsd;
                        } else {
                            $scope.showErrorMontoIngresado = false;
                        }
                    }

                }

                $scope.formIngresoHasErrores = function () {
                    if ($scope.formData.idCliente === undefined) {
                        return true;
                    }

                    if ($scope.formData.idCliente.id === undefined) {
                        return true;
                    }

                    if (!$scope.myForm.txtFactor.$valid) {
                        return true;
                    }

                    if (!$scope.myForm.txtFechaEmision.$valid) {
                        return true;
                    }

                    return false;
                }

                $scope.pendiente = function () {
                    if ($scope.formIngresoHasErrores()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    $scope.showLoading = true;
                    $http({
                        method: 'POST',
                        headers: {'Content-type': 'application/json'},
                        url: `${url.value}pendiente`,
                        data: {token: token.value, content: angular.toJson($scope.formData)}
                    }).then(function (response) {
                        $scope.showLoading = false;
                        if (response.data.code === 201) {
                            $scope.showForm = false;
                            $scope.showTable = true;
                            $scope.showRestfulSuccess = true;
                            $scope.showRestfulMessage = response.data.content;
                        } else {
                            $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                        }
                    }, $scope.errorFunction);
                }

                $scope.finalizar = function () {
                    if ($scope.formIngresoHasErrores()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    if (!$scope.hasFormaDePagos()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, $scope.errorFormaPagoMessage);
                        return;
                    }

                    if ($scope.formData.estado === $scope.EMITIDO) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, ERROR_NOTA_DEBITO_EMITIDA);
                        return;
                    }
                    
                    showBackground();
                    $http({
                        method: 'POST',
                        headers: {'Content-type': 'application/json'},
                        url: `${url.value}finalizar`,
                        data: {token: token.value, content: angular.toJson($scope.formData)}
                    }).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.showTable = true;
                                    $scope.showForm = false;
                                    $scope.showRestfulSuccess = true;
                                    $scope.showRestfulMessage = response.data.content;
                                    $scope.formData= response.data.entidad;
                                    goScrollTo('#restful-success');
                                    $scope.imprimir();
                                } else {
                                    $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideModalWindow('frmBackground');
                            },
                            $scope.errorFunction);
                }

                $scope.imprimir = function () {
                    if ($scope.formData.estado !== $scope.PENDIENTE) {
                        window.open(`../../IngresoCajaReportServlet?idIngreso=${$scope.formData.idIngresoCaja}`, '_blank');
                    } else {
                        showWarning(WARNING_TITLE, 'Debe emitir el Ingreso de caja para Imprimirlo');
                    }
                }


                $scope.hasFormaDePagos = function () {
                    console.log(`formaPago:${$scope.formData}`);
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


                $scope.save = function () {
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
//$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    
                    if (!$scope.hasFormaDePagos()){
                        $scope.showAlert(ERROR_TITLE, $scope.errorFormaPagoMessage);
                        return
                    }

                    $scope.loading = true;
                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;
                    console.log($scope.formData);
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
                            $scope.getData(url.value, 'all');
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                    }, function (error) {
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        $scope.showForm = false;
                    });
                };
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
                            showModalWindow('#frmIngresoTransaccion');
                        } else {
                            showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                        }

                    }, $scope.errorFunction);
                }
                $scope.formHasError = function () {
                    if ($scope.formData.idEmpresa === undefined) {
                        return true;
                    }
                    return (!$scope.formData.idEmpresa.id);
                }

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
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
                $scope.delete = function () {
                    if ($scope.modalConfirmation.method === 'delete-comision') {
                        $scope.deleteComision();
                    }
                };
                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.nuevo = function () {
                    $scope.showLoading = true;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = new IngresoCaja();
                    $scope.formData.formaPago = $scope.EFECTIVO;
                    $scope.formData.fechaEmision = today;
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

                $scope.addTransaccion = function () {

                    $scope.trx = new IngresoTransaccion();
                    $scope.trx.estado = $scope.PENDIENTE;
                    $scope.trx.idNotaDebito = $scope.formData.idNotaDebito;
                    $scope.trx.idIngresoCaja = $scope.formData.idIngresoCaja;
                    
                    $scope.itemTrxSelected=false ;

                    $scope.showFrmBoletoNuevo = true;
                    $scope.showFrmBoletoEditar = false;
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

                $scope.saveTransaccion = function () {

                    showBackground();

                    if ($scope.showErrorMontoIngresado) {
                        return;
                    }

                    $scope.trx.idIngresoCaja = $scope.formData.idIngresoCaja;
                    $scope.trx.estado = $scope.PENDIENTE;

                    return $http({
                        url: `${url.value}save/transaccion`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.trx)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var ingreso = response.data.entidad;
                            $scope.formData.montoAbonadoBs = ingreso.montoAbonadoBs;
                            $scope.formData.montoAbonadoUsd = ingreso.montoAbonadoUsd;
                            $scope.loadTransacciones();

                            hideModalWindow('#frmIngresoTransaccion');

                        }
                    }, $scope.errorFunction);
                }


                $scope.updateTransaccion = function () {

                    if ($scope.showErrorMontoIngresado) {
                        return;
                    }

                    return $http({
                        url: `${url.value}update/transaccion`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson($scope.trx)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var ingreso = response.data.entidad;
                            $scope.formData.montoAbonadoBs = ingreso.montoAbonadoBs;
                            $scope.formData.montoAbonadoUsd = ingreso.montoAbonadoUsd;
                            $scope.loadTransacciones();
                            hideModalWindow('#frmIngresoTransaccion');
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

                $scope.seleccionarItemNotaDebito = function (row) {
                    $scope.trx = row;
                    $scope.itemTrxSelected = true ;
                }

                $scope.seleccionarTransaccion = function () {
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;

                    $scope.trx.monedaTransaccion = $scope.trx.moneda;
                    if ($scope.trx.moneda === $scope.MONEDA_EXTRANJERA) {
                        $scope.trx.monto = $scope.trx.montoAdeudadoUsd;
                    } else {
                        $scope.trx.monto = $scope.trx.montoAdeudadoBs;
                    }
                    hideModalWindow('#frmNotasDebitos');
                    $scope.showFrmNuevaTrx = true;
                    $scope.showFrmEditarTrx = false;
                    showModalWindow('#frmIngresoTransaccion');

                }

                $scope.disableButtonAnadir = function () {

                    if ($scope.formData.idCliente === null)
                        return true;
                    if ($scope.formData.idCliente === undefined)
                        return true;
                    if ($scope.formData.idCliente.id === undefined)
                        return true;

                    if ($scope.formData.fechaEmision === null)
                        return true;
                    if ($scope.formData.fechaEmision === '')
                        return true;


                    return false;
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

                $scope.modalEliminar = function (idx, nombrex, methodx) {
                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
                }


                $scope.findCta = function (cta, input) {
                    var i = 0;
                    for (i; i < input.length; i++) {
                        if (input[i].id == cta) {
                            return input[i];
                        }
                    }
                }

//metodos para los combos
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

                $scope.getAllBancosCuentas = function () {
                    return $http.get(`${urlBancos.value}getBancosCuentas/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboBancosConCuenta = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.getTarjetas = function () {
                    return $http.get(`${urlTarjeta.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboTarjetas = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.getAllBancos = function () {
                    return $http.get(`${urlBancos.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboAllBancos = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
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
                    
                    console.log($scope.formData);
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
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

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


                $scope.getFactorCambiario()
                $scope.getClientes();
                $scope.getAllBancos();
                $scope.getTarjetas();
                $scope.getAllBancosCuentas();
                // los watch sirven para verificar si el valor cambio
                $scope.$watch('search.idCliente', function (now, old) {
                    if (now === undefined) {
                    }
                });
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
                return 'SIN TIPO';
                break;
        }
    }
});
app.filter('printFormaPago', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E':
                return 'EFECTIVO';
            case 'D' :
                return 'DEPOSITO BANCARIO';
            case 'T' :
                return 'TARJETA';
            case 'H' :
                return 'CHEQUE';
            case 'C' :
                return 'CREDITO';
            default :
                return 'SIN TIPO';
                break;
        }
    }
});
app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
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
})
        ;

