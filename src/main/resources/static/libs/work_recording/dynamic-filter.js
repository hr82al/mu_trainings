"use strict";
let table;
document.addEventListener("DOMContentLoaded", function () {
  table = $("table.filter");
});
var tmp;

function filterChanged(self) {
  let filters = self.value.split(",");
  for (const row of $(table).find("tr")) {
    $(row).removeClass("hidden");
  }

  console.log(filters);
  for (const filter of filters) {
    for (const row of $(table).find("tbody>tr")) {
      if ($(row).hasClass("hidden")) {
        continue;
      }
      if (!isRowIncludes(row, filter)) {
        $(row).addClass("hidden");
      }
    }
  }
}

function isRowIncludes(row, filter) {
  for (const column of $(row).find("td")) {
    if ($(column).text().includes(filter)) {
      return true;
    }
  }
  return false;
}
