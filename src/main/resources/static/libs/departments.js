var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


function addDepartment(dep) {
    console.log('dep');
    var department = $('#inputDepartment').val();
    sendPost("/departments/add", {text: department}, 
        ()=>{
    });
    addRow(dep, {text: department});
}

function getDepartment(_text){
    sendPost("/departments/get", {text: _text}, function (p, department){
        addRow(department);
    });
}

function addRow(p, department){
    var currentRow = $(".addDepartment");
    var last = $(currentRow).prev();
    var newRow = $(last).clone();
    console.log($(newRow).html());
    sendPost("/departments/get", {text: department.text}, (jqXHRJson) => {
        $(newRow).attr('id', `id${jqXHRJson.id}`);
    })
    $(newRow).children().eq(0).text(parseInt($(newRow).children().eq(0).text()) + 1);
    console.log(`id${parseInt($(newRow).attr('id').substr(2)) + 1}`);
    $(newRow).children().eq(1).text(department.text);
    $(currentRow).before(newRow);
}
// $(function() {
//     addRow($(".addDepartment"), {id: 6, text: 'h'});
// });
function _addDepartment(id, text) {
    var row = $('tr').eq(-2);
    row.after(
        `<tr id="id${id}" class="department_id"<td>${(parseInt($("tr").eq(-2).eq(0).children().eq(0).text()) + 1)}</td> <td><div class="department">${text}</div></td><td><input type="button" class="deleteDepartment" value="Удалить" onclick="deleteDepartment(this)"></td>`);

}

function findByDepartment() {
    $.ajax({
        url: "/departments",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({ text: $("#inputDepartment").val() }),
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader(header, token)
        },
        complete: function (jqXHR, textStatus) {
            if (textStatus == 'success') {
                _addDepartment(jqXHR.responseJSON.id, jqXHR.responseJSON.text);
                $("#inputDepartment").val('');
            }
            if (jqXHR.status == 500) {
                unDelDepartment($("#inputDepartment").val());
                $("#inputDepartment").val('');
            }
        },
    });
}


function sendPost(path, param, run) {
    $.ajax({
        url: path,
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(param),
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader(header, token)
        },
        complete: function (jqXHR, textStatus) {
            if (textStatus == 'success') {
                run(jqXHR.responseJSON);
            }
            if (jqXHR.status == 500) {
                console.log(jqXHR.responseJSON);
            }
        },
    });
}


function deleteDepartment(row) {
    console.log('del');
    var currentRow = $(row).closest(".department_id");
    var _id = $(currentRow).attr('id').substr(2);
    sendPost("/departments/del", {id: _id}, function (a1){
        
    });
    currentRow.remove();
    console.log(_id);
    //sendPost("/departments/del", )
    //remove row
    //$(tmp).closest(".department_id").remove()
}

