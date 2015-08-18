yuventoryApp.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope', 'AjaxService', function($http, $cookieStore, $rootScope, AjaxService) {
	'use strict';

	var service = {};

	service.Login = function(user, callback) {
		AjaxService.call('users/login', 'POST', user).success(function(data, status, headers, config) {
			callback(status, headers);
		});
	};

	service.SetCredentials = function(username, authdata, roles) {
	    service.ClearCredentials();
		if (authdata != null && authdata != '' && authdata != 'null' && authdata != undefined) {
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

	return service;
} ]);

yuventoryApp.factory('AjaxService', [ '$rootScope', '$http', function($rootScope, $http) {
	
	var serverUrl = "http://localhost:8080/yuventory/rest/";
	
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
