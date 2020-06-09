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
    <title>物资分类</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>
    <script src="${ctxstatic}/js/mtManage/mtCategory.js" type="text/javascript"></script>
    <style>
        #form_main tr {
            line-height: 35px;
        }

        #form_main tr input, select {
            width: 220px;
        }
    </style>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'west',border:false" style="height:100%;width: 45%;">
        <%--<div id="rtb" style="height: 30px;">
        </div>--%>
        <div id="tb_category" style="padding:2px 5px;">
            <a id="btn-category-add" href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-category-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-category-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
        <table id="mdg_main" style="width:100%;height:100%;">
        </table>
    </div>
    <div data-options="region:'center',border:false" style="height:100%">
        <div id="tb_detail" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add_dtl()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-detail-edit" href="javascript:void(0)" onclick="edit_dtl()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-detail-delete" href="javascript:void(0)" onclick="remove_dtl()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
        <table id="mdg_detail" style="width:100%;height:100%;">
        </table>
    </div>


</div>
<div id="dlg">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" style="height:100%;">
            <form id="form_main">
                <table style="margin: 0 auto; padding: 10px;">
                    <tr>
                        <td align="right">名称:</td>
                        <td>
                            <input id="type_main_name" name="type_main_name" class="easyui-textbox" data-options="required:true"/>
                            <input id="type_main_id" name="type_main_id" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">所属部门:</td>
                        <td>
                            <input  id="deptInfo" name="deptInfo" class="easyui-combobox" data-options="valueField: 'dept_id', textField: 'dept_name'"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td><input id="remark" name="remark" class="easyui-textbox" data-options="required:true"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="dlg_dtl">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" style="height:100%;">
            <form id="form_dtl">
                <table style="margin: 0 auto; padding: 10px;">
                    <tr>
                        <td align="right">物资名称:</td>
                        <td>
                            <input id="type_dtl_name" name="type_dtl_name" class="easyui-textbox" data-options="required:true"/>
                            <input id="type_dtl_id" name="type_dtl_id" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">物资编号:</td>
                        <td><input id="type_dtl_no" name="type_dtl_no" class="easyui-textbox" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td align="right">型号:</td>
                        <td><input id="model" name="model" class="easyui-textbox" /></td>
                    </tr>
                    <tr>
                        <td align="right">规格:</td>
                        <td><input id="sizes" name="sizes" class="easyui-textbox" /></td>
                    </tr>
                    <tr>
                        <td align="right">备注:</td>
                        <td><input id="remark_dtl" name="remark_dtl" class="easyui-textbox" /></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>