/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var hoy = function () {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd
    }

    if (mm < 10) {
        mm = '0' + mm
    }

    today = mm + '/' + dd + '/' + yyyy;
    return today;
}

var app = angular.module("jsIndex", ['jsIndex.controllers', 'smart-table', 'ui.bootstrap']);

app.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.myEnter);
                });
                event.preventDefault();
            }
        })
    }
});
        
angular.module('jsIndex.controllers', []).controller('frmIndex',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

        var urlFactores = document.getElementsByName("hdUrlFactores")[0];
        var urlNotaDebito = document.getElementsByName("hdUrlNotaDebito")[0];
        var urlIngresoCaja = document.getElementsByName("hdUrlIngresoCaja")[0];
        var urlNotaCredito = document.getElementsByName("hdUrlNotaCredito")[0];
        var urlComprobante = document.getElementsByName("hdUrlComprobante")[0];
        var token = document.getElementsByName("hdToken")[0];
        var formName = document.getElementsByName("hfFormName")[0];
        //modal dolar
        $scope.showFactores = false;
        $scope.fechaHoy = hoy();

        $scope.dollarModalSuccess = false;
        $scope.dollarModalError = false;
        $scope.itemsByPage = 5;

        $scope.getDataFactor = function (method) {
            return $http({
                method: 'POST',
                url: `${urlFactores.value}${method}/today`,
                data: {token: token.value, content: '', formName: formName},
                headers: {'Content-Type': 'application/json'},
                contentType: "application/x-www-form-urlencoded"
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === 'dollar') {
                        $scope.dollar = response.data.content;
                    } else if (method === 'ufv') {
                        $scope.ufv = response.data.content;
                    }
                    $scope.showFactores = true;
                } else if (response.data.code === 301) {
                    $scope.showBtnGuardarDolarFactor = true;
                    $scope.showBtnCerrarDolarFactor = false;
                    document.getElementById("openModalButton").click();
                }
            }, function (error) {
            });
        }

        $scope.guardar = function () {
            var data = {fecha: $scope.fechaHoy, valor: $scope.valorDolar};
            $scope.showBtnCerrarDolarFactor = true;
            $scope.showBtnGuardarDolarFactor = false;
            return $http({
                method: 'POST',
                url: `${urlFactores.value}dollar/save`,
                data: {token: token.value, content: angular.toJson(data), formName: formName},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.dollarModalMessage = response.data.content;
                    $scope.dollarModalSuccess = true;
                    $scope.getDataFactor('dollar');
                }
            }, function (error) {
                $scope.dollarModalMessage = error.statusText;
                $scope.dollarModalError = true;
            });
        }

        $scope.showNotaDebitoPendiente = function () {
            $scope.showTableNotaDebitoPendiente = false;
            $scope.showMessageNotaDebitoPendienteCero = false;

            return $http({
                method: 'POST',
                url: `${urlNotaDebito.value}index/notadebito/pendiente`,
                data: {token: token.value},
                headers: {'Content-type': 'application/json'}
            }).then(
                    function (response) {
                        if (response.data.code === 201) {
                            if (response.data.content.length > 0) {
                                $scope.showTableNotaDebitoPendiente = true;
                                $scope.debitoPendienteGrid = response.data.content;
                            } else {
                                $scope.showMessageNotaDebitoPendienteCero = true;
                            }
                        }
                    }, function (error) {}
            );
        }

        $scope.showNotaDebitoMora = function () {
            $scope.showTableNotaDebitoMora = false;
            $scope.showMessageNotaDebitoMora = false;

            return $http({
                method: 'POST',
                url: `${urlNotaDebito.value}index/notadebito/mora`,
                data: {token: token.value},
                headers: {'Content-type': 'application/json'}
            }).then(
                    function (response) {
                        if (response.data.code === 201) {
                            if (response.data.content.length > 0) {
                                $scope.showTableNotaDebitoMora = true;
                                $scope.debitoMoraGrid = response.data.content;
                            } else {
                                $scope.showMessageNotaDebitoMora = true;
                            }
                        }
                    }, function (error) {}
            );
        }
        
        $scope.showIngresoCajaPendiente = function () {
            $scope.showTableIngresosIndex = false;
            $scope.showMessageIngresoCajaCero = false;
            return $http({
                method: 'POST',
                url: `${urlIngresoCaja.value}index/pendiente`,
                data: {token: token.value},
                headers: {'Content-type': 'application/json'}
            }).then(
                    function (response) {
                        if (response.data.code === 201) {
                            if (response.data.content.length > 0) {
                                $scope.showTableIngresosIndex = true;
                                $scope.ingresosGrid = response.data.content;
                            } else {
                                $scope.showMessageIngresoCajaCero = true;
                            }
                        }
                    }, function (error) {}
            );
        }
        
        $scope.showNotaCreditoPendiente = function () {
            $scope.showTableNotaCredito = false;
            $scope.showMessageNotaCredito = false;
            return $http({
                method: 'POST',
                url: `${urlNotaCredito.value}index/pendiente`,
                data: {token: token.value},
                headers: {'Content-type': 'application/json'}
            }).then(
                    function (response) {
                        if (response.data.code === 201) {
                            if (response.data.content.length > 0) {
                                $scope.showTableNotaCredito = true;
                                $scope.notaCreditoGrid = response.data.content;
                            } else {
                                $scope.showMessageNotaCredito = true;
                            }
                        }
                    }, function (error) {}
            );
        }
        
        
        $scope.showComprobante = function () {
            $scope.showTableNshowTableComprobanteotaCredito = false;
            $scope.showMessageComprobante = false;
            return $http({
                method: 'POST',
                url: `${urlComprobante.value}index/pendiente`,
                data: {token: token.value},
                headers: {'Content-type': 'application/json'}
            }).then(
                    function (response) {
                        if (response.data.code === 201) {
                            if (response.data.content.length > 0) {
                                $scope.showTableComprobante = true;
                                $scope.comprobanteGrid = response.data.content;
                            } else {
                                $scope.showMessageComprobante = true;
                            }
                        }
                    }, function (error) {}
            );
        }
        
        

        $scope.getDataFactor('dollar');
        $scope.getDataFactor('ufv');
        $scope.showNotaDebitoMora();
        $scope.showNotaDebitoPendiente();
        $scope.showIngresoCajaPendiente();
        $scope.showNotaCreditoPendiente();
        $scope.showComprobante();
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