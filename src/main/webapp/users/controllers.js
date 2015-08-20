yuventoryApp.controller('UsersController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('users', 'GET').success(function(data, status, headers, config) {
            $scope.users = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            username : "",
            fullName : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add User" : "Edit User";
        $scope.addDialog = $modal.open({
            templateUrl : 'users/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('users/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddUserController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('users', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);