var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
const NEW_ROW_ITEM =
  '<tr class="id_positionName deleteEntry"><td><div></div></td><td><div class="itemPosition"></div></td><td><div class="itemTraining"></div></td><td><input type="checkbox" class="trainingOptional"></td><td><input type="checkbox" class="itemDel"></td></tr>';

var availablePositions;
var availableTrainings;

//const NEW_ROW_ITEM = '<tr id="id" class="id_trainingName deleteEntry"><td><div></div></td><td><div class="itemTraining"></div></td><td><div class="itemTrainingPeriod"></div></td><td><input type="button" class="deleteTrainingName deleteItemButton btn btn-primary" value="Удалить" onclick="deleteItem(this)"></td></tr>';
var tmp;
$(function () {
  update();

  $(".trainingOptional").change(function () {
    const isChecked = $(this).prop("checked");
    const id = $(this).closest("tr").attr("id").substr(2);

    sendPost(
      "/positionsTrainings/change",
      { id: id, optional: isChecked },
      () => {}
    );
  });
  //$('td.select2').click(showSelect);
  // $('#position').click(function() {
  //   customSelect('#position', availablePositions);
  // });

  // $('#training').click(function() {
  //   customSelect('#training', availableTrainings);
  // });
  //setSelects();
});

function setSelects() {
  customSelect("#position", availablePositions);
  customSelect("#training", availableTrainings);
}

function update() {
  //Load list of available positions
  sendPost("/positions/get", {}, (positions) => {
    availablePositions = positions;
    customSelect("#position", availablePositions);
  });
  //Load list of available trainingsNames
  sendPost("/trainingsNamesList/get", {}, (trainings) => {
    availableTrainings = trainings;
    customSelect("#training", availableTrainings);
  });
}

function showSelect() {
  let cur = this;
  let tmp = $("<div></div>");
  $(cur).append(tmp);
  let path = `/${$(cur).attr("class").split(" ")[0]}/get`;
  sendPost(path, {}, (x) => {
    $(tmp).select2({
      data: x,
    });
    $(tmp).on("select2:select", function (e) {
      //Here select new a position of  thr employee.
      $(cur).html(`${e.params.data.text}`);
      $(cur).click(showSelect);
    });
  });
  //$(cur).off('click');
}

function customSelect(curId, aData) {
  let cur = $(curId);
  let tmp = $("<div></div>");
  $(cur).append(tmp);
  $(tmp).select2({
    data: aData,
  });
  $(tmp).on("select2:select", function (a) {
    //   $(cur).html(a.params.data.text);
    $(cur).attr("aid", a.params.data.id);
  });
  //$(cur).off('click');
}

function sendPost(path, param, run) {
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
      }
      if (jqXHR.status == 500) {
        console.log(jqXHR.responseJSON);
      }
    },
  });
}

function addItem(item) {
  var entry = {};
  for (var i = 1; i < $("#addEntry").children().length - 1; i++) {
    var currentVal = $("#addEntry").children().eq(i).children().eq(0).val();
    console.log(currentVal);
    var inputVarName = getInputVarName(i, $("#addEntry"));
    entry[inputVarName] = currentVal;
  }
  sendPost(`${window.location.pathname}/add`, entry, (item) => {
    addNewRow(item);
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
  console.log(str);
  let first = str.search(/[A-Z]/);
  let last = str.search(/\s/);
  last = last == -1 ? undefined : last;
  str = str.substring(first, last);
  return str[0].toLocaleLowerCase() + str.substr(1);
}

function getVarInputByColumn(num, row) {
  return $(row).children().eq(num).children().eq(0).attr("class");
}

function checkIfAddedAndAddNewRow(entry) {
  addNewRow(entry);
}
function addNewRow(entry) {
  console.log(JSON.stringify(entry));
  var currentRow = $("#addEntry");
  var last = $(currentRow).prev();
  var newRow = $(NEW_ROW_ITEM);
  $(newRow)
    .children()
    .eq(0)
    .text(parseInt($(newRow).children().eq(0).text()) + 1);
  var nom = $(last).children().eq(0).text();
  if (nom == undefined) {
    nom = 1;
  } else {
    nom = `${parseInt(nom.substr(2)) + 1}`;
  }
  $(newRow).children().eq(0).text(nom);
  $(newRow).attr("id", `id${entry.id}`);
  for (var i = 1; i < $("#addEntry").children().length - 1; i++) {
    var name = getInputVarName(i, currentRow);
    $(newRow).children().eq(i).text(entry[name]);
  }
  $(currentRow).before($(newRow));
}

/*
$(function() {
    $('.itemTrainingPeriod').editable({
        event: 'click',
        lineBreaks: false,
        closeOnEnter: true,
        callback: function (data) {
            
            if(data.content){
                currentRow = $(data.$el).closest('.deleteEntry');
                tmp = currentRow;
                
                let entry = getItemFromRow(currentRow)
                $(currentRow).children().eq(2).children().eq(0).text('Сохранение данных ...');
                let currentValue = entry.trainingPeriod;
                if (validateNum(currentValue)) {
                    changeRow(currentRow, entry);
                }
                else {
                    $(currentRow).children().eq(2).children().eq(0).text('Введите число');
                }
            }
        }
    });
});
*/

function changeRow(p, entry) {
  sendPost(`${window.location.pathname}/change`, entry, (item) => {
    $(p).children().eq(2).children().eq(0).text(item.trainingPeriod);
  });
}

function validateNum(data) {
  return data.match(/^[0-9]+$/) != null;
}

function cachingDecorator(func) {
  let cache = new Map();
  return function (x) {
    if (cache.has(x)) {
      return cache.get(x);
    }
    let result = func(x);
    return result;
  };
}

function addPositionTraining(input) {
  console.log($("#trainingOptional").prop("checked"));
  const isChecked = $("#trainingOptional").prop("checked");
  sendPost(
    "/positionsTrainings/set",
    {
      positionId: $("#position").attr("aid"),
      trainingId: $("#training").attr("aid"),
      optional: isChecked,
    },
    (res) => {
      let newRow = $(NEW_ROW_ITEM);
      console.log("f");

      $(newRow)
        .find(".itemPosition")
        .text($("#position").find(".select2-selection__rendered").text());
      $(newRow)
        .find(".itemTraining")
        .text($("#training").find(".select2-selection__rendered").text());
      $(newRow)
        .children()
        .eq(0)
        .text(
          parseInt($("#addPositionTraining").prev().children().eq(0).text()) + 1
        );
      $(newRow).find(".trainingOptional").checked = isChecked;
      $("#addPositionTraining").before(newRow);
      // $('#position').find('.select2-selection__rendered').text('');
      // $('#training').find('.select2-selection__rendered').text('');
      $("#position").text("");
      $("#training").text("");

      setSelects();
      $("#position").select2("focus");
    }
  );
}
