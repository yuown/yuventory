yuventoryApp.controller('ReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    
    
} ]);

yuventoryApp.controller('MainReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.today = function() {
		$scope.reports.startDate = new Date();
		$scope.reports.endDate = new Date();
	};
	
	$scope.clear = function() {
		$scope.reports.startDate = null;
		$scope.reports.endDate = null;
	};

	$scope.open = function(field) {
		if(field == 'start') {
			$scope.status.sopened = true;
		} else if(field == 'end') {
			$scope.status.eopened = true;
		}
	};
	
	$scope.init = function() {
		$scope.reports = { startDate: null, endDate: null, categories: []};
	    $scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd-MMMM-yyyy', 'shortDate' ];
		$scope.format = $scope.formats[2];
		$scope.status = { sopened: false, eopened: false };
		$scope.reportsMeta = [];
		
		$scope.today();

		$scope.reportsMeta.reportTypes = [ {
			id : 'stock',
			name : 'In Stock Items',
			mode: 'Entry',
			supplierOrLent: 'By Supplier'
		}, {
			id : 'sold',
			name : 'Sold Items',
			mode: 'Exit',
			supplierOrLent: 'By Supplier'
		}, {
			id : 'lent',
			name : 'Items Lent to others',
			mode: 'Exit',
			supplierOrLent: 'Items Lent to'
		} ];
		
		$scope.reportsInput = {
			reportType : $scope.reportsMeta.reportTypes[0].id,
			supplierOrLent: $scope.reportsMeta.reportTypes[0].supplierOrLent
		};
		
		AjaxService.call("meta/itemTypes/", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.itemTypes = data;
		});
		
		AjaxService.call("categories/", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.categories = data;
		});
		
		AjaxService.call("suppliers", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.suppliers = data;
		});
		
		$scope.changeReportType();
	};
	
	$scope.changeReportType = function() {
		var mode = {};
		for (var i in $scope.reportsMeta.reportTypes) {
			if($scope.reportsMeta.reportTypes[i].id == $scope.reportsInput.reportType) {
				mode = $scope.reportsMeta.reportTypes[i];
				break;
			}
		}
		
		$scope.reportsInput.supplierOrLent = mode.supplierOrLent;
		
		AjaxService.call('stockTypes?method=' + mode.id, 'GET').success(function(data, status, headers, config) {
            $scope.reportsMeta.stockTypes = data;
        });
	};
	
} ]);