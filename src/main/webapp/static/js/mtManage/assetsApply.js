var datagrid;
var dialog;
var dialog_flow;
var dialog_operator;
var flowDataGrid;
var operatorDataGrid;
var dialog_operator_form;
var dialog_flow_form;
var dialog_apply_form;
var assetsDataGrid;
var dialog_assets;
    $(function () {
        //物资申请列表
        datagrid = $('#dg').datagrid({
            method: "get",
            url: getRootPath()+'/mtManage/assets/list_applyGrid',
            fit: true,
            border: true,
            idField: 'apply_id',
            striped: true,
            pagination: true,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: true,
            checkOnSelect: true,
            toolbar: '#tb',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'apply_id', title: 'apply_id', hidden: true},
                {field: 'erp_no', title: 'ERP', width: 100},
                {field: 'apply_dept', title: '部门',  width: 100},
                {field: 'apply_man', title: '申请人',  width: 100},
                {field: 'return_plandate', title: '预计归还',  width: 100},
                {field: 'apply_time', title: '申请时间',  width: 100},
                {field: 'state', title: '状态',  width: 100},
                {field: 'currentuser', title: '当前责任人',  width: 100},
                {field: 'dept_name', title: '物资部门',  width: 100},
                {field: 'type_main_name', title: '物资类别',  width: 100},
                {field: 'type_dtl_name', title: '物资名称',  width: 100},
                {field: 'type_dtl_no', title: '物资编号',  width: 100},
                {field: 'model', title: '型号',  width: 100},
                {field: 'sizes', title: '规格',  width: 100},
                {field: 'out_type', title: '出库类型', width: 100},
                {field: 'apply_num', title: '申请数量',  width: 100},
                {field: 'backto_user', title: '打回人',  width: 100},
                {field: 'backto_time', title: '打回时间',  width: 100},
                {field: 'besureout_user', title: '发放人',  width: 100},
                {field: 'besureout_time', title: '发放时间',  width: 100},
                {field: 'refusereason_user', title: '拒绝发放人',  width: 100},
                {field: 'refusereason', title: '拒绝发放原因',  width: 100},
                {field: 'operator_record', title: '操作记录',  width: 100},
                {field: 'apply_reason', title: '申请原因',  width: 100},
                {field: 'stock_num', title: '库存数量',  width: 100},
                {field: 'demand_time', title: '需求日期',  width: 100},
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        var r = "<a onclick='getRoles(" + row.id + ")' class='button-edit button-teal'>角色设置</a>";
                        if (row.isFixed == 1) {//固定的
                            return e + '  ' + r;
                        } else {
                            return e + '  ' + d + '  ' + r;
                        }
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});

                if (data) {
                    $.each(data.rows,
                        function (index, item) {
                            if (item.checked) {
                                $('#dg').datagrid('checkRow', index);
                            }
                        });
                }
            },
            onSelect: function (index, row) {

            },
            onDblClickRow: function(index, row){
                setVal("#type_main_name",row.type_main_name);
                $("#apply_id").val(row.apply_id);
                //修改物资申请
                operator_apply();
            },
            queryParams: {
                username: $('#username').val(),
                mobile: $('#mobile').val(),
                gender: $('#gender').val()
            }
        });
    });

    //查询操作员
    function queryOperator() {
         var row_flow = flowDataGrid.datagrid('getSelected');
         if(row_flow == null){
            return;
         }
         var id = row_flow.id;
         $(operatorDataGrid).datagrid('load', {
                id:id
         }
        );
    }

    //流程查询
    function queryFlow() {
            $(flowDataGrid).datagrid('load', {
                    model_name:'工程物资申请'
                }
            );
    }


    //物资申请查询
    function queryApply() {
        $(datagrid).datagrid('load', {
             erp_no:$("#erp_no_s").val(),
             apply_man:$("#apply_man_s").val()
         }
        );
    }

    // 导出物资申请excel
    function exportApplyData(){
        var erp_no = $("#erp_no_s").val();
        var apply_man = $("#apply_man_s").val();
        window.location.href=getRootPath()+'/mtManage/assets/exportApplyData?erp_no='+erp_no
        +'&apply_man='+apply_man;
    }

    //添加操作员
    function add_operator() {

        var row_flow = flowDataGrid.datagrid('getSelected');
        if(row_flow == null){
            return;
        }
        var id = row_flow.id;
        var node_name = row_flow.node_name;
        $.getJSON(getRootPath()+"/mtManage/assets/list_dept",function(json) {
            $('#deptInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"dept_id",
                textField:"dept_name"
            });
        });

        $.getJSON(getRootPath()+"/mtManage/assets/list_user",function(json) {
            $('#userInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"user_id",
                textField:"user_name"
            });
        });
        dialog_operator_form = $("#dlg_operator_form").dialog({
            title: '添加操作员信息',
            width: 340,
            height: 300,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_operator").form('validate');
                    var dept_id=$("#deptInfo").combobox("getValue");
                    var dept_name=$("#deptInfo").combobox("getText");
                    var user_id=$("#userInfo").combobox("getValue");
                    var user_name=$("#userInfo").combobox("getText");
                    console.log(dept_id+"&&"+dept_name);
                    var addObj = [{"name":"dept_id","value":dept_id},
                                  {"name":"dept_name","value":dept_name},
                                  {"name":"user_id","value":user_id},
                                  {"name":"user_name","value":user_name}
                                 ];
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/add_operator",
                            loading: true,
                            data: getFormJson("#form_operator","deptInfo",addObj),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog_operator_form.dialog('close');
                                    queryOperator();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
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
                    dialog_operator_form.dialog('close');
                }
            }]
        });
        //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
        setVal("#operator_info",node_name);
        $("#operator_node").val(id);

    }
    //流程添加
    function add_flow() {
        dialog_flow_form = $("#dlg_flow_form").dialog({
            title: '添加流程信息',
            width: 340,
            height: 300,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_flow").form('validate');
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/add_operator",
                            loading: true,
                            data: $('#form_flow').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog_flow_form.dialog('close');
                                    queryFlow();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
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
                    dialog_flow_form.dialog('close');
                }
            }]
        });

    }

    //添加申请
    function add_apply(){
        setVal("#dept_name","");
        setVal("#type_main_name","");
        setVal("#type_dtl_name","");
        setVal("#type_dlt_no","");
        setVal("#model","");
        setVal("#sizes","");
        setVal("#return_plandate","");
        setVal("#apply_num","");
        setVal("#apply_man","");
        setVal("#apply_time","");
        setVal("#currentuser","");
        setVal("#backto_user","");
        setVal("#backto_time","");
        setVal("#besureout_user","");
        setVal("#besureout_time","");
        setVal("#refusereason","");
        setVal("#operator_record","");
        setVal("#apply_reason","");
        setVal("#stock_num","");
        setVal("#demand_time","");
        $("#assets_id").val("");
        $("#apply_id").val("");
        setVal("#conProductNum","");
        setVal("#conDept","");
        setVal("#sdOrderAmount","");
        setVal("#cusSentToName","");
        $.getJSON(getRootPath()+"/mtManage/assets/list_erp",function(json) {
            $('#erpInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"conErpNo",
                textField:"conErpNo",
                onSelect: function (record) {
                    if(record.conErpNo!=""){
                        U.post({
                            url: getRootPath()+"/mtManage/assets/getErpInfo",
                            loading: true,
                            data: {
                                conErpNo:record.conErpNo
                            },
                            success: function (data) {
                                if (data.code == 200) {
                                    var erpInfo = data.data[0];
                                    setVal("#conProductNum",erpInfo.conProductNum);
                                    setVal("#conDept",erpInfo.conDept);
                                    setVal("#sdOrderAmount",erpInfo.sdOrderAmount);
                                    setVal("#cusSentToName",erpInfo.cusSentToName);
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            });
            //$("#erpInfo").combobox('setText',row.user_name);
        });

        $("#apply_time_span").hide();
        $("#apply_time_td").hide();
        //$("#apply_time").next().hide();
        /*$("#currentuser_span").hide();
        $("#currentuser").next().hide();*/
        $("#backto_user_td").hide();
        $("#backto_user_span").hide();
        $("#backto_time_span").hide();
        $("#backto_time_td").hide();
        $("#besureout_user_span").hide();
        $("#besureout_user_td").hide();
        $("#besureout_time_span").hide();
        $("#besureout_time_td").hide();
        $("#refusereason_user_span").hide();
        $("#refusereason_user_td").hide();
        $("#refusereason_span").hide();
        $("#refusereason_td").hide();
        $("#refusereason_time_span").hide();
        $("#refusereason_time_td").hide();
        dialog_apply_form = $("#dlg_apply_form").dialog({
            title: '添加申请',
            width: 680,
            height: 500,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_apply").form('validate');
                    //getFormJson("#form_apply","deptInfo","");
                    //console.log($('#form_apply'));
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/add_apply",
                            loading: true,
                            data: $('#form_apply').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog_apply_form.dialog('close');
                                    queryApply();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
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
                    dialog_apply_form.dialog('close');
                }
            }]
        });
    }

    /*流程编辑*/
    function edit_flow() {

        var row_flow = flowDataGrid.datagrid('getSelected');
        if(row_flow == null){
            U.msg('请先选中');
            return;
        }
        var id = row_flow.id;
        var node_name = row_flow.node_name;

        $("#serial_flag").combobox('setValue',row.serial_flag);

        dialog_flow_form = $("#dlg_flow_form").dialog({
            title: '修改流程信息',
            width: 340,
            height: 300,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_flow").form('validate');
                    var addObj = [];
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/update_flow",
                            loading: true,
                            data: $("#form_flow").serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog_flow_form.dialog('close');
                                    queryFlow();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('已存在');
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
                    dialog_flow_form.dialog('close');
                }
            }]
        });
        console.log(node_name);
        console.log(id);
        //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
        setVal("#operator_info",node_name);
        $("#operator_node").val(id);
    }

    //物资申请编辑
    function edit_apply() {

        var row_apply = datagrid.datagrid('getSelected');
        if(row_apply == null){
            U.msg('请先选中');
            return;
        }
        var id = row_apply.apply_id;
        var state = row_apply.state;
        var apply_man = row_apply.apply_man;
        var currentuser = row_apply.currentuser;

        setVal("#dept_name",row_apply.dept_name);
        setVal("#type_main_name",row_apply.type_main_name);
        setVal("#type_dtl_name",row_apply.type_dtl_name);
        setVal("#type_dtl_no",row_apply.type_dtl_no);
        setVal("#model",row_apply.model);
        setVal("#sizes",row_apply.sizes);
        setVal("#return_plandate",new Date(row_apply.return_plandate).format('yyyy-MM-dd'));
        setVal("#apply_num",row_apply.apply_num);
        setVal("#apply_man",apply_man);
        setVal("#apply_time",new Date(row_apply.apply_time).format('yyyy-MM-dd'));
        setVal("#currentuser",currentuser);
        setVal("#backto_user",row_apply.backto_user);
        setVal("#backto_time",new Date(row_apply.backto_time).format('yyyy-MM-dd'));
        setVal("#besureout_user",row_apply.besureout_user);
        setVal("#besureout_time",row_apply.besureout_time);
        setVal("#refusereason",row_apply.refusereason);
        setVal("#operator_record",row_apply.operator_record);
        setVal("#demand_time",new Date(row_apply.demand_time).format('yyyy-MM-dd'));
        setVal("#stock_num",row_apply.stock_num);
        setVal("#apply_reason",row_apply.apply_reason);
        $("#assets_id").val(row_apply.assets_id);
        $("#apply_id").val(id);
        $("#out_type").combobox('setValue',row_apply.out_type);
        $.getJSON(getRootPath()+"/mtManage/assets/list_erp",function(json) {
            $('#erpInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"conErpNo",
                textField:"conErpNo",
                onSelect: function (record) {
                    console.log(record.conErpNo);
                    if(record.conErpNo!=""){
                        U.post({
                            url: getRootPath()+"/mtManage/assets/getErpInfo",
                            loading: true,
                            data: {
                                conErpNo:record.conErpNo
                            },
                            success: function (data) {
                                if (data.code == 200) {
                                    var erpInfo = data.data[0];
                                    setVal("#conProductNum",erpInfo.conProductNum);
                                    setVal("#conDept",erpInfo.conDept);
                                    setVal("#sdOrderAmount",erpInfo.sdOrderAmount);
                                    setVal("#cusSentToName",erpInfo.cusSentToName);
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            });
            $("#erpInfo").combobox('setValue',row_apply.erp_no);
        });

        $("#apply_time_span").hide();
        $("#apply_time_td").hide();
        //$("#apply_time").next().hide();
        /*$("#currentuser_span").hide();
        $("#currentuser").next().hide();*/
        $("#backto_user_td").hide();
        $("#backto_user_span").hide();
        $("#backto_time_span").hide();
        $("#backto_time_td").hide();
        $("#besureout_user_span").hide();
        $("#besureout_user_td").hide();
        $("#besureout_time_span").hide();
        $("#besureout_time_td").hide();
        $("#refusereason_user_span").hide();
        $("#refusereason_user_td").hide();
        $("#refusereason_span").hide();
        $("#refusereason_td").hide();
        $("#refusereason_time_span").hide();
        $("#refusereason_time_td").hide();
        dialog_apply_form = $("#dlg_apply_form").dialog({
            title: '编辑申请',
            width: 680,
            height: 500,
            maximizable: false,
            modal: true,
            buttons: [{
                id:'updateBtn',
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_apply").form('validate');
                    //getFormJson("#form_apply","deptInfo","");
                    //console.log($('#form_apply'));
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/update_apply",
                            loading: true,
                            data: $('#form_apply').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('提交成功');
                                    dialog_apply_form.dialog('close');
                                    queryApply();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            },{
                id:'checkBtn',
                text: '审核',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_apply").form('validate');
                    //getFormJson("#form_apply","deptInfo","");
                    //console.log($('#form_apply'));
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/check_apply",
                            loading: true,
                            data: $('#form_apply').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog_apply_form.dialog('close');
                                    queryApply();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            },{
                id:'applyBtn',
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_apply").form('validate');
                    //getFormJson("#form_apply","deptInfo","");
                    //console.log($('#form_apply'));
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/add_apply",
                            loading: true,
                            data: $('#form_apply').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog_apply_form.dialog('close');
                                    queryApply();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
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
                    dialog_apply_form.dialog('close');
                }
            }]
        });

        $('#applyBtn').hide();
        $('#checkBtn').hide();

        //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
        //setVal("#operator_info",node_name);
        //$("#operator_node").val(id);
    }

//物资申请审核 打回操作
function operator_apply() {

    var row_apply = datagrid.datagrid('getSelected');
    if(row_apply == null){
        U.msg('请先选中');
        return;
    }
    var id = row_apply.apply_id;
    var state = row_apply.state;
    var apply_man = row_apply.apply_man;
    var currentuser = row_apply.currentuser;
    var node_seq = row_apply.node_seq;
    var turn_node_seq = row_apply.turn_node_seq;
    setVal("#dept_name",row_apply.dept_name);
    setVal("#type_main_name",row_apply.type_main_name);
    setVal("#type_dtl_name",row_apply.type_dtl_name);
    setVal("#type_dtl_no",row_apply.type_dtl_no);
    setVal("#model",row_apply.model);
    setVal("#sizes",row_apply.sizes);
    setVal("#return_plandate",new Date(row_apply.return_plandate).format('yyyy-MM-dd'));
    setVal("#apply_num",row_apply.apply_num);
    setVal("#apply_man",apply_man);
    setVal("#apply_time",new Date(row_apply.apply_time).format('yyyy-MM-dd'));
    setVal("#currentuser",currentuser);
    setVal("#backto_user",row_apply.backto_user);
    setVal("#backto_time",new Date(row_apply.backto_time).format('yyyy-MM-dd'));
    setVal("#besureout_user",row_apply.besureout_user);
    setVal("#besureout_time",row_apply.besureout_time);
    setVal("#refusereason",row_apply.refusereason);
    setVal("#operator_record",row_apply.operator_record);
    setVal("#demand_time",new Date(row_apply.demand_time).format('yyyy-MM-dd'));
    setVal("#stock_num",row_apply.stock_num);
    setVal("#apply_reason",row_apply.apply_reason);
    $("#assets_id").val(row_apply.assets_id);
    $("#apply_id").val(id);
    $("#out_type").combobox('setValue',row_apply.out_type);

    $.getJSON(getRootPath()+"/mtManage/assets/list_erp",function(json) {
        $('#erpInfo').combobox({
            data: json.data, //获取到的json 数据
            valueField:"conErpNo",
            textField:"conErpNo",
            onSelect: function (record) {
                if(record.conErpNo!=""){
                    U.post({
                        url: getRootPath()+"/mtManage/assets/getErpInfo",
                        loading: true,
                        data: {
                            conErpNo:record.conErpNo
                        },
                        success: function (data) {
                            if (data.code == 200) {
                                var erpInfo = data.data[0];
                                setVal("#conProductNum",erpInfo.conProductNum);
                                setVal("#conDept",erpInfo.conDept);
                                setVal("#sdOrderAmount",erpInfo.sdOrderAmount);
                                setVal("#cusSentToName",erpInfo.cusSentToName);
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 409) {
                                U.msg('');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        });
        $("#erpInfo").combobox('setValue',row_apply.erp_no);
    });


    //$("#apply_time_span").hide();
    //$("#apply_time_td").hide();
    //$("#apply_time").next().hide();
    /*$("#currentuser_span").hide();
    $("#currentuser").next().hide();*/
    $("#backto_user_td").hide();
    $("#backto_user_span").hide();
    $("#backto_time_span").hide();
    $("#backto_time_td").hide();
    $("#besureout_user_span").hide();
    $("#besureout_user_td").hide();
    $("#besureout_time_span").hide();
    $("#besureout_time_td").hide();
    $("#refusereason_user_span").hide();
    $("#refusereason_user_td").hide();
    $("#refusereason_span").hide();
    $("#refusereason_td").hide();
    $("#refusereason_time_span").hide();
    $("#refusereason_time_td").hide();
    dialog_apply_form = $("#dlg_apply_form").dialog({
        title: '物资申请',
        width: 680,
        height: 500,
        maximizable: false,
        modal: true,
        buttons: [{
            id:'checkBtn',
            text: '审核',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_apply").form('validate');
                if (isValid) {
                    var addObj = [{"name":"node_seq","value":node_seq},
                        {"name":"turn_node_seq","value":turn_node_seq}
                    ];
                    U.post({
                        url: getRootPath()+"/mtManage/assets/check_apply",
                        loading: true,
                        data: getFormJson("#form_apply","deptInfo",addObj),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog_apply_form.dialog('close');
                                queryApply();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        },{
            id:'issueBtn',
            text: '发放',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_apply").form('validate');
                //getFormJson("#form_apply","deptInfo","");
                //console.log($('#form_apply'));
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/issue_apply",
                        loading: true,
                        data: $('#form_apply').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog_apply_form.dialog('close');
                                queryApply();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 409) {
                                U.msg('用户已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            id:'backBtn',
            text: '打回',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form_apply").form('validate');
                //getFormJson("#form_apply","deptInfo","");
                //console.log($('#form_apply'));
                if (isValid) {
                    U.post({
                        url: getRootPath()+"/mtManage/assets/back_apply",
                        loading: true,
                        data: $('#form_apply').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('操作成功');
                                dialog_apply_form.dialog('close');
                                queryApply();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        },{
            text: '取消',
            handler: function () {
                dialog_apply_form.dialog('close');
            }
        }]
    });

    //$('#applyBtn').hide();
    if("审核"==state){
        $('#issueBtn').hide();
    }
    if("已审核"==state){
        $('#checkBtn').hide();
        $('#backBtn').hide();
    }
    if("打回"==state){
        $('#checkBtn').hide();
        $('#backBtn').hide();
        $('#issueBtn').hide();
    }
    if("发放"==state){
        $('#checkBtn').hide();
        $('#backBtn').hide();
    }
    U.post({
        url: getRootPath()+"/mtManage/assets/judge_permission",
        loading: true,
        data: {
            apply_id:id,
            username:username,
            flowNode:state
        },
        success: function (data) {
            if (data.code == 200) {
                if(!data.data){
                    if("已审核"==state){
                        $('#issueBtn').hide();
                    }else if("审核"==state){
                        $('#checkBtn').hide();
                        $('#backBtn').hide();
                        //$('#issueBtn').hide();
                    }else if("发放"==state){
                        $('#issueBtn').hide();
                    }
                }
            } else if (data.code == 400) {//参数验证失败
                U.msg('参数验证失败');
            }  else {
                U.msg('服务器异常');
            }
        }
    });

    //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
    //setVal("#operator_info",node_name);
    //$("#operator_node").val(id);
}


    function edit_operator() {
        var row = operatorDataGrid.datagrid('getSelected');
        if(row == null){
            U.msg('请先选中');
            return
        }

        var row_flow = flowDataGrid.datagrid('getSelected');
        if(row_flow == null){
            return;
        }
        var id = row_flow.id;
        var node_name = row_flow.node_name;
        $.getJSON(getRootPath()+"/mtManage/assets/list_dept",function(json) {
            $('#deptInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"dept_id",
                textField:"dept_name"
            });
            $("#deptInfo").combobox('setValue',row.deptId);
        });

        $.getJSON(getRootPath()+"/mtManage/assets/list_user",function(json) {
            $('#userInfo').combobox({
                data: json.data, //获取到的json 数据
                valueField:"user_id",
                textField:"user_name"
            });
            $("#userInfo").combobox('setText',row.user_name);
        });
        dialog_operator_form = $("#dlg_operator_form").dialog({
            title: '添加操作员信息',
            width: 340,
            height: 300,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form_operator").form('validate');
                    var dept_id=$("#deptInfo").combobox("getValue");
                    var dept_name=$("#deptInfo").combobox("getText");
                    var user_id=$("#userInfo").combobox("getValue");
                    var user_name=$("#userInfo").combobox("getText");
                    var operator_id = row.id;
                    var addObj = [{"name":"dept_id","value":dept_id},
                        {"name":"dept_name","value":dept_name},
                        {"name":"user_id","value":user_id},
                        {"name":"user_name","value":user_name},
                        {"name":"id","value":operator_id}
                    ];
                    if (isValid) {
                        U.post({
                            url: getRootPath()+"/mtManage/assets/update_operator",
                            loading: true,
                            data: getFormJson("#form_operator","deptInfo",addObj),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog_operator_form.dialog('close');
                                    queryOperator();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('已存在');
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
                    dialog_operator_form.dialog('close');
                }
            }]
        });
        console.log(node_name);
        console.log(id);
        //setVal自定义函数 位置common.js 因为用了easyui-textbox 所以显示还需要特殊赋值
        setVal("#operator_info",node_name);
        $("#operator_node").val(id);
    }

    function remove_operator(id) {
        var row = operatorDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选中');
            return;
        } else {
            id = row.id;
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: getRootPath()+"/mtManage/assets/del_operator",
                    loading: true,
                    data: {id: id},
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryOperator();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该用户');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

    function remove_flow(id) {
        var row = flowDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选中');
            return;
        } else {
            id = row.id;
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: getRootPath()+"/mtManage/assets/del_flow",
                    loading: true,
                    data: {id: id},
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryFlow();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该用户');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

function remove_apply(id) {
    var row = datagrid.datagrid('getSelected');
    if (row == null) {
        U.msg('请先选中');
        return;
    } else {
        id = row.apply_id;
    }

    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            U.post({
                url: getRootPath()+"/mtManage/assets/del_apply",
                loading: true,
                data: {apply_id: id},
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryApply();
                    } else if (data.code == 400) {//参数验证失败
                        U.msg('参数验证失败');
                    } else if (data.code == 404) {
                        U.msg('未找到该用户');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}

    /**操作员配置列表*/
    function getOperator(){
            var row = flowDataGrid.datagrid('getSelected');
            var id;
            if (row == null) {
                U.msg('请先选择流程');
                return
            } else {
                id = row.id;
            }

        dialog_operator = $("#dlg_operator").dialog({
            title: '节点人员配置管理',
            width: 700,
            height: 400,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    U.post({
                        url: getRootPath()+'/sys/user/' + id + '/role/modify',
                        loading: true,
                        data: {
                            roles: getSelectedRoles()
                        },
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog_operator.dialog('close');
                                queryOperator();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该用户');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                handler: function () {
                    dialog_operator.dialog('close');
                }
            }]
        });

        operatorDataGrid = $('#operator-dg').datagrid({
            method: "post",
            url: getRootPath()+'/mtManage/assets/list_operator?id='+id,
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#bar-operator',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'dept_name', title: '部门', hidden:true},
                {field: 'dept_id', title: '部门ID', hidden:true},
                {field: 'operator_node', title: '节点ID', hidden:true},
                {field: 'operator_info', title: '节点名称', sortable: false, width: 25},
                {field: 'sequence', title: '排序',sortable:false,width:25},
                {field: 'user_name', title: '操作员',sortable:false,width:35},
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove_operator(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit_operator(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        return e + '  ' + d;

                    }

                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});
            }
        });

    }

    function getFlow() {
        dialog_flow = $("#dlg").dialog({
            title: '流程模板管理',
            width: 800,
            height: 400,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    U.post({
                        url: getRootPath()+'/sys/user/' + id + '/role/modify',
                        loading: true,
                        data: {
                            roles: getSelectedRoles()
                        },
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog_flow.dialog('close');
                                queryFlow();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该用户');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                handler: function () {
                    dialog_flow.dialog('close');
                }
            }]
        });

        flowDataGrid = $('#flow-dg').datagrid({
            method: "post",
            url: getRootPath()+'/mtManage/assets/list_flow',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#bar-flow',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'model_name', title: '模板名称', sortable: false, width: 25},
                {field: 'node_name', title: '节点名称', sortable: false, width: 25},
                {field: 'node_seq', title: '节点序号',hidden:true},
                {field: 'turn_node_seq', title: '转向节点序号',hidden:true},
                {field: 'serial_flag', title: '是否并行操作', sortable: false, width: 50,
                   formatter:function(value,row,index){
                     if(value=='2'){
                         return '';
                     }else if(value =='1'){
                         return '是';
                     }else{
                         return '否';
                     }
                   }
                },
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove_flow(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit_flow(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        var r = "<a onclick='getOperator(" + row.id + ")' class='button-edit button-teal'>操作员配置</a>";
                        if (row.serial_flag == '2') {//物资申请
                            return e + '  ' + d;
                        } else {
                            return e + '  ' + d + '  ' + r;
                        }
                    }

                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});
            }
        });
    }

    function getAssets(){
        dialog_assets = $("#dlg_assets").dialog({
            title: '选择物资',
            width: 800,
            height: 350,
            maximizable: false,
            modal: true
        });

        assetsDataGrid = $('#assets-dg').datagrid({
            method: "post",
            url: getRootPath()+'/mtManage/assets/list_Detail',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'type_dtl_id',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'type_dtl_id', title: 'id', hidden: true},
                {field: 'type_dtl_name', title: '物资名称', sortable: false, width: 25},
                {field: 'type_dtl_no', title: '物资编号', sortable: false, width: 25},
                {field: 'model', title: '物资型号',sortable: false, width: 25},
                {field: 'sizes', title: '物资规格',sortable: false, width: 25},
                {field: 'type_main_id', title: '主分类id',hidden:true},
                {field: 'dept_name', title: '部门',hidden:true},
                {field: 'type_main_name', title: '分类名称', sortable: false, width: 25},
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove_flow(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit_flow(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        var r = "<a onclick='getOperator(" + row.id + ")' class='button-edit button-teal'>操作员配置</a>";
                        if (row.serial_flag == '2') {//物资申请
                            return e + '  ' + d;
                        } else {
                            return e + '  ' + d + '  ' + r;
                        }
                    }

                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});
            },
            queryParams: {
                "typeMainId":''
            },
            onDblClickRow: function(index, row){
                dialog_assets.dialog('close');
                setVal("#dept_name",row.dept_name);
                setVal("#type_dtl_name",row.type_dtl_name);
                setVal("#type_dtl_no",row.type_dtl_no);
                setVal("#model",row.model);
                setVal("#sizes",row.sizes);
                setVal("#type_main_name",row.type_main_name);
                $("#assets_id").val(row.type_dtl_id);
            }
        });
    }


    //保存用户角色
    function getSelectedRoles() {

        //所选的角色列表
        var roleIds = [];
        var data = $('#flow-dg').datagrid('getSelections');
        for (var i = 0, j = data.length; i < j; i++) {
            roleIds.push(data[i].id);
        }
        return roleIds;
    }

function printBill(){
    var row_apply = datagrid.datagrid('getSelected');
    var erpInfo;
    var nw;
    var conProductNum="";
    var sdOrderAmount="";
    var cusSentToName="";
    var conDept="";
    $.ajax({
        type:'post',
        url: getRootPath()+"/mtManage/assets/getErpInfo",
        data: {
            conErpNo:row_apply.erp_no
        },
        success: function (data) {
            if (data.code == 200) {
                erpInfo = data.data[0];
                var conProductNum=erpInfo.conProductNum;
                var sdOrderAmount=erpInfo.sdOrderAmount;
                var cusSentToName=erpInfo.cusSentToName;
                var conDept=erpInfo.conDept;
                nw = window.open();
                //将表格放在div里，然后把整个div里面的内容给赋给一个变量。
                var tab_html = '<div id="sendTitle" style="width:1150px;margin:0 auto;height:30px;"><img style="width:420px;height:60px;" src="/psystem/static/images/tablelogo.png"/></div>'
                    +"<div style=\"width:1150px;margin:0 auto\">"
                    +"     <p align=\"center\" style=\"font-size:20px;\">请购单</p>"
                    +"  <div width=\"1150px\" style=\"font-size:20px;\" style=\"margin-bottom:10px;\">"
                    +"    <span style=\"margin-right:100px;\">申购部门:"+row_apply.apply_dept+"</span>"
                    +"    <span style=\"margin-right:100px;\">申购人:"+row_apply.apply_man+"</span>"
                    +"   <span style=\"margin-right:100px;\">申请日期:"+new Date(row_apply.apply_time).format('yyyy-MM-dd')+"</span>"
                    +"  </div>"
                    +"  <div width=\"1150px\" style=\"font-size:20px;\">"
                    +"   <span>申购类型:"
                    +"   <input type=\"checkbox\" name=\"temp\" value=\"临时\" />临时"
                    +"   <input type=\"checkbox\" name=\"new\" value=\"新增\" style=\"margin-left:50px;\"/>新增"
                    +"   <input type=\"checkbox\" name=\"special\" value=\"订单特殊需求\" style=\"margin-left:50px;\" />订单特殊需求"
                    +"   <input type=\"checkbox\" name=\"temp\" value=\"外部维修\" style=\"margin-left:50px;\" />外部维修"
                    +"   </span>"
                    +" </div>"
                    +"  </div>"
                    +"  <table border=\"1\" cellspacing=\"0\"  width=\"1150px\" style=\"margin:0 auto;\">"
                    +"   <tbody>"
                    +"    <tr>"
                    +"     <th align=\"center\" width=\"100px;\">序号</th>"
                    +"     <th align=\"center\" width=\"100px;\">物品名称</th>"
                    +"     <th align=\"center\" width=\"100px;\">数量/单位</th>"
                    +"     <th align=\"center\" width=\"100px;\">规格</th>"
                    +"     <th align=\"center\" width=\"100px;\">单价</th>"
                    +"     <th align=\"center\" width=\"200px;\">用途</th>"
                    +"     <th align=\"center\" width=\"100px;\">申请原因</th>"
                    +"     <th align=\"center\" width=\"100px;\">需求日期</th>"
                    +"     <th align=\"center\" width=\"150px;\">确认库存数量</th>"
                    +"     <th align=\"center\" width=\"100px;\">备注</th>"
                    +"    </tr>"
                    +"    <tr id=\"item01\">"
                    +"     <td align=\"center\" width=\"100px;\">1</td>"
                    +"     <td align=\"center\" width=\"100px;\">"+row_apply.type_dtl_name+"</td>"
                    +"     <td align=\"center\" width=\"100px;\">"+row_apply.apply_num+"</td>"
                    +"     <td align=\"center\" width=\"100px;\">"+row_apply.sizes+"</td>"
                    +"     <td align=\"center\" width=\"100px;\"></td>"
                    +"     <td align=\"center\" width=\"200px;\">订单号:"+row_apply.erp_no+"</br>"
                    +"     机型:"+conProductNum+"</br>"
                    +"     数量:"+sdOrderAmount+"</br>"
                    +"     发往地:"+cusSentToName+"</br>"
                    +"     事业部:"+conDept
                    +"    </td>"
                    +"     <td align=\"center\" width=\"100px;\">"+row_apply.apply_reason+"</td>"
                    +"     <td align=\"center\" width=\"100px;\">"+new Date(row_apply.demand_time).format('yyyy-MM-dd')+"</td>"
                    +"     <td align=\"center\" width=\"150px;\">"+row_apply.stock_num+"</td>"
                    +"     <td align=\"center\" width=\"100px;\"></td>"
                    +"    </tr>"
                    +" <tr height=\"50px\">"
                    +" <td colspan=\"10\" >具体要求:</td>"
                    +"</tr>"
                    +"<tr height=\"40px\">"
                    +"  <td colspan=\"5\">预算资金:</td>"
                    +"  <td colspan=\"5\">结算资金:</td>"
                    +"</tr>"
                    +"<tr height=\"40px\">"
                    +   "<td colspan=\"3\">部门经理审核:</td>"
                    +   "<td colspan=\"4\">副总经理审核:</td>"
                    +   "<td colspan=\"3\">总经理审核:</td>"
                    +"</tr>"
                    +"<tr height=\"40px\">"
                    +"  <td colspan=\"5\">报价初核:</td>"
                    +"  <td colspan=\"5\">报价批准:</td>"
                    +"</tr>"
                    +"</tbody>"
                    +"</table>"
                    +"<div style=\"width:1150px;margin:0 auto;\">"
                    +"<p>说明：签名时，请签上日期。</p>"
                    +"</div>"
                    +"<div style=\"width:1150px;margin:0 auto;\">"
                    +" <p align=\"right\" style=\"height:10px;\">表单编号：UM-FM-GP-038D</p>"
                    +" <p align=\"right\" style=\"height:10px;align:right;\">保存期限：2年</p>"
                    +"</div>";
                var tit_html =	'<div id="sendTitle" style="width:1150px;"><center><span style="font-size:24px"><b></b></span></center></div>';
                tab_html = tab_html.replace(/disabled/g,"");
                tab_html = tab_html.replace(/<TEXTAREA/g,'<TEXTAREA readonly style="border:0px 0px 0px 0px;"');
                tab_html = tab_html.replace(/<INPUT/g,'<INPUT readonly style="border:0px 0px 0px 0px;" ');
                tab_html = tab_html.replace(/type=checkbox/g,'type=checkbox onClick="return false;"');
                tab_html = tab_html.replace(/type=button/g,'type=button style="visibility:hidden"');
                //console.log(tab_html);
                //document.getElementsByTagName('body')[0].style.zoom=0.92;
                nw.document.open( "text/html", "GB2312");
                nw.document.write("<style media=print>.Noprint{display:none;}.PageNext{page-break-BEFORE: always;} html{zoom:80%;}</style>");
                nw.document.write(tit_html);//打印第一个表格
                nw.document.write(tab_html);//打印第一个表格
                nw.document.write('<div  style="margin:0 auto;width:100px;"><input type="button" class="Noprint" onclick = "javascript:window.print()" value="确定打印" /></div>');
                //nw.location.reload();
                //nw.document.close();
            }
        },
        complete:function(){

        }
    });

    /*document.body.innerHTML=document.getElementById('dlg_apply_form').innerHTML;
    window.print();*/
}
