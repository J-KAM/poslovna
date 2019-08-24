app.controller('CreateAccountController', function ($scope, $http, $state, $mdDialog, userService, locationService, companyService) {

    $scope.username = {
        exists: true
    };

    locationService.read(function (response) {
        $scope.locations = response.data;
    });

    $scope.queryLocations = function (searchText) {
        if (searchText === null) {
            return $scope.locations;
        }
        var queryResults = [];
        for (var i = 0; i < $scope.locations.length; i++) {
            if ($scope.locations[i].name.toLowerCase().match(searchText.toLowerCase())
                || $scope.locations[i].ptt === parseInt(searchText)) {
                queryResults.push($scope.locations[i]);
            }
        }
        return queryResults;
    };

    companyService.read(function (response) {
        $scope.companies = response.data;
    });

    $scope.queryCompanies = function (searchText) {
        if (searchText === null) {
            return $scope.companies;
        }
        var queryResults = [];
        for (var i = 0; i < $scope.companies.length; i++) {
            if ($scope.companies[i].name.toLowerCase().match(searchText.toLowerCase())
                || $scope.companies[i].pib === parseInt(searchText)){
                queryResults.push($scope.companies[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        userService.create($scope.user, function () {
            $scope.close();
        });
    };

    $scope.usernameChanged = function () {
        if ($scope.user) {
            $http.get('/api/users/' + $scope.user.username).success(function () {
                $scope.username.exists = true;
            }).error(function () {
                $scope.username.exists = false;
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
