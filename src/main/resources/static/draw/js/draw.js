function getRandomColor() {
    return '#' +
        (function (color) {
            return (color += '0123456789abcdef'[Math.floor(Math.random() * 16)])
            && (color.length == 6) ? color : arguments.callee(color);
        })('');
}

/*
 * label：标注（框选）
 *  @params x pos.x,
            y pos.y,
            w width,
            h height
            el DOM标注框,
            color 标注框颜色,
            tag 标签内容
            isExist 是否绘制,
 */

var $imgAreaEl = $('#img-area-self'),//图片放置元素
    $labelTag = $('#label-tag'), // 图片标签
    isMouseDown = false,//鼠标点击动作
    mouseType = 0,//鼠标操作内容，1：创建，2：移动，3：缩放
    drawType = "",
    mouse = {},//mouse属性(x,y)
    dotList = [],
    selected = 0,//操作标注索引
    pencilSelected = 0,
    tempSelected = 0,//暂存选择项
    pencilTmp = 0,
    operateData = {},//标注操作参数(x,y,w,h)，animate方法使用(渲染)
    pencilData = {},
    labelWhole = "", //整体的标注
    labelList = [],//标注列表
    pencilList = [],
    labelMove = {},//记录标注初始坐标等相关参数(x,y,resize)
    labelTotal = 0,//标注总数
    pencilTotal = 0,
    tagData = [],//标签数据name,color
    winScale = $imgAreaEl.width() / $imgAreaEl.height(),//图片放置区域比例
    labelIdFinish = [],
    idOfImg = 0;


// 全局提示框
var _placeHolderEl = {
    el: $('.placeholder'),
    setText: function (text) {
        this.el.html(text).show();
    },
    setHide: function (text, time) {
        var that = this;
        this.setText(text);
        setTimeout(function () {
            that.hide()
        }, time || 1000)
    },
    hide: function () {
        this.el.hide();
    }
};
getImage(true);

// 获取图片
function getImage(back) {
    // _placeHolderEl.setText('新图片加载中......');
    if (back) {
        idOfImg += 1;
        if (idOfImg > 22)
            idOfImg -= 22;
    }
    else {
        idOfImg -= 1;
        if (idOfImg < 1)
            idOfImg += 22;
    }

    var pic = 'images/images' + idOfImg.toString() + '.png';
    adaptionImg(pic, $('#img-self'));
}

// 图片自适应
function adaptionImg(url, el, callback) {
    //url += '?x-oss-process=image/resize,w_500';
    var img = new Image();
    img.onload = function () {
        var imgScale = img.naturalWidth / img.naturalHeight;
        _placeHolderEl.hide();
        if (imgScale > winScale) {
            el.attr({
                'src': url,
                'data-scale': imgScale,
                'width': '100%',
                'height': 'auto'
            });
        } else {
            el.attr({
                'src': url,
                'data-scale': imgScale,
                'width': 'auto',
                'height': '100%'
            });
        }
        callback && callback();
    };
    img.onerror = function () {
        _placeHolderEl.setHide('图片获取失败...');
    };
    img.src = url;
}

// // 绑定鼠标事件
// bindNewLabel();

// 初始化移动
function initOperate() {
    operateData.y = labelList[selected].y;
    operateData.x = labelList[selected].x;
    operateData.w = labelList[selected].w;
    operateData.h = labelList[selected].h;
}

// 清除
function clearLabel() {
    labelMove = {};
}

function clearAll() {
    clearLabel();
    selected = 0;
    labelList = [];
    labelTotal = 0;
    $('.label-area').remove();
    $('.label-area-right').remove();
    // $('#img-self').attr('src', '')
}

//画图形
var draw_graph = function (graphType, obj) {
    drawType = graphType;

    bindNewLabel();
//$(canvas_bak).unbind();
//$(canvas_bak).bind('mousedown', mousedown);
//$(canvas_bak).bind('mousemove', mousemove);
//$(canvas_bak).bind('mouseup', mouseup);
//$(canvas_bak).bind('mouseout', mouseout);
}

//创建label
function bindNewLabel() {
    // move绑定到父元素
    $('#img-area-self').on('mousedown', newLabelMouseDown)
        .on('mousedown', '.ui-resizable-handle', scaleLabelMouseDown)
        .on('mousedown', '.label-area', moveLabelMouseDown)
        .on('mousemove', move)
        .on('mouseup', up);
}

