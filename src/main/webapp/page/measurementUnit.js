/**
 * Created by Alex on 5/21/17.
 */
app.controller('MeasurementUnitController', function ($scope, $state, $rootScope, $mdDialog, measurementUnitService) {

    $scope.page.current = 3.1;

    var measurementUnits = [];

    var loadData = function () {
        measurementUnitService.read(function (response) {
            $scope.measurementUnits = response.data;
            measurementUnits = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
       var searchText = args.searchText;
       if(searchText === null || searchText === "") {
            $scope.measurementUnits = measurementUnits;
            return;
       }
       var criteria = searchText.match(/\S+/g);
       $scope.measurementUnits = [];
       for(var i = 0; i < measurementUnits.length; i++) {
           for(var j = 0; j < criteria.length; j++) {
               //TODO: Add reverse criteria unit check if neccessary
               if (measurementUnits[i].id === Number(criteria) ||
                   measurementUnits[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                   measurementUnits[i].label.toLowerCase().match(criteria[j].toLowerCase())) {
                   if ($scope.measurementUnits.indexOf(measurementUnits[i]) === -1) {
                       $scope.measurementUnits.push(measurementUnits[i]);
                   }
               }
           }
       }
    });

    var openForm = function (measurementUnit) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/measurementUnitForm.html',
            controller: 'MeasurementUnitFormController',
            locals: { measurementUnit: measurementUnit}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function() {
        openForm(null);
    });

    $scope.edit = function (measurementUnit) {
        openForm(measurementUnit);
    };

    $scope.delete = function (measurementUnit, event) {
        event.stopPropagation();
        measurementUnitService.delete(measurementUnit.id, function () {
            loadData();
        });
    };

    $scope.options = {
        rowSelection: true,
        boundaryLinks: true
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});