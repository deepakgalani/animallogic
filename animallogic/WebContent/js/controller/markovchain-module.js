/**
 * The Markov 
 */

var animallogicMarkovChainModule = angular.module('animallogic.markovchain.module', ['animallogic.markovchain.service']);

animallogicMarkovChainModule.config(function($stateProvider){
    $stateProvider
    .state('animallogic.home', {
        url: '/',
        views: {
            'application.centre.view@': {
            	controller: 'animallogicMarkovChainCtrl',
                templateUrl: 'view/markovchain.tmpl.html'
            }
        }
    })
    .state('animallogic.markovchain', {
        url: '/markovresult',
        params: {markovchainresult: ''},
        views: {
            'application.centre.view@': {    
            	controller: 'animallogicMarkovChainResultCtrl',
            	templateUrl: 'view/markovchainresult.tmpl.html'
            }
        }
    })
});

var animallogicMarkovChainCtrl = animallogicMarkovChainModule.controller('animallogicMarkovChainCtrl', function($scope, $http, $location, $window, $cookies, $browser, $filter, $state, $stateParams, MarkovChainService) {	
	$scope.markovchainrequest = {};
	algorithmInputForm.file.$invalid=false;
	
	function markovChainResultHandler(markovChainResultRecord){
		if(markovChainResultRecord != null){
			//$scope.markovchainresult = markovChainResultRecord.text;		
			$state.go('animallogic.markovchain', {'markovchainresult': markovChainResultRecord.text}, { reload: true });
		}else{
			$scope.errors="Text file could not be processed.";
		}
	}
	
	function markovChainErrorHandler(error){
		if(error != null){
			$scope.errors=error.ErrorMessage;
		}else{
			$scope.errors="There was something wrong.";
		}
	}

	$scope.generateText = function(markovchainrequest) {
		// As the controller is initialised we will get the transformed text as the template is loaded.
		MarkovChainService.generateText(markovchainrequest, markovChainResultHandler, markovChainErrorHandler);		
	};
	
});

var animallogicMarkovChainResultCtrl = animallogicMarkovChainModule.controller('animallogicMarkovChainResultCtrl', function($scope, $state, $stateParams) {	
	$scope.markovchainresult = "";
	$scope.markovchainresult = $stateParams.markovchainresult;
	
	$scope.back = function back(){
		$state.go('animallogic.home');	
	}
});

animallogicMarkovChainModule.directive('fileModel', function () {
    return {
      restrict: "A",
      require: '^form',
      
      link: function (scope,elem,attrs, ctrl) {

        elem.on("change", function(changeEvent) {
          console.log("change");
          var reader = new FileReader();
    	  reader.onload = function(e) {
    	      scope.$apply(function() {
    	          scope.algorithmInputForm.file = reader.result;
    	          scope.markovchainrequest.file = reader.result;
    	      });
    	  };
          if (typeof(changeEvent.target.files[0]) === 'object') {
        	  if(changeEvent.target.files[0].name.substr(changeEvent.target.files[0].name.length-4, 4)== ".txt"){
        		  scope.invalidFileType = false;        		  
        	  }else{
        		  scope.invalidFileType = true;
        	  }       	  
        	  reader.readAsText(changeEvent.target.files[0]);
          };          
        });

      }
    };
  });