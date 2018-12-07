/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString('en-GB');
let today = new Date().toLocaleDateString('en-GB');

var app = angular.module("jsPlanillaBsp", ['jsPlanillaBsp.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsPlanillaBsp.controllers', []).controller('frmPlanillaBsp',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.loading = false;
                $scope.search = {fechaInicio: firstDay, fechaFin: today};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 99999;

                $scope.find = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}get`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.loading = false;
                            $scope.sumarTotales();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.sumarTotales = function () {
                    var totalImporteNeto = new Number(0);
                    var totalImpuestos = new Number(0);
                    var totalBoleto = new Number(0);
                    var totalMontoComision = new Number(0);
                    var totalNetoAPagar = new Number(0);

                    for (var i in $scope.mainGrid) {
                        totalImporteNeto += $scope.mainGrid[i].importeNeto !== undefined ? $scope.mainGrid[i].importeNeto : Number(0);
                        totalImpuestos += $scope.mainGrid[i].impuestos !== undefined ? $scope.mainGrid[i].impuestos : Number(0);
                        totalBoleto += $scope.mainGrid[i].totalBoleto !== undefined ? $scope.mainGrid[i].totalBoleto : Number(0);
                        totalMontoComision += $scope.mainGrid[i].montoComision !== undefined ? $scope.mainGrid[i].montoComision : Number(0);
                        totalNetoAPagar += $scope.mainGrid[i].totalMontoCancelado !== undefined ? $scope.mainGrid[i].totalMontoCancelado : Number(0);

                        console.log(`totalNetoAPagar : ${totalNetoAPagar}`);
                        console.log(`$scope.mainGrid[i].totalMontoCancelado : ${$scope.mainGrid[i].totalMontoCancelado}`);
                    }

                    $scope.totalImporteNeto = totalImporteNeto.toFixed(2);
                    $scope.totalImpuestos = totalImpuestos.toFixed(2);
                    $scope.totalBoleto = totalBoleto.toFixed(2);
                    $scope.totalMontoComision = totalMontoComision.toFixed(2);
                    $scope.totalNetoAPagar = totalNetoAPagar.toFixed(2);

                }

                $scope.exportar = function () {
                    window.open(`../../PlanillaBspReportServlet?pi=${$scope.search.fechaInicio}&pf=${$scope.search.fechaFin}`, '_blank');
                }

            }
        ]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});
app.filter('printNumber', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        return new Number(input).toFixed(2);
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

