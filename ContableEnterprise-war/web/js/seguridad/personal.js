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

var app = angular.module("jsPersonal", ['jsPersonal.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsPersonal.controllers', []).controller('frmPersonal', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var modalInstance = null;
        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.loading = false;
        $scope.formData = {};
        $scope.data = {};
        $scope.sucursales = {};
        $scope.showForm = false;
        $scope.showTable = true;

        $scope.itemsByPage = 15;

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
                    console.log('getdata');
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }

            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
            });
            
            $http({
                method: 'POST',
                url: url.value + 'sucursales',
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    
                    
                    $scope.sucursales = response.data.content;
                    $scope.formData.idEmpresa = $scope.sucursales[0].id;
                    $scope.loading = false;
                    console.log($scope.sucursales);
                    
                    console.log($scope.formData);
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

        $scope.guardar = function () {
            if (!$scope.myForm.$valid)
                return;
            $scope.loading = true;
            console.log(angular.toJson($scope.formData));
            $http({
                method: 'POST',
                url: url.value + 'guardar',
                data: {token: token.value, content: angular.toJson($scope.formData)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.formData = response.data.content;
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.getData();
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = true;
                }

            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
                $scope.showForm = true;
            });
        }

        $scope.actualizar = function () {
            if (!$scope.myForm.$valid)
                return;
            console.log(angular.toJson($scope.formData));
            $scope.loading = true;
            $http({
                method: 'POST',
                url: url.value + 'actualizar',
                data: {token: token.value, content: angular.toJson($scope.formData)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                $scope.loading = false;
                if (response.data.code === 201) {
                    $scope.showRestfulSuccess = true;
                    console.log('actualizar');
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.getData();
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = true;
                }

            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
                $scope.showForm = true;
            });
        }

        $scope.editar = function (id) {
            $scope.loading = true;
            $scope.showTable = false;
            $scope.showEditar = true;
            $scope.showNuevo = false;
            $scope.showError = false;
            if (id > 0) {
                $http({
                    method: 'POST',
                    url: url.value + 'get',
                    data: {token: token.value, content: {"idEmpleado": id}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    if (response.data.code === 201) {
                        $scope.formData = response.data.content;
                        
                        console.log($scope.formData);
                        //document.getElementById($scope.formData.idEmpresa).selected = "true" ; 
                        //document.getElementById("txtEmpresa").value = $scope.formData.idEmpresa ; 
                        //console.log(document.getElementById($scope.formData.idEmpresa));
                        $scope.showForm = true;
                        $scope.showTable = false;
                        $scope.loading = false;
                        //document.getElementById("txtEmpresa").options[1].selected= 'selected' ;//= $scope.formData.idEmpresa ;
                    
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                    }

                }, function (error) {
                    $scope.showRestfulMessage = error;
                    $scope.showRestfulError = true;
                });
                
                // 11:30 rodrigo farias
            }
        }

        $scope.ver = function (id) {
            console.log(id);
            if (id > 0) {
                $http({
                    method: 'POST',
                    url: url.value + 'get',
                    data: {token: token.value, content: {"idEmpleado": id}},
                    headers: {'Content-Type': 'application/json'}
                }).then(function (response) {
                    if (response.data.code === 201) {
                        $scope.verForm = response.data.content;
                    } else {
                        $scope.showRestfulMessage = response.data.content;
                        $scope.showRestfulError = true;
                    }

                }, function (error) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                });
            }
        }

        $scope.nuevo = function () {
            $scope.formData = {};
            $scope.showForm = true;
            $scope.showNuevo = true;
            $scope.showEditar = false;
            $scope.showTable = false;
        }

        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
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

