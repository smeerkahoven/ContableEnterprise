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

var app = angular.module("jsBancos", ['jsBancos.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBancos.controllers', []).controller('frmBancos', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {

        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var frmName = document.getElementsByName("hdFrmName")[0];

        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.loading = false;
        $scope.frmNew = {};
        $scope.mainGrid = {};
        $scope.comboCuentasBanco = {};

        $scope.showForm = false;
        $scope.showTable = false;

        $scope.itemsByPage = 15;

        $scope.getData = function (method) {
            $scope.loading = true;
            return $http({
                method: 'POST',
                url: url.value + method,
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === 'all') {
                        $scope.mainGrid = response.data.content;
                        $scope.showTable = true;
                    }

                    if (method === 'edit') {
                        $scope.formData = response.data.content;
                        $scope.showForm = true;
                    }

                    if (method === 'combo') {
                        $scope.comboCuentasBanco = response.data.content;
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

        $scope.save = function () {
            if (!$scope.myForm.$valid)
                return;
            $scope.loading = true;

            $http({
                method: 'POST',
                url: url.value + 'save',
                data: {token: token.value, content: angular.toJson($scope.frmNew)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {

                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.getData('all', $scope.data);
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

        $scope.update = function () {
            if (!$scope.myForm.$valid)
                return;
            $scope.loading = true;

            $http({
                method: 'POST',
                url: url.value + 'update',
                data: {token: token.value, content: angular.toJson($scope.frmNew)},
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
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.link = function (item) {
            $scope.showLinkRestfulError = false;
            $scope.showLinkRestfulSuccess = false;
            $scope.linkLoading = true;
            $scope.frmLink = item;
            $scope.frmNewLink = {idBanco: item.idBanco, descripcion: ""};
            $http({
                method: 'POST',
                url: url.value + 'link-grid',
                data: {token: token.value, content: angular.toJson(item)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.cuentaBancoGrid = response.data.content;
                    //$scope.getData();
                } else {
                    $scope.showLinkRestfulMessage = response.data.content;
                    $scope.showLinkRestfulError = true;
                }
                $scope.linkLoading = false;

            }, function (error) {
                $scope.linkLoading = false;
                $scope.showLinkRestfulMessage = error.statusText;
                $scope.showLinkRestfulError = true;
                //$scope.showForm = true;
            });
        };


        $scope.addlink = function () {
            if (!$scope.formLink.$valid)
                return;

            $scope.linkLoading = true;
            $scope.frmNewLink.idPlanCuentas = $scope.frmNewLink.cuenta.id;
            var data = {idBanco: $scope.frmNewLink.idBanco, idPlanCuentas: $scope.frmNewLink.idPlanCuentas, descripcion: $scope.frmNewLink.descripcion};
            $http({
                method: 'POST',
                url: url.value + 'add-link',
                data: {token: token.value, content: angular.toJson(data)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.linkLoading = false;
                if (response.data.code === 201) {
                    $scope.showLinkRestfulMessage = response.data.content;
                    $scope.showLinkRestfulSuccess = true;
                    $scope.link($scope.frmLink);
                } else {
                    $scope.showLinkRestfulMessage = response.data.content;
                    $scope.showLinkRestfulError = true;

                }
                $scope.linkLoading = false;

            }, function (error) {
                $scope.linkLoading = false;
                $scope.showLinkRestfulMessage = error.statusText;
                $scope.showLinkRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.delete = function (method) {

            $scope.linkLoading = true;
            $scope.loading = true;
            var data = {};
            if (method === 'delete-link')
                data = $scope.linkDelete;
            else
                data = {idBanco: $scope.idEliminar};

            $http({
                method: 'POST',
                url: url.value + method,
                data: {token: token.value, content: angular.toJson(data)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === 'delete-link') {
                        $scope.link($scope.frmLink);
                        $scope.showLinkRestfulMessage = response.data.content;
                        $scope.showLinkRestfulSuccess = true;
                        $scope.linkLoading = false;
                        $scope.loading = false;
                    } else {
                        $scope.getData('all');
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulSuccess = true;
                        $scope.loading = false;
                    }

                } else {
                    if (method === 'delete-link') {
                        $scope.showLinkRestfulMessage = response.data.content;
                        $scope.showLinkRestfulError = true;
                        $scope.linkLoading = true;
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                        $scope.loading = false;
                    }
                }

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };

        $scope.edit = function (row) {
            $scope.loading = true;
            $scope.showTable = false;
            $scope.showError = false;
            $scope.showForm = true;
            $scope.frmNew = row;
            $scope.loading = false;
            $scope.showBtnActualizar = true;
            $scope.showBtnNuevo = false;
            $scope.showRestfulError = false;
            $scope.showRestfulSuccess = false;
        }

        $scope.nuevo = function () {
            $scope.showForm = true;
            $scope.showBtnNuevo = true;
            $scope.showBtnActualizar = false;
            $scope.showTable = false;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
            $scope.frmNew = {};

        }

        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
        }

        $scope.modalEliminar = function (id, nombre, method) {
            $scope.idEliminar = id;
            $scope.nombreEliminar = nombre;
            $scope.methodEliminar = method;
        }

        $scope.deleteLink = function (row, method) {
            $scope.idEliminar = row[4];
            $scope.nombreEliminar = row[1] + " " + row[5];
            $scope.methodEliminar = method;

            $scope.linkDelete = {idPlanCuentas: row[0], idEmpresa: row[2], idBanco: row[3], idCuentaBanco: row[4]};
        }

        $scope.deleteBanco = function (row, method) {
            $scope.idEliminar = row.idBanco;
            $scope.nombreEliminar = row.nombre;
            $scope.methodEliminar = method;
        }


        $scope.getData('all');
        $scope.getData('combo');
    }
]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});

app.directive('validNumber', function () {
    return {
        require: '?ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
            if (!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function (val) {
                if (angular.isUndefined(val)) {
                    var val = '';
                }
                var clean = val.replace(/[^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function (event) {
                if (event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

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

