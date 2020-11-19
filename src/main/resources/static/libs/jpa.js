var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function addItem(item) {
    var entry =  {};
    for(var i = 1; i < $('#addEntry').children().length - 1; i++){
        var currentVal = $('#addEntry').children().eq(i).children().eq(0).val();
        var inputVarName = getInputVarName(i, $('#addEntry'));
        entry[inputVarName] = currentVal;
    }
    console.log(entry);
    sendPost(`${window.location.pathname}/add`, entry, (item) => {
        addNewRow(item);
        $('#inputText').val('');
        $('#inputText').focus();
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


function sendPost(path, param, run) {
    console.log(param);
    console.log(path);
    console.log('****************************************************************************');
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

function addNewRow(entry) {
    console.log(JSON.stringify(entry));
    let currentRow = $("#addEntry");
    let last = $(currentRow).prev();
    let newRow = $(NEW_ROW_PATTERN);
    $(newRow).children().eq(0).text(parseInt($(newRow).children().eq(0).text()) + 1);
    let nom = parseInt($(last).children().eq(0).text());
    if (nom == undefined) {
        nom = 1;
    }
    else {
        nom = nom + 1;
    }  
    $(newRow).children().eq(0).html(`<div>${nom}</div>`)
    $(newRow).attr('id', `id${entry.id}`);
    for(let i = 1; i < $('#addEntry').children().length - 1; i++) {
        let name = getInputVarName(i, currentRow);
        console.log('varname ' + $(newRow).children().eq(i).children().eq(0).text(entry[name]).html());
        $(newRow).children().eq(i).children().eq(0).text(entry[name]);
    }
    console.log('nt' + $(newRow).html());
    $(currentRow).before($(newRow));
}
