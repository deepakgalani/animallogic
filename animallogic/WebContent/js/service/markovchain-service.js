/**
 * 
 */

var markovChainServiceModule = angular.module('animallogic.markovchain.service', []);

markovChainServiceModule.service("MarkovChainService", function ($http){
	
	function generateText(markovchainrequest, callBackFunction, errorCallBackFunction){
		//callBackFunction({"text":markovchainrequest.file});
		$http.post("http://localhost:8087/animallogic/api/markovchain", markovchainrequest, {headers: {'Content-Type': 'application/json'}})
		.then(function(response) {
			callBackFunction((response != null? response.data: null));
		}, function (response) {
			errorCallBackFunction((response != null? response.data: null));
		});
	}	
	
	this.generateText = generateText;	
});