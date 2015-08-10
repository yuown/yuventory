yuventoryApp.controller('CategoriesController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('categories', 'GET').success(function(data, status, headers, config) {
            $scope.categories = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            name : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add Category" : "Edit Category";
        $scope.addDialog = $modal.open({
            templateUrl : 'categories/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('categories/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
} ]);

yuventoryApp.controller('AddCategoryController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('categories', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);