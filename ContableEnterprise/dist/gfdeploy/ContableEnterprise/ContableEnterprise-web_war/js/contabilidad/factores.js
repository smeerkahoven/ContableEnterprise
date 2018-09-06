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

var app = angular.module("jsFactores", ['jsFactores.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsFactores.controllers', []).controller('frmFactores', ['$scope', '$http', '$uibModal', function ($scope, $http, $modal) {
        $scope.showAll = false;
        var token = document.getElementsByName("hdToken")[0];
        var url = document.getElementsByName("hdUrl")[0];
        var formName = document.getElementsByName("hdFormName")[0].value;

        $scope.showRestfulMessage = '';
        $scope.showRestfulError = false;
        $scope.showRestfulSuccess = false;

        $scope.loading = false;
        $scope.frmNewTransaction = {};
        $scope.frmDelTransaction = {};

        $scope.showForm = false;
        $scope.showTable = true;
        $scope.showBtnNuevo = true;

        $scope.itemsByPage = 15;

        $scope.getData = function (method) {
            $scope.loading = true;
            //console.log(formName);
            return $http({
                method: 'POST',
                url: url.value + method + "/all",
                data: {token: token.value, content: ''},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                if (response.data.code === 201) {
                    if (method === 'dollar') {
                        $scope.dolarGrid = response.data.content;
                    }
                    if (method === 'ufv') {
                        $scope.ufvGrid = response.data.content;
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

        $scope.update = function (method) {
            console.log(method);
            console.log($scope.myForm);
            if (!$scope.myForm.$valid)
                return;
            $http({
                method: 'POST',
                url: url.value + method + '/update',
                data: {token: token.value, formName: formName, content: angular.toJson($scope.formData)},
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {

                if (response.data.code === 201) {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulSuccess = true;

                    $scope.getData('dollar', $scope.data);
                    $scope.getData('ufv', $scope.data);
                } else {
                    $scope.showRestfulMessage = response.data.content;
                    $scope.showRestfulError = true;
                }
                $scope.loading = false;
                $scope.showForm = false;
                $scope.showTable = true;

            }, function (error) {
                $scope.loading = false;
                $scope.showRestfulMessage = error.statusText;
                $scope.showRestfulError = true;
                //$scope.showForm = true;
            });
        };



        $scope.cancelar = function () {
            $scope.showForm = false;
            $scope.showTable = true;
            $scope.showFormNewTransaction = false;
        }


        $scope.edit = function (item, method) {
            $scope.showLoading = true;
            $scope.showBtnNuevo = false;
            $scope.showBtnActualizar = true;
            $scope.showRestfulSuccess = false;
            $scope.showRestfulError = false;
            $scope.showForm = true;
            $scope.showTable = false;
            $scope.formData = item ;
            $scope.method = method ;
        }

        $scope.getData('dollar', $scope.data);
        $scope.getData('ufv', $scope.data);
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

