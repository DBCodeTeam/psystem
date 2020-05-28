var resourceDataGrid, permissionDataGrid, dialog;
$(function () {
    /*resourceDataGrid = $('#rdg').treegrid({
        title: '物资分类',
        method: "get",
        url: '${ctx}/sys/resource/menus',
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        treeField: 'name',
        iconCls: 'icon',
        animate: true,
        rownumbers: true,
        striped: true,
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        toolbar: '#rtb',
        columns: [[
            {field: 'id', title: 'id', hidden: true, width: 100},
            {field: 'name', title: '物资类别', width: 100},
            {field: 'mark', title: '备注', width: 100}
        ]],
        onLoadSuccess: function (row, data) {
            if (data != null && data.rows != null && data.rows.length > 0) {
                $('#rdg').treegrid('select', data.rows[0].id);
            }
        },
        onSelect: function (row) {
            queryPermissions();

            var gridPanel = $(permissionDataGrid).datagrid("getPanel");//先获取panel对象
            gridPanel.panel('setTitle', row.name + '-明细');//再通过panel对象去修改title
        },
        queryParams: {
            type: '1'
        }
    });

    permissionDataGrid = $('#pdg').datagrid({
        title: '权限',
        method: "get",
        url: '${ctx}/sys/resource/permissions',
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
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'id', hidden: true},
            {field: 'name', title: '权限名称', sortable: false, width: 20},
            {field: 'code', title: '权限编码', sortable: false, width: 20},
            {field: 'description', title: '描述', sortable: false, width: 25},
            {
                field: 'operate', title: '操作', width: 35,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                    if (row.isFixed == 1) {//固定的
                        return e;
                    } else {
                        return e + '  ' + d;
                    }
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.button-delete').linkbutton({});
            $('.button-edit').linkbutton({});

            /!*if (data != null && data.rows != null && data.rows.length > 0) {
                $('#pdg').datagrid('checkRow', 0);
            }*!/
        },
        onSelect: function (index, row) {
            if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                $('#btn-delete').hide();
            } else {
//                    $('#btn-edit').show();
                $('#btn-delete').show();
            }
        },
        queryParams: {
            type: '3',
            parentId: getSelectedMenu()
        }
    });*/
});

function getSelectedMenu(){
    var row = resourceDataGrid.datagrid('getSelected');
    if (row == null) {
        return null;
    } else {
        return row.id;
    }
}

/**
 * 获取权限
 */
function queryPermissions() {
    $(permissionDataGrid).datagrid('load', {
        type: '2',
        parentId: getSelectedMenu()
    });
}

/**
 * 添加权限
 */
function add() {
    var menuRow = resourceDataGrid.datagrid('getSelected');
    if (menuRow == null) {
        U.msg('请先选择菜单');
        return
    }

    dialog = $("#dlg").dialog({
        title: '添加权限',
        width: 360,
        height: 280,
        href: '${ctx}/admin/permission/add/'+menuRow.id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "${ctx}/sys/resource/add",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog.dialog('close');
                                queryPermissions();
                            } else if (data.code == 400) {
                                U.msg('参数验证失败');
                            } else if (data.code == 409) {
                                U.msg('权限编码已存在');
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
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 编辑权限
 */
function edit(id) {
    if (id == null) {
        var row = permissionDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择权限');
            return
        } else {
            id = row.id;
        }
    }

    dialog = $("#dlg").dialog({
        title: '编辑权限',
        width: 360,
        height: 280,
        href: '${ctx}/admin/permission/edit/' + id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "${ctx}/sys/resource/update",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                queryPermissions();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该权限');
                            } else if (data.code == 409) {
                                U.msg('权限编码已存在');
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
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 删除权限
 */
function remove(id) {
    if (id == null) {
        var row = permissionDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择权限');
            return
        } else {
            id = row.id;
        }
    }

    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            U.post({
                url: "${ctx}/sys/resource/" + id + "/delete",
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryPermissions();
                    } else if (data.code == 400) {//参数验证失败
                        U.msg('参数验证失败');
                    } else if (data.code == 404) {
                        U.msg('未找到该权限');
                    } else if (data.code == 424) {
                        U.msg('该权限已被使用，无法删除');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}