/**
 * Created by JELENA on 3.6.2017.
 */

app.controller('BusinessPartnerFormController', function ($scope, $http, $state, $mdDialog, businessPartnerService, locationService, companyService, businessPartner) {

    var editingMode = businessPartner !== null;
    if (editingMode) {
        $scope.businessPartner = JSON.parse(JSON.stringify(businessPartner));
        $scope.disabled = true;
    } else {
        $scope.businessPartner = {};
    }

    $scope.partnershipTypes = {};
    $scope.partnershipTypes["BUYER"] = "Kupac";
    $scope.partnershipTypes["SUPPLIER"] = "Dobavljač";
    $scope.partnershipTypes["ALL"] = "Kupac/Dobavljač";

    $scope.reverseDict = {};
    $scope.reverseDict["Kupac"] = "BUYER";
    $scope.reverseDict["Dobavljač"] = "SUPPLIER";
    $scope.reverseDict["Kupac/Dobavljač"] = "ALL";

    $scope.partnershipType = businessPartner !== null ? $scope.partnershipTypes[businessPartner.partnershipType]: "Kupac/Dobavljač";

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
        if (!editingMode) {
            businessPartnerService.create($scope.businessPartner, function () {
                $scope.close();
            }, function (response) {
                $scope.businessPartnerForm.pib.$error.uniqueError = response.status === 409;
            });
        } else {
            businessPartnerService.update($scope.businessPartner, function () {
                $scope.close();
                $scope.disabled = false;
            }, function (response) {
                $scope.businessPartnerForm.pib.$error.uniqueError = response.status === 409;
            });
        }
    };

    $scope.setPartnershipType = function () {
        $scope.businessPartner.partnershipType = $scope.reverseDict[$scope.partnershipType];
    };

    $scope.setPartnershipType();

    $scope.close = function () {
        $mdDialog.hide();
    };
});