yuventoryApp.factory('RoleService', function($http) {
    'use strict';

    var otherRoles = [ 'user' ];

    return {
        validateRoleOther : function(currentUser) {
            return currentUser ? _.contains(otherRoles, currentUser.role) : false;
        }
    };
});

yuventoryApp.factory('AuthenticationService', [
        '$http',
        'SessionService',
        'AjaxService',
        function($http, SessionService, AjaxService) {
            'use strict';

            return {
                login : function(user) {
                    // this method could be used to call the API and set the
                    // user
                    // instead of taking it in the function params
                    // SessionService.currentUser = user;
                    AjaxService.call('http://localhost:8080/yuventory/rest/users/login', user, 'POST').success(
                            function(data, status, headers, config) {
                                SessionService.authKey = headers("YUOWN-KEY");
                                sessionStorage.setItem('YUOWN-KEY', headers("YUOWN-KEY"));
                                SessionService.currentUser = user;
                            });
                },
                isLoggedIn : function() {
                    return SessionService.currentUser !== null;
                }
            };
        } ]);

yuventoryApp.factory('SessionService', function() {
    'use strict';

    return {
        currentUser : null
    };
});

yuventoryApp.factory('AjaxService', [ '$http', function($http) {
    return {
        call : function(url, params, method) {
            switch (method) {
            case 'POST':
                return $http.post(url, params);
                break;
            default:
                break;
            }
        }
    };
} ]);
