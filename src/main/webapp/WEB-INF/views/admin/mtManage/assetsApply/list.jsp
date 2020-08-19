<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>物资申请管理</title>
    <link href="${ctxstatic}/css/custom-style.css" rel="stylesheet" type="text/css">

    <%@ include file="/WEB-INF/views/include/common.jsp" %>
    <style>
        #form_apply tr {
              line-height: 35px;
              margin-bottom: 4px;
          }

        #form_apply tr input, #form_apply select,#form_apply a {
            width: 144px;
        }
        .combo, .textbox{
            border: 1px solid #b9b8b8;
        }
    </style>
    <script>
        var username= "${user.username}";
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="erp_no_s" class="easyui-textbox" data-options="label:'ERP'" style="width:160px;"/>
        <input id="apply_man_s" class="easyui-textbox" data-options="label:'申请人'" style="width:150px;"/>
        <select id="state_s" class="easyui-combobox" panelHeight="auto" name="state" label='状态' style="width:120px">
            <option value="">全部</option>
            <option value="审核">审核</option>
            <option value="已审核">已审核</option>
        </select>
        <a href="javascript:void(0)" onclick="queryApply()" class="easyui-linkbutton button-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add_apply()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-edit" href="javascript:void(0)" onclick="edit_apply()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-delete" href="javascript:void(0)" onclick="remove_apply" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
            <a href="javascript:void(0)" onclick="getFlow()" class="easyui-linkbutton" iconCls="icon-user-config"
               plain="true">流程配置</a>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true">剪切</a>--%>

        </div>
    </div>
</div>
<div id="dlg"> <%--流程--%>
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="flow-dg" style="width:100%;height:100%;">
        </table>
        <div id="bar-flow" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add_flow()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-flow-edit" href="javascript:void(0)" onclick="edit_flow()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-flow-delete" href="javascript:void(0)" onclick="remove_flow()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
            <a href="javascript:void(0)" onclick="getOperator()" class="easyui-linkbutton"
               iconCls="icon-user-config"
               plain="true">操作员配置</a>
        </div>
    </div>
</div>

<div id="dlg_assets"> <%--物资--%>
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="assets-dg" style="width:100%;height:100%;">
        </table>
    </div>
</div>

<div id="dlg_operator"> <%--操作员配置--%>
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="operator-dg" style="width:100%;height:100%;">
        </table>
        <div id="bar-operator" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add_operator()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-operator-edit" href="javascript:void(0)" onclick="edit_operator()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-operator-delete" href="javascript:void(0)" onclick="remove_operator()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
    </div>
