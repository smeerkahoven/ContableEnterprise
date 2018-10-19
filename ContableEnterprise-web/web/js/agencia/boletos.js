/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function callPrintModal() {
    $('#frmImprimir').modal('show');
}

function isNumberKey(evt)
{
    if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 ||
            (event.ctrlKey == true && (event.which == '118' || event.which == '86')) ||
            (event.ctrlKey == true && (event.which == '99' || event.which == '67')) ||
            (event.keyCode == 65 && event.ctrlKey === true) ||
            (event.keyCode >= 35 && event.keyCode <= 39)) {
        return;
    } else {
        // Ensure that it is a number and stop the keypress
        if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105)) {
            event.preventDefault();
        }
    }
}




let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString();
let today = new Date().toLocaleDateString();

function Boleto() {
    this.idBoleto = 0;
    this.gestion = null;
    this.idAerolinea = null;
    this.idEmpresa = null;
    this.emision = null;
    this.tipoBoleto = null;
    this.tipoCupon = null;
    this.idPromotor = null;
    this.idLibro = null;
    this.idIngresoCaja = null;
    this.idIngresoCajaTransaccion = null;
    this.estado = null;

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

    this.idNotaDebito = null;
    this.idNotaDebitoTransaccion = null;
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
    this.montoComisionBs = null;
    this.montoComisionUsd = null;
    this.fee = null;
    this.montoFeeBs = null;
    this.montoFeeUsd = null;
    this.descuento = null;
    this.montoDescuentoBs = null;
    this.montoDescuentoUsd = null;
    this.totalMontoCanceladoBs = null;
    this.totalMontoCanceladoUsd = null;

    this.montoPagarLineaAereaBs = null;
    this.montoPagarLineaAereaUsd = null;

    this.formaPago = null; // Contado, Credito, Tarjeta, Combinado

    this.creditoDias = null;
    this.creditoVencimiento = null;

    this.contadoNroCheque = null;
    this.contadoIdBanco = null;
    this.contadoNroDeposito = null;
    this.contadoIdCuenta = null;
    this.contadoTipo = null; // Efectivo, Cheque, Deposito

    this.tarjetaNumero = null;
    this.tarjetaId = null;

    this.combinadoTipo = null; // Contado, Credito, Tarjeta

    this.combinadoCredito = null
    this.combinadoCreditoDias = null;
    this.combinadoCreditoVencimiento = null;
    this.combinadoCreditoMonto = null;

    this.combinadoCredito = null;
    this.combinadoContadoTipo = null; // Efectivo, Cheque, Deposito
    this.combinadoContadoMonto = null;
    this.combinadoContadoNroCheque = null;
    this.combinadoContadoIdBanco = null;
    this.combinadoContadoNroDeposito = null;

    this.combinadoTarjeta = null;
    this.combinadoTarjetaId = null;
    this.combinadoTarjetaNumero = null;
    this.combinadoTarjtaMonto = null;

    //interfaz
    this.creditoPlazoMaximo = null;

}

function Multiple() {
    this.boleto = null;

    this.listaBoletos = [];
    this.totalCancelarMultipleBs = null;
    this.totalCancelarMultipleUsd = null;

    this.idBoletoPadre;
    this.idLibro = null;
    this.idNotaDebito = null;
    this.idIngresoCaja = null;
}

Boleto.prototype = Object.create(Boleto.prototype);
Boleto.prototype.constructor = Boleto;

Multiple.prototype = Object.create(Multiple.prototype);
Multiple.prototype.constructor = Multiple;



