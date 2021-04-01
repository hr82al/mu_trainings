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
      console.log(pdfUrl);
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
