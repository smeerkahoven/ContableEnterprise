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

function showModal(idModal) {
    $(`#${idModal}`).modal();
}

var RECUPERADO = "R";
var APROBADO = "A";
var PENDIENTE = "P";
var ANULADO = "N";
var INICIAL = "I";
let ZERO = "0";
let SI = "S";
let NO = "N";

function AsientoContable() {
    this.position = 0;
    this.idAsiento = '0';
    this.gestion = '0';

    this.idPlanCuenta = '';
    this.idLibro = '0';
    this.fechaMovimiento = '';
    this.debeMonNac = 0.00;
    this.haberMonNac = 0.00;
    this.debeMonExt = 0.00;
    this.haberMonExt = 0.00;
    this.estado = 'I';
    this.moneda = '';
    this.editable = true;
    this.updated = false;
    this.action = '';
    this.isOK = false;

}

function Comprobante() {
    this.idLibro = '0';
    this.idUsuarioCreador = '';
    this.idUsuarioAnulado = '';
    this.idNumeroGestion = '0';
    this.gestion = '0';
    this.fecha = '';
    this.nombre = '';
    this.estado = INICIAL; // 
    this.moneda = 'B';
    this.factorCambiario = '0';
    this.factorMin = 0;
    this.factorMax = 0;
    this.tipo = '';
    this.transacciones = [];
    this.totalDebeMonNac = 0;
    this.totalHaberMonNac = 0;
    this.totalDebeMonExt = 0;
    this.totalHaberMonExt = 0;
    this.difMonNac = 0;
    this.difMonExt = 0;
    this.conErrores = SI;
}

AsientoContable.prototype = Object.create(AsientoContable.prototype);
AsientoContable.prototype.constructor = AsientoContable;

Comprobante.prototype = Object.create(Comprobante.prototype);
Comprobante.prototype = Comprobante;

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString();
let today = new Date().toLocaleDateString();

let ALL_TIPO_COMPROBANTES = 'all-tipo-comprobantes';
let ALL_PLAN_CUENTAS = "combo/";
let DOLLAR_TODAY = 'dollar/today';
let UFV_TODAY = 'ufv/today';


let METHOD_ANULAR = 'anular';
let METHOD_PENDIENTE = 'pendiente';

let ACTION_CREATE = 'c';
let ACTION_UPDATE = 'u';
let ACTION_DELETE = 'd';

let MONEDA_NACIONAL = "B";
let MONEDA_EXTRANJERA = 'U';
let ELIMINAR_ASIENTO = 'eliminar-asiento';
let ELIMINAR_ASIENTO_UPDATE = 'eliminar-transaccion';

