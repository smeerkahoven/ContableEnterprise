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

var app = angular.module("jsRecover", ['jsRecover.controllers', 'smart-table', 'ui.bootstrap']);

angular.module('jsRecover.controllers', []).controller('frmRecover',
        ['$scope', '$http', '$uibModal', '$window', function ($scope, $http, $window) {

                var token = document.getElementsByName("hdToken")[0];
                var url = document.getElementsByName("hdUrl")[0];
                var urlUser = document.getElementsByName("hdUrlUser")[0];
                var formName = document.getElementsByName("hdFormName")[0];
                var myForm = document.getElementsByName("myForm")[0];
                var method = "update-pwd";

                $scope.showRestfulMessage = '';
                $scope.showRestfulError = false;
                $scope.showRestfulSuccess = false;

                $scope.loading = false;
                $scope.formData = {};
                $scope.mainGrid = {};
                $scope.modalConfirmation = {};

                $scope.showRecover = true;

                $scope.itemsByPage = 15;
                $scope.newPassword = '';

                $scope.checkButton = function () {
                    if ($scope.newPassword) {
                        return  $scope.newPassword.length < 5;
                    } else {
                        return !$scope.newPassword;
                    }
                }

                $scope.save = function () {
                    console.log(`url.value:${url.value}`);
                    $scope.showRecover = true;
                    var data = {usr: urlUser.value, pwd: $scope.newPassword};
                    console.log(data);
                    
                    
                    return $http({
                        method: 'POST',
                        url: `${url.value}` ,
                        data: {token: '', content: data},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function (response) {
                        console.log(response);
                        if (response.data.code === 201) {
                            $scope.mainGrid = response.data.content;
                            $scope.showRecover = false;
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

