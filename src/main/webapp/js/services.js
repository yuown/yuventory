yuventoryApp.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope', 'AjaxService', function($http, $cookieStore, $rootScope, AjaxService) {
	'use strict';

	var service = {};

	service.Login = function(user, callback) {
		AjaxService.call('users/login', 'POST', user).success(function(data, status, headers, config) {
			callback(status, headers);
		}).error(function(data, status, headers, config) {
			callback(data);
		});
	};

	service.SetCredentials = function(username, authdata, r) {
	    service.ClearCredentials();
		if (authdata != null && authdata != '' && authdata != 'null' && authdata != undefined) {
			var roles = [];
			var rolesArray = JSON.parse(r);
			for(var i = 0; i < rolesArray.length; i++) {
				roles.push(rolesArray[i].authority);
			}
			$rootScope.globals = {
				currentUser : {
					username : username,
					authdata : authdata,
					roles: roles
				}
			};

			$http.defaults.headers.common['YUOWN-KEY'] = authdata;
			$cookieStore.put('globals', $rootScope.globals);
		} else{
			$rootScope.errorMessage = "Failed to Login, due to a Server Error, Please contact Administrator!";
		}
	};

	service.ClearCredentials = function() {
		$rootScope.globals = {};
		$cookieStore.remove('globals');
		$http.defaults.headers.common['YUOWN-KEY'] = '';
	};
	
	service.hasAccess = function(role) {
	    var allowed = false;
	    var rolesToCheck = $rootScope.globals.currentUser.roles;
	    toBreak: 
	        for(var i = 0; i < rolesToCheck.length; i++) {
    	        for(var j = 0; j < role.length; j++) {
        	        if(rolesToCheck[i] == role[j].trim()) {
        	            allowed = true;
        	            break toBreak;
        	        }
    	        }
    	    }
		return allowed;
	};
	
	return service;
} ]);

yuventoryApp.factory('AjaxService', [ '$rootScope', '$http', function($rootScope, $http) {
	
	var serverUrl = "http://localhost:8090/yuventory/rest/";
	
    return {
        call : function(url, method, params) {
        	$rootScope.errorMessage = '';
            switch (method) {
            case 'POST':
                return $http.post(serverUrl + url, params);
                break;
            case 'GET':
                return $http.get(serverUrl + url, params);
                break;
            case 'DELETE':
                return $http.delete(serverUrl + url);
                break;
            default:
                break;
            }
        },
        baseUrl: function() {
        	return serverUrl;
        }
    };
} ]);

yuventoryApp.directive('access', ['AuthenticationService', function(AuthenticationService) {
	return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var makeVisible = function () {
                    element.removeClass('hidden');
                },
                makeHidden = function () {
                    element.addClass('hidden');
                },
                determineVisibility = function (resetFirst) {
                    var result;
                    if (resetFirst) {
                        makeVisible();
                    }

                    result = AuthenticationService.hasAccess(role);
                    if (result === true) {
                        makeVisible();
                    } else {
                        makeHidden();
                    }
                },
                role = attrs.access.split(",");

            if (role != null && role.length > 0) {
                determineVisibility(true);
            }
        }
      };
} ]);

yuventoryApp.directive('switch', function() {
    return {
		require: "ngModel",
        restrict: 'E',
        scope: {
            callback: '&callback'
        },
        template: function(elem, attrs) {
        	return    "" +
					  	"<button type='button' class='btn btn-info' data-f='1'>" + attrs.enableText + "</button>" +
					  	"<button type='button' class='btn btn-warning' data-f='0'>" + attrs.disableText + "</button>" +
					  "";
        },
        link: function(scope, element, attrs, ngModelController) {
        	ngModelController.$render = function() {
        	    var effect = 'drop';
        		if(ngModelController.$viewValue == true) {
        			jQuery(element.find('[data-f=0]')).show(effect)
        			jQuery(element.find('[data-f=1]')).hide(effect);
        		} else {
        			jQuery(element.find('[data-f=0]')).hide(effect);
                    jQuery(element.find('[data-f=1]')).show(effect);
        		}
            };
            function toggle() {
                ngModelController.$setViewValue(!ngModelController.$viewValue);
                ngModelController.$render();
                scope.callback();
            };
            element.on('click', function(event) {
            	toggle();
            });
        }
    };
});