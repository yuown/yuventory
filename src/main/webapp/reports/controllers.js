yuventoryApp.controller('ReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
} ]);

yuventoryApp.controller('MainReportsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', '$location', function($scope, AjaxService, $modal, AlertsService, $location) {
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
			sellEndDate: null,
			soldOrLent: false,
			url: 'reports/stockOutReportsOptions.html'
        };
		$scope.today();
		
		AjaxService.call("meta/itemTypes/", 'GET').success(function(data, status, headers, config) {
	    	$scope.reportsMeta.itemTypes = data;
		});
		
		AjaxService.call('categories', 'GET').success(function(data, status, headers, config) {
            $scope.categories = data;
        });
        AjaxService.call('suppliers', 'GET').success(function(data, status, headers, config) {
            $scope.suppliers = data;
        });
		
		AjaxService.call('stockTypes', 'GET').success(function(data, status, headers, config) {
            $scope.reportsMeta.stockTypes = data;
        });
		AjaxService.call('locations', 'GET').success(function(data, status, headers, config) {
            $scope.locations = data;
        });
		
		$scope.getCategoryName = function(id) {
	        return getObjectFromId($scope.categories, id)['name'];
	    };
	    
	    $scope.getSupplierName = function(id) {
	        var r = '';
	        if(id) {
	            r = getObjectFromId($scope.suppliers, id)['name'];
	        }
	        return r;
	    };
	    
	    $scope.getLocationName = function(id) {
	        var r = '';
            if(id) {
                r = getObjectFromId($scope.locations, id)['name'];
            }
            return r;
        }
	    
	    yuQuery('[data-toggle="popover"]').popover();
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
            	$scope.reportsOutput[i].stockTypeDesc = getObjectFromId($scope.reportsMeta.stockTypes, $scope.reportsOutput[i].stockType).name;
			}
        });
	};
	
	$scope.downloadReport = function() {
		var csv = encodeURIComponent(json2csv($scope.reportsOutput));
		$("#downloadCSV").attr('download', "yuventory_Unified_Report.csv");
		$("#downloadCSV").attr('href', 'data:Application/octet-stream,' + csv)[0].click();
	};
	
	$scope.getSoldOrLentItems = function() {
        var clonedReportsInput = angular.copy($scope.reportsInput);
        if($scope.reportsMeta.isSellCollapsed == true) {
            clonedReportsInput.sellStartDate = null;
            clonedReportsInput.sellEndDate = null;
        }
        var param;
        if($scope.reportsInput.soldOrLent) {
            param = 'lent';
        } else {
            param = 'sold';
        }
        $scope.stockOutReportModel = {
            from: clonedReportsInput.sellStartDate,
            to: clonedReportsInput.sellEndDate,
            owner: $scope.globals.owner[0]
        };
        AjaxService.call('reports/' + param + 'Items', 'POST', clonedReportsInput).success(function(data, status, headers, config) {
            $scope.reportsOutput = data;
            for (var i = 0; i < $scope.reportsOutput.length; i++) {
                $scope.reportsOutput[i].stockTypeDesc = getObjectFromId($scope.reportsMeta.stockTypes, $scope.reportsOutput[i].stockType).name;
            }
            $scope.toggleSelectAllItems(true);
        });
    };
    
    $scope.showInStockOut = function(item) {
        $scope.globals.search = {
            id : item.id
        };
        $location.path('/home/stockOut');
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to Archive this ?', function() {
            AjaxService.call('items/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.getSoldOrLentItems();
            });
        });
    };
    
    $scope.toggleSelectAllItems = function(selectAll) {
        if(selectAll == undefined || selectAll == null || selectAll == '') {
            selectAll = false;
        } else {
            selectAll = true;
        }
        $scope.reportsOutput.selectAll=selectAll;
        for(var i = 0; i < $scope.reportsOutput.length; i++) {
            $scope.reportsOutput[i].selected=$scope.reportsOutput.selectAll;
        }
    }
    
    $scope.toggleEach = function(item) {
        if(item.sold && item.sold == true) {
            item.selected=!item.selected;
        }
    };
    
    $scope.archiveSelected = function() {
        var selectedItems = [];
        for (var int = 0; int < $scope.reportsOutput.length; int++) {
            var element = $scope.reportsOutput[int];
            if(element.selected == true) {
                selectedItems.push(element);
            }
        }
        $scope.stockOutReportModel.selectedItems = selectedItems;
        AlertsService.confirmArchiveSelected($scope.stockOutReportModel, function() {
            var selectedIds = [];
            for (var int = 0; int < selectedItems.length; int++) {
                selectedIds.push(selectedItems[int].id);
            }
            AjaxService.call('items/archiveSoldItems', 'POST', selectedIds).success(function(data, status, headers, config) {
                $scope.getSoldOrLentItems();
            });
        });
    };
    
} ]);

yuventoryApp.controller('BalanceSheetController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.generateReport = function() {
        AjaxService.call('reports/balanceSheet', 'GET').success(function(data, status, headers, config) {
            $scope.reportsOutput = data;
        });
    };
    
} ]);

yuventoryApp.controller('SupplierStatisticsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.supplierStats = function() {
        AjaxService.call('reports/supplierStats', 'GET').success(function(data, status, headers, config) {
            $scope.supplierStatsOutput = data;
            $scope.goldTotal = 0;
            $scope.silverTotal = 0;
            for(var i = 0; i < data.length; i++) {
                var each = data[i];
                    $scope.goldTotal += each.gold;
                    $scope.silverTotal += each.silver;
            }
        });
    };
    
} ]);

yuventoryApp.controller('ItemsCountController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';
    
    $scope.itemsCountFetch = function() {
        AjaxService.call('reports/itemsCount', 'GET').success(function(data, status, headers, config) {
            $scope.itemsCount = data;
        });
    };
    
} ]);