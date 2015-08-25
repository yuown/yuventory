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
                $scope.authorities.push({"name": data[i]});
            }
        });
    };
    
    $scope.showGroupUsers = function(group) {
        $scope.group = group;
        $scope.addDialog = $modal.open({
            templateUrl : 'users/groupUsers.html',
            scope : $scope
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
        var authsToSave = [];
        for(var i=0;i<authorities.length;i++) {
            if(authorities[i].selected == true) {
                authsToSave.push(authorities[i].name)
            }
        }
        AjaxService.call('users/groups/auth/' + groupName.name, 'POST', authsToSave).success(function(data, status, headers, config) {
            $scope.addDialog.dismiss('cancel');
            $scope.load();
        });
    };
} ]);

yuventoryApp.controller('GroupUsersController', [ '$scope', 'AjaxService', '$modal', function($scope, AjaxService, $modal) {
    
    $scope.loadGroupUsers = function() {
        AjaxService.call('users/groups/user/' + $scope.group.name, 'GET').success(function(data, status, headers, config) {
            $scope.groupUsers = [];
            for(var i = 0; i < data.length; i++) {
                $scope.groupUsers.push({"username": data[i]});
            }
        });
    };
    
    $scope.addUserToGroup = function(currentUsers) {
        $scope.currentUsers = currentUsers;
        $scope.addUserToGroupPopup = $modal.open({
            templateUrl : 'users/groupUsersPopup.html',
            scope : $scope
        });
    };
    
    $scope.removeUserFromGroup = function(groupName, selecteduser) {
        AjaxService.call('users/groups/user/' + groupName, 'DELETE', selecteduser).success(function(data, status, headers, config) {
            $scope.loadGroupUsers();
        });
    };
}] );

yuventoryApp.controller('AddUserToGroupController', [ '$scope', 'AjaxService', '$modal', function($scope, AjaxService, $modal) {
    
    $scope.loadUsersList = function() {
        AjaxService.call('users', 'GET').success(function(data, status, headers, config) {
            $scope.users = [];
            for(var j = 0; j < data.length; j++) {
                if(!contains($scope.currentUsers, data[j], 'username')) {
                    $scope.users.push(data[j]);
                }
            }
        });
    };
    
    $scope.confirmAddUserToGroup = function(selecteduser) {
        AjaxService.call('users/groups/user/' + $scope.group.name, 'POST', selecteduser).success(function(data, status, headers, config) {
            $scope.addUserToGroupPopup.dismiss('cancel');
            $scope.loadGroupUsers();
        });
    };
    
}] );