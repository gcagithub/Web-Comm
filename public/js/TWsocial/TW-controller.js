'use strict';

angular.module('angspark1').controller('TWController', ['$scope', 'resolvedTW', 'TWLoginService',
	'TWTweetService', function($scope, resolvedTW, TWLoginService,
		TWTwittService) {

		$scope.formOAuthPIN = {};
		$scope.formTWMessage = {};
		$scope.formTWMessage.isReadyForPost = false;
		$scope.isLastTweetsReady = true;

		resolvedTW.$promise.then(function() {
			console.log('Promise is ready!');
			if(resolvedTW.result && resolvedTW.result.tw === 'active'){
				formTWReady(true);
				hideFormOAuthPIN(true);
			}
		});

		function formTWReady (isReady) {
			$scope.formTWMessage.isReadyForPost = isReady;
		}

		function hideFormOAuthPIN (isHide) {
			$scope.formOAuthPIN.hide = isHide;
		}


		$scope.sendOAuthPIN = function() {
			var pin = $scope.formOAuthPIN.inputPIN;

			if(!pin) return;

			TWLoginService.query(
					{id: 'OAuthPIN', pin:pin},

					function(response) {
						hideFormOAuthPIN(true);
						formTWReady(true);
						console.log(response)
					},
					function(error) {
						console.log(error);
					}
				);
		}

		$scope.sendTWMessage = function() {
			var twMessage = $scope.formTWMessage.message;

			if(!twMessage) return;

			var body = {};
			body.id = 'twMessage';
			body.message = encodeURI(twMessage);
			
			formTWReady(false);

			TWTwittService.create(
					body,

					function(response) {
						formTWReady(true);
						formTWMessageStatus('success',twMessage);
						console.log('Success');
						console.log(response);
					},

					function(error) {
						formTWMessageStatus('danger',twMessage);
						console.log('Error');
						console.log(error);
					}

				);
		}

		function formTWMessageStatus (status, msg) {
			$scope.formTWMessage.status = status;
			$scope.formTWMessage.statusMsg = '';
			if (status === 'success' ) {
				$scope.formTWMessage.message = "";
				$scope.formTWMessage.statusMsg = 'Message was sended successfuly!';
			} else {
				$scope.formTWMessage.statusMsg = 'Message was not sended!';
			}
			$scope.formTWMessage.postMsg = msg;
		}



		$scope.showLastTwittes = function() {
			$scope.isLastTweetsReady = false;

			var body = {};
			body.id = 'lastTwittes';
			body.limit = 10;

			TWTwittService.get(
					body,

					function(response) {
						$scope.lastTweets = response.result;
						$scope.isLastTweetsReady = true;
						console.log('Success');
						console.log(response.result);
					},
					function(error) {
						$scope.isLastTweetsReady = false;
						console.log('Error');
						console.log(error);
					}
				);
		}

	}]);