// 解绑鼠标事件
function unbindEvent() {
    $('#img-area-self').unbind('mousedown', newLabelMouseDown)
        .unbind('mousemove', move)
        .unbind('mouseup', up);
}

function newLabelMouseDown(event) {

    mouse = captureMouse(event);
    isMouseDown = true;
    mouseType = 1;


    // 为每个标注分配颜色
    var color = getRandomColor();
    labelMove = {
        x: mouse.x,
        y: mouse.y
    };
    if (drawType == "square") {

        tempSelected = selected;
        selected = labelTotal;

        var newLabel = {
            x: mouse.x,
            y: mouse.y,
            el: $($('#tpl-area').html()),
            isExist: false,
            color: color,
            w: 0,
            h: 0
        };

        newLabel.el.attr('id', 'label_' + selected);
        newLabel.el.find('.ui-resizable-handle').css('background', color);
        labelList.push(newLabel);
        labelTotal++;
    }
    else {

        pencilTmp = pencilSelected;
        pencilSelected = pencilTotal;
        var pencilLabel = {
            x: mouse.x,
            y: mouse.y,
            el: $($('#tpl-area').html()),
            isExist: false,
            color: color,
            dotList: []
        };

        dotList.push(labelMove);
        pencilLabel.el.attr('id', 'pencil_' + selected);
        pencilLabel.el.find('.ui-pencil-handle').css('background', color);
        pencilList.push(pencilLabel);
        pencilTotal++;
    }
    // 记录当前鼠标pos

    animate();

    console.log('创建标注开始');


    return false;
}

function newLabelMouseMove() {


    var difference_x = mouse.x - labelMove.x,
        difference_y = mouse.y - labelMove.y;
    if (difference_x >= 0 && difference_y >= 0) {
        // 左上角向右下角
        operateData = {
            x: labelMove.x,
            y: labelMove.y,
            w: difference_x,
            h: difference_y
        };
    } else if (difference_x >= 0 && difference_y < 0) {
        // 左下角向右上角
        operateData = {
            x: labelMove.x,
            y: labelMove.y + difference_y,
            w: difference_x,
            h: -1 * difference_y
        };
    } else if (difference_x < 0 && difference_y >= 0) {
        // 右上角向左下角
        operateData = {
            x: labelMove.x + difference_x,
            y: labelMove.y,
            w: -1 * difference_x,
            h: difference_y
        };
    } else {
        // 右下角向左上角
        operateData = {
            x: labelMove.x + difference_x,
            y: labelMove.y + difference_y,
            w: -1 * difference_x,
            h: -1 * difference_y
        };
    }

    if (!labelList[selected].isExist) {
        $('#img-area-self').append(labelList[selected].el);
        labelList[selected].isExist = true;
    }


}

function newLabelMouseup() {
    if (!labelList[selected].isExist) {
        labelList.pop();
        labelTotal--;
        selected = tempSelected;
        console.log('创建标注失败or撤销');
        return;
    }

    // 绑定标注框鼠标事件
    console.log('创建标注' + (labelTotal - 1));
}

// 移动area
//$(document).bind('contextmenu', function (e) {
//    return false; //屏蔽菜单
//});

function moveLabelMouseDown(event) {
    if (event.which === 3) {//右击删除
        if (confirm('删除该标注?')) {
            var id = $(this).attr('id').replace('label_', '');
            $(this).remove();
            labelList[id].isExist = false;
        }
        return false;
    } else {
        mouse = captureMouse(event);
        isMouseDown = true;
        mouseType = 2;
        selected = $(this).attr('id').replace('label_', '');
        $labelTag.val(labelList[selected].tag);
        labelMove = {
            x: mouse.x,
            y: mouse.y
        };
        initOperate();
        console.log('移动标注_' + selected);
        animate();
    }
    return false;
}

function moveLabelMouseMove() {
    operateData.y = labelList[selected].y + mouse.y - labelMove.y;
    operateData.x = labelList[selected].x + mouse.x - labelMove.x;
}

function moveLabelMouseup() {
    console.log('移动结束并选中标注_' + selected);
}

