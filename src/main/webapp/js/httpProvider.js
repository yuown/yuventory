yuventoryApp.config(function($httpProvider) {
	'use strict';

	var logsOutUserOn401 = [ '$q', '$location', function($q, $location) {
		var success = function(response) {
			return response;
		};

		var error = function(response) {
			if (response.status === 401) {
				// redirect them back to login page
				$location.path('/login');

				return $q.reject(response);
			} else {
				return $q.reject(response);
			}
		};

		return function(promise) {
			return promise.then(success, error);
		};
	} ];

	$httpProvider.interceptors.push(logsOutUserOn401);
});