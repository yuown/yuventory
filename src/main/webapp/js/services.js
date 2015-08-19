yuventoryApp.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope', 'AjaxService', function($http, $cookieStore, $rootScope, AjaxService) {
	'use strict';

	var service = {};

	service.Login = function(user, callback) {
		AjaxService.call('users/login', 'POST', user).success(function(data, status, headers, config) {
			callback(status, headers);
		});
	};

	service.SetCredentials = function(username, authdata, r) {
	    service.ClearCredentials();
		if (authdata != null && authdata != '' && authdata != 'null' && authdata != undefined) {
			var roles = [];
			var rolesArray = JSON.parse(r);
			for(var i = 0; i < rolesArray.length; i++) {
				roles.push(rolesArray[i].authority);
			}
			$rootScope.globals = {
				currentUser : {
					username : username,
					authdata : authdata,
					roles: roles
				}
			};

			$http.defaults.headers.common['YUOWN-KEY'] = authdata;
			$cookieStore.put('globals', $rootScope.globals);
		} else{
			$rootScope.errorMessage = "Failed to Login, due to a Server Error, Please contact Administrator!";
		}
	};

	service.ClearCredentials = function() {
		$rootScope.globals = {};
		$cookieStore.remove('globals');
		$http.defaults.headers.common['YUOWN-KEY'] = '';
	};
	
	service.getRoles = function() {
		return $rootScope.globals.roles;
	};
	
	return service;
} ]);

yuventoryApp.factory('AjaxService', [ '$rootScope', '$http', function($rootScope, $http) {
	
	var serverUrl = "http://localhost:8090/yuventory/rest/";
	
    return {
        call : function(url, method, params) {
        	$rootScope.errorMessage = '';
            switch (method) {
            case 'POST':
                return $http.post(serverUrl + url, params);
                break;
            case 'GET':
                return $http.get(serverUrl + url, params);
                break;
            case 'DELETE':
                return $http.delete(serverUrl + url);
                break;
            default:
                break;
            }
        },
        baseUrl: function() {
        	return serverUrl;
        }
    };
} ]);

yuventoryApp.directive('access', ['AuthenticationService', function(AuthenticationService) {
	return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var makeVisible = function () {
                    element.removeClass('hidden');
                },
                makeHidden = function () {
                    element.addClass('hidden');
                },
                determineVisibility = function (resetFirst) {
                    var result;
                    if (resetFirst) {
                        makeVisible();
                    }

                    result = AuthenticationService.authorize(true, roles, attrs.accessPermissionType);
                    if (result === jcs.modules.auth.enums.authorised.authorised) {
                        makeVisible();
                    } else {
                        makeHidden();
                    }
                },
                roles = attrs.access.split(',');

            if (roles.length > 0) {
                determineVisibility(true);
            }
        }
      };
} ]);