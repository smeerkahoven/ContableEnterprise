/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var hoy = function () {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd
    }

    if (mm < 10) {
        mm = '0' + mm
    }

    today = mm + '/' + dd + '/' + yyyy;
    return today;
}

var app = angular.module("jsIndex", ['jsIndex.controllers']);

angular.module('jsIndex.controllers', []).controller('frmIndex', ['$scope', '$http',
    function ($scope, $http) {

        var urlFactores = document.getElementsByName("hdUrlFactores")[0];
        var token = document.getElementsByName("hdToken")[0];
        var formName = document.getElementsByName("hfFormName")[0];
        //modal dolar
        $scope.showFactores = false;
        $scope.fechaHoy = hoy();

        $scope.dollarModalSuccess = false;
        $scope.dollarModalError = false;

        $scope.getDataFactor = function (method) {
            return $http({
                method: 'POST',
                url: urlFactores.value + method + '/today',
                data: {token: token.value, content: '', formName: formName},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === 'dollar') {
                        $scope.dollar = response.data.content;

                    } else if (method === 'ufv') {
                        $scope.ufv = response.data.content;
                    }
                    $scope.showFactores = true;
                } else if (response.data.code === 301) {
                    $scope.showBtnGuardarDolarFactor = true;
                    $scope.showBtnCerrarDolarFactor = false;
                    document.getElementById("openModalButton").click();
                }
            }, function (error) {
            });
        }

        $scope.guardar = function () {
            var data = {fecha: $scope.fechaHoy, valor: $scope.valorDolar};
            $scope.showBtnCerrarDolarFactor = true;
            $scope.showBtnGuardarDolarFactor = false;
            return $http({
                method: 'POST',
                url: urlFactores.value + 'dollar/save',
                data: {token: token.value, content: angular.toJson(data), formName: formName},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    $scope.dollarModalMessage = response.data.content;
                    $scope.dollarModalSuccess = true;
                }
            }, function (error) {
                $scope.dollarModalMessage = error.statusText;
                $scope.dollarModalError = true;


            });
        }

        $scope.getDataFactor('dollar');
         $scope.getDataFactor('ufv');

    }
]);