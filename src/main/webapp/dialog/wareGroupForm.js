/**
 * Created by Jasmina on 30/05/2017.
 */
app.controller('WareGroupFormController', function ($scope, $http, $state, $mdDialog, wareGroupService, companyService, wareGroup) {

    var editingMode = wareGroup !== null;
    if(editingMode){
        $scope.wareGroup = JSON.parse(JSON.stringify(wareGroup));
    }
    
    companyService.read(function (response) {
        $scope.companies = response.data;
    });

    
    $scope.queryCompanies = function (searchText) {
        if(searchText === null){
            return $scope.companies;
        }
        var queryResults = [];
        for(var i=0; i<$scope.companies.length; i++){
            if($scope.companies[i].name.toLowerCase().match(searchText.toLowerCase())
                || $scope.companies[i].pib === parseInt(searchText)){
                queryResults.push($scope.companies[i]);
            }
        }

        return queryResults;
    };
    
    $scope.submit = function () {
        if(!editingMode){
            wareGroupService.create($scope.wareGroup, function () {
                $scope.close();
            });
        }else {
            wareGroupService.update($scope.wareGroup, function () {
                $scope.close();
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    }
});