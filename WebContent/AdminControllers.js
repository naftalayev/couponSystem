/**
 * admin controller
 * 
 */

var adminApp = angular.module("adminApp", []);

adminApp.controller("adminController", function($scope, $http) {

	$scope.showCompany = false;
	$scope.showCustomer = false;

	$http.get("rest/admin_service/get-all-companies").then(function(response) {
		$scope.companies = response.data.company;
	});

	$http.get("rest/admin_service/get-all-customers").then(function(response) {
		$scope.customers = response.data.customer;
	});

	// ///////////////////////////

	$scope.createCompany = function() {
		var companyToCreate = new Object();
		companyToCreate.id = $scope.compId;
		companyToCreate.compName = $scope.compName;
		companyToCreate.password = $scope.companyPassword;
		companyToCreate.email = $scope.email;
		if(companyToCreate.email==null){
			$scope.compAddMessage="Invalid email address"
		}else{
		var companyCreated = $http.post("rest/admin_service/create-company",
				companyToCreate);
		companyCreated.then(function(response) {
			$scope.compAddMessage = response.data;
			$http.get("rest/admin_service/get-all-companies").then(
					function(response) {
						$scope.companies = response.data.company;
					});

		});

	};}

	// /////////////////////////////////

	$scope.createCustomer = function() {
		var customerToCreate = new Object();
		customerToCreate.id = $scope.customerId;
		customerToCreate.custName = $scope.customerName;
		customerToCreate.password = $scope.customerPassword;
		var customerCreated = $http.post("rest/admin_service/create-customer",
				customerToCreate);
		customerCreated.then(function(response) {
			$scope.custAddMessage = response.data;
			$http.get("rest/admin_service/get-all-customers").then(
					function(response) {
						$scope.customers = response.data;
					});

		});

	};

	// /////////////////////////////////////////////////////////////////////////

	$scope.searchCompany = function() {
		if ($scope.showCompany == false) {
			$scope.showCompany = true;
		}
		$http.get("rest/admin_service/get_company/" + $scope.selectedComp)
				.then(function(response) {
					$scope.company = response.data;
				});
	};

	// //////////////////////////////////////////////////////////////
	$scope.searchCustomer = function() {
		if ($scope.showCustomer == false) {
			$scope.showCustomer = true;
		}
		$http.get("rest/admin_service/get-customer/" + $scope.selectedCustomer)
				.then(function(response) {
					$scope.customer = response.data;
				});
	};

	// /////////////////////////////////////////////////////

	$scope.deleteCompany = function() {
		if ($scope.showCompany == false) {
			$scope.showCompany = true;
		}
		$http.get("rest/admin_service/get_company/" + $scope.selectedComp)
				.then(function(response) {
					$scope.company = response.data;
				});
		var companyToDelete = $scope.company;
		var companyDeleted = $http.put("rest/admin_service/remove-company",
				companyToDelete);
		companyDeleted.then(function(response) {
			$scope.compDelAddMessage = response.data;
			$http.get("rest/admin_service/get-all-companies").then(
					function(response) {
						$scope.companies = response.data;
					});

		});

	}
	//////////////////////////////////////////////////////////////////////////

	$scope.deleteCustomer = function() {
	
		$http.get("rest/admin_service/get-customer/" + $scope.selectedCustomer)
				.then(function(response) {
					$scope.customer = response.data;
				});
		var customerToDelete = $scope.customer
		var customerDeleted = $http.put("rest/admin_service/remove-customer",
				customerToDelete);
		customerDeleted.then(function(response) {
			$scope.custDelAddMessage = response.data;
			$http.get("rest/admin_service/get-all-customers").then(
					function(response) {
						$scope.customers = response.data;
					});

		});

	}
	
	// /////////////////////////////////////////////////////////

	$scope.updateCompany = function() {
		if ($scope.showCompany == false) {
			$scope.showCompany = true;
		}
		$http.get("rest/admin_service/get_company/" + $scope.selectedComp)
				.then(function(response) {
					$scope.company = response.data;
				});
		var companyToUpdate = $scope.company;
		companyToUpdate.password = $scope.updatedPassword
		companyToUpdate.email = $scope.updatedEmail

		if (companyToUpdate.passwors == null) {
			companyToUpdate.password = $scope.company.password
		}
		if (companyToUpdate.email == null) {
			companyToUpdate.email = $scope.company.email
		}
		var companyUpdated = $http.put("rest/admin_service/update-company",
				companyToUpdate);
		companyUpdated.then(function(response) {
			$scope.compUpdateMessage = response.data;
			$http.get("rest/admin_service/get-all-companies").then(
					function(response) {
						$scope.companies = response.data;
					});
		});

	}
////////////////////////////////////////////////////////////////////////	
	$scope.updateCustomer = function() {
		if ($scope.showCustomer == false) {
			$scope.showCustomer = true;
		}
		$http.get("rest/admin_service/get-customer/" + $scope.selectedCustomer)
		.then(function(response) {
			$scope.customer = response.data;
		});
		var customerToUpdate = $scope.customer;
		customerToUpdate.password = $scope.updatedPassword
		
		var customerUpdated = $http.put("rest/admin_service/update-customer",
				customerToUpdate);
		customerUpdated.then(function(response) {
			$scope.custUpdateMessage = response.data;
			$http.get("rest/admin_service/get-all-customers").then(
					function(response) {
						$scope.customers = response.data;
					});
		});
		
	}
	
});
