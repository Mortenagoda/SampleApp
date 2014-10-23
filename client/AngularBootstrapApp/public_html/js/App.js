var app = angular.module("myApp", ["ngRoute", "ngTouch", "mobile-angular-ui"]);

app.config(function($routeProvider) {
            $routeProvider.when("/", {
                templateUrl: 'partials/main.html',
                controller: 'mainCtrl'
            }).when("/shopping", {
                templateUrl: 'partials/shopping.html',
                controller: "shoppingCtrl"
            }).when("/register", {
                templateUrl: "partials/register.html",
                controller: "registerCtrl"
            });
        });

var authService = app.factory("authService", ["$http", function ($http) {
        return {
            login: function (emailPassword) {
                return $http.post("http://localhost:8080/rest/auth/login", emailPassword);
            }
        };
    }
]);
var registerService = app.factory("registerService", ["$http", function ($http) {
        return {
            login: function (emailPassword) {
                return $http.post("http://localhost:8080/rest/auth/login", emailPassword);
            }
        };
    }
]);

app.controller("mainCtrl", ["$scope", "authService", function ($scope, authService) {
        $scope.main = {
            appName: "SampleApp",
            user: {},
            credentials: {email: "admin@domain.com", password: "password"},
            loggedIn: false
        };
        
        $scope.showLogin = function() {
            $scope.toggle('loginOverlay', 'on');
        };

        $scope.login = function () {
            authService.login({email: $scope.main.credentials.email, password: $scope.main.credentials.password})
                    .success(function (data, status) {
                        $scope.main.user = data.person;
                        $scope.main.credentials.email = "admin@domain.com";
                        // $scope.main.credentials.password = "";
                        $scope.main.loggedIn = true;
                    }).error(function(data, status) {
                        $scope.main.user = {};
                        $scope.main.loggedIn = false;
                    });
        };
        
        $scope.logout = function() {
            $scope.main.credentials.email = "admin@domain.com";
            $scope.main.credentials.password = "";
            $scope.main.user = {};
            $scope.main.loggedIn = false;
        };
    }]);

app.controller("shoppingCtrl", ["$scope", function ($scope) {
        $scope.shopping = {
            label: "We are shopping"
        };
}]);

app.controller("registerCtrl", ["$scope", "registerService", function($scope, registerService) {
        $scope.newUser = {
            email: "",
            password: "",
            password2: ""
        };
        
        $scope.registerUser = function() {
            Console.log("Register clicked.");
        };
        
}]);