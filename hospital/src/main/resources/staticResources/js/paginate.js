function paginate(pageNo, pageTotal, firstPage, prevPage, nextPage, lastPage, callback) {
    $("#" + firstPage).click(function () {
        $("#" + pageNo).val(1);
        callback();
    });
    $("#" + prevPage).click(function () {
        if (parseInt($("#" + pageNo).val()) > 1) {
            $("#" + pageNo).val(parseInt($("#" + pageNo).val()) - 1);
        }
        callback();
    });
    $("#" + nextPage).click(function () {
        var No = parseInt($("#" + pageNo).val());
        var Total = parseInt($("#" + pageTotal).text());
        if (No < Total) {
            $("#" + pageNo).val(No + 1);
        }
        callback();
    });
    $("#" + lastPage).click(function () {
        $("#" + pageNo).val($("#" + pageTotal).text());
        callback();
    });
    $("#" + pageNo).blur(function () {
        if (parseInt($("#" + pageNo).val()) > 0 && parseInt($("#" + pageNo).val()) <= parseInt($("#" + pageTotal).text())) {
            callback();
        } else {
            alert("页码错误！");
            $("#" + pageNo).val(1);
            callback();
        }

    })
}