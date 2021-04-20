"use strict";

document.addEventListener("DOMContentLoaded", function () {
  setDatepicker(".datetime");
  fillDates(new Date());
  initMobile();
});
function initMobile() {
  if (isMobile()) {
    $("head").append(
      '<link rel="stylesheet" type="text/css" href="/styles/mobile-styles.css">'
    );
  }
  const REM_SIZE = parseFloat(
    getComputedStyle(document.documentElement).fontSize
  );
  const SCREEN_WIDTH = parseInt($(window).width());
}
var tmp;
function fillDates(dateParam) {
  $(".datetime")[0].value = dateParam.toDateTimeString();
  let lastDate = new Date();
  for (let date of $(".datetime").slice(1)) {
    if ($(date).hasClass("start")) {
      date.value = lastDate;
      // let currentDate = lastDate.toDate();
    } else {
      const DURATION = parseInt($(date).parent().parent().attr("duration"));
      const START_DATE = $(date).parent().parent().find(".start")[0].value;
      lastDate = START_DATE.toDate();
      lastDate.setMinutes(lastDate.getMinutes() + DURATION);
      lastDate = lastDate.toDateTimeString();
      date.value = lastDate;
    }
  }
}

var current_datetimepicker;
function setDatepicker(cell) {
  $(cell)
    .datetimepicker({
      format: "yyyy-mm-dd hh:ii",
      autoclose: true,
      todayBtn: true,
      language: "ru",
      initialDate: new Date(),
    })
    .on("changeDate", function (ev) {
      recalculateDates(this);
    });
  $($(cell)[0]).on("changeDate", function (ev) {
    fillDates(new Date(ev.date.valueOf()));
  });
}

function recalculateDates(cell) {
  let start = false;
  for (let date of $(".datetime").slice(1)) {
    if (date == cell) {
      start = true;
      continue;
    }
    if (start) {
      if ($(date).hasClass("start")) {
        let prevDate = $(date).parent().parent().prev().find(".end")[0].value;
        if (prevDate.toDate() > date.value.toDate()) {
          date.value = prevDate;
          const DURATION = parseInt($(date).parent().parent().attr("duration"));
          let endDate = prevDate.toDate();
          endDate.setMinutes(endDate.getMinutes() + DURATION);
          $(date)
            .parent()
            .next()
            .find(".end")[0].value = endDate.toDateTimeString();
        }
      }
    }
  }
}

Date.prototype.toDateTimeString = function () {
  const date =
    this.getFullYear() +
    "-" +
    String(this.getMonth() + 1).padStart(2, "0") +
    "-" +
    String(this.getDate()).padStart(2, "0");
  const time =
    String(this.getHours()).padStart(2, "0") +
    ":" +
    String(this.getMinutes()).padStart(2, "0");
  return date + " " + time;
};

String.prototype.toDate = function () {
  let date;
  let time;
  [date, time] = this.split(" ");
  let year;
  let month;
  let day;
  [year, month, day] = date.split("-");
  month -= 1;
  let hours;
  let minutes;
  [hours, minutes] = time.split(":");
  let outDate = new Date();
  outDate.setFullYear(year);
  outDate.setMonth(month);
  outDate.setDate(day);
  outDate.setHours(hours);
  outDate.setMinutes(minutes);
  return outDate;
};

function sendPost(uri, data) {
  let promise = new Promise((resolve, reject) => {
    fetch(uri, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
        "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
      },
      body: JSON.stringify(data),
    }).then((data) => {
      data.json().then((response) => {
        resolve(response);
      });
    });
  });
  return promise;
}

function addWork(p) {
  const WORK_DESCRIPTION = $("#work-description").val();
  console.log(WORK_DESCRIPTION);
  let list = [];
  for (let i of $("#add_record_table>tbody>tr")) {
    let record = {};
    record.id = i.id;
    record.startDateTime = $(i).find(".start").val();
    record.endDateTime = $(i).find(".end").val();
    console.log(record);
    list.push(record);
  }
  if (WORK_DESCRIPTION.length > 0 && list.length > 0) {
    list = { records: list, description: WORK_DESCRIPTION };
    fetch("/add_work_record/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf-8",
        "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
      },
      body: JSON.stringify(list),
    }).then((data) => {
      window.location.replace("/action_plan");
    });
  }
}

function isMobile() {
  try {
    document.createEvent("TouchEvent");
    return true;
  } catch (e) {
    return false;
  }
}
