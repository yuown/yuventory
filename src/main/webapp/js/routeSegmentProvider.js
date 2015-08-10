yuventoryApp.config(function($routeSegmentProvider, $routeProvider) {

    $routeSegmentProvider.options.autoLoadTemplates = true;

    // Setting routes. This consists of two parts:
    // 1. `when` is similar to vanilla $route `when` but takes segment name instead of params hash
    // 2. traversing through segment tree to set it up

    $routeSegmentProvider.
        when('/login', 'login').
        when('/home', 'home').
        when('/home/stockOut', 'home.stockOut').
        when('/home/stockIn', 'home.stockIn').
        when('/home/suppliers', 'home.suppliers').
        when('/home/stockTypes', 'home.stockTypes').
        when('/home/categories', 'home.categories').
        when('/home/reports', 'home.reports').
        when('/home/settings', 'home.settings').
        segment('login', {
            templateUrl : 'templates/login.html',
        }).
        segment('home', {
            templateUrl : 'templates/home.html',
        }).
        within().
	        segment('stockOut', {
	            'default': 'true',
	            templateUrl : 'sell/search.html'
	        }).
            segment('stockIn', {
                templateUrl : 'items/list.html'
            }).
            segment('suppliers', {
                templateUrl : 'suppliers/list.html'
            }).
            segment('stockTypes', {
                templateUrl : 'stockTypes/list.html'
            }).
            segment('categories', {
                templateUrl : 'categories/list.html'
            }).
            segment('reports', {
                templateUrl : 'reports/list.html'
            }).
            segment('settings', {
                templateUrl : 'settings/list.html'
            });

    $routeProvider.otherwise({redirectTo: '/login'}); 
});