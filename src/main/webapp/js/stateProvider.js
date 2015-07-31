yuventoryApp.config(function($stateProvider, $urlRouterProvider) {
    'use strict';

    $urlRouterProvider.otherwise('/login');

    $stateProvider.state('login', {
        url : '/login',
        templateUrl : 'templates/login.html',
        controller : 'LoginController'
    }).state('home', {
        url : '/home',
        templateUrl : 'templates/home.html',
        controller : 'HomeController'
    }).state('suppliers', {
        url : '/suppliers',
        scope : {
            current : '=content-holder'
        },
        templateUrl : 'templates/suppliers.html',
        controller : 'SuppliersController'
    })
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