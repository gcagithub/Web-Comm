'use strict';

angular.module('angspark1')
  .factory('Myentity', ['$resource', function ($resource) {
    return $resource('angspark1/myentities/:id', {}, {
      'query': { method: 'GET', isArray: true},
      'get': { method: 'GET'},
      'update': { method: 'PUT'}
    });
  }]);
