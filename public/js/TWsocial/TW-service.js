'use strict';

angular.module('angspark1')
  .factory('TWLoginService', ['$resource', function ($resource) {
    return $resource('angspark1/twlogin/:id', {}, {
      'query': { method: 'GET', isArray: true},
      'get': { method: 'GET'},
      'create': { method: 'PUT'}
    });
	}]);

angular.module('angspark1')
  .factory('TWSocialService', ['$resource', function ($resource) {
    return $resource('angspark1/twsocial/:id', {}, {
      'query': { method: 'GET'}
    });
	}]);

angular.module('angspark1')
  .factory('TWTweetService', ['$resource', function ($resource) {
    return $resource('angspark1/twtweet/:id',
    	{id: '@id'},
    	{
	      'query': { method: 'GET', isArray: true},
	      'get': { method: 'GET'},
	      'create': { method: 'POST'}
    });
	}]);		