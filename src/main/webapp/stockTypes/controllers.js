yuventoryApp.controller('StockTypesController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('stockTypes', 'GET').success(function(data, status, headers, config) {
            $scope.stockTypes = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
        };
        if($scope.request.id == null) {
            $scope.title = "Add Stock Type";
            $scope.showStockTypeDelete = false;
        } else {
            $scope.title = "Edit Stock Type";
        }
        
        $scope.addDialog = $modal.open({
            templateUrl : 'stockTypes/add.html',
            scope : $scope
        });
        AjaxService.call('meta/stockTypeMethods', 'GET').success(function(data, status, headers, config) {
            $scope.stockMethods = data;
            if($scope.request.id != null) {
                if($scope.stockMethods[$scope.request.method] == true) {
                    $scope.showStockTypeDelete = true;
                } else {
                    $scope.showStockTypeDelete = false;
                }
            }
        });
    };
    
    $scope.showHideDelete = function(key, value) {
        $scope.request.method = key;
        $scope.showStockTypeDelete = value;
    }
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('stockTypes/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddStockTypeController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('stockTypes', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);