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

var app = angular.module("jsEmpresa", ['jsEmpresa.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsEmpresa.controllers', []).controller('frmEmpresa', ['$scope', '$http', function ($scope, $http) {
        $scope.loading = false;
        $scope.itemsByPage = 15;

        $scope.showForm = false;
        $scope.showTable = true;

    }]);


