"use strict";
document.addEventListener("DOMContentLoaded", function () {
  $("table>tbody>tr").click(function (event) {
    rowClicked(this, event);
  });
});
document.body.onkeydown = function (event) {
  if (event.keyCode == 65 && !$(".dynamic-filter").is(":focus")) {
    if (hasSelected()) {
      clearSelection();
    } else {
      selectAll();
    }
  }
};

function selectAll() {
  console.log("select all");
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

var tmp;
function rowClicked(self, event) {
  if ($(self).hasClass("selected")) {
    $(self).removeClass("selected");
  } else {
    $(self).addClass("selected");
  }
}
