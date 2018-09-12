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

let DOLAR = 'D';
let BOLIVIANO = 'B';
let NETO = 'N';
let TOTAL = 'T';

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString();
let today = new Date().toLocaleDateString();

function Boleto() {
    this.idBoleto = 0;
    this.idAerolinea = null;
    this.idEmpresa = '';
    this.emision = null;
    this.tipoBoleto = null;
    this.tipoCupon = null;
    this.numero = null;
    this.idRuta1 = null;
    this.idRuta2 = null;
    this.idRuta3 = null;
    this.idRuta4 = null;
    this.idRuta5 = null;
    this.idCliente = null;
    this.nombrePasajero = null;
    this.fechaEmision = null;
    this.fechaViaje = null;
    this.factorCambiario = null;
    this.factorMin = null;
    this.factorMax = null;

    this.idPromotor = null;
    this.idLibro = null;
    this.estado = null;

    this.idNotaDebito = null;
    this.idUsuarioCreador = null;

    this.importeNetoBs = null;
    this.importeNetoUsd = null;
    this.impuestoBobBs = null;
    this.impuestoBobUsd = null;
    this.impuestoQmBs = null;
    this.impuestoQmUsd = null;
    this.impuesto1 = null;
    this.impuesto2 = null;
    this.impuesto3 = null;
    this.impuesto4 = null;
    this.impuesto5 = null;
    this.totalBoletoBs = null;
    this.totalBoletoUsd = null;
    this.comision = null;
    this.comisionBs = null;
    this.comisionUsd = null;
    this.montoFee = null;
    this.montoFeeBs = null;
    this.montoFeeUsd = null;
    this.montoDescuento = null;
    this.montoDescuentoBs = null;
    this.montoDescuentoUsd = null;
    this.totalCancelBs = null;
    this.totalCancelUsd = null;

    this.formaPago = null; // Contado, Credito, Tarjeta, Combinado

    this.creditoDias = null;
    this.creditoVencimiento = null;

    this.contadoNroCheque = null;
    this.contadoIdBanco = null;
    this.contadoNroDeposito = null;
    this.contadoTipo = null; // Efectivo, Cheque, Deposito

    this.tarjetaNumero = null;
    this.tarjetaId = null;

    this.combinadoTipo = null; // Contado, Credito, Tarjeta

    this.combinadoCreditoDias = null;
    this.combinadoCreditoVencimiento = null;
    this.combinadoCreditoMonto = null;

    this.combinadoContadoTipo = null; // Efectivo, Cheque, Deposito
    this.combinadoContadoMonto = null;
    this.combinadoContadoNroCheque = null;
    this.combinadoContadoIdBanco = null;
    this.combinadoContadoNroDeposito = null;

    //interfaz
    this.creditoPlazoMaximo = null;
}

Boleto.prototype = Object.create(Boleto.prototype);
Boleto.prototype.constructor = Boleto;



