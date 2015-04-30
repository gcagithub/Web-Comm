'use strict';

angular.module('angspark1')
  .factory('VkAuthService', ['$resource', function ($resource) {
    return $resource('angspark1/vklogin/:id', {}, {
      'query': { method: 'GET', isArray: true},
      'get': { method: 'GET'},
      'update': { method: 'PUT'}
    });
  }]);


  angular.module('angspark1')
  	.factory('VkMessageResource', ['$resource', function ($resource) {
    	return $resource('angspark1/vkMessage/:id',
    				{id:"save", vkMessage: "@vkMessage"}, {
    					'save': { method: 'POST'},
    					'query': {method: "GET"}
    				}
    			);
  }]);