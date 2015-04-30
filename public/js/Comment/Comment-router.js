'use strict';

angular.module('angspark1')
  .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/comments', {
        templateUrl: 'views/Comment/Comments.html',
        controller: 'CommentController',
        resolve:{
          resolvedComment: ['Comment', function (Comment) {
            return Comment.query();
          }]
        }
      })
    }]);
