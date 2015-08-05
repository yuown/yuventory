yuventoryApp.config(function($routeSegmentProvider, $routeProvider) {

    $routeSegmentProvider.options.autoLoadTemplates = true;

    // Setting routes. This consists of two parts:
    // 1. `when` is similar to vanilla $route `when` but takes segment name instead of params hash
    // 2. traversing through segment tree to set it up

    $routeSegmentProvider.
        when('/login', 'login').
        when('/home', 'home').
        when('/home/sell', 'home.sell').
        when('/home/items', 'home.items').
        when('/home/suppliers', 'home.suppliers').
        when('/home/stockTypes', 'home.stockTypes').
        segment('login', {
            templateUrl : 'templates/login.html',
        }).
        segment('home', {
            templateUrl : 'templates/home.html',
        }).
        within().
	        segment('sell', {
	            'default': 'true',
	            templateUrl : 'sell/search.html'
	        }).
            segment('items', {
                templateUrl : 'items/list.html'
            }).
            segment('suppliers', {
                templateUrl : 'suppliers/list.html'
            }).
            segment('stockTypes', {
                templateUrl : 'stockTypes/list.html'
            });

    $routeProvider.otherwise({redirectTo: '/login'}); 
});