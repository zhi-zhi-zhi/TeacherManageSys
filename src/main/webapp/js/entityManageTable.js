// 初始化table内容
function initTable(dynamicColumns) {
    let table = $('#table');
    table.bootstrapTable({
        // url: "/json/falseData.json",
        // method: "get",
        url: "/entity",
        method: "get",
        dataType: "json",
        ajaxOptions: ajaxOptions,
        cache: false,
        search: true,
        sortable: true,
        sortStable: true,  // 相同的元素排序后前后位置不变
        toggle: "table",
        toolbar: "#toolbar",
        showFooter: false,
        pagination: true,
        paginationLoop: true,
        pageNumber: 1,
        pageSize: 5,
        pageList: [1, 2, 5],
        queryParamsType: "limit",
        queryParams: queryParams,
        idField: "id",
        clickToSelect: true,
        showRefresh: true,
        showToggle: true,
        detailView: true,
        detailFormatter: detailFormatter,
        responseHandler: responseHandler,

        sidePagination: "server", // 服务器端分页，暂时不搞

        columns: dynamicColumns
    });
    console.log("~~~~~~~~~~~~~~")
}

const ajaxOptions = {
    success: function (res) {
        console.log("ajax success");
        console.log(res);
    },
    error: function (res) {
        console.log("ajax error");
        console.log(res)
    },
    complete: function (XMLHttpRequest, textStatus) {
        console.log("ajax complete");
        if (XMLHttpRequest.getResponseHeader("REDIRECT") !== null) {
            window.location.href = XMLHttpRequest.getResponseHeader("urlLocation");
        }
    }
}

function queryParams(params) {
    console.log(params);
    return {
        // pageSize
        limit: params.limit,
        // 初始偏移量
        offset: params.offset,
        sortField: params.sort,
        sortOrder: params.order,
        entity: entity
    }
}

// 对返回的数据加上index
function responseHandler(res) {
    console.log("");

    console.log(res);
    res.rows.forEach(function (row, i) {
        row.index = i + 1;
    });


    return res;
}

// 详细信息格式化
function detailFormatter(index, res) {
    const html = [];
    $.each(res, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>')
    });
    return html.join('')
}

// 监控选中的行数----->update、delete按钮是否可点击
$("#table").on('check.bs.table uncheck.bs.table ' +
    'check-all.bs.table uncheck-all.bs.table',
    function () {
        let $table = $("#table");
        console.log("check.bs.table");

        $("#remove").prop('disabled', !$table.bootstrapTable('getSelections').length);
        $("#update").prop('disabled',
            $table.bootstrapTable('getSelections').length !== 1);
    });