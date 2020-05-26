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
                <ul>
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
                </ul>
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

            <div title="统计图" id="charts-layout">
                <!--统计开始-->

                <div id="charts" style="height:400px;"></div>
                <script type="text/javascript">
                    $(function () {
                        $('#charts').highcharts({
                            chart: {
                                type: 'spline',
                                events: {
                                    load: function () {

                                    }
                                }
                            },
                            title: {
                                text: 'Wind speed during two days'
                            },
                            subtitle: {
                                text: 'October 6th and 7th 2009 at two locations in Vik i Sogn, Norway'
                            },
                            xAxis: {
                                type: 'datetime'
                            },
                            yAxis: {
                                title: {
                                    text: 'Wind speed (m/s)'
                                },
                                min: 0,
                                minorGridLineWidth: 0,
                                gridLineWidth: 0,
                                alternateGridColor: null,
                                plotBands: [{ // Light air
                                    from: 0.3,
                                    to: 1.5,
                                    color: 'rgba(68, 170, 213, 0.1)',
                                    label: {
                                        text: 'Light air',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // Light breeze
                                    from: 1.5,
                                    to: 3.3,
                                    color: 'rgba(0, 0, 0, 0)',
                                    label: {
                                        text: 'Light breeze',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // Gentle breeze
                                    from: 3.3,
                                    to: 5.5,
                                    color: 'rgba(68, 170, 213, 0.1)',
                                    label: {
                                        text: 'Gentle breeze',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // Moderate breeze
                                    from: 5.5,
                                    to: 8,
                                    color: 'rgba(0, 0, 0, 0)',
                                    label: {
                                        text: 'Moderate breeze',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // Fresh breeze
                                    from: 8,
                                    to: 11,
                                    color: 'rgba(68, 170, 213, 0.1)',
                                    label: {
                                        text: 'Fresh breeze',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // Strong breeze
                                    from: 11,
                                    to: 14,
                                    color: 'rgba(0, 0, 0, 0)',
                                    label: {
                                        text: 'Strong breeze',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }, { // High wind
                                    from: 14,
                                    to: 15,
                                    color: 'rgba(68, 170, 213, 0.1)',
                                    label: {
                                        text: 'High wind',
                                        style: {
                                            color: '#606060'
                                        }
                                    }
                                }]
                            },
                            tooltip: {
                                valueSuffix: ' m/s'
                            },
                            plotOptions: {
                                spline: {
                                    lineWidth: 4,
                                    states: {
                                        hover: {
                                            lineWidth: 5
                                        }
                                    },
                                    marker: {
                                        enabled: false
                                    },
                                    pointInterval: 3600000, // one hour
                                    pointStart: Date.UTC(2009, 9, 6, 0, 0, 0)
                                }
                            },
                            series: [{
                                name: 'Hestavollane',
                                data: [4.3, 5.1, 4.3, 5.2, 5.4, 4.7, 3.5, 4.1, 5.6, 7.4, 6.9, 7.1,
                                    7.9, 7.9, 7.5, 6.7, 7.7, 7.7, 7.4, 7.0, 7.1, 5.8, 5.9, 7.4,
                                    8.2, 8.5, 9.4, 8.1, 10.9, 10.4, 10.9, 12.4, 12.1, 9.5, 7.5,
                                    7.1, 7.5, 8.1, 6.8, 3.4, 2.1, 1.9, 2.8, 2.9, 1.3, 4.4, 4.2,
                                    3.0, 3.0]

                            }, {
                                name: 'Voll',
                                data: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.3, 0.0,
                                    0.0, 0.4, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                    0.0, 0.6, 1.2, 1.7, 0.7, 2.9, 4.1, 2.6, 3.7, 3.9, 1.7, 2.3,
                                    3.0, 3.3, 4.8, 5.0, 4.8, 5.0, 3.2, 2.0, 0.9, 0.4, 0.3, 0.5, 0.4]

                            }]
                            ,
                            navigation: {
                                menuItemStyle: {
                                    fontSize: '10px'
                                }
                            }
                        });


                        /*$('#user-info-more').tabs({
                         onSelect: function(){
                         //重置宽度
                         var chart = $('#charts').highcharts();
                         chart.reflow();
                         }
                         });
                         */


                    });

                    document.addEventListener("DOMContentLoaded", function () {
                        //完成所有页面处理后
                        //重置宽度
                        var chart = $('#charts').highcharts();
                        chart.reflow();
                    });
                </script>

                <!--统计结束-->
            </div>
            <div title="帮助" data-options="closable:true" style="padding:10px">
                This is the help content.
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