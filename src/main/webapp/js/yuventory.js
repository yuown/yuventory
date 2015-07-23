var yuventoryApp = angular.module('yuventoryApp', [ 'ngRoute' ]);

yuventoryApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'templates/login.html',
	}).otherwise({
		redirectTo : '/login'
	});
} ]);

yuventoryApp.controller('LoginController', function($scope) {
	console.log("Loaded App");
});
