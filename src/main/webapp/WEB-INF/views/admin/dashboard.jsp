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
    <title>后台管理界面平台</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

    <script src="${ctxstatic}/plugin/Highcharts-5.0.0/js/highcharts.js"></script>
    <script src="${ctxstatic}/plugin/justgage-1.2.2/raphael-2.1.4.min.js"></script>
    <script src="${ctxstatic}/plugin/justgage-1.2.2/justgage.js"></script>

</head>

<body style="padding: 20px">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" style="height:40%">
        <div class="theme-user-info-panel">
            <div class="left">
                <img src="${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png" width="86" height="86" onerror="javascript:this.src='${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png'">
            </div>
            <div class="right">

                <style>
                    .gauge {
                        width: 130px;
                        height: 130px;
                    }
                </style>
                <script>
                    $(function () {

                        var dflt = {
                            min: 0,
                            max: 2800,
                            donut: true,
                            gaugeWidthScale: 0.6,
                            counter: true,
                            hideInnerShadow: true
                        };
                        var gg1 = new JustGage({
                            id: 'gg1',
                            value: 125,
                            defaults: dflt
                        });

                        var gg2 = new JustGage({
                            id: 'gg2',
                            defaults: dflt
                        });
                        var gg3 = new JustGage({
                            id: 'gg3',
                            defaults: dflt
                        });
                        var gg4 = new JustGage({
                            id: 'gg4',
                            defaults: dflt
                        });

                    });
                </script>
                <%--<ul>
                    <li>
                        <div id="gg1" class="gauge" data-value="250"></div>
                        <span>我的数据</span></li>
                    <li>
                        <div id="gg2" class="gauge" data-value="2025"></div>
                        <span>我的数据</span></li>
                    <li>
                        <div id="gg3" class="gauge" data-value="115"></div>
                        <span>我的数据</span></li>
                    <li>
                        <div id="gg4" class="gauge" data-value="687"></div>
                        <span>我的数据</span></li>
                </ul>--%>
            </div>
            <div class="center">
                <h1>${user.username}<%--匿名--%><span class="color-warning badge">未认证</span></h1>
                <h2>管理员</h2>
                <dl>
                    <dt>${user.email}<%--examples@insdep.com--%></dt>
                    <dd>${user.mobile}<%--13000000000--%></dd>
                </dl>
            </div>

        </div>
    </div>
    <div data-options="region:'center',border:false">

        <div id="user-info-more" class="easyui-tabs theme-tab-blue-line theme-tab-body-unborder"
             data-options="tools:'#tab-tools',fit:true">


            <div title="帮助" data-options="closable:true" style="padding:10px">
                第一版完成,接下来交给你们了
            </div>
        </div>


    </div>
    <div id="tab-tools">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-set'"></a>
    </div>
</div>
</div>
</body>
</html>