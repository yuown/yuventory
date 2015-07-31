yuventoryApp.config(function($httpProvider) {
    'use strict';

    var logsOutUserOn401 = [ '$q', '$location', 'SessionService', function($q, $location, SessionService) {
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

    var authSetter = [ '$q', '$location', function($q, $location) {
        return {
            request : function(config) {
                config.headers = config.headers || {};
                config.headers['YUOWN-KEY'] = sessionStorage.getItem('YUOWN-KEY');
                return config;
            }
        };
    } ];

    $httpProvider.interceptors.push(authSetter);
    $httpProvider.interceptors.push(logsOutUserOn401);
});