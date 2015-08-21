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
        when('/home/reports/unifiedReport', 'home.reports.unifiedReport').
        when('/home/reports/other1', 'home.reports.other1').
        when('/home/reports/other2', 'home.reports.other2').
        when('/home/settings', 'home.settings').
        when('/home/users', 'home.users').
        when('/home/groups', 'home.groups').
        segment('login', {
            templateUrl : 'templates/login.html',
        }).
        segment('home', {
            templateUrl : 'templates/home.html',
        }).
        within().
	        segment('stockOut', {
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
            within().
	            segment('unifiedReport', {
	            	'default': true,
	                templateUrl : 'reports/main.html'
	            }).
	            segment('other1', {
	                templateUrl : 'reports/other1.html'
	            }).
	            segment('other2', {
	                templateUrl : 'reports/other2.html'
	            }).
            up().
            segment('settings', {
                templateUrl : 'settings/list.html'
            }).
            segment('users', {
                templateUrl : 'users/list.html'
            }).
            segment('groups', {
                templateUrl : 'users/groupsList.html'
            });

    $routeProvider.otherwise({redirectTo: '/login'}); 
});