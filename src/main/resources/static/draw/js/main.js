var isWhole = false;

$(function () {

    $("#check-tag").live("click", function () {
        if (isWhole)
            alert("The picture has been tagged");
        else
            draw_tag(this);
    });


});

function chooseType(taskType) {
    // 绑定绘画板工具
    if (taskType == "Part")
        draw_graph('square', this);
    else if (taskType == "Whole")
        isWhole = true;
    else if (taskType == "Split")
        draw_graph('pencil', this);
    else
        alert("No this type");


};