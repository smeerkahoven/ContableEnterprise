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

var app = angular.module("jsEstadosResultados", ['jsEstadosResultados.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsEstadosResultados.controllers', []).controller('frmEstadosResultados',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlComprobantes = document.getElementsByName("hdUrlComprobantes")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var idEmpresa = document.getElementsByName("idEmpresa")[0];
                var myForm = document.getElementById("myForm");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.showTable = false;

                $scope.idEmpresa = idEmpresa.value;
                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.search = {};

                $scope.showForm = false;

                $scope.itemsByPage = 15;
                
                $scope.cancelar = function () {
                    $scope.gridMain = {} ;
                    $scope.search = {} ;
                }

                $scope.find = function () {

                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;

                    if ($scope.searchFormHasError()) {
                        return;
                    }

                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: `${url.value}generar`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {   
                            $scope.gridMain = response.data.content;
                            $scope.showTable = true;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.gridMain = [];
                        }
                    }, $scope.errorFunction);
                }

                $scope.searchFormHasError = function () {

                    if ($scope.search.moneda === undefined) {
                        $scope.showMonedaRequired = true;
                        return true;
                    } else {
                        $scope.showMonedaRequired = false;
                    }

                    if ($scope.search.fechaInicio === undefined) {
                        $scope.showFechaInicioRequired = true;
                        return true;
                    } else {
                        $scope.showFechaInicioRequired = false;
                    }
                    
                    if ($scope.search.fechaFin === undefined) {
                        $scope.showFechaFinRequired = true;
                        return true;
                    } else {
                        $scope.showFechaFinRequired = false;
                    }

                    return false;
                }
                
                 $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }



                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                
                $scope.imprimirComprobante = function (data) {
                    window.open(`../../EstadosResultadosServlet?idLibro=${data.idLibro}`, '_target');
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



                $scope.findCta = function (cta, input) {
                    var i = 0;
                    for (i; i < input.length; i++) {
                        if (input[i].id == cta) {
                            return input[i];
                        }
                    }
                }

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

app.filter('printNumero', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        else {
            var value = new Number(input).toFixed(2);

            return  value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            ;
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

