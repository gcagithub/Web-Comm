'use strict';

angular.module('angspark1').controller('FBController', ['$scope', 'resolvedFB', 'FBLoginService',
	'FBWallService', function($scope, resolvedFB, FBLoginService, FBWallService) {

		$scope.formWallMessage = {};
		$scope.formWallMessage.isReadyForPost = false;
		$scope.formOAuthURL = {};
		$scope.isLastPostsReady = true;

		resolvedFB.$promise.then(function() {
			console.log('Promise is ready!');
			if(resolvedFB.result && resolvedFB.result.fb === 'active'){
				formWallReady(true);
				hideFormOAuthURL(true);
			}
		});


		function formWallReady (isReady) {
			$scope.formWallMessage.isReadyForPost = isReady;
		}

		function hideFormOAuthURL (isHide) {
			$scope.formOAuthURL.hide = isHide;
		}

		$scope.showLastPosts = function() {
			$scope.isLastPostsReady = false;

			var body = {};
			body.id = 'lastPostMessages';
			body.limit = 10;

			FBWallService.get(
					body,

					function(response) {
						$scope.isLastPostsReady = true;
						$scope.lastPosts = response.result;
						console.log('Success');
						console.log(response.result);
					},
					function(error) {
						$scope.isLastPostsReady = false;
						console.log('Error');
						console.log(error);
					}
				);
		}

		$scope.sendOAuthURL = function() {
			var OAuthURL = $scope.formOAuthURL.inputURL;

			if(!OAuthURL) return;

			FBLoginService.query(
					{id:'OAuthURL', url:OAuthURL},

					function(response) {
						formWallReady(true);
						hideFormOAuthURL(true);
						console.log('Success');
						console.log(response);
					},

					function(error) {
						console.log('Error');
						console.log(error);
					}
				);
		}

		$scope.sendWallMessage = function() {
			var wallMessage = $scope.formWallMessage.message;

			if(!wallMessage) return;

			var body = {};
			body.id = 'wallMessage';
			body.message = encodeURI(wallMessage);
			
			formWallReady(false);

			FBWallService.create(
					body,

					function(response) {
						formWallReady(true);
						formWallMessageStatus('success',wallMessage);
						console.log('Success');
						console.log(response);
					},

					function(error) {
						formWallMessageStatus('danger',wallMessage);
						console.log('Error');
						console.log(error);
					}

				);
		}

		function formWallMessageStatus (status, msg) {
			$scope.formWallMessage.status = status;
			$scope.formWallMessage.statusMsg = '';
			if (status === 'success' ) {
				$scope.formWallMessage.message = "";
				$scope.formWallMessage.statusMsg = 'Message was sended successfuly!';
			} else {
				$scope.formWallMessage.statusMsg = 'Message was not sended!';
			}
			$scope.formWallMessage.postMsg = msg;
		}

	}]);