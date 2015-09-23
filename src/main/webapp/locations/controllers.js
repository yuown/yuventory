yuventoryApp.controller('LocationsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('locations', 'GET').success(function(data, status, headers, config) {
            $scope.locations = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add Location" : "Edit Location";
        $scope.addDialog = $modal.open({
            templateUrl : 'locations/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('locations/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddLocationController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('locations', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);