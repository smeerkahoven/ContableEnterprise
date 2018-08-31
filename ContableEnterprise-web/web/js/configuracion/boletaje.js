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
let SI = 'S';
let NO = 'N';


var app = angular.module("jsBoletaje", ['jsBoletaje.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBoletaje.controllers', []).controller('frmBoletaje',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showForm = true;
                $scope.showTable = false;

                $scope.itemsByPage = 15;

                $scope.getData = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}get/${$scope.formData.idEmpresa}`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData = response.data.content;
                            console.log($scope.formData);
                            $scope.getCuentas();
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
                    console.log($scope.formData);
                }
                $scope.save = function () {
                    let saveData = $scope.formData;
                    if (!$scope.myForm.$valid) {
                        //$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formData.emisionBolivianos) {
                        if ($scope.formData.idTotalBoletoBs.id === undefined) {
                            $scope.showAlert('Error de Verificacion', 'Ingrese un cuenta para Total Boletos en Bolivianos');
                            return;
                        } else {
                            saveData.idTotalBoletoBs = $scope.formData.idTotalBoletoBs.id;
                        }
                    }
                    if ($scope.formData.emisionDolares) {
                        if ($scope.formData.idTotalBoletoUs.id === undefined) {
                            $scope.showAlert('Error de Verificacion', 'Ingrese un cuenta para Total Boletos en Dolares');
                            return;
                        } else {
                            saveData.idTotalBoletoUs = $scope.formData.idTotalBoletoUs.id;
                        }
                    }

                    if ($scope.formData.idCuentaFee !== undefined) {
                        if ($scope.formData.idCuentaFee.id === undefined) {
                            $scope.showAlert('Error de Verificacion', 'Ingrese un cuenta para cuenta FEE');
                            return;
                        } else {
                            saveData.idCuentaFee = $scope.formData.idCuentaFee.id;
                        }
                    }

                    if ($scope.formData.idDescuentos !== undefined) {
                        if ($scope.formData.idDescuentos.id === undefined) {
                            $scope.showAlert('Error de Verificacion', 'Ingrese un cuenta para Descuentos');
                            return;
                        } else {
                            saveData.idDescuentos = $scope.formData.idDescuentos.id;
                        }
                    }

                    $scope.loading = true;
                    saveData.emisionBolivianos = $scope.formData.emisionBolivianos ? SI : NO;
                    saveData.emisionDolares = $scope.formData.emisionDolares ? SI : NO;
                    console.log(saveData);

                    $http({
                        method: 'POST',
                        url: `${url.value}save`,
                        data: {token: token.value, content: angular.toJson(saveData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showForm = true;
                            $scope.getData(url.value, 'all');
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

                $scope.getComboEmpresas = function () {
                    $scope.loading = true;
                    //console.log(formName);
                    return $http({
                        method: 'POST',
                        url: `${urlEmpresa.value}all-combo`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboEmpresa = response.data.content;
                            $scope.showTable = true;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                        $scope.loading = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                        $scope.loading = false;
                    });
                }

                $scope.getCuentas = function () {
                    $scope.loading = true;
                    $scope.showBtnNuevo = false;
                    return $http({
                        method: 'POST',
                        url: `${urlPlanCuentas.value}combo/${$scope.formData.idEmpresa}`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboCuentas = response.data.content;
                            $scope.formData.idTotalBoletoBs = $scope.findCta($scope.formData.idTotalBoletoBs, $scope.comboCuentas);
                            $scope.formData.idTotalBoletoUs =$scope.findCta($scope.formData.idTotalBoletoUs, $scope.comboCuentas);
                            $scope.formData.idCuentaFee = $scope.findCta($scope.formData.idCuentaFee, $scope.comboCuentas);
                            $scope.formData.idDescuentos = $scope.findCta($scope.formData.idDescuentos, $scope.comboCuentas);
                            $scope.loading = false;
                            $scope.showBtnNuevo = true;
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
                    if ($scope.formData.idEmpresa === undefined) {
                        return true;
                    }

                    if ($scope.formData.emisionBolivianos) {
                        if ($scope.formData.idTotalBoletoBs === undefined) {
                            return true;
                        } else {
                            if ($scope.formData.idTotalBoletoBs.id === undefined) {
                                return true;
                            }
                        }
                    }

                    if ($scope.formData.emisionDolares) {
                        if ($scope.formData.idTotalBoletoUs === undefined) {
                            return true;
                        } else {
                            if ($scope.formData.idTotalBoletoUs.id === undefined) {
                                return true;
                            }
                        }
                    }

                    if ($scope.formData.idCuentaFee === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.idCuentaFee.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.idDescuentos === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.idDescuentos.id === undefined) {
                            return true;
                        }
                    }
                }

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    $scope.loading = true;

                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
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
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                        //$scope.showForm = true;
                    });
                };


                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.edit = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.showBtnNuevo = false;
                    $scope.showBtnEditar = true;
                    $scope.hideMessagesBox();
                    $scope.formData = item;

                    $scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);
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

                $scope.modalEliminar = function (idx, nombrex, methodx) {
                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
                }


                $scope.findCta = function (cta, input) {
                    var i = 0;
                    if (cta !== undefined) {
                        for (i; i < input.length; i++) {
                            if (input[i].id === cta) {
                                return input[i];
                            }
                        }
                    }
                }

                $scope.getComboEmpresas();

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