// 缩放area
function scaleLabelMouseDown(event) {
    mouse = captureMouse(event);
    isMouseDown = true;
    mouseType = 3;
    selected = $(this).parents('.label-area').attr('id').replace('label_', '');
    labelMove = {
        resize: $(this).data('resize'),
        x: mouse.x,
        y: mouse.y
    };
    initOperate();
    console.log('缩放start' + selected);
    animate();
    return false;
}

function scaleLabelMouseMove() {
    switch (labelMove.resize) {
        case 't':
            operateData.y = labelList[selected].y + mouse.y - labelMove.y;
            operateData.h = labelList[selected].h - (mouse.y - labelMove.y);
            break;
        case 'l':
            operateData.x = labelList[selected].x + mouse.x - labelMove.x;
            operateData.w = labelList[selected].w - (mouse.x - labelMove.x);
            break;
        case 'r':
            operateData.w = labelList[selected].w + mouse.x - labelMove.x;
            break;
        case 'b':
            operateData.h = labelList[selected].h + mouse.y - labelMove.y;
            break;
        case 'tl':
            operateData.y = labelList[selected].y + mouse.y - labelMove.y;
            operateData.x = labelList[selected].x + mouse.x - labelMove.x;
            operateData.w = labelList[selected].w - (mouse.x - labelMove.x);
            operateData.h = labelList[selected].h - (mouse.y - labelMove.y);
            break;
        case 'tr':
            operateData.y = labelList[selected].y + mouse.y - labelMove.y;
            operateData.w = labelList[selected].w + mouse.x - labelMove.x;
            operateData.h = labelList[selected].h - (mouse.y - labelMove.y);
            break;
        case 'bl':
            operateData.x = labelList[selected].x + mouse.x - labelMove.x;
            operateData.w = labelList[selected].w - (mouse.x - labelMove.x);
            operateData.h = labelList[selected].h + mouse.y - labelMove.y;
            break;
        case 'br':
            operateData.h = labelList[selected].h + mouse.y - labelMove.y;
            operateData.w = labelList[selected].w + mouse.x - labelMove.x;
            break;
    }
}

function scaleLabelMouseup() {
    console.log('缩放结束并选中标注_' + selected);
}


// 公共方法，根据MouseDown事件发生Node区分操作
function move(event) {
    if (!isMouseDown) {
        return false;
    }

    mouse = captureMouse(event);

    if (drawType == "square") {
        switch (mouseType) {
            case 1:
                newLabelMouseMove();
                break;
            case 2:
                moveLabelMouseMove();
                break;
            case 3:
                scaleLabelMouseMove();
                break;
        }
    }
    //铅笔
    else {

        var dot = {
            x: mouse.x,
            y: mouse.y
        };
        dotList.push(dot);

        // if (!pencilList[pencilSelected].isExist) {
        //     $('#img-area-self').append(pencilList[pencilSelected].el);
        //     pencilList[pencilSelected].isExist = true;
        // }
        animate();

    }
    return false;
}

function up() {
    if (drawType == "square") {
        if (isMouseDown) {
            switch (mouseType) {
                case 1:
                    newLabelMouseup();
                    break;
                case 2:
                    moveLabelMouseup();
                    break;
                case 3:
                    scaleLabelMouseup();
                    break;
            }
            if (labelList[selected]) {//鼠标点击引起的错误
                labelList[selected].y = operateData.y;
                labelList[selected].x = operateData.x;
                labelList[selected].w = _max(operateData.w);
                labelList[selected].h = _max(operateData.h);
                clearLabel();
                //操作label添加selected
                $('.label-area').removeClass('selected');
                $('#label_' + selected).addClass('selected');
            }
        }
        // window.cancelAnimationFrame(animate);
    }
    else {
        if (isMouseDown) {
            if (pencilList[pencilSelected]) {//鼠标点击引起的错误
                pencilList[pencilSelected].y = pencilData.y;
                pencilList[pencilSelected].x = pencilData.x;
                clearLabel();
                //操作label添加selected
                $('.label-area').removeClass('selected');
                $('#pencil_' + selected).addClass('selected');
            }
        }
    }
    isMouseDown = false;
    mouseType = 0;
    return false;
}

// 删除标注
$('body').on('click', '.remove-label', function () {
    if (confirm('删除该标注?')) {
        var id = $(this).parent().attr('id').replace('label_', '');
        $(this).parent().remove();
        labelList[id].isExist = false;
    }
    return false;
});

