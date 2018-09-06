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
let  NETO ='N';
let TOTAL = 'T' ;
function Aerolinea(){
    this.idAerolinea = null;
    this.numero =null ;
    this.iata=null;
    this.nit = null ;
    this.emitirFacturaA= null;
    this.nombre = null ;
    this.representante=null;
    this.direccion= null ;
    this.telefono=null;
    this.celular=null;
    this.email=null;
    this.personaContacto=null;
    this.bsp=null ;
    this.comisionPromInt=0;
    this.comisionPromIntTipo=NETO;
    this.comisionPromNac=0;
    this.comisionPromNacTipo=NETO;
    this.roundComisionBob=false ;
    this.roundComisionUsd=false;
    this.boletosMonNac=false;
    this.boletosMonExt=false;
    this.moneda=null;
    this.impuestoValorNeto=true;
    this.impuestoQm=true;
    this.registraPnr=true;
    this.bsp=true;
}

Aerolinea.prototype = Object.create(Aerolinea.prototype);
Aerolinea.prototype.constructor = Aerolinea;

var app = angular.module("jsAerolinea", ['jsAerolinea.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsAerolinea.controllers', []).controller('frmAerolinea',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {
                //var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTUyNzg0MjcwNCwiaWF0IjoxNTI3Nzk5NTA0LCJ2YWx1ZSI6ImFkbWluIn0.20_NgML0xEVr3W9R7RuZOxqno1cLk7cMq5bOtNDLZGQ";//document.getElementsByName("hdToken")[0];
                //var url = "http://localhost:8080/ContableEnterprise-ws/ws-api/roles/form-perm" //document.getElementsByName("hdUrl")[0];

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var formName = document.getElementsByName("hdFormName")[0];
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
                    $scope.showBtnNuevo = false;
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
                        url: url.value + 'save',
                        data: {token: token.value, content: angular.toJson($scope.formData) , formName : formName.value},
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

                    if ($scope.formData.otrosImpuestos === undefined) {
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
                        data: {token: token.value, content: angular.toJson(data) , formName : formName.value},
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
                    if ($scope.formData.otrosImpuestos === undefined) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    if ($scope.formData.otrosImpuestos.length < 5) {
                        $scope.showErrorOtrosImpuestos = true;
                        return;
                    }

                    if ($scope.formData.aerolineaImpuestoList === undefined) {
                        $scope.formData.aerolineaImpuestoList = [];
                    }
                    $scope.formData.aerolineaImpuestoList.push({nombre: $scope.formData.otrosImpuestos});

                    $scope.formData.otrosImpuestos = '';
                    $scope.showErrorOtrosImpuestos = false;
                }

                $scope.formHasError = function () {
                    return (
                            (!$scope.formData.boletosMonNac && !$scope.formData.boletosMonExt));

                }

                $scope.showAlert = function (title, message) {
                    swal({
                        title: title,
                        text: message,
                        type: 'error',
                        closeOnConfirm: true
                    });
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

                    $http({
                        method: 'POST',
                        url: url.value + 'update',
                        data: {token: token.value, content: angular.toJson($scope.formData) , formName : formName.value},
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

                $scope.addCuentaVenta = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaVentasMonNac.id,
                        nombreCuenta: $scope.formData.ctaVentasMonNac.name
                    }

                    if ($scope.formData.listCtaVentasMonNac === undefined) {
                        $scope.formData.listCtaVentasMonNac = [];
                        $scope.formData.listCtaVentasMonNac.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaVentasMonNac) {
                            if ($scope.formData.listCtaVentasMonNac[i].idPlanCuentas ===
                                    $scope.formData.ctaVentasMonNac.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }
                            $scope.formData.listCtaVentasMonNac.push(cuenta);
                        }
                    }
                    $scope.formData.ctaVentasMonNac = '';
                }

                $scope.addCuentaVentaExt = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaVentasMonExt.id,
                        nombreCuenta: $scope.formData.ctaVentasMonExt.name
                    }

                    if ($scope.formData.listCtaVentasMonExt === undefined) {
                        $scope.formData.listCtaVentasMonExt = [];
                        $scope.formData.listCtaVentasMonExt.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaVentasMonExt) {
                            if ($scope.formData.listCtaVentasMonExt[i].idPlanCuentas ===
                                    $scope.formData.ctaVentasMonExt.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }
                            $scope.formData.listCtaVentasMonExt.push(cuenta);
                        }
                    }
                    $scope.formData.ctaVentasMonExt = '';
                }

                $scope.addCuentaComisionNac = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaComisionMonNac.id,
                        nombreCuenta: $scope.formData.ctaComisionMonNac.name
                    }

                    if ($scope.formData.listCtaComisionMonNac === undefined) {
                        $scope.formData.listCtaComisionMonNac = [];
                        $scope.formData.listCtaComisionMonNac.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaComisionMonNac) {
                            if ($scope.formData.listCtaComisionMonNac[i].idPlanCuentas ===
                                    $scope.formData.listCtaComisionMonNac.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }

                            $scope.formData.listCtaComisionMonNac.push(cuenta);
                        }
                    }
                    $scope.formData.ctaComisionMonNac = '';
                }

                $scope.addCuentaComisionExt = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaComisionMonExt.id,
                        nombreCuenta: $scope.formData.ctaComisionMonExt.name
                    }

                    if ($scope.formData.listCtaComisionMonExt === undefined) {
                        $scope.formData.listCtaComisionMonExt = [];
                        $scope.formData.listCtaComisionMonExt.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaComisionMonExt) {
                            if ($scope.formData.listCtaComisionMonExt[i].idPlanCuentas ===
                                    $scope.formData.ctaComisionMonExt.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }
                            $scope.formData.listCtaComisionMonExt.push(cuenta);
                        }
                    }
                    $scope.formData.ctaComisionMonExt = '';
                }

                $scope.addCuentaDevolucionNac = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaDevolucionMonNac.id,
                        nombreCuenta: $scope.formData.ctaDevolucionMonNac.name
                    }

                    if ($scope.formData.listCtaDevolucionMonNac === undefined) {
                        $scope.formData.listCtaDevolucionMonNac = [];
                        $scope.formData.listCtaDevolucionMonNac.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaDevolucionMonNac) {
                            if ($scope.formData.listCtaDevolucionMonNac[i].idPlanCuentas ===
                                    $scope.formData.listCtaDevolucionMonNac.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }

                            $scope.formData.listCtaDevolucionMonNac.push(cuenta);
                        }
                    }
                    $scope.formData.ctaDevolucionMonNac = '';
                }

                $scope.addCuentaDevolucionExt = function (p_tipo, p_moneda) {
                    var cuenta = {
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaDevolucionMonExt.id,
                        nombreCuenta: $scope.formData.ctaDevolucionMonExt.name
                    }

                    if ($scope.formData.listCtaDevolucionMonExt === undefined) {
                        $scope.formData.listCtaDevolucionMonExt = [];
                        $scope.formData.listCtaDevolucionMonExt.push(cuenta);
                    } else {

                        for (var i in $scope.formData.listCtaDevolucionMonExt) {
                            if ($scope.formData.listCtaDevolucionMonExt[i].idPlanCuentas ===
                                    $scope.formData.ctaDevolucionMonExt.id) {
                                $scope.showAlert("Error", "El registro ya existe. Ingrese otro valor");
                                return;
                            }
                            $scope.formData.listCtaDevolucionMonExt.push(cuenta);
                        }
                    }
                    $scope.formData.ctaDevolucionMonExt = '';
                }

                $scope.updateAddCuentaVenta = function (p_tipo, p_moneda) {
                    $scope.loadingAddCuentaVenta = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: $scope.formData.ctaVentasMonNac.id
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data) , formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaVentasMonNac = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaVenta = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddCuentaVentaExt = function (p_planCuentas, p_tipo, p_moneda) {
                    $scope.loadingAddCuentaVentaExt = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: p_planCuentas.id
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data), formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaVentasMonExt = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaVentaExt = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddCuentaComisionNac = function (p_planCuentas,p_tipo, p_moneda) {
                    $scope.loadingAddCuentaComisionNac = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: p_planCuentas.id
                    }
                    
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data) ,formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaComisionMonNac = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaComisionNac = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddCuentaComisionExt = function (p_planCuentas, p_tipo, p_moneda) {
                    $scope.loadingAddCuentaComisionExt = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: p_planCuentas.id
                    }
                                        
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data) ,formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaComisionMonExt = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaComisionExt = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddCuentaDevolucionNac = function (p_planCuentas,p_tipo, p_moneda) {
                    $scope.loadingAddCuentaDevolucionNac = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: p_planCuentas.id
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data) ,formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaDevolucionMonNac = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaDevolucionNac = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.updateAddCuentaDevolucionExt = function (p_planCuentas, p_tipo, p_moneda) {
                    $scope.loadingAddCuentaDevolucionExt = true;
                    var data = {
                        idAerolinea: $scope.formData.idAerolinea,
                        tipo: p_tipo,
                        moneda: p_moneda,
                        idPlanCuentas: p_planCuentas.id
                    }
                    return $http({
                        method: 'POST',
                        url: url.value + 'save-cuenta',
                        data: {token: token.value, content: angular.toJson(data) ,formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formData.ctaDevolucionMonExt = '';
                        } else if (response.data.code === 200) {
                            $scope.showAlert("Error", response.data.content);
                        }
                        $scope.loadingAddCuentaDevolucionExt = false;
                    }, function (error) {
                        $scope.showRestfulMessage = error;
                        $scope.showRestfulError = true;
                    });
                }

                $scope.showAddCuentaVenta = function (list, formName) {
                    $scope.formAerolineaCuentaList = list;
                    $scope.frmFormAerolineaCuentaName = formName;
                }

                $scope.showUpdateAddCuenta = function (formName, tipo, moneda) {
                    $scope.frmFormAerolineaCuentaName = formName;
                    $scope.frmFormAerolineaCuentaTipo = tipo;
                    $scope.frmFormAerolineaMoneda = moneda;
                    $scope.linkLoading = true;
                    $scope.showLinkRestfulError = false;
                    $scope.showLinkRestfulSuccess = false;

                    var urlComision = url.value + 'get-cuentas/' + $scope.formData.idAerolinea + "/" + tipo + '/' + moneda;
                    return $http({
                        method: 'POST',
                        url: urlComision,
                        data: {token: token.value, content: ''},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        if (response.data.code === 201) {
                            $scope.formAerolineaCuentaList = response.data.content;
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

                $scope.delete = function (method) {
                    $scope.loading = true;

                    if ($scope.modalConfirmation.method === 'impuesto-delete') {
                        var data = {idAerolineaImpuesto: $scope.modalConfirmation.id};

                    } else if ($scope.modalConfirmation.method === 'delete-cuenta') {
                        var data = {idAerolineaCuenta: $scope.modalConfirmation.id};
                    } else {
                        var data = {idAerolinea: $scope.modalConfirmation.id};
                    }
                    $http({
                        method: 'POST',
                        url: url.value + method,
                        data: {token: token.value, content: angular.toJson(data),formName : formName.value},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        $scope.loading = false;
                        if (response.data.code === 201) {
                            $scope.showRestfulMessage = response.data.content;
                            $scope.showRestfulSuccess = true;

                            if ($scope.modalConfirmation.method === 'impuesto-delete') {
                                $scope.getImpuestos();
                            } else if ($scope.modalConfirmation.method === 'delete-cuenta') {
                                $scope.showUpdateAddCuenta($scope.frmFormAerolineaCuentaName,
                                        $scope.frmFormAerolineaCuentaTipo,
                                        $scope.frmFormAerolineaMoneda)
                            } else {
                                $scope.getData('all', $scope.data);
                            }

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
                    $scope.showBtnEditar = true;
                    $scope.showRestfulSuccess = false;
                    $scope.showRestfulError = false;

                    $scope.formData = item;
                    console.log(item);
                    /*$scope.formData.ctaVentasMonNac = $scope.findCta(item.ctaVentasMonNac, $scope.comboVentas);
                    $scope.formData.ctaVentasMonExt = $scope.findCta(item.ctaVentasMonExt, $scope.comboVentas);
                    $scope.formData.ctaComisionMonNac = $scope.findCta(item.ctaComisionMonNac, $scope.comboComisiones);
                    $scope.formData.ctaComisionMonExt = $scope.findCta(item.ctaComisionMonExt, $scope.comboComisiones);
                    $scope.formData.ctaDevolucionMonExt = $scope.findCta(item.ctaDevolucionMonExt, $scope.comboDevoluciones);
                    $scope.formData.ctaDevolucionMonNac = $scope.findCta(item.ctaDevolucionMonNac, $scope.comboDevoluciones);
*/
                }

                $scope.nuevo = function () {
                    $scope.showForm = true;
                    $scope.showBtnNuevo = true;
                    $scope.showBtnEditar = false;
                    $scope.showTable = false;
                    $scope.hideMessagesBox();
                    $scope.formData = new Aerolinea();
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

                $scope.modalEliminar = function (idx, nombrex, methodx) {
                    $scope.modalConfirmation = {id: idx, nombre: nombrex, method: methodx};
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

