/*
Библиотека позволяет показывать таблицы по запросу из базы данных в html файле в скрытой строке с id=row_pattern необходимо указать шаблон строки. Каждая колонка тег td должны иметь первым классом название колонки из базы данных
Пример:
<tr id=row_pattern hidden=true class="employee_id">
            <td class="fio"></td>
            <td class="position select"> </td>
            <td class="department select"> </td>
        </tr>
Скрипт заполняет таблицу с id="table_anchor"
*/
var table = {};

function sendPost(path, param, run) {
  $.ajax({
    url: path,
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify(param),
    beforeSend: function (jqXHR, settings) {
      jqXHR.setRequestHeader(table.header, table.token);
    },
    complete: function (jqXHR, textStatus) {
      if (textStatus == "success") {
        run(jqXHR.responseJSON);
      }
      if (jqXHR.status == 500) {
        console.log(jqXHR.responseJSON);
      }
    },
  });
}

document.addEventListener("DOMContentLoaded", () => {
  init();
});

function init() {
  table.token = $("meta[name='_csrf']").attr("content");
  table.header = $("meta[name='_csrf_header']").attr("content");
  table.address = window.location.pathname;
  sendPost(`${table.address}/get`, {}, (value) => {
    table.data = value;
    table.columnNames = getColumnNames($("#row_pattern"));
    table.selectNames = getSelectNames($("#row_pattern"));
    drawTable(table.data);
    initSelect();
    // setListeners();
  });
}

function getColumnNames(pattern) {
  const tds = $(pattern).find("td");
  let attributes = [];
  for (let item of tds) {
    attributes.push(item.classList[0]);
  }
  return attributes;
}

function getSelectNames(pattern) {
  const selects = $(pattern).find(".select");
  let selectNames = [];
  for (const item of selects) {
    selectNames.push(item.classList[0]);
  }
  return selectNames;
}

function drawTable(data) {
  for (let item of data) {
    addRow(item);
  }
}

function addRow(row) {
  let pattern = $("#row_pattern");
  let newRow = $(pattern).clone().removeAttr("hidden").removeAttr("id");
  $(pattern).parent().append(newRow);
  for (let attribute of table.columnNames) {
    $(newRow)
      .find("." + attribute)
      .text(row[attribute]);
    const name = $(`#row_pattern>.${attribute}`).attr("name");
    if (name && row[name]) {
      $(newRow).find(`.${attribute}`).attr(name, `${row[name]}`);
    }
  }
}

function setListeners() {
  for (let item of table.columnNames) {
    console.log(item);
  }
  $(".select").click(clickedSelect);
}

function clickedSelect() {
  console.log(this);
}

function initSelect() {
  table.selections = new Map();
  for (const item of table.selectNames) {
    // const url = `/get_${item}s`;
    const url = `/${item}s/get_json`;
    sendPost(url, {}, function (data) {
      table.selections.set(item + "s", data);
    });

    $(".select").click(itemSelect);
  }
}
var k;
function itemSelect() {
  let current = this;
  // const parent = $(this).parent();
  console.log(parent);
  const item = table.selections.get(current.classList[0] + "s");
  $(current).unbind("click");
  $(current).html("");
  let tmp = $("<div></div>");
  $(current).append($(tmp));
  $(tmp).select2({
    data: item,
    width: "100%",
  });

  $(tmp).on("select2:select", function (e) {
    $(tmp).remove();
    //set text to the current cell
    $(current).text(e.params.data.text);
    // k = $(current).parent();
    // k = $(current);
    //set id to the current cell
    const id = e.params.data.id;
    $(current).attr($(current).attr("name"), id);
    let employees = ``;
    console.log(id);
    console.log("************");
    save($(current).parent());
    // let fn = itemSelect.bind(this);
    //Bind a new click listener
    $(current).click(itemSelect);
  });

  // console.log(this);
  // $(`.${item}.select`).select2({
  //   data: table.selections.get(item),
  // });
  // $(`.${item}.select`).on("select2:select", function (e) {
  //   console.log("select");
  // });
}

