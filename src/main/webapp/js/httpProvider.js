yuventoryApp.factory('YuventoryHttpInterceptor', [ '$q', '$location', '$rootScope', function($q, $location, $rootScope) {
    return {
       'responseError': function(response) {
           if (response.status === 500) {
               $rootScope.errorMessage = response.headers("errorMessage");
               return $q.reject(response);
           } else if (response.status === 401) {
               // redirect them back to login page
               $location.path('/login');

               return $q.reject(response);
           } else {
               return $q.reject(response);
           }
          return $q.reject(response);
        }
      };
} ]);

yuventoryApp.config(function($httpProvider) {
	'use strict';

	$httpProvider.interceptors.push('YuventoryHttpInterceptor');
});