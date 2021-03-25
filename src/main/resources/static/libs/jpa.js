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
const SAVE_DATA = "Сохранение данных...";
const DAYS_ALARM = 7;
const DAYS_NOTIFY = 28;

function createRow(data, pattern) {
  let newRow = pattern
    .clone()
    .removeAttr("hidden")
    .removeAttr("address")
    .removeAttr("id");
  const VALUES = newRow.find("td[value_from]");
  //set text in td
  for (let i of VALUES) {
    const VALUE = $(i).attr("value_from");
    const TEXT = data[VALUE];
    i.textContent = TEXT;
  }
  //set input checkbox
  const CHECKBOXES = newRow.find('input[type="checkbox"]');
  for (let checkbox of CHECKBOXES) {
    const VALUE = $(checkbox).attr("value_from");
    const STATE = data[VALUE];
    $(checkbox).prop("checked", STATE);
  }
  //set ids
  const ID_NAMES = newRow.find("[name]");
  for (let tag of ID_NAMES) {
    const NAME = $(tag).attr("name");
    const ID = data[NAME];
    $(tag).attr(NAME, ID);
  }
  if (newRow.attr("name")) {
    newRow.attr(newRow.attr("name"), data[newRow.attr("name")]);
  }
  return newRow;
}

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

table.findBaseAddress = function () {
  const PATH = window.location.pathname;
  const LAST_INDEX = PATH.indexOf("/");
  const PREVIOUS_INDEX = PATH.substr(0, LAST_INDEX);
  if (PREVIOUS_INDEX != -1) {
    return PATH.substr(PREVIOUS_INDEX, LAST_INDEX);
  }
};

function getObjectFromTr(tr) {
  let data = {};
  // const VALUES = tr.find("td[value_from]");
  const VALUES = tr.find("[value_from]");
  //set text in td
  for (let i of VALUES) {
    const VALUE = $(i).attr("value_from");
    data[VALUE] = $(i).val();
  }
  //set input checkbox
  const CHECKBOXES = tr.find('input[type="checkbox"]');
  for (let checkbox of CHECKBOXES) {
    const VALUE = $(checkbox).attr("value_from");
    data[VALUE] = $(checkbox).prop("checked");
  }
  //set ids
  const ID_NAMES = tr.find("[name]");
  for (let tag of ID_NAMES) {
    const NAME = $(tag).attr("name");
    data[NAME] = $(tag).attr(NAME);
  }
  if (tr.attr("name")) {
    data[tr.attr("name")] = tr.attr(tr.attr("name"));
  }

  return data;
}

function init() {
  table.token = $("meta[name='_csrf']").attr("content");
  table.header = $("meta[name='_csrf_header']").attr("content");
  table.address = $("#row_pattern").attr("address");
  table.sectionAddress = $("#section_pattern").attr("address");
  table.baseAddress = table.findBaseAddress();
  if (table.address) {
    table.columnNames = getColumnNames($("#row_pattern"));
    table.selectNames = getSelectNames($("#row_pattern"));
    if (!table.sectionAddress) {
      sendPost(`${table.address}`, {}, (value) => {
        table.data = value;
        drawTable(table.data);
        initSelect();
        drawMenu();
      });
    } else {
      drawSection();
    }
  }
  const address = $("#r_pattern").attr("address");
  if (address) {
    sendPost(`${address}`, {}, (rows) => {
      for (let row of rows) {
        addTotable(createRow(row, $(r_pattern)));
      }
      // $(".itemOptional").on("change", function (e) {
      //   const CHECKED = $(this).prop("checked");
      // });
    });
  }
  if ($(".preload").length != 0) {
    for (let item of $(".preload")) {
      const ADDRESS = $(item).attr("address");
      sendPost(ADDRESS, {}, function (data) {
        table[item.classList[0]] = data;
        console.log(`.select2.${item.classList[0]}`);
        $(`.select2.${item.classList[0]}`).select2({
          data: data,
        });
        $(`.select2.${item.classList[0]}`).on("select2:select", function (a) {
          $(item).attr($(item).attr("name"), a.params.data.id);
        });
      });
    }
  }
  drawTh();
}

function drawMenu() {
  $(".fio")
    .on("mouseover", function (e) {
      // deleteDateById(parseInt(this.id));
      if ($(this).find(".tmp-button").length == 0 && $(this).html() != "") {
        $(this).append(
          ' <div class="dropdown-menu"><a class="dropdown-item" onclick="resetUser(this)">Сбросить</a><a class="dropdown-item" onclick="editUser(this)">Редактировать</a></div>'
        );
      }
    })
    .on("mouseleave", function (e) {
      $(".tmp-button").remove();
    });
}

