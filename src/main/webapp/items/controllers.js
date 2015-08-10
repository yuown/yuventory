yuventoryApp.controller('ItemsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('items', 'GET').success(function(data, status, headers, config) {
            $scope.items = data;
        });
    };
    
    $scope.view = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
            itemType: null,
            stockType: null,
            supplier: null,
            category: null
        };
        AjaxService.call("barcode/" + $scope.request.id, 'GET').success(function(data, status, headers, config) {
            $scope.barcode = data;
        });
        
        AjaxService.call("stockTypes/" + $scope.request.stockType, 'GET').success(function(data, status, headers, config) {
            $scope.request.stockTypeDesc = data.name;
        });
        
        AjaxService.call("categories/" + $scope.request.category, 'GET').success(function(data, status, headers, config) {
            $scope.request.categoryDesc = data.name;
        });
        
        AjaxService.call("suppliers/" + $scope.request.supplier, 'GET').success(function(data, status, headers, config) {
            $scope.request.supplierDesc = data.name;
        });
        
        if($scope.request.lendTo && $scope.request.lendTo > 0) {
            AjaxService.call("suppliers/" + request.lendTo, 'GET').success(function(data, status, headers, config) {
                $scope.request.lendToDesc = data.name;
            });
        }
        $scope.addDialog = $modal.open({
            templateUrl : 'items/view.html',
            scope : $scope
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
            itemType: null,
            stockType: null,
            supplier: null,
            category: null
        };
        AjaxService.call('meta/itemTypes', 'GET').success(function(data, status, headers, config) {
            $scope.itemTypes = data;
            $scope.request.itemType = $scope.request.itemType == null ? data[0] : $scope.request.itemType;
        });
        AjaxService.call('suppliers', 'GET').success(function(data, status, headers, config) {
            $scope.suppliers = data;
            $scope.request.supplier = $scope.request.supplier == null ? data[0].id : $scope.request.supplier;
        });
        AjaxService.call('categories', 'GET').success(function(data, status, headers, config) {
            $scope.categories = data;
            $scope.request.category = $scope.request.category == null ? data[0].id : $scope.request.category;
            
            $scope.request.currCategory=getObjectFromId($scope.categories, $scope.request.category)['name'];
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
            $scope.request.currCategory=getObjectFromId($scope.categories, $scope.request.category)['name'];
            AjaxService.call("barcode/" + data.id, 'GET').success(function(data, status, headers, config) {
        		$scope.barcode = data;
        	});
            $scope.load();
        });
    };
    
    $scope.setCategoryDescription = function() {
        $scope.request.currCategory=getObjectFromId($scope.categories, $scope.request.category)['name'];
    };
    
} ]);