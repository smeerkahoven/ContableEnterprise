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
                            $scope.getCuentas();
                            $scope.loading = false;
                        } else {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulError = true;
                            return {};
                        }
                    }, function (error) {
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        goScrollToSuccess();
                    });
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
                    $scope.loading = true;
                    saveData.emisionBolivianos = $scope.formData.emisionBolivianos ? SI : NO;
                    saveData.emisionDolares = $scope.formData.emisionDolares ? SI : NO;

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
                        goScrollToSuccess();
                    });
                };

                $scope.getComboEmpresas = function () {
                    $scope.loading = true;
                    return $http({
                        method: 'POST',
                        url: `${urlEmpresa.value}all-combo`,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.comboEmpresa = response.data.content;
                            if ($scope.comboEmpresa.length > 0){
                                $scope.formData.idEmpresa = $scope.comboEmpresa[0].id;
                                $scope.getData();
                            }
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
                            $scope.formData.idTotalBoletoUs = $scope.findCta($scope.formData.idTotalBoletoUs, $scope.comboCuentas);
                            $scope.formData.idCuentaFee = $scope.findCta($scope.formData.idCuentaFee, $scope.comboCuentas);
                            $scope.formData.idDescuentos = $scope.findCta($scope.formData.idDescuentos, $scope.comboCuentas);

                            $scope.formData.cuentaEfectivoDebeBs = $scope.findCta($scope.formData.cuentaEfectivoDebeBs, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoHaberBs = $scope.findCta($scope.formData.cuentaEfectivoHaberBs, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoDebeUsd = $scope.findCta($scope.formData.cuentaEfectivoDebeUsd, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoHaberUsd = $scope.findCta($scope.formData.cuentaEfectivoHaberUsd, $scope.comboCuentas);

                            $scope.formData.depositoBancoHaberBs = $scope.findCta($scope.formData.depositoBancoHaberBs, $scope.comboCuentas);
                            $scope.formData.depositoBancoDebeBs = $scope.findCta($scope.formData.depositoBancoDebeBs, $scope.comboCuentas);
                            $scope.formData.depositoBancoHaberUsd = $scope.findCta($scope.formData.depositoBancoHaberUsd, $scope.comboCuentas);
                            $scope.formData.depositoBancoDebeUsd = $scope.findCta($scope.formData.depositoBancoDebeUsd, $scope.comboCuentas);

                            $scope.formData.cuentaEfectivoNoBspDebeBs = $scope.findCta($scope.formData.cuentaEfectivoNoBspDebeBs, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoNoBspHaberBs = $scope.findCta($scope.formData.cuentaEfectivoNoBspHaberBs, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoNoBspDebeUsd = $scope.findCta($scope.formData.cuentaEfectivoNoBspDebeUsd, $scope.comboCuentas);
                            $scope.formData.cuentaEfectivoNoBspHaberUsd = $scope.findCta($scope.formData.cuentaEfectivoNoBspHaberUsd, $scope.comboCuentas);
                            $scope.formData.tarjetaCreditoBspDebeBs = $scope.findCta($scope.formData.tarjetaCreditoBspDebeBs, $scope.comboCuentas);
                            $scope.formData.tarjetaCreditoBspHaberBs = $scope.findCta($scope.formData.tarjetaCreditoBspHaberBs, $scope.comboCuentas);
                            $scope.formData.tarjetaCreditoBspDebeUsd = $scope.findCta($scope.formData.tarjetaCreditoBspDebeUsd, $scope.comboCuentas);
                            $scope.formData.tarjetaCreditoBspHaberUsd = $scope.findCta($scope.formData.tarjetaCreditoBspHaberUsd, $scope.comboCuentas);
                            //Paquetes y Otros
                            $scope.formData.otrosCargosClienteCobrarDebeBs =  $scope.findCta($scope.formData.otrosCargosClienteCobrarDebeBs, $scope.comboCuentas);
                            $scope.formData.otrosCargosClienteCobrarDebeUsd =  $scope.findCta($scope.formData.otrosCargosClienteCobrarDebeUsd, $scope.comboCuentas);

                            //Nota CRedito 
                            $scope.formData.notaCreditoHaberBs =  $scope.findCta($scope.formData.notaCreditoHaberBs, $scope.comboCuentas);
                            $scope.formData.notaCreditoHaberUsd =  $scope.findCta($scope.formData.notaCreditoHaberUsd, $scope.comboCuentas);
                            
                            //Depositos Anticipados
                            $scope.formData.depositoClienteAnticipadoBs =  $scope.findCta($scope.formData.depositoClienteAnticipadoBs, $scope.comboCuentas);
                            $scope.formData.depositoClienteAnticipadoUsd =  $scope.findCta($scope.formData.depositoClienteAnticipadoUsd, $scope.comboCuentas);
                            
                            //Acreditacion
                            $scope.formData.acreditacionDepositoAnticipadoDebeBs =  $scope.findCta($scope.formData.acreditacionDepositoAnticipadoDebeBs, $scope.comboCuentas);
                            $scope.formData.acreditacionDepositoAnticipadoHaberBs =  $scope.findCta($scope.formData.acreditacionDepositoAnticipadoHaberBs, $scope.comboCuentas);
                            $scope.formData.acreditacionDepositoAnticipadoDebeUsd =  $scope.findCta($scope.formData.acreditacionDepositoAnticipadoDebeUsd, $scope.comboCuentas);
                            $scope.formData.acreditacionDepositoAnticipadoHaberUsd =  $scope.findCta($scope.formData.acreditacionDepositoAnticipadoHaberUsd, $scope.comboCuentas);
                            
                            //Devolucion
                            $scope.formData.devolucionDepositoAnticipadoDebeBs =  $scope.findCta($scope.formData.devolucionDepositoAnticipadoDebeBs, $scope.comboCuentas);
                            $scope.formData.devolucionDepositoAnticipadoHaberBs =  $scope.findCta($scope.formData.devolucionDepositoAnticipadoHaberBs, $scope.comboCuentas);
                            $scope.formData.devolucionDepositoAnticipadoDebeUsd =  $scope.findCta($scope.formData.devolucionDepositoAnticipadoDebeUsd, $scope.comboCuentas);
                            $scope.formData.devolucionDepositoAnticipadoHaberUsd =  $scope.findCta($scope.formData.devolucionDepositoAnticipadoHaberUsd, $scope.comboCuentas);
                            
                            $scope.formData.diferenciaCambio = $scope.findCta($scope.formData.diferenciaCambio, $scope.comboCuentas);
                            
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

                    //Formas de Pago
                    if ($scope.formData.cuentaEfectivoNoBspDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.cuentaEfectivoNoBspDebeBs.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.cuentaEfectivoNoBspHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.cuentaEfectivoNoBspHaberBs.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.cuentaEfectivoNoBspDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.cuentaEfectivoNoBspDebeUsd.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.cuentaEfectivoNoBspHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.cuentaEfectivoNoBspHaberUsd.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.tarjetaCreditoBspDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.tarjetaCreditoBspDebeBs.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.tarjetaCreditoBspHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.tarjetaCreditoBspHaberBs.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.tarjetaCreditoBspDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.tarjetaCreditoBspDebeUsd.id === undefined) {
                            return true;
                        }
                    }

                    if ($scope.formData.tarjetaCreditoBspHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.tarjetaCreditoBspHaberUsd.id === undefined) {
                            return true;
                        }
                    }
                    
                    //Deposito Bancario Sin o Con BSP
                    if ($scope.formData.depositoBancoDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.depositoBancoDebeBs.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.depositoBancoHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.depositoBancoHaberBs.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.depositoBancoDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.depositoBancoDebeUsd.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.depositoBancoHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.depositoBancoHaberUsd.id === undefined) {
                            return true;
                        }
                    }

                    //PAQUETES y OTROS
                    //Cuenta Clientes x Cobrar
                    if ($scope.formData.otrosCargosClienteCobrarDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.otrosCargosClienteCobrarDebeBs.id === undefined) {
                            return true;
                        }
                    }
                    
                    if ($scope.formData.otrosCargosClienteCobrarDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.otrosCargosClienteCobrarDebeUsd.id === undefined) {
                            return true;
                        }
                    }
                    
                    //NOTA CREDITO
                    //Deposito Cliente
                    if ($scope.formData.notaCreditoHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.notaCreditoHaberBs.id === undefined) {
                            return true;
                        }
                    }
                    
                    if ($scope.formData.notaCreditoHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.notaCreditoHaberUsd.id === undefined) {
                            return true;
                        }
                    }
                    
                    //DEPOSITOS ANTICIPADOS
                    //Deposito Cliente Anticipado
                    if ($scope.formData.depositoClienteAnticipadoBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.depositoClienteAnticipadoBs.id === undefined) {
                            return true;
                        }
                    }
                    //Acreditacion Deposito Cliente bs
                    if ($scope.formData.acreditacionDepositoAnticipadoDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.acreditacionDepositoAnticipadoDebeBs.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.acreditacionDepositoAnticipadoHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.acreditacionDepositoAnticipadoHaberBs.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.acreditacionDepositoAnticipadoDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.acreditacionDepositoAnticipadoDebeUsd.id === undefined) {
                            return true;
                        }
                    }
                    if ($scope.formData.acreditacionDepositoAnticipadoHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.acreditacionDepositoAnticipadoHaberUsd.id === undefined) {
                            return true;
                        }
                    }
                    
                    //DEvolucion Deposito Cliente Usd
                     if ($scope.formData.devolucionDepositoAnticipadoDebeBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.devolucionDepositoAnticipadoDebeBs.id === undefined) {
                            return true;
                        }
                    }
                     if ($scope.formData.devolucionDepositoAnticipadoHaberBs === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.devolucionDepositoAnticipadoHaberBs.id === undefined) {
                            return true;
                        }
                    }
                     if ($scope.formData.devolucionDepositoAnticipadoDebeUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.devolucionDepositoAnticipadoDebeUsd.id === undefined) {
                            return true;
                        }
                    }
                     if ($scope.formData.devolucionDepositoAnticipadoHaberUsd === undefined) {
                        return true;
                    } else {
                        if ($scope.formData.devolucionDepositoAnticipadoHaberUsd.id === undefined) {
                            return true;
                        }
                    }
                    
                    //diferencia cambio
                    if ($scope.formData.diferenciaCambio === undefined) {
                        return true ;
                    }else {
                        if ($scope.formData.diferenciaCambio.id === undefined){
                            return true ;
                        }
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
                        goScrollToSuccess();

                    }, function (error) {
                        $scope.loading = false;
                        $scope.showRestfulMessage = error.statusText;
                        $scope.showRestfulError = true;
                        goScrollToSuccess();
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
                    //$scope.showForm = false;
                    //$scope.showTable = true;
                    $scope.formData.idEmpresa = undefined;
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
                    if (cta.id !== undefined) {
                        for (i; i < input.length; i++) {
                            if (input[i].id === cta.id) {
                                return input[i];
                            }
                        }
                    }
                }

                $scope.getComboEmpresas();
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

