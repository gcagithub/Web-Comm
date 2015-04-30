'use strict';

angular.module('angspark1')
  .controller('MyentityController', ['$scope', '$modal', 'resolvedMyentity', 'Myentity',
    function ($scope, $modal, resolvedMyentity, Myentity) {

      $scope.myentities = resolvedMyentity;

      $scope.create = function () {
        $scope.clear();
        $scope.open();
      };

      $scope.update = function (id) {
        $scope.myentity = Myentity.get({id: id});
        $scope.open(id);
      };

      $scope.delete = function (id) {
        Myentity.delete({id: id},
          function () {
            $scope.myentities = Myentity.query();
          });
      };

      $scope.save = function (id) {
        if (id) {
          Myentity.update({id: id}, $scope.myentity,
            function () {
              $scope.myentities = Myentity.query();
              $scope.clear();
            });
        } else {
          Myentity.save($scope.myentity,
            function () {
              $scope.myentities = Myentity.query();
              $scope.clear();
            });
        }
      };

      $scope.clear = function () {
        $scope.myentity = {
          
          "myattr": "",
          
          "id": ""
        };
      };

      $scope.open = function (id) {
        var myentitySave = $modal.open({
          templateUrl: 'myentity-save.html',
          controller: 'MyentitySaveController',
          resolve: {
            myentity: function () {
              return $scope.myentity;
            }
          }
        });

        myentitySave.result.then(function (entity) {
          $scope.myentity = entity;
          $scope.save(id);
        });
      };
    }])
  .controller('MyentitySaveController', ['$scope', '$modalInstance', 'myentity',
    function ($scope, $modalInstance, myentity) {
      $scope.myentity = myentity;

      

      $scope.ok = function () {
        $modalInstance.close($scope.myentity);
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
    }]);
