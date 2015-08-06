yuventoryApp.controller('ItemsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
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
        AjaxService.call('stockTypes?method=Entry', 'GET').success(function(data, status, headers, config) {
            $scope.stockTypes = data;
            $scope.request.stockType = $scope.request.stockType == null ? data[0].id : $scope.request.stockType;
        });
        $scope.title = $scope.request.id == null ? "Add Item" : "Edit Item";
        if($scope.request.id == null) {
        	$scope.title = "Add Item";
        	$scope.barcode = "";
        } else {
        	$scope.title = "Edit Item";
        	AjaxService.call("barcode/" + $scope.request.id, 'GET').success(function(data, status, headers, config) {
                $scope.barcode = data;
            });
        }
        $scope.addDialog = $modal.open({
            templateUrl : 'items/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('items/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddItemController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('items', 'POST', request).success(function(data, status, headers, config) {
            $scope.request = data;
            AjaxService.call("barcode/" + data.id, 'GET').success(function(data, status, headers, config) {
        		$scope.barcode = data;
        	});
            $scope.load();
        });
    };
} ]);