function resetUser(current) {
  "use strict";
  const USER_ID = parseInt($(current).parent().parent().attr("userid"));
  sendPost("muTrainings/resetUser", { userId: USER_ID }, function (result) {
    $(current).parent().parent().parent().find(".position").html("");
    $(current).parent().parent().parent().find(".department").html("");
  });
}
function editUser(current) {
  "use strict";
  const USER_ID = parseInt($(current).parent().parent().attr("userid"));
  window.location = `employee?userId=${USER_ID}`;
}

//on Listeners
function changeOptional(current) {
  const REQUEST = getObjectFromTr($(current).parent().parent());
  sendPost("positionsTrainings/set", REQUEST, function (result) {
    $(current).prop("checked", result.optional);
  });
}

function changeCheckbox(current) {
  const ADDRESS = $(current).attr("address");
  const REQUEST = getObjectFromTr($(current).parent().parent());
  sendPost(ADDRESS, REQUEST, function (result) {
    $(current).prop("checked", result.del);
  });
}

function deletePositionTraining(current) {
  const ROW = $(current).parent().parent();
  const REQUEST = getObjectFromTr(ROW);
  sendPost("positionsTrainings/del", REQUEST, function (result) {
    ROW.remove();
  });
}

function add(current) {
  const ROW = $(current).parent().parent();
  const ADDRESS = ROW.attr("address");
  const REQUEST = getObjectFromTr($(current).parent().parent());
  sendPost(ADDRESS, REQUEST, function (data) {
    REQUEST.id = data.id;
    addTotable(createRow(REQUEST, $(r_pattern)));
  });
}

function addPositionTraining(current) {
  const ROW = $(current).parent().parent();
  const ADDRESS = ROW.attr("address");
  const REQUEST = getObjectFromTr(ROW);
  sendPost(ADDRESS, REQUEST, function (data) {
    for (let item of $(ROW).find(".select2-selection__rendered")) {
      REQUEST[
        $(item)
          .parent()
          .parent()
          .parent()
          .parent()
          .find("[value_from]")
          .attr("value_from")
      ] = item.textContent;
    }
    REQUEST.id = data.id;
    addTotable(createRow(REQUEST, $(r_pattern)));
  });
}

function addTotable(row) {
  $("#table_anchor>tbody").eq(-1).before(row);
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
            if (employees[department.id].length > 0) {
              addItem2(department, $("#section_pattern"));
            }
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
  $("#section_pattern").remove();
  sendPost("lastDates/get_json", {}, (data) => {
    table.lastDates = data;

    sendPost("positionsTrainings/get_json", {}, (training_list) => {
      table.positionsTrainings = training_list;
      initDates();
      setDatepicker(".date");
      // setDatepicker(".required, .optional");
    });
  });
}

function setDatepicker(cell) {
  $(cell)
    .datepicker({ language: "ru", autoclose: true, format: "yyyy-dd-mm" })
    .tooltip({
      title: function (e) {
        return getNextDate(this);
      },
    })
    .on("changeDate", function (e) {
      const DATE = e.format("yyyy-mm-dd");
      let request = getUserTrainingId(this);
      request.lastDate = DATE;
      request.id = parseInt(this.id);

      this.textContent = SAVE_DATA;
      sendPost("lastDates/set_json", request, (response) => {
        const ROW = table.userIdRowMap.get(request.userId);
        const COLUMN = table.trainingIdColumnMap.get(request.trainingId);
        const TRAINING_ID = getTrainingIdByColumn(COLUMN);
        const TRAINING_PERIOD = table.trainingsPeriodsMap.get(TRAINING_ID);
        setCell(response.id, ROW, COLUMN, request.lastDate, TRAINING_PERIOD);
      });
    })
    .on("mouseover", function (e) {
      // deleteDateById(parseInt(this.id));
      if ($(this).find(".tmp-button").length == 0 && $(this).html() != "") {
        $(this).append(
          '<div class="tmp-button"><input type="button" class="btn btn-primary" value="Удалить" onclick="deleteDateById(this)"></div>'
        );
      }
    })
    .on("mouseleave", function (e) {
      $(".tmp-button").remove();
    });
}
var tmp = "";
function deleteDateById(current) {
  let request = {};
  request.id = parseInt($(current).parent().parent().attr("id"));
  // let request = getUserTrainingId(this);
  // request.id = parseInt(this.id);
  sendPost("lastDates/del", request, function () {
    let currentCell = $(current).parent().parent();
    currentCell.html("");
    currentCell.removeClass("alarm");
    currentCell.removeClass("notify");
  });
  // console.log(parseInt(this.id));
}

