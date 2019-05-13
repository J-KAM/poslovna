/**
 * Created by JELENA on 3.6.2017.
 */

app.controller('WareFormController', function ($scope, $http, $state, $mdDialog, wareService, measurementUnitService, wareGroupService, ware) {

    var editingMode = ware !== null;
    if(editingMode) {
        $scope.ware = JSON.parse(JSON.stringify(ware));
    }

    measurementUnitService.read(function (response) {
        $scope.measurementUnits = response.data;
    });

    $scope.queryMeasurementUnits = function (searchText) {
        if (searchText === null) {
            return $scope.measurementUnits;
        }
        var queryResults = [];
        for (var i=0; i < $scope.measurementUnits.length; i++) {
            if ($scope.measurementUnits[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.measurementUnits[i])
            }
        }
        return queryResults;
    };

    wareGroupService.read(function (response) {
       $scope.wareGroups = response.data;
    });

    $scope.queryWareGroups = function (searchText) {
      if(searchText === null) {
          return $scope.wareGroups;
      }
      var queryResults = [];
      for (var i=0; i < $scope.wareGroups.length; i++) {
          if ($scope.wareGroups[i].name.toLowerCase().match(searchText.toLowerCase())) {
              queryResults.push($scope.wareGroups[i])
          }
      }
      return queryResults;
    };

    $scope.submit = function () {
      if (!editingMode) {
          wareService.create($scope.ware, function() {
              $scope.close();
          });
      }   else {
          wareService.update($scope.ware, function () {
             $scope.close();
          });
      }
    };

    $scope.close = function () {
      $mdDialog.hide();
    };
});