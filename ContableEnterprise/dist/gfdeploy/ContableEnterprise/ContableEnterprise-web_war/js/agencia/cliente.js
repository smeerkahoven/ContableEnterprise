/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var ALL = 'all';
var ALL_TIPO_COMBO = 'all-tipo';
var ALL_PROMOTORES_COMBO = 'all-promotor-combo';
var ALL_CLIENTE_COMBO = 'all-cliente-combo';
var DELETE_SOLICITADO = 'delete-solicitado';
var DELETE = "delete";
var SAVE_TIPO = 'save-tipo';
function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}
;

var app = angular.module("jsCliente", ['jsCliente.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsCliente.controllers', []).controller('frmCliente', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {

        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var urlPromotor = document.getElementsByName("hdUrlPromotor")[0];
        var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
        var formName = document.getElementsByName("hdFormName")[0];

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

        $scope.getData = function (method, url) {
            $scope.loading = true;

            return $http({
                method: 'POST',
                url: url + method,
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === ALL) {
                        $scope.mainGrid = response.data.content;
                        $scope.showTable = true;
                    }

                    if (method === ALL_TIPO_COMBO) {
                        $scope.comboGrupo = response.data.content;
                    }

                    if (method === ALL_PROMOTORES_COMBO) {
                        $scope.comboPromotor = response.data.content;
                    }

                    if (method === ALL_CLIENTE_COMBO) {
                        $scope.comboCorporativo = response.data.content;
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

        $scope.getComboCuentas = function (method, id) {
            $scope.loadingCuentas = true;
            return $http({
                method: 'POST',
                url: url.value + method + '/' + id,
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.comboCuentasBanco = response.data.content;

                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }
                $scope.loadingCuentas = false;
            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
                $scope.loadingCuentas = false;
            });
        }

        $scope.getSolicitados = function (id) {
            return $http({
                method: 'POST',
                url: url.value + 'get-solicitados/' + id,
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.formData.listSolicitadoPor = response.data.content;
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }
                $scope.loadingCuentas = false;
            }, function (error) {
                $scope.showRestfulMessage = error;
                $scope.showRestfulError = true;
                $scope.loadingCuentas = false;
            });
        }

        $scope.getDataEmpresa = function (method) {
            $scope.linkLoading = true;
            return $http({
                method: 'POST',
                url: urlEmpresa.value + 'all-combo',
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.comboEmpresa = response.data.content;
                } else {
                    $scope.showLinkRestfulMessage = response.data.content;
                    $scope.showLinkRestfulError = true;
                    return {};
                }
                $scope.linkLoading = false;
            }, function (error) {
                $scope.showLinkRestfulMessage = error;
                $scope.showLinkRestfulError = true;
            });
        }

        $scope.save = function () {
            if (!$scope.myForm.$valid) {
                $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos');
                return;
            }
            if ($scope.formHasError()) {
                $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos');
                return;
            }

            $scope.checkValues();

            $http({
                method: 'POST',
                url: url.value + 'save',
                data: {token: token.value, content: angular.toJson($scope.formData), formName: formName.value},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {

                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.getData(ALL, url.value);
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

        $scope.saveTipoCliente = function () {
            $scope.loadingTipoCliente = true;

            $http({
                method: 'POST',
                url: url.value + 'save-tipo/' + $scope.clienteSolicitadoNombre,
                data: {token: token.value, content: '', formName: formName.value},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.showRestfulMessageTipo = response.data.content;
                    $scope.showRestfulSuccessTipo = true;
                    $scope.getData(ALL_TIPO_COMBO, $scope.data);
                } else {
                    $scope.showRestfulMessageTipo = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = false;
                }
                $scope.loadingTipoCliente = false;

            }, function (error) {
                $scope.loadingTipoCliente = false;
                $scope.showRestfulMessageTipo = error.statusText;
                $scope.showRestfulErrortipo = true;
                $scope.showForm = false;
            });
        };

        $scope.checkValues = function () {

            if ($scope.formData.idClienteCorporativo != undefined) {
                if ($scope.formData.idClienteCorporativo.id) {
                    $scope.formData.idClienteCorporativo = $scope.formData.idClienteCorporativo.id;
                }
            }

            if ($scope.formData.idPromotor != undefined) {
                if ($scope.formData.idPromotor.id) {
                    $scope.formData.idPromotor = $scope.formData.idPromotor.id;
                }
            }

            if ($scope.formData.idClienteGrupo != undefined) {
                if ($scope.formData.idClienteGrupo.id) {
                    $scope.formData.idClienteGrupo = $scope.formData.idClienteGrupo.id;
                }
            }
        }

        $scope.formHasError = function () {
            return (
                    $scope.formData.idClienteGrupo == undefined);

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

            $scope.checkValues();

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

        $scope.delete = function (method) {

            $scope.loading = true;
            var data = {};

            $http({
                method: 'POST',
                url: url.value + $scope.methodEliminar + "/" + $scope.idEliminar,
                data: {token: token.value, content: angular.toJson(data), formName: formName.value},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {

                    if ($scope.methodEliminar === DELETE) {
                        $scope.getData( ALL, url.value);
                    }else if ($scope.methodEliminar === DELETE_SOLICITADO){
                        $scope.getSolicitados($scope.formData.idCliente);
                    }
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;
                    $scope.loading = false;
                } else {

                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.loading = false;
                }

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
            });
        };

        $scope.edit = function (row) {
            $scope.loading = true;
            $scope.showTable = false;
            $scope.showError = false;
            $scope.showForm = true;
            $scope.formData = row;
            $scope.loading = false;
            $scope.showBtnEditar = true;
            $scope.showBtnNuevo = false;
            $scope.showRestfulError = false;
            $scope.showRestfulSuccess = false;

            $scope.formData.idPromotor = $scope.findCta($scope.formData.idPromotor, $scope.comboPromotor);
            $scope.formData.idClienteGrupo = $scope.findCta($scope.formData.idClienteGrupo, $scope.comboGrupo);
            $scope.formData.idClienteCorporativo = $scope.findCta($scope.formData.idClienteCorporativo, $scope.comboCorporativo);

            $scope.getSolicitados($scope.formData.idCliente);

        }

        $scope.updateAddSolicitadoPor = function () {

            if ($scope.formData.solicitadoPor == undefined) {
                $scope.showErrorSolicitadoPor = true;
                return;
            }

            if ($scope.formData.solicitadoPor.length < 5) {
                $scope.showErrorSolicitadoPor = true;
                return;
            }

            $scope.loadingAddSolicitadoPor = true;
            $scope.showErrorSolicitadoPor = false;

            var data = {idCliente: $scope.formData.idCliente, nombre: $scope.formData.solicitadoPor}
            $http({
                method: 'POST',
                url: url.value + 'save-solicitado',
                data: {token: token.value, content: angular.toJson(data), formName: formName.value},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.getSolicitados($scope.formData.idCliente);
                    $scope.formData.solicitadoPor = '';
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                    $scope.showForm = false;
                }
                $scope.loadingAddSolicitadoPor = false;

            }, function (error) {
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
            });
        };

        $scope.addSolicitadoPor = function () {
            if ($scope.formData.solicitadoPor === undefined) {
                $scope.showErrorSolicitadoPor = true;
                return;
            }

            if ($scope.formData.solicitadoPor.length < 4) {
                $scope.showErrorOtrosImpuestos = true;
                return;
            }

            if ($scope.formData.listSolicitadoPor === undefined) {
                $scope.formData.listSolicitadoPor = [];
            }
            $scope.formData.listSolicitadoPor.push({nombre: $scope.formData.solicitadoPor});

            $scope.formData.solicitadoPor = '';
            $scope.showErrorOtrosImpuestos = false;
        }

        $scope.nuevo = function () {
            $scope.showForm = true;
            $scope.showBtnNuevo = true;
            $scope.showBtnEditar = false;
            $scope.showTable = false;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
            $scope.hideMessagesBox();
            $scope.formData = { estado : 'A'};

        }

        $scope.hideMessagesBox = function () {
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
        }

        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
            $scope.hideMessagesBox();
        }

        $scope.modalEliminar = function (id, nombre, method) {
            $scope.idEliminar = id;
            $scope.nombreEliminar = nombre;
            $scope.methodEliminar = method;
        }

        $scope.findCta = function (cta, input) {
            var i = 0;
            if (input) {
                for (i; i < input.length; i++) {
                    if (input[i].id === cta) {
                        //console.log(input[i]);
                        return input[i];
                    }
                }
            }
        }

        $scope.showAlert = function (title, message) {
            swal({
                title: title,
                text: message,
                type: 'error',
                closeOnConfirm: true
            });
        }

        $scope.getData(ALL, url.value);
        $scope.getData(ALL_TIPO_COMBO, url.value);
        $scope.getData(ALL_PROMOTORES_COMBO, urlPromotor.value);
        $scope.getData(ALL_CLIENTE_COMBO, url.value);
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

