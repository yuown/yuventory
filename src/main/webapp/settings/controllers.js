yuventoryApp.controller('SettingsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.settings = {};
    
    $scope.refreshSettings = function() {
        $scope.settings.barcodeDimensions = {};
        
        AjaxService.call("barcode/dimensions", 'GET').success(function(data, status, headers, config) {
            if(!data['barcode_width']) {
                $scope.settings.barcodeDimensions.barcode_width = 0;
            } else {
                $scope.settings.barcodeDimensions.barcode_width = data.barcode_width;
            }
            if(!data['barcode_height']) {
                $scope.settings.barcodeDimensions.barcode_height = 0;
            } else {
                $scope.settings.barcodeDimensions.barcode_height = data.barcode_height;
            }
        });
        
        AjaxService.call("items/pageSize", 'GET').success(function(data, status, headers, config) {
        	$scope.settings.pageSize = data;
        });
    };
    
    $scope.popDimensions = function() {
        $scope.barcodeDimensionsDialog = $modal.open({
            templateUrl : 'settings/barcodeDimensions.html',
            scope : $scope
        });
    };
    
    $scope.barcodeCallback = function() {
        AjaxService.call("barcode/dimensions", 'POST', $scope.settings.barcodeDimensions).success(function(data, status, headers, config) {
            $scope.refreshSettings();
        });
    };
    
    $scope.popPageSize = function() {
        $scope.pageSizeDialog = $modal.open({
            templateUrl : 'settings/pageSize.html',
            scope : $scope
        });
    };
    
    $scope.pageSizeCallback = function() {
        AjaxService.call("items/pageSize", 'POST', $scope.settings.pageSize).success(function(data, status, headers, config) {
            $scope.refreshSettings();
        });
    };
    
} ]);

yuventoryApp.controller('BarcodeDimensionsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    
    $scope.submitOption = function(option, callback) {
        $scope.barcodeDimensionsDialog.dismiss('cancel')
        if(option == 'yes') {
            callback();
        }
    };
    
} ]);

yuventoryApp.controller('PagesizeController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    
    $scope.submitOption = function(option, callback) {
        $scope.pageSizeDialog.dismiss('cancel')
        if(option == 'yes') {
            callback();
        }
    };
    
} ]);
