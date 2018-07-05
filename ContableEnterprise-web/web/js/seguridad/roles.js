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

var app = angular.module("jsRoles", ['jsRoles.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsRoles.controllers', []).controller('frmRoles', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        //var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTUyNzg0MjcwNCwiaWF0IjoxNTI3Nzk5NTA0LCJ2YWx1ZSI6ImFkbWluIn0.20_NgML0xEVr3W9R7RuZOxqno1cLk7cMq5bOtNDLZGQ";//document.getElementsByName("hdToken")[0];
        //var url = "http://localhost:8080/ContableEnterprise-ws/ws-api/roles/form-perm" //document.getElementsByName("hdUrl")[0];

        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];

        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.loading = false;
        $scope.formDataNew = {};
        $scope.formDataEdit = {};
        $scope.mainGrid = {};

        $scope.showFormEdit = false;
        $scope.showFormNew = false;
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
                        $scope.formDataEdit = response.data.content;
                        $scope.showFormEdit = true;
                    }

                    if (method === 'new') {
                        $scope.formDataNew = response.data.content;
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

        $scope.getData('all', $scope.data);
        $scope.getData('new', $scope.formDataNew);
        //$scope.getData('roles', $scope.roles);

        $scope.guardar = function () {
            if (!$scope.formNew.$valid)
                return;
            $scope.loading = true;
            var data = {
                rolnombre: $scope.formDataNew.nombre,
                rolstatus: $scope.formDataNew.status,
                list: $scope.formDataNew
            };
            $http({
                method: 'POST',
                url: url.value + 'save',
                data: {token: token.value, content: data},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {

                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showFormNew = false;
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
            if (!$scope.formEdit.$valid)
                return;
            $scope.loading = true;

            $http({
                method: 'POST',
                url: url.value + 'update',
                data: {token: token.value, content: $scope.formDataEdit},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showFormEdit = false;
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


        $scope.delete = function () {
            /*if (!$scope.formEdit.$valid)
                return;*/
            console.log("delete;" + $scope.idDelete);
            $scope.loading = true;

            $http({
                method: 'POST',
                url: url.value + 'delete',
                data: {token: token.value, content: { rolid : $scope.idDelete} },
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showTable = true;
                    $scope.getData('all');
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

        $scope.editar = function (id) {
            $scope.loading = true;
            $scope.showTable = false;
            $scope.showError = false;
            $scope.showFormEdit = false;
            $scope.showFormNew = false;
            if (id > 0) {
                $http({
                    method: 'POST',
                    url: url.value + 'get',
                    data: {token: token.value, content: {"idRol": id}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    $scope.loading = false;
                    if (response.data.code === 201) {
                        $scope.formDataEdit = response.data.content;
                        $scope.showFormEdit = true;
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                    }
                }, function (error) {
                    $scope.showRestfulMessage = error;
                    $scope.showRestfulError = true;
                    $scope.loading = false;
                });
            }
        }

        $scope.nuevo = function () {
            $scope.getData('new', $scope.formDataNew);
            $scope.showFormNew = true;
            $scope.showFormEdit = false;
            $scope.showBtnNuevo = true;
            $scope.showTable = false;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;

        }

        $scope.cancelar = function () {
            $scope.showFormNew = false;
            $scope.showFormEdit = false;
            $scope.showTable = true;
        }

        $scope.modalEliminar = function (idRol, nombreRol) {
            $scope.idDelete = idRol;
            $scope.nombreDelete = nombreRol;
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

