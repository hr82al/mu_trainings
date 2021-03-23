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

var token, header;
document.addEventListener("DOMContentLoaded", () => {
  token = $("meta[name='_csrf']").attr("content");
  header = $("meta[name='_csrf_header']").attr("content");

  $("#firstName").on("change", function (e) {
    $("#enFirstName").val(transliterate(this.value));
    console.log(this.value);
  });
  $("#lastName").on("change", function (e) {
    $("#enLastName").val(transliterate(this.value));
  });

  //date pickers
  $("#birthDate").datepicker(RU_PICKER);
  $("#beginDate").datepicker(RU_PICKER);
  $("#endDate").datepicker(RU_PICKER);

  //select 2
  sendPost("employee/positions", {}).then(function (data) {
    $("#position").select2({
      data: data,
    });
  });

  sendPost("employee/enPositions", {}).then(function (data) {
    $("#enPosition").select2({
      data: data,
    });
  });
  //Check for unique
  $(".unique").on("change", function (e) {
    sendPost("employee/checkForUnique", {
      name: this.id,
      value: this.value,
    }).then(function (data) {
      tmp = e;
      if (data) {
        $(this).addClass("not-unique");
      } else {
        $(this).removeClass("not-unique");
      }
    });
    console.log(this.value);
    console.log(this.id);
  });
});

var tmp;
function addEmployee() {
  let employee = {};
  const values = $("#user input");
  for (const i of values) {
    if (i.id.endsWith("Date")) {
      let val = i.value.split(".").reverse().join("-");
      employee[i.id] = val;
    } else {
      employee[i.id] = i.value;
    }
  }
  const selections = $("#user select");
  for (const i of selections) {
    employee[i.id] = i.value;
  }
  const selects2 = $("div.select2");
  for (const i of selects2) {
    employee[i.id] = $(i).find(":selected").text();
  }
  let promise = sendPost("employee/add", employee);
  promise.then((data) => console.log(JSON.stringify(data)));
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
