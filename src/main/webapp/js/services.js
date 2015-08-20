yuventoryApp.factory('AuthenticationService', ['$http', '$cookieStore', '$rootScope', 'AjaxService', function($http, $cookieStore, $rootScope, AjaxService) {
	'use strict';

	var service = {};

	service.Login = function(user, callback) {
		AjaxService.call('users/login', 'POST', user).success(function(data, status, headers, config) {
			callback(status, headers);
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

yuventoryApp.directive('switch', ['$parse', function($parse) {
    return {
		require: "?ngModel",
		scope: {
			value: '=ngModel'
		},
        restrict: 'E',
        template: function(elem, attrs) {
        	return    "<div class='btn-group' ng-click='toggle()'>" +
					  	"<button type='button' class='btn btn-info' data-f='1'>" + attrs.enableText + "</button>" +
					  	"<button type='button' class='btn btn-warning' data-f='0'>" + attrs.disableText + "</button>" +
					  "</div>";
        },
        link: function(scope, element, attributes, ngModelController) {
        	ngModelController.$render = function() {
        		element.find('div').text(ngModelController.$viewValue);
            };

            // update the model then the view
            function updateModel(offset) {
                // call $parsers pipeline then update $modelValue
                ngModelController.$setViewValue(ngModelController.$viewValue);
                // update the local view
                ngModelController.$render();
            }

            // update the value when user clicks the buttons
            scope.decrement = function() {
                updateModel(-1);
            };
            scope.increment = function() {
                updateModel(+1);
            };
        }
    };
}] );