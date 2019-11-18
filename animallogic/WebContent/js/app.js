/**
 * This is the application file where Angular JS Application is declared.
 */

var app = angular.module('animallogic', ['ngCookies', 'ui.router', 'animallogic.markovchain.module']);
app.config(function ($stateProvider, $urlRouterProvider) {
			$stateProvider
		    .state('animallogic', {
		    	url: '',
                abstract: true
		    });
		$urlRouterProvider.otherwise('/');
    });