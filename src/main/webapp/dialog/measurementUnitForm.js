/**
 * Created by Alex on 5/21/17.
 */
app.controller('MeasurementUnitFormController', function ($scope, $http, $state, $mdDialog, measurementUnitService, measurementUnit) {

    var editingMode = measurementUnit !== null;
    if (editingMode) {
        $scope.measurementUnit = JSON.parse(JSON.stringify(measurementUnit));
    }

    $scope.submit = function () {
        if (!editingMode) {
            measurementUnitService.create($scope.measurementUnit, function () {
                $scope.close();
            });
        } else {
            measurementUnitService.update($scope.measurementUnit, function () {
                $scope.close();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
