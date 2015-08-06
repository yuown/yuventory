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
        confirmLending : function(callback) {
            $rootScope.title = "yuventory";
            $rootScope.confirmCallback = callback;
            $rootScope.confirmDialog = $modal.open({
                templateUrl : 'alerts/confirmLend.html',
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

yuventoryApp.controller('AlertsLendController', [ '$scope', '$modal', function($scope, $modal) {
    'use strict';
    
    AjaxService.call("suppliers/", 'GET').success(function(data, status, headers, config) {
    	$scope.lendTos = data;
	});
    
    $scope.submitOption = function(option, confirmCallback) {
        $scope.confirmDialog.dismiss('cancel')
        if(option == 'yes') {
            confirmCallback();
        }
    };
    
} ]);