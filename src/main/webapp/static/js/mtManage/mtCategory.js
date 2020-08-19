var datagridMain;
var datagridDetail;
var dialogMain;
var dialogDetail;
$(function () {
        datagridMain = $('#mdg_main').datagrid({
            method: "post",
            url: getRootPath()+'/mtManage/assets/list_MainGrid',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'typeMainId',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#tb_category',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'typeMainId', title: 'id', hidden: true},
                {field: 'typeMainName', title: '分类名称', sortable: false, width: 20},
                {field: 'remark', title: '备注', sortable: false, width: 20},
                {field: 'deptId', title: 'dept_id', hidden: true},
                {field: 'deptName', title: '所属部门', sortable: false, width: 25},
                {
                    field: 'operate', title: '操作', width: 42,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove(" + row.typeMainId + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit(" + row.typeMainId + ")' class='button-edit button-blue'>编辑</a>";
                        return e + '  ' + d;
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});

                if (data != null && data.rows != null && data.rows.length > 0) {
                    $('#mdg_main').datagrid('checkRow', 0);
                    //$('#mdg_main').datagrid('selectRow', 0);
                }
            },
            onSelect: function (index, row) {
                getAssetsDtl();
//              $('#btn-edit').show();
                $('#btn-category-delete').show();
            },
            queryParams: {
                "typeMainName":''
            }
        });
    });

//获取物资明细
function getAssetsDtl(){
    var row = datagridMain.datagrid('getSelected');
    if (row == null) {
        return null;
    } else {
        datagridDetail = $('#mdg_detail').datagrid({
            method: "post",
            url: getRootPath()+'/mtManage/assets/list_DetailGrid',
            //fit: true,
            border: true,
            idField: 'typeDtlId',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#tb_detail',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'typeDtlId', title: 'id', hidden: true},
                {field: 'typeDtlName', title: '物资名称', sortable: false, width: 80},
                {field: 'typeDtlNo', title: '物资编号', sortable: false, width: 80},
                {field: 'model', title: '型号', sortable: false, width: 80},
                {field: 'sizes', title: '规格', sortable: false, width: 80},
                {field: 'user_to', title: '用途', sortable: false, width: 80},
                {field: 'k3_code', title: 'k3编码', sortable: false, width: 80},
                {field: 'brand_name', title: '品牌', sortable: false, width: 80},
                {field: 'remark', title: '备注', sortable: false, width: 80},
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove_dtl(" + row.typeDtlId + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit_dtl(" + row.typeDtlId + ")' class='button-edit button-blue'>编辑</a>";
                        return e + '  ' + d;
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});
            },
            onSelect: function (index, row) {

            },
            queryParams: {
                "typeDtlName":'',
                "typeMainId":row.typeMainId
            }
        });
    }
}

function getSelectedMenu(){
    var row = resourceDataGrid.datagrid('getSelected');
    if (row == null) {
        return null;
    } else {
        return row.id;
    }
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


function queryType() {
    console.log($("#typeMain").val());
    $(datagridMain).datagrid('load', {
        "typeMainName":$("#typeMain").val()
      }
    );
}

function queryDtl() {
    var row = datagridMain.datagrid('getSelected');
    $(datagridDetail).datagrid('load', {
            "typeMainId":row.typeMainId,
            "typeDtlName":$("#dtlName").val()
        }
    );
}



/**
 * 添加分类
 */
function add() {
    $.getJSON(getRootPath()+"/mtManage/assets/list_dept",function(json) {
        $('#deptInfo').combobox({
            data: json.data, //获取到的json 数据
            valueField:"dept_id",
            textField:"dept_name"
        });
    });

    dialogMain = $("#dlg").dialog({
        title: '添加分类',
        width: 360,
        height: 280,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_main").form('validate');
                var dept_id=$("#deptInfo").combobox("getValue");
                var dept_name=$("#deptInfo").combobox("getText");
                console.log(dept_id+"&&"+dept_name);
                var addObj = [{"name":"dept_id","value":dept_id},{"name":"dept_name","value":dept_name}];
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/add_MainGrid",
                        loading: true,
                        data: getFormJson("#form_main","deptInfo",addObj),//$('#form_main').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialogMain.dialog('close');
                                queryType();
                            } else if (data.code == 400) {
                                U.msg('参数验证失败');
                            } else if (data.code == 409) {
                                U.msg('分类已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialogMain.dialog('close');
            }
        }]
    });

}

/**
 * 添加物资
 */
