'use strict';

// Flatpickr Demo
// =============================================================


var flatpickrDemo = {
  init: function init() {

    this.bindUIActions();
  },
  bindUIActions: function bindUIActions() {

    // event handlers
    this.handleFlatpickr();
  },
  _fp9: function _fp9() {
    // wrap element
    return flatpickr('#flatpickr9', {
      disableMobile: true,
      wrap: true,
      dateFormat: "d/m/Y"
    });
  }
  ,
  _fp10: function _fp10() {
    // wrap element
    return flatpickr('#flatpickr10', {
        static : true,
      disableMobile: true,
      wrap: true,
      dateFormat: "d/m/Y"
    });
  },
 
  handleFlatpickr: function handleFlatpickr() {

    this._fp9();
    this._fp10();
  }
};

flatpickrDemo.init();