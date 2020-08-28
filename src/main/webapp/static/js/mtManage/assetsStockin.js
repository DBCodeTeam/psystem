
$(function () {
     $("#list").datagrid({
        method: "get",
        url: getRootPath() + "/mtManage/assets/list_stockinGrid",
        columns: [[
            {field:'ck',checkbox:true},
            {field: "id", title: "ID", width: 25},
            {field: "type_main", title: "类型", width: 100},
            {field: "mt_name", title: "物料名称", width: 100},
            {field: "mt_code", title: "物料编码", width: 80},
            {field: "k3_code", title: "K3编码", width: 110},
            {field: "model", title: "型号", width: 100},
            {field: "sizes", title: "规格参数", width: 100},
            {field: "num", title: "数量", width: 50},
            {field: "unit", title: "单位", width: 30},
            {field: "supplier", title: "供应商", width: 50},
            {field: "remarks", title: "备注", width: 95},
            {field: "username", title: "入库人", width: 95},
            {field: "datetime", title: "入库时间", width: 150
                //内容悬浮功能，未实现
                // formatter:function (value,row,index) {
                //     return '<span title='+value+'>'+value+'</span>';
                //     //return "<span title='123'>12111</span>";
                // }
            }
        ]],
        pagination: true,
        striped: true,
        // singleSelect:true,
        //工具条
        toolbar: "#toolbar",
    });

    //点击添加按钮，弹出编辑窗口
    $("#saveBtn").click(function () {
        //将表单清空
        $("#editForm").form("clear");
        //打开窗口
        $("#editWin").dialog("open");
    });

    //通过K3编码找到对应信息
    $("#k3Code").textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            var value = $("input[name='k3Code']").val().trim().toUpperCase();
            $.getJSON(getRootPath() + "/mtManage/assets/mtInfo?k3_code=" + value, function (json) {
                if (json.code == 200) {
                    $("#editForm").form("load", json.data);
                    $("#stockin_user").textbox({
                        value:username
                    });
                    $.messager.show({
                        title: "提示",
                        msg: "查询成功"
                    });
                } else {
                    $.messager.alert("提示", "未查询到对应数据:" + json.message, "error");
                    $("#editForm").form("clear");
                }
            })
        }
    });

    //保存商品
    $("#save").click(function () {
        var formUrl;
        if($("input[name='id']").val()==""){
            formUrl=getRootPath() + "/mtManage/assets/addMtRecord";
        }else{
            formUrl=getRootPath() + "/mtManage/assets/updateMtRecord";
        }
        $("#editForm").form("submit", {
            url:formUrl ,
            onSubmit: function () {
                //表单验证
                return $("#editForm").form("validate");
            },
            success: function (data) {
               // $.messager.alert("提示", "保存失败:" + data, "error");
                data = eval("(" + data + ")");//转成json
                if (data.message=='ok') {
                    //1.表单清空
                    $("#editForm").form("clear");
                    //2.关闭窗口
                    $("#editWin").window("close");
                    //3.刷新datagrid
                    $("#list").datagrid("reload");
                    if(formUrl==getRootPath() + "/mtManage/assets/updateMtRecord"){
                        $.messager.show({
                            title: "提示",
                            msg: "修改成功"
                        });
                    }else{
                        $.messager.show({
                            title: "提示",
                            msg: "新增成功"
                        });
                    }
                } else {
                    if(formUrl==getRootPath() + "/mtManage/assets/updateMtRecord"){
                        $.messager.alert("提示", "修改失败:" + data.message, "error");
                    }else{
                        $.messager.alert("提示", "新增失败:" + data.message, "error");
                    }
                }
            }
        });
    });
    $("#reset").click(function () {
        $("#editForm").form("clear");
    });

    //修改商品 -- 回显商品信息
    $("#editBtn").click(function () {

        //1.获取选择的商品
        var rows = $("#list").datagrid("getSelections");

        //判断一次只能选择一个
        if (rows.length != 1) {
            $.messager.alert("提示", "一次只能选择一行", "warning");
            return;
        }

        //2.获取第一行
        var row = rows[0];

        //3.到后台商品数据，填充到表单
        $.getJSON(getRootPath() + "/mtManage/assets/recordInfo?id=" + row.id, function (json) {
            $("#editForm").form("load", json.data);
        })

        //4.弹出编辑窗口
        $("#editWin").window("open");
    });

    //删除商品
    $("#deleteBtn").click(function () {
        //1.获取选择的商品
        var rows = $("#list").datagrid("getSelections");

        if (rows.length == 0) {
            $.messager.alert("提示", "至少选择一个商品", "warning");
            return;
        }

        //2.获取商品的id 格式： 字符串  1,2,3
        var ids = new Array();
        $(rows).each(function (i) {
            ids.push(rows[i].id);
        });

        ids = ids.join(",");//用逗号进行分隔
        //通过提示框确认是否进行删除
        $.messager.confirm('提示', '确定要删除吗？', function(r){
            if (r){
                //3.发送商品id到后台进行删除
                $.post(getRootPath() + "/mtManage/assets/deleRecordInfo", {"ids": ids}, function (data) {
                    if (data.message=="ok") {
                        //刷新datagrid
                        $("#list").datagrid("reload");
                        //提示
                        $.messager.show({
                            title: "提示",
                            msg: "删除成功"
                        });
                    } else {
                        $.messager.show({
                            title: "提示",
                            msg: "删除失败"
                        });
                    }
                }, "json");
            }
        });
    });
});

//单元格扩展悬浮显示功能
var columShow = function colum_Show(value,row,index){
    return '<span title='+value+'>'+value+'</span>'
}

// 物资入库查询
function queryStockin() {
    //console.log($("#stockin_man_s").val());
    $("#list").datagrid('load', {
            k3_code:$("#k3_code").val(),
            stockin_man:$("#stockin_man_s").val()
        }
    );
}