function save(row) {
  data = {};
  for (const item of row.children()) {
    data[$(item).attr("name")] = $(item).attr($(item).attr("name"));
  }
  needFill = Object.keys(data).find((key) => data[key] == undefined);
  if (needFill) {
    $(row).find(`[name=${needFill}]`).text("Для сохранения заполните это поле");
  } else {
    sendPost("/muTrainings/add", data, (v) => console.log(v));
  }
}

function addItem(item) {
  var entry = {};
  for (var i = 1; i < $("#addEntry").children().length - 1; i++) {
    var currentVal = $("#addEntry").children().eq(i).children().eq(0).val();
    var inputVarName = getInputVarName(i, $("#addEntry"));
    entry[inputVarName] = currentVal;
  }
  console.log(entry);
  sendPost(`${window.location.pathname}/add`, entry, (item) => {
    addNewRow(item);
    $("#inputText").val("");
    $("#inputText").focus();
  });
}

function getItemFromRow(row) {
  let item = {};
  item.id = $(row).attr("id").substr(2);
  for (let i = 1; i < row.children().length - 1; i++) {
    let currentVal = $(row).children().eq(i).children().eq(0).text();
    let inputVarName = getInputVarName(i, row);
    item[inputVarName] = currentVal;
  }
  return item;
}

function deleteItem(item) {
  //Select current row
  let row = $(item).closest(".deleteEntry");
  console.log(JSON.stringify(getItemFromRow(row)));
  sendPost(`${window.location.pathname}/del`, getItemFromRow(row), () => {
    console.log("success");
    row.remove();
  });
}

function getInputVarName(num, row) {
  str = getVarInputByColumn(num, row);
  //str = str.substr(5)
  //Cut from the first Uppercase character to space
  let first = str.search(/[A-Z]/);
  let last = str.search(/\s/);
  last = last == -1 ? undefined : last;
  str = str.substring(first, last);
  return str[0].toLocaleLowerCase() + str.substr(1);
}

function getVarInputByColumn(num, row) {
  return $(row).children().eq(num).children().eq(0).attr("class");
}

function sendPost2(path, param, run) {
  console.log(param);
  console.log(path);
  console.log(
    "****************************************************************************"
  );
  $.ajax({
    url: path,
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify(param),
    beforeSend: function (jqXHR, settings) {
      jqXHR.setRequestHeader(header, token);
    },
    complete: function (jqXHR, textStatus) {
      if (textStatus == "success") {
        run(jqXHR.responseJSON);
        console.log(jqXHR.responseJSON);
      }
      if (jqXHR.status == 500) {
        console.log(jqXHR.responseJSON);
      }
    },
  });
}

function addNewRow(entry) {
  console.log(JSON.stringify(entry));
  let currentRow = $("#addEntry");
  let last = $(currentRow).prev();
  let newRow = $(NEW_ROW_PATTERN);
  $(newRow)
    .children()
    .eq(0)
    .text(parseInt($(newRow).children().eq(0).text()) + 1);
  let nom = parseInt($(last).children().eq(0).text());
  if (nom == undefined) {
    nom = 1;
  } else {
    nom = nom + 1;
  }
  $(newRow).children().eq(0).html(`<div>${nom}</div>`);
  $(newRow).attr("id", `id${entry.id}`);
  for (let i = 1; i < $("#addEntry").children().length - 1; i++) {
    let name = getInputVarName(i, currentRow);
    console.log(
      "var name " +
        $(newRow).children().eq(i).children().eq(0).text(entry[name]).html()
    );
    $(newRow).children().eq(i).children().eq(0).text(entry[name]);
  }
  console.log("nt" + $(newRow).html());
  $(currentRow).before($(newRow));
}
