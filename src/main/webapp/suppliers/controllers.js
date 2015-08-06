yuventoryApp.controller('SuppliersController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('suppliers', 'GET').success(function(data, status, headers, config) {
            $scope.suppliers = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add Supplier" : "Edit Supplier";
        $scope.addDialog = $modal.open({
            templateUrl : 'suppliers/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('suppliers/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddSupplierController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('suppliers', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);