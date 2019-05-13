/**
 * Created by Ana on 05/29/17.
 */
'use strict';

var app = angular.module('app', ['ui.router', 'ngMessages', 'ngMaterial', 'md.data.table']);

app.factory('authInterceptor', ['$q', '$injector', function ($q, $injector) {
    var authInterceptor = {
        responseError: function (response) {
            var $state = $injector.get('$state');
            if ($state.current.name !== 'login' && response.status == 401) {
                $state.transitionTo('login');
            }
            return $q.reject(response);
        }
    };
    return authInterceptor;
}]);

app.config(function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider, $mdDateLocaleProvider) {

    $mdThemingProvider.theme('default')
        .primaryPalette('indigo')
        .accentPalette('blue');

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('authInterceptor');

    $stateProvider
        .state('login', {
            url: '/login',
            controller: 'LoginController',
            templateUrl: 'page/login.html'
        })
        .state('navigation', {
            url: '',
            abstract: true,
            controller: 'NavigationController',
            templateUrl: 'page/navigation.html'
        })
        .state('navigation.home', {
            url: '/home',
            controller: 'HomeController',
            templateUrl: 'page/home.html'
        })
        .state('navigation.measurementUnit', {
            url: '/measurementUnit',
            controller: 'MeasurementUnitController',
            templateUrl: 'page/measurementUnit.html'
        })
        .state('navigation.company', {
            url: '/company',
            controller: 'CompanyController',
            templateUrl: 'page/company.html'
        })
        .state('navigation.location', {
            url: '/location',
            controller: 'LocationController',
            templateUrl: 'page/location.html'
        })
        .state('navigation.wareGroup', {
            url: '/wareGroup',
            controller: 'WareGroupController',
            templateUrl: 'page/wareGroup.html'
        })
        .state('navigation.ware', {
            url: '/ware',
            controller: 'WareController',
            templateUrl: 'page/ware.html'
        })
        .state('navigation.businessPartner', {
            url: '/businessPartner',
            controller: 'BusinessPartnerController',
            templateUrl: 'page/businessPartner.html'
        })
        .state('navigation.businessYear', {
            url: '/businessYear',
            controller: 'BusinessYearController',
            templateUrl: 'page/businessYear.html'
        })
        .state('navigation.warehouse', {
            url: '/warehouse',
            controller: 'WarehouseController',
            templateUrl: 'page/warehouse.html'
        })
        .state('navigation.document', {
            url: '/document',
            controller: 'DocumentController',
            templateUrl: 'page/document.html'
        })
        .state('navigation.warehouseCard', {
        url: '/warehouseCard',
        controller: 'WarehouseCardController',
        templateUrl: 'page/warehouseCard.html'
    });

});