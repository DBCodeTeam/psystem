function getRootPath() {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}

function setVal(id,value){
    $(id).val(value);
    $(id).textbox('setValue',value);
}

function getFormJson(form,filterName,addName) {
    var o = {};
    var a = $(form).serializeArray();
    for(var i=0;i<addName.length;i++){
        a.push(addName[i]);
    }
    console.log(a);
    $.each(a, function () {
        console.log(this.name);
        if (o[this.name] !== undefined && this.name!=filterName) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else if(this.name!=filterName){
            o[this.name] = this.value || '';
        }
    });
    console.log(o);
    return o;
}