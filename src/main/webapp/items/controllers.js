yuventoryApp.controller('ItemsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', '$location', function($scope, AjaxService, $modal, AlertsService, $location) {
    'use strict';
    
    $scope.currentPage = 1;

    $scope.load = function(pageNumber, itemName) {
    	var itemNameParam = '';
    	if(itemName) {
    		itemNameParam = '&name=' + itemName;
    	}
    	AjaxService.call('categories', 'GET').success(function(data, status, headers, config) {
            $scope.categories = data;
        });
    	AjaxService.call('items/names', 'GET').success(function(data, status, headers, config) {
            $scope.newItemNames = data;
        });
    	
        AjaxService.call('items?page=' + (pageNumber - 1) + itemNameParam, 'GET').success(function(data, status, headers, config) {
        	$scope.totalItems = headers("totalItems");
        	$scope.pages = headers("pages");
        	$scope.currentPage = pageNumber;
            $scope.items = data;
        });
    };
    
    $scope.getNumber = function(num) {
    	var array = [];
    	for (var int = 0; int < num;) {
			array[int] = ++int;
		}
    	return array;
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
        AjaxService.call("barcode/print/" + $scope.request.id, 'GET').success(function(data, status, headers, config) {
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
        	AjaxService.call("barcode/print/" + $scope.request.id, 'GET').success(function(data, status, headers, config) {
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
                $scope.load($scope.currentPage);
            });
        });
    };
    
    $scope.showInStockOut = function(item) {
    	$scope.globals.search = {
			id : item.id
		};
    	$location.path('/home/stockOut');
    };
    
    $scope.getCategoryName = function(id) {
        return getObjectFromId($scope.categories, id)['name'];
    }
    
    $scope.getSupplierName = function(id) {
        var r = getObjectFromId($scope.suppliers, id)['name'];
        return r != null && r != '' ? r : '';
    }
    
} ]);

yuventoryApp.controller('AddItemController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';
    
    $scope.save = function(request) {
        AjaxService.call('items', 'POST', request).success(function(data, status, headers, config) {
            $scope.request = data;
            $scope.request.currCategory=getObjectFromId($scope.categories, $scope.request.category)['name'];
            AjaxService.call("barcode/print/" + data.id, 'GET').success(function(data, status, headers, config) {
        		$scope.barcode = data;
        	});
            $scope.load($scope.currentPage);
        });
    };
    
    $scope.setCategoryDescription = function() {
        $scope.request.currCategory=getObjectFromId($scope.categories, $scope.request.category)['name'];
    };
    
    $scope.init = function() {
        $scope.onemore = true;
        AjaxService.call('items/names', 'GET').success(function(data, status, headers, config) {
            $scope.newItemNames = data;
        });
    };
    
    $scope.printBarcode = function() {
    	window.print();
    	$scope.addDialog.dismiss('cancel');
    	if($scope.onemore == true) {
            $scope.add();
        }
    };
    
    $scope.previewBarcode = function() {
        $.printPreview.loadPrintPreview();
    };
    
} ]);