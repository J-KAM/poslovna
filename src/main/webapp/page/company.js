/**
 * Created by Alex on 5/30/17.
 */
app.controller('CompanyController', function ($scope, $state, $rootScope, $mdDialog, companyService) {

    $scope.page.current = 3.3;

    var companies = [];

    var loadData = function () {
        companyService.read(function (response) {
            $scope.companies = response.data;
            companies = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
       var searchText = args.searchText;
       if(searchText === null || searchText === "") {
           $scope.companies = companies;
           return;
       }
       var criteria = searchText.match(/\S+/g);
       $scope.companies = [];
       for(var i = 0; i < companies.length; i++) {
           for(var j = 0; j < criteria.length; j++) {
               if (companies[i].id === Number(criteria) ||
                   companies[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                   String(companies[i].pib).toLowerCase().match(criteria[j].toLowerCase()) ||
                   companies[i].address.toLowerCase().match(criteria[j].toLowerCase()) ||
                   companies[i].location.name.toLowerCase().match(criteria[j].toLowerCase())) {
                   if($scope.companies.indexOf(companies[i]) === -1) {
                       $scope.companies.push(companies[i]);}
               }
           }
       }
    });

    var openForm = function (company) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/companyForm.html',
            controller: 'CompanyFormController',
            locals: { company: company}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function() {
        openForm(null);
    });

    $scope.edit = function (company) {
        openForm(company);
    };

    $scope.delete = function (company, event) {
        event.stopPropagation();
        companyService.delete(company.id, function () {
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