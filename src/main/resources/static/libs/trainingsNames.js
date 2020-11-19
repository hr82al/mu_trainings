var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

var itemTrainingPeriodEditable = {
    event: 'click',
    lineBreaks: false,
    closeOnEnter: true,
    callback: function (data) {
        
        if(data.content){
            currentRow = $(data.$el).closest('.deleteEntry');
            
            let entry = getItemFromRow(currentRow)
            $(currentRow).children().eq(2).children().eq(0).text('Сохранение данных ...');
            let currentValue = entry.trainingPeriod;
            if (validateNum(currentValue)) {
                changeRow(currentRow, entry);
            }
            else {
                $(currentRow).children().eq(2).children().eq(0).text('Введите число');
            }
        }
    }
};

var itemTextEditable = {
    event: 'click',
    lineBreaks: false,
    closeOnEnter: true,
    callback: function (data) {
        
        if(data.content){
            currentRow = $(data.$el).closest('.deleteEntry');
            tmp = currentRow;
            
            let entry = getItemFromRow(currentRow)
            $(currentRow).children().eq(1).children().eq(0).text('Сохранение данных ...');
            changeRow(currentRow, entry);
        }
    }
};

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
                console.log(jqXHR.responseJSON);
            }
            if (jqXHR.status == 500) {
                console.log(jqXHR.responseJSON);
            }
        },
        
    });
}

function addItem(item) {
    var entry =  {};
    for(var i = 1; i < $('#addEntry').children().length - 1; i++){
        var currentVal = $('#addEntry').children().eq(i).children().eq(0).val();
        var inputVarName = getInputVarName(i, $('#addEntry'));
        entry[inputVarName] = currentVal;
    }
    sendPost(`${window.location.pathname}/add`, entry, (item) => {
        addNewRow(item);
    });
}

function getItemFromRow(row) {
    let item = {};
    item.id = $(row).attr('id').substr(2);
    for(let i = 1; i < row.children().length - 1; i++) {
        let currentVal = $(row).children().eq(i).children().eq(0).text();
        let inputVarName = getInputVarName(i, row);
        item[inputVarName] = currentVal;
    }
    return item;
}
var tmp;
function deleteItem(item) {
    //Select current row
    let row = $(item).closest('.deleteEntry');
    console.log(JSON.stringify(getItemFromRow(row)));
    sendPost(`${window.location.pathname}/del`, getItemFromRow(row), () => {
        console.log('success');
        row.remove();
    });
}

function getInputVarName(num, row) {
    str = getVarInputByColumn(num, row)
    //str = str.substr(5)
    //Cut from the first Uppercase character to space
    let first = str.search(/[A-Z]/);
    let last = str.search(/\s/);
    last = last == -1 ? undefined : last;
    str = str.substring(first, last);
    return str[0].toLocaleLowerCase() + str.substr(1);
}

function getVarInputByColumn(num, row){
    return $(row).children().eq(num).children().eq(0).attr('class');
}

function checkIfAddedAndAddNewRow(entry) {
    addNewRow(entry);
}

var tmp;
function addNewRow(entry) {
    console.log(JSON.stringify(entry));
    let currentRow = $("#addEntry");
    let last = $(currentRow).prev();
    let newRow = $(`<tr id="id" class="id_trainingName deleteEntry"><td><div></div></td><td><div class="itemText"></div></td><td><div class="itemTrainingPeriod" ></div></td><td><input type="button" class="deleteTrainingName deleteItemButton btn btn-primary" value="Удалить" onclick="deleteItem(this)"></td></tr>`);
    $(newRow).children().eq(0).text(parseInt($(newRow).children().eq(0).text()) + 1);
    let nom = parseInt($(last).children().eq(0).text());
    tmp = nom;
    if (nom == undefined) {
        nom = 1;
    }
    else {
        nom = nom + 1;
    }  
    console.log(nom);
    $(newRow).children().eq(0).html(`<div>${nom}</div>`)
    $(newRow).attr('id', `id${entry.id}`);
    for(let i = 1; i < $('#addEntry').children().length - 1; i++) {
        let name = getInputVarName(i, currentRow);
        $(newRow).children().eq(i).children().eq(0).text(entry[name]);
    }
    $(currentRow).before($(newRow));
    $(`#id${entry.id} .itemTrainingPeriod`).editable(itemTrainingPeriodEditable);
    $('#inputTraining').text('');
    $('#inputTraining').focus();
}

$(function() {
    $('.itemTrainingPeriod').editable(itemTrainingPeriodEditable);
    $('.itemText').editable(itemTextEditable);
});

function changeRow(p, entry) {
    sendPost(`${window.location.pathname}/change`, entry, (item) => {
        updateRow(p, entry);
        //$(p).children().eq(2).children().eq(0).text(item.trainingPeriod);
    })
}

function validateNum(data) {
    return data.match(/^[0-9]+$/) != null
}

function updateRow(p, entry) {
    for(let i = 1; i < $('#addEntry').children().length - 1; i++) {
        let inputVarName = getInputVarName(i, p);
        console.log(inputVarName);
        $(p).children().eq(i).children().eq(0).text(entry[inputVarName]);
    }
}