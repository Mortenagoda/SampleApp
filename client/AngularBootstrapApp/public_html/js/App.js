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

app.run(function($http) {
    $http.get("http://localhost:8080/rest/api/apikeys").success(function (data, status) {
        $http.defaults.headers.common.ApiKey = data.apiKey;
    });
});

var authService = app.factory("authService", ["$http", function($http) {      
        return {
            login: function(emailPassword) {
                return $http.post("http://localhost:8080/rest/auth/login", emailPassword);
            },
            create: function(username, password) {
                return $http.post("http://localhost:8080/rest/auth/credentials", {username: username, password: password});
            }
        };
    }
]);

app.controller("mainCtrl", ["$scope", "authService", function($scope, authService) {
        $scope.main = {
            appName: "SampleApp",
            username: "",
            credentials: {username: "mortena", password: "password"},
            loggedIn: false
        };

        $scope.showLogin = function() {
            $scope.toggle('loginOverlay', 'on');
        };

        $scope.login = function() {
            authService.login({username: $scope.main.credentials.username, password: $scope.main.credentials.password})
                    .success(function(data, status) {
                        $scope.main.username = data.username;
                        $scope.main.credentials.username = "mortena";
                        $scope.main.credentials.password = "password";
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

app.controller("shoppingCtrl", ["$scope", function($scope) {
        $scope.shopping = {
            label: "We are shopping"
        };
    }]);

app.controller("registerCtrl", ["$scope", "authService", function($scope, authService) {
        $scope.newUser = {
            username: "",
            password: "",
            password2: "",
            errorTxt: "",
            msgTxt: ""
        };

        $scope.registerUser = function() {
            authService.create($scope.newUser.username, $scope.newUser.password)
                    .success(
                            function(newUserData, status) {
                                $scope.newUser = {};
                                
                            }
                    )
                    .error(
                            function(data, status) {
                                $scope.newUser.errorTxt = "Unable to create user!";
                            }
                    );
        };

    }]);