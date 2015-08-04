yuventoryApp.controller('StockTypesController', [ '$scope', 'AjaxService', '$modal', function($scope, AjaxService, $modal) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('http://localhost:8080/yuventory/rest/stockTypes', 'GET').success(function(data, status, headers, config) {
            $scope.stockTypes = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add Stock Type" : "Edit Stock Type";
        $scope.addDialog = $modal.open({
            templateUrl : 'stockTypes/add.html',
            scope : $scope
        });
    };
    
} ]);

yuventoryApp.controller('AddStockTypeController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('http://localhost:8080/yuventory/rest/stockTypes', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);