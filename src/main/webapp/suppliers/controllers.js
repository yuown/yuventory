yuventoryApp.controller('SuppliersController', [ '$scope', 'AjaxService', '$modal', function($scope, AjaxService, $modal) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('http://localhost:8080/yuventory/rest/suppliers', 'GET').success(function(data, status, headers, config) {
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
    
} ]);

yuventoryApp.controller('AddSupplierController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('http://localhost:8080/yuventory/rest/suppliers', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);