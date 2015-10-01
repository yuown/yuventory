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
        var bcPgSet = $scope.globals.barcodePageSettings;
        var printCode = '<html><head><title>Print Barcode</title>' +
                            '<link rel="stylesheet" type="text/css" href="bootstrap/bootstrap.css" />' +
                            '<link rel="stylesheet" type="text/css" href="bootstrap/bootstrap-theme.css" />' +
                            '<link rel="stylesheet" type="text/css" href="css/bar-print.css" />' +
                            '<style type="text/css" media="print">' +
                                '@page {' +
                                '    size: ' + bcPgSet.page_width + 'mm ' + bcPgSet.page_height + 'mm;' +
                                '    margin: ' + bcPgSet.margin_top + 'mm ' + bcPgSet.margin_right + 'mm ' + bcPgSet.margin_bottom + 'mm ' + bcPgSet.margin_left + 'mm;' +
                                '}' +
                            '</style>' +
                            '<script type="text/javascript" src="jquery/jquery-2.1.4.js"></script>' +
                            '<script type="text/javascript">' +
                                '$(document).ready(function() {' +
                                    '$(".no-print").remove();' +
                                  'window.print();' +
                                  'window.close();' +
                                 '});' +
                            '</script>' +  
                        '</head><body>' + yuQuery(".print-section").html() + '</body></html>';
        var winPrint = window.open('', '', '');
        winPrint.document.write(printCode);
        winPrint.document.close();
        winPrint.focus();
    	$scope.addDialog.dismiss('cancel');
    	if($scope.onemore == true) {
            $scope.add();
        }
    };
    
    $scope.previewBarcode = function() {
        $.printPreview.loadPrintPreview();
    };
    
} ]);

