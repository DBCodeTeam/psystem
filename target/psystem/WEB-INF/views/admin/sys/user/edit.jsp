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
    <title>用户管理</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:100%;">
        <form id="form">
            <table style="margin: 0 auto; padding: 10px;">
                <tr>
                    <td>用户名:</td>
                    <td><input name="username" class="easyui-textbox" data-options="required:true,readonly:true"/></td>
                </tr>
                <tr>
                    <td>姓名:</td>
                    <td><input name="name" class="easyui-textbox"/></td>
                </tr>
                <tr>
                    <td>手机号:</td>
                    <td><input name="mobile" class="easyui-textbox" data-options="validType:['mobile']"/></td>
                </tr>
                <tr>
                    <td>邮箱:</td>
                    <td><input name="email" class="easyui-textbox" data-options="validType:['email']"/></td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td>
                        <select name="gender" class="easyui-combobox" panelHeight="auto">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                        <input name="id" type="hidden" value="${id}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<style scoped>
    #form tr {
        line-height: 35px;
    }

    #form tr input, select {
        width: 220px;
    }
</style>
<script>
    $(function () {
        U.get({
            url: "${ctx}/sys/user/" + ${id},
            loading: true,
            success: function (data, status) {
                if (data.code == 200) {
                    $('#form').form('load', data.body);
                } else if (data.code == 400) {//参数验证失败
                    U.msg('参数验证失败');
                } else if (data.code == 404) {
                    U.msg('未找到该用户');
                } else {
                    U.msg('服务器异常');
                }
            }
        });
    });
</script>
</body>
</html>