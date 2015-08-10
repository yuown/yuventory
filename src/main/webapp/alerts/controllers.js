yuventoryApp.factory('AlertsService', [ '$rootScope', '$modal', function($rootScope, $modal) {
    'use strict';

    return {
        confirm : function(message, callback) {
            $rootScope.title = "yuventory";
            $rootScope.confirmMessage = message;
            $rootScope.confirmCallback = callback;
            $rootScope.confirmDialog = $modal.open({
                templateUrl : 'alerts/confirm.html',
                scope : $rootScope
            });
        },
        confirmLending : function(clonedRequest, callback) {
            $rootScope.title = "yuventory";
            $rootScope.confirmCallback = callback;
            $rootScope.clonedRequest = clonedRequest;
            $rootScope.lendReturnEntryExitFlag = 'Exit';
            $rootScope.confirmDialog = $modal.open({
                templateUrl : 'alerts/confirmLend.html',
                scope : $rootScope
            });
        },
        confirmReturn : function(clonedRequest, callback) {
            $rootScope.title = "yuventory";
            $rootScope.confirmCallback = callback;
            $rootScope.clonedRequest = clonedRequest;
            $rootScope.lendReturnEntryExitFlag = 'Entry';
            $rootScope.confirmDialog = $modal.open({
                templateUrl : 'alerts/confirmReturn.html',
                scope : $rootScope
            });
        },
        confirmSell : function(clonedRequest, callback) {
            $rootScope.title = "yuventory";
            $rootScope.confirmCallback = callback;
            $rootScope.clonedRequest = clonedRequest;
            $rootScope.confirmDialog = $modal.open({
                templateUrl : 'alerts/confirmSell.html',
                scope : $rootScope
            });
        }
    };

} ]);

yuventoryApp.controller('AlertsController', [ '$scope', '$modal', function($scope, $modal) {
    'use strict';
    
    $scope.submitOption = function(option, confirmCallback) {
        $scope.confirmDialog.dismiss('cancel')
        if(option == 'yes') {
            confirmCallback();
        }
    };
    
} ]);

yuventoryApp.controller('AlertsLendController', [ '$scope', '$modal', 'AjaxService', function($scope, $modal, AjaxService) {
    'use strict';
    
    AjaxService.call("suppliers/", 'GET').success(function(data, status, headers, config) {
    	$scope.lendTos = data;
    	$scope.clonedRequest.lendTo = $scope.clonedRequest.lendTo == 0 || $scope.clonedRequest.lendTo == null ? data[0].id : $scope.clonedRequest.lendTo;
	});
    
    AjaxService.call('stockTypes?method=' + $scope.lendReturnEntryExitFlag + '&remove=false', 'GET').success(function(data, status, headers, config) {
        $scope.stockTypes = data;
        $scope.clonedRequest.stockType = $scope.clonedRequest.stockType == null ? data[0].id : $scope.clonedRequest.stockType;
    });
    
    $scope.submitOption = function(option, confirmCallback) {
        $scope.confirmDialog.dismiss('cancel')
        if(option == 'yes') {
            confirmCallback($scope.clonedRequest);
        }
    };
    
} ]);

yuventoryApp.controller('AlertsSellController', [ '$scope', '$modal', 'AjaxService', function($scope, $modal, AjaxService) {
    'use strict';
    
    AjaxService.call("suppliers/", 'GET').success(function(data, status, headers, config) {
        $scope.lendTos = data;
        $scope.clonedRequest.lendTo = $scope.clonedRequest.lendTo == 0 || $scope.clonedRequest.lendTo == null ? data[0].id : $scope.clonedRequest.lendTo;
    });
    
    AjaxService.call('stockTypes?method=Exit', 'GET').success(function(data, status, headers, config) {
        $scope.stockTypes = data;
        $scope.clonedRequest.stockType = $scope.clonedRequest.stockType == null ? data[0].id : $scope.clonedRequest.stockType;
    });
    
    $scope.submitOption = function(option, confirmCallback) {
        $scope.confirmDialog.dismiss('cancel')
        if(option == 'yes') {
            confirmCallback($scope.clonedRequest, $scope.deleteFromStock);
        }
    };
    
    $scope.showHideDeleteMessage = function() {
        $scope.deleteFromStock = null;
        if(getObjectFromId($scope.stockTypes, $scope.clonedRequest.stockType)) {
            $scope.deleteFromStock = getObjectFromId($scope.stockTypes, $scope.clonedRequest.stockType)['remove'];
        }
    };
    
} ]);