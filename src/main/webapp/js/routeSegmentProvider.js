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
        when('/home/notification', 'home.notification').
        when('/home/reports/unifiedReport', 'home.reports.unifiedReport').
        when('/home/reports/balanceSheet', 'home.reports.balanceSheet').
        when('/home/reports/supplierStats', 'home.reports.supplierStats').
        when('/home/reports/lentItems', 'home.reports.lentItems').
        when('/home/settings', 'home.settings').
        when('/home/users', 'home.users').
        when('/home/changeProfile', 'home.changeProfile').
        when('/home/groups', 'home.groups').
        when('/home/aboutMe', 'home.aboutMe').
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
	            segment('balanceSheet', {
	                templateUrl : 'reports/balanceSheet.html'
	            }).
	            segment('supplierStats', {
	                templateUrl : 'reports/supplierStats.html'
	            }).
                segment('lentItems', {
                    templateUrl : 'reports/lentItems.html'
                }).
            up().
            segment('notification', {
                templateUrl : 'reports/notification.html'
            }).
            segment('settings', {
                templateUrl : 'settings/list.html'
            }).
            segment('users', {
                templateUrl : 'users/list.html'
            }).
            segment('changeProfile', {
                templateUrl : 'users/changeProfile.html'
            }).
            segment('groups', {
                templateUrl : 'users/groupsList.html'
            }).
            segment('aboutMe', {
                templateUrl : 'about/me.html'
            });

    $routeProvider.otherwise({redirectTo: '/login'}); 
});