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



var app = angular.module("jsPromotores", ['jsPromotores.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsPromotores.controllers', []).controller('frmPromotores',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");

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

                $scope.getData = function (urls, method) {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: urls + method,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            if (urls === url.value) {
                                $scope.mainGrid = response.data.content;
                                $scope.showTable = true;
                            }

                            if (urls === urlEmpresa.value) {
                                $scope.comboSucursales = response.data.content;
                            }

                            if (method === "all-combo/B") {
                                $scope.gridNacionales = response.data.content;
                            }

                            if (method === "all-combo/D") {
                                $scope.gridInternacionales = response.data.content;
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
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
                        //$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    $scope.loading = true;

                    //$scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

                    $http({
                        method: 'POST',
                        url: url.value + 'save',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showForm = false;
                            $scope.showTable = true;
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

                $scope.updateAddComisionNacional = function () {
                    $scope.loadingAddComisionNac = true;
                    var data = {
                        idAerolinea: $scope.formData.comisionNacional.idAerolinea,
                        idPromotor: $scope.formData.idPromotor,
                        tipoAerolinea: $scope.formData.comisionNacional.tipoAerolinea,
                        montoComision: $scope.formData.comisionNacionalMonto
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-comision',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.comisionNacional = '';
                            $scope.formData.comisionNacionalMonto = '';
                        } else if (response.data.code == 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddComisionNac = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddComisionInternacional = function () {
                    $scope.loadingAddComisionInt = true;
                    var data = {
                        idAerolinea: $scope.formData.comisionInternacional.idAerolinea,
                        idPromotor: $scope.formData.idPromotor,
                        tipoAerolinea: $scope.formData.comisionInternacional.tipoAerolinea,
                        montoComision: $scope.formData.comisionInternacionalMonto
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-comision',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.comisionInternacional = '';
                            $scope.formData.comisionInternacionalMonto = '';

                        } else {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddComisionInt = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                        $scope.loadingAddComisionInt = false;
                    });
                }

                $scope.addComisionNacional = function () {
                    var comision = {
                        idAerolinea: $scope.formData.comisionNacional.idAerolinea,
                        montoComision: $scope.formData.comisionNacionalMonto,
                        nombreAerolinea: $scope.formData.comisionNacional.nombreAerolinea,
                        tipoAerolinea: $scope.formData.comisionNacional.tipoAerolinea
                    }

                    if ($scope.formData.listComisionNacional === undefined) {
                        $scope.formData.listComisionNacional = [];
                        $scope.formData.listComisionNacional.push(comision);
                    } else {

                        for (var i in $scope.formData.listComisionNacional) {
                            if ($scope.formData.listComisionNacional[i].idAerolinea ===
                                    $scope.formData.comisionNacional.idAerolinea){
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return ;
                            }
                            
                            $scope.formData.listComisionNacional.push(comision);
                        }
                    }
                    $scope.formData.comisionNacional = '';
                    $scope.formData.comisionNacionalMonto = '';
                }

                $scope.addComisionInternacional = function () {
                    var comision = {
                        idAerolinea: $scope.formData.comisionInternacional.idAerolinea,
                        montoComision: $scope.formData.comisionInternacionalMonto,
                        nombreAerolinea: $scope.formData.comisionInternacional.nombreAerolinea,
                        tipoAerolinea: $scope.formData.comisionInternacional.tipoAerolinea
                    }

                    if ($scope.formData.listComisionInternacional === undefined) {
                        $scope.formData.listComisionInternacional = [];
                    }
                    $scope.formData.listComisionInternacional.push(comision);

                    $scope.formData.comisionInternacional = '';
                    $scope.formData.comisionInternacionalMonto = '';
                }

                $scope.showComisionAerolinea = function (list, formName) {
                    $scope.formComisionesList = list;
                    $scope.frmFormComisionName = formName;
                }

                $scope.showComisionUpdate = function (formName, moneda) {
                    $scope.frmFormComisionName = formName;
                    $scope.frmFormComisionMoneda = moneda;
                    $scope.linkLoading = true;
                    $scope.showLinkRestfulError = false;
                    $scope.showLinkRestfulSuccess = false;

                    var urlComision = url.value + 'show-comision/' + $scope.formData.idPromotor + "/" + moneda;
                    return $http({
                        method: 'POST',
                        url: urlComision,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formComisionesList = response.data.content;
                            $scope.linkLoading = false;
                        } else {
                            $scope.showLinkRestfulMessage = response.data.content;
                            $scope.showLinkRestfulSuccess = true;
                            return {};
                        }
                    }, function (error) {
                        $scope.showLinkRestfulMessage = error;
                        $scope.showLinkRestfulSuccess = true;
                    });
                }

                $scope.formHasError = function () {
                    if ($scope.formData.idEmpresa === undefined) {
                        return true;
                    }
                    
                    return false ;
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

                    //$scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

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

                $scope.delete = function () {

                    if ($scope.modalConfirmation.method === 'delete-comision') {
                        $scope.deleteComision();
                    }
                };

                $scope.deleteComision = function () {
                    $scope.linkLoading = true;
                    var data = {
                        idComisionPromotor: $scope.modalConfirmation.id
                    }

                    return $http({
                        method: 'POST',
                        url: url.value + $scope.modalConfirmation.method,
                        data: {token: token.value, content: angular.toJson(data), formName: formName},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.showLinkRestfulSuccess = true;
                            $scope.showLinkRestfulMessage = response.data.content;

                            $scope.showComisionUpdate($scope.frmFormComisionName, $scope.frmFormComisionMoneda);
                        } else if (response.data.code == 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.linkLoading = false;
                    }, function (error) {
                        $scope.showLinkRestfulMessage = error;
                        $scope.showLinkRestfulError = true;
                        $scope.linkLoading = false;

                    });
                }

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

                    //$scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);
                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = {};
                    $scope.clickNuevo = true;
                    $scope.formData.tipo ='P' ;
                    $scope.myForm.$setPristine();
                    myForm.reset();
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

                $scope.getData(url.value, 'all');
                $scope.getData(urlEmpresa.value, 'all-combo');
                $scope.getData(urlAerolinea.value, 'all-combo/B');
                $scope.getData(urlAerolinea.value, 'all-combo/D');

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

