/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString('en-GB');
let today = new Date().toLocaleDateString('en-GB');

var app = angular.module("jsHistoricoCliente", ['jsHistoricoCliente.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsHistoricoCliente.controllers', []).controller('frmHistoricoCliente',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var user = document.getElementById("hdUser");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.user = user.value;

                $scope.loading = false;
                //$scope.search = {fechaInicio: firstDay, fechaFin: today};
                $scope.mainGrid = [];
                $scope.modalConfirmation = {};

                $scope.CLIENTE = "C";
                $scope.NOTA_DEBITO = "D";
                $scope.BOLETO = "B";
                $scope.PASAJERO = "P";

                $scope.showForm = false;
                $scope.showTable = false;

                $scope.itemsByPage = 99999;

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.search = {fechaInicio: firstDay, fechaFin: today};
                    $scope.mainGrid = [];
                    $scope.hideMessagesBox();
                }

                $scope.find = function () {

                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;


                    if (!$scope.search.idCliente) {
                        showAlert(ERROR_TITLE, 'Ingrese un Cliente Valido');
                        return;
                    }

                    if (!$scope.search.fechaInicio) {
                        showAlert(ERROR_TITLE, 'Ingrese una fecha inicio');
                        return;
                    }
                    
                    $scope.loading = true;
                    
                    return $http({
                        method: 'POST',
                        url: `${url.value}generar`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            //$scope.sumarTotales();
                            console.log($scope.mainGrid);
                            $scope.showTable = true;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.mainGrid = [];
                        }
                        $scope.loading = false;
                    }, $scope.errorFunction);
                }

                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
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

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }


                $scope.getClientes();

                $scope.exportar = function () {
                    window.open(`../../ReportesComisionClienteServlet?pi=${$scope.search.fechaInicio}&pf=${$scope.search.fechaFin}&id=${$scope.search.idCliente.id}&tp=${$scope.search.tipoCupon}`, '_blank');
                }


            }
        ]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});
app.filter('printEstado', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        else {
                if (input === 'D') {
                    return 'CANCELADO'
                }else if (input === 'E'){
                    return 'EMITIDO';
                }else if (input === 'P'){
                    return 'PENDIENTE';
                }else if (input === 'A'){
                    return 'ANULADO';
                }else if (input === 'M'){
                    return 'EN MORA';
                }
           
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

