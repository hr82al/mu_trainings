$(function(){
    $(".deletePosition").click(function(e) {
        var id = $(this).parent().parent().attr("id").substr(2);
        deletePosition(id);
    });

    $(".postPosition").click(function(e) {
        postPosition();
    })
});
var gl;
$(function(){
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



    function onAjaxSuccess(data)
    {
      // Здесь мы получаем данные, отправленные сервером и выводим их на экран.
      alert(data);
    }


});


function deletePosition(id){
    $.ajax({
        url: '/position/delete/' + id,
        type: 'DELETE',
        data: {
            _csrf: getCsrf(),
        },
        success: function () {
            console.log("ok");
        }
    })
}

function postPosition(){
    console.log("post position");
    $.ajax({
        url: '/position/post',
        type: 'POST',
        data: {
            _csrf: $("input[name='_csrf']").val(),
            id: 3,
            text: "sdfa",
        },
        success: function () {
            console.log("ok");
        }
    });
}

function getPositions() {
    var tmp
    $.ajax({
        url: "/get_positions",
        success: function(res) { tmp =  $.parseJSON(res);},
        async: false
    });
    return tmp;;
}

function positionSelect(e) {
    console.log($(this).html()) ;
        var cur = $(this).children()[0];
        $.ajax({
            url: "/get_positions",
            success: function(res) {

                $(cur).select2({
                    data: $.parseJSON(res)
                });
                $(cur).on('select2:select', function (e) {
                //Here select new a position of  thr employee.
                    var data = e.params.data;
                    $(this).text(e.params.text);
                    var div = $(cur).parent();
                     console.log(div.html("<div class=\"employee_position\">" + e.params.data.text + "</div>"));
                     div.click(positionSelect);
                });

            }
        });

        $(this).off("click");
}

function getCsrf(){
    return $("input[name='_csrf']").val();
}