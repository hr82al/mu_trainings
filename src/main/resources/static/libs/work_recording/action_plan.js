document.addEventListener("DOMContentLoaded", () => {
  //change icon if pdf instruction does not exists.
  const INSTRUCTIONS = $(".instruction");
  for (const i of INSTRUCTIONS) {
    const LINK = $(i).attr("lnk");
    if (!LINK.endsWith(".pdf")) {
      $(i).find("img").attr("src", "/images/brocken-document.png");
    }
  }
  //change the color of the cell according to its date
  const DATES = $(".date");
  for (const i of DATES) {
    tmp = i;
    let duration = parseInt($(i).attr("duration"));
    let date = new Date($(i).html());
    date.setDate(date.getDate() + duration);
    new Date($(i).html());
    const DURATION = parseInt($(i).attr("duration"));
    console.log(`duration ${date}`);
    const DAY_DIFFERENCE = Math.floor(
      (date - new Date().getTime()) / (1000 * 24 * 3600)
    );
    //check if the task is delayed
    if (DAY_DIFFERENCE < 0) {
      $(i).addClass("delayed");
    }
    console.log(DAY_DIFFERENCE);
  }
});

function sendPost(uri, data) {
  console.log(data);
  let promise = fetch(uri, {
    method: "POST",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content"),
    },
    body: JSON.stringify(data),
  });
  return promise.then((response) => console.log(response.json()));
}

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