let app = angular.module("jsComprobantes", ['jsComprobantes.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsComprobantes.controllers', []).controller('frmComprobantes',
        ['$scope', '$http', '$uibModal', '$window',
            function ($scope, $http, $window) {
                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlPlanCuentas = document.getElementsByName("hdUrlPlanCuentas")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var idEmpresa = document.getElementById("idEmpresa");
                var factorMaxMin = document.getElementById("hdFactorMaxMin");
                var username = document.getElementById("hdUserName");

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;
                $scope.disableAddButton = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;
                $scope.dollarToday = 0;

                $scope.search = {tipo: 'AD', estado: APROBADO, fechaInicio: firstDay, fechaFin: today};

                $scope.getData = function (urls, method) {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: urls + method,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {

                            if (method === ALL_TIPO_COMPROBANTES) {
                                $scope.comboTiposComprobantes = response.data.content;
                            }

                            if (method === DOLLAR_TODAY) {
                                $scope.dollarToday = response.data.content;
                            }

                            if (method === UFV_TODAY) {
                                $scope.ufvToday = response.data.content;
                                console.log($scope.ufvToday);
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

                $scope.find = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}all`,
                        data: {token: token.value, content: $scope.search},
                        headers: {'Content-Type': 'application/json'}
                        //`${url.value}all?tipo=${$scope.search.tipo}&estado=${$scope.search.estado}&fechaI=${$scope.search.fechaInicio}&fechaF=${$scope.search.fechaFin}`
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.loading = false;
                            $scope.hideMessagesBox();
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

                $scope.edit = function (row) {
                    $scope.loading = true;
                    return $http.get(`${url.value}${row.idLibro}`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData = response.data.content;
                            console.log($scope.formData);
                            $scope.loading = false;
                            $scope.showTable = false;
                            $scope.showForm = true;
                            $scope.formData.factorUfv = $scope.ufvToday.valor;
                            $scope.hideMessagesBox();
                            if ($scope.formData.transacciones !== undefined) {
                                for (var i = 0; i < $scope.formData.transacciones.length; i++) {
                                    $scope.formData.transacciones[i].idPlanCuenta = $scope.findCta($scope.formData.transacciones[i].idPlanCuenta.id, $scope.comboPlanCuentas);
                                }
                            }
                            //$scope.sumarTotales();
                            $scope.sumarDiferencias()
                            $scope.habilitarBotones();
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


                $scope.habilitarBotones = function () {
                    switch ($scope.formData.estado) {
                        case ANULADO :
                            $scope.disableAnularButton = true;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = true;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = true;
                            $scope.disableGuardarTransaccion = true;
                            $scope.disableEliminarTransaccion = true;
                            break;
                        case APROBADO :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = true;
                            $scope.disableImprimirButton = false;
                            $scope.disableEditarTransaccion = true;
                            $scope.disableEliminarTransaccion = true;
                            $scope.disableGuardarTransaccion = true;
                            break;
                        case PENDIENTE :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = true;
                            $scope.disableAddButton = false;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = false;
                            $scope.disableEliminarTransaccion = false;
                            $scope.disableGuardarTransaccion = false;
                            if ($scope.existenTransaccionesInvalidas()) {
                                $scope.disableGuardarButton = true;
                            } else {
                                $scope.disableGuardarButton = false;
                            }
                            break;
                        case INICIAL :
                            $scope.disableAnularButton = false;
                            $scope.disableGuardarButton = true;
                            $scope.disablePendienteButton = false;
                            $scope.disableAddButton = false;
                            $scope.disableImprimirButton = true;
                            $scope.disableEditarTransaccion = false;
                            $scope.disableEliminarTransaccion = false;
                            $scope.disableGuardarTransaccion = false;
                            break;

                    }
                }

                $scope.anular = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}${METHOD_ANULAR}`,
                        data: {token: token.value, content: angular.toJson($scope.modalAnularData.row)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.modalAnularData.row.estado = ANULADO;
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.loading = false;
                            $scope.showForm = false;
                            $scope.showTable = true;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.imprimir = function (data) {
                    window.open(`../../ComprobanteReporteServlet?idLibro=${data.idLibro}`, '_target');
                }

                $scope.pendiente = function () {
                    //$("frmPendiente").modal() ;
                    console.log($scope.myForm);
                    if (!$scope.myForm.$valid)
                        return;
                    showModal("frmPendiente");
                }

                $scope.colocarPendiente = function () {
                    if ($scope.formData.estado === INICIAL) {
                        $scope.save(PENDIENTE);
                    } else if ($scope.formData.estado === ANULADO) {
                        $scope.loading = true;
                        return $http({
                            method: 'POST',
                            url: `${url.value}${METHOD_PENDIENTE}`,
                            data: {token: token.value, content: angular.toJson($scope.modalAnularData.row)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            if (response.data.code === 201) {
                                $scope.modalAnularData.row.estado = PENDIENTE;
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulSuccess = true;
                                $scope.loading = false;
                                $scope.showForm = false;
                                $scope.showTable = true;
                            } else {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulError = true;
                            }
                        }, $scope.showErrorFunction());
                    } else if ($scope.formData.estado === PENDIENTE) {

                    }
                }

                $scope.newComprobante = function (tipo) {
                    $scope.formData = new Comprobante();
                    $scope.formData.factorCambiario = $scope.dollarToday.valor;
                    $scope.formData.factorUfv = $scope.ufvToday.valor;
                    $scope.formData.fecha = $scope.dollarToday.fecha;
                    $scope.formData.tipo = tipo;
                    $scope.formData.idUsuarioCreador = username.value;
                    $scope.formData.idEmpresa = idEmpresa.value;

                    $scope.nuevo();
                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.disableAddButton = false;
                    $scope.hideMessagesBox();

                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);

                    $scope.habilitarBotones();

                    $scope.myForm.$setPristine();
                    myForm.reset();
                }

                $scope.existenTransaccionesInvalidas = function () {

                    if ($scope.formData.transacciones === undefined) {
                        return true;
                    }

                    //si no hay transacciones
                    if ($scope.formData.transacciones.length === 0) {
                        return true;
                    }

                    //si existen transacciones
                    var disable = false;
                    if ($scope.formData.transacciones.length > 0) {
                        for (var i in $scope.formData.transacciones) {
                            if ($scope.formData.transacciones[i].idPlanCuenta === undefined) {
                                return true;
                            }

                            if ($scope.formData.transacciones[i].moneda === undefined) {
                                return true;
                            }
                            /* if (!$scope.formData.transacciones[i].isOK) {
                             retur*n true;
                             }*/
                        }
                    }

                    //existen diferencias
                    console.log(`existen diferencias:${$scope.existenDiferencias()} `);
                    disable = $scope.existenDiferencias();

                    return disable;
                }

                $scope.existenDiferencias = function () {
                    console.log($scope.formData.difMonNac);
                    console.log($scope.formData.difMonExt);
                    if ($scope.formData.difMonNac != 0 && $scope.formData.difMonExt != 0) {
                        $scope.formData.conErrores = true;
                        return true;
                    }
                    return false;
                }

                $scope.obtenerPlanCuentas = function () {
                    return $http({
                        method: 'POST',
                        url: urlPlanCuentas.value + ALL_PLAN_CUENTAS + idEmpresa.value,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboPlanCuentas = response.data.content;
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

                $scope.save = function (estado) {
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
                        //$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formData.estado === INICIAL) {
                        $scope.loading = true;
                        if (estado === PENDIENTE)
                            $scope.formData.conErrores = SI;
                        else
                            $scope.formData.conErrores = NO;

                        $scope.formData.estado = estado;
                        console.log($scope.formData);
                        $http({
                            method: 'POST',
                            url: url.value + 'save',
                            //data: {token: token.value, content: angular.toJson($scope.formData)},
                            data: angular.toJson($scope.formData),
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            if (response.data.code === 201) {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulSuccess = true;
                                $scope.formData = response.data.entidad;
                                $scope.disableGuardarButton = true;
                                $scope.disablePendienteButton = false;
                                $scope.disableAnularButton = false;
                                $scope.habilitarBotones();
                                if (estado === APROBADO) {
                                    $scope.imprimir($scope.formData);
                                }
                                //$scope.showForm = false;
                                //$scope.showTable = true;
                                //$scope.getData(url.value, 'all');
                            } else {
                                $scope.showRestfulMessage = response.data.content;
                                $scope.showRestfulError = true;
                                //$scope.showForm = false;
                            }
                            $scope.loading = false;

                        }, function (error) {
                            $scope.loading = false;
                            $scope.showRestfulMessage = error.statusText;
                            $scope.showRestfulError = true;
                            //$scope.showForm = false;
                        });
                    } else {
                        $scope.update();
                    }
                };

                $scope.formHasError = function () {
                    /*if ($scope.formData.idEmpresa == undefined) {
                     return true;
                     }
                     return (!$scope.formData.idEmpresa.id);
                     */
                    return true;
                }

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: `${url.value}update`,
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
                    if ($scope.modalConfirmation.method === ELIMINAR_ASIENTO) {
                        $scope.eliminarAsiento();
                    }
                };

                $scope.eliminarAsientoFromTable = function () {
                    for (var i = 0; i <= $scope.formData.transacciones.length - 1; i++) {
                        if ($scope.formData.transacciones[i] === $scope.modalConfirmation.row) {
                            $scope.formData.transacciones.splice(i, 1);
                            $scope.sumarTotales();
                            $scope.sumarDiferencias();
                            break;
                        }
                    }
                }

                $scope.eliminarAsiento = function () {
                    if ($scope.modalConfirmation.row.idAsiento === ZERO) {
                        $scope.eliminarAsientoFromTable();
                    } else {
                        $http({
                            method: 'POST',
                            url: `${url.value}delete-transaction/${$scope.modalConfirmation.row.idAsiento}`,
                            data: {token: token.value, content: angular.toJson($scope.formData)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            $scope.sumarTotales();
                            $scope.sumarDiferencias();
                            $scope.eliminarAsientoFromTable();
                            if ($scope.existenTransaccionesInvalidas()) {
                                $scope.disableGuardarButton = true;
                            } else {
                                $scope.disableGuardarButton = false;
                            }
                        }, $scope.showErrorFunction);
                    }
                }

                $scope.showErrorFunction = function (error) {
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error;
                }

                $scope.hideMessagesBox = function () {
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                }

                $scope.editTransaccion = function (item) {
                    $scope.showTable = false;
                    $scope.showForm = true;
                    $scope.hideMessagesBox();
                    item.editable = true;
                    item.superDisabled = true;
                    $scope.disableAddButton = true;
                    console.log(item);
                }

                $scope.saveTransaccion = function (item) {
                    $scope.showRowError = false;
                    if (item.idPlanCuenta.id === undefined) {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe ingresar una Cuenta Valida.";
                        return false;
                    }

                    if (item.moneda === '') {
                        $scope.showRowError = true;
                        $scope.showRowMessage = "Debe seleccionar un tipo de moneda.";
                        return false;
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        if (item.debeMonNac === undefined || item.haberMonNac === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }

                        if (item.debeMonNac === 0 && item.haberMonNac === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda nacional";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_EXTRANJERA) {
                        if (item.debeMonExt === undefined || item.haberMonExt === undefined) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }

                        if (item.debeMonExt === 0 && item.haberMonExt === 0) {
                            $scope.showRowError = true;
                            $scope.showRowMessage = "Ingrese valores a los montos de Moneda Extranjera";
                            return false;
                        }
                    }

                    if (item.moneda === MONEDA_NACIONAL) {
                        item.debeMonNac = parseFloat(Math.round(item.debeMonNac * 100) / 100).toFixed(2);
                        item.haberMonNac = parseFloat(Math.round(item.haberMonNac * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonExt === undefined || item.debeMonExt <= 0) {
                            item.debeMonExt = parseFloat(item.debeMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                            //if (item.haberMonExt === undefined || item.haberMonExt <= 0) {
                            item.haberMonExt = parseFloat(item.haberMonNac / $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }

                    } else if (item.moneda === MONEDA_EXTRANJERA) {
                        item.debeMonExt = parseFloat(Math.round(item.debeMonExt * 100) / 100).toFixed(2);
                        item.haberMonExt = parseFloat(Math.round(item.haberMonExt * 100) / 100).toFixed(2);

                        if (item.idPlanCuenta.comodin === "N") {
                            //if (item.debeMonNac <= 0) {
                            item.debeMonNac = parseFloat(item.debeMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}

                            //if (item.haberMonNac <= 0) {
                            item.haberMonNac = parseFloat(item.haberMonExt * $scope.formData.factorCambiario).toFixed(2);
                            //}
                        }
                    }

                    $scope.sumarTotales();
                    $scope.sumarDiferencias();
                    item.editable = false;
                    item.action = ACTION_UPDATE;
                    item.isOK = true;
                    $scope.disableAddButton = false;

                    console.log($scope.existenTransaccionesInvalidas());
                    if ($scope.existenTransaccionesInvalidas()) {
                        $scope.disableGuardarButton = true;
                    } else {
                        $scope.disableGuardarButton = false;
                    }

                    if ($scope.formData.estado === PENDIENTE) {
                        console.log(item);
                        if (item.idAsiento === ZERO) {
                            $http({
                                method: 'POST',
                                url: `${url.value}add-transaction`,
                                data: {token: token.value, content: angular.toJson($scope.formData)},
                                headers: {'Content-Type': 'application/json'}
                            }).then(function (response) {
                                console.log(response);
                                item = response.data.entidad;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idAsiento = item.idAsiento;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].idLibro = item.idLibro;
                                $scope.formData.transacciones[$scope.formData.transacciones.length - 1].gestion = item.gestion;
                                console.log($scope.formData);
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error;
                            })
                        } else {
                            $http({
                                method: 'POST',
                                url: `${url.value}update-transaction`,
                                data: {token: token.value, content: angular.toJson($scope.formData)},
                                headers: {'Content-Type': 'application/json'}
                            }).then(function (response) {

                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error;
                            })
                        }
                    }
                }

                $scope.sumarTotales = function () {
                    $scope.formData.totalDebeMonNac = 0;
                    $scope.formData.totalHaberMonNac = 0;
                    $scope.formData.totalHaberMonExt = 0;
                    $scope.formData.totalDebeMonExt = 0;
                    for (var i in $scope.formData.transacciones) {
                        $scope.formData.totalDebeMonNac = parseFloat((parseFloat($scope.formData.totalDebeMonNac)) + (parseFloat($scope.formData.transacciones[i].debeMonNac))).toFixed(2);
                        $scope.formData.totalHaberMonNac = parseFloat((parseFloat($scope.formData.totalHaberMonNac)) + (parseFloat($scope.formData.transacciones[i].haberMonNac))).toFixed(2);
                        $scope.formData.totalDebeMonExt = parseFloat((parseFloat($scope.formData.totalDebeMonExt)) + (parseFloat($scope.formData.transacciones[i].debeMonExt))).toFixed(2);
                        $scope.formData.totalHaberMonExt = parseFloat((parseFloat($scope.formData.totalHaberMonExt)) + (parseFloat($scope.formData.transacciones[i].haberMonExt))).toFixed(2);
                    }
                }

                $scope.sumarDiferencias= function() {
                    $scope.formData.difMonNac = parseFloat(Number($scope.formData.totalDebeMonNac) - Number($scope.formData.totalHaberMonNac)).toFixed(2);
                    $scope.formData.difMonExt = parseFloat(Number($scope.formData.totalDebeMonExt) - Number($scope.formData.totalHaberMonExt)).toFixed(2);
                    console.log($scope.formData.difMonNac);
                    console.log($scope.formData.difMonExt);

                    $scope.showDifHaberNac = ($scope.formData.difMonNac > 0);
                    $scope.showDifDebeNac = ($scope.formData.difMonNac < 0);

                    $scope.showDifHaberExt = ($scope.formData.difMonExt > 0);
                    $scope.showDifDebeExt = ($scope.formData.difMonExt < 0);
                }

                $scope.ngChange = function (key, method, row) {
                    // TAB
                    if (key.keyCode != 9) {
                        row.haberMonNac = (method == 'debe') ? 0 : row.haberMonNac;
                        row.haberMonExt = (method == 'debe') ? 0 : row.haberMonExt;

                        row.debeMonNac = (method == 'haber') ? 0 : row.debeMonNac;
                        row.debeMonExt = (method == 'haber') ? 0 : row.debeMonExt;
                    }
                }

                $scope.addTransaccion = function () {
                    var newAsiento = new AsientoContable;
                    newAsiento.action = ACTION_CREATE;
                    newAsiento.position = $scope.formData.transacciones.length;
                    $scope.formData.transacciones.push(newAsiento);
                    $scope.disableAddButton = true;
                    $scope.disableGuardarButton = true;
                }

                $scope.onChangeMoneda = function (row) {
                    row.debeMonNac = 0.00;
                    row.haberMonNac = 0.00;
                    row.debeMonExt = 0.00;
                    row.haberMonExt = 0.00;
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

                $scope.showConfirmation = function (title, message, method) {
                    swal({
                        title: title,
                        text: message,
                        type: 'info',
                        buttons: {},
                    }).then(method);
                }

                $scope.modalEliminarAsiento = function (row) {
                    $scope.modalConfirmation = {id: row.position, nombre: '', method: ELIMINAR_ASIENTO, row: row};
                }

                $scope.modalAnular = function (row) {
                    $scope.modalAnularData = {id: row.position, nombre: '', method: METHOD_ANULAR, row: row};
                }

                $scope.findCta = function (cta, input) {
                    var i = 0;
                    if (input === undefined)
                        return;
                    for (i; i < input.length; i++) {
                        if (input[i].id == cta) {
                            return input[i];
                        }
                    }
                }

                $scope.getData(url.value, ALL_TIPO_COMPROBANTES);
                $scope.getData(urlFactores.value, DOLLAR_TODAY);
                $scope.getData(urlFactores.value, UFV_TODAY);
                $scope.obtenerPlanCuentas();

                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.factorCambiario', function (now, old) {
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

app.filter('makePositive', function () {
    return function (num) {
        return Math.abs(num);
    }
});

app.filter('tipoComprobante', function () {
    return function (tipo, scope) {
        let object = scope.findCta(tipo, scope.comboTiposComprobantes);
        if (object !== undefined) {
            return object.name;
        }
    }
});

app.filter('planCuenta', function () {
    return function (tipo, scope) {
        let object = scope.findCta(tipo, scope.comboPlanCuentas);
        if (object !== undefined) {
            return object;
        }
    }
});

app.filter('checkStatus', function () {
    return function (estado) {
        switch (estado) {
            case 'I':
                return "INICIAL";
            case 'N' :
                return "ANULADO";
            case 'P' :
                return "PENDIENTE";
            case 'A' :
                return "APROBADO";
            case 'R' :
                return "RECUPERADO";
            default :
                return '';
        }
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


app.directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value);
            });
        }
    };
});
