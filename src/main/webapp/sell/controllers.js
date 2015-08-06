yuventoryApp.controller('SellController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.searchItem = function() {
    	$scope.clearSearch();
    	
    	if($scope.search.id != null && $scope.search.id != '') {
	    	AjaxService.call('items/' + $scope.search.id, 'GET').success(function(data, status, headers, config) {
	    		$scope.search.hasResult = true;
	            $scope.request = data;
	            AjaxService.call("barcode/" + $scope.search.id, 'GET').success(function(data, status, headers, config) {
	        		$scope.barcode = data;
	        	});
	            
	            AjaxService.call("stockTypes/" + $scope.request.stockType, 'GET').success(function(data, status, headers, config) {
	            	$scope.request.stockType = data.name;
	        	});
	            
	            AjaxService.call("suppliers/" + $scope.request.supplier, 'GET').success(function(data, status, headers, config) {
	            	$scope.request.supplier = data.name;
	        	});
	        });
    	} else {
    		$scope.errorMessage = "Search criteria is invalid!";
    	}
    };
    
    $scope.clearSearch = function() {
    	$scope.search.hasResult = false;
    	$scope.request = null;
    	$scope.errorMessage = null;
    };
    
    $scope.lendItem = function(request) {
        AlertsService.confirm(function() {
        	
        });
    };
    
} ]);
