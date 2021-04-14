"use strict";

document.addEventListener("DOMContentLoaded", function () {
  $("table>tbody>tr").click(function (event) {
    rowClicked(this, event);
  });
});

document.body.onkeydown = function (event) {
  //if key "a" pressed and if input filter not focused
  if (event.keyCode == 65 && !$(".dynamic-filter").is(":focus")) {
    if (hasSelected()) {
      clearSelection();
    } else {
      selectAll();
    }
  }
};

function selectAll() {
  for (const i of $("table>tbody>tr")) {
    if (!$(i).hasClass("hidden")) {
      $(i).addClass("selected");
    }
  }
}

function clearSelection() {
  for (const i of $("table>tbody>tr")) {
    $(i).removeClass("selected");
  }
}

function hasSelected() {
  for (const i of $("table>tbody>tr")) {
    if ($(i).hasClass("selected")) {
      return true;
    }
  }
  return false;
}

function rowClicked(self, event) {
  if ($(self).hasClass("selected")) {
    $(self).removeClass("selected");
  } else {
    $(self).addClass("selected");
  }
}
