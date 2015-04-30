'use strict';

angular.module('angspark1')
  .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/vklogin', {
        templateUrl: 'views/VkAuth/vkauth.html',
        controller: 'VkAuthController',
        resolve:{
          resolvedVkAuth: ['VkAuthService', function (VkAuthService) {
            return VkAuthService.query();
          }]
        }
      })
    }]);
