yuventoryApp.controller('UsersController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
    'use strict';

    $scope.load = function() {
        AjaxService.call('users', 'GET').success(function(data, status, headers, config) {
            $scope.users = data;
        });
    };
    
    $scope.add = function(request) {
        $scope.request = angular.copy(request) || {
            username : "",
            fullName : "",
            id : null,
        };
        $scope.title = $scope.request.id == null ? "Add User" : "Edit User";
        $scope.addDialog = $modal.open({
            templateUrl : 'users/add.html',
            scope : $scope
        });
    };
    
    $scope.deleteRecord = function(request) {
        AlertsService.confirm('Are you sure to delete this?', function() {
            AjaxService.call('users/' + request.id, 'DELETE').success(function(data, status, headers, config) {
                $scope.load();
            });
        });
    };
    
    $scope.enableUser =  function (user) {
        console.log(user.enabled);
        AjaxService.call('users/enable', 'POST', user);
    };

} ]);

yuventoryApp.controller('AddUserController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('users', 'POST', request).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);

yuventoryApp.controller('GroupsController', [ '$scope', 'AjaxService', '$modal', 'AlertsService', function($scope, AjaxService, $modal, AlertsService) {
	'use strict';
	
	$scope.load = function() {
        AjaxService.call('users/groups', 'GET').success(function(data, status, headers, config) {
        	$scope.groups = [];
        	for(var i = 0; i < data.length; i++) {
        		$scope.groups.push({"name": data[i]});
        	}
        });
    };
    
    $scope.add = function(group) {
        $scope.group = angular.copy(group) || {
            name : ""
        };
        $scope.addDialog = $modal.open({
            templateUrl : 'users/addGroup.html',
            scope : $scope
        });
    };
    
    $scope.editAuthorities = function(group) {
        $scope.group = angular.copy(group);
        $scope.getAllAuthorities();
        AjaxService.call('users/groups/auth/' + group.name, 'GET').success(function(dataInner, status, headers, config) {
			$scope.grantedAuths = dataInner;
			for(var j = 0; j < $scope.grantedAuths.length; j++) {
				for(var i = 0; i < $scope.authorities.length; i++) {
					if($scope.authorities[i].name == $scope.grantedAuths[j]) {
						$scope.authorities[i].selected = true;
						break;
					}
				}
			}
		});
        
        $scope.addDialog = $modal.open({
            templateUrl : 'users/authorities.html',
            scope : $scope
        });
    };
    
    $scope.getAllAuthorities = function() {
    	AjaxService.call('users/groups/authorities', 'GET').success(function(data, status, headers, config) {
            $scope.authorities = [];
        	for(var i = 0; i < data.length; i++) {
        		$scope.authorities.push({"name": data[i], "selected": false});
        	}
        });
    };
}] );

yuventoryApp.controller('AddGroupController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.save = function(request) {
        AjaxService.call('users/groups', 'POST', request.name).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);

yuventoryApp.controller('GroupAuthoritiesController', [ '$scope', 'AjaxService', function($scope, AjaxService) {
    'use strict';

    $scope.saveAuthorities = function(groupName, authorities) {
        AjaxService.call('users/groups', 'POST', request.name).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);