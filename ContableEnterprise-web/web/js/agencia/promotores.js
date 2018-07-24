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

var app = angular.module("jsAerolinea", ['jsAerolinea.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsAerolinea.controllers', []).controller('frmAerolinea',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {
                //var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTUyNzg0MjcwNCwiaWF0IjoxNTI3Nzk5NTA0LCJ2YWx1ZSI6ImFkbWluIn0.20_NgML0xEVr3W9R7RuZOxqno1cLk7cMq5bOtNDLZGQ";//document.getElementsByName("hdToken")[0];
                //var url = "http://localhost:8080/ContableEnterprise-ws/ws-api/roles/form-perm" //document.getElementsByName("hdUrl")[0];

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlPlan = document.getElementsByName("hdUrlPlanCuentas")[0];
                var urlCuentaVentas = document.getElementsByName("hdComboPlanVentas")[0];
                var urlCuentaComisiones = document.getElementsByName("hdComboPlanComisiones")[0];
                var urlCuentaDevoluciones = document.getElementsByName("hdComboPlanDevoluciones")[0];
                var myForm = document.getElementsByName("myForm")[0];

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

                            if (method === 'combo') {
                                $scope.comboCuentasLineaAerea = response.data.content;
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

                $scope.getImpuestos = function () {
                    var data = {idAerolinea: $scope.formData.idAerolinea};

                    return $http({
                        method: 'POST',
                        url: url.value + 'all-impuestos',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.aerolineaImpuestoList = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.getPlanCuentas = function (url, method) {
                    return $http({
                        method: 'POST',
                        url: urlPlan.value + url,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            if (method === 'ventas') {
                                $scope.comboVentas = response.data.content;
                            }

                            if (method === 'comisiones') {
                                $scope.comboComisiones = response.data.content;
                            }

                            if (method === 'devoluciones') {
                                $scope.comboDevoluciones = response.data.content;
                            }

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
                    if (!$scope.myForm.$valid) {
                       swal("hola  que tal");
                        return;
                    }
                    if ($scope.formHasError()) {
                        swal("hola  que tal");
                        return;
                    }

                    $scope.loading = true;
                    $scope.formData.ctaVentasMonExt = $scope.formData.ctaVentasMonExt.id;
                    $scope.formData.ctaVentasMonNac = $scope.formData.ctaVentasMonNac.id;
                    $scope.formData.ctaComisionMonExt = $scope.formData.ctaComisionMonExt.id;
                    $scope.formData.ctaComisionMonNac = $scope.formData.ctaComisionMonNac.id;
                    $scope.formData.ctaDevolucionMonExt = $scope.formData.ctaDevolucionMonExt.id;
                    $scope.formData.ctaDevolucionMonNac = $scope.formData.ctaDevolucionMonNac.id;

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

                $scope.updateAddImpuesto = function () {

                    if ($scope.formData.otrosImpuestos == undefined) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    if ($scope.formData.otrosImpuestos.length < 5) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    $scope.showErrorOtrosImpuestos = false;


                    var data = {idAerolinea: $scope.formData.idAerolinea, nombre: $scope.formData.otrosImpuestos}
                    $http({
                        method: 'POST',
                        url: url.value + 'update-impuestos-insert',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.getImpuestos();
                            $scope.formData.otrosImpuestos = '';
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }

                    }, function (error) {
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                    });
                };

                $scope.addImpuesto = function () {
                    if ($scope.formData.otrosImpuestos == undefined) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    if ($scope.formData.otrosImpuestos.length < 5) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    if ($scope.formData.aerolineaImpuestoList == undefined) {
                        $scope.formData.aerolineaImpuestoList = [];
                    }
                    $scope.formData.aerolineaImpuestoList.push({nombre: $scope.formData.otrosImpuestos});

                    $scope.formData.otrosImpuestos = '';
                    $scope.showErrorOtrosImpuestos = false;
                }

                $scope.formHasError = function () {
                    return ($scope.showErrorCtaComisionMonExt ||
                            $scope.showErrorCtaComisionMonNac ||
                            $scope.showErrorCtaDevolucionMonExt ||
                            $scope.showErrorCtaDevolucionMonNac ||
                            $scope.showErrorCtaVentasMonExt ||
                            $scope.showErrorCtaVentasMonNac);

                }
                
                $scope.showAlert = function (title,message) {
                    swal({
                        title : title,
                        text : message,
                        type : 'error',
                        closeOnConfirm : true
                    }) ;
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

                    $scope.formData.ctaVentasMonExt = $scope.formData.ctaVentasMonExt.id;
                    $scope.formData.ctaVentasMonNac = $scope.formData.ctaVentasMonNac.id;
                    $scope.formData.ctaComisionMonExt = $scope.formData.ctaComisionMonExt.id;
                    $scope.formData.ctaComisionMonNac = $scope.formData.ctaComisionMonNac.id;
                    $scope.formData.ctaDevolucionMonExt = $scope.formData.ctaDevolucionMonExt.id;
                    $scope.formData.ctaDevolucionMonNac = $scope.formData.ctaDevolucionMonNac.id;

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
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        //$scope.showForm = true;
                    });
                };

                $scope.delete = function (method) {
                    $scope.loading = true;
                    var data = {idAerolineaImpuesto: $scope.modalConfirmation.id};
                    $http({
                        method: 'POST',
                        url: url.value + method + 'delete',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.getImpuestos();
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

                $scope.edit = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.showBtnNuevo = false;
                    $scope.showBtnActualizar = true;

                    $scope.formData = item;
                    $scope.formData.ctaVentasMonNac = $scope.findCta(item.ctaVentasMonNac, $scope.comboVentas);
                    $scope.formData.ctaVentasMonExt = $scope.findCta(item.ctaVentasMonExt, $scope.comboVentas);
                    $scope.formData.ctaComisionMonNac = $scope.findCta(item.ctaComisionMonNac, $scope.comboComisiones);
                    $scope.formData.ctaComisionMonExt = $scope.findCta(item.ctaComisionMonExt, $scope.comboComisiones);
                    $scope.formData.ctaDevolucionMonExt = $scope.findCta(item.ctaDevolucionMonExt, $scope.comboDevoluciones);
                    $scope.formData.ctaDevolucionMonNac = $scope.findCta(item.ctaDevolucionMonNac, $scope.comboDevoluciones);
                    console.log($scope.formData);

                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = {};
                    console.log($scope.formData);
                }

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
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


                $scope.modalEliminar = function (idx, nombrex, methodx) {

                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
                    console.log($scope.modalConfirmation);
                }

                $scope.getData('all', $scope.data);

                $scope.getPlanCuentas(urlCuentaVentas.value, 'ventas');
                $scope.getPlanCuentas(urlCuentaComisiones.value, 'comisiones');
                $scope.getPlanCuentas(urlCuentaDevoluciones.value, 'devoluciones');

                $scope.$watch('formData.ctaVentasMonNac.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaVentasMonNac = true;
                    } else {
                        $scope.showErrorCtaVentasMonNac = false;
                    }
                });

                $scope.$watch('formData.ctaVentasMonExt.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaVentasMonExt = true;
                    } else {
                        $scope.showErrorCtaVentasMonExt = false;
                    }
                });

                $scope.$watch('formData.ctaComisionMonNac.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaComisionMonNac = true;
                    } else {
                        $scope.showErrorCtaComisionMonNac = false;
                    }
                });

                $scope.$watch('formData.ctaComisionMonExt.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaComisionMonExt = true;
                    } else {
                        $scope.showErrorCtaComisionMonExt = false;
                    }
                });

                $scope.$watch('formData.ctaDevolucionMonNac.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaDevolucionMonNac = true;
                    } else {
                        $scope.showErrorCtaDevolucionMonNac = false;
                    }
                });

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

