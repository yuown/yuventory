yuventoryApp.controller('SettingsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', 'AuthenticationService', function($scope, AjaxService, $modal, AlertsService, AuthenticationService) {
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
        
        AjaxService.call("items/notifySize", 'GET').success(function(data, status, headers, config) {
            $scope.settings.itemNotifySize = data;
        });
        
        AjaxService.call("settings/barPage", 'GET').success(function(data, status, headers, config) {
            $scope.settings.barcodePageSettings = data;
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
    
    $scope.popNotifySize = function() {
        $scope.notifySizeDialog = $modal.open({
            templateUrl : 'settings/notifySize.html',
            scope : $scope
        });
    };
    
    $scope.itemsSizeCallback = function() {
        AjaxService.call("items/notifySize", 'POST', $scope.settings.itemNotifySize).success(function(data, status, headers, config) {
            $scope.refreshSettings();
        });
    };
    
    $scope.popBarcodePageSettings = function() {
        $scope.barcodePageSettings = $modal.open({
            templateUrl : 'settings/barcodePageSettings.html',
            scope : $scope
        });
    };
    
    $scope.barcodePageSettingsCallback = function() {
        AjaxService.call("settings/barPage", 'POST', $scope.settings.barcodePageSettings).success(function(data, status, headers, config) {
            $scope.globals.barcodePageSettings = $scope.settings.barcodePageSettings;
            AuthenticationService.updateCookie();
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

yuventoryApp.controller('ItemsizeController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    
    $scope.submitOption = function(option, callback) {
        $scope.notifySizeDialog.dismiss('cancel')
        if(option == 'yes') {
            callback();
        }
    };
    
} ]);

yuventoryApp.controller('BarcodePageSettingsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    
    $scope.submitOption = function(option, callback) {
        $scope.barcodePageSettings.dismiss('cancel')
        if(option == 'yes') {
            callback();
        }
    };
    
} ]);