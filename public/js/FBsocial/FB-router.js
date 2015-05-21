'use strict';

angular.module('angspark1')
  .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/fbsocial', {
        templateUrl: 'views/FBSocial/index.html',
        controller: 'FBController',
        resolve:{
          resolvedFB: ['FBSocialService', function (FBSocialService) {
            return FBSocialService.query({id:'init'});
          }]
        }
      })
    }]);
