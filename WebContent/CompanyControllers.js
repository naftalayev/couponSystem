/**
 * 
 */

var comapnyApp = angular.module("companyApp", []);

comapnyApp.controller("CompanyController", function($scope, $http) {

	$scope.showCoupon = false;
	$scope.showCouponByType = false;

	$http.get("rest/company-service/get-all-coupon").then(function(response) {
		$scope.coupons = response.data.coupon;
	});

	// /////////////////////////////////////////////////////////////////

	$scope.createCoupon = function() {
		var couponToCreate = new Object();
		couponToCreate.id = $scope.coupId;
		couponToCreate.title = $scope.coupTitle;
		var stringStartDate = $scope.startDate;
		couponToCreate.startDate = new Date(stringStartDate);
		alert(couponToCreate.startDate);
		var stringEndDate = $scope.endDate;
		couponToCreate.endDate = new Date(stringEndDate);
		alert(couponToCreate.endDate);
		couponToCreate.amount = $scope.amount;
		couponToCreate.type = $scope.coupType;
		couponToCreate.message = $scope.message;
		couponToCreate.price = $scope.price;
		couponToCreate.image = $scope.image;
		var couponCreated = $http.post("rest/company-service/create-coupon",
				couponToCreate);

		couponCreated.then(function(response) {
			$scope.coupMessage = response.data;
			alert("3");
			$http.get("rest/company-service/get-all-coupon").then(
					function(response) {
						$scope.coupons = response.data;
					});

		});

	};
	// ////////////////////////////////////////////////////////

	$scope.searchCoupon = function() {
		if ($scope.showCoupon == false) {
			$scope.showCoupon = true;
		}
		$http.get("rest/company-service/get-coupon/" + $scope.selectedCoupon)
				.then(function(response) {
					$scope.coupon = response.data;
				});
	};

	// /////////////////////////////////////////////////////////

	$scope.searchCouponByType = function() {
		if ($scope.showCouponByType == false) {
			$scope.showCouponByType = true;
		}

		$http.get("rest/company-service/get-coupon-by-type/"
						+ $scope.selectedType).then(function(response) {
			$scope.couponsByType = response.data.coupon;
		});
	}

	// ///////////////////////////////////////////////////////////////////
	
	$scope.deleteCoupon = function() {
		if ($scope.showCoupon == false) {
			$scope.showCoupon = true;
		}
		$http.get("rest/company-service/get-coupon/" + $scope.selectedCoupon)
				.then(function(response) {
					$scope.coupon = response.data;
				});
		var couponToDelete = $scope.coupon;
		var couponDeleted = $http.put("rest/company-service/remove-coupon",
				couponToDelete);
		couponDeleted.then(function(response) {
			$scope.coupDelAddMessage = response.data;
			$http.get("rest/company-service/get-all-coupon").then(
					function(response) {
						$scope.coupons = response.data;
					});

		});

	}
//////////////////////////////////////////////////////////////////////////////////////////// update coupon
	$scope.updateCoupon = function() {
		if ($scope.showCoupon == false) {
			$scope.showCompany = true;
		}
		$http.get("rest/company-service/get-coupon/" + $scope.selectedCoupon)
		.then(function(response) {
			$scope.coupon = response.data;
		});
		var couponToUpdate = $scope.coupon;
		couponToUpdate.endDate = $scope.updatedEndDate
		couponToUpdate.price = $scope.updatedPrice

		if (couponToUpdate.endDate == null) {
			couponToUpdate.endDate = $scope.coupon.endDate
		}
		if (couponToUpdate.price == null) {
			couponToUpdate.price = $scope.coupon.price
		}
		var couponUpdated = $http.put("rest/company-service/update-coupon",
				couponToUpdate);
		couponUpdated.then(function(response) {
			$scope.coupUpdateMessage = response.data;
			$http.get("rest/company-service/get-all-coupon").then(
					function(response) {
						$scope.coupons = response.data;
					});

		});

	}
	
})