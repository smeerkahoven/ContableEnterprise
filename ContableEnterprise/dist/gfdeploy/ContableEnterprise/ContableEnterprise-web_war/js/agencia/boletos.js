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
let BOLIVIANO= 'B';

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
    this.comision = null ;
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

    this.contadoNroTarjeta = null;
    this.contadoIdTarjeta = null;
    this.contadoNroCheque = null;
    this.contadoIdBanco = null;
    this.contadoNroDeposito = null;

}

Boleto.prototype = Object.create(Boleto.prototype);
Boleto.prototype.constructor = Boleto;

let date = new Date();
let firstDay = new Date(date.getFullYear(), date.getMonth(), 1).toLocaleDateString();
let today = new Date().toLocaleDateString();

var app = angular.module("jsBoletos", ['jsBoletos.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsBoletos.controllers', []).controller('frmBoletos',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlEmpresa = document.getElementsByName("hdUrlEmpresa")[0];
                var urlAerolinea = document.getElementsByName("hdUrlAerolinea")[0];
                var urlAeropuertos = document.getElementsByName("hdUrlAeropuertos")[0];
                var urlClientes = document.getElementsByName("hdUrlClientes")[0];
                var urlFactores = document.getElementsByName("hdUrlFactores")[0];
                var factorMaxMin = document.getElementsByName("hdFactorMaxMin")[0];

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

                $scope.getImpuestos = function(aerolinea){
                    var data = {idAerolinea: aerolinea.idAerolinea};
                    return $http({
                        url: `${urlAerolinea.value}all-impuestos`,
                        method: 'POST',
                        data: {token: token.value, content : angular.toJson(data)},
                        headers: {'Content-type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.aerolineaImpuestos = response.data.content;
                            console.log($scope.aerolineaImpuestos);
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
                
                $scope.calculateTotalBoleto = function(){
                    if ($scope.aerolinea.moneda === DOLAR){
                        /*if ($scope.formData.importeNetoUsd === undefined){$scope.formData.importeNetoUsd =0};
                        if ($scope.formData.impuestoBobUsd === undefined){$scope.formData.impuestoBobUsd =0};
                        if ($scope.formData.impuestoQmUsd === undefined){$scope.formData.impuestoQmUsd =0};*/
                        
                        /*if ($scope.aerolineaImpuestos.length > 0){
                            for(var e in $scope.aerolineaImpuestos){
                               if ($scope.aerolineaImpuestos[e].valorImpuestoUsd === undefined){
                                   $scope.aerolineaImpuestos[e].valorImpuestoUsd =0 ;
                               };
                            }
                        }*/
                        
                        /*Total Boleto*/
                        var totalUsd = Number(0) ;
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.formData.importeNetoUsd === null ? 0 :$scope.formData.importeNetoUsd).toFixed(2)) ;
                        //console.log($scope.formData.importeNetoUsd);
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.formData.impuestoBobUsd === null ? 0 :$scope.formData.impuestoBobUsd).toFixed(2)) ;
                        //console.log($scope.formData.impuestoBobUsd );
                        totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.formData.impuestoQmUsd === null ? 0 : $scope.formData.impuestoQmUsd).toFixed(2)) ;
                        //console.log(totalUsd);
                        if ($scope.aerolineaImpuestos.length > 0){
                            for(var e in $scope.aerolineaImpuestos){
                                if ($scope.aerolineaImpuestos[e].valorImpuestoUsd !== undefined){
                                totalUsd = Number(parseFloat(totalUsd)) + Number(parseFloat($scope.aerolineaImpuestos[e].valorImpuestoUsd === null ? 0 :$scope.aerolineaImpuestos[e].valorImpuestoUsd).toFixed(2)) ;
                                console.log($scope.aerolineaImpuestos[e].valorImpuestoUsd);
                            }
                            }
                        }
                        
                        //console.log(totalUsd);
                        $scope.formData.totalBoletoUsd = parseFloat((totalUsd *100)/100).toFixed(2);
                        
                        /*total a Cancelar*/
                        var totalCancelUsd = Number(0);
                        
                        if ($scope.formData.comision !== null){
                            $scope.formData.comisionUsd = Number(parseFloat())
                        }
                        
                        totalCancelUsd = Number(parseFloat(totalCancelUsd)) + Number(parseFloat($scope.formData.comisionUsd === null ? 0 :$scope.formData.comisionUsd).toFixed(2)) ;
                        totalCancelUsd = Number(parseFloat(totalCancelUsd)) + Number(parseFloat($scope.formData.montoFeeUsd === null ? 0 :$scope.formData.montoFeeUsd).toFixed(2)) ;
                        totalCancelUsd = Number(parseFloat(totalCancelUsd)) + Number(parseFloat($scope.formData.montoDescuentoUsd === null ? 0 :$scope.formData.montoDescuentoUsd).toFixed(2)) ;
                        
                        $scope.formData.totalCancelUsd = parseFloat((totalCancelUsd*100)/100) + parseFloat($scope.formData.totalBoletoUsd);
                        //se transforma la info de la moneda actual a bs
                        $scope.formData.importeNetoBs = $scope.formData.importeNetoUsd === null? undefined :  Number(parseFloat($scope.formData.importeNetoUsd * $scope.formData.factorCambiario).toFixed(2) );
                        $scope.formData.impuestoBobBs = $scope.formData.impuestoBobUsd === null ? undefined :  Number(parseFloat($scope.formData.impuestoBobUsd * $scope.formData.factorCambiario).toFixed(2) );
                        $scope.formData.impuestoQmBs = $scope.formData.impuestoQmUsd === null ? undefined :  Number(parseFloat($scope.formData.impuestoQmUsd * $scope.formData.factorCambiario).toFixed(2)) ;
                        
                    }else if ($scope.aerolinea.moneda ===BOLIVIANO){
                        
                    }
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
                $scope.getFactorCambiario();

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
                                        
                                        if ($scope.formData.tipoCupon === DOLAR){
                                            $scope.formData.comision = $scope.aerolinea.comisionPromInt ;
                                        }else if ($scope.formData.tipoCupon === BOLIVIANO){
                                            $scope.formData.comision = $scope.aerolinea.comisionPromNac ;
                                        }
                                        
                                        console.log($scope.aerolinea);
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

