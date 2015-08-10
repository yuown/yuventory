yuventoryApp.controller('SellController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.searchItem = function(manual) {
    	$scope.clearSearch();
    	if($scope.search.id != null && $scope.search.id != '') {
	    	AjaxService.call('items/' + $scope.search.id, 'GET').success(function(data, status, headers, config) {
	    	    if(data != null && data.id != null) {
	    	        $scope.errorMessage = '';
    	    		$scope.search.hasResult = true;
    	            $scope.request = data;
    	            AjaxService.call("barcode/" + $scope.search.id, 'GET').success(function(data, status, headers, config) {
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
        	            AjaxService.call("suppliers/" + $scope.request.lendTo, 'GET').success(function(data, status, headers, config) {
                            $scope.request.lendToDesc = data.name;
                        });
    	            }
	    	    } else {
	    	        $scope.errorMessage = "Item not found";
	    	    }
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
        
        var clonedRequest = angular.copy(request);
        AlertsService.confirmLending(clonedRequest, function(clonedRequest) {
            AjaxService.call("items/", 'POST', clonedRequest).success(function(data, status, headers, config) {
                $scope.searchItem();
            });
        });
    };
    
    $scope.getItemBack = function(request) {
        
        var clonedRequest = angular.copy(request);
        AlertsService.confirmReturn(clonedRequest, function(clonedRequest) {
            clonedRequest.lendTo = null;
            clonedRequest.lendDescription = null;
            AjaxService.call("items/", 'POST', clonedRequest).success(function(data, status, headers, config) {
                $scope.searchItem();
            });
        });
    };
    
    $scope.sellItem = function(request) {
        
        var clonedRequest = angular.copy(request);
        AlertsService.confirmSell(clonedRequest, function(clonedRequest, deleteFromStock) {
            if(deleteFromStock) {
                AjaxService.call("items/" + clonedRequest.id, 'DELETE').success(function(data, status, headers, config) {
                    $scope.searchItem();
                });
            } else {
                clonedRequest.sold = true;
                AjaxService.call("items/", 'POST', clonedRequest).success(function(data, status, headers, config) {
                    $scope.searchItem();
                });
            }
        });
    };
} ]);