// 绘制标签
function drawTag(color, tag) {
    return '<li class="tag-item" style="background:' + color + '">' + tag + '</li>';
}

// 处理宽高比例
function dealWH(type, num) {
    var t = $('#img-self'),
        w = t.width(),
        h = t.height();
    if (type == 'w') {
        return (num / w).toFixed(4);
    } else if (type == 'h') {
        return (num / h).toFixed(4);
    }
}

// 帧率显示
var stats = new Stats();
stats.showPanel(0); // 0: fps, 1: ms, 2: mb, 3+: custom
stats.dom.style.cssText = 'position:fixed;top:0;right:0;cursor:pointer;opacity:0.9;z-index:10000';
document.getElementById("img-area-self").appendChild(stats.dom);

// 按浏览器刷新率渲染标注
function animate() {

    stats.begin();
    stats.end();
    isMouseDown && window.requestAnimationFrame(animate);
    if (drawType == "square") {
        if (labelTotal > 0 && labelList[selected] && labelList[selected].isExist) {
            operateData.w = _max(operateData.w);
            operateData.h = _max(operateData.h);
            $('#label_' + selected).css({
                top: operateData.y,
                left: operateData.x,
                width: operateData.w,
                height: operateData.h
            })
        }
    } else {
        if (pencilTotal > 0 && pencilList[pencilSelected] && pencilList[pencilSelected].isExist) {

            $('#pencil_' + pencilSelected).css({
                top: pencilData.y,
                left: pencilData.x,

            })
        }
    }
}

// 根据数据还原框选
function restore(data) {
    restoreLeft(data);
    restoreRight(data);
    // 差异信息显示
    $('.label-diff').html(diffText[data.diffType] + '<br/>' + data.diffDesc);
}

function restoreLeft(data, el) {
    var t = el ? $(el) : $('#img-self');
    adaptionImg(data.pic, t, function () {
        var w = t.width(),
            h = t.height();
        for (var i in data.label) {
            var item = data.label[i];
            selected = i;
            var _tag_name = item.tag,
                _tag_2_name = item.property[0],
                tagObj = returnTagIndex(_tag_name, _tag_2_name),
                newLabel = {
                    x: item.pos[1] * w,
                    y: item.pos[0] * h,
                    el: $($('#tpl-area').html()),
                    isExist: true,
                    w: item.pos[2] * w,
                    h: item.pos[3] * h,
                    tag: tagObj.tag_index,
                    tag_2: tagObj.tag_2_index
                },
                color = tagData[tagObj.tag_index].color;
            newLabel.el.attr('id', 'label_' + selected);
            newLabel.el.find('.tag-list').eq(0).append(drawTag(color, _tag_name))
                .append(drawTag_2(_tag_2_name))
                .siblings(".ui-resizable-handle")
                .css('background', color);
            labelList.push(newLabel);
            operateData = {
                x: newLabel.x,
                y: newLabel.y,
                w: newLabel.w,
                h: newLabel.h,
            };
            labelTotal++;
            t.after(newLabel.el);
            //强制模拟鼠标动作，渲染一把
            isMouseDown = true;
            animate();
            isMouseDown = false;
        }
        // 模拟选中最后一个标签
        isMouseDown = true;
        mouseType = 1;
        up();
    });
}

function restoreRight(data, el) {
    var t = el ? $(el) : $('#img-other');
    adaptionImg(data.pic, t, function () {
        var w = t.width(),
            h = t.height();
        for (var i in data.diffLabel) {
            var item = data.diffLabel[i];
            var $tpl = $($('#tpl-other-person').html()),
                _tag_name = item.tag,
                _tag_2_name = item.property[0],
                tagObj = returnTagIndex(_tag_name, _tag_2_name),
                color = tagData[tagObj.tag_index].color;
            $tpl.find('.tag-list').eq(0).append(drawTag(color, _tag_name)).append(drawTag_2(_tag_2_name));
            $tpl.css({
                borderColor: color,
                top: item.pos[0] * h + 'px',
                left: item.pos[1] * w + 'px',
                width: item.pos[2] * w + 'px',
                height: item.pos[3] * h + 'px'
            });
            t.after($tpl);
        }
    });
}

//取最小值
function _max(num) {
    return Math.max(20, num);
}

