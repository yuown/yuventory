yuventoryApp.config(function($stateProvider, $urlRouterProvider) {
    'use strict';


});

yuventoryApp.run(function($rootScope, $location, AuthenticationService, RoleService, SessionService) {
    'use strict';

    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = [ '/login' ];

    // check if current location matches route
    var routeClean = function(route) {
        return _.find(routesThatDontRequireAuth, function(noAuthRoute) {
            return sessionStorage.getItem('YUOWN-KEY') != null || route.startsWith(noAuthRoute);
        });
    };

    $rootScope.$on('$stateChangeStart', function(ev, to, toParams, from, fromParams) {
        // if route requires auth and user is not logged in
        if (!routeClean($location.url()) && !AuthenticationService.isLoggedIn()) {
            // redirect back to login
            // ev.preventDefault();
            $location.path('/login');
        }
    });
});