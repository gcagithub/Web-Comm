'use strict';

angular.module('angspark1')
  .controller("VkAuthController", ['$scope', 'resolvedVkAuth', 'VkAuthService', "VkMessageResource",
    function ($scope, resolvedVkAuth, VkAuthService, VkMessageResource) {

    	$scope.vkFriends = resolvedVkAuth;

    	$scope.save = function () {
    		var codeId = $scope.formUserCode.codeId;
    		if(!codeId){
    			alert("Field is empty!");
    			return;
    		}

   			// if(!$scope.vkFriends || $scope.vkFriends.length == 0){
    			// console.log("Save! " + codeId);
    			VkAuthService.query({id: codeId}, $scope.VkAuthService,
    				function(response) {
						$scope.vkFriends = response;
    				},
    				function(error) {
    					alert("Something wrong! See log in console.");
    					console.log(error);
    				}
    			);
    		// }
    	}

    	$scope.sendWallText = function sendWallText () {
    		console.log("wall text");
    		var message = $scope.formWallText.wallText;
    		if(message && message != ""){
    			var objMessage = {};
    			objMessage.vkMessage = encodeURI(message);
    			// var jsonMessage = JSON.parse(objMessage);
    			// console.log(jsonMessage);
    			VkMessageResource.save(objMessage,
    				function(response) {
    					console.log(response);
    				},
    				function(error) {
    					alert("Something wrong! See log in console.");
    					console.log("Error!!!");
    					console.log(error);
    				});
    		}
    		console.log($scope.formWallText.wallText);
    	}

    	$scope.displayLastMessage = function displayLastMessage () {
    		VkMessageResource.query({id:"last"},
    				function(response) {
    					console.log(response);
    					$scope.lastVkMessage = response.response.items[0].text;
    				},
    				function(error) {
    					console.log(error);
    				});
    	}
    }]);