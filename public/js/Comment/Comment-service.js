'use strict';

angular.module('angspark1')
  .factory('Comment', ['$resource', function ($resource) {
    return $resource('angspark1/comments/:id', {}, {
      'query': { method: 'GET', isArray: true},
      'get': { method: 'GET'},
      'update': { method: 'PUT'}
    });
  }]);
