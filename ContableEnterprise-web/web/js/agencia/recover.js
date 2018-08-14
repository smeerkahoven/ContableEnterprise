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

var app = angular.module("jsRecover", ['jsRecover.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsRecover.controllers', []).controller('frmRecover',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {
                //var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTUyNzg0MjcwNCwiaWF0IjoxNTI3Nzk5NTA0LCJ2YWx1ZSI6ImFkbWluIn0.20_NgML0xEVr3W9R7RuZOxqno1cLk7cMq5bOtNDLZGQ";//document.getElementsByName("hdToken")[0];
                //var url = "http://localhost:8080/ContableEnterprise-ws/ws-api/roles/form-perm" //document.getElementsByName("hdUrl")[0];

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlUser = document.getElementsByName("hdUrlUser")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementsByName("myForm")[0];
                var method = "update-pwd";

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showRecover = true;

                $scope.itemsByPage = 15;
                $scope.newPassword = '';

                $scope.checkButton = function () {
                    if ($scope.newPassword) {
                        return  $scope.newPassword.length < 5;
                    } else {
                        return !$scope.newPassword;
                    }
                }

                $scope.save = function () {
                    $scope.showRecover = true;
                    var data = {usr: urlUser.value, pwd: $scope.newPassword};
                    return $http({
                        method: 'POST',
                        url: url.value ,
                        data: {token: '', content: data},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.showRecover = false;
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


                $scope.formHasError = function () {
                    return (
                            (!$scope.formData.boletosMonNac && !$scope.formData.boletosMonExt));

                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
                }

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos');
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos');
                        return;
                    }

                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData), formName: formName.value},
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

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.hideMessagesBox();
                }

                $scope.findCta = function (cta, input) {
                    var i = 0;
                    if (input) {
                        for (i; i < input.length; i++) {
                            if (input[i].id == cta) {
                                //console.log(input[i]);
                                return input[i];
                            }
                        }
                    }
                }

                $scope.modalEliminar = function (idx, nombrex, methodx) {
                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
                }


                $scope.$watch('formData.ctaDevolucionMonExt.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $scope.showErrorCtaDevolucionMonExt = false;
                    }
                });
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

