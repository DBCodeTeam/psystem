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

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'west',border:false" style="height:100%;width: 35%;">
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
        <%--<table id="rdg" style="width:100%;height:100%;">
        </table>--%>
    </div>
    <div data-options="region:'center',border:false" style="height:100%">
        <div id="tb_detail" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-detail-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-detail-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
        <table id="pdg" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlg"></div>
</body>
</html>