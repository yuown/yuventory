yuventoryApp.controller('LoginController', [ '$scope', '$location', 'AuthenticationService', function($scope, $location, AuthenticationService) {
	'use strict';

	AuthenticationService.ClearCredentials();

	$scope.login = function() {
		$scope.dataLoading = true;
		AuthenticationService.Login($scope.user, function(response, headers) {
			if (response == 200) {
				AuthenticationService.SetCredentials($scope.user.username, headers("YUOWN-KEY"), headers("USER_ROLES"));
				$location.path('/home');
			} else {
				$scope.error = response.message;
				$scope.dataLoading = false;
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