function getNextDate(cell) {
  const LAST_DATE = cell.textContent;
  const COLUMN = this.cellIndex;
  // const ROW = this.parentNode.rowIndex;
  let tmp = "";
  if (LAST_DATE != "") {
    tmp = new Date(LAST_DATE.split(".").reverse().join("-"));
    tmp.setMonth(tmp.getMonth() + getTrainingPeriodByCell(cell));
    tmp = "Следующая дата: " + tmp.toLocaleDateString();
  }
  tmp +=
    "\r\n" +
    cell.parentElement.getElementsByClassName("v_fio")[0].textContent +
    "\r\n  " +
    $("thead>tr").children().eq($(cell).index()).text();
  return tmp;
}

function getTrainingPeriodByCell(cell) {
  return table.trainingsPeriodsMap.get(getTrainingIdByCell(cell));
}

function getTrainingIdByCell(cell) {
  const COLUMN = cell.cellIndex;
  const TRAINING_ID = parseInt(
    $("#table_anchor tr:eq(" + 0 + ") th:eq(" + COLUMN + ")").attr("id")
  );
  return TRAINING_ID;
}

function getUserTrainingId(obj) {
  const ROW = obj.parentNode.rowIndex;
  const USER_ID = parseInt(
    $("#table_anchor tr:eq(" + ROW + ") td:eq(" + 0 + ")").attr("userid")
  );
  return { userId: USER_ID, trainingId: getTrainingIdByCell(obj) };
}

function initDates() {
  table.trainingsMap = new Map();
  const trainings = $("#table_anchor").find("th");
  for (let i = 2; i < trainings.length; i++) {
    table.trainingsMap.set(i, parseInt($(trainings[i]).attr("id")));
  }
  table.userIdRowMap = new Map();
  for (let item of $("tr.employee")) {
    const row = item.rowIndex;
    const userId = parseInt($(item).find("td").attr("userid"));
    table.userIdRowMap.set(userId, row);
  }
  table.positionsTrainingsMap = new Map();
  // for (let item of table.positionsTrainings) {
  //   if (table.positionsTrainingsMap.has(item.positionId)) {
  //     table.positionsTrainingsMap
  //       .get(item.positionId)
  //       .push([item.trainingId, item.optional]);
  //   } else {
  //     table.positionsTrainingsMap.set(item.positionId, [
  //       [item.trainingId, item.optional],
  //     ]);
  //   }
  // }
  table.maxTrainingsId = 0;
  // let maxPositionsId = 0;
  for (let item of table.positionsTrainings) {
    // if (item.positionId > maxPositionsId) {
    //   maxPositionsId = item.positionId;
    // }
    if (item.trainingId > table.maxTrainingsId) {
      table.maxTrainingsId = item.trainingId;
    }
  }
  for (let item of table.positionsTrainings) {
    table.positionsTrainingsMap.set(
      item.positionId * table.maxTrainingsId + item.trainingId,
      item.optional
    );
  }
  table.trainingIdColumnMap = new Map();
  for (let item of $("th>.training")) {
    const POSITION_ID = parseInt($(item).parent()[0].id);
    const COLUMN = $(item).parent()[0].cellIndex;
    table.trainingIdColumnMap.set(POSITION_ID, COLUMN);
  }
  table.rowIndexPositionId = [];
  for (let item of $("tr.employee")) {
    const ROW = item.rowIndex;
    const POSITION_ID = parseInt($(item).parent()[0].id);
    table.rowIndexPositionId[ROW] = POSITION_ID;
  }

  for (let cell of $(".date")) {
    const ROW = $(cell).parent()[0].rowIndex;
    const COLUMN = cell.cellIndex;
    const POSITION_ID = parseInt(
      $(cell).parent().children().eq(1).attr("positionid")
    );
    // const POSITION_ID = parseInt(
    //   $($(cell).parent().find("[name='positionId']").attr("positionid"))
    // );
    const TRAINING_ID = parseInt(
      $("#table_anchor tr:eq(" + 0 + ") th:eq(" + COLUMN + ")").attr("id")
    );
    const OPTIONAL = isTrainingPositionOptional(TRAINING_ID, POSITION_ID);
    if (OPTIONAL != undefined) {
      if (OPTIONAL) {
        $(cell).addClass("optional");
      } else {
        $(cell).addClass("required");
      }
    }
  }

  for (let date of table.lastDates) {
    const ROW = table.userIdRowMap.get(date.userId);
    const COLUMN = table.trainingIdColumnMap.get(date.trainingId);
    const TRAINING_ID = getTrainingIdByColumn(COLUMN);
    const TRAINING_PERIOD = table.trainingsPeriodsMap.get(TRAINING_ID);
    const ID = date.id;
    setCell(ID, ROW, COLUMN, date.lastDate, TRAINING_PERIOD);
  }
}

