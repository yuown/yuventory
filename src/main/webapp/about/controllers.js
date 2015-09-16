yuventoryApp.controller('AboutController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';
    
    $scope.load = function() {
    	AjaxService.call('about', 'GET').success(function(data, status, headers, config) {
            $scope.about = data;
        });
    };
} ]);
