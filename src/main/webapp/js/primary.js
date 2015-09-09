yuventoryApp.controller('LoginController', [ '$scope', '$location', 'AuthenticationService', function($scope, $location, AuthenticationService) {
	'use strict';

	AuthenticationService.ClearCredentials();

	$scope.login = function() {
		$scope.errorMessage = '';
		AuthenticationService.Login($scope.user, function(response, headers) {
			if (response == 200) {
				AuthenticationService.SetCredentials($scope.user.username, headers("YUOWN-KEY"), headers("USER_ROLES"));
				$scope.globals.currentUser.fullName = headers("USER_FULLNAME");
				$location.path('/home');
			} else {
				var err = '';
				try {
					err = $($(response).get(3)).html();
					err = err.split('-')[1].trim();
				} catch (e) {
					err = 'Failed to Login, please contact administrator!';
				}
				$scope.errorMessage = err;
			}
		});
	};

} ]);

yuventoryApp.controller('HomeController', [ '$scope', '$routeSegment', '$location', 'AuthenticationService', function($scope, $routeSegment, $location, AuthenticationService) {
    'use strict';
    
    $scope.isSegment = function(segment) {
        return $routeSegment.name.endsWith(segment);
    };
    
    $scope.logout = function() {
    	AuthenticationService.ClearCredentials();
    	$location.path('/login');
    };
    
} ]);

yuventoryApp.controller('MenuController', function($scope) {
    'use strict';
});
