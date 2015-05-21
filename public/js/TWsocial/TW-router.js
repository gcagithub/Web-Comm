'use strict';

angular.module('angspark1')
  .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/twsocial', {
        templateUrl: 'views/TWSocial/index.html',
        controller: 'TWController',
        resolve:{
          resolvedTW: ['TWSocialService', function (TWSocialService) {
            return TWSocialService.query({id:'init'});
          }]
        }
      })
    }]);