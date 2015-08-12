yuventoryApp.controller('ReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    
    
} ]);

yuventoryApp.controller('MainReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.today = function() {
		$scope.reportsInput.startDate = new Date();
		$scope.reportsInput.endDate = new Date();
	};
	
	$scope.clear = function() {
		$scope.reportsInput.startDate = null;
		$scope.reportsInput.endDate = null;
	};

	$scope.open = function(field) {
		if(field == 'start') {
			$scope.status.sopened = true;
		} else if(field == 'end') {
			$scope.status.eopened = true;
		}
	};
	
	$scope.init = function() {
	    $scope.reportsMeta = [];
	    $scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd-MMMM-yyyy', 'shortDate' ];
		$scope.format = $scope.formats[2];
		$scope.status = { sopened: false, eopened: false };
		
		$scope.reportsInput = {
            startDate: null,
            endDate: null
        };
		$scope.today();
		
		AjaxService.call("meta/itemTypes/", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.itemTypes = data;
		});
		
		AjaxService.call("categories/", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.categories = data;
		});
		
		AjaxService.call("suppliers", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.suppliers = data;
		});
		
		AjaxService.call('stockTypes', 'GET').success(function(data, status, headers, config) {
            $scope.reportsMeta.stockTypes = data;
        });
	};
	
	$scope.generateReport = function(reportsRequest) {
	    AjaxService.call('reports/generate/', 'POST', reportsRequest).success(function(data, status, headers, config) {
            //$scope.reportsMeta.stockTypes = data;
        });
	};
	
} ]);