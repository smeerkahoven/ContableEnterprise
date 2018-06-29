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

var app = angular.module("jsSucursal", ['jsSucursal.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsSucursal.controllers', []).controller('frSucursal', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var modalInstance = null;
        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.loading = false;
        $scope.formData = {};
        $scope.getData = function () {
            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'all',
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.data = response.data.content;
                    $scope.loading = false;
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }

            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
            });
        }
        $scope.getData();
        $scope.itemsByPage = 15;
        $scope.editar = function (id) {
            $scope.loading = true;
            $scope.showTable = false;
            $scope.showEditar = true;
            $scope.showNuevo = false;
            $scope.showMessages = false;
            $scope.ocultar();
            if (id > 0) {
                $http({
                    method: 'POST',
                    url: url.value + 'get',
                    data: {token: token, content: {"idEmpresa": id}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    if (response.data.code === 201) {
                        $scope.formData = response.data.content;
                        $scope.showForm = true;
                        $scope.showTable = false;
                        $scope.loading = false;
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                    }

                }, function (error) {});
            }
        }


        $scope.ver = function (id) {
            $scope.ocultar();
            if (id > 0) {
                $http({
                    method: 'POST',
                    url: url.value + 'get',
                    data: {token: token, content: {"idEmpresa": id}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    if (response.data.code === 201) {
                        $scope.verForm = response.data.content;
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                    }

                }, function (error) {});
            }
        }

        $scope.data = {};
        $scope.showForm = false;
        $scope.showTable = true;
        $scope.editData = {};

        $scope.nuevo = function () {
            $scope.formData = {};
            $scope.showForm = true;
            $scope.showNuevo = true;
            $scope.showEditar = false;
            $scope.showTable = false;
            $scope.showMessages = false;
            $scope.ocultar();
        }


        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
        }

        $scope.cancelModal = function () {
            modalInstance.close(false);

        }

        $scope.ocultar = function () {
            var x = document.getElementById("divSuccess");
            if (x != null)
                x.style.display = "none";

            var y = document.getElementById("divError");
            if (y != null)
                y.style.display = "none";
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

