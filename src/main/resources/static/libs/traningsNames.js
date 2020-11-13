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

function addItem(item) {
    var entry =  {};
    //$('#addEntry').children().each((j, i) => {console.log($(i).children().eq(0).attr('id'));});
    //Get a tails (strings like a 'input[someText]' will be 'text') except first and last column and put them and their values to entry.
    for(var i = 1; i < $('#addEntry').children().length - 1; i++){
        var currentVal = $('#addEntry').children().eq(i).children().eq(0).val();
        var inputVarName = getInputVarName(getVarInputByColumn(i));
        entry[inputVarName] = currentVal;
    }

    //sendPost(`${window.location.pathname}/add`, entry, () => {});
    checkIfAddedAndAddNewRow(entry);
    
    // var department = $('#inputDepartment').val();
    // sendPost("/departments/add", {text: department}, 
    //     ()=>{
    // });
    // addRow(dep, {text: department});
}

function getInputVarName(str) {
    str = str.substr(5)
    return str[0].toLocaleLowerCase() + str.substr(1);
}

function getVarInputByColumn(num){
    return $('#addEntry').children().eq(num).children().eq(0).attr('id');
}

function checkIfAddedAndAddNewRow(entry) {
    addNewRow(entry);
}

function addNewRow(entry) {
    var currentRow = $("#addEntry");
    var last = $(currentRow).prev();
    var newRow = $(`<tr class="id_trainingName"> <td> <div></div></td> <td ><div></div></td> <td><div></div> </td> <td><input type="button" class="deleteTrainingName deleteItemButton" value="Удалить" onclick="deleteDepartment(this)"></input></td></tr>`);
    // sendPost("/departments/get", {text: department.text}, (jqXHRJson) => {
    //     $(newRow).attr('id', `id${jqXHRJson.id}`);
    // })
    $(newRow).children().eq(0).text(parseInt($(newRow).children().eq(0).text()) + 1);
    var nom = $(last).attr('id');
    if (nom == undefined) {
        nom = 1;
    }
    else {
        nom = `id${parseInt(nom.substr(2)) + 1}` ;
    }  
    $(newRow).children().eq(0).text(nom);
    //FIXME set id
    //$(newRow).children().eq(0).attr(id, `id${entry.id}`);
    for(var i = 1; i < $('#addEntry').children().length - 1; i++) {
        var name = getInputVarName(getVarInputByColumn(i));
        $(newRow).children().eq(i).text(entry[name]);
    }
    
    console.log(JSON.stringify(entry));
    console.log(newRow.html());
    tmp = entry;
    //console.log(`id${parseInt($(newRow).attr('id').substr(2)) + 1}`);
    //$(newRow).children().eq(1).text(depart);
    $(currentRow).before($(newRow));
}
var tmp;