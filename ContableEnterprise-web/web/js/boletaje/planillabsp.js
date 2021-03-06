/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var tableToExcel = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,'
            , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" \n\
    xmlns:x="urn:schemas-microsoft-com:office:excel" \n\
    xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
            , base64 = function (s) {
                return window.btoa(unescape(encodeURIComponent(s)))
            }
    , format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p) {
            return c[p];
        })
    }
    return function (table, name) {
        if (!table.nodeType)
            table = document.getElementById(table)
        var ctx = {worksheet: "hola" || 'Worksheet', table: table.innerHTML}
        window.location.href = uri + base64(format(template, ctx))
    }
})();

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

                    if (!$scope.search.tipoCupon) {
                        showAlert(ERROR_TITLE, 'Seleccione un tipo de Cupon');
                        return;
                    }
                    if (!$scope.search.fechaInicio) {
                        showAlert(ERROR_TITLE, 'Seleccione una Fecha de Inicio');
                        return;
                    }
                    if (!$scope.search.fechaFin) {
                        showAlert(ERROR_TITLE, 'Seleccione una Fecha Fin');
                        return;
                    }

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
                            $scope.mainGrid = [];
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

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.search = {fechaInicio: firstDay, fechaFin: today};
                    $scope.hideMessagesBox();
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
                        totalNetoAPagar += $scope.mainGrid[i].totalMontoCobrar !== undefined ? $scope.mainGrid[i].totalMontoCobrar : Number(0);

                    }

                    $scope.totalImporteNeto = totalImporteNeto.toFixed(2);
                    $scope.totalImpuestos = totalImpuestos.toFixed(2);
                    $scope.totalBoleto = totalBoleto.toFixed(2);
                    $scope.totalMontoComision = totalMontoComision.toFixed(2);
                    $scope.totalNetoAPagar = totalNetoAPagar.toFixed(2);

                }

                $scope.exportar = function () {
                    window.open(`../../PlanillaBspReportServlet?pi=${$scope.search.fechaInicio}&pf=${$scope.search.fechaFin}&tp=${$scope.search.tipoCupon}`, '_blank');
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