var draw_tag = function () {
    var tag = $labelTag.val();
    labelList[selected].tag = tag;
    $('#label_' + selected).find('.tag-list').eq(0).html(drawTag(labelList[selected].color, tag));

};


function previousPic() {
    //添加更换背景图片的方法-前翻
    clearAll();
    getImage(false);
}

function nextPic() {
    //添加更换背景图片的方法-后翻
    clearAll();
    getImage(true);

}

//保存框选
function saveSquare() {
    $.ajax({
        type: "POST",
        url: "workplace/whole/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            labelList: labelList
        }
    })
}

//保存区域
function savePencil() {
    $.ajax({
        type: "POST",
        url: "workplace/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            pencilList: pencilList
        }
    })
}

//保存整体
function saveWhole() {

    $.ajax({
        type: "POST",
        url: "workplace/whole/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            labelWhole: labelWhole
        }
    })

}

//获取框选
function getSquare() {
    $.ajax({
        type: "GET",
        url: "workplace/whole/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            labelList: labelList
        }
    })
}

//保存区域
function getPencil() {
    $.ajax({
        type: "POST",
        url: "workplace/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            pencilList: pencilList
        }
    })
}

//保存整体
function getWhole() {

    $.ajax({
        type: "GET",
        url: "workplace/whole/save",
        dataType: "json",
        data: {
            imageID: idOfImg,
            labelWhole: labelWhole
        }
    })

}


//function saveTagForWhole(imageID, description) {
//    $.ajax({
//        type: "POST",
//        url: "/workplace/whole/save",
//        dataType: "json",
//        data: {
//            imageID: imageID,
//            description: description
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success saved for whole picture !");
//            }
//            else
//                alert("Error for whole picture !");
//        },
//
//        error: function () {
//            alert("Network warning for saving whole picture !");
//        }
//
//    });
//}
//
////删除整张照片的标签
//function deleteTagForWhole(imageID) {
//
//    $.ajax({
//        type: "DELETE",
//        url: "/workplace/whole/delete",
//        dataType: "json",
//        data: {
//            imageID: imageID
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success deleted for whole picture !");
//            }
//            else
//                alert("Error deleted for whole picture !");
//        },
//
//        error: function () {
//            alert("Network warning for deleting whole picture !");
//        }
//
//    });
//}
//
////更改整张照片的标签
//function updateTagForWhole(imageID, description) {
//    $.ajax({
//        type: "POST",
//        url: "/workplace/whole/update",
//        dataType: "json",
//        data: {
//            imageID: imageID,
//            description: description
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success updated for whole picture !");
//            }
//            else
//                alert("Error updated for whole picture !");
//        },
//
//        error: function () {
//            alert("Network warning for updating whole picture !");
//        }
//
//    });
//}
//
//
////保存部分照片的标签
//function saveTagForPart(imageID, x1, x2, y1, y2, description) {
//    $.ajax({
//        type: "POST",
//        url: "/workplace/part/save",
//        dataType: "json",
//        data: {
//            imageID: imageID,
//            x1: x1,
//            x2: x2,
//            y1: y1,
//            y2: y2,
//            description: description
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success saved for part picture !");
//            }
//            else
//                alert("Error savedd for part picture !");
//        },
//
//        error: function () {
//            alert("Network warning for saving part picture !");
//        }
//
//    });
//}
//
////删除部分照片的标签
//function deleteTagForPart(imageID) {
//    $.ajax({
//        type: "DELETE",
//        url: "/workplace/part/delete",
//        dataType: "json",
//        data: {
//            imageID: imageID
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success deleted for part picture !");
//            }
//            else
//                alert("Error deleted for part picture !");
//        },
//
//        error: function () {
//            alert("Network warning for deleting part picture !");
//        }
//
//    });
//}
//
////更新部分照片的标签
//function updateTagForPart(imageID, x1, x2, y1, y2, description) {
//    $.ajax({
//        type: "POST",
//        url: "/workplace/part/update",
//        dataType: "json",
//        data: {
//            imageID: imageID,
//            x1: x1,
//            x2: x2,
//            y1: y1,
//            y2: y2,
//            description: description
//        },
//
//        success: function (data) {
//            if (data.success) {
//                alert("Success updated for part picture !");
//            }
//            else
//                alert("Error updated for part picture !");
//        },
//
//        error: function () {
//            alert("Network warning for updating part picture !");
//        }
//
//    });
//}