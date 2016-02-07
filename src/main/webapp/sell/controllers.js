yuventoryApp.controller('SellController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.init = function() {
		$scope.search = {};
        
		try {
		    yuQuery(document.body).scannerDetection(function(data){
	            $scope.search.id = parseInt(data);
	            yuQuery("#yuventoryBarcode").val(parseInt(data));
	            $scope.searchItem(false);
	        });
        } catch (e) {
            console.log('Failed to Initialize barcode scanner: ' + e.message);
        }
        
        if($scope.globals.search && $scope.globals.search.id) {
        	$scope.search.id = $scope.globals.search.id;
        	$scope.searchItem(false);
        	$scope.globals.search = {};
        }
    };
    
    $scope.searchItem = function(manual) {
    	$scope.clearSearch();
    	if($scope.search.id != null && !isNaN($scope.search.id)) {
    	    $scope.errorMessage = null;
	    	AjaxService.call('items/stockout/' + $scope.search.id, 'GET').success(function(data, status, headers, config) {
	    	    if(data != null && data.id != null) {
	    	        $scope.errorMessage = headers("errorMessage");
    	    		$scope.search.hasResult = true;
    	    		data.createDate = new Date(data.createDate);
                    data.lendDate = new Date(data.lendDate);
    	            $scope.request = data;
    	            if(!$scope.errorMessage) {
        	            AjaxService.call("barcode/print/" + $scope.search.id, 'GET').success(function(data, status, headers, config) {
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
    	            }
	    	    } else {
	    	        $scope.errorMessage = "Item not found";
	    	    }
	        }).error(function(data, status, headers, config) {
	            $scope.errorMessage = headers("errorMessage");
	        });
    	} else {
	        $scope.errorMessage = "Invalid barcode";
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
            AjaxService.call("items/lend", 'POST', clonedRequest).success(function(data, status, headers, config) {
                $scope.searchItem();
            });
        });
    };
    
    $scope.getItemBack = function(request) {
        
        var clonedRequest = angular.copy(request);
        AlertsService.confirmReturn(clonedRequest, function(clonedRequest) {
            clonedRequest.lendTo = null;
            clonedRequest.lendDescription = null;
            AjaxService.call("items/getBack", 'POST', clonedRequest).success(function(data, status, headers, config) {
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
                clonedRequest.createDate = clonedRequest.createDate.getTime();
                clonedRequest.lendDate = clonedRequest.lendDate.getTime();
                AjaxService.call("items/sell", 'POST', clonedRequest).success(function(data, status, headers, config) {
                    $scope.searchItem();
                });
            }
        });
    };
    
} ]);
