yuventoryApp.controller('ReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    
    
} ]);

yuventoryApp.controller('MainReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.resetPurchaseDate = function() {
    	$scope.reportsInput.purchaseStartDate = new Date();
		$scope.reportsInput.purchaseEndDate = new Date();
    };
    
    $scope.resetSellDate = function() {
    	$scope.reportsInput.sellStartDate = new Date();
		$scope.reportsInput.sellEndDate = new Date();
    };
    
    $scope.today = function() {
    	$scope.resetPurchaseDate();
    	$scope.resetSellDate();
	};
	
	$scope.clearPurchaseDate = function() {
		$scope.reportsInput.purchaseStartDate = null;
		$scope.reportsInput.purchaseEndDate = null;
    };
    
    $scope.clearSellDate = function() {
    	$scope.reportsInput.sellStartDate = null;
		$scope.reportsInput.sellEndDate = null;
    };
	
	$scope.clear = function() {
		$scope.clearPurchaseDate();
		$scope.clearSellDate();
	};

	$scope.open = function(field) {
		switch (field) {
		case 'purchaseStartDate':
			$scope.status.psopened = true;
			break;
		case 'purchaseEndDate':
			$scope.status.peopened = true;
			break;
		case 'sellStartDate':
			$scope.status.ssopened = true;
			break;
		case 'sellEndDate':
			$scope.status.seopened = true;
			break;
		default:
			break;
		}
	};
	
	$scope.init = function() {
	    $scope.reportsMeta = [];
	    $scope.reportsMeta.isPurchaseCollapsed = true;
	    $scope.reportsMeta.isSellCollapsed = true;
	    $scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd-MMMM-yyyy', 'shortDate' ];
		$scope.format = $scope.formats[2];
		$scope.status = { psopened: false, peopened: false, ssopened: false, seopened: false };
		
		$scope.reportsInput = {
			purchaseStartDate: null,
			purchaseEndDate: null,
			sellStartDate: null,
			sellEndDate: null
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
	
	$scope.togglePurchaseDate = function() {
		$scope.reportsMeta.isPurchaseCollapsed = !$scope.reportsMeta.isPurchaseCollapsed;
	};
	
	$scope.toggleSellDate = function() {
		$scope.reportsMeta.isSellCollapsed = !$scope.reportsMeta.isSellCollapsed;
	};
	
	$scope.generateReport = function(reportsRequest) {
		var clonedReportsInput = angular.copy(reportsRequest);
		if($scope.reportsMeta.isPurchaseCollapsed == true) {
			clonedReportsInput.purchaseStartDate = null;
			clonedReportsInput.purchaseEndDate = null;
					
		}
		if($scope.reportsMeta.isSellCollapsed == true) {
			clonedReportsInput.sellStartDate = null;
			clonedReportsInput.sellEndDate = null;
		}
	    AjaxService.call('reports/generate/', 'POST', clonedReportsInput).success(function(data, status, headers, config) {
            $scope.reportsOutput = data;
            for (var i = 0; i < $scope.reportsOutput.length; i++) {
            	$scope.reportsOutput[i].supplierDesc = getObjectFromId($scope.reportsMeta.suppliers, $scope.reportsOutput[i].supplier).name;
            	$scope.reportsOutput[i].categoryDesc = getObjectFromId($scope.reportsMeta.categories, $scope.reportsOutput[i].category).name;
            	$scope.reportsOutput[i].stockTypeDesc = getObjectFromId($scope.reportsMeta.stockTypes, $scope.reportsOutput[i].stockType).name;
            	$scope.reportsOutput[i].lendToDesc = getObjectFromId($scope.reportsMeta.suppliers, $scope.reportsOutput[i].lendTo).name;
			}
        });
	};
	
	$scope.downloadReport = function() {
		var csv = encodeURIComponent(json2csv($scope.reportsOutput));
		$("#downloadCSV").attr('download', "yuventory_Unified_Report.csv");
		$("#downloadCSV").attr('href', 'data:Application/octet-stream,' + csv)[0].click();
	};
	
} ]);

yuventoryApp.controller('BalanceSheetController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.generateReport = function() {
        AjaxService.call('reports/balanceSheet', 'POST').success(function(data, status, headers, config) {
            $scope.reportsOutput = data;
        });
    };
    
} ]);