var app = angular.module("jsBoletos", ['jsBoletos.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBoletos.controllers', []).controller('frmBoletos',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                $scope.MONEDA_EXTRANJERA = 'U';
                $scope.MONEDA_NACIONAL = 'B';
                $scope.NETO = 'N';
                $scope.TOTAL = 'T';

                $scope.CREDITO = "C";
                $scope.COMBINADO = "P";
                $scope.CONTADO = "E";
                $scope.TARJETA = "T";

                $scope.EFECTIVO = "E";
                $scope.CHEQUE = "C";
                $scope.DEPOSITO = "D";

                $scope.NACIONAL = "N";
                $scope.INTERNACIONAL = "I";

                $scope.MULTIPLE = "M";
                $scope.SIMPLE = "S";
                $scope.VOID = "V";
                $scope.INICIAL = 'I';
                $scope.EMITIDO = 'E';
                $scope.ANULADO = 'A';
                $scope.PENDIENTE = 'P';
                $scope.NOTA_DEBITO = 'N';
                $scope.COMPROBANTE_INGRESO = 'I';
                $scope.COMPROBANTE_CONTABLE = 'C';

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var urlAeropuertos = document.getElementsByName("hdUrlAeropuertos")[0];
                var urlPromotores = document.getElementsByName("hdUrlPromotores")[0];
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
                $scope.showFormNuevo = false;
                $scope.showFormEditar = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = null;
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.itemsByPage = 15;

                $scope.search = {fechaInicio: firstDay, fechaFin: today};

                /*
                 * Obtencion de Datos
                 */

                $scope.find = function () {
                    $scope.loading = true;
                    $scope.showRestfulError = false ;
                    $scope.showRestfulSuccess = false ;
                    return $http({
                        method: 'POST',
                        url: `${url.value}all`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.loading = false;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error.statusText;
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
                        } else {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = error.statusText;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
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
                        $scope.showRestfulMessage = error.statusText;
                    });
                }

                $scope.getTipoEmision = function () {
                    return $http.get(`${url.value}tipo-emision`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboEmision = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
                        ;
                    });
                }

                $scope.getAeropuertos = function () {
                    return $http.get(`${urlAeropuertos.value}combo`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboAeropuerto = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
                        ;
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
                        $scope.showRestfulMessage = error.statusText;
                        ;
                    });
                }

                $scope.getClientesPasajeros = function (idCliente) {
                    return $http.get(`${url.value}cliente-pasajeros/${idCliente}`, )
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboClientePasajeros = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                                ;
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
                        $scope.showRestfulMessage = error.statusText;
                        ;
                    });
                }

                $scope.getImpuestosEditar = function (aerolinea) {
                    var data = {idAerolinea: aerolinea.idAerolinea};
                    return $http({
                        url: `${urlAerolinea.value}all-impuestos`,
                        method: 'POST',
                        data: {token: token.value, content: angular.toJson(data)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.aerolineaImpuestos = response.data.content;
                            if ($scope.formData.tipoCupon === $scope.INTERNACIONAL) {
                                if ($scope.aerolineaImpuestos[0]) {
                                    if ($scope.formData.impuesto1) {
                                        $scope.aerolineaImpuestos[0].valorImpuestoUsd = $scope.formData.impuesto1;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[1]) {
                                    if ($scope.formData.impuesto2) {
                                        $scope.aerolineaImpuestos[1].valorImpuestoUsd = $scope.formData.impuesto2;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[2]) {
                                    if ($scope.formData.impuesto3) {
                                        $scope.aerolineaImpuestos[2].valorImpuestoUsd = $scope.formData.impuesto3;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[3]) {
                                    if ($scope.formData.impuesto4) {
                                        $scope.aerolineaImpuestos[3].valorImpuestoUsd = $scope.formData.impuesto4;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[4]) {
                                    if ($scope.formData.impuesto5) {
                                        $scope.aerolineaImpuestos[4].valorImpuestoUsd = $scope.formData.impuesto4;
                                    }
                                }
                                $scope.transformarListaImpuestosToBs();
                            } else if ($scope.formData.tipoCupon === $scope.NACIONAL) {
                                if ($scope.aerolineaImpuestos[0]) {
                                    if ($scope.formData.impuesto1) {
                                        $scope.aerolineaImpuestos[0].valorImpuestoBs = $scope.formData.impuesto1;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[1]) {
                                    if ($scope.formData.impuesto2) {
                                        $scope.aerolineaImpuestos[1].valorImpuestoBs = $scope.formData.impuesto2;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[2]) {
                                    if ($scope.formData.impuesto3) {
                                        $scope.aerolineaImpuestos[2].valorImpuestoBs = $scope.formData.impuesto3;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[3]) {
                                    if ($scope.formData.impuesto4) {
                                        $scope.aerolineaImpuestos[3].valorImpuestoBs = $scope.formData.impuesto4;
                                    }
                                }

                                if ($scope.aerolineaImpuestos[4]) {
                                    if ($scope.formData.impuesto5) {
                                        $scope.aerolineaImpuestos[4].valorImpuestoBs = $scope.formData.impuesto4;
                                    }
                                }
                                $scope.transformarListaImpuestosToUsd();
                            }
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
                        ;
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
                                $scope.showRestfulMessage = error.statusText;
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
                                $scope.showRestfulMessage = error.statusText;
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
                                $scope.showRestfulMessage = error.statusText;
                            });
                }

                $scope.getCounters = function () {
                    return $http.get(`${urlPromotores.value}combo/all-counters/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboCounter = response.data.content;
                                }
                            }, function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });
                }

                $scope.save = function () {
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    //$scope.formData.idEmpresa = $scope.formData.idEmpresa.id;
                    $http({
                        method: 'POST',
                        url: url.value + 'save-simple',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var boleto = response.data.entidad;
                            $scope.mensajeImprimir = response.data.content;
                            $scope.formData.idNotaDebito = boleto.idNotaDebito;
                            $scope.formData.idLibro = boleto.idLibro;
                            $scope.formData.idIngresoCaja = boleto.idIngresoCaja;
                            $scope.showBtnGuardar = false ;
                            //$scope.showForm = false;
                            //$scope.showTable = true;
                            //$scope.getData(url.value, 'all');
                            //document.getElementById("openImprimirDialog").click();
                            callPrintModal();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideBackground();

                    }, function (error) {
                        hideBackground();
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        $scope.showForm = false;
                        return;
                    });
                };

                $scope.saveMultiple = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    $scope.formData.estado = 'P'; // PEndiente
                    $scope.loading = true;
                    showBackground();
                    $http({
                        method: 'POST',
                        url: url.value + 'save-boleto',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.showLoading = false;
                        if (response.data.code === 201) {
                            var boleto = response.data.entidad;
                            if (boleto !== null) {
                                $scope.formData.idBoleto = boleto.idBoleto;
                            }

                            $scope.showFormMultiple = false;
                            $scope.showTableMultiple = true;

                            if ($scope.multiple.listaBoletos.length === 0) {
                                $scope.multiple.idBoletoPadre = boleto.idBoleto;
                            }

                            $scope.multiple.listaBoletos.push(boleto);
                            $scope.calcularTotalesMultiples();

                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;

                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideBackground();
                    }, function (error) {
                        hideBackground();
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        $scope.showForm = false;
                        return;
                    });
                };

                $scope.saveVoid = function (estado) {

                    if (!$scope.myFormV.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    showBackground();
                    $scope.formData.estado = estado;
                    $http({
                        method: 'POST',
                        url: url.value + 'save-void',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.showLoading = false;
                        if (response.data.code === 201) {

                            $scope.showFormMultiple = false;
                            $scope.showTableMultiple = false;
                            $scope.showFormVoid = false;
                            $scope.showTable = true;

                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;

                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        hideBackground()
                        $scope.loading = false;

                    }, function (error) {
                        hideBackground();
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        $scope.showForm = false;
                        return;
                    });
                }

                $scope.calcularTotalesMultiples = function () {
                    $scope.multiple.totalCancelarMultipleBs = 0;
                    $scope.multiple.totalCancelarMultipleUsd = 0;
                    for (var i = 0 in $scope.multiple.listaBoletos) {
                        if ($scope.multiple.listaBoletos[i]) {
                            var boleto = $scope.multiple.listaBoletos[i];
                            $scope.multiple.totalCancelarMultipleBs = Number(parseFloat($scope.multiple.totalCancelarMultipleBs).toFixed(2)) + Number(parseFloat(boleto.totalMontoCanceladoBs).toFixed(2));
                            $scope.multiple.totalCancelarMultipleUsd = Number(parseFloat($scope.multiple.totalCancelarMultipleUsd).toFixed(2)) + Number(parseFloat(boleto.totalMontoCanceladoUsd).toFixed(2));
                        }
                    }
                }

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
                    $scope.formData.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.formData.montoComisionUsd)) * Number(parseFloat($scope.formData.factorCambiario)))).toFixed(2));
                }

                $scope.transformarComisionToUsd = function () {
                    $scope.formData.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.montoComisionBs)) / Number(parseFloat($scope.formData.factorCambiario)))).toFixed(2));
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
                    $scope.formData.totalMontoCanceladoBs = $scope.formData.totalMontoCanceladoUsd === null ? undefined : Number(parseFloat($scope.formData.totalMontoCanceladoUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarTotalCancelToUsd = function () {
                    $scope.formData.totalMontoCanceladoUsd = $scope.formData.totalMontoCanceladoBs === null ? undefined : Number(parseFloat($scope.formData.totalMontoCanceladoBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarMontoPagarLineaAereaUsd = function () {
                    $scope.formData.montoPagarLineaAereaUsd = $scope.formData.montoPagarLineaAereaBs === null ? undefined : Number(parseFloat($scope.formData.montoPagarLineaAereaBs / $scope.formData.factorCambiario).toFixed(2));
                }

                $scope.transformarMontoPagarLineaAereaBs = function () {
                    $scope.formData.montoPagarLineaAereaBs = $scope.formData.montoPagarLineaAereaUsd === null ? undefined : Number(parseFloat($scope.formData.montoPagarLineaAereaUsd * $scope.formData.factorCambiario).toFixed(2));
                }

                /**
                 * Anulaciones  Anula el boleto que esta en el formData
                 **/

                $scope.anular = function (boleto) {
                    $scope.showLoading = true;
                    console.log(boleto) ;
                    return $http({
                        url: `${url.value}anular`,
                        method: 'POST',
                        data: {token: token.value, content: {idBoleto: boleto.idBoleto}
                        },
                        headers: {'Content-type': 'application/json'}
                    }
                    ).then(function (response) {
                        $scope.showLoading = false;
                        console.log(response);
                        if (response.data.code === 201) {
                            $scope.showRestfulSuccess = true;
                            $scope.showRestfulMessage = response.data.content;
                        } else {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
                    });
                }
                
                $scope.modalAnular = function (boleto) {
                    $scope.boletoAnular = boleto ;
                }

                /*
                 * Calculos del formulario
                 */

                $scope.calculateComision = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO || $scope.aerolinea.comisionPromNacTipo === $scope.NETO) {
                        $scope.formData.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        $scope.formData.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        //console.log(Number(parseFloat(((porcentaje * $scope.formData.montoComisionUsd) / 100) + $scope.formData.montoComisionUsd).toFixed(2)));
                        $scope.formData.montoComisionUsd = Number(parseFloat(((porcentaje * $scope.formData.montoComisionUsd) / 100) + $scope.formData.montoComisionUsd).toFixed(2));
                        $scope.formData.montoComisionBs = Number(parseFloat(((porcentaje * $scope.formData.montoComisionBs) / 100) + $scope.formData.montoComisionBs).toFixed(2));
                        //$scope.calculateTotalBoleto();
                        $scope.calculateTotalCancelUsd();
                        $scope.calculateTotalCancelBs();
                        $scope.calculateMontoPagarLineaAerea();
                    } else if ($scope.aerolinea.comisionPromIntTipo === $scope.TOTAL || $scope.aerolinea.comisionPromNacTipo === $scope.TOTAL) {
                        $scope.formData.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.totalBoletoUsd)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));
                        $scope.formData.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.formData.totalBoletoBs)) * Number(parseFloat($scope.formData.comision))) / 100).toFixed(2));

                        $scope.formData.montoComisionUsd = Number(parseFloat(((porcentaje * $scope.formData.montoComisionUsd) / 100) + $scope.formData.montoComisionUsd).toFixed(2));
                        $scope.formData.montoComisionBs = Number(parseFloat(((porcentaje * $scope.formData.montoComisionBs) / 100) + $scope.formData.montoComisionBs).toFixed(2));

                        //$scope.calculateTotalBoleto();
                        $scope.calculateTotalCancelUsd();
                        $scope.calculateTotalCancelBs();
                        $scope.calculateMontoPagarLineaAerea();
                    }
                }

                $scope.calculateComisionBs = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO) {
                        if ($scope.aerolinea.moneda === BOLIVIANO) {
                            $scope.formData.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) / 100) * Number(parseFloat($scope.formData.comision))).toFixed(2));
                            $scope.formData.montoComisionBs = Number(parseFloat(((porcentaje * $scope.formData.montoComisionBs) / 100) + $scope.formData.montoComisionBs).toFixed(2));

                            $scope.calculateTotalCancelBs();
                            $scope.transformarComisionToUsd();
                        }
                    }
                }

                $scope.calculateComisionUsd = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO) {
                        if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                            $scope.formData.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) / 100) * Number(parseFloat($scope.formData.comision))).toFixed(2));
                            $scope.formData.montoComisionUsd = Number(parseFloat(((porcentaje * $scope.formData.montoComisionUsd) / 100) + $scope.formData.montoComisionUsd).toFixed(2));
                            $scope.calculateTotalCancelUsd();
                            $scope.transformarComisionToBs();
                        }
                    }
                }

                $scope.calculateFeeMonto = function () {
                    $scope.formData.montoFeeUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.fee))) / 100).toFixed(2));
                    $scope.formData.montoFeeBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.fee))) / 100).toFixed(2));

                    $scope.calculateTotalCancelUsd();
                    //$scope.transformarMontoFeeToBs();
                }

                $scope.calculateFeeMontoBs = function () {
                    $scope.formData.fee = Number(parseFloat($scope.formData.montoFeeBs).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoBs));
                    $scope.calculateTotalCancelBs();
                }

                $scope.calculateFeeMontoUsd = function () {
                    $scope.formData.fee = Number(parseFloat($scope.formData.montoFeeUsd).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoUsd));
                    $scope.calculateTotalCancelUsd();
                }
                $scope.calculateDescuentoMonto = function () {
                    $scope.formData.montoDescuentoUsd = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoUsd)) * Number(parseFloat($scope.formData.descuento))) / 100).toFixed(2));
                    $scope.formData.montoDescuentoBs = Number(parseFloat((Number(parseFloat($scope.formData.importeNetoBs)) * Number(parseFloat($scope.formData.descuento))) / 100).toFixed(2));
                    $scope.calculateTotalCancelUsd();
                }

                $scope.calculateDescuentoMontoBs = function () {
                    $scope.formData.descuento = Number(parseFloat($scope.formData.montoDescuentoBs).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoBs));
                    $scope.calculateTotalCancelBs();
                    $scope.transformarDescuentoToUsd();
                }


                $scope.calculateDescuentoMontoUsd = function () {
                    $scope.formData.descuento = Number(parseFloat($scope.formData.montoDescuentoUsd).toFixed(2) * 100) / Number(parseFloat($scope.formData.importeNetoUsd));
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarDescuentoToBs();
                }

                $scope.calculateTotalCancelBs = function () {
                    var totalMontoCanceladoBs = Number(0);
                    totalMontoCanceladoBs = Number(parseFloat(totalMontoCanceladoBs).toFixed(2)) + Number(parseFloat($scope.formData.montoFeeBs === null || Number.isNaN($scope.formData.montoFeeBs) ? 0 : Number(parseFloat($scope.formData.montoFeeBs).toFixed(2))).toFixed(2));
                    totalMontoCanceladoBs = Number(parseFloat(totalMontoCanceladoBs).toFixed(2)) - Number(parseFloat($scope.formData.montoDescuentoBs === null || Number.isNaN($scope.formData.montoDescuentoBs) ? 0 : Number(parseFloat($scope.formData.montoDescuentoBs).toFixed(2))).toFixed(2));
                    $scope.formData.totalMontoCanceladoBs = parseFloat(Number(parseFloat(totalMontoCanceladoBs).toFixed(2)) + Number(parseFloat($scope.formData.totalBoletoBs).toFixed(2))).toFixed(2);
                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.formData.totalMontoCanceladoBs))) {
                        $scope.formData.totalMontoCanceladoBs = null;
                    }
                    $scope.transformarTotalCancelToUsd();
                }

                $scope.calculateTotalCancelUsd = function () {
                    var totalMontoCanceladoUsd = Number(0);
                    totalMontoCanceladoUsd = Number(parseFloat(totalMontoCanceladoUsd).toFixed(2)) + Number(parseFloat($scope.formData.montoFeeUsd === null || Number.isNaN($scope.formData.montoFeeUsd) ? 0 : Number(parseFloat($scope.formData.montoFeeUsd).toFixed(2))).toFixed(2));
                    totalMontoCanceladoUsd = Number(parseFloat(totalMontoCanceladoUsd).toFixed(2)) - Number(parseFloat($scope.formData.montoDescuentoUsd === null || Number.isNaN($scope.formData.montoDescuentoUsd) ? 0 : Number(parseFloat($scope.formData.montoDescuentoUsd).toFixed(2))).toFixed(2));
                    $scope.formData.totalMontoCanceladoUsd = parseFloat(Number(parseFloat(totalMontoCanceladoUsd).toFixed(2)) + Number(parseFloat($scope.formData.totalBoletoUsd).toFixed(2))).toFixed(2);
                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.formData.totalMontoCanceladoUsd))) {
                        $scope.formData.totalMontoCanceladoUsd = null;
                    }
                    $scope.transformarTotalCancelToBs();
                }

                $scope.calculateImporteNetoBs = function () {
                    if ($scope.aerolinea.moneda === $scope.MONEDA_NACIONAL) {
                        $scope.calculateTotalBoletoBs();
                        $scope.calculateComisionBs();
                        $scope.calculateFeeMontoBs();
                        $scope.calculateDescuentoMontoBs();
                        $scope.calculateTotalCancelBs();
                        $scope.transformarCalculateToUsd();
                        $scope.calculateMontoPagarLineaAerea();
                    }
                }

                $scope.calculateImporteNetoUsd = function () {
                    if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                        $scope.calculateTotalBoletoUsd();
                        $scope.calculateComisionUsd();
                        $scope.calculateFeeMontoUsd();
                        $scope.calculateDescuentoMontoUsd();
                        $scope.transformarTotalBoletoToBs();
                        $scope.calculateTotalCancelUsd();
                        $scope.transformarCalculateToBs();
                        $scope.calculateMontoPagarLineaAerea();
                    }
                }

                $scope.calculateImpuestoBobBs = function () {
                    $scope.calculateComisionBs();
                    $scope.calculateTotalBoletoBs();
                    $scope.calculateTotalCancelBs();
                    $scope.transformarImpuestoBobToUsd();
                }

                $scope.calculateImpuestoBobUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateComisionUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarImpuestoBobToBs();
                    $scope.transformarTotalBoletoToBs();
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
                    $scope.transformarTotalBoletoToBs();
                }

                $scope.calculateListaImpuestosUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarListaImpuestosToBs();
                    $scope.transformarTotalBoletoToBs();
                }

                $scope.calculateTotalBoletoBs = function () {
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
                    $scope.formData.totalBoletoBs = Number.isNaN(Number($scope.formData.totalBoletoBs)) ? '' : $scope.formData.totalBoletoBs;
                    $scope.calculateMontoPagarLineaAerea();

                }

                $scope.calculateTotalBoletoUsd = function () {
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
                    $scope.formData.totalBoletoUsd = Number.isNaN(Number($scope.formData.totalBoletoUsd)) ? Number(null) : $scope.formData.totalBoletoUsd;
                    $scope.calculateMontoPagarLineaAerea();
                }

                $scope.calculateMontoPagarLineaAerea = function () {
                    $scope.formData.montoPagarLineaAereaBs = parseFloat(Number($scope.formData.totalBoletoBs) - Number($scope.formData.montoComisionBs)).toFixed(2);
                    $scope.formData.montoPagarLineaAereaUsd = parseFloat(Number($scope.formData.totalBoletoUsd) - Number($scope.formData.montoComisionUsd)).toFixed(2);

                    $scope.formData.montoPagarLineaAereaBs = Number.isNaN(Number($scope.formData.montoPagarLineaAereaBs)) ? Number(null) : $scope.formData.montoPagarLineaAereaBs;
                    $scope.formData.montoPagarLineaAereaUsd = Number.isNaN(Number($scope.formData.montoPagarLineaAereaUsd)) ? Number(null) : $scope.formData.montoPagarLineaAereaUsd;
                }

                /**
                 * 
                 * eventos formas de pago
                 */
                $scope.resetCredito = function () {
                    $scope.formData.creditoDias = null;
                    $scope.formData.creditoVencimiento = null;
                }

                $scope.initCredito = function () {
                    $scope.formData.formaPago = $scope.CREDITO;
                    $scope.resetCredito();
                    $scope.resetContado();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                    $scope.formData.creditoVencimiento = today;
                    if ($scope.cliente !== undefined) {
                        $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                        $scope.setCreditoVencimiento();
                    }
                }

                $scope.resetContado = function () {
                    $scope.formData.contadoTipo = null;
                    $scope.formData.contadoNroCheque = null;
                    $scope.formData.contadoIdBanco = null;
                    $scope.formData.contadoNroDeposito = null;
                }

                $scope.initContado = function () {
                    $scope.formData.formaPago = $scope.CONTADO;
                    $scope.resetContado();
                    $scope.resetCredito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                    $scope.formData.contadoTipo = $scope.EFECTIVO;
                }

                $scope.resetTarjeta = function () {
                    $scope.formData.tarjetaNumero = null;
                    $scope.formData.tarjetaId = null;
                }

                $scope.initTarjeta = function () {
                    $scope.formData.formaPago = $scope.TARJETA;
                    $scope.resetContado();
                    $scope.resetCredito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                }

                $scope.resetCombinado = function () {
                    $scope.formData.combinadoTipo = null;

                    $scope.formData.combinadoCredito = null;
                    $scope.formData.combinadoCreditoDias = null;
                    $scope.formData.combinadoCreditoVencimiento = null;
                    $scope.formData.combinadoCreditoMonto = null;

                    $scope.formData.combinadoContado = null;
                    $scope.formData.combinadoContadoTipo = null; // Efectivo, Cheque, Deposito
                    $scope.formData.combinadoContadoMonto = null;
                    $scope.formData.combinadoContadoNroCheque = null;
                    $scope.formData.combinadoContadoIdBanco = null;
                    $scope.formData.combinadoContadoNroDeposito = null;

                    $scope.formData.combinadoTarjeta = null;
                    $scope.formData.combinadoTarjetaId = null;
                    $scope.formData.combinadoTarjetaNumero = null;
                    $scope.formData.combinadoTarjtaMonto = null;
                }

                $scope.initCombinado = function () {
                    $scope.formData.formaPago = $scope.COMBINADO;

                    $scope.resetContado();
                    $scope.resetCredito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();
                }

                /**
                 * Validacion Nro Boleto
                 */
                $scope.validarNroBoleto = function () {
                    $scope.showCheckNumeroBoletoExists = false;

                    if ($scope.formData.numero !== null && $scope.formData.numero !== undefined) {
                        $scope.showCheckNumeroBoleto = true;
                        return $http.get(`${url.value}existe-boleto/${$scope.formData.numero}`)
                                .then(function (response) {
                                    if (response.data.code === 201) {
                                        $scope.showCheckNumeroBoleto = false;
                                    } else {
                                        $scope.showAlert("Error", response.data.content);
                                        $scope.showCheckNumeroBoletoExists = true;
                                        $scope.showCheckNumeroBoletoMensaje = response.data.content;
                                        $scope.showCheckNumeroBoleto = false;
                                    }
                                }, function (error) {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = error.statusText;
                                });
                    }
                }

                /**
                 * 
                 * Credito
                 */
                $scope.setCreditoVencimiento = function () {
                    if ($scope.formData.creditoDias !== null || $scope.formData.combinadoCreditoDias !== null) {
                        var date = new Date(today);
                        var newdate = new Date(date);

                        newdate.setDate(newdate.getDate() + $scope.formData.creditoDias);

                        var dd = newdate.getDate();
                        var mm = newdate.getMonth() + 1;
                        var y = newdate.getFullYear();
                        var someFormattedDate = dd + '/' + mm + '/' + y;
                        $scope.formData.creditoVencimiento = someFormattedDate;
                        $scope.formData.combinadoCreditoVencimiento = someFormattedDate;
                        //$scope.formData.creditoVencimiento = creditToday.format("dd/mm/yyyy");
                    }
                }

                $scope.onClickCombinadoCredito = function () {
                    $scope.formData.combinadoCreditoDias = $scope.cliente.plazoMaximo;
                    $scope.setCreditoVencimiento();

                    $scope.checkMontoTotalCredito();
                    $scope.checkMontoTotalContado();
                }

                /**
                 * Revisa que las cifras introducidas en el monto del Credito no sobrepasen el total 
                 * a Cancelar, verificando los montos de las otras formas de pago
                 * @returns {undefined}
                 */
                $scope.checkMontoTotalCredito = function () {
                    var montoTotalContadoCredito = 0;
                    $scope.showMontoTotalCancelar = ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) ? $scope.formData.totalMontoCanceladoUsd : $scope.formData.totalMontoCanceladoBs;

                    if ($scope.formData.combinadoContado && $scope.formData.combinadoTarjeta) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - (Number($scope.formData.combinadoContadoMonto) + Number($scope.formData.combinadoTarjetaMonto));
                    } else if ($scope.formData.combinadoContado) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoContadoMonto);
                    } else
                    if ($scope.formData.combinadoTarjeta) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoTarjetaMonto);
                    }

                    if ($scope.formData.combinadoCreditoMonto > montoTotalContadoCredito) {
                        $scope.showCombinadoCreditoMontoExcede = true;
                    } else {
                        $scope.showCombinadoCreditoMontoExcede = false;
                    }
                }

                /**
                 * Revisa que las cifras introducidas en el monto al Contado no sobrepasen el total 
                 * a Cancelar, verificando los montos de las otras formas de pago
                 * @returns {undefined}
                 */
                $scope.checkMontoTotalContado = function () {
                    var montoTotalContadoCredito = 0;
                    $scope.showMontoTotalCancelar = ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) ? $scope.formData.totalMontoCanceladoUsd : $scope.formData.totalMontoCanceladoBs;

                    if ($scope.formData.combinadoTarjeta && $scope.formData.combinadoCredito) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - (Number($scope.formData.combinadoCredito) - Number($scope.formData.combinadoTarjetaMonto));
                    } else if ($scope.formData.combinadoTarjeta) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoTarjetaMonto);
                    } else
                    if ($scope.formData.combinadoCredito) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoCreditoMonto);
                    }

                    if ($scope.formData.combinadoContadoMonto > montoTotalContadoCredito) {
                        $scope.showCombinadoContadoMontoExcede = true;
                    } else {
                        $scope.showCombinadoContadoMontoExcede = false;
                    }

                    /*
                     if ($scope.formData.combinadoTarjeta) {
                     if (Number($scope.formData.combinadoContadoMonto) + Number($scope.formData.combinadoTarjtaMonto) > montoTotalContadoCredito) {
                     $scope.showCombinadoContadoMontoExcede = true;
                     } else {
                     $scope.showCombinadoContadoMontoExcede = false;
                     }
                     
                     } else {
                     if ($scope.formData.combinadoContadoMonto > montoTotalContadoCredito) {
                     $scope.showCombinadoContadoMontoExcede = true;
                     } else {
                     $scope.showCombinadoContadoMontoExcede = false;
                     }
                     
                     }*/
                }

                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.checkMontoTotalTarjeta = function () {
                    var montoTotalContadoCredito = 0;
                    $scope.showMontoTotalCancelar = ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) ? $scope.formData.totalMontoCanceladoUsd : $scope.formData.totalMontoCanceladoBs;

                    if ($scope.formData.combinadoCredito && $scope.formData.combinadoContado) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - (Number($scope.formData.combinadoCredito) - Number($scope.formData.combinadoContado));
                    } else if ($scope.formData.combinadoContado) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoContado);
                    } else
                    if ($scope.formData.combinadoCredito) {
                        montoTotalContadoCredito = Number($scope.showMontoTotalCancelar) - Number($scope.formData.combinadoCreditoMonto);
                    }

                    if ($scope.formData.combinadoContadoMonto > montoTotalContadoCredito) {
                        $scope.showCombinadoTarjetaMontoExcede = true;
                    } else {
                        $scope.showCombinadoTarjetaMontoExcede = false;
                    }
                }
                /*
                 *  Eventos Click Pestanhas
                 */
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

                /**
                 * Falta realizar las validaciones de los valores que son requeridos
                 * @returns {Boolean}
                 */
                $scope.formHasError = function () {
                    //numero del boleto
                    if ($scope.showCheckNumeroBoletoExists) {
                        return true;
                    }

                    //Linea Aerea
                    if ($scope.formData.idAerolinea.id === undefined) {
                        return true;
                    }

                    //ruta 1
                    if ($scope.formData.idRuta1.id === undefined) {
                        return true;
                    }
                    //ruta 2
                    if ($scope.formData.idRuta2.id === undefined) {
                        return true;
                    }

                    //cuenta
                    if ($scope.formData.idCliente.id === undefined) {
                        return true;
                    }

                    if ($scope.formData.nombrePasajero === undefined || $scope.formData.nombrePasajero === null) {
                        return true;
                    }

                    //formas de Pago
                    if ($scope.formData.formaPago === null) {
                        $scope.showAlert('Error', 'Debe Seleccionar una forma de Pago');
                        return;
                    }

                    if ($scope.formData.formaPago === $scope.CREDITO) {
                        if (!$scope.myForm.txtPlazoDias.$valid) {
                            return true;
                        }
                    } else if ($scope.formData.formaPago === $scope.CONTADO) {
                        if ($scope.formData.contadoTipo === $scope.CHEQUE) {
                            if (!$scope.myForm.txtContadoNroCheque.$valid) {
                                return true;
                            } else if (!$scope.formData.contadoIdBanco) {
                                return true;
                            }
                        } else if ($scope.formData.contadoTipo === $scope.DEPOSITO) {
                            if (!$scope.myForm.txtContadoNroDeposito.$valid) {
                                return true;
                            } else if (!$scope.formData.contadoIdBanco) {
                                return true;
                            }
                        }
                    } else if ($scope.formData.formaPago === $scope.COMBINADO) {
                        if ($scope.formData.combinadoCredito) {
                            if ($scope.showCombinadoCreditoMontoExcede) {
                                return true;
                            }
                        }

                        if ($scope.formData.combinadoContado) {
                            if ($scope.showCombinadoContadoMontoExcede) {
                                return true;
                            }
                        }

                        if ($scope.formData.combinadoTarjeta) {
                            if ($scope.showCombinadoTarjetaMontoExcede) {
                                return true;
                            }
                        }
                    }
                    return false;
                }


                $scope.formHasErrorMultiple = function () {
                    //numero del boleto
                    if ($scope.showCheckNumeroBoletoExists) {
                        return true;
                    }

                    //Linea Aerea
                    if ($scope.formData.idAerolinea.id === undefined) {
                        return true;
                    }

                    //ruta 1
                    if ($scope.formData.idRuta1.id === undefined) {
                        return true;
                    }
                    //ruta 2
                    if ($scope.formData.idRuta2.id === undefined) {
                        return true;
                    }

                    //cuenta
                    if ($scope.formData.idCliente.id === undefined) {
                        return true;
                    }

                    //formas de Pago
                    return false;
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
                    showBackground();
                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        hideBackground();
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
                        hideBackground();
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        //$scope.showForm = true;
                    });
                };


                $scope.updateMultiple = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        hideBackground();
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;
                            $scope.showFormMultiple = false;
                            $scope.showTableMultiple = true;
                            //$scope.getData();
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }

                    }, function (error) {
                        hideBackground()
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
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

                    $scope.hideMessagesBox();

                    if (item.tipoBoleto !== $scope.VOID) {
                        $scope.showTable = false;
                        $scope.showForm = true;
                        $scope.showBtnNuevo = false;
                        $scope.showBtnEditar = true;
                        $scope.showFormNuevo = false;
                        $scope.showFormEditar = true;
                        $scope.showFormVoid = false;

                        $scope.formData = item;
                        $scope.formData.idAerolinea = $scope.findCta(item.idAerolinea.id, $scope.comboAerolineas);
                        $scope.formData.idCliente = $scope.findCta(item.idCliente.id, $scope.comboCliente);
                        $scope.formData.idPromotor = $scope.findCta(item.idPromotor.id, $scope.comboCounter);
                        $scope.formData.idRuta1 = $scope.findCta(item.idRuta1.id, $scope.comboAeropuerto);
                        $scope.formData.idRuta2 = $scope.findCta(item.idRuta2.id, $scope.comboAeropuerto);
                        $scope.formData.idRuta3 = $scope.findCta(item.idRuta3 !== undefined ? item.idRuta3.id : null, $scope.comboAeropuerto);
                        $scope.formData.idRuta4 = $scope.findCta(item.idRuta4 !== undefined ? item.idRuta4.id : null, $scope.comboAeropuerto);
                        $scope.formData.idRuta5 = $scope.findCta(item.idRuta5 !== undefined ? item.idRuta5.id : null, $scope.comboAeropuerto);

                        $scope.getImpuestosEditar({idAerolinea: item.idAerolinea.id});
                        //$scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);

                        //transformamos todo a Moneda Nacional
                        if ($scope.formData.tipoCupon === $scope.INTERNACIONAL) {
                            $scope.transformarImporteNetoBs();
                            $scope.transformarImpuestoBobToBs();
                            $scope.transformarImpuestoQmToBs();
                            $scope.transformarTotalBoletoToBs();
                            $scope.transformarComisionToBs();
                            $scope.transformarMontoFeeToBs();
                            $scope.transformarDescuentoToBs();
                            $scope.transformarTotalCancelToBs();
                            $scope.transformarMontoPagarLineaAereaBs();

                        } else if ($scope.formData.tipoCupon === $scope.NACIONAL) {
                            $scope.transformarImporteNetoUsd();
                            $scope.transformarImpuestoBobToUsd();
                            $scope.transformarImpuestoQmToUsd();
                            $scope.transformarTotalBoletoToUsd();
                            $scope.transformarComisionToUsd();
                            $scope.transformarMontoFeeToUsd();
                            $scope.transformarDescuentoToUsd();
                            $scope.transformarTotalCancelToUsd();
                            $scope.transformarMontoPagarLineaAereaUsd();
                        }
                    } /*else if (item.tipoBoleto === $scope.MULTIPLE) {
                        $scope.multiple = new Multiple();
                        if (item.idBoletoPadre !== undefined) {
                            $scope.multiple.idBoletoPadre = item.idBoletoPadre;
                        } else {
                            $scope.multiple.idBoletoPadre = item.idBoleto;
                        }

                        $scope.showTable = false;
                        $scope.showMultiple = true;
                        $scope.showTableMultiple = true;
                        $scope.showForm = false;
                        $scope.showFormVoid = false;

                        $scope.cargarMultiples(item);

                    } */else if (item.tipoBoleto === $scope.VOID) {
                        $scope.formData = item;
                        $scope.showTable = false;
                        $scope.showMultiple = false;
                        $scope.showTableMultiple = false;
                        $scope.showFormMultiple = false;
                        $scope.showFormVoid = true;

                        $scope.showBtnNuevo = false;
                        $scope.showBtnEditar = true;
                        $scope.showFormNuevo = false;
                        $scope.showFormEditar = true

                        $scope.formData.idPromotor = $scope.findCta(item.idPromotor.id, $scope.comboCounter);
                        $scope.formData.idAerolinea = $scope.findCta(item.idAerolinea.id, $scope.comboAerolineas);
                    }
                }

                $scope.editMultiple = function (item) {
                    $scope.showTableMultiple = false;
                    $scope.showForm = true;
                    $scope.formData = item;
                    $scope.showBtnNuevo = false;
                    $scope.showBtnNuevo = false;
                    $scope.showBtnEditar = true;

                    $scope.showBtnMultipleNuevo = false;
                    $scope.showBtnMultipleEditar = true;

                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    $scope.formData = item;
                    $scope.formData.idAerolinea = $scope.findCta(item.idAerolinea.id, $scope.comboAerolineas);
                    $scope.formData.idCliente = $scope.findCta(item.idCliente.id, $scope.comboCliente);
                    $scope.formData.idPromotor = $scope.findCta(item.idPromotor.id, $scope.comboCounter);
                    $scope.formData.idRuta1 = $scope.findCta(item.idRuta1.id, $scope.comboAeropuerto);
                    $scope.formData.idRuta2 = $scope.findCta(item.idRuta2.id, $scope.comboAeropuerto);
                    $scope.formData.idRuta3 = $scope.findCta(item.idRuta3 !== undefined ? item.idRuta3.id : null, $scope.comboAeropuerto);
                    $scope.formData.idRuta4 = $scope.findCta(item.idRuta4 !== undefined ? item.idRuta4.id : null, $scope.comboAeropuerto);
                    $scope.formData.idRuta5 = $scope.findCta(item.idRuta5 !== undefined ? item.idRuta5.id : null, $scope.comboAeropuerto);

                    $scope.getImpuestosEditar({idAerolinea: item.idAerolinea.id});
                    //$scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);

                    //transformamos todo a Moneda Nacional
                    if ($scope.formData.tipoCupon === $scope.INTERNACIONAL) {
                        $scope.transformarImporteNetoBs();
                        $scope.transformarImpuestoBobToBs();
                        $scope.transformarImpuestoQmToBs();
                        $scope.transformarTotalBoletoToBs();
                        $scope.transformarComisionToBs();
                        $scope.transformarMontoFeeToBs();
                        $scope.transformarDescuentoToBs();
                        $scope.transformarTotalCancelToBs();
                        $scope.transformarMontoPagarLineaAereaBs();

                    } else if ($scope.formData.tipoCupon === $scope.NACIONAL) {
                        $scope.transformarImporteNetoUsd();
                        $scope.transformarImpuestoBobToUsd();
                        $scope.transformarImpuestoQmToUsd();
                        $scope.transformarTotalBoletoToUsd();
                        $scope.transformarComisionToUsd();
                        $scope.transformarMontoFeeToUsd();
                        $scope.transformarDescuentoToUsd();
                        $scope.transformarTotalCancelToUsd();
                        $scope.transformarMontoPagarLineaAereaUsd();
                    }
                }

                $scope.cargarMultiples = function (item) {
                    return $http({
                        method: 'POST',
                        url: `${url.value}load-multiple`,
                        data: {token: token.value, content: angular.toJson({idBoleto: item.idBoleto, idBoletoPadre: item.idBoletoPadre})},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.multiple.listaBoletos = response.data.content;
                                    if ($scope.multiple.listaBoletos.length > 0) {
                                        var boleto = $scope.multiple.listaBoletos[0];
                                        $scope.showBtnFinalizar = boleto.tipoPago ? false : false;
                                    }
                                    $scope.calcularTotalesMultiples();
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            },
                            function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            })
                }

                $scope.newBoleto = function (tipo) {
                    console.log(tipo);
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.showCheckNumeroBoletoExists = false;
                    $scope.showCheckNumeroBoleto = false;
                    $scope.showCombinadoCreditoMontoExcede = false;
                    $scope.showCombinadoTarjetaMontoExcede = false;
                    $scope.showMontoTotalCancelar = null;
                    $scope.showFormNuevo = true;
                    $scope.showFormEditar = false;
                    $scope.showBtnGuardar = true ;

                    $scope.formData = new Boleto();
                    $scope.formData.tipoBoleto = tipo;

                    if (tipo === 'M') {
                        $scope.showMultiple = true;
                        $scope.showForm = false;
                        $scope.showFormVoid = false;
                        $scope.showTableMultiple = true;
                        $scope.showFormMultiple = false;
                        $scope.showBtnSaveMultiple = false ;
                        $scope.showBtnFinalizar = true ;
                        
                        $scope.multiple = new Multiple();

                    } else if (tipo === 'S') {
                        $scope.showForm = true;
                        $scope.showFormMultiple = false;

                        $scope.formData.factorCambiario = $scope.dollarToday.valor;
                        $scope.formData.fechaEmision = $scope.dollarToday.fecha;
                        $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                        $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);

                    } else if (tipo === 'V') {
                        $scope.showFormVoid = true;
                        $scope.showMultiple = false;
                    }

                    $scope.clickNuevo = true;
                }

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showMultiple = false;
                    $scope.showFormVoid = false;
                    $scope.showTable = true;
                    $scope.hideMessagesBox();
                    $scope.search = {fechaInicio: firstDay, fechaFin: today};
                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
                }

                $scope.showSuccess = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'success',
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
                            if (input[i].id == cta) {
                                return input[i];
                            }
                        }
                    }
                }

                $scope.modalAnularMultiple = function (boleto) {
                    $scope.modalAnularData = {boleto: boleto.numero, idBoleto: boleto.idBoleto, idNotaDebito: boleto.idNotaDebito, idLibro: boleto.idLibro, idIngresoCaja: boleto.idIngreso};
                }

                $scope.imprimir = function (option) {
                    switch (option) {
                        case 'N'://Nota Debito
                            if (!$scope.formData.idNotaDebito) {
                                $scope.showAlert('Error', 'El Boleto No tiene una Nota de Debito asignada.');
                                return;
                            }
                            window.open(`../../NotaDebitoReportServlet?idNota=${$scope.formData.idNotaDebito}`, '_target');
                            break;

                        case 'I': //Ingreso de Caja
                            if (!$scope.formData.idIngresoCaja) {
                                $scope.showAlert('Error', 'El Boleto No tiene un Ingreso de Caja asignado.');
                                return;
                            }
                            window.open(`../../IngresoCajaReportServlet?idIngreso=${$scope.formData.idIngresoCaja}`, '_target');
                            break;

                        case 'C': // Comprobante de Ingreso
                            if (!$scope.formData.idIngresoCaja) {
                                $scope.showAlert('Error', 'El Boleto No tiene un Comprobante de Ingreso Asignado.');
                                return;
                            }
                            window.open(`../../ComprobanteReporteServlet?idLibro=${$scope.formData.idLibro}`, '_target');
                            break;

                        default:
                            break;
                    }
                }
                /**
                 * Boletos Multiples
                 *
                 */
                $scope.addBoletoMultiple = function () {
                    $scope.showTableMultiple = false;
                    $scope.showForm = true;
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    $scope.showFormNuevo = true;
                    $scope.showFormEditar = false;
                    $scope.showBtnMultipleNuevo = true;
                    $scope.showBtnMultipleEditar = false;
                    
                    $scope.formData = new Boleto();
                    $scope.formData.tipoBoleto = 'M'; // Multiple
                    $scope.formData.factorCambiario = $scope.dollarToday.valor;
                    $scope.formData.fechaEmision = $scope.dollarToday.fecha;
                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);

                    if ($scope.multiple.idBoletoPadre) {
                        $scope.formData.idBoletoPadre = $scope.multiple.idBoletoPadre;
                    }
                }

                $scope.cancelarBoletoMultiple = function () {
                    $scope.showTableMultiple = true;
                    $scope.showForm = false;
                }

                $scope.finalizarBoletoMultiple = function () {
                    if ($scope.multiple.listaBoletos.length < 1) {
                        $scope.showAlert("Error", 'No puede finalizar un boleto multiple con un solo boleto. Debe ingresar al menos 2 boletos');
                        return;
                    }
                    
                    $scope.showBtnSaveMultiple = true ;
                    $scope.showFormaPagoMultipleNuevo = true ;
                    $scope.showFormaPagoMultipleEditar = false ;
                }


                $scope.getLineaAerea();
                $scope.getTipoEmision();
                $scope.getAeropuertos();
                $scope.getClientes();
                $scope.getAllBancos();
                $scope.getAllBancosCuentas();
                $scope.getFactorCambiario();
                $scope.getTarjetas();
                $scope.getCounters();

                // los watch sirven para verificar si el valor cambio
                // Al Momento de seleccionar la linea aerea, carga los datos de la misma
                $scope.$watch('formData.idAerolinea.id', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        if ($scope.showFormNuevo) {
                            $http.get(`${urlAerolinea.value}get/${$scope.formData.idAerolinea.id}`).then(
                                    function (response) {
                                        if (response.data.code === 201) {
                                            $scope.aerolinea = response.data.content;
                                            //cargamos los datos de la aerolinea
                                            if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                                                $scope.formData.tipoCupon = $scope.INTERNACIONAL;
                                                $scope.formData.moneda = $scope.MONEDA_EXTRANJERA;
                                            } else if ($scope.aerolinea.moneda === $scope.MONEDA_NACIONAL) {
                                                $scope.formData.tipoCupon = $scope.NACIONAL;
                                                $scope.formData.moneda = $scope.MONEDA_NACIONAL;
                                            } else {
                                                $scope.showAlert("Error", "La Aerolinea seleccionada, no tiene configurada una moneda preferencial");
                                            }


                                            if ($scope.formData.tipoCupon === $scope.INTERNACIONAL) {
                                                $scope.formData.comision = $scope.aerolinea.comisionPromInt;
                                            } else if ($scope.formData.tipoCupon === $scope.NACIONAL) {
                                                $scope.formData.comision = $scope.aerolinea.comisionPromNac;
                                            }
                                            $scope.calculateComision();

                                            $scope.getImpuestos($scope.aerolinea);
                                        }
                                    },
                                    function (error) {
                                        $scope.showRestfulError = true;
                                        $scope.showRestfulMessage = error.statusText;
                                    }
                            );
                        } else {
                            $http.get(`${urlAerolinea.value}get/${$scope.formData.idAerolinea.id}`).then(
                                    function (response) {
                                        if (response.data.code === 201) {
                                            $scope.aerolinea = response.data.content;
                                        }
                                    },
                                    function (error) {
                                        $scope.showRestfulError = true;
                                        $scope.showRestfulMessage = error.statusText;
                                    }
                            );
                        }
                    }
                });

                //valida que no sea {name : "valor"} el nombre del pasajero
                $scope.$watch('formData.nombrePasajero', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {

                        if (now instanceof Object) {
                            $scope.formData.nombrePasajero = now.name;
                        }

                    }
                });

                // carga los valores de Credito Vencimiento 
                // Lista de Pasajeros
                $scope.$watch('formData.idCliente.id', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        if ($scope.showFormNuevo) {
                            $scope.getClientesPasajeros($scope.formData.idCliente.id);
                            $http.get(`${urlClientes.value}get/${$scope.formData.idCliente.id}`).then(
                                    function (response) {
                                        if (response.data.code === 201) {
                                            $scope.cliente = response.data.content;
                                            //cargamos los datos de la aerolinea
                                            $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                                            $scope.formData.combinadoCreditoDias = $scope.cliente.plazoMaximo;
                                            $scope.setCreditoVencimiento();
                                        }
                                    },
                                    function (error) {
                                        $scope.showRestfulError = true;
                                        $scope.showRestfulMessage = error.statusText;
                                    }
                            );
                        } else {
                            $scope.getClientesPasajeros($scope.formData.idCliente.id);
                        }
                    }
                });
            }
        ]);

app.filter('myStrictFilter', function ($filter) {
    return function (input, predicate) {
        return $filter('filter')(input, predicate, true);
    }
});

app.filter('printEstado', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'V':
                return 'VOID';
            case 'E' :
                return 'EMITIDO';
            case 'P' :
                return 'PENDIENTE';
            case 'A' :
                return 'ANULADO';
            default :
                return 'SIN ESTADO';
                break;
        }
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

