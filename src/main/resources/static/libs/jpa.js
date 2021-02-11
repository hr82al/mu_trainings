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
  table.address = $("#row_pattern").attr("address");
  table.sectionAddress = $("#section_pattern").attr("address");
  if (table.address) {
    table.columnNames = getColumnNames($("#row_pattern"));
    table.selectNames = getSelectNames($("#row_pattern"));
    if (!table.sectionAddress) {
      sendPost(`${table.address}`, {}, (value) => {
        table.data = value;
        drawTable(table.data);
        initSelect();
      });
    } else {
      drawSection();
    }
  }

  drawTh();
}

function drawSection() {
  sendPost(table.sectionAddress, {}, (data) => {
    const sections = data;
    let employees = [];
    let counter = data.length;
    for (let item of data) {
      sendPost(`${table.address}`, { departmentId: item.id }, (value) => {
        employees[item.id] = value;
        if (--counter == 0) {
          for (const department of sections) {
            addItem2(department, $("#section_pattern"));
            for (const employee of employees[department.id]) {
              addItem2(employee, $("#row_pattern"));
            }
          }
          drawLastDates();
        }
      });
    }
  });
}

function drawLastDates() {
  const COLUMNS_NUM =
    $("#th_pattern").parent().children().length -
    1 -
    $("#row_pattern").children().length;
  // const ROOT = $("#row_pattern").parent();
  let employee = $(".employee");
  for (let i = 0; i < COLUMNS_NUM; i++) {
    employee.append('<td class="date"></td>');
  }
  $("#th_pattern").remove();
  $("#row_pattern").remove();
  sendPost("/lastDates/get_json", {}, (data) => {
    table.dates = data;

    sendPost("/positionsTrainings/get_json", {}, (training_list) => {
      table.positionsTrainings = training_list;
      initDates();
      $(".date").click(dateClick);
    });
  });
}

function initDates() {
  table.trainingsMap = new Map();
  const trainings = $("#table_anchor").find("th");
  for (let i = 2; i < trainings.length; i++) {
    table.trainingsMap.set(i, parseInt($(trainings[i]).attr("id")));
  }
  table.employeeIdMap = new Map();
  for (let item of $("tr.employee")) {
    const row = item.rowIndex;
    const userId = parseInt($(item).find("td").attr("userid"));
    table.employeeIdMap.set(userId, row);
  }
  table.positionIdMap = new Map();
  for (let item of $("tr.employee")) {
    const row = item.rowIndex;
    const positionId = parseInt($($(item).find("td")[1]).attr("positionid"));
    table.positionIdMap.set(positionId, row);
  }
  table.positionsTrainingsMap = new Map();
  for (let item of table.positionsTrainings) {
    if (table.positionsTrainingsMap.has(item.positionId)) {
      table.positionsTrainingsMap
        .get(item.positionId)
        .push([item.trainingId, item.optional]);
    } else {
      table.positionsTrainingsMap.set(item.positionId, [
        [item.trainingId, item.optional],
      ]);
    }
  }
}

function dateClick() {
  const COLUMN = this.cellIndex;
  const ROW = this.parentNode.rowIndex;
  // k = document.getElementById("table_anchor").rows[ROW].cells[COLUMN];
  const USER_ID = $("#table_anchor tr:eq(" + ROW + ") td:eq(" + 0 + ")").attr(
    "userid"
  );
  const TRAINING_ID = parseInt(
    $("#table_anchor tr:eq(" + 0 + ") th:eq(" + COLUMN + ")").attr("id")
  );
  console.log(
    `row index: ${ROW}\ntraining id: ${TRAINING_ID} \nuser id: ${USER_ID}`
  );
  k = this;
  console.log(this);
}

function drawTh() {
  const th_pattern = $("#th_pattern");
  if (th_pattern.length != 0) {
    table.thAddress = th_pattern.attr("address");
    sendPost(table.thAddress, {}, (data) => {
      table.thData = data;
      for (let item of table.thData) {
        addItem2(item, th_pattern);
      }
    });
  }
}

function addItem2(item, pattern) {
  const root = $(pattern).parent();
  let newRow = $(pattern)
    .clone()
    .removeAttr("hidden")
    .removeAttr("address")
    .removeAttr("id");
  // const var_name = $("#th_pattern>.text")[0].classList[1];
  for (let v of newRow.find(".text")) {
    v.textContent = item[v.classList[1].substr(2)];
  }
  // const var_name = pattern.find(".text")[0].classList[1];
  // newRow.find(`.${var_name}`)[0].textContent = item[var_name.substr(2)];
  const root_name = $(pattern).attr("name");
  if (root_name) {
    newRow.attr(root_name, item[root_name]);
  }
  const child_names = $(newRow).find("[name]");
  for (let child_name of child_names) {
    const name = $(child_name).attr("name");
    $(child_name).attr(name, item[name]);
  }
  // newRow.attr("tid", item.id);
  root.append(newRow);
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
  let newRow = $(pattern)
    .clone()
    .removeAttr("hidden")
    .removeAttr("id")
    .removeAttr("address");
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
