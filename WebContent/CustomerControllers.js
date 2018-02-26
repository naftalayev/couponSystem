/**
 * 
 */
var customerApp = angular.module("customerApp", []);

customerApp.controller("customerController", function($scope, $http) {
	

	$scope.showCoupon = false;
	$scope.showCouponByType = false;
	$scope.showCouponByPrice = false;

	$http.get("rest/customer-service/get-all-coupon-customer").then(function(response) {
		$scope.coupons = response.data.coupon;
	});

//	///////////////////////////////////////////////////// 
	$scope.purchaseCoupon= function (x){
		
		$http.post("rest/customer-service/purchase-coupon",x)
		.then(function(response) {
			$scope.purchaseMessage = response.data;
		}
	);
		
		
	}
	
	///////////////////////////////////////////////
	$scope.getAllMyCoupons= function() {
		if ($scope.showCoupon==false){
			$scope.showCoupon=true
		}
		$http.get("rest/customer-service/get-all-purchased").then(function(response) {
			$scope.purchasedCoupons = response.data.coupon;
		}
	);
		
		
	}
	
	///////////////////////////////////////////////////////
	
	$scope.getMyCouponsByType=function(){
		if ($scope.showCouponByType==false){
			$scope.showCouponByType=true
		}
		$http.get("rest/customer-service/get-all-purchased-coupon-by-type/"+$scope.selectedType).then(function(response) {
			$scope.allCouponsByType = response.data.coupon;
		}
		
	)}
	/////////////////////////////////////////////////////////////////
	$scope.getMyCouponsByPrice=function(){
		if ($scope.showCouponByPrice==false){
			$scope.showCouponByPrice=true
		}
		$http.get("rest/customer-service/get-all-purchased-coupon-by-price/"+$scope.price).then(function(response) {
			$scope.allCouponsByPrice = response.data.coupon;
		}
		
		)}
	

	

})