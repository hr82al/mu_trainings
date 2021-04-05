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
  //FIXME check for status
  sendPost("/action_plan/colors", {}).then((dates) => {
    let datesMap = new Map();
    for (const date of dates) {
      datesMap.set(date.days, date.color.toLocaleLowerCase());
    }
    const DATES = $(".date");
    for (const i of DATES) {
      let date = new Date($(i).html());
      const DAY_DIFFERENCE = Math.floor(
        (date - new Date().getTime()) / (1000 * 24 * 3600)
      );

      if (DAY_DIFFERENCE < 0) {
        console.log(DAY_DIFFERENCE);
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
  });
  const DATES = $(".date");
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
