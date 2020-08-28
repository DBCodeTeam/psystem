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
    <title>物资入库管理</title>
    <link href="${ctxstatic}/css/custom-style.css" rel="stylesheet" type="text/css">

    <%@ include file="/WEB-INF/views/include/common.jsp" %>
    <style>
        #form_apply tr {
            line-height: 35px;
            margin-bottom: 4px;
        }

        #form_apply tr input, #form_apply select, #form_apply a {
            width: 144px;
        }

        .combo, .textbox {
            border: 1px solid #b9b8b8;
        }
    </style>
    <script>
        var username = "${user.username}";
    </script>
</head>

<body class="easyui-layout">
<div data-options="region:'north',border:false" style="padding: 10px 5px;">
    <input id="k3_code" class="easyui-textbox" data-options="label:'K3编码'" style="width:200px;"/>
    <input id="stockin_man_s" class="easyui-textbox" data-options="label:'入库人'" style="width:150px;"/>
    <a href="javascript:void(0)" onclick="queryStockin()" class="easyui-linkbutton button-blue"
       style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
</div>
<div data-options="region:'center',border:false" style="height:100%">
    <table id="list">
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th data-options="field:'ck',checkbox:true"></th>--%>
<%--            <th data-options="field:'id',width:25">ID</th>--%>
<%--            <th data-options="field:'type_main',width:100">类型</th>--%>
<%--            <th data-options="field:'mt_name',width:100">物料名称</th>--%>
<%--            <th data-options="field:'mt_code',width:80">物料编码</th>--%>
<%--            <th data-options="field:'k3_code',width:110">K3编码</th>--%>
<%--            <th data-options="field:'model',width:100">型号</th>--%>
<%--            <th data-options="field:'sizes',width:100">规格参数</th>--%>
<%--            <th data-options="field:'num',width:50">数量</th>--%>
<%--            <th data-options="field:'unit',width:30">单位</th>--%>
<%--            <th data-options="field:'supplier',width:50">供应商</th>--%>
<%--            <th data-options="field:'remarks',width:95">备注</th>--%>
<%--            <th data-options="field:'username',width:50">入库人</th>--%>
<%--            <th data-options="field:'datetime',width:100,formatter:columShow">入库时间--%>
<%--            </th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
    </table>
    <!-- 工具条 -->
    <div id="toolbar">
        <a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
        <a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
        <a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
    </div>
</div>
<div id="editWin" class="easyui-dialog" data-options="title:'物料入库单',width:500,height:400,closed:true,modal:true">
    <form id="editForm" method="post">
        <%-- 提供一个隐藏域--%>
        <input type="hidden" name="id"/>

        <table style="margin:auto" width="100%">
            <tr>
                <td style="text-align: right">K3编码:</td>
                <td><input id="k3Code" type="text" name="k3Code" class="easyui-textbox"
                           data-options="required:true,prompt:'输入后回车'"/></td>
                <td style="text-align: right">物料编码:</td>
                <td><input type="text" name="mtCode" class="easyui-textbox"
                           data-options="required:false,readonly:true"/></td>
            </tr>
            <tr>
                <td style="text-align: right">类别:</td>
                <td><input type="text" name="typeMain" class="easyui-textbox"
                           data-options="required:false,readonly:true"/></td>
                <td style="text-align: right">物料名称:</td>
                <td><input type="text" name="mtName" class="easyui-textbox"
                           data-options="required:false,readonly:true"/></td>
            </tr>
            <tr>
                <td style="text-align: right">型号:</td>
                <td><input type="text" name="model" class="easyui-textbox" data-options="required:false,readonly:true"/>
                </td>
                <td style="text-align: right">规格:</td>
                <td><input type="text" name="sizes" class="easyui-textbox" data-options="required:false,readonly:true"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right">数量:</td>
                <td><input id="num" type="text" name="num" class="easyui-numberbox"
                           data-options="required:true,readonly:false,prompt:'输入数量'"/></td>
                <td style="text-align: right">单位:</td>
                <td><input type="text" name="unit" class="easyui-textbox" data-options="required:false,readonly:true"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right">入库人:</td>
                <td><input id="stockin_user" type="text" name="stockin_username" class="easyui-textbox"
                           data-options="required:false,readonly:true"/>
                </td>
                <td style="text-align: right">供应商:</td>
                <td><input id="supplier" type="text" name="supplier" class="easyui-textbox"
                           data-options="required:true,readonly:false,prompt:'输入供应商'"/></td>
            </tr>
            <tr>
                <td style="text-align: right">备注说明:</td>
                <td colspan="3"><input id="remarks" type="text" name="remarks" class="easyui-textbox"
                                       data-options="required:false,readonly:false,prompt:'备注说明',multiline:true,height:44,width:300"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a id="save" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
                    <a id="reset" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
<script src="${ctxstatic}/js/mtManage/assetsStockin.js" type="text/javascript"></script>
</html>