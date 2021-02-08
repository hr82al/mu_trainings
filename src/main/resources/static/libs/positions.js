const NEW_ROW_PATTERN =
  '<tr class="position_id deleteEntry"><td></td><td><div class="itemText"></div></td><td><input type="checkbox" class="itemDel"></td></tr>';

$(function () {
  $(".postPosition").click(function (e) {
    postPosition();
  });
});
var gl;
$(function () {
  /*$(".employee_position").select2({
        data: getPositions(),
    });*/
  $(".employee_position").parent().click(positionSelect);
  /*    $(".employee_position").click(function(e){
        //Получаем id работника
        var userId = $(this).parent().attr("id").substr(2);
        $.post({
          url: "/get_positions",
          data: {
            _csrf: $("input[name='_csrf']").val(),
            pos: userId
          },
          success: function (data, textStatus, jqXHR) {
              positions = $.parseJSON(data);
          }
        });

    });*/

  function onAjaxSuccess(data) {
    // Здесь мы получаем данные, отправленные сервером и выводим их на экран.
    alert(data);
  }
});

function deletePosition(p) {
  console.log("delete");
  console.log($(p).html());
  var id = $(p).parent().parent().attr("id").substr(2);
  $.ajax({
    url: "/position/delete/" + id,
    type: "DELETE",
    data: {
      _csrf: getCsrf(),
    },
    success: function () {
      console.log("ok");
    },
  });
}

function postPosition() {
  console.log("post position");
  $.ajax({
    url: "/position/post",
    type: "POST",
    data: {
      _csrf: $("input[name='_csrf']").val(),
      id: 3,
      text: "sdfa",
    },
    success: function () {
      console.log("ok");
    },
  });
}

function getPositions() {
  var tmp;
  $.ajax({
    url: "/get_positions",
    success: function (res) {
      tmp = $.parseJSON(res);
    },
    async: false,
  });
  return tmp;
}

function positionSelect(e) {
  console.log($(this).html());
  var cur = $(this).children()[0];
  $.ajax({
    url: "/get_positions",
    success: function (res) {
      $(cur).select2({
        data: $.parseJSON(res),
      });
      $(cur).on("select2:select", function (e) {
        //Here select new a position of  thr employee.
        var data = e.params.data;
        $(this).text(e.params.text);
        var div = $(cur).parent();
        console.log(
          div.html(
            '<div class="employee_position">' + e.params.data.text + "</div>"
          )
        );
        div.click(positionSelect);
      });
    },
  });

  $(this).off("click");
}

function getCsrf() {
  return $("input[name='_csrf']").val();
}

function addPosition() {
  console.log("add position");
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $.ajax({
    url: "/positions",
    type: "POST",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify({ text: $("#inputPos").val() }),
    beforeSend: function (jqXHR, settings) {
      jqXHR.setRequestHeader(header, token);
    },
    complete: function (jqXHR, textStatus) {
      tmp = jqXHR;
      if (textStatus == "success") {
        addNewPositionToTable(jqXHR.responseJSON.id, jqXHR.responseJSON.text);
        $("#inputPos").val("");
      }
      if (jqXHR.status == 500) {
        $("#inputPos").val("");
      }
    },
  });
}

function addNewPositionToTable(id, text) {
  var row = $("tr").eq(-2);
  row.after(
    "<tr id='id" +
      id +
      '\' class="position_id"><td>' +
      (parseInt($("tr").eq(-2).eq(0).children().eq(0).text()) + 1) +
      "</td><td>" +
      text +
      '</td><td><input type="button" class="deletePosition btn btn-primary" value="Удалить" onclick="deletePosition(this)"></input></td></td></tr>'
  );
}
