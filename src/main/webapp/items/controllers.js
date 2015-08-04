yuventoryApp.controller('ItemsController', [ '$scope', 'AjaxService', '$modal', function($scope, AjaxService, $modal) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('items', 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
            itemType: null,
            stockType: null,
            supplier: null
        };
        AjaxService.call('meta/itemTypes', 'GET').success(function(data, status, headers, config) {
            $scope.itemTypes = data;
            $scope.request.itemType = $scope.request.itemType == null ? data[0] : $scope.request.itemType;
        });
        AjaxService.call('suppliers', 'GET').success(function(data, status, headers, config) {
            $scope.suppliers = data;
            $scope.request.supplier = $scope.request.supplier == null ? data[0].id : $scope.request.supplier;
        });
        AjaxService.call('stockTypes', 'GET').success(function(data, status, headers, config) {
            $scope.stockTypes = data;
            $scope.request.stockType = $scope.request.stockType == null ? data[0].id : $scope.request.stockType;
        });
        $scope.title = $scope.request.id == null ? "Add Item" : "Edit Item";
        if($scope.request.id == null) {
        	$scope.title = "Add Item";
        	$scope.barcodePath = "";
        } else {
        	$scope.title = "Edit Item";
        	$scope.barcodePath = AjaxService.baseUrl() + "items/barcode/" + request.id;
        }
        $scope.addDialog = $modal.open({
            templateUrl : 'items/add.html',
            scope : $scope
        });
    };
    
} ]);

yuventoryApp.controller('AddItemController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('items', 'POST', request).success(function(data, status, headers, config) {
        	if(request.id == null) {
        		$scope.barcodePath = AjaxService.baseUrl() + "items/barcode/" + request.id;
        	}
            $scope.load();
        });
    };
} ]);