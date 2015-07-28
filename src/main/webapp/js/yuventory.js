var yuventoryApp = angular.module('yuventoryApp', [ 'ngRoute' ]);

yuventoryApp.config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl : 'templates/login.html',
    }).when('/home', {
        templateUrl : 'templates/home.html',
    }).otherwise({
        redirectTo : '/login'
    });
} ]);

yuventoryApp.factory('Auth', function() {
    var user;
    return {
        setUser : function(aUser) {
            user = aUser;
        },
        isLoggedIn : function() {
            return (user) ? user : false;
        }
    };

});

yuventoryApp.run([ '$rootScope', '$location', 'Auth', function($rootScope, $location, Auth) {
    $rootScope.$on('$routeChangeStart', function(event) {
        if (Auth.isLoggedIn() == true) {
            if ($location.path() != '/home') {
                event.preventDefault();
                $location.path('/home');
            }
        } else {
            console.log('DENY ' + $location.path());
            if ($location.path() != '/login') {
                event.preventDefault();
                $location.path('/login');
            }
        }
    });
} ]);

yuventoryApp.controller('LoginController', [ '$scope', 'Auth', '$location', function($scope, Auth, $location) {
    console.log("Loaded App");
    $scope.login = function() {
        if ($scope.user.username == $scope.user.password) {
            console.log("Login Success");
            Auth.setUser($scope.user);
            $location.path('/home');
        }
    };
} ]);

yuventoryApp.controller('HomeController', [ '$scope', 'Auth', '$location', function($scope, Auth, $location) {
    console.log("Home App");
    $scope.$watch(Auth.isLoggedIn, function(value, oldValue) {
        if (!value && oldValue) {
            console.log("Disconnect");
            $location.path('/login');
        }
        if (value) {
            console.log("Connect");
        }

    }, true);
} ]);
