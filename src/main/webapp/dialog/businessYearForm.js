/**
 * Created by JELENA on 3.6.2017.
 */

app.controller('BusinessYearFormController', function ($scope, $http, $state, $mdDialog, businessYearService, businessYear) {

    var editingMode = businessYear !== null;
    if (editingMode) {
        $scope.businessYear = JSON.parse(JSON.stringify(businessYear));
    }

    $scope.submit = function () {
        if (!editingMode) {
            businessYearService.create($scope.businessYear, function () {
                $scope.close();
            });
        } else {
            businessYearService.update($scope.businessYear, function () {
                $scope.close();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});

