/**
 * Created by Jasmina on 29/05/2017.
 */
app.controller('LocationFormController', function ($scope, $http, $state, $mdDialog, locationService, location) {

    var editingMode = location !== null;
    if(editingMode){
        $scope.location = JSON.parse(JSON.stringify(location));
    }

    $scope.submit = function () {
        if(!editingMode){
            locationService.create($scope.location, function () {
                $scope.close();
            });
        } else {
            locationService.update($scope.location, function () {
                $scope.close();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});