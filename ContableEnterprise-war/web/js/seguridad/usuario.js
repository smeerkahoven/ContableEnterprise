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

var app = angular.module("jsUsuario", ['jsUsuario.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsUsuario.controllers', []).controller('frmUsuario', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.userExists = false;
        $scope.userAgree = false;
        $scope.userLength = false;

        $scope.loading = false;
        $scope.formData = {};
        $scope.data = {};
        $scope.roles = {};
        $scope.empleados = {};

        $scope.showForm = false;
        $scope.showTable = true;

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
                    if (method === 'all')
                        $scope.data = response.data.content;
                    if (method === 'personal')
                        $scope.empleados = response.data.content;
                    if (method === 'roles')
                        $scope.roles = response.data.content;

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

        $scope.getData('all', $scope.data);
        $scope.getData('personal', $scope.empleados);
        $scope.getData('roles', $scope.roles);

        $scope.guardar = function () {
            if (!$scope.myForm.$valid)
                return;
            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'guardar',
                data: {token: token.value, content: angular.toJson($scope.formData)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.getData('all');
                    $scope.formData = response.data.content;
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showForm = false;
                    $scope.showTable = true;
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = true;
                }
            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
                $scope.showForm = true;
            });
        }

        $scope.nuevo = function () {
            $scope.formData = {};
            $scope.showForm = true;
            $scope.showNuevo = true;
            $scope.showEditar = false;
            $scope.showTable = false;

            $scope.userLength = false;
            $scope.userExists = false;
            $scope.userAgree = false;

            $scope.myForm.$setPristine();
            $scope.myForm.$submitted = false;
            $scope.myForm.txtPassword.$touched = false;
            $scope.myForm.txtConfirmPwd.$touched = false;
        }

        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
            $scope.userLength = false;
            $scope.userExists = false;
            $scope.userAgree = false;
        }

        $scope.showConfirmation = function (username, query, opcion) {
            $scope.deacUsername = username;
            $scope.comando = query;
            $scope.opcion = opcion;
        }

        $scope.update = function (query) {
            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'update',
                data: {token: token.value, content: {username: $scope.deacUsername, comando: $scope.comando}},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.formData = response.data.content;
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.getData('all');
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = false;
                }
            }, function (error) {
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                $scope.loading = false;
            });
        }


        $scope.validar = function () {
            if ($scope.formData.username === undefined)
                return;
            $scope.userExists = false;
            $scope.userAgree = false;
            if ($scope.formData.username.length < 4) {
                $scope.userLength = true;
                $scope.userExists = false;
                $scope.userAgree = false;
            } else {
                $scope.loading = true;
                $http({
                    method: 'POST',
                    url: url.value + 'validate',
                    data: {token: token.value, content: {username: $scope.formData.username}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    if (response.data.code === 105) {//exissts
                        $scope.userExists = true;
                    } else {
                        $scope.userAgree = true;
                    }
                    $scope.loading = false;

                }, function (error) {
                    $scope.showRestfulMessage = error.statusText;
                    $scope.showRestfulError = true;
                    $scope.loading = false;
                });
            }
        }

        $scope.disableButton = function () {
            if ($scope.formData.confirmpwd === undefined || $scope.formData.password === undefined) {
                return true;
            } else if ($scope.formData.confirmpwd.length < 4 && $scope.formData.password.length < 4) {
                return true;
            } else if ($scope.formData.password !== $scope.formData.confirmpwd) {
                return true;
            } else if ($scope.userAgree) {
                return false;
            }
            return true;

        }

    }
]);


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

