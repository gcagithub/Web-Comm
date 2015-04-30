'use strict';

angular.module('angspark1')
  .controller('CommentController', ['$scope', '$modal', 'resolvedComment', 'Comment',
    function ($scope, $modal, resolvedComment, Comment) {

      $scope.Comments = resolvedComment;

      $scope.create = function () {
        $scope.clear();
        $scope.open();
      };

      $scope.update = function (id) {
        $scope.Comment = Comment.get({id: id});
        $scope.open(id);
      };

      $scope.delete = function (id) {
        Comment.delete({id: id},
          function () {
            $scope.Comments = Comment.query();
          });
      };

      $scope.save = function (id) {
        if (id) {
          Comment.update({id: id}, $scope.Comment,
            function () {
              $scope.Comments = Comment.query();
              $scope.clear();
            });
        } else {
          Comment.save($scope.Comment,
            function () {
              $scope.Comments = Comment.query();
              $scope.clear();
            });
        }
      };

      $scope.clear = function () {
        $scope.Comment = {
          
          "id": "",
          
          "title": "",
          
          "done": "",
          
          "createdOn": "",
          
          "id": ""
        };
      };

      $scope.open = function (id) {
        var CommentSave = $modal.open({
          templateUrl: 'Comment-save.html',
          controller: 'CommentSaveController',
          resolve: {
            Comment: function () {
              return $scope.Comment;
            }
          }
        });

        CommentSave.result.then(function (entity) {
          $scope.Comment = entity;
          $scope.save(id);
        });
      };
    }])
  .controller('CommentSaveController', ['$scope', '$modalInstance', 'Comment',
    function ($scope, $modalInstance, Comment) {
      $scope.Comment = Comment;

      
      $scope.createdOnDateOptions = {
        dateFormat: 'yy-mm-dd',
        
        
      };

      $scope.ok = function () {
        alert("Ok!");
        console.log($modalInstance);
        $modalInstance.close($scope.Comment);
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
    }]);