function add_dtl() {

    var row_main = datagridMain.datagrid('getSelected');
    if(row_main == null){
        U.msg('请先选择分类');
        return
    }

    dialogDetail = $("#dlg_dtl").dialog({
        title: '添加分类',
        width: 360,
        height: 280,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_dtl").form('validate');
                var type_main_id = row_main.typeMainId;
                var type_main_name = row_main.typeMainName;
                var addObj = [{"name":"type_main_id","value":type_main_id},{"name":"type_main_name","value":type_main_name}];
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/add_DtlGrid",
                        loading: true,
                        data: getFormJson("#form_dtl","type_dtl_id",addObj),//自定义过滤字段和追加字段
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialogDetail.dialog('close');
                                getAssetsDtl();;
                            } else if (data.code == 400) {
                                U.msg('参数验证失败');
                            } else if (data.code == 409) {
                                U.msg('物资编码已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialogDetail.dialog('close');
            }
        }]
    });

}

/**
 * 编辑分类
 */
function edit(id) {
    var row = datagridMain.datagrid('getSelected');
    if(row == null){
        U.msg('请先选择分类');
        return
    }

    $.getJSON(getRootPath()+"/mtManage/assets/list_dept",function(json) {
        $('#deptInfo').combobox({
            data: json.data, //获取到的json 数据
            valueField:"dept_id",
            textField:"dept_name"
        });
        console.log(row.deptId);
        $("#deptInfo").combobox('setValue',row.deptId);
    });
    dialogMain = $("#dlg").dialog({
        title: '编辑分类',
        width: 360,
        height: 280,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_main").form('validate');
                var dept_id=$("#deptInfo").combobox("getValue");
                var dept_name=$("#deptInfo").combobox("getText");
                console.log(dept_id+"&&"+dept_name);
                var addObj = [{"name":"dept_id","value":dept_id},{"name":"dept_name","value":dept_name}];
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/update_MainGrid",
                        loading: true,
                        data: getFormJson("#form_main","deptInfo",addObj),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialogMain.dialog('close');
                                queryType();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该分类');
                            } else if (data.code == 409) {
                                U.msg('该分类已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialogMain.dialog('close');
            }
        }]
    });
    //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
    setVal("#type_main_name",row.typeMainName);
    $("#type_main_id").val(row.typeMainId);
    setVal("#remark",row.remark);
}

/**
 * 编辑物资
 */
function edit_dtl(id) {
    var row_main = datagridMain.datagrid('getSelected');
    if(row_main == null){
        U.msg('请先选择分类');
        return
    }

    var row = datagridDetail.datagrid('getSelected');
    if(row == null){
        U.msg('请先选择物资');
        return
    }
    dialogDetail = $("#dlg_dtl").dialog({
        title: '编辑物资',
        width: 360,
        height: 280,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_dtl").form('validate');
                var type_main_id = row_main.typeMainId;
                var type_main_name = row_main.typeMainName;
                var addObj = [{"name":"type_main_id","value":type_main_id},{"name":"type_main_name","value":type_main_name}];
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/update_DtlGrid",
                        loading: true,
                        data: getFormJson("#form_dtl","",addObj),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialogDetail.dialog('close');
                                getAssetsDtl();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该分类');
                            } else if (data.code == 409) {
                                U.msg('该分类已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialogDetail.dialog('close');
            }
        }]
    });
    //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
    setVal("#type_dtl_name",row.typeDtlName);
    $("#type_dtl_id").val(row.typeDtlId);
    setVal("#type_dtl_no",row.typeDtlNo);
    setVal("#model",row.model);
    setVal("#sizes",row.sizes);
    setVal("#remark_dtl",row.remark);
}


/**
 * 删除分类
 */
function remove(id) {
    if (id == null) {
        var row = datagridMain.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择分类');
            return
        } else {
            id = row.typeMainId;
        }
    }

    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            U.post({
                url:  getRootPath()+'/mtManage/assets/delType/'+id,
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryType();
                    } else if (data.code == 400) {//参数验证失败
                        U.msg('参数验证失败');
                    } else if (data.code == 404) {
                        U.msg('未找到该分类');
                    } else if (data.code == 424) {
                        U.msg('该分类已被使用，无法删除');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}

/**
 * 删除物资
 */
function remove_dtl(id) {
    if (id == null) {
        var row = datagridDetail.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择物资');
            return
        } else {
            id = row.typeDtlId;
        }
    }

    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            U.post({
                url:  getRootPath()+'/mtManage/assets/delDtl/'+id,
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        getAssetsDtl();
                    } else if (data.code == 400) {//参数验证失败
                        U.msg('参数验证失败');
                    } else if (data.code == 404) {
                        U.msg('未找到该分类');
                    } else if (data.code == 424) {
                        U.msg('该分类已被使用，无法删除');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}


