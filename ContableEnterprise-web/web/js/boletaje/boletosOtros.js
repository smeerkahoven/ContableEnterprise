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

function NotaDebito() {
    this.idNotaDebito = null;
    this.gestion = null;
    this.idEmpresa = null;
    this.idCliente = null;
    this.idCounter = null;
    this.fechaEmision = null;
    this.fechaInsert = null;
    this.montoTotalBs = null;
    this.montoTotalUsd = null;
    this.montoAdeudadoUsd = null;
    this.montoAdeudadoBs = null;
    this.factorCambiario = null;
    this.formaPago = null;
    this.tipoContado = null;
    this.idBanco = null;
    this.nroCheque = null;
    this.idTarjetaCredito = null;
    this.nroTarjeta = null;
    this.nroDeposito = null;
    this.idCuentaDeposito = null;
    this.idUsuarioCreador = null;
    this.creditoDias = null;
    this.creditoVencimiento = null;
    this.combinadoContado = null;
    this.combinadoTarjeta = null;
    this.combinadoCredito = null;
    this.combinadoContadoTipo = null;
    this.estado = null;
    this.transacciones = null;
}

NotaDebito.prototype = Object.create(NotaDebito.prototype);
NotaDebito.prototype.constructor = NotaDebito;

Boleto.prototype = Object.create(Boleto.prototype);
Boleto.prototype.constructor = Boleto;

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
                var idEmpresa = document.getElementsByName("idEmpresa")[0].value;

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.MANUAL = 'MA';
                $scope.VOID = 'MV';
                $scope.SABRE = 'SA';
                $scope.AMADEUS = 'AM';
                $scope.PAQUETE = 'PQ';
                $scope.SEGURO = 'SE';
                $scope.ALQUILER = 'AL';

                $scope.PENDIENTE = 'P';
                $scope.EMITIDO = 'E';
                $scope.ANULADO = 'A';

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = null;
                $scope.modalConfirmation = {};

                $scope.showForm = false;
                $scope.showTable = true;

                $scope.search = {fechaInicio: firstDay, fechaFin: today};

                $scope.itemsByPage = 15;

                /*
                 * Obtencion de Datos
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
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                        }
                    }, function (error) {
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
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
                            },
                            function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            });

                    //$scope.formData.idEmpresa = $scope.findCta(item.idEmpresa, $scope.comboSucursales);
                }

                $scope.editTransaccion = function (row){
                    console.log(row) ;
                }
                
                $scope.nuevo = function () {
                    $scope.showLoading = true;

                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;
                    $scope.formData = new NotaDebito();
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
                                    $scope.formData.factorCambiario = $scope.dollarToday.valor;
                                } else {
                                    $scope.showRestfulError = true;
                                    $scope.showRestfulMessage = response.data.content;
                                }
                            }, function (error) {
                        $scope.showRestfulError = true;
                        $scope.showRestfulMessage = error.statusText;

                    });
                }

                $scope.newBoleto = function (tipo) {
                    switch (tipo) {
                        case $scope.MANUAL:
                            break;
                        case $scope.MANUAL_VOID :
                            break;
                        case $scope.SABRE :
                            break;
                        case $scope.AMADEUS :
                            $scope.showFormAmadeus();
                            break;
                    }
                }

                $scope.showFormAmadeus = function () {
                    showModalWindow('#frmAmadeus');

                    $scope.loadingFrmAmadeus = true;
                    $scope.showTableAmadeus = false;

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
                            function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.data.content;
                            });

                }

                $scope.loadBoletos = function () {
                    var data = [];
                    for (var i in $scope.amadeusGrid) {
                        if ($scope.amadeusGrid[i].selected) {
                            data.push($scope.amadeusGrid[i].idBoleto);
                        }
                    }

                    if (data.length > 0) {
                        var asociacion = {
                            idNotaDebito: $scope.formData.idNotaDebito,
                            idCliente: $scope.formData.idCliente.id,
                            factor : $scope.formData.factorCambiario ,
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
                                $scope.formData.montoTotalUsd = nota.montoTotaUsd;

                                $scope.loadTransacciones();
                            } else {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = response.data.content;
                            }
                            hidModalWindow("#frmAmadeus");

                        }, function (error) {
                            $scope.showRestfulError = true;
                            $scope.showRestfulMessage = error.statusText;
                            hidModalWindow("#frmAmadeus");
                        });
                    }
                }

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
                            function (error) {
                                $scope.showRestfulError = true;
                                $scope.showRestfulMessage = error.statusText;
                            }
                    );
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

                $scope.getClientesPasajeros = function (idCliente) {
                    return $http.get(`${url.value}cliente-pasajeros/${idCliente}`, )
                            .then(function (response) {
                                if (response.data.code === 201) {
                                    $scope.comboClientePasajeros = response.data.content;
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
                $scope.getClientes();
                $scope.getAeropuertos();

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
            case 'C' :
                return 'CREADO';
            default :
                return 'SIN ESTADO';
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
                return 'SIN MONEDA';
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
                return 'SIN TIPO';
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
                return 'SIN TIPO';
                break;
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

