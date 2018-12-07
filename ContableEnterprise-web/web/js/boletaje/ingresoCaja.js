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
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlTarjeta = document.getElementsByName("hdUrlTarjetas")[0];
                var urlPlan = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlBancos = document.getElementsByName("hdUrlBancos")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");

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

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;

                $scope.find = function () {
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
                        url: url.value + 'all/transaccion',
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

                    $scope.loading = true;

                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

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


                $scope.formHasError = function () {
                    if ($scope.formData.idEmpresa == undefined) {
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
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = {};
                    $scope.clickNuevo = true;
                    $scope.myForm.$setPristine();
                    myForm.reset();
                }

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
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

                $scope.getFactorCambiario()
                $scope.getClientes();
                $scope.getAllBancos();
                $scope.getTarjetas();
                $scope.getAllBancosCuentas();


                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.ctaDevolucionMonExt.id', function (now, old) {
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

