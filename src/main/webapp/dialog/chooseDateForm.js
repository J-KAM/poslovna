app.controller('ChooseDateController', function ($scope, $http, $state, $mdDialog, warehouseCard, warehouseCardService) {

    $scope.currentDate = new Date();

    $scope.generateReport = function() {
        $scope.data.warehouseCardDTO = warehouseCard;
        warehouseCardService.generateReport($scope.data, function (response) {
            $mdDialog.hide();
            if(response.data !== "") {
                window.open("data:application/pdf;base64, " + response.data);
            } else {
                showFailureInfo();
            }
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    }


    var showFailureInfo = function() {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Neuspeh')
                .content('Izveštaj za izabranu karticu nije kreiran.')
                .ok('Ok')
        );
    };
});
