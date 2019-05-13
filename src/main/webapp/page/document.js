/**
 * Created by Jasmina on 05/06/2017.
 */
app.controller('DocumentController', function ($scope, $state, $rootScope, $mdDialog, documentService, documentUnitService, warehouseService) {

    $scope.page.current = 3.9;

    $scope.status = {};
    $scope.status["PENDING"] = "U fazi formiranja";
    $scope.status["BOOKED"] = "Proknjižen";
    $scope.status["REVERSED"] = "Storniran";

    $scope.documentType = {};
    $scope.documentType["RECEIPT"] = "Primka";
    $scope.documentType["DISPATCH"] = "Otpremnica";
    $scope.documentType["INTER_WAREHOUSE_TRAFFIC"] = "Međumagacinski promet";

    var documents = [];

    var loadData = function () {
        if (warehouseService.getActiveWarehouse().id === -1) {
            documentService.read(function (response) {
                $scope.documents = response.data;
                documents = response.data;
            });
        } else {
            documentService.readByWarehouse(warehouseService.getActiveWarehouse(), function (response) {
                $scope.documents = response.data;
                documents = response.data;
            });
        }
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if(searchText === null || searchText === "") {
            $scope.documents = documents;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.documents = [];
        for(var i = 0; i < documents.length; i++) {
            for(var j = 0; j < criteria.length; j++) {
                if (documents[i].id === Number(criteria) ||
                    $scope.documentType[documents[i].documentType.toString()].toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(documents[i].serialNumber).toLowerCase().match(criteria[j].toLowerCase()) ||
                    $scope.status[documents[i].status.toString()].toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(documents[i].businessYear.year).toLowerCase().match(criteria[j].toLowerCase()) ||
                    documents[i].warehouse.name.toLowerCase().match(criteria[j].toLowerCase())) {
                    if($scope.documents.indexOf(documents[i]) === -1) {
                        $scope.documents.push(documents[i]);}
                }
            }
        }
    });


    var openForm = function (document) {
        $mdDialog.show({
            parent: angular.element(this.document.body),
            templateUrl: 'dialog/documentForm.html',
            controller: 'DocumentFormController',
            locals: {document: document}
        }).finally(function () {
            loadData();
        });
    };

    var openUnitsPreview = function (document) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/documentUnitPreview.html',
            controller: 'DocumentUnitPreviewController',
            locals: {document: document}
        });
    };

    var openUnitForm = function (document, documentUnit) {
        if (document.status !== "PENDING") {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Knjiženje dokumenta')
                    .textContent('Dokument mora biti u fazi formiranja da biste mogli dodavati nove stavke!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/documentUnitForm.html',
            controller: 'DocumentUnitFormController',
            locals: {
                document: document,
                documentUnit: documentUnit
            }
        }).finally(function () {
            loadData();
        })
    };


    $scope.$on('add', function () {
        openForm(null);
    });

    $scope.checkBoxClick = function (reverse, event) {
        event.stopPropagation();
    }

    $scope.edit = function (document) {
        if (document.status !== "PENDING") {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Izmena dokumenta')
                    .textContent('Dokument se može menjati isključivo dok je u statusu formiranja!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        openForm(document);
    };

    $scope.delete = function (document, event) {
        event.stopPropagation();
        if (document.status !== "PENDING") {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Brisanje dokumenta')
                    .textContent('Brisanje dokumenata koji nisu u statusu formiranja nije dozvoljeno!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        documentService.delete(document.id, function () {
            loadData();
        });
    };

    $scope.showUnits = function (document, event) {
        event.stopPropagation();
        openUnitsPreview(document);
    };

    $scope.addUnit = function (document, event) {
        event.stopPropagation();
        if (document.status !== "PENDING") {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Dodavanje novih stavki dokumenta')
                    .textContent('Dodavanje novih stavki dokumenta nije moguće. Dokument je već proknjižen ili storniran!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        openUnitForm(document, null);
    };

    $scope.book = function (document, event) {
        event.stopPropagation();
        documentUnitService.read(document.id, function (response) {
            $scope.documentUnits = response.data;
            if ($scope.documentUnits.length == 0) {
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .clickOutsideToClose(true)
                        .title('Knjiženje dokumenta')
                        .textContent('Ne možete knjižiti dokument koji nema nijednu stavku!')
                        .ariaLabel('Alert knjizenje')
                        .ok('OK')
                        .targetEvent(event)
                );
                return;
            }
        });

        if (!$scope.dateRegular(document.establishmentDate)) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Knjiženje dokumenta')
                    .textContent('Datum formiranja dokumenta mora biti manji od današnjeg datuma!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        if (document.status != "BOOKED" && document.reverse) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Knjiženje dokumenta')
                    .textContent('Ne možete stornirati dokument koju prethodno nije proknjižen!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        if (document.status == "REVERSED") {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Knjiženje dokumenta')
                    .textContent('Ne možete knjižiti dokument koju već storniran!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        if (document.status == "BOOKED" && !document.reverse) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .clickOutsideToClose(true)
                    .title('Knjiženje dokumenta')
                    .textContent('Ne možete knjižiti dokument koji već proknjižen!')
                    .ariaLabel('Alert knjizenje')
                    .ok('OK')
                    .targetEvent(event)
            );
            return;
        }
        documentService.book(document, function () {
            console.log(document);
            loadData();
        });
    };

    $scope.dateRegular = function (establishmentDate) {
        var establishmentDate = new Date(establishmentDate);
        var currentDate = new Date();
        if (establishmentDate > currentDate) {
            return false;
        } else {
            return true;
        }
    };

    $scope.$on('warehouseChanged', function() {
        loadData();
    });

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