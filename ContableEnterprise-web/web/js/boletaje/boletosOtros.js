/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('[id^=txtSearchNumero]').keypress(validateNumber);
});
$(document).ready(function () {
    $('[id^=txtSearchNota]').keypress(validateNumber);
});
$(document).ready(function () {
    $('[id^=txtSearchNota]').keypress(validateNumber);
});
$(document).ready(function () {
    $('[id^=txtSearchNota]').keypress(validateNumber);
});

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString('en-GB');
let today = new Date().toLocaleDateString('en-GB');
let todayDate = new Date();

function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}

function Impuesto() {
    this.nombre = null;
    this.valorImpuestoBs = null;
    this.valorImpuestoUsd = null;
}

function Boleto() {
    this.idBoleto = null;
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
    this.numeroOrigen = null;
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
    this.impuesto1Bs = null;
    this.impuesto2Bs = null;
    this.impuesto3Bs = null;
    this.impuesto4Bs = null;
    this.impuesto5Bs = null;
    this.impuesto1Usd = null;
    this.impuesto2Usd = null;
    this.impuesto3Usd = null;
    this.impuesto4Usd = null;
    this.impuesto5Usd = null;
    this.impuesto1nombre = null;
    this.impuesto2nombre = null;
    this.impuesto3nombre = null;
    this.impuesto4nombre = null;
    this.impuesto5nombre = null;
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
    this.totalMontoCobrarBs = null;
    this.totalMontoCobrarUsd = null;
    this.moneda = null;

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

function NotaDebito() {
    this.idNotaDebito = null;
    this.gestion = null;
    this.idEmpresa = null;
    this.idCliente = null;
    this.idPromotor = null;
    this.fechaEmision = null;
    this.fechaInsert = null;
    this.montoTotalBs = null;
    this.montoTotalUsd = null;
    this.montoAdeudadoUsd = null;
    this.montoAdeudadoBs = null;
    this.factorCambiario = null;
    this.formaPago = null;
    this.idBanco = null;
    this.nroCheque = null;
    this.idTarjetaCredito = null;
    this.nroTarjeta = null;
    this.nroDeposito = null;
    this.idCuentaDeposito = null;
    this.idUsuarioCreador = null;
    this.creditoDias = null;
    this.creditoVencimiento = null;
    /*this.combinadoContado = null;
     this.combinadoTarjeta = null;
     this.combinadoCredito = null;
     this.combinadoContadoTipo = null;*/
    this.estado = null;
    this.transacciones = null;
}

function OtroCargo() {
    this.idCargo = null;
    this.idEmpresa = null;
    this.moneda = null;
    this.idCuentaMayorista = null;
    this.comisionMayorista = null;
    this.idCuentaAgencia = null;
    this.comisionAgencia = null;
    this.idCuentaPromotor = null;
    this.comisionPromotor = null;
    this.concepto = null;
    this.tipo = null;
    this.estado = null;
    this.idNotaDebitoTransaccion = null;
    this.idNotaDebito = null;
    this.usuarioCreador = null;
    this.fechaInsert = null;
}

Impuesto.prototype = Object.create(Impuesto.prototype);
Impuesto.prototype.constructor = Impuesto;

NotaDebito.prototype = Object.create(NotaDebito.prototype);
NotaDebito.prototype.constructor = NotaDebito;

Boleto.prototype = Object.create(Boleto.prototype);
Boleto.prototype.constructor = Boleto;

OtroCargo.prototype = Object.create(OtroCargo.prototype);
OtroCargo.prototype.constructor = OtroCargo;

var app = angular.module("jsBoletosOtros", ['jsBoletosOtros.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBoletosOtros.controllers', []).controller('frmBoletosOtros',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var urlPromotores = document.getElementsByName("hdUrlPromotores")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlTarjeta = document.getElementsByName("hdUrlTarjetas")[0];
                var urlBancos = document.getElementsByName("hdUrlBancos")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var urlAeropuertos = document.getElementsByName("hdUrlAeropuertos")[0];
                var urlBoletos = document.getElementsByName("hdUrlBoletos")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementById("myForm");
                var myFormBoleto = document.getElementById("myFormBoleto");
                var urlPlan = document.getElementsByName("hdUrlPlanCuentas")[0];

                var urlComisionPromotor = document.getElementsByName("hdComisionPromotor")[0];
                var urlcomisionAgencia = document.getElementsByName("hdComisionAgencia")[0];
                var urlPasivoMayorista = document.getElementsByName("hdPasivoMayorista")[0];

                var idEmpresa = document.getElementsByName("idEmpresa")[0].value;
                var porcentaje = document.getElementsByName("hdPorcentaje")[0].value;
                var factorMaxMin = document.getElementsByName("hdFactorMaxMin")[0];

                var urlIngresoCaja = document.getElementsByName("hdIngresoCaja")[0];
                var urlComprobante = document.getElementsByName("hdComprobante")[0];


                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.MANUAL = 'MA';
                $scope.MANUAL_VOID = 'MV';
                $scope.SABRE = 'SA';
                $scope.SABRE_VOID = 'SV';
                $scope.AMADEUS = 'AM';
                $scope.AMADEUS_VOID = 'AV';
                $scope.PAQUETE = 'PQ';
                $scope.SEGURO = 'SE';
                $scope.ALQUILER = 'AL';

                $scope.BOLETO = 'B';
                $scope.PAQUETE = 'P';
                $scope.ALQUILER = 'A';
                $scope.HOTEL = 'H';
                $scope.SEGURO = 'S';
                $scope.RESERVA = 'R';
                $scope.CARGO = 'C';
                $scope.DEBITO = 'D';

                $scope.NOTA_DEBITO = 'N';
                $scope.TRANSACCION = 'T';
                $scope.COMPROBANTE_INGRESO = 'I';
                $scope.COMPROBANTE_CONTABLE = 'C';

                $scope.PENDIENTE = 'P';
                $scope.EMITIDO = 'E';
                $scope.ANULADO = 'A';
                $scope.CREADO = 'C';
                $scope.EN_MORA = 'M';

                $scope.MONEDA_NACIONAL = 'B';
                $scope.MONEDA_EXTRANJERA = 'U';

                $scope.CREDITO = "C";
                $scope.COMBINADO = "P";
                $scope.CONTADO = "E";
                $scope.TARJETA = "T";

                $scope.EFECTIVO = "E";
                $scope.CHEQUE = "H";
                $scope.DEPOSITO = "D";

                $scope.NACIONAL = 'N';
                $scope.INTERNACIONAL = 'I';

                $scope.NETO = 'N';
                $scope.TOTAL = 'T';

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = null;
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.search = {fechaInicio: firstDay, fechaFin: today};

                $scope.itemsByPage = 15;

                /*
                 * Obtencion de Datos Nota Debito
                 */

                $scope.find = function () {
                    $scope.loading = true;
                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;
                    return $http({
                        method: 'POST',
                        url: `${url.value}all`,
                        data: {token: token.value, content: angular.toJson($scope.search)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;

                        } else {
                            $scope.mainGrid = [];
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, $scope.errorFunction);
                }

                /**
                 * Guardar Nota de Debito
                 * @returns {undefined}
                 */
                $scope.save = function () {
                    $scope.clickNuevo = false;
                    if (!$scope.myForm.$valid) {
                        //$scope.showAlert('Error de Verificacion', 'Verifique los mensajes de los valores requeridos')
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
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

                    }, $scope.errorFunction);
                };

                /**
                 * Pendiente Nota de Debito
                 * @returns {undefined}
                 */
                $scope.pendiente = function () {
                    if ($scope.formNotaDebitoHasError()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    $scope.showLoading = true;
                    $http({
                        method: 'POST',
                        headers: {'Content-type': 'application/json'},
                        url: `${url.value}pendiente`,
                        data: {token: token.value, content: angular.toJson($scope.formData)}
                    }).then(function (response) {
                        $scope.showLoading = false;
                        if (response.data.code === 201) {
                            $scope.showForm = false;
                            $scope.showTable = true;
                            $scope.showRestfulSuccess = true;
                            $scope.showRestfulMessage = response.data.content;
                        } else {
                            $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                        }
                    }, $scope.errorFunction);
                }

                $scope.pendienteSinMensaje = function () {
                    $http({
                        method: 'POST',
                        headers: {'Content-type': 'application/json'},
                        url: `${url.value}pendiente`,
                        data: {token: token.value, content: angular.toJson($scope.formData)}
                    }).then(function (response) {
                        $scope.showLoading = false;
                        if (response.data.code === 201) {
                        } else {
                            $scope.showAlert(ERROR_RESPUESTA_TITLE, reponse.data.content);
                        }
                    }, $scope.errorFunction);
                }

                $scope.finalizar = function () {
                    if ($scope.formNotaDebitoHasError()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    if (!$scope.hasFormaDePagos()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, $scope.errorFormaPagoMessage);
                        return;
                    }

                    if ($scope.formData.estado === $scope.EMITIDO) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, ERROR_NOTA_DEBITO_EMITIDA);
                        return;
                    }
                    showBackground();
                    $http({
                        method: 'POST',
                        headers: {'Content-type': 'application/json'},
                        url: `${url.value}finalizar`,
                        data: {token: token.value, content: angular.toJson($scope.formData)}
                    }).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.showTable = true;
                                    $scope.showForm = false;

                                    $scope.showRestfulSuccess = true;
                                    $scope.showRestfulMessage = response.data.content;

                                    goScrollTo('#restful-success');
                                    showModalWindow('#frmImprimir');

                                } else {
                                    $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideModalWindow('frmBackground');
                            },
                            $scope.errorFunction);
                }

                $scope.hasFormaDePagos = function () {
                    switch ($scope.formData.formaPago) {
                        case $scope.EFECTIVO:
                            break;
                        case $scope.CHEQUE:

                            if (!$scope.formData.nroCheque) {
                                $scope.errorFormaPagoMessage = 'No definio un numero de Cheque';
                                return false;
                            }

                            if ($scope.formData.nroCheque === undefined) {
                                $scope.errorFormaPagoMessage = 'No definio un numero de Cheque';
                                return false;
                            }
                            if (!$scope.formData.idBanco) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar un Banco';
                                return false;
                            }

                            break;

                        case $scope.DEPOSITO :
                            if (!$scope.formData.nroDeposito) {
                                $scope.errorFormaPagoMessage = 'No ha ingresado un numero de deposito';
                                return false;
                            }
                            if ($scope.formData.nroDeposito === null) {
                                $scope.errorFormaPagoMessage = 'No ha ingresado un numero de deposito';
                                return false;
                            }


                            if (!$scope.formData.idCuentaDeposito) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una cuenta de deposito';
                                return false;
                            }

                            if ($scope.formData.idCuentaDeposito === null) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una cuenta de deposito';
                                return false;
                            }


                            break;

                        case $scope.CREDITO :

                            if (!$scope.formData.creditoDias) {
                                $scope.errorFormaPagoMessage = 'Debe ingresar una cantidad de Dias para el credito';
                                return false;
                            }

                            if ($scope.formData.creditoDias === null) {
                                $scope.errorFormaPagoMessage = 'Debe ingresar una cantidad de Dias para el credito';
                                return false;
                            }

                            if ($scope.formData.creditoDias > 365) {
                                $scope.errorFormaPagoMessage = 'La cantidad de dias no debe execeder los 365 dias';
                                return false;
                            }

                            break;

                        case $scope.TARJETA :

                            if (!$scope.formData.idTarjetaCredito) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una Tarjeta';
                                return false;
                            }

                            if ($scope.formData.idTarjetaCredito === null) {
                                $scope.errorFormaPagoMessage = 'Debe seleccionar una Tarjeta';
                                return false;
                            }

                            if (!$scope.formData.nroTarjeta) {
                                $scope.errorFormaPagoMessage = 'Ingrese un numero de Tarjeta';
                                return false;
                            }

                            if ($scope.formData.nroTarjeta === null) {
                                $scope.errorFormaPagoMessage = 'Ingrese un numero de Tarjeta';
                                return false;
                            }

                            if ($scope.formData.nroTarjeta.length > 16) {
                                $scope.errorFormaPagoMessage = 'El numero ingresado no tiene los 16 digitos de la tarjeta';
                                return false;
                            }

                            break;
                    }

                    return true;
                }

                $scope.changeTipoCupon = function () {
                    if ($scope.boleto.tipoCupon === $scope.INTERNACIONAL) {
                        $scope.boleto.moneda = $scope.MONEDA_EXTRANJERA;
                    } else if ($scope.boleto.tipoCupon === $scope.NACIONAL) {
                        $scope.boleto.moneda = $scope.MONEDA_NACIONAL;
                    }
                }

                $scope.imprimir = function (option) {
                    switch (option) {
                        case $scope.NOTA_DEBITO://Nota Debito
                            if (!$scope.formData.idNotaDebito) {
                                $scope.showAlert('Error', 'El Boleto No tiene una Nota de Debito asignada.');
                                return;
                            }
                            window.open(`../../NotaDebitoReportServlet?idNota=${$scope.formData.idNotaDebito}`, '_blank');
                            break;

                        case $scope.COMPROBANTE_INGRESO: //Ingreso de Caja
                            $scope.imprimirIngresoCaja();
                            break;

                        case $scope.COMPROBANTE_CONTABLE: // Comprobante de Ingreso
                            $scope.imprimirComprobante();



                        default:
                            break;
                    }
                }

                $scope.imprimirIngresoCaja = function () {
                    $http.get(`${urlIngresoCaja.value}getall/notadebito/${$scope.formData.idNotaDebito}`).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    var lista = response.data.content;
                                    if (lista.length > 0) {
                                        if (lista.length > 1) {
                                            //mostramos la ventana
                                            $scope.gridIngresos = lista;
                                            showModalWindow("#frmIngresosCaja");
                                        } else {
                                            //imprimimos solo 1
                                            $scope.imprimirItemIngresoCaja(lista[0]);
                                        }
                                    } else {
                                        //no hay Ingresos de CAja para Imprimir
                                        $scope.showAlert(ERROR_RESPUESTA_TITLE, 'No Exiten Ingresos de Caja');
                                    }
                                }
                            }, $scope.errorFunction)
                }

                $scope.imprimirItemIngresoCaja = function (item) {
                    window.open(`../../IngresoCajaReportServlet?idIngreso=${item.idIngresoCaja}`, '_blank');
                }

                $scope.imprimirComprobante = function () {
                    $http.get(`${urlComprobante.value}getall/notadebito/${$scope.formData.idNotaDebito}`).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    var lista = response.data.content;
                                    if (lista.length > 0) {

                                        if (lista.length > 1) {
                                            //mostramos la ventana
                                            $scope.gridIngresos = lista;
                                            showModalWindow("#frmComprobante");
                                        } else {
                                            //imprimimos solo 1
                                            $scope.imprimirItemComprobante(lista[0]);
                                        }
                                    } else {
                                        //no hay Ingresos de CAja para Imprimir
                                        $scope.showAlert(ERROR_RESPUESTA_TITLE, 'No Exiten Ingresos de Caja');
                                    }
                                }
                            }, $scope.errorFunction)
                }

                $scope.imprimirItemComprobante = function (item) {
                    window.open(`../../ComprobanteReporteServlet?idLibro=${item.idLibro}`, '_blank');
                }


                /**
                 * Falta realizar las validaciones de los valores que son requeridos
                 * @returns {Boolean}
                 */
                $scope.formHasErrorBoleto = function () {
                    //numero del boleto
                    //console.log(`showCheckNumeroBoletoExists:${$scope.showCheckNumeroBoletoExists}`);
                    if ($scope.showCheckNumeroBoletoExists) {
                        $scope.showErrorAlertMessage = "El numero de Boleto ya existe.";
                        return true;
                    }

                    //console.log(`showMensajeBoletoOrigenNoExiste:${$scope.showMensajeBoletoOrigenNoExiste}`);
                    if ($scope.showMensajeBoletoOrigenNoExiste) {
                        $scope.showErrorAlertMessage = "El numero de Boleto Origen No existe.";
                        return true;
                    }

                    //Linea Aerea
                    //console.log(`$scope.boleto.idAerolinea.id :${$scope.boleto.idAerolinea.id }`);
                    if ($scope.boleto.idAerolinea.id === undefined) {
                        $scope.showErrorAlertMessage = "No ha seleccionado una Aerolinea valida.";
                        return true;
                    }

                    //console.log(`$scope.boleto.idRuta1.id :${$scope.boleto.idRuta1.id }`);
                    //ruta 1
                    if ($scope.boleto.idRuta1.id === undefined) {
                        $scope.showErrorAlertMessage = "Debe ingresar una ruta Origen: Ruta 1";
                        return true;
                    }
                    //ruta 2
                    //console.log(`$scope.boleto.idRuta2.id :${$scope.boleto.idRuta2.id }`);
                    if ($scope.boleto.idRuta2.id === undefined) {
                        $scope.showErrorAlertMessage = "Debe ingresar una ruta Destino: Ruta 2";
                        return true;
                    }

                    //console.log(`$scope.boleto.nombrePasajero:${$scope.boleto.nombrePasajero }`);
                    if ($scope.boleto.nombrePasajero === undefined || $scope.boleto.nombrePasajero === null) {
                        $scope.showErrorAlertMessage = "Debe ingresar un nombre de Pasajero";
                        return true;
                    }

                    //console.log(`$scope.boleto.idPromotor:${$scope.boleto.idPromotor }`);
                    if ($scope.boleto.idPromotor === undefined) {
                        $scope.showErrorAlertMessage = "Debe ingresar una nombre de Promotor";
                        return null;
                    }




                    return false;
                }

                $scope.formHasErrorVoid = function () {
                    //numero del boleto
                    if ($scope.showCheckNumeroBoletoExists) {
                        return true;
                    }

                    if ($scope.showMensajeBoletoOrigenNoExiste) {
                        return true;
                    }

                    //Linea Aerea
                    if ($scope.boleto.idAerolinea.id === undefined) {
                        return true;
                    }

                    if ($scope.boleto.fechaEmision === undefined) {
                        return true;
                    }

                    return false;

                }

                $scope.formNotaDebitoHasError = function () {
                    if ($scope.formData.idCliente === undefined) {
                        return true;
                    }

                    if ($scope.formData.idCliente.id === undefined) {
                        return true;
                    }

                    if (!$scope.myForm.txtFactor.$valid) {
                        return true;
                    }

                    if (!$scope.myForm.txtFechaEmision.$valid) {
                        return true;
                    }

                    return false;
                }

                $scope.formHasErrorCargos = function () {
                    if ($scope.showCargo) {

                        if (!$scope.cargos.idCuentaMayorista) {
                            return true;
                        }

                        if (!$scope.cargos.idCuentaMayorista.id) {
                            return true;
                        }

                        if (!$scope.cargos.idCuentaAgencia) {
                            return true;
                        }

                        if (!$scope.cargos.idCuentaAgencia.id) {
                            return true;
                        }


                        if (!$scope.cargos.idCuentaPromotor) {
                            return true;
                        }

                        if (!$scope.cargos.idCuentaPromotor.id) {
                            return true;
                        }
                    } else if ($scope.showDebito) {
                        if (!$scope.cargos.idCuentaMayorista) {
                            return true;
                        }

                        if (!$scope.cargos.idCuentaMayorista.id) {
                            return true;
                        }
                    }

                    return false;
                }

                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.updateBoleto = function () {
                    if (!$scope.myFormBoleto.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorBoleto()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, $scope.showErrorAlertMessage);
                        return;
                    }

                    if ($scope.boleto.fechaEmision !== $scope.formData.fechaEmision) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, `La fecha de emision del Boleto <b>${$scope.boleto.numero}</b> es ${$scope.boleto.fechaEmision}, la cual difiere de la fecha de Emision de la Nota de Debito ${$scope.formData.fechaEmision}.`);
                    }

                    showBackground();
                    $scope.loading = true;
                    //if ($scope.boleto.tipoBoleto === $scope.MANUAL)
                    //$scope.assignImpuestosToBoleto();

                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}update`,
                        data: {token: token.value, content: angular.toJson($scope.boleto)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var nota = response.data.entidad;
                            $scope.formData.montoTotalBs = nota.montoTotalBs;
                            $scope.formData.montoTotalUsd = nota.montoTotalUsd;
                            $scope.formData.montoAdeudadoBs = nota.montoAdeudadoBs;
                            $scope.formData.montoAdeudadoUsd = nota.montoAdeudadoUsd;

                            $scope.loadTransacciones();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmBoleto');
                        hideBackground();
                    }, $scope.errorFunction);

                }

                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.updateVoid = function () {
                    if (!$scope.myFormVoid.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorVoid()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}update`,
                        data: {token: token.value, content: angular.toJson($scope.boleto)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var nota = response.data.entidad;

                            $scope.loadTransacciones();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmBoleto');
                        hideBackground();
                    }, $scope.errorFunction);

                }

                $scope.update = function () {
                    if (!$scope.myForm.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasError()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    $scope.loading = true;

                    $scope.formData.idEmpresa = $scope.formData.idEmpresa.id;

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
                    return $http({
                        method: 'POST',
                        url: url.value + 'all/transaccion',
                        data: {token: token.value, content: {'idNotaDebito': item.idNotaDebito}},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.formData.transacciones = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                                hideBackground();
                                $scope.setFactorMaxMin();
                            },
                            function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });

                    //$scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);
                }

                $scope.setFactorMaxMin = function () {
                    $scope.formData.factorMax = parseFloat($scope.formData.factorCambiario) + parseFloat(factorMaxMin.value);
                    $scope.formData.factorMin = parseFloat($scope.formData.factorCambiario) - parseFloat(factorMaxMin.value);
                }

                $scope.editTransaccion = function (row) {
                    console.log(row);
                    if (row.tipo === $scope.BOLETO) {
                        $scope.editBoleto(row.idBoleto);
                    } else {
                        $scope.editCargo(row.idCargo);
                    }
                }

                $scope.editCargo = function (idCargo) {
                    $scope.showDebito = false ;
                    $scope.showCargo = false ;
                    //showBackground();
                    return $http({
                        method: 'POST',
                        url: `${url.value}cargos/get`,
                        data: {token: token.value, content: angular.toJson({'idCargo': idCargo})},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.cargos = response.data.content;
                            $scope.showFrmBoletoEditar = true;
                            $scope.showFrmBoletoNuevo = false;

                            switch ($scope.cargos.tipo) {
                                case $scope.PAQUETE :
                                    $scope.showFormCargoTitle = 'Datos del Paquete';
                                    $scope.showCargo = true ;
                                    break;
                                case $scope.SEGURO :
                                    $scope.showFormCargoTitle = 'Datos del Seguro';
                                    $scope.showCargo = true ;
                                    break;
                                case $scope.ALQUILER :
                                    $scope.showFormCargoTitle = 'Datos del Alquiler';
                                    $scope.showCargo = true ;
                                    break;
                                case  $scope.DEBITO :
                                    $scope.showFormCargoTitle = 'Datos del Alquiler';
                                    $scope.showDebito = true ;
                                    break ;
                                    
                            }

                            if ($scope.cargos.estado === $scope.PENDIENTE) {
                                $scope.showFrmBoletoEditar = true;
                                $scope.showFrmBoletoNuevo = false;
                            } else {
                                $scope.showFrmBoletoEditar = false;
                                $scope.showFrmBoletoNuevo = false;
                            }
                            showModalWindow('#frmCargos');

                        }

                    }, $scope.errorFunction);
                }

                $scope.editBoleto = function (idBoleto) {
                    //showBackground();
                    $scope.showFrmBoletoAutomaticos = false;
                    $scope.showFrmBoletoVer = false;
                    $scope.showFrmBoletoManual = false;


                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}get`,
                        data: {token: token.value, content: angular.toJson({'idBoleto': idBoleto})},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.boleto = response.data.content;
                            switch ($scope.boleto.tipoBoleto) {
                                case $scope.AMADEUS :
                                case $scope.SABRE :
                                case $scope.MANUAL :
                                    $scope.loadEditBoleto();
                                    break;
                                case $scope.MANUAL_VOID :
                                case $scope.AMADEUS_VOID :
                                case $scope.SABRE_VOID:
                                    $scope.loadEditBoletoVoid();
                                    break;
                            }

                            if ($scope.boleto.estado === $scope.PENDIENTE) {
                                $scope.showFrmBoletoEditar = true;
                                $scope.showFrmBoletoNuevo = false;
                            } else {
                                $scope.showFrmBoletoEditar = false;
                                $scope.showFrmBoletoNuevo = false;
                            }
                        }

                    }, $scope.errorFunction);
                }

                $scope.loadEditBoleto = function () {
                    hideBackground();
                    $scope.boleto.idRuta1 = $scope.findCta($scope.boleto.idRuta1.id, $scope.comboAeropuerto);
                    $scope.boleto.idRuta2 = $scope.findCta($scope.boleto.idRuta2.id, $scope.comboAeropuerto);
                    $scope.boleto.idRuta3 = $scope.findCta($scope.boleto.idRuta3 !== undefined ? $scope.boleto.idRuta3.id : null, $scope.comboAeropuerto);
                    $scope.boleto.idRuta4 = $scope.findCta($scope.boleto.idRuta4 !== undefined ? $scope.boleto.idRuta4.id : null, $scope.comboAeropuerto);
                    $scope.boleto.idRuta5 = $scope.findCta($scope.boleto.idRuta5 !== undefined ? $scope.boleto.idRuta5.id : null, $scope.comboAeropuerto);
                    $scope.boleto.idPromotor = $scope.findCta($scope.boleto.idPromotor !== undefined ? $scope.boleto.idPromotor.id : null, $scope.comboCounter);
                    //cargar la aerolinea del boleto
                    showModalWindow('#frmBoleto');

                    $scope.getClientesPasajeros($scope.formData.idCliente.id);
                    //cargar los impuestos del boleto

                    if ($scope.boleto.estado === $scope.EMITIDO
                            || $scope.boleto.estado === $scope.ANULADO) {
                        $scope.showFrmBoletoVer = true;
                    } else if ($scope.boleto.estado === $scope.PENDIENTE
                            || $scope.boleto.estado === $scope.CREADO) {
                        if ($scope.boleto.tipoBoleto === $scope.AMADEUS
                                || $scope.boleto.tipoBoleto === $scope.SABRE) {
                            $scope.showFrmBoletoAutomaticos = true;
                        } else if ($scope.boleto.tipoBoleto === $scope.MANUAL) {
                            $scope.cargarImpuestosBoletosManuales();
                            $scope.showFrmBoletoManual = true;
                        }
                    }

                    if ($scope.formData.estado === $scope.CANCELADO ||
                            $scope.formData.estado === $scope.EN_MORA) {
                        $scope.showFrmBoletoEditar = false;
                    } else {
                        $scope.showFrmBoletoEditar = true;
                    }

                }

                $scope.cargarImpuestosBoletosManuales = function () {
                    $scope.aerolineaImpuestos = [];

                    if ($scope.boleto.impuesto1nombre !== undefined) {
                        $scope.aerolineaImpuestos.push({
                            nombre: $scope.boleto.impuesto1nombre,
                            valorImpuestoBs: $scope.boleto.impuesto1Bs,
                            valorImpuestoUsd: $scope.boleto.impuesto1Usd});
                    }

                    if ($scope.boleto.impuesto2nombre !== undefined) {
                        $scope.aerolineaImpuestos.push({
                            nombre: $scope.boleto.impuesto2nombre,
                            valorImpuestoUsd: $scope.boleto.impuesto2Usd,
                            valorImpuestoBs: $scope.boleto.impuesto2Bs
                        });
                    }

                    if ($scope.boleto.impuesto3nombre !== undefined) {
                        $scope.aerolineaImpuestos.push({
                            nombre: $scope.boleto.impuesto3nombre,
                            valorImpuestoUsd: $scope.boleto.impuesto3Usd,
                            valorImpuestoBs: $scope.boleto.impuesto3Bs
                        });
                    }

                    if ($scope.boleto.impuesto4nombre !== undefined) {
                        $scope.aerolineaImpuestos.push({
                            nombre: $scope.boleto.impuesto4nombre,
                            valorImpuestoUsd: $scope.boleto.impuesto4Usd,
                            valorImpuestoBs: $scope.boleto.impuesto4Bs
                        });
                    }

                    if ($scope.boleto.impuesto5nombre !== undefined) {
                        $scope.aerolineaImpuestos.push({
                            nombre: $scope.boleto.impuesto5nombre,
                            valorImpuestoUsd: $scope.boleto.impuesto5Usd,
                            valorImpuestoBs: $scope.boleto.impuesto5Bs
                        });
                    }
                }


                $scope.loadEditBoletoVoid = function () {
                    hideBackground();
                    showModalWindow('#frmBoletoVoid');
                    $scope.boleto.idPromotor = $scope.findCta($scope.boleto.idPromotor !== undefined ? $scope.boleto.idPromotor.id : null, $scope.comboCounter);
                }

                $scope.ngDisableForAutomaticTicket = function () {
                    if ($scope.boleto.estado === $scope.EMITIDO
                            || $scope.boleto.estado === $scope.ANULADO)
                        return true;

                    if ($scope.boleto.estado === $scope.PENDIENTE) {
                        if ($scope.boleto.tipoBoleto === $scope.AMADEUS
                                || $scope.boleto.tipoBoleto === $scope.AMADEUS_VOID
                                || $scope.boleto.tipoBoleto === $scope.SABRE
                                || $scope.boleto.tipoBoleto === $scope.SABRE_VOID) {
                            return true;
                        } else {
                            return true;
                        }
                    }
                    return false;
                }

                $scope.modalAnular = function (id, title, tipo, item) {
                    $scope.itemAnular = {id: null, title: null, tipo: null, item: null};

                    $scope.itemAnular.id = id;
                    $scope.itemAnular.title = title;
                    $scope.itemAnular.tipo = tipo;
                    $scope.itemAnular.item = item;

                }

                $scope.modalEliminar = function (id, title, tipo, item) {
                    $scope.itemEliminar = {id: null, title: null, tipo: null, item: null};

                    $scope.itemEliminar.id = id;
                    $scope.itemEliminar.title = title;
                    $scope.itemEliminar.tipo = tipo;
                    $scope.itemEliminar.item = item;

                }

                $scope.anular = function () {
                    switch ($scope.itemAnular.tipo) {
                        case $scope.BOLETO:
                            break;

                        case $scope.TRANSACCION :
                            $scope.anularTransaccion();
                            break;

                        case $scope.NOTA_DEBITO :
                            $scope.anularNotaDebito();
                            break;

                        case $scope.CARGO :
                            break;

                        default:

                            break;
                    }
                }

                $scope.anularTransaccion = function () {
                    $scope.showLoading = true;
                    showBackground();
                    return $http({
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        url: `${url.value}anular/transaccion`,
                        data: {token: token.value, content: angular.toJson($scope.itemAnular.item)}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    showSuccess(SUCCESS_TITLE, response.data.content);
                                    var notadebito = response.data.entidad;
                                    $scope.edit(notadebito);
                                } else {
                                    showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideBackground();
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);
                }

                $scope.anularNotaDebito = function () {
                    $scope.showLoading = true;
                    showBackground();
                    return $http({
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        url: `${url.value}anular`,
                        data: {token: token.value, content: angular.toJson($scope.itemAnular.item)}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    $scope.showForm = false;
                                    $scope.showTable = true;
                                    showSuccess(SUCCESS_TITLE, response.data.content);
                                } else {
                                    $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideBackground();
                                goScrollTo('#restful-success');
                            },
                            $scope.errorFunction);

                }

                $scope.eliminarBoleto = function () {
                    $scope.showLoading = true;
                    showBackground();
                    return $http({
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        url: `${urlBoletos.value}delete`,
                        data: {token: token.value, content: angular.toJson($scope.itemEliminar.item)}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    $scope.showForm = false;
                                    $scope.showTable = true;
                                    showSuccess(SUCCESS_TITLE, response.data.content);
                                } else {
                                    $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                }
                                hideBackground();
                            },
                            $scope.errorFunction);

                }

                $scope.errorFunction = function (error) {
                    $scope.loading = false;
                    $scope.showRestfulError = true;
                    $scope.showRestfulMessage = error.statusText;
                    goScrollTo('#restful-success');
                }

                $scope.nuevo = function () {
                    $scope.showLoading = true;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = new NotaDebito();
                    $scope.formData.formaPago = $scope.EFECTIVO;

                    $scope.clickNuevo = true;
                    return $http({
                        method: 'POST',
                        url: `${url.value}new`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                $scope.showLoading = false;
                                if (response.data.code === 201) {
                                    $scope.showForm = true;
                                    $scope.showBtnNuevo = true;
                                    $scope.showBtnEditar = false;
                                    $scope.showTable = false;
                                    $scope.formData = response.data.content;
                                    //$scope.formData.factorCambiario = $scope.dollarToday.valor;
                                    $scope.setFactorMaxMin();
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            }, $scope.errorFunction);
                }

                $scope.newBoleto = function (tipo) {
                    $scope.showCheckNumeroBoletoExists = false;
                    $scope.showMensajeBoletoOrigenNoExiste = false;

                    $scope.showRestfulError = false;
                    $scope.showRestfulSuccess = false;

                    $scope.showFrmBoletoAutomaticos = false;
                    $scope.showFrmBoletoVer = false;
                    $scope.showFrmBoletoManual = false;

                    $scope.showFrmBoletoNuevo = true;
                    $scope.showFrmBoletoEditar = false;

                    switch (tipo) {
                        case $scope.MANUAL:
                            $scope.showFormBoletoManual();
                            break;
                        case $scope.MANUAL_VOID :
                            $scope.showFormBoletoVoid();
                            break;
                        case $scope.SABRE :
                            $scope.showFormSabre();
                            break;
                        case $scope.AMADEUS :
                            $scope.showFormAmadeus();
                            break;
                    }

                    $scope.pendienteSinMensaje();
                }

                $scope.newOtroCargo = function (tipo) {

                    $scope.cargos = new OtroCargo();
                    $scope.cargos.estado = $scope.PENDIENTE;
                    $scope.cargos.tipo = tipo;
                    $scope.cargos.idNotaDebito = $scope.formData.idNotaDebito;

                    $scope.showCargo = false;
                    $scope.showDebito = false;

                    $scope.showFrmBoletoNuevo = true;
                    $scope.showFrmBoletoEditar = false;

                    switch (tipo) {
                        case $scope.PAQUETE :
                            $scope.showFormCargoTitle = 'Datos del Paquete';
                            $scope.showCargo = true;
                            break;
                        case $scope.SEGURO :
                            $scope.showFormCargoTitle = 'Datos del Seguro';
                            $scope.showCargo = true;
                            break;
                        case $scope.ALQUILER :
                            $scope.showFormCargoTitle = 'Datos del Alquiler';
                            $scope.showCargo = true;
                            break;
                        case $scope.DEBITO :
                            $scope.showFormCargoTitle = 'Datos del Debito';
                            $scope.showDebito = true;
                            $scope.getPlanCuentasAll();
                            break;
                    }
                    $scope.showFrmBoletoNuevo = true;
                    $scope.showFrmBoletoEditar = false;

                    showModalWindow('#frmCargos');
                    $scope.pendienteSinMensaje();
                }

                $scope.showFormBoletoVoid = function () {
                    $scope.boleto = new Boleto();
                    $scope.boleto.idCliente = $scope.formData.idCliente;
                    $scope.boleto.idCounter = $scope.formData.idCounter;
                    $scope.boleto.factorCambiario = $scope.formData.factorCambiario;
                    $scope.boleto.estado = $scope.CREADO;
                    $scope.boleto.tipoBoleto = $scope.MANUAL_VOID;
                    $scope.boleto.idNotaDebito = $scope.formData.idNotaDebito;

                    showModalWindow("#frmBoletoVoid");

                    $scope.showFrmBoletoEditar = false;
                    $scope.showFrmBoletoNuevo = true;

                }

                $scope.showFormSabre = function () {
                    showModalWindow('#frmAmadeus');
                    $scope.loadingFrmAmadeus = true;
                    $scope.showTableAmadeus = false;
                    $scope.showRestfulError = false;
                    $scope.frmTitle = 'Boletos Automaticos SABRE';
                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}all/sabre`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                $scope.loadingFrmAmadeus = false;
                                $scope.showTableAmadeus = true;
                                if (response.data.code === 201) {
                                    $scope.amadeusGrid = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            },
                            $scope.errorFunction);
                }

                $scope.showFormAmadeus = function () {
                    showModalWindow('#frmAmadeus');
                    $scope.loadingFrmAmadeus = true;
                    $scope.showTableAmadeus = false;
                    $scope.showRestfulError = false;
                    $scope.frmTitle = 'Boletos Automaticos AMADEUS';
                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}all/amadeus`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(
                            function (response) {
                                $scope.loadingFrmAmadeus = false;
                                $scope.showTableAmadeus = true;
                                if (response.data.code === 201) {
                                    $scope.amadeusGrid = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            },
                            $scope.errorFunction);

                }

                $scope.showFormBoletoManual = function () {
                    $scope.boleto = new Boleto();
                    $scope.boleto.idCliente = $scope.formData.idCliente;
                    $scope.boleto.idPromotor = $scope.formData.idPromotor;
                    $scope.boleto.factorCambiario = $scope.formData.factorCambiario;
                    $scope.boleto.estado = $scope.CREADO;
                    $scope.boleto.tipoBoleto = $scope.MANUAL;
                    $scope.boleto.idNotaDebito = $scope.formData.idNotaDebito;
                    $scope.boleto.fechaEmision = $scope.formData.fechaEmision;

                    $scope.aerolinea = null;
                    $scope.aerolineaImpuestos = null;
                    $scope.getClientesPasajeros($scope.formData.idCliente.id);
                    showModalWindow("#frmBoleto");

                    $scope.showFrmBoletoManual = true;
                    $scope.showFrmBoletoNuevo = true;

                }

                $scope.loadBoletos = function () {
                    var data = [];
                    for (var i in $scope.amadeusGrid) {
                        if ($scope.amadeusGrid[i].selected) {
                            if ($scope.amadeusGrid[i].fechaEmision === $scope.formData.fechaEmision) {
                                data.push($scope.amadeusGrid[i].idBoleto);
                            } else {
                                $scope.showAlert(ERROR_RESPUESTA_TITLE, `La fecha de Emision del Boleto <b>${$scope.amadeusGrid[i].numero}</b> es ${$scope.amadeusGrid[i].fechaEmision}, la cual difiere de la fecha de Emision de la Nota de Debito ${$scope.formData.fechaEmision}. Solo se deben seleccionar Boletos con la misma Fecha de Emision.`);
                                return;
                            }
                        }

                        if (data.length == 0) {
                            showWarning(WARNING_TITLE, 'Debe elegir al menos un Boleto.');
                            return;
                        }
                    }
                    if (data.length > 0) {
                        var asociacion = {
                            idNotaDebito: $scope.formData.idNotaDebito,
                            idCliente: $scope.formData.idCliente.id,
                            factor: $scope.formData.factorCambiario,
                            idPromotor: $scope.formData.idPromotor.id,
                            fechaEmision: $scope.formData.fechaEmision,
                            estado: $scope.formData.estado,
                            boletos: data
                        }
                        $http({
                            method: 'POST',
                            url: `${url.value}boleto/asociar`,
                            data: {token: token.value, content: angular.toJson(asociacion)},
                            headers: {'Content-Type': 'application/json'}
                        }).then(function (response) {
                            //cargar los items de la nota de debito
                            if (response.data.code === 201) {
                                var nota = response.data.entidad;
                                $scope.formData.montoTotalBs = nota.montoTotalBs;
                                $scope.formData.montoTotalUsd = nota.montoTotalUsd;

                                $scope.loadTransacciones();
                            } else {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = response.data.content;
                            }
                            hideModalWindow("#frmAmadeus");

                        }, $scope.errorFunction);
                    }
                }

                $scope.disableButtonAnadir = function () {

                    if ($scope.formData.idCliente === undefined || $scope.formData.idPromotor === undefined)
                        return true;

                    if ($scope.formData.idCliente.id === undefined || $scope.formData.idPromotor.id === undefined)
                        return true;

                    if ($scope.formData.fechaEmision === undefined)
                        return true;

                    return false;
                }

                /**
                 * 
                 * @returns {unresolved}
                 */
                $scope.loadTransacciones = function () {
                    return $http({
                        method: 'POST',
                        url: `${url.value}all/transaccion`,
                        headers: {'Content-Type': 'application/json'},
                        data: {token: token.value,
                            content: {'idNotaDebito': $scope.formData.idNotaDebito}
                        }
                    }
                    ).then(
                            function (response) {
                                if (response.data.code === 201) {
                                    $scope.formData.transacciones = response.data.content;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            },
                            $scope.errorFunction
                            );
                }

                /**
                 * Validacion Nro Boleto
                 */
                $scope.validarNroBoleto = function () {
                    if ($scope.showCheckNumeroBoletoExists)
                        return;

                    if ($scope.boleto.numero !== null && $scope.boleto.numero !== undefined) {
                        $scope.showCheckNumeroBoleto = true;
                        return $http.get(`${urlBoletos.value}existe-boleto/${$scope.boleto.numero}`)
                                .then(function (response) {
                                    if (response.data.code === 201) {
                                        $scope.showCheckNumeroBoleto = false;
                                    } else {
                                        $scope.showAlert("Error", response.data.content);
                                        $scope.showCheckNumeroBoletoExists = true;
                                        $scope.showCheckNumeroBoletoMensaje = response.data.content;
                                        $scope.showCheckNumeroBoleto = false;
                                    }
                                }, $scope.errorFunction);
                    }
                }

                /**
                 * Validar Nro Boleto Origen
                 * @returns {unresolved}
                 */
                $scope.validarNroBoletoOrigen = function () {
                    if ($scope.showMensajeBoletoOrigenNoExiste)
                        return;

                    if ($scope.boleto.numeroOrigen !== null && $scope.boleto.numeroOrigen !== undefined) {
                        $scope.showLoadingBoletoOrigen = true;
                        return $http.get(`${urlBoletos.value}existe-boleto-origen/${$scope.boleto.numeroOrigen}`)
                                .then(function (response) {
                                    $scope.showLoadingBoletoOrigen = false;
                                    if (response.data.code === 201) {
                                        var boleto = response.data.entidad;
                                        $scope.showBoletoOrigenExiste = true;

                                        if ($scope.boleto.tipoBoleto === $scope.MANUAL
                                                || $scope.boleto.tipoBoleto === $scope.AMADEUS
                                                || $scope.boleto.tipoBoleto === $scope.SABRE) {
                                            $scope.boleto.nombrePasajero = boleto.nombrePasajero;
                                            //$scope.boleto.idCliente = $scope.findCta(boleto.idCliente.id, $scope.comboCliente);
                                            $scope.boleto.idPromotor = $scope.findCta(boleto.idPromotor.id, $scope.comboCliente);
                                            $scope.boleto.fechaEmision = boleto.fechaEmision;
                                            $scope.boleto.fechaViaje = boleto.fechaViaje;
                                            $scope.boleto.moneda = boleto.moneda;
                                            $scope.boleto.factorCambiario = boleto.factorCambiario;
                                        }
                                    } else {
                                        $scope.showAlert(ERROR_RESPUESTA_TITLE, response.data.content);
                                        $scope.showMensajeBoletoOrigenNoExiste = true;
                                        $scope.showMensajeBoletoOrigen = response.data.content;
                                    }
                                }, function (error) {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = error.statusText;
                                });
                    }
                }

                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.assignImpuestosToBoleto = function () {
                    for (var i = 0; i < $scope.aerolineaImpuestos.length; i++) {
                        if (i === 0) {
                            $scope.boleto.impuesto1nombre = $scope.aerolineaImpuestos[i].nombre;
                            $scope.boleto.impuesto1Bs = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoBs)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoBs;
                            $scope.boleto.impuesto1Usd = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoUsd)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoUsd;
                        } else if (i === 1) {
                            $scope.boleto.impuesto2nombre = $scope.aerolineaImpuestos[i].nombre;
                            $scope.boleto.impuesto2Bs = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoBs)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoBs;
                            $scope.boleto.impuesto2Usd = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoUsd)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoUsd;
                        } else if (i === 2) {
                            $scope.boleto.impuesto3nombre = $scope.aerolineaImpuestos[i].nombre;
                            $scope.boleto.impuesto3Bs = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoBs)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoBs;
                            $scope.boleto.impuesto3Usd = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoUsd)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoUsd;
                        } else if (i === 3) {
                            $scope.boleto.impuesto4nombre = $scope.aerolineaImpuestos[i].nombre;
                            $scope.boleto.impuesto4Bs = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoBs)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoBs;
                            $scope.boleto.impuesto4Usd = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoUsd)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoUsd;
                        } else if (i === 4) {
                            $scope.boleto.impuesto5nombre = $scope.aerolineaImpuestos[i].nombre;
                            $scope.boleto.impuesto5Bs = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoBs)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoBs;
                            $scope.boleto.impuesto5Usd = Number.isNaN(Number($scope.aerolineaImpuestos[i].valorImpuestoUsd)) ? Number(null) : $scope.aerolineaImpuestos[i].valorImpuestoUsd;
                        }
                    }
                }

                $scope.saveCargos = function () {
                    if (!$scope.myFormCargos.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorCargos()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: url.value + 'cargos/save',
                        data: {token: token.value, content: angular.toJson($scope.cargos)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var nota = response.data.entidad;
                            $scope.formData.montoTotalBs = nota.montoTotalBs;
                            $scope.formData.montoTotalUsd = nota.montoTotalUsd;

                            $scope.formData.montoAdeudadoBs = nota.montoAdeudadoBs;
                            $scope.formData.montoAdeudadoUsd = nota.montoAdeudadoUsd;

                            $scope.loadTransacciones();
                            
                            $scope.showDebito = false ;
                            $scope.showCargo = false ;
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmCargos');
                        hideModalWindow('#frmBackGround');
                        hideBackground();
                    }, $scope.errorFunction);
                };

                $scope.updateCargo = function () {
                    if (!$scope.myFormCargos.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorCargos()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: url.value + 'cargos/update',
                        data: {token: token.value, content: angular.toJson($scope.cargos)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var nota = response.data.entidad;
                            $scope.formData.montoTotalBs = nota.montoTotalBs;
                            $scope.formData.montoTotalUsd = nota.montoTotalUsd;

                            $scope.loadTransacciones();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmCargos');
                        hideModalWindow('#frmBackGround');
                        hideBackground();
                    }, $scope.errorFunction);
                };

                $scope.saveBoleto = function () {
                    if (!$scope.myFormBoleto.$valid) {
                        console.log($scope.myFormBoleto);
                        console.log($scope.myFormBoleto.$valid);
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorBoleto()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    if ($scope.boleto.fechaEmision !== $scope.formData.fechaEmision) {
                        $scope.showAlert(ERROR_RESPUESTA_TITLE, `La fecha de emision del Boleto <b>${$scope.boleto.numero}</b> es ${$scope.boleto.fechaEmision}, la cual difiere de la fecha de Emision de la Nota de Debito ${$scope.formData.fechaEmision}.`);
                    }

                    if ($scope.boleto.estado === $scope.CREADO) {
                        $scope.boleto.estado = $scope.PENDIENTE;
                    }

                    if ($scope.boleto.nombrePasajero !== undefined) {
                        if ($scope.boleto.nombrePasajero.name !== undefined) {
                            $scope.boleto.nombrePasajero = $scope.boleto.nombrePasajero.name;
                        }
                    }

                    showBackground();
                    $scope.loading = true;
                    $scope.assignImpuestosToBoleto();

                    return $http({
                        method: 'POST',
                        url: urlBoletos.value + 'save',
                        data: {token: token.value, content: angular.toJson($scope.boleto)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            var nota = response.data.entidad;
                            $scope.formData.montoTotalBs = nota.montoTotalBs;
                            $scope.formData.montoTotalUsd = nota.montoTotalUsd;

                            $scope.loadTransacciones();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            $scope.showForm = false;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmBoleto');
                        hideBackground();
                    }, $scope.errorFunction);
                };

                $scope.saveBoletoVoid = function () {
                    if (!$scope.myFormVoid.$valid) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }
                    if ($scope.formHasErrorVoid()) {
                        $scope.showAlert(ERROR_VERIFICACION_TITLE, VERIFIQUE_VALORES_REQUERIDOS);
                        return;
                    }

                    showBackground();
                    $scope.loading = true;

                    return $http({
                        method: 'POST',
                        url: `${urlBoletos.value}save-void`,
                        data: {token: token.value, content: angular.toJson($scope.boleto)},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.loadTransacciones();
                        } else {
                            $scope.showAlert("Error", response.data.content);
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                        $scope.loading = false;
                        hideModalWindow('#frmBackground');
                        hideModalWindow('#frmBoletoVoid');

                    }, $scope.errorFunction);
                }
                /*
                 * Calculos del formulario
                 */

                $scope.calculateComision = function () {
                    if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO || $scope.aerolinea.comisionPromNacTipo === $scope.NETO) {
                        $scope.boleto.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoUsd)) * Number(parseFloat($scope.boleto.comision))) / 100).toFixed(2));
                        $scope.boleto.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoBs)) * Number(parseFloat($scope.boleto.comision))) / 100).toFixed(2));
                    } else if ($scope.aerolinea.comisionPromIntTipo === $scope.TOTAL || $scope.aerolinea.comisionPromNacTipo === $scope.TOTAL) {
                        $scope.boleto.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.boleto.totalBoletoUsd)) * Number(parseFloat($scope.boleto.comision))) / 100).toFixed(2));
                        $scope.boleto.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.boleto.totalBoletoBs)) * Number(parseFloat($scope.boleto.comision))) / 100).toFixed(2));
                    }

                    if ($scope.boleto.comision !== undefined && $scope.boleto.comision !== null) {
                        if ($scope.aerolinea.ivaItComision) {
                            $scope.boleto.montoComisionUsd = Number(parseFloat(((porcentaje * $scope.boleto.montoComisionUsd) / 100) + $scope.boleto.montoComisionUsd).toFixed(2));
                            $scope.boleto.montoComisionBs = Number(parseFloat(((porcentaje * $scope.boleto.montoComisionBs) / 100) + $scope.boleto.montoComisionBs).toFixed(2));
                        }

                        if ($scope.aerolinea.roundComisionBob) {
                            $scope.boleto.montoComisionBs = Math.round($scope.boleto.montoComisionBs);
                        }

                        if ($scope.aerolinea.roundComisionUsd) {
                            $scope.boleto.montoComisionUsd = Math.round($scope.boleto.montoComisionUsd);
                        }
                    }
                    $scope.calculateMontoPagarLineaAerea();
                }

                $scope.calculateComisionBs = function () {

                    /* if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO) {
                     if ($scope.aerolinea.moneda === $scope.MONEDA_NACIONAL) {
                     if ($scope.boleto.importeNetoBs) {
                     //$scope.boleto.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoBs)) / 100) * Number(parseFloat($scope.boleto.comision))).toFixed(2));
                     if ($scope.boleto.comision !== undefined && $scope.boleto.comision !== null) {
                     $scope.boleto.montoComisionBs = Number(parseFloat(((porcentaje * $scope.boleto.montoComisionBs) / 100) + $scope.boleto.montoComisionBs).toFixed(2));
                     
                     if ($scope.aerolinea.roundComisionBob) {
                     if ($scope.boleto.montoComisionBs !== null && $scope.boleto.montoComisionBs !== undefined) {
                     $scope.boleto.montoComisionBs = Math.trunc($scope.boleto.montoComisionBs);
                     }
                     }
                     }
                     }
                     
                     //$scope.calculateTotalCancelBs();
                     $scope.transformarComisionToUsd();
                     }
                     }*/

                    if ($scope.aerolinea.roundComisionBob) {
                        if ($scope.boleto.montoComisionBs !== null && $scope.boleto.montoComisionBs !== undefined) {
                            $scope.boleto.montoComisionBs = Math.trunc($scope.boleto.montoComisionBs);
                        }
                    }

                    $scope.transformarComisionToUsd();
                }

                $scope.calculateComisionUsd = function () {
                    /* if ($scope.aerolinea.comisionPromIntTipo === $scope.NETO) {
                     if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                     if ($scope.boleto.importeNetoUsd)
                     $scope.boleto.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoUsd)) / 100) * Number(parseFloat($scope.boleto.comision))).toFixed(2));
                     if ($scope.boleto.montoComisionUsd)
                     $scope.boleto.montoComisionUsd = Number(parseFloat(((porcentaje * $scope.boleto.montoComisionUsd) / 100) + $scope.boleto.montoComisionUsd).toFixed(2));
                     
                     if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.montoComisionUsd = Math.trunc($scope.boleto.montoComisionUsd);
                     }
                     $scope.calculateTotalCancelUsd();
                     $scope.transformarComisionToBs();
                     }
                     }*/

                    if ($scope.aerolinea.roundComisionUsd) {
                        if ($scope.boleto.montoComisionBs !== null && $scope.boleto.montoComisionBs !== undefined) {
                            $scope.boleto.montoComisionUsd = Math.trunc($scope.boleto.montoComisionUsd);
                        }
                    }

                    $scope.transformarComisionToBs();
                }

                $scope.calculateFeeMonto = function () {
                    if ($scope.boleto.fee !== null && $scope.boleto.fee !== undefined) {
                        var feeUsd = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoUsd).toFixed(2)).toFixed(2) * Number(parseFloat($scope.boleto.fee).toFixed(2))) / 100)).toFixed(2);
                        var feeBs = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoBs).toFixed(2)).toFixed(2) * Number(parseFloat($scope.boleto.fee))) / 100)).toFixed(2);

                        if (feeUsd !== null || feeUsd !== undefined)
                            $scope.boleto.montoFeeUsd = parseFloat(feeUsd);
                        if (feeBs !== null || feeBs !== undefined)
                            $scope.boleto.montoFeeBs = parseFloat(feeBs);


                        //$scope.transformarMontoFeeToBs();
                    }
                    if ($scope.boleto.moneda === $scope.MONEDA_NACIONAL) {
                        $scope.calculateTotalCancelBs();
                    } else {
                        $scope.calculateTotalCancelUsd();
                    }
                }

                $scope.calculateFeeMontoBs = function () {
                    if ($scope.boleto.montoFeeBs !== null && $scope.boleto.montoFeeBs !== undefined) {
                        var feeNumber = Number(parseFloat($scope.boleto.montoFeeBs).toFixed(2) * 100).toFixed(2) / Number(parseFloat($scope.boleto.importeNetoBs).toFixed(2)).toFixed(2);
                        $scope.boleto.fee = parseFloat(feeNumber.toFixed(2));
                    }
                    $scope.transformarMontoFeeToUsd();
                    $scope.calculateTotalCancelBs();

                }

                $scope.calculateFeeMontoUsd = function () {
                    var feeNumber = Number(parseFloat($scope.boleto.montoFeeUsd).toFixed(2) * 100).toFixed(2) / Number(parseFloat($scope.boleto.importeNetoUsd).toFixed(2)).toFixed(2);
                    $scope.boleto.fee = parseFloat(feeNumber.toFixed(2));
                    $scope.transformarMontoFeeToBs();
                    $scope.calculateTotalCancelUsd();
                }

                $scope.calculateDescuentoMonto = function () {
                    if ($scope.boleto.descuento !== null && $scope.boleto.descuento !== undefined) {
                        var descuentoUSd = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoUsd)) * Number(parseFloat($scope.boleto.descuento))) / 100).toFixed(2));
                        var descuentoBs = Number(parseFloat((Number(parseFloat($scope.boleto.importeNetoBs)) * Number(parseFloat($scope.boleto.descuento))) / 100).toFixed(2));
                        $scope.boleto.montoDescuentoUsd = parseFloat(descuentoUSd);
                        $scope.boleto.montoDescuentoBs = parseFloat(descuentoBs);
                    }

                    if ($scope.boleto.moneda === $scope.MONEDA_NACIONAL) {
                        $scope.calculateTotalCancelBs();
                    } else {
                        $scope.calculateTotalCancelUsd();
                    }
                    //$scope.calculateTotalCancelUsd();
                }

                $scope.calculateDescuentoMontoBs = function () {
                    if ($scope.boleto.montoDescuentoBs !== null && $scope.boleto.montoDescuentoBs !== undefined) {
                        var descuento = Number(parseFloat($scope.boleto.montoDescuentoBs).toFixed(2) * 100) / Number(parseFloat($scope.boleto.importeNetoBs)).toFixed(2);
                        $scope.boleto.descuento = parseFloat(descuento.toFixed(2));
                    }
                    $scope.calculateTotalCancelBs();
                    $scope.transformarDescuentoToUsd();
                }

                $scope.calculateDescuentoMontoUsd = function () {
                    if ($scope.boleto.montoDescuentoUsd !== null && $scope.boleto.montoDescuentoUsd !== undefined) {
                        var descuento = Number(parseFloat($scope.boleto.montoDescuentoUsd).toFixed(2) * 100) / Number(parseFloat($scope.boleto.importeNetoUsd)).toFixed(2);
                        $scope.boleto.descuento = parseFloat(descuento.toFixed(2));

                    }
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarDescuentoToBs();
                }

                $scope.calculateTotalCancelBs = function () {
                    var totalMontoCobrarBs = Number(0);
                    if ($scope.boleto.montoFeeBs) {
                        totalMontoCobrarBs = Number(parseFloat(totalMontoCobrarBs).toFixed(2)) + Number(parseFloat($scope.boleto.montoFeeBs === null || Number.isNaN($scope.boleto.montoFeeBs) ? 0 : Number(parseFloat($scope.boleto.montoFeeBs).toFixed(2))).toFixed(2));
                    }
                    if ($scope.boleto.montoDescuentoBs) {
                        totalMontoCobrarBs = Number(parseFloat(totalMontoCobrarBs).toFixed(2)) - Number(parseFloat($scope.boleto.montoDescuentoBs === null || Number.isNaN($scope.boleto.montoDescuentoBs) ? 0 : Number(parseFloat($scope.boleto.montoDescuentoBs).toFixed(2))).toFixed(2));
                    }
                    $scope.boleto.totalMontoCobrarBs = parseFloat(Number(parseFloat(totalMontoCobrarBs).toFixed(2)) + Number(parseFloat($scope.boleto.totalBoletoBs).toFixed(2))).toFixed(2);
                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.boleto.totalMontoCobrarBs))) {
                        $scope.boleto.totalMontoCobrarBs = null;
                    }

                    /*if ($scope.aerolinea.roundComisionBob) {
                     if ($scope.boleto.totalMontoCobrarBs !== null) {
                     $scope.boleto.totalMontoCobrarBs =Math.round ($scope.boleto.totalMontoCobrarBs);
                     }
                     }*/

                    $scope.transformarTotalCancelToUsd();
                }

                $scope.calculateTotalCancelUsd = function () {
                    var totalMontoCobrarUsd = Number(0);
                    if ($scope.boleto.montoFeeUsd)
                        totalMontoCobrarUsd = Number(parseFloat(totalMontoCobrarUsd).toFixed(2)) + Number(parseFloat($scope.boleto.montoFeeUsd === null || Number.isNaN($scope.boleto.montoFeeUsd) ? 0 : Number(parseFloat($scope.boleto.montoFeeUsd).toFixed(2))).toFixed(2));
                    if ($scope.boleto.montoDescuentoUsd)
                        totalMontoCobrarUsd = Number(parseFloat(totalMontoCobrarUsd).toFixed(2)) - Number(parseFloat($scope.boleto.montoDescuentoUsd === null || Number.isNaN($scope.boleto.montoDescuentoUsd) ? 0 : Number(parseFloat($scope.boleto.montoDescuentoUsd).toFixed(2))).toFixed(2));

                    if ($scope.boleto.totalBoletoUsd)
                        $scope.boleto.totalMontoCobrarUsd = parseFloat(Number(parseFloat(totalMontoCobrarUsd).toFixed(2)) + Number(parseFloat($scope.boleto.totalBoletoUsd).toFixed(2))).toFixed(2);
                    // solucion a Numeros NaN
                    if (Number.isNaN(Number($scope.boleto.totalMontoCobrarUsd))) {
                        $scope.boleto.totalMontoCobrarUsd = null;
                    }
                    /* if ($scope.boleto.totalMontoCobrarUsd !== null) {
                     if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.totalMontoCobrarUsd = Math.round($scope.boleto.totalMontoCobrarUsd);
                     }
                     }*/

                    $scope.transformarTotalCancelToBs();
                }


                $scope.calculateImporteNetoBs = function () {
                    if ($scope.boleto.moneda === $scope.MONEDA_NACIONAL) {

                        if ($scope.aerolinea.roundComisionBob) {
                            $scope.boleto.importeNetoBs = Math.round($scope.boleto.importeNetoBs);
                        }
                        $scope.calculateTotalBoletoBs();
                        //$scope.calculateComisionBs();
                        $scope.calculateComision();
                        $scope.calculateFeeMontoBs();
                        $scope.calculateDescuentoMontoBs();
                        $scope.calculateTotalCancelBs();
                        $scope.transformarCalculateToUsd();
                        $scope.calculateMontoPagarLineaAerea();
                    }
                }

                $scope.calculateImporteNetoUsd = function () {
                    if ($scope.boleto.moneda === $scope.MONEDA_EXTRANJERA) {
                        if ($scope.aerolinea.roundComisionUsd) {
                            $scope.boleto.importeNetoUsd = Math.round($scope.boleto.importeNetoUsd);
                        }
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
                    if ($scope.boleto.roundComisionBob) {
                        $scope.boleto.impuestoBobBs = Math.trunc($scope.boleto.impuestoBobBs);
                    }

                    //$scope.calculateComisionBs();
                    $scope.calculateComision();
                    $scope.calculateTotalBoletoBs();
                    $scope.calculateTotalCancelBs();
                    $scope.transformarImpuestoBobToUsd();
                }

                $scope.calculateImpuestoBobUsd = function () {

                    if ($scope.aerolinea.roundComisionUsd) {
                        $scope.boleto.impuestoBobUsd = Math.round($scope.boleto.impuestoBobUsd);
                    }

                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateComisionUsd();
                    $scope.calculateTotalCancelUsd();
                    $scope.transformarImpuestoBobToBs();
                    $scope.transformarTotalBoletoToBs();
                }

                $scope.calculateImpuestoQmBs = function () {
                    $scope.calculateTotalBoletoBs();
                    //$scope.calculateComisionBs();
                    $scope.calculateComision();
                    $scope.calculateTotalCancelBs();
                    $scope.transformarImpuestoQmToUsd();
                }

                $scope.calculateImpuestoQmUsd = function () {
                    $scope.calculateTotalBoletoUsd();
                    //$scope.calculateComisionUsd();
                    $scope.calculateComision();
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

                $scope.calculateListaImpuestosBs = function () {
                    $scope.transformarListaImpuestosToUsd();

                    $scope.calculateTotalBoletoBs();
                    $scope.calculateTotalCancelBs();
                }

                $scope.calculateListaImpuestosUsd = function () {
                    $scope.transformarListaImpuestosToBs();

                    $scope.calculateTotalBoletoUsd();
                    $scope.calculateTotalCancelUsd();
                }

                $scope.calculateTotalBoletoBs = function () {
                    /*Total Boleto*/
                    var totalBs = Number(0);
                    if ($scope.boleto.importeNetoBs)
                        totalBs = Number(parseFloat(totalBs).toFixed(2)) + Number(parseFloat($scope.boleto.importeNetoBs === null ? 0 : $scope.boleto.importeNetoBs).toFixed(2));
                    if ($scope.boleto.impuestoBobBs)
                        totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.boleto.impuestoBobBs === null ? 0 : $scope.boleto.impuestoBobBs).toFixed(2));
                    if ($scope.boleto.impuestoQmBs)
                        totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.boleto.impuestoQmBs === null ? 0 : $scope.boleto.impuestoQmBs).toFixed(2));


                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoBs !== undefined) {
                                totalBs = Number(parseFloat(totalBs)) + Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoBs === null ? 0 : $scope.aerolineaImpuestos[e].valorImpuestoBs).toFixed(2));
                            }
                        }
                    }
                    $scope.boleto.totalBoletoBs = parseFloat((totalBs * 100) / 100).toFixed(2);
                    $scope.boleto.totalBoletoBs = Number.isNaN(Number($scope.boleto.totalBoletoBs)) ? '' : $scope.boleto.totalBoletoBs;

                    if ($scope.aerolinea) {
                        if ($scope.aerolinea.roundComisionBob) {
                            $scope.boleto.totalBoletoBs = Math.round($scope.boleto.totalBoletoBs);
                        }
                    }

                    $scope.calculateMontoPagarLineaAerea();

                    $scope.transformarTotalBoletoToUsd();

                }

                $scope.calculateTotalBoletoUsd = function () {
                    /*Total Boleto*/
                    var totalUsd = Number(0);
                    if ($scope.boleto.importeNetoUsd)
                        totalUsd = Number(parseFloat(totalUsd).toFixed(2)) + Number(parseFloat($scope.boleto.importeNetoUsd === null ? 0 : $scope.boleto.importeNetoUsd).toFixed(2));
                    if ($scope.boleto.impuestoBobUsd)
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.boleto.impuestoBobUsd === null ? 0 : $scope.boleto.impuestoBobUsd).toFixed(2));
                    if ($scope.boleto.impuestoQmUsd)
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.boleto.impuestoQmUsd === null ? 0 : $scope.boleto.impuestoQmUsd).toFixed(2));
                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoUsd !== undefined) {
                                totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoUsd === null ? 0 : $scope.aerolineaImpuestos[e].valorImpuestoUsd).toFixed(2));
                            }
                        }
                    }
                    $scope.boleto.totalBoletoUsd = parseFloat((totalUsd * 100) / 100).toFixed(2);
                    //$scope.boleto.totalBoletoUsd = Number.isNaN(Number($scope.boleto.totalBoletoUsd)) ? Number(null) : $scope.boleto.totalBoletoUsd;

                    if ($scope.aerolinea) {
                        if ($scope.aerolinea.roundComisionUsd) {
                            $scope.boleto.totalBoletoUsd = Math.round($scope.boleto.totalBoletoUsd);
                        }
                    }

                    $scope.calculateMontoPagarLineaAerea();

                    $scope.transformarTotalBoletoToBs();
                }

                $scope.calculateMontoPagarLineaAerea = function () {


                    $scope.boleto.montoPagarLineaAereaBs = parseFloat(Number($scope.boleto.totalBoletoBs) - (Number.isNaN(Number($scope.boleto.montoComisionBs)) ? Number(null) : $scope.boleto.montoComisionBs)).toFixed(2);
                    $scope.boleto.montoPagarLineaAereaUsd = parseFloat(Number($scope.boleto.totalBoletoUsd) - (Number.isNaN(Number($scope.boleto.montoComisionUsd)) ? Number(null) : $scope.boleto.montoComisionUsd)).toFixed(2);

                    if ($scope.aerolinea) {
                        if ($scope.aerolinea.roundComisionBob) {
                            $scope.boleto.montoPagarLineaAereaBs = Math.round($scope.boleto.montoPagarLineaAereaBs);
                        }
                    }

                    if ($scope.aerolinea) {
                        if ($scope.aerolinea.roundComisionUsd) {
                            $scope.boleto.montoPagarLineaAereaUsd = Math.round($scope.boleto.montoPagarLineaAereaUsd);
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
                    $scope.boleto.importeNetoBs = $scope.boleto.importeNetoUsd === null ? undefined : Number(parseFloat($scope.boleto.importeNetoUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.importeNetoBs = Math.trunc($scope.boleto.importeNetoBs);
                     }*/
                }

                $scope.transformarImporteNetoUsd = function () {
                    $scope.boleto.importeNetoUsd = $scope.boleto.importeNetoBs === null ? undefined : Number(parseFloat($scope.boleto.importeNetoBs / $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.importeNetoUsd = Math.trunc($scope.boleto.importeNetoUsd);
                     }*/
                }

                $scope.transformarImpuestoBobToBs = function () {
                    $scope.boleto.impuestoBobBs = $scope.boleto.impuestoBobUsd === null ? undefined : Number(parseFloat($scope.boleto.impuestoBobUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.impuestoBobBs = Math.trunc($scope.boleto.impuestoBobBs);
                     }*/
                }

                $scope.transformarImpuestoBobToUsd = function () {
                    $scope.boleto.impuestoBobUsd = $scope.boleto.impuestoBobBs === null ? undefined : Number(parseFloat($scope.boleto.impuestoBobBs / $scope.boleto.factorCambiario).toFixed(2));

                    /* if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.impuestoBobUsd = Math.trunc($scope.boleto.impuestoBobUsd);
                     }*/
                }

                $scope.transformarImpuestoQmToBs = function () {
                    $scope.boleto.impuestoQmBs = $scope.boleto.impuestoQmUsd === null ? undefined : Number(parseFloat($scope.boleto.impuestoQmUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.impuestoQmBs = Math.trunc($scope.boleto.impuestoQmBs);
                     }*/
                }

                $scope.transformarImpuestoQmToUsd = function () {
                    $scope.boleto.impuestoQmUsd = $scope.boleto.impuestoQmBs === null ? undefined : Number(parseFloat($scope.boleto.impuestoQmBs / $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.impuestoQmUsd = Math.trunc($scope.boleto.impuestoQmUsd);
                     }*/
                }

                $scope.transformarListaImpuestosToBs = function () {
                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoUsd !== undefined) {
                                $scope.aerolineaImpuestos[e].valorImpuestoBs = $scope.aerolineaImpuestos[e].valorImpuestoUsd === null ? undefined : Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoUsd * $scope.boleto.factorCambiario).toFixed(2));
                                /*if ($scope.aerolinea.roundComisionBob) {
                                 $scope.aerolineaImpuestos[e].valorImpuestoBs = Math.trunc($scope.aerolineaImpuestos[e].valorImpuestoBs);
                                 }*/
                            }
                        }
                    }
                }

                $scope.transformarListaImpuestosToUsd = function () {
                    if ($scope.aerolineaImpuestos.length > 0) {
                        for (var e in $scope.aerolineaImpuestos) {
                            if ($scope.aerolineaImpuestos[e].valorImpuestoBs !== undefined) {
                                $scope.aerolineaImpuestos[e].valorImpuestoUsd = $scope.aerolineaImpuestos[e].valorImpuestoBs === null ? undefined : Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoBs / $scope.boleto.factorCambiario).toFixed(2));
                                /*if ($scope.aerolinea.roundComisionUsd) {
                                 $scope.aerolineaImpuestos[e].valorImpuestoUsd = Math.trunc($scope.aerolineaImpuestos[e].valorImpuestoUsd);
                                 }*/
                            }
                        }
                    }
                }

                $scope.transformarMontoFeeToBs = function () {
                    if ($scope.boleto.montoFeeUsd !== null && $scope.boleto.montoFeeUsd !== undefined) {

                        var feeMontoBs = Number(parseFloat($scope.boleto.montoFeeUsd).toFixed(2) * parseFloat($scope.boleto.factorCambiario).toFixed(2)).toFixed(2);
                        $scope.boleto.montoFeeBs = Number(feeMontoBs);
                    }
                }

                $scope.transformarMontoFeeToUsd = function () {

                    if ($scope.boleto.montoFeeBs !== null && $scope.boleto.montoFeeBs !== undefined) {

                        var feeMontoUsd = Number(parseFloat($scope.boleto.montoFeeBs).toFixed(2) / parseFloat($scope.boleto.factorCambiario).toFixed(2)).toFixed(2);
                        $scope.boleto.montoFeeUsd = Number(feeMontoUsd);
                    }
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.montoFeeUsd = Math.trunc($scope.boleto.montoFeeUsd);
                     }*/
                }

                $scope.transformarComisionToBs = function () {
                    $scope.boleto.montoComisionBs = Number(parseFloat((Number(parseFloat($scope.boleto.montoComisionUsd)) * Number(parseFloat($scope.boleto.factorCambiario)))).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.montoComisionBs = Math.trunc($scope.boleto.montoComisionBs);
                     }*/
                }

                $scope.transformarComisionToUsd = function () {
                    $scope.boleto.montoComisionUsd = Number(parseFloat((Number(parseFloat($scope.boleto.montoComisionBs)) / Number(parseFloat($scope.boleto.factorCambiario)))).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.montoComisionUsd = Math.trunc($scope.boleto.montoComisionUsd);
                     }*/
                }

                $scope.transformarDescuentoToBs = function () {
                    var montoDescuentoBs = Number(parseFloat($scope.boleto.montoDescuentoUsd).toFixed(2) * parseFloat($scope.boleto.factorCambiario).toFixed(2)).toFixed(2);
                    $scope.boleto.montoDescuentoBs = parseFloat(montoDescuentoBs);
                }

                $scope.transformarDescuentoToUsd = function () {
                    var montoDescuentoUsd = Number(parseFloat($scope.boleto.montoDescuentoBs).toFixed(2) / parseFloat($scope.boleto.factorCambiario).toFixed(2)).toFixed(2);
                    $scope.boleto.montoDescuentoUsd = parseFloat(montoDescuentoUsd);
                }

                $scope.transformarTotalBoletoToBs = function () {
                    $scope.boleto.totalBoletoBs = $scope.boleto.totalBoletoUsd === null ? undefined : Number(parseFloat($scope.boleto.totalBoletoUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.totalBoletoBs = Math.trunc($scope.boleto.totalBoletoBs);
                     }*/
                }

                $scope.transformarTotalBoletoToUsd = function () {
                    $scope.boleto.totalBoletoUsd = $scope.boleto.totalBoletoBs === null ? undefined : Number(parseFloat($scope.boleto.totalBoletoBs / $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.totalBoletoUsd = Math.trunc($scope.boleto.totalBoletoUsd);
                     }*/
                }

                $scope.transformarTotalCancelToBs = function () {
                    $scope.boleto.totalMontoCobrarBs = $scope.boleto.totalMontoCobrarUsd === null ? undefined : Number(parseFloat($scope.boleto.totalMontoCobrarUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.totalMontoCobrarBs = Math.trunc($scope.boleto.totalMontoCobrarBs);
                     }*/
                }

                $scope.transformarTotalCancelToUsd = function () {
                    $scope.boleto.totalMontoCobrarUsd = $scope.boleto.totalMontoCobrarBs === null ? undefined : Number(parseFloat($scope.boleto.totalMontoCobrarBs / $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.totalMontoCobrarUsd = Math.trunc($scope.boleto.totalMontoCobrarUsd);
                     }*/
                }

                $scope.transformarMontoPagarLineaAereaUsd = function () {
                    $scope.boleto.montoPagarLineaAereaUsd = $scope.boleto.montoPagarLineaAereaBs === null ? undefined : Number(parseFloat($scope.boleto.montoPagarLineaAereaBs / $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionUsd) {
                     $scope.boleto.montoPagarLineaAereaUsd = Math.trunc($scope.boleto.montoPagarLineaAereaUsd);
                     }*/
                }

                $scope.transformarMontoPagarLineaAereaBs = function () {
                    $scope.boleto.montoPagarLineaAereaBs = $scope.boleto.montoPagarLineaAereaUsd === null ? undefined : Number(parseFloat($scope.boleto.montoPagarLineaAereaUsd * $scope.boleto.factorCambiario).toFixed(2));
                    /*if ($scope.aerolinea.roundComisionBob) {
                     $scope.boleto.montoPagarLineaAereaBs = Math.trunc($scope.boleto.montoPagarLineaAereaBs);
                     }*/
                }

                /*
                 *  Eventos Click Pestanhas
                 */
                $scope.efectivoClick = function () {
                    $scope.initEfectivo();
                }

                $scope.chequeClick = function () {
                    $scope.initCheque();
                }

                $scope.depositoClick = function () {
                    $scope.initDeposito();
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

                /**
                 * 
                 * eventos formas de pago
                 */


                $scope.initEfectivo = function () {
                    $scope.formData.formaPago = $scope.EFECTIVO;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initCheque = function () {
                    $scope.formData.formaPago = $scope.CHEQUE;
                    $scope.resetDeposito();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initDeposito = function () {
                    $scope.formData.formaPago = $scope.DEPOSITO;
                    $scope.resetCheque();
                    $scope.resetCredito();
                    $scope.resetTarjeta();
                }

                $scope.initCredito = function () {
                    $scope.formData.formaPago = $scope.CREDITO;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                    $scope.formData.creditoVencimiento = $scope.formData.fechaEmision !== undefined ? $scope.formData.fechaEmision : today;
                    if ($scope.cliente !== undefined) {
                        $scope.formData.creditoDias = $scope.cliente.plazoMaximo;
                        $scope.setCreditoVencimiento();
                    }
                }

                $scope.initTarjeta = function () {
                    $scope.formData.formaPago = $scope.TARJETA;
                    $scope.resetCheque();
                    $scope.resetDeposito();
                    $scope.resetCredito();
                    $scope.resetCombinado();
                    $scope.resetTarjeta();

                }

                $scope.resetCredito = function () {
                    $scope.formData.creditoDias = null;
                    $scope.formData.creditoVencimiento = null;
                }

                $scope.resetCheque = function () {
                    $scope.formData.nroCheque = null;
                    $scope.formData.idBanco = null;
                }

                $scope.resetDeposito = function () {
                    $scope.formData.nroDeposito = null;
                    $scope.formData.idCuentaDeposito = null;
                }

                $scope.resetTarjeta = function () {
                    $scope.formData.idTarjetaCredito = null;
                    $scope.formData.nroTarjeta = null;
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
                 * 
                 * Credito
                 */
                $scope.setCreditoVencimiento = function () {
                    if ($scope.formData.creditoDias !== null) {

                        var fechaEmision = $scope.formData.fechaEmision !== undefined ? $scope.formData.fechaEmision : today ;
                        var dias = $scope.formData.creditoDias !== undefined ? $scope.formData.creditoDias : 0 ;

                        return $http.get(`${urlBoletos.value}sumar-fecha/${fechaEmision}/${dias}`).then(function (response) {
                            if (response.data.code === 201) {
                                $scope.formData.creditoVencimiento = response.data.content;
                            }
                        }, function (error) {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = error.statusText;
                            ;
                        });

                    } else {
                        $scope.formData.creditoVencimiento = today;
                    }
                }

                /**
                 * 
                 * @returns {undefined}
                 */

                $scope.cancelar = function () {
                    $scope.showForm = false;
                    $scope.showTable = true;
                    $scope.search = {fechaInicio: firstDay, fechaFin: today};
                    $scope.hideMessagesBox();
                    $scope.mainGrid = [];
                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        html: message,
                        type: 'error'
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
                    }, $scope.errorFunction);
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
                    }, $scope.errorFunction);
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
                    }, $scope.errorFunction);
                }

                $scope.getTipoEmision = function () {
                    return $http.get(`${urlBoletos.value}tipo-emision`).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboEmision = response.data.content;
                        }
                    }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;
                        ;
                    });
                }

                $scope.getClientesPasajeros = function (idCliente) {
                    return $http.get(`${urlBoletos.value}cliente-pasajeros/${idCliente}`, )
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
                            for (var i in $scope.aerolineaImpuestos) {
                                if (i === 0) {
                                    $scope.formData.impuesto1nombre = $scope.aerolineaImpuestos[i].nombre;
                                }
                                if (i === 1) {
                                    $scope.formData.impuesto2nombre = $scope.aerolineaImpuestos[i].nombre;
                                }
                                if (i === 2) {
                                    $scope.formData.impuesto3nombre = $scope.aerolineaImpuestos[i].nombre;
                                }
                                if (i === 3) {
                                    $scope.formData.impuesto4nombre = $scope.aerolineaImpuestos[i].nombre;
                                }
                                if (i === 4) {
                                    $scope.formData.impuesto5nombre = $scope.aerolineaImpuestos[i].nombre;
                                }
                            }
                        }
                    }, $scope.errorFunction);
                }

                $scope.getPlanCuentas = function (url, method) {
                    return $http({
                        method: 'POST',
                        url: urlPlan.value + url,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            if (method === 'promotor') {
                                $scope.comboComisionPromotor = response.data.content;
                            }

                            if (method === 'agencia') {
                                $scope.comboComisionAgencia = response.data.content;
                            }

                            if (method === 'mayorista') {
                                $scope.comboPasivoMayorista = response.data.content;
                            }

                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }


                $scope.getPlanCuentasAll = function () {
                    return $http({
                        method: 'POST',
                        url: `${urlPlan.value}combo/${idEmpresa}`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboCuentaContable = response.data.content;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, $scope.errorFunction);
                }

                $scope.getCounters = function () {
                    return $http.get(`${urlPromotores.value}combo/all-counters/${idEmpresa}`)
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboCounter = response.data.content;
                                }
                            }, $scope.errorFunction);
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

                $scope.getFactorCambiario();
                $scope.getTipoEmision();
                $scope.getClientes();
                $scope.getAeropuertos();
                $scope.getLineaAerea();
                $scope.getCounters();
                $scope.getTarjetas();
                $scope.getAllBancos();
                $scope.getAllBancosCuentas();
                $scope.getPlanCuentas(urlComisionPromotor.value, 'promotor');
                $scope.getPlanCuentas(urlcomisionAgencia.value, 'agencia');
                $scope.getPlanCuentas(urlPasivoMayorista.value, 'mayorista');

                // los watch sirven para verificar si el valor cambio
                $scope.$watch('formData.ctaDevolucionMonExt.id', function (now, old) {
                    if (now == undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        $scope.showErrorCtaDevolucionMonExt = false;
                    }
                });

                $scope.$watch('boleto.nombrePasajero', function (now, old) {
                    if (now === undefined) {
                    } else {
                        if (now instanceof Object) {
                            $scope.formData.nombrePasajero = now.name;
                        }

                    }
                });

                $scope.$watch('boleto.numero', function (now, old) {
                    if (now === undefined) {
                    } else {
                        if (now !== old) {
                            $scope.showCheckNumeroBoletoExists = false;
                        }
                    }
                });


                $scope.$watch('boleto.numeroOrigen', function (now, old) {
                    if (now === undefined) {
                    } else {
                        if (now !== old) {
                            $scope.showMensajeBoletoOrigenNoExiste = false;
                            $scope.showBoletoOrigenExiste = false;
                        }
                    }
                });

                $scope.$watch('formData.fechaEmision', function (now, old) {
                    if (now === undefined) {
                    } else {
                        if (now !== old) {

                            if ($scope.formData.formaPago === $scope.CREDITO) {
                                $scope.setCreditoVencimiento();
                            }
                        }
                    }
                });


                $scope.$watch('boleto.idAerolinea.id', function (now, old) {
                    if (now === undefined) {
                        $scope.showErrorCtaDevolucionMonExt = true;
                    } else {
                        if ($scope.boleto.estado === $scope.CREADO) {
                            $http.get(`${urlAerolinea.value}get/${$scope.boleto.idAerolinea.id}`).then(
                                    function (response) {
                                        if (response.data.code === 201) {
                                            $scope.aerolinea = response.data.content;
                                            //cargamos los datos de la aerolinea
                                            if ($scope.boleto.tipoBoleto === $scope.MANUAL) {
                                                if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                                                    $scope.boleto.tipoCupon = $scope.INTERNACIONAL;
                                                    $scope.boleto.moneda = $scope.MONEDA_EXTRANJERA;
                                                } else if ($scope.aerolinea.moneda === $scope.MONEDA_NACIONAL) {
                                                    $scope.boleto.tipoCupon = $scope.NACIONAL;
                                                    $scope.boleto.moneda = $scope.MONEDA_NACIONAL;
                                                } else {
                                                    $scope.showAlert("Error", "La Aerolinea seleccionada, no tiene configurada una moneda preferencial");
                                                }

                                                if ($scope.boleto.tipoCupon === $scope.INTERNACIONAL) {
                                                    $scope.boleto.comision = $scope.aerolinea.comisionPromInt;
                                                } else if ($scope.boleto.tipoCupon === $scope.NACIONAL) {
                                                    $scope.boleto.comision = $scope.aerolinea.comisionPromNac;
                                                }
                                                $scope.calculateComision();
                                                $scope.getImpuestos($scope.aerolinea);
                                            } else if ($scope.boleto.tipoBoleto === $scope.MANUAL_VOID) {
                                                if ($scope.aerolinea.moneda === $scope.MONEDA_EXTRANJERA) {
                                                    $scope.boleto.tipoCupon = $scope.INTERNACIONAL;
                                                    $scope.boleto.moneda = $scope.MONEDA_EXTRANJERA;
                                                } else if ($scope.aerolinea.moneda === $scope.MONEDA_NACIONAL) {
                                                    $scope.boleto.tipoCupon = $scope.NACIONAL;
                                                    $scope.boleto.moneda = $scope.MONEDA_NACIONAL;
                                                } else {
                                                    $scope.showAlert("Error", "La Aerolinea seleccionada, no tiene configurada una moneda preferencial");
                                                }
                                            }
                                            $scope.redondearDecimales = $scope.aerolinea.roundComisionBob ? true : false;

                                        }
                                    },
                                    $scope.errorFunction
                                    );
                        } else {
                            $http.get(`${urlAerolinea.value}get/${$scope.boleto.idAerolinea.id}`).then(
                                    function (response) {
                                        if (response.data.code === 201) {
                                            $scope.aerolinea = response.data.content;
                                            $scope.redondearDecimales = $scope.aerolinea.roundComisionBob ? true : false;
                                        }
                                    },
                                    $scope.errorFunction
                                    );
                        }
                    }
                });

            }
        ]);
app.filter('utcToDate', function ($filter) {
    return function (input, predicate) {
        if (input != undefined) {
            var date = input.substring(0, 10);
            date = moment(date, 'yyyy-mm-dd').format('DD/mm/YYYY');
            return date;
        }
        return "";
    }
})

app.filter('utcToDateTime', function ($filter) {
    return function (input, predicate) {
        if (input != undefined) {
            var date = input.substring(0, 20);
            date = moment(date).format('DD/mm/YYYY HH:MM:ss');

            return date;
        }
        return "";
    }
})

app.filter('utcToDate', function ($filter) {
    return function (input, predicate) {
        if (input != undefined) {
            var date = input.substring(0, 10);
            date = moment(date, 'yyyy-mm-dd').format('DD/mm/YYYY');
            input = date;
            return date;
        }
        return "";
    }
})

app.filter('printTipoBoleto', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'AM':
                return 'AMADEUS';
            case 'AV' :
                return 'AMADEUS VOID';
            case 'SA' :
                return 'SABRE';
            case 'SV' :
                return 'SABRE VOID';
            case 'MA' :
                return 'MANUAL';
            case 'MV' :
                return 'MANUAL VOID';
            default :
                return '-';
                break;
        }
    }
});

app.filter('printEstado', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E' :
                return 'EMITIDO';
            case 'P' :
                return 'PENDIENTE';
            case 'A' :
                return 'ANULADO';
            case 'C' :
                return 'CREADO';
            case 'M' :
                return 'EN MORA';
            case 'D' :
                return 'CANCELADO';
            default :
                return '-';
                break;
        }
    }
});
app.filter('printMoneda', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'B':
                return 'BOB.';
            case 'U' :
                return 'USD.';
            default :
                return '-';
                break;
        }
    }
});
app.filter('printTipo', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'B':
                return 'BOLETO';
            case 'P' :
                return 'PAQUETE';
            case 'A' :
                return 'ALQUILER AUTO';
            case 'H' :
                return 'HOTEL';
            case 'S' :
                return 'SEGURO';
            case 'R' :
                return 'RESERVA';
            default :
                return '-';
                break;
        }
    }
});
app.filter('printTipoCupon', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'N':
                return 'NACIONAL';
            case 'I' :
                return 'INTERNACIONAL';
            default :
                return '-';
                break;
        }
    }
});

app.filter('printTipoComprobante', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'CI':
                return 'Comprobante de Ingreso';
            case 'AD' :
                return 'Asiento Diario';
            case 'CE' :
                return 'Comprobante de Egreso';
            case 'AJ' :
                return 'Asiento de Ajuste';
            case 'CT' :
                return 'Comprobante de Traspaso';
            default :
                return 'SIN TIPO';
                break;
        }
    }
});
app.filter('printFormaPago', function ($filter) {
    return function (input, predicate) {
        switch (input) {
            case 'E':
                return 'EFECTIVO';
            case 'C' :
                return 'CREDITO';
            case 'H' :
                return 'CHEQUE';
            case 'T' :
                return 'TARJETA';
            default :
                return '-';
                break;
        }
    }
});


app.filter('printNumber', function ($filter) {
    return function (input, predicate) {
        if (input === undefined || input === null || input === 0 || input === 0.00)
            return '-';
        else {
            var value = new Number(input).toFixed(2);
            return  value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
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

