var data;
$(function(){
    /*$(".employee_position").select2({
        data: getPositions(),
    });*/
    $(".employee_position").(function(e) {
    }) select2({
            data: getPositions(),
        });
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

function getPositions() {
    var tmp
    $.ajax({
        url: "/get_positions",
        success: function(res) { tmp =  $.parseJSON(res);},
        async: false
    });
    return tmp;;
}

