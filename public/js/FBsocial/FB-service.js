'use strict';

angular.module('angspark1')
  .factory('FBLoginService', ['$resource', function ($resource) {
    return $resource('angspark1/fblogin/:id', {}, {
      'query': { method: 'GET', isArray: true},
      'get': { method: 'GET'},
      'create': { method: 'PUT'}
    });
	}]);

angular.module('angspark1')
  .factory('FBSocialService', ['$resource', function ($resource) {
    return $resource('angspark1/fbsocial/:id', {}, {
      'query': { method: 'GET'}
    });
	}]);	

angular.module('angspark1')
  .factory('FBWallService', ['$resource', function ($resource) {
    return $resource('angspark1/fbwall/:id',
    	{id: '@id'},
    	{
	      'query': { method: 'GET', isArray: true},
	      'get': { method: 'GET'},
	      'create': { method: 'POST'}
    });
	}]);	