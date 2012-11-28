'use strict';

/* App Module */

angular.module('addressBook', ['addressBookServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/contact/:contactId', {templateUrl: 'partials/contact-detail.html', controller: ContactController}).
      when('', {templateUrl: 'partials/contact-none.html'}).
      otherwise({redirectTo: ''});
}]);