function getTrainingIdByColumn(column) {
  return parseInt(
    $("#table_anchor tr:eq(" + 0 + ") th:eq(" + column + ")").attr("id")
  );
}

function isTrainingPositionOptional(trainingId, positionId) {
  return table.positionsTrainingsMap.get(
    positionId * table.maxTrainingsId + trainingId
  );
}

function setCell(id, row, column, lastDate, period) {
  const CELL = $("#table_anchor tr:eq(" + row + ") td:eq(" + column + ")");

  const LAST_DATE = new Date(lastDate);
  let tmp = new Date(lastDate);
  tmp.setMonth(tmp.getMonth() + period);
  const NEXT_DATE = tmp;
  CELL.text(LAST_DATE.toLocaleDateString());
  const DAY_DIFFERENCE = Math.floor(
    (NEXT_DATE.getTime() - new Date().getTime()) / (1000 * 24 * 3600)
  );
  $(CELL)
    .removeClass("required")
    .removeClass("optional")
    .removeClass("alarm")
    .removeClass("notify")
    .addClass("text-nowrap");
  setDatepicker(CELL);
  $(CELL).attr("id", id);
  highlightCell(CELL, DAY_DIFFERENCE);
}

function highlightCell(cell, dayDifference) {
  if (dayDifference < DAYS_NOTIFY && dayDifference >= DAYS_ALARM) {
    $(cell).addClass("notify");
  } else if (dayDifference <= DAYS_ALARM) {
    $(cell).addClass("alarm");
  }
}

function dateClick() {
  const COLUMN = this.cellIndex;
  const ROW = this.parentNode.rowIndex;
  const USER_ID = $("#table_anchor tr:eq(" + ROW + ") td:eq(" + 0 + ")").attr(
    "userid"
  );
  const TRAINING_ID = parseInt(
    $("#table_anchor tr:eq(" + 0 + ") th:eq(" + COLUMN + ")").attr("id")
  );
}

function drawTh() {
  const th_pattern = $("#th_pattern");
  if (th_pattern.length != 0) {
    table.thAddress = th_pattern.attr("address");
    sendPost(table.thAddress, {}, (data) => {
      table.trainingsNames = data;
      table.trainingsPeriodsMap = new Map();
      for (let period of table.trainingsNames) {
        table.trainingsPeriodsMap.set(period.id, period.trainingPeriod);
      }
      for (let item of table.trainingsNames) {
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

function initSelect() {
  table.selections = new Map();
  for (const item of table.selectNames) {
    // const url = `/get_${item}s`;
    const url = `${item}s/get_json`;
    sendPost(url, {}, function (data) {
      table.selections.set(item + "s", data);
    });

    $(".select").click(itemSelect);
  }
}
function itemSelect() {
  let current = this;
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
    save($(current).parent());
    // let fn = itemSelect.bind(this);
    //Bind a new click listener
    $(current).click(itemSelect);
  });
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
    sendPost("muTrainings/add", data, (v) => console.log(v));
  }
}

function addItem(item) {
  var entry = {};
  for (var i = 1; i < $("#addEntry").children().length - 1; i++) {
    var currentVal = $("#addEntry").children().eq(i).children().eq(0).val();
    var inputVarName = getInputVarName(i, $("#addEntry"));
    entry[inputVarName] = currentVal;
  }
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
  sendPost(`${window.location.pathname}/del`, getItemFromRow(row), () => {
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
    $(newRow).children().eq(i).children().eq(0).text(entry[name]);
  }
  $(currentRow).before($(newRow));
}
