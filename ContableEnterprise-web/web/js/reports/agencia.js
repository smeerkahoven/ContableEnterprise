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

var app = angular.module("jsRprtAgencia", ['jsRprtAgencia.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsRprtAgencia.controllers', []).controller('rptAgencia',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {
                //var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTUyNzg0MjcwNCwiaWF0IjoxNTI3Nzk5NTA0LCJ2YWx1ZSI6ImFkbWluIn0.20_NgML0xEVr3W9R7RuZOxqno1cLk7cMq5bOtNDLZGQ";//document.getElementsByName("hdToken")[0];
                //var url = "http://localhost:8080/ContableEnterprise-ws/ws-api/roles/form-perm" //document.getElementsByName("hdUrl")[0];

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
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