</div>
<div id="dlg_operator_form">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" style="height:100%;">
            <form id="form_operator">
                <table style="margin: 0 auto; padding: 10px;">
                    <tr>
                        <td align="right">部门:</td>
                        <td>
                            <input  id="deptInfo" name="deptInfo" class="easyui-combobox" data-options="valueField: 'dept_id', textField: 'dept_name'"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">节点信息:</td>
                        <td>
                            <input id="operator_info" name="operator_info" class="easyui-textbox" data-options="required:true"/>
                            <input id="operator_node" name="operator_node" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">序号:</td>
                        <td><input id="sequence" name="sequence" class="easyui-numberspinner" value="0" data-options="spinAlign:'right'"/></td>
                    </tr>
                    <tr>
                        <td align="right">操作员:</td>
                        <td>
                            <input  id="userInfo" name="userInfo" class="easyui-combobox" data-options="valueField: 'user_id', textField: 'user_name'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<div id="dlg_flow_form">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" style="height:100%;">
            <form id="form_flow">
                <table style="margin: 0 auto; padding: 10px;">
                    <tr>
                        <td align="right">模板名称:</td>
                        <td>
                            <input id="model_name" name="model_name" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">节点名称:</td>
                        <td>
                            <input id="node_name" name="node_name" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">节点序号:</td>
                        <td>
                            <input id="node_seq" name="node_seq" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">转向节点序号:</td>
                        <td>
                            <input id="turn_node_seq" name="turn_node_seq" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">是否并行操作:</td>
                        <td>
                            <select name="serial_flag" class="easyui-combobox" panelHeight="auto">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </td>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<div id="dlg_apply_form">
    <div class="easyui-layout" data-options="fit:true,border:false" >

        <div data-options="region:'center',border:false" style="width:100%;height:100%;" toolbar="#dlg-toolbar">
            <!--创建Toolbar-->
            <div id="dlg-toolbar" class="applytoolbar">
                <a  id="printBill" href="#" class="easyui-linkbutton" iconcls="icon-print" plain="true" onclick="printBill()">打印</a>
                <a  href="#" class="easyui-linkbutton" iconcls="icon-help" plain="true">Help</a>
            </div>
            <form id="form_apply">
                <table style="margin: 0 auto; padding: 10px;">
                    <tr>
                        <td align="right">ERP:</td>
                        <td>
                            <input  id="erpInfo" name="erpInfo" class="easyui-combobox" data-options="valueField: 'conErpNo', textField: 'conErpNo'"/>
                        </td>
                        <td align="right">机型:</td>
                        <td>
                            <input id="conProductNum" name="conProductNum" class="easyui-textbox" />
                        </td>
                        <td align="right">部门:</td>
                        <td>
                            <input id="conDept" name="conDept" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">数量:</td>
                        <td>
                            <input  id="sdOrderAmount" name="sdOrderAmount" class="easyui-textbox"/>
                        </td>
                        <td align="right">客户:</td>
                        <td>
                            <input id="cusSentToName" name="cusSentToName" class="easyui-textbox" />
                        </td>
                        <td align="right">选择:</td>
                        <td>
                            <a href="javascript:void(0)" onclick="getAssets()" class="easyui-linkbutton button-default">选择物资</a>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">物资部门:</td>
                        <td>
                            <input id="dept_name" name="dept_name" class="easyui-textbox" data-options="required:true"/>
                        </td>
                        <td align="right">物资类别:</td>
                        <td>
                            <input id="type_main_name" name="type_main_name" class="easyui-textbox" data-options="required:true"/>
                        </td>
                        <td align="right">物资名称:</td>
                        <td>
                            <input id="type_dtl_name" name="type_dtl_name" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">物资编号:</td>
                        <td>
                            <input id="type_dtl_no" name="type_dtl_no" class="easyui-textbox" data-options="required:true"/>
                            <input id="assets_id" name="assets_id" type="hidden"/>
                        </td>
                        <td align="right">型号:</td>
                        <td>
                            <input id="model" name="model" class="easyui-textbox" data-options="required:true"/>
                        </td>
                        <td align="right">规格:</td>
                        <td>
                            <input id="sizes" name="sizes" class="easyui-textbox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">出库类型:</td>
                        <td>
                            <select name="out_type" class="easyui-combobox" panelHeight="auto" style="width:144px;">
                                <option value="借用出库">借用出库</option>
                                <option value="永久出库">永久出库</option>
                            </select>
                        </td>
                        <td align="right">预计归还:</td>
                        <td>
                            <input id="return_plandate" name="return_plandate" type= "text" class= "easyui-datebox"/>
                        </td>
                        <td align="right">申请数量:</td>
                        <td>
                            <input id="apply_num" name="apply_num" class="easyui-textbox" data-options="required:true"/>
                            <input id="apply_id" name="apply_id" type="hidden"/>
                        </td>
                    </tr>

                    <tr>
                        <td align="right">申请人:</td>
                        <td>
                            <input id="apply_man" name="apply_man" class="easyui-textbox" value="${user.username}" data-options="readonly:true"/>
                        </td>
                        <td id="apply_time_span" align="right">申请时间:</td>
                        <td id="apply_time_td">
                            <input id="apply_time" name="apply_time" class="easyui-textbox" />
                        </td>
                        <td id="currentuser_span" align="right">审核人:</td>
                        <td id="currentuser_td">
                            <input id="currentuser" name="currentuser" class="easyui-textbox" data-options="readonly:true"/>
                        </td>
                    </tr>

                    <tr>
                        <td id="demand_time_span" align="right">需求时间:</td>
                        <td id="demand_time_td">
                            <input id="demand_time" name="demand_time" class="easyui-datebox" />
                        </td>
                        <td id="stock_num_span" align="right">库存数量:</td>
                        <td id="stock_num_td">
                            <input id="stock_num" name="stock_num" class="easyui-textbox" />
                        </td>
                        <td id="apply_reason_span" align="right">申请原因:</td>
                        <td id="apply_reason_td">
                            <input id="apply_reason" name="apply_reason" class="easyui-textbox" />
                        </td>
                    </tr>

                    <tr>
                        <td id="backto_time_span" align="right">打回时间:</td>
                        <td id="backto_time_td">
                            <input id="backto_time" name="backto_time" class="easyui-textbox" data-options="readonly:true"/>
                        </td>
                        <td id="backto_user_span" align="right">打回人:</td>
                        <td id="backto_user_td">
                            <input id="backto_user" name="backto_user" class="easyui-textbox" />
                        </td>

                    </tr>

                    <tr>
                        <td id="besureout_user_span" align="right">发放人:</td>
                        <td id="besureout_user_td">
                            <input id="besureout_user" name="besureout_user" class="easyui-textbox" />
                        </td>
                        <td id="besureout_time_span" align="right">发放时间:</td>
                        <td id="besureout_time_td">
                            <input id="besureout_time" name="besureout_time" class="easyui-textbox" data-options="readonly:true"/>
                        </td>
                    </tr>

                    <tr>
                        <td id="refusereason_user_span" align="right">拒绝发放人:</td>
                        <td id="refusereason_user_td">
                            <input id="refusereason_user" name="refusereason_user" class="easyui-textbox" data-options="readonly:true"/>
                        </td>
                        <td id="refusereason_span" align="right">拒绝原因:</td>
                        <td id="refusereason_td">
                            <input id="refusereason" name="refusereason" class="easyui-textbox" />
                        </td>
                        <td id="refusereason_time_span" align="right">拒绝时间:</td>
                        <td id="refusereason_time_td">
                            <input id="refusereason_time" name="refusereason_time" class="easyui-textbox" data-options="readonly:true"/>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="1">操作记录:</td>
                        <td colspan="5">
                            <input id="operator_record" name="operator_record" class="easyui-textbox" style="width:544px;height:60px;" data-options="multiline:true,readonly:true" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
<script src="${ctxstatic}/js/mtManage/assetsApply.js" type="text/javascript"></script>
</html>