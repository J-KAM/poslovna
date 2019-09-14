/**
 * Created by Alex on 5/30/17.
 */
app.controller('CompanyFormController', function ($scope, $http, $state, $mdDialog, companyService, locationService, company) {

    var editingMode = company !== null;
    if (editingMode) {
        $scope.company = JSON.parse(JSON.stringify(company));
        $scope.disabled = true;
    }

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

    $scope.submit = function () {
        if (!editingMode) {
            companyService.create($scope.company, function () {
                $scope.close();
            }, function (response) {
                $scope.companyForm.pib.$error.uniqueError = response.status === 409;
            });
        } else {
            companyService.update($scope.company, function () {
                $scope.close();
            }, function (response) {
                $scope.companyForm.pib.$error.uniqueError = response.status === 409;
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});