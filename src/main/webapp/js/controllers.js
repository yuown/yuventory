yuventoryApp.controller('LoginController', [ '$scope', 'AuthenticationService', '$location',
        function($scope, AuthenticationService, $location) {
            'use strict';

            $scope.loginUser = function(user) {
                // this should be replaced with a call to your API for user
                // verification
                // (or you could also do it in the service)
                AuthenticationService.login(user);
                if (AuthenticationService.isLoggedIn()) {
                    $location.path('/home');
                }
            };
        } ]);

yuventoryApp.controller('HomeController', function($scope, SessionService) {
    'use strict';

    $scope.name = SessionService.currentUser.username;
});