yuventoryApp.controller('EstimateController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    var calculateTotalPrice = function(item) {
    	var pW = parseFloat(item.weight);
    	var pG = parseFloat(item.perGram);
    	var pM = parseFloat(item.makingCharges);
    	var pIw = parseFloat(item.wastage);
    	var wt1 = pW * (pG + pM);
    	var wt2 = (pW * pG) * pIw / 100;
    	return roundTo2Decimals(wt1 + wt2);
    };
    
    $scope.roundTo2Decimals = function(amount) {
        return roundTo2Decimals(amount);
    };
    
    $scope.init = function() {
        $scope.search = {};
        $scope.addedItems2Estimate = [];
        
        try {
            yuQuery(document.body).scannerDetection(function(data){
                $scope.search.id = parseInt(data);
                $("#yuventoryBarcode").val(parseInt(data));
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
            AjaxService.call('items/' + $scope.search.id, 'GET').success(function(data, status, headers, config) {
                if(data != null && data.id != null) {
                    $scope.errorMessage = '';
                    $scope.search.hasResult = true;
                    $scope.request = data;
                    
                    AjaxService.call("categories/" + $scope.request.category, 'GET').success(function(data, status, headers, config) {
                        $scope.request.categoryDesc = data.name;
                    });
                    
                } else {
                    $scope.errorMessage = "Item not found";
                }
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
    
    $scope.addItem = function(request) {
        var contains = getObjectFromId($scope.addedItems2Estimate, request.id);
    	if(contains == null) {
    		var clonedRequest = angular.copy(request);
            AlertsService.estimateDetails(clonedRequest, function(item) {
            	item.totalPrice = calculateTotalPrice(item);
            	item.discAmount = 0;
            	$scope.addedItems2Estimate.push(item);
            	$scope.clearSearch();
            });
    	} else {
    		$scope.clearSearch();
    	}
    };
    
    $scope.modifyEstimatedItem = function(request) {
    	var cloned = angular.copy(request);
        AlertsService.estimateDetails(cloned, function(item) {
        	request.perGram = item.perGram;
        	request.makingCharges = item.makingCharges;
        	request.wastage = item.wastage;
        	request.discAmount = item.discAmount;
        	request.totalPrice = calculateTotalPrice(item);
        });
    };
    
    $scope.applyDiscount = function(request) {
        var cloned = angular.copy(request);
        AlertsService.discountBox(cloned, function(item) {
            request.discAmount = item.discAmount;
        });
    };
    
    $scope.calculatedWastage = function(item) {
        var pW = parseFloat(item.weight);
        var pG = parseFloat(item.perGram);
        var pIw = parseFloat(item.wastage);
        var wt2 = (pW * pG) * pIw / 100;
        return roundTo2Decimals(wt2);
    };
    
    $scope.calculatedMaking = function(item) {
        var pW = parseFloat(item.weight);
        var pM = parseFloat(item.makingCharges);
        return roundTo2Decimals(pW * pM);
    };
    
    $scope.sum = function(addedItems2Estimate) {
    	var sum = 0.0;
    	for (var int = 0; int < addedItems2Estimate.length; int++) {
    		sum += parseFloat(addedItems2Estimate[int].totalPrice - addedItems2Estimate[int].discAmount);
		}
    	return roundTo2Decimals(sum);
    };
    
    $scope.basePrice = function(item) {
    	return roundTo2Decimals(parseFloat(item.weight) * parseFloat(item.perGram));
    };
    
    $scope.printEstimate = function() {
        var winPrint = window.open('', '', 'left=0,top=0,toolbar=0,scrollbars=0,status=0');
        winPrint.document.write('<html><head><title>Print Estimate</title>' +
                '<link rel="stylesheet" type="text/css" href="bootstrap/bootstrap.css" />' +
                '<link rel="stylesheet" type="text/css" href="bootstrap/bootstrap-theme.css" />' +
                '<script type="text/javascript" src="jquery/jquery-2.1.4.js"></script>' +
                '<script type="text/javascript">' +
                    '$(document).ready(function() {' +
                    	'$(".no-print").remove();' +
            			'window.print();window.close();});' +
                '</script>' +  
                '</head><body>' + yuQuery(".estimate-section-print").html() + "</body></html>");
        winPrint.document.close();
        winPrint.focus();
    };
    
    $scope.getDate = function() {
    	return getDate();
    };
    
} ]);

yuventoryApp.controller('ItemsValidationController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', '$location', function($scope, AjaxService, $modal, AlertsService, $location) {
    'use strict';
    
    $scope.init = function() {
        $scope.currentPage = 1;
        $scope.validateRequest = {
            valid : true,
            invalid : true
        };
        $scope.search = {};
        yuQuery(document.body).scannerDetection(function(data) {
            $scope.search.id = parseInt(data);
            AjaxService.call('items/' + $scope.search.id, 'GET').success(function(data, status, headers, config) {
                if(data) {
                    $scope.validate(data);
                }
            });
        });
        AjaxService.call('categories', 'GET').success(function(data, status, headers, config) {
            $scope.categories = data;
        });
    };
    
    $scope.loadByValidity = function(pageNumber, valid, invalid) {
        var param = null;
        if(valid == true || invalid == true) {
            if(valid) {
                param = true;
            }
            if(invalid) {
                param = false;
            }
        }
        if(valid == true && invalid == true) {
            param = null;
        }
        var paramVal = '';
        if(param != null) {
            paramVal = '&valid=' + param;
        }
        
        AjaxService.call('items?page=' + (pageNumber - 1) + paramVal, 'GET').success(function(data, status, headers, config) {
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
    
    $scope.validate = function(request) {
        request.validated = true;
        $scope.submitValidation(request);
    };
    
    $scope.invalidate = function(request) {
        request.validated = false;
        $scope.submitValidation(request);
    };
    
    $scope.submitValidation = function(request) {
        AjaxService.call('items/validity', 'POST', request).success(function(data, status, headers, config) {
            $scope.message = "Item with ID " + request.id + ", successfully marked as " + (request.validated == false ? 'InValidated' : 'Validated');
            $scope.request = data;
            $scope.loadByValidity($scope.currentPage, $scope.validateRequest.valid, $scope.validateRequest.invalid);
        });
    };
    
    $scope.validateAll = function(flag) {
        AjaxService.call('items/validateAll?flag=' + flag, 'GET').success(function(data, status, headers, config) {
            $scope.loadByValidity(1, $scope.validateRequest.valid, $scope.validateRequest.invalid);
        });
    };
    
    $scope.getCategoryName = function(id) {
        return getObjectFromId($scope.categories, id)['name'];
    }
    
    $scope.getSupplierName = function(id) {
        var r = getObjectFromId($scope.suppliers, id)['name'];
        return r != null && r != '' ? r : '';
    }
    
} ]);