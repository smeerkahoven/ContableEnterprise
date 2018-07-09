/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module("jsIndex", ['jsIndex.controllers']);

angular.module('jsIndex.controllers', []).controller('frmIndex', ['$scope', '$http',
    function ($scope, $http) {

        var urlFactores = document.getElementsByName("hdUrlFactores")[0];
        var token = document.getElementsByName("hdToken")[0];
        var formName = document.getElementsByName("hfFormName")[0];
        
        $scope.showFactores = false ;

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
                    
                    $scope.showFactores = true ;
                }
            }, function (error) {

            });
        }

        $scope.getDataFactor('dollar');
        $scope.getDataFactor('ufv');

    }
]);