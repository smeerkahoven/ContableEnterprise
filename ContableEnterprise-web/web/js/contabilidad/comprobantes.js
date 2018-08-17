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

function AsientoContable() {
    this.idAsiento = '';
    this.idPlanCuenta = '';
    this.idLibro = '';
    this.gestion = '';
    this.fechaMovimiento = '';
    this.montoDebeNac = '';
    this.montoaHaberNac = '';
    this.montoDebeExt = '';
    this.montoHaberExt = '';
    this.estado = '';
    this.moneda = '';
    this.editable = true;
    this.updated = false;
    this.action = '';
}

function Comprobante() {
    this.idLibro = '';
    this.idUsuarioCreador = '';
    this.idUsuarioAnulado = '';
    this.gestion = '';
    this.fecha = '';
    this.glosa = '';
    this.estado = '';
    this.moneda = 'B';
    this.factorCambiario = '0';
    this.factorMin = 0;
    this.factorMax = 0;
    this.tipo = '';
    this.transacciones = [];
    this.totalDebeMonNac=0;
    this.totalHaberMonNac=0;
    this.totalDebeMonExt=0;
    this.totalHaberMonNac=0;
    this.difMonNac=0;
    this.difMonExt=0;
}

AsientoContable.prototype = Object.create(AsientoContable.prototype);
AsientoContable.prototype.constructor = AsientoContable;

Comprobante.prototype = Object.create(Comprobante.prototype);
Comprobante.prototype = Comprobante;


var ALL_TIPO_COMPROBANTES = 'all-tipo-comprobantes';
var ALL_PLAN_CUENTAS = "combo/";
var DOLLAR_TODAY = 'dollar/today';
var NUMERO_COMPROBANTE = 'numero-comprobante/';

var ACTION_CREATE = 'c';
var ACTION_UPDATE = 'u';
var ACTION_DELETE = 'd';

var MONEDA_NACIONAL = "B";
var MONEDA_EXTRANJERA = 'U';

var app = angular.module("jsComprobantes", ['jsComprobantes.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsComprobantes.controllers', []).controller('frmComprobantes',
        ['$scope', '$http', '$uibModal', '$window',
            function ($scope, $http, $window) {
                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var idEmpresa = document.getElementById("idEmpresa");
                var factorMaxMin = document.getElementById("hdFactorMaxMin");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.disableAddButton = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;
                $scope.dollarToday = 0;

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

                            $scope.loading = false;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.nuevoComprobante = function (tipo) {
                    $scope.formData = new Comprobante();
                    $scope.formData.factorCambiario = $scope.dollarToday.valor;
                    $scope.formData.fecha = $scope.dollarToday.fecha;
                    $scope.formData.tipo = tipo;
                    $scope.nuevo();
                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.disableAddButton = false;

                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);

                    $scope.myForm.$setPristine();
                    myForm.reset();
                }

                $scope.obtenerNumeroComprobante = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: url.value + NUMERO_COMPROBANTE + $scope.formData.tipo,
                        data: {token: token.value, content: '', formName: formName},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.idLibro = response.data.content;
                            $scope.nuevo();
                        } else {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = response.data.content;
                        }

                        $scope.loading = false;
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                        $scope.loadingNumero = false;
                    });
                }

                $scope.existenTransaccionesInvalidas = function () {

                    if ($scope.formData.transacciones == undefined) {
                        return true;
                    }

                    //si no hay transacciones
                    if ($scope.formData.transacciones.length === 0) {
                        return true;
                    }

                    //si existen transacciones
                    if ($scope.formData.transacciones.length > 1) {
                        for (var i in $scope.formData.transacciones) {
                            if ($scope.formData.transacciones[i].idPlanCuenta === undefined) {
                                return true;
                            }

                            if ($scope.formData.transacciones[i].moneda === undefined) {
                                return true;
                            }

                        }
                    }
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
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
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

                $scope.editTransaccion = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.hideMessagesBox();
                    item.editable = true;
                    $scope.disableAddButton = true;
                }

                $scope.saveTransaccion = function (item) {

                    if (item.idPlanCuenta.id === undefined) {
                        $scope.showRowError = true
                        $scope.showRowMessage = "Debe ingresar una Cuenta V&aacute;lida.";
                        console.log($scope.showRowMessage);
                        return false;
                    }

                    console.log("moneda:" + item.moneda);

                    if (item.moneda === '') {
                        $scope.showRowError = true
                        $scope.showRowMessage = "Debe seleccionar un tipo de moneda.";
                        console.log($scope.showRowMessage);
                        return false;
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        if (item.debeMonNac == undefined || item.haberMonNac == undefined) {
                            $scope.showRowError = true
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            console.log($scope.showRowMessage);
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_EXTRANJERA) {
                        if (item.debeMonExt == undefined || item.haberMonExt == undefined) {
                            $scope.showRowError = true
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            console.log($scope.showRowMessage);
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        item.debeMonNac = parseFloat(Math.round(item.debeMonNac * 100) / 100).toFixed(2);
                        item.haberMonNac = parseFloat(Math.round(item.haberMonNac * 100) / 100).toFixed(2);

                        item.debeMonExt = item.debeMonNac * $scope.formData.factorCambiario;
                        item.haberMonExt = item.haberMonNac * $scope.formData.factorCambiario;
                    } else if (item.moneda == MONEDA_EXTRANJERA) {
                        item.debeMonExt = parseFloat(Math.round(item.debeMonExt * 100) / 100).toFixed(2);
                        item.haberMonExt = parseFloat(Math.round(item.haberMonExt * 100) / 100).toFixed(2);

                        item.debeMonExt = item.debeMonNac / $scope.formData.factorCambiario;
                        item.haberMonExt = item.haberMonNac / $scope.formData.factorCambiario;
                    }
                    console.log("todo ok");
                    item.editable = false;
                    item.action = ACTION_UPDATE;
                    $scope.disableAddButton = false;
                }

                $scope.sumarTotales = function(){
                    var tdmn = 0, thmn = 0 , tdme = 0, thme = 0;
                    for (var i in $scope.formData.transacciones){
                        $scope.formData.totalDebeMonNac += $scope.formData.transacciones[i].debeMonNac  ;
                        $scope.formData.totalHaberMonNac += $scope.formData.transacciones[i].haberMonNac ;
                        $scope.formData.totalHaberMonExt += $scope.formData.transacciones[i].debeMonExt  ;
                        $scope.formData.totalDebeMonExt += $scope.formData.transacciones[i].haberMonExt;
                    }
                    
                    $scope.formData.difMonNac = $scope.formData.totalDebeMonNac -   $scope.formData.totalHaberMonNac;
                    $scope.formData.difMonExt =$scope.formData.totalHaberMonExt - $scope.formData.totalDebeMonExt;
                }

                $scope.addTransaccion = function () {
                    var newAsiento = new AsientoContable;
                    newAsiento.action = ACTION_CREATE;
                    $scope.formData.transacciones.push(newAsiento);
                    $scope.disableAddButton = true;
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

                $scope.getData(url.value, ALL_TIPO_COMPROBANTES);
                $scope.getData(urlFactores.value, DOLLAR_TODAY);
                $scope.obtenerPlanCuentas();

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


app.directive('stringToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        return '' + value;
      });
      ngModel.$formatters.push(function(value) {
        return parseFloat(value);
      });
    }
  };
});