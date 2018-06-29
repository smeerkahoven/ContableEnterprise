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

var app = angular.module("jsPlanCuentas", ['jsPlanCuentas.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsPlanCuentas.controllers', []).controller('frmPlanCuentas', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        $scope.showAll = false;
        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var formName = document.getElementsByName("hdFormName")[0].value;

        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.showFormNewTransaction = false;

        $scope.loading = false;
        $scope.frmNewTransaction = {};
        $scope.frmDelTransaction = {};
        $scope.frmPlanCuentas = {};
        $scope.mainGrid = {};
        $scope.comboAitb = {};

        $scope.showForm = false;
        $scope.showTable = false;
        $scope.showBtnNuevo = true;

        $scope.itemsByPage = 15;

        $scope.getData = function (method) {
            $scope.loading = true;
            //console.log(formName);
            return $http({
                method: 'POST',
                url: url.value + method,
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                console.log(url.value + method);
                if (response.data.code === 201) {
                    if (method === 'all') {
                        $scope.mainGrid = response.data.content;
                        $scope.showTable = true;
                        $scope.showAll = true;
                        $scope.loading = false;
                    }
                    if (method === 'combo') {
                        $scope.comboAitb = response.data.content;
                    }
                } else {
                    console.log("Error aqui" +rl.value + method);
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    return {};
                }
            }, function (error) {
                console.log("url.value + method Error de Respuesta");
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
            });
        }

        $scope.save = function () {
            if (!$scope.formNewTransaction.$valid) {
                return;
            }
            $scope.frmNewTransaction.ctaItb = $scope.frmNewTransaction.itb.id;
            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'save',
                data: {token: token.value, formName: formName, content: angular.toJson($scope.frmNewTransaction)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.getData('all', $scope.data);
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;

                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = false;
                }
                $scope.loading = false;
                $scope.showFormNewTransaction = false;
                $scope.showTable = true;

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                $scope.showFormNewTransaction = false;
            });
        };

        $scope.update = function () {
            if (!$scope.myForm.$valid)
                return;
            $scope.loading = true;

            $scope.frmNewTransaction.ctaItb = $scope.frmNewTransaction.itb.id;
            $http({
                method: 'POST',
                url: url.value + 'update',
                data: {token: token.value, formName: formName, content: angular.toJson($scope.frmNewTransaction)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.getData('all');
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }
                $scope.loading = false;
                $scope.showFormNewTransaction = false;
                $scope.showTable = true;

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.delete = function () {

            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'delete',
                data: {token: token.value, formName: formName, content: angular.toJson($scope.frmDelTransaction)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {

                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    //$scope.showTable = true;
                    $scope.getData('all');
                    $scope.getData('combo');
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.get = function (id) {
            $scope.loading = true;
            var data = {idPlanCuentas: id};
            $http({
                method: 'POST',
                url: url.value + 'get',
                data: {token: token.value, formName: $scope.formName, content: angular.toJson(data)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    //console.log(response.data.content);
                    $scope.frmPlanCuentas = response.data.content;
                    $scope.frmNewTransaction.idPlanCuentaPadreNombre = $scope.frmPlanCuentas.nombreCuenta;
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }
                $scope.loading = false;
            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.nuevo = function () {
            $scope.showForm = true;
            $scope.showBtnNuevo = true;
            $scope.showBtnEditar = false;
            $scope.showTable = false;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
        }

        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
            $scope.showFormNewTransaction = false;
        }

        $scope.modalEliminar = function (id, nombre) {
            $scope.frmDelTransaction.idPlanCuentas = id;
            $scope.frmDelTransaction.nombreCuenta = nombre
            $scope.idEliminar = id;
            $scope.nombreEliminar = nombre;
        }

        $scope.showBtnAdicionar = function (nivel) {
            return nivel < 5;
        }

        $scope.getItem = function (item) {
            $scope.selectedId = item.idPlanCuentas;
        }

        $scope.add = function (item) {
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
            $scope.showLoading = true;
            $scope.showBtnNuevo = true;
            $scope.showBtnActualizar = false;
            $scope.showBtnNuevoForm = false;
            $scope.frmNewTransaction.idEmpresa = item.idEmpresa;
            $scope.frmNewTransaction.idPlanCuentas = $scope.getLastIdPlanCuentas(item);
            $scope.frmNewTransaction.idPlanCuentaPadre = item.idPlanCuentas;
            $scope.frmNewTransaction.idPlanCuentaPadreNombre = item.nombreCuenta;
            $scope.frmNewTransaction.nombreCuenta = '';
            $scope.frmNewTransaction.nivel = item.nivel + 1;
            $scope.frmNewTransaction.marco = item.marco;
            $scope.frmNewTransaction.marcoNombre = item.marcoNombre;
            $scope.frmNewTransaction.moneda = 'B';
            $scope.frmNewTransaction.mantenimientoValor = 'MON';
            $scope.frmNewTransaction.itb = '';

            if (item.nivel < 4) {
                $scope.frmNewTransaction.aplicaMovimiento = 'M';
                $scope.frmNewTransaction.aplicaMovimientoNombre = 'Mayorizacion';
                $scope.frmNewTransaction.moneda = null;
                $scope.frmNewTransaction.mantenimientoValor = null;
            } else {
                $scope.frmNewTransaction.aplicaMovimiento = 'T';
                $scope.frmNewTransaction.aplicaMovimientoNombre = 'Transaccion';
            }

            $scope.showFormNewTransaction = true;
            $scope.showTable = false;
            $scope.showLoading = false;
        }


        $scope.edit = function (item) {
            $scope.get(item.idPlanCuentaPadre);
            $scope.showLoading = true;
            $scope.showBtnNuevo = false;
            $scope.showBtnActualizar = true;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;

            $scope.showBtnNuevoForm = false;
            $scope.frmNewTransaction.idEmpresa = item.idEmpresa;
            $scope.frmNewTransaction.idPlanCuentas = item.idPlanCuentas;
            $scope.frmNewTransaction.idPlanCuentaPadre = item.idPlanCuentaPadre;
            $scope.frmNewTransaction.nombreCuenta = item.nombreCuenta;
            $scope.frmNewTransaction.nivel = item.nivel;
            $scope.frmNewTransaction.marco = item.marco;
            $scope.frmNewTransaction.marcoNombre = item.marcoNombre;
            $scope.frmNewTransaction.itb = $scope.findCta(item.ctaItb, $scope.comboAitb);
            $scope.frmNewTransaction.ctaItb = ($scope.frmNewTransaction.itb != undefined ? $scope.frmNewTransaction.itb.id : null);
            $scope.frmNewTransaction.aplicaMovimiento = item.aplicaMovimiento;
            $scope.frmNewTransaction.aplicaMovimientoNombre = item.aplicaMovimientoNombre;

            if (item.nivel < 4) {
                $scope.frmNewTransaction.moneda = null;
                $scope.frmNewTransaction.mantenimientoValor = null;
            } else {
                $scope.frmNewTransaction.moneda = item.moneda;
                $scope.frmNewTransaction.mantenimientoValor = item.mantenimientoValor;
            }

            $scope.showFormNewTransaction = true;
            $scope.showTable = false;
            $scope.showLoading = false;
        }

        $scope.findCta = function (cta, input) {
            var i = 0;
            for (i; i < input.length; i++) {
                if (input[i].id == cta) {
                    //console.log(input[i]);
                    return input[i];
                }
            }
        }

        $scope.getLastIdPlanCuentas = function (item) {
            if (item.children != undefined && item.children.length > 0) {
                var idlast = 0;
                for (var i in item.children) {
                    idlast = item.children[i].idPlanCuentas;
                }
                return idlast + 1;
            } else {
                if (item.nivel <= 3) {
                    return (item.idPlanCuentas * 1000) + 1
                } else {
                    return (item.idPlanCuentas * 1000) + 1
                }
            }
        }

        $scope.getData('all', $scope.data);
        $scope.getData('combo', $scope.data);
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

