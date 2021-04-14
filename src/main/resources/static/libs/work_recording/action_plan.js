"use strict";
document.addEventListener("DOMContentLoaded", () => {
  //change icon if pdf instruction does not exists.
  const INSTRUCTIONS = $(".instruction");
  for (const i of INSTRUCTIONS) {
    const LINK = $(i).attr("lnk");
    if (!LINK.endsWith(".pdf")) {
      $(i).find("img").attr("src", "/images/brocken_document.png");
    }
  }
  //change the color of the cell according to its date
  sendPost("/action_plan/colors", {}).then((dates) => {
    let datesMap = new Map();
    for (const date of dates) {
      datesMap.set(parseInt(date.days), date.color.toLocaleLowerCase());
    }
    const DATES = $(".date");
    for (const i of DATES) {
      let date = new Date($(i).html());
      const DAY_DIFFERENCE = Math.floor(
        (date - new Date().getTime()) / (1000 * 24 * 3600)
      );
      const STATUS = parseInt(
        $($(i).parent().find(".execution")[0]).attr("status")
      );
      if (STATUS == 0) {
        if (DAY_DIFFERENCE < 0) {
          let color = datesMap.get(DAY_DIFFERENCE);
          if (color) {
            $(i).attr("style", `background-color: ${color};`);
          } else {
            $(i).attr("style", `background-color: red;`);
          }
        } else {
          $(i).attr("style", `background-color: green;`);
        }
      }
    }
  });
  //Change the color of the IDs according to its status
  const WORKER_IDs = $(".worker-id");
  for (const i of WORKER_IDs) {
    const STATUS = parseInt($(i).attr("status"));
    if (STATUS == 1) {
      $(i).addClass("done");
    } else if (STATUS == 2) {
      $(i).addClass("confirmed");
    }
  }

  //click a owner button to create a new record in the work plan
  $(".owner-button").click(function () {
    createWorkPlanRecord(this);
  });
});

function openInstruction(self) {
  const LINK = $(self).attr("lnk");
  getPlainTextByPost("/work_recording/pdf_instruction/get", LINK).then(
    (pdfUrl) => {
      if (pdfUrl != ".") {
        window.open(pdfUrl, "_blank");
      }
    }
  );
}

function getPlainTextByPost(url, data) {
  let promise = new Promise((resolve, reject) => {
    fetch("/work_recording/pdf_instruction/get", {
      method: "POST",
      headers: {
        "Content-Type": "text/plain; charset=UTF-8",
        "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
      },
      body: data,
    }).then((response) => {
      response.text().then((value) => {
        resolve(value);
      });
    });
  });
  return promise;
}

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

function createWorkPlanRecord(self) {
  $(self).parent().removeClass("selected");
  $(self).parent().addClass("selected");
  createWorkPlanRecordClick(self);
  // let ids = [];
  // for (const i of $(".selected")) {
  //   ids.push($($(i).find(".id")[0]).text());
  // }
  // fetch("/add_work_record/add", {
  //   method: "POST",
  //   headers: {
  //     "Content-Type": "application/json;charset=utf-8",
  //     "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
  //   },
  //   body: JSON.stringify(ids),
  // }).then((response) => {
  //   window.location.replace("/add_work_record");
  // });
  // $(self).parent().removeClass("selected");
}

function createWorkPlanRecordClick(self) {
  let ids = [];
  for (const i of $(".selected")) {
    ids.push($($(i).find(".id")[0]).text());
  }
  console.log(ids);
  fetch("/add_work_record/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
    },
    body: JSON.stringify(ids),
  }).then((response) => {
    window.location.replace("/add_work_record");
  });
  $(self).parent().removeClass("selected");
}
