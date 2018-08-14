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
      wrap: true
    });
  },
 
  handleFlatpickr: function handleFlatpickr() {

    this._fp9();
  }
};

flatpickrDemo.init();