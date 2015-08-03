yuventoryApp.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope', 'AjaxService', function($http, $cookieStore, $rootScope, AjaxService) {
	'use strict';

	var service = {};

	service.Login = function(user, callback) {
		AjaxService.call('http://localhost:8080/yuventory/rest/users/login', 'POST', user).success(function(data, status, headers, config) {
			callback(status, headers);
		});
	};

	service.SetCredentials = function(username, authdata) {
		$rootScope.globals = {
			currentUser : {
				username : username,
				authdata : authdata
			}
		};

		$http.defaults.headers.common['YUOWN-KEY'] = authdata;
		$cookieStore.put('globals', $rootScope.globals);
	};

	service.ClearCredentials = function() {
		$rootScope.globals = {};
		$cookieStore.remove('globals');
		$http.defaults.headers.common['YUOWN-KEY'] = '';
	};

	return service;
} ]);

yuventoryApp.factory('AjaxService', [ '$http', function($http) {
    return {
        call : function(url, method, params) {
            switch (method) {
            case 'POST':
                return $http.post(url, params);
                break;
            case 'GET':
                return $http.get(url, params);
                break;
            default:
                break;
            }
        }
    };
} ]);
