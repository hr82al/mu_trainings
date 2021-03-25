"use strict";
const RU_PICKER = {
  language: "ru",
  autoclose: true,
  format: "dd.mm.yyyy",
};
const ruEn = new Map([
  ["А", "A"],
  ["а", "a"],
  ["Б", "B"],
  ["б", "b"],
  ["В", "V"],
  ["в", "v"],
  ["Г", "G"],
  ["г", "g"],
  ["Д", "D"],
  ["д", "d"],
  ["Е", "E"],
  ["е", "e"],
  ["Ё", "E"],
  ["ё", "e"],
  ["Ж", "Zh"],
  ["ж", "zh"],
  ["З", "Z"],
  ["з", "z"],
  ["И", "I"],
  ["и", "i"],
  ["Й", "Y"],
  ["й", "y"],
  ["К", "K"],
  ["к", "k"],
  ["Л", "L"],
  ["л", "l"],
  ["М", "M"],
  ["м", "m"],
  ["Н", "N"],
  ["н", "n"],
  ["О", "O"],
  ["о", "o"],
  ["П", "P"],
  ["п", "p"],
  ["Р", "R"],
  ["р", "r"],
  ["С", "S"],
  ["с", "s"],
  ["Т", "T"],
  ["т", "t"],
  ["У", "U"],
  ["у", "u"],
  ["Ф", "F"],
  ["ф", "f"],
  ["Х", "Kh"],
  ["х", "kh"],
  ["Ц", "Ts"],
  ["ц", "ts"],
  ["Ч", "Ch"],
  ["ч", "ch"],
  ["Ш", "Sh"],
  ["ш", "sh"],
  ["Щ", "Shch"],
  ["щ", "shch"],
  ["Ъ", ""],
  ["ъ", ""],
  ["Ь", ""],
  ["ь", ""],
  ["Ы", "Y"],
  ["ы", "y"],
  ["Э", "E"],
  ["э", "e"],
  ["Ю", "Yu"],
  ["ю", "yu"],
  ["Я", "Ya"],
  ["я", "ya"],
]);

document.addEventListener("DOMContentLoaded", () => {
  $("#ruFirstName").on("change", function (e) {
    $("#enFirstName").val(transliterate(this.value));
  });
  $("#ruLastName").on("change", function (e) {
    $("#enLastName").val(transliterate(this.value));
  });

  //date pickers
  $("#birthDate").datepicker(RU_PICKER);
  $("#beginDate")
    .datepicker(RU_PICKER)
    .on("changeDate", function (e) {
      const DATE = e.date;
      $("#endDate").datepicker(
        "setDate",
        new Date(DATE.setFullYear(DATE.getFullYear() + 40))
      );
    });
  $("#endDate").datepicker(RU_PICKER);

  //select 2
  sendPost("employee/ruPositions").then(function (data) {
    $("#ruPosition")
      .select2({
        data: data,
      })
      .val(idByValue(data, $("#ruPosition").attr("value")))
      .trigger("change");
  });

  sendPost("employee/enPositions").then(function (data) {
    $("#enPosition")
      .select2({
        data: data,
      })
      .val(idByValue(data, $("#enPosition").attr("value")))
      .trigger("change");
  });
  //Check for unique
  $(".unique").on("change", function (e) {
    const CURRENT = this;
    sendPost("employee/checkForUnique", {
      name: this.id,
      value: this.value,
    }).then(function (data) {
      if (data) {
        $(CURRENT).addClass("not-unique");
      } else {
        $(CURRENT).removeClass("not-unique");
      }
    });
  });
  fixInputs();
});

function idByValue(data, value) {
  for (const i of data) {
    if (i.text == value) {
      return i.id;
    }
  }
}

function fixInputs() {
  //fix dates
  const DATES = $(".date");
  for (const i of DATES) {
    $(i).datepicker("update", new Date(i.value));
  }
  //set selects
  const SELECTS = $("select");
  for (const i of SELECTS) {
    i.value = $(i).attr("value");
  }
}

function yearLocalToUTC(year) {
  return year.value.split(".").reverse().join("-");
}

var tmp;
function addEmployee() {
  let employee = {};
  const values = $("#user input");
  for (const i of values) {
    if (i.id.endsWith("Date")) {
      let val = yearLocalToUTC(i);
      employee[i.id] = val;
    } else {
      employee[i.id] = i.value;
    }
  }
  const selections = $("#user select");
  for (const i of selections) {
    employee[i.id] = i.value;
  }
  const selects2 = $("select.select2");
  for (const i of selects2) {
    employee[i.id] = $(i).find(":selected").text();
  }
  employee.userId = $("#user").attr("user-id");
  console.log(JSON.stringify(employee));
  sendPost("employee/add", employee).then((data) => {
    window.history.back();
  });
}

function transliterate(rus) {
  let eng = "";
  for (const ch of rus) {
    if (ruEn.has(ch)) {
      eng += ruEn.get(ch);
    } else {
      eng += ch;
    }
  }
  return eng;
}

function sendPost(uri, data) {
  let promise = fetch(uri, {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
    },
    body: JSON.stringify(data),
  });
  return promise.then((response) => response.json());
}