var app = angular.module("jsBoletos", ['jsBoletos.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBoletos.controllers', []).controller('frmBoletos',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var urlAeropuertos = document.getElementsByName("hdUrlAeropuertos")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlTarjeta = document.getElementsByName("hdUrlTarjetas")[0];
                var urlBancos = document.getElementsByName("hdUrlBancos")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var factorMaxMin = document.getElementsByName("hdFactorMaxMin")[0];
                var porcentaje = document.getElementsByName("hdPorcentaje")[0].value;
                var idEmpresa = document.getElementsByName("idEmpresa")[0].value;

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

                /*
                 * Obtencion de Datos
                 */

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

                $scope.getLineaAerea = function () {
                    return $http({
                        url: `${urlAerolinea.value}combo`,
                        method: 'POST',
                        data: {token: token.value},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboAerolineas = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getFactorCambiario = function () {
                    return $http({
                        url: `${urlFactores.value}dollar/today`,
                        method: 'POST',
                        data: {token: token.value},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.dollarToday = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getTipoEmision = function () {
                    return $http.get(`${url.value}tipo-emision`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboEmision = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getAeropuertos = function () {
                    return $http.get(`${urlAeropuertos.value}combo`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboAeropuerto = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getClientes = function () {
                    return $http({
                        url: `${urlClientes.value}all-cliente-combo`,
                        method: 'POST',
                        data: {token: token.value},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboCliente = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getImpuestos = function (aerolinea) {
                    var data = {idAerolinea: aerolinea.idAerolinea};
                    return $http({
                        url: `${urlAerolinea.value}all-impuestos`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.aerolineaImpuestos = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error;
                    });
                }

                $scope.getAllBancos = function () {
                    return $http.get(`${urlBancos.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboAllBancos = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error;
                            });
                }
                
                $scope.getAllBancosCuentas = function () {
                    return $http.get(`${urlBancos.value}getBancosCuentas/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboBancosConCuenta = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error;
                            });
                }

                $scope.getTarjetas = function () {
                    return $http.get(`${urlTarjeta.value}combo/all`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboTarjetas = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error;
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

                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

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

                /*
                 * Transformaciones
                 */

                $scope.transformarCalculateToBs = function () {

                    $scope.transformarImporteNetoBs();
                    $scope.transformarImpuestoBobToBs();
                    $scope.transformarImpuestoQmToBs();
                    $scope.transformarComisionToBs();
                    $scope.transformarMontoFeeToBs();
                    $scope.transformarDescuentoToBs();
                    $scope.transformarListaImpuestosToBs();

                    $scope.transformarTotalBoletoToBs();
                    $scope.transformarTotalCancelToBs();

                }

                $scope.transformarCalculateToUsd = function () {

                    $scope.transformarImporteNetoUsd();
                    $scope.transformarImpuestoBobToUsd();
                    $scope.transformarImpuestoQmToUsd();
                    $scope.transformarComisionToUsd();
                    $scope.transformarMontoFeeToUsd();
                    $scope.transformarDescuentoToUsd();
                    $scope.transformarListaImpuestosToUsd();

                    $scope.transformarTotalBoletoToUsd();
                    $scope.transformarTotalCancelToUsd();

                }

                $scope.transformarImporteNetoBs = function () {
                    $scope.formData.importeNetoBs = $scope.formData.importeNetoUsd === null ? undefined : Number(parseFloat($scope.formData.importeNetoUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarImporteNetoUsd = function () {
                    $scope.formData.importeNetoUsd = $scope.formData.importeNetoBs === null ? undefined : Number(parseFloat($scope.formData.importeNetoBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarImpuestoBobToBs = function () {
                    $scope.formData.impuestoBobBs = $scope.formData.impuestoBobUsd === null ? undefined : Number(parseFloat($scope.formData.impuestoBobUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarImpuestoBobToUsd = function () {
                    $scope.formData.impuestoBobUsd = $scope.formData.impuestoBobBs === null ? undefined : Number(parseFloat($scope.formData.impuestoBobBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarImpuestoQmToBs = function () {
                    $scope.formData.impuestoQmBs = $scope.formData.impuestoQmUsd === null ? undefined : Number(parseFloat($scope.formData.impuestoQmUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarImpuestoQmToUsd = function () {
                    $scope.formData.impuestoQmUsd = $scope.formData.impuestoQmBs === null ? undefined : Number(parseFloat($scope.formData.impuestoQmBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarListaImpuestosToBs = function () {
                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoUsd !== undefined) {
                                $scope.aerolineaImpuestos[e].valorImpuestoBs = $scope.aerolineaImpuestos[e].valorImpuestoUsd === null ? undefined : Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoUsd * $scope.formData.factorCambiario).toFixed(2));
                            }
                        }
                    }
                }

                $scope.transformarListaImpuestosToUsd = function () {
                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoBs !== undefined) {
                                $scope.aerolineaImpuestos[e].valorImpuestoUsd = $scope.aerolineaImpuestos[e].valorImpuestoBs === null ? undefined : Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoBs / $scope.formData.factorCambiario).toFixed(2));
                            }
                        }
                    }
                }

                $scope.transformarMontoFeeToBs = function () {
                    $scope.formData.montoFeeBs = Number(parseFloat($scope.formData.montoFeeUsd) * parseFloat($scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarMontoFeeToUsd = function () {
                    $scope.formData.montoFeeUsd = Number(parseFloat($scope.formData.montoFeeBs) / parseFloat($scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarComisionToBs = function () {
                    $scope.formData.comisionBs = Number(parseFloat((Number(parseFloat($scope.formData.comisionUsd)) * Number(parseFloat($scope.formData.factorCambiario)))).toFixed(2));
                }

                $scope.transformarComisionToUsd = function () {
                    $scope.formData.comisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.comisionBs)) / Number(parseFloat($scope.formData.factorCambiario)))).toFixed(2));
                }

                $scope.transformarDescuentoToBs = function () {
                    $scope.formData.montoDescuentoBs = Number(parseFloat($scope.formData.montoDescuentoUsd) * parseFloat($scope.formData.factorCambiario).toFixed(2))
                }

                $scope.transformarDescuentoToUsd = function () {
                    $scope.formData.montoDescuentoUsd = Number(parseFloat($scope.formData.montoDescuentoBs) / parseFloat($scope.formData.factorCambiario).toFixed(2))
                }

                $scope.transformarTotalBoletoToBs = function () {
                    $scope.formData.totalBoletoBs = $scope.formData.totalBoletoUsd === null ? undefined : Number(parseFloat($scope.formData.totalBoletoUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarTotalBoletoToUsd = function () {
                    $scope.formData.totalBoletoUsd = $scope.formData.totalBoletoBs === null ? undefined : Number(parseFloat($scope.formData.totalBoletoBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarTotalCancelToBs = function () {
                    $scope.formData.totalCancelBs = $scope.formData.totalCancelUsd === null ? undefined : Number(parseFloat($scope.formData.totalCancelUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarTotalCancelToUsd = function () {
                    $scope.formData.totalCancelUsd = $scope.formData.totalCancelBs === null ? undefined : Number(parseFloat($scope.formData.totalCancelBs / $scope.formData.factorCambiario).toFixed(2));
                }

                /*
                 * Calculos del formulario
                 */

                $scope.calculateComision = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === NETO || $scope.aerolinea.comisionPromNacTipo === NETO) {
                        $scope.formData.comisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        $scope.formData.comisionBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        //console.log(Number(parseFloat(((porcentaje * $scope.formData.comisionUsd) / 100) + $scope.formData.comisionUsd).toFixed(2)));
                        $scope.formData.comisionUsd = Number(parseFloat(((porcentaje * $scope.formData.comisionUsd) / 100) + $scope.formData.comisionUsd).toFixed(2));
                        $scope.formData.comisionBs = Number(parseFloat(((porcentaje * $scope.formData.comisionBs) / 100) + $scope.formData.comisionBs).toFixed(2));
                        //$scope.calculateTotalBoleto();
                        $scope.calculateTotalCancelUsd();
                        $scope.calculateTotalCancelBs();
                    } else if ($scope.aerolinea.comisionPromIntTipo === TOTAL || $scope.aerolinea.comisionPromNacTipo === TOTAL) {
                        $scope.formData.comisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.totalBoletoUsd)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        $scope.formData.comisionBs = Number(parseFloat((Number(parseFloat($scope.formData.totalBoletoBs)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));

                        $scope.formData.comisionUsd = Number(parseFloat(((porcentaje * $scope.formData.comisionUsd) / 100) + $scope.formData.comisionUsd).toFixed(2));
                        $scope.formData.comisionBs = Number(parseFloat(((porcentaje * $scope.formData.comisionBs) / 100) + $scope.formData.comisionBs).toFixed(2));

                        //$scope.calculateTotalBoleto();
                        $scope.calculateTotalCancelUsd();
                        $scope.calculateTotalCancelBs();
                    }

                }

                $scope.calculateComisionBs = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === NETO) {
                        if ($scope.aerolinea.moneda === BOLIVIANO) {
                            $scope.formData.comisionBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) / 100) * Number(parseFloat($scope.formData.comision))).toFixed(2));
                            $scope.formData.comisionBs = Number(parseFloat(((porcentaje * $scope.formData.comisionBs) / 100) + $scope.formData.comisionBs).toFixed(2));

                            $scope.calculateTotalCancelBs();
                            $scope.transformarComisionToUsd();
                        }
                    }
                }

                $scope.calculateComisionUsd = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === NETO) {
                        if ($scope.aerolinea.moneda === DOLAR) {
                            $scope.formData.comisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) / 100) * Number(parseFloat($scope.formData.comision))).toFixed(2));
                            $scope.formData.comisionUsd = Number(parseFloat(((porcentaje * $scope.formData.comisionUsd) / 100) + $scope.formData.comisionUsd).toFixed(2));
                            $scope.calculateTotalCancelUsd();
                            $scope.transformarComisionToBs();
                        }
                    }
                }

                $scope.calculateFeeMonto = function () {
                    $scope.formData.montoFeeUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.montoFee))) / 100).toFixed(2));
                    $scope.formData.montoFeeBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.montoFee))) / 100).toFixed(2));

                    $scope.calculateTotalCancelUsd();
                    //$scope.transformarMontoFeeToBs();
                }

                $scope.calculateFeeMontoBs = function () {
                    $scope.formData.montoFee = Number(parseFloat($scope.formData.montoFeeBs).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoBs));
                    $scope.calculateTotalCancelBs();
                }

                $scope.calculateFeeMontoUsd = function () {
                    $scope.formData.montoFee = Number(parseFloat($scope.formData.montoFeeUsd).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoUsd));
                    $scope.calculateTotalCancelUsd();
                }
                $scope.calculateDescuentoMonto = function () {
                    $scope.formData.montoDescuentoUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.montoDescuento))) / 100).toFixed(2));
                    $scope.formData.montoDescuentoBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.montoDescuento))) / 100).toFixed(2));
                    $scope.calculateTotalCancelUsd();
                }

                $scope.calculateDescuentoMontoBs = function () {
                    $scope.formData.montoDescuento = Number(parseFloat($scope.formData.montoDescuentoBs).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoBs));
                    $scope.calculateTotalCancelBs();
                    $scope.transformarDescuentoToUsd();
                }


                $scope.calculateDescuentoMontoUsd = function () {
                    $scope.formData.montoDescuento = Number(parseFloat($scope.formData.montoDescuentoUsd).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoUsd));
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarDescuentoToBs();
                }

                $scope.calculateTotalCancelBs = function () {
                    var totalCancelBs = Number(0);

                    totalCancelBs = Number(parseFloat(totalCancelBs).toFixed(2)) + Number(parseFloat($scope.formData.montoFeeBs === null || Number.isNaN($scope.formData.montoFeeBs) ? 0 : Number(parseFloat($scope.formData.montoFeeBs).toFixed(2))).toFixed(2));
                    totalCancelBs = Number(parseFloat(totalCancelBs).toFixed(2)) - Number(parseFloat($scope.formData.montoDescuentoBs === null || Number.isNaN($scope.formData.montoDescuentoBs) ? 0 : Number(parseFloat($scope.formData.montoDescuentoBs).toFixed(2))).toFixed(2));

                    $scope.formData.totalCancelBs = parseFloat(Number(parseFloat(totalCancelBs).toFixed(2)) + Number(parseFloat($scope.formData.totalBoletoBs).toFixed(2))).toFixed(2);

                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.formData.totalCancelBs))) {
                        $scope.formData.totalCancelBs = null;
                    }

                    $scope.transformarTotalCancelToUsd();
                }

                $scope.calculateTotalCancelUsd = function () {
                    var totalCancelUsd = Number(0);

                    totalCancelUsd = Number(parseFloat(totalCancelUsd).toFixed(2)) + Number(parseFloat($scope.formData.montoFeeUsd === null || Number.isNaN($scope.formData.montoFeeUsd) ? 0 : Number(parseFloat($scope.formData.montoFeeUsd).toFixed(2))).toFixed(2));
                    totalCancelUsd = Number(parseFloat(totalCancelUsd).toFixed(2)) - Number(parseFloat($scope.formData.montoDescuentoUsd === null || Number.isNaN($scope.formData.montoDescuentoUsd) ? 0 : Number(parseFloat($scope.formData.montoDescuentoUsd).toFixed(2))).toFixed(2));

                    $scope.formData.totalCancelUsd = parseFloat(Number(parseFloat(totalCancelUsd).toFixed(2)) + Number(parseFloat($scope.formData.totalBoletoUsd).toFixed(2))).toFixed(2);

                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.formData.totalCancelUsd))) {
                        $scope.formData.totalCancelUsd = null;
                    }

                    $scope.transformarTotalCancelToBs();
                }

                $scope.calculateImporteNetoBs = function () {
                    if ($scope.aerolinea.moneda === BOLIVIANO) {
                        $scope.calculateTotalBoletoBs();

                        $scope.calculateComisionBs();
                        $scope.calculateFeeMontoBs();
                        $scope.calculateDescuentoMontoBs();

                        $scope.calculateTotalCancelBs();
                        $scope.transformarCalculateToUsd();
                    }
                }

                $scope.calculateImporteNetoUsd = function () {
                    if ($scope.aerolinea.moneda === DOLAR) {
                        $scope.calculateTotalBoletoUsd();

                        $scope.calculateComisionUsd();
                        $scope.calculateFeeMontoUsd();
                        $scope.calculateDescuentoMontoUsd();


                        $scope.transformarTotalBoletoToBs();
                        $scope.calculateTotalCancelUsd();
                        $scope.transformarCalculateToBs();
                    }
                }

                $scope.calculateImpuestoBobBs = function () {
                    $scope.calculateTotalBoletoBs();
                    $scope.calculateComisionBs();
                    $scope.calculateTotalCancelBs();
                    $scope.transformarImpuestoBobToUsd();
                }

                $scope.calculateImpuestoBobUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateComisionUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarImpuestoBobToBs();
                }

                $scope.calculateImpuestoQmBs = function () {
                    $scope.calculateTotalBoletoBs();
                    $scope.calculateComisionBs();
                    $scope.calculateTotalCancelBs();
                    $scope.transformarImpuestoQmToUsd();
                }

                $scope.calculateImpuestoQmUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateComisionUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarImpuestoQmToBs();
                }

                $scope.calculateListaImpuestosUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarListaImpuestosToBs();
                }

                $scope.calculateTotalBoletoBs = function () {
                    if ($scope.aerolinea.moneda === BOLIVIANO) {
                        /*Total Boleto*/
                        var totalBs = Number(0);
                        totalBs = Number(parseFloat(totalBs).toFixed(2)) + Number(parseFloat($scope.formData.importeNetoBs === null ? 0 : $scope.formData.importeNetoBs).toFixed(2));
                        totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.formData.impuestoBobBs === null ? 0 : $scope.formData.impuestoBobBs).toFixed(2));
                        totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.formData.impuestoQmBs === null ? 0 : $scope.formData.impuestoQmBs).toFixed(2));
                        if ($scope.aerolineaImpuestos.length > 0) {
                            for (var e in $scope.aerolineaImpuestos) {
                                if ($scope.aerolineaImpuestos[e].valorImpuestoBs !== undefined) {
                                    totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoBs === null ? 0 : $scope.aerolineaImpuestos[e].valorImpuestoBs).toFixed(2));
                                }
                            }
                        }
                        $scope.formData.totalBoletoBs = parseFloat((totalBs * 100) / 100).toFixed(2);

                    }
                }

                $scope.calculateTotalBoletoUsd = function () {
                    if ($scope.aerolinea.moneda === DOLAR) {
                        /*Total Boleto*/
                        var totalUsd = Number(0);
                        totalUsd = Number(parseFloat(totalUsd).toFixed(2)) + Number(parseFloat($scope.formData.importeNetoUsd === null ? 0 : $scope.formData.importeNetoUsd).toFixed(2));
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.formData.impuestoBobUsd === null ? 0 : $scope.formData.impuestoBobUsd).toFixed(2));
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.formData.impuestoQmUsd === null ? 0 : $scope.formData.impuestoQmUsd).toFixed(2));
                        if ($scope.aerolineaImpuestos.length > 0) {
                            for (var e in $scope.aerolineaImpuestos) {
                                if ($scope.aerolineaImpuestos[e].valorImpuestoUsd !== undefined) {
                                    totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoUsd === null ? 0 : $scope.aerolineaImpuestos[e].valorImpuestoUsd).toFixed(2));
                                }
                            }
                        }
                        $scope.formData.totalBoletoUsd = parseFloat((totalUsd * 100) / 100).toFixed(2);

                    }
                }

                /**
                 * 
                 * eventos formas de pago
                 */
                $scope.initCredito = function () {
                    $scope.formData.creditoDias = null;
                    $scope.formData.creditoVencimiento = today;

                    if ($scope.cliente !== undefined) {
                        $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                        $scope.setCreditoVencimiento();
                    }
                }

                $scope.initContado = function () {
                    $scope.formData.contadoTipo = null;
                    $scope.formData.contadoNroCheque = null;
                    $scope.formData.contadoIdBanco = null;
                    $scope.formData.contadoNroDeposito = null;
                }

                $scope.initTarjeta = function () {
                    $scope.formData.tarjetaNumero = null;
                    $scope.formData.tarjetaId = null;
                }

                $scope.initCombinado = function () {
                    $scope.formData.combinadoTipo = null;

                    $scope.formData.combinadoCreditoDias = null;
                    $scope.formData.combinadoCreditoVencimiento = null;
                    $scope.formData.combinadoCreditoMonto = null;

                    $scope.formData.combinadoContadoTipo = null; // Efectivo, Cheque, Deposito
                    $scope.formData.combinadoContadoMonto = null;
                    $scope.formData.combinadoContadoNroCheque = null;
                    $scope.formData.combinadoContadoIdBanco = null;
                    $scope.formData.combinadoContadoNroDeposito = null;
                }

                /**
                 * 
                 * Credito
                 */
                $scope.setCreditoVencimiento = function () {
                    var creditToday = new Date();
                    creditToday.setDate(creditToday.getDate() + Number($scope.formData.creditoDias));
                    $scope.formData.creditoVencimiento = creditToday.format("dd/mm/yyyy");
                }

                $scope.creditoClick = function () {
                    $scope.initCredito();
                }

                $scope.contadoClick = function () {
                    $scope.initContado();
                }

                $scope.tarjetaClick = function () {
                    $scope.initTarjeta();
                }

                $scope.combinadoClick = function () {
                    $scope.initCombinado();
                }

                $scope.formHasError = function () {
                    if ($scope.formData.idEmpresa === undefined) {
                        return true;
                    }
                    return (!$scope.formData.idEmpresa.id);
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

                $scope.delete = function () {

                    if ($scope.modalConfirmation.method === 'delete-comision') {
                        $scope.deleteComision();
                    }
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

                $scope.newBoleto = function (tipo) {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;

                    $scope.formData = new Boleto();
                    $scope.formData.tipoBoleto = tipo;
                    $scope.formData.factorCambiario = $scope.dollarToday.valor;

                    $scope.formData.fechaEmision = $scope.dollarToday.fecha;

                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);

                    $scope.clickNuevo = true;
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

                $scope.findCta = function (cta, input) {
                    var i = 0;
                    for (i; i < input.length; i++) {
                        if (input[i].id == cta) {
                            return input[i];
                        }
                    }
                }


                $scope.getLineaAerea();
                $scope.getTipoEmision();
                $scope.getAeropuertos();
                $scope.getClientes();
                $scope.getAllBancos();
                $scope.getAllBancosCuentas();
                $scope.getFactorCambiario();
                $scope.getTarjetas();

                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.idAerolinea.id', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $http.get(`${urlAerolinea.value}get/${$scope.formData.idAerolinea.id}`).then(
                                function (response) {
                                    if (response.data.code === 201) {
                                        $scope.aerolinea = response.data.content;
                                        //cargamos los datos de la aerolinea
                                        $scope.formData.tipoCupon = $scope.aerolinea.moneda;
                                        console.log($scope.aerolinea);
                                        if ($scope.formData.tipoCupon === DOLAR) {
                                            $scope.formData.comision = $scope.aerolinea.comisionPromInt;
                                        } else if ($scope.formData.tipoCupon === BOLIVIANO) {
                                            $scope.formData.comision = $scope.aerolinea.comisionPromNac;
                                        }
                                        $scope.calculateComision();

                                        $scope.getImpuestos($scope.aerolinea);
                                    }
                                },
                                function (error) {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = error;
                                }
                        );
                    }
                });


                $scope.$watch('formData.idCliente.id', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $http.get(`${urlClientes.value}get/${$scope.formData.idCliente.id}`).then(
                                function (response) {
                                    if (response.data.code === 201) {
                                        $scope.cliente = response.data.content;
                                        //cargamos los datos de la aerolinea
                                        $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                                        console.log($scope.cliente);
                                    }
                                },
                                function (error) {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = error;
                                }
                        );
                    }
                });
            }
        ]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});


app.filter('searchAerolinea', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});

app.filter('searchCliente', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});
app.filter('searchEstado', function ($filter) {
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

