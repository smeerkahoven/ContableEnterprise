/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module("jsLogs", ['jsLogs.controllers', 'smart-table', 'ui.bootstrap']);
angular.module('jsLogs.controllers', []).controller('frmLogs',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlUser = document.getElementsByName("hdUser")[0];
                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};
                $scope.showForm = false;
                $scope.showTable = true;
                $scope.itemsByPage = 15;

                $scope.find = function () {
                    if ($scope.search) {
                        if ($scope.search.username) {
                            if (!$scope.search.username.id) {
                                showAlert(ERROR_TITLE, 'Ingrese un usuario valido');
                                return;
                            }
                        }
                    }
                    $scope.loading = true;

                    $http({method: 'POST',
                        url: `${url.value}find`,
                        headers: {'Content-type': 'application/json'},
                        data: {token: token.value, content: angular.toJson($scope.search)}
                    }).then(
                            function (response) {
                                $scope.loading = false;
                                if (response.data.code === 201) {
                                    $scope.mainGrid = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.formHasError = function () {
                    if ($scope.formData.idEmpresa === undefined) {
                        return true;
                    }
                    return (!$scope.formData.idEmpresa.id);
                }

                $scope.getUsers = function () {
                    $http({
                        method: 'POST',
                        url: `${urlUser.value}combo`,
                        headers: {'Content-Type': 'application/json'},
                        data: {token: token.value}
                    }).then(function (response) {
                        if (response.data.code === 201) {

                            $scope.comboUsuario = response.data.content;
                        }
                    }, $scope.errorFunction);
                }

                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.search = {};
                    $scope.hideMessagesBox();
                }

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
                }

                $scope.getUsers();

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
app.filter('printNumber', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        return new Number(input).toFixed(2);
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

