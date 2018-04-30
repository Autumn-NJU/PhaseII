var str = window.location.href;//取地址参数部分
taskID = str.substr(str.indexOf('=') + 1);
document.getElementById('taskId').innerHTML = taskID;
var username = getCookieValue("username");

var pointer = -1,
    imgID,
    imgList;

$.ajax({
    type: "GET",
    url: "/task/getID",
    dataType: "json",
    data: {
        id: parseInt(taskID),
    },
    success: function (data) {
        var taskType = data.data.tagType;
        chooseType(taskType);
    },
    error: function (data) {
        alert(data.message);
    }
})

$.ajax({
    type: "GET",
    url: "/task/listPicturePath",
    dataType: "json",
    data: {
        taskId: parseInt(taskID),
    },
    success: function (data) {
        if (data.success) {
            imgList = data.data;
            getImage(true);
        } else
            alert(data.message);
    },
    error: function (data) {
        alert(data.message);
    }

})

// 获取图片
function getImage(back) {

    if (back) {
        pointer += 1;
        if (pointer >= imgList.length) {
            alert("Misson is finished!");
            window.location.href = "../gentelella/worker/projects-worker.html";
        }
        else {
            var pic = "../task/files/" + taskID + "/" + imgList[pointer].substr(37);
            imgID = imgList[pointer];
            if (hasTag()) {
                if (isWhole) {
                    getWholeTag();
                    adaptionImg(pic, $('#img-self'));
                }
                else
                    getPartTag(pic);
            }
            else {
                adaptionImg(pic, $('#img-self'));
            }
        }
    }
    else {
        pointer -= 1;
        if (pointer < 0)
            alert("This is first picture.");
        else {
            var pic = "../task/files/" + taskID + "/" + imgList[pointer].substr(37);
            imgID = imgList[pointer];

            if (hasTag()) {
                if (isWhole) {
                    getWholeTag();
                    adaptionImg(pic, $('#img-self'));
                }
                else
                    getPartTag(pic);
            }
            else {
                adaptionImg(pic, $('#img-self'));
            }

        }
    }
}

function getPartTag(pic) {
    $.ajax({
        type: "GET",
        url: "/workplace/part/show",
        dataType: "json",
        data: {
            imageID: imgID
        },
        success: function (data) {
            restore(pic, data.data, data.data.length);
        },
        error: function () {
            alert("Network warning for getting part tags !");
        }
    });
}

function getWholeTag() {
    $.ajax({
        type: "GET",
        url: "/workplace/whole/getWholeTag",
        dataType: "json",
        data: {
            imageId: imgID
        },
        success: function (data) {
            $("#label-tag").val(data.data);
        },
        error: function () {
            alert("Network warning for getting whole tags !");
        }
    });
}

function hasTag() {
    var isTagged = true;
    $.ajax({
        type: "GET",
        url: "/picture/isTagged",
        dataType: "json",
        data: {
            taskId: parseInt(taskID),
            worker: username,
            imageId: imgID
        },
        success: function (data) {
            if (data.success) {
                isTagged = true;
            }
            else {
                isTagged = false;
            }
        },
        error: function () {
            alert("Network warning for judging tagged !");
        }
    });
    return isTagged;
}

//保存框选
function saveSquare() {
    $.ajax({
        type: "DELETE",
        url: "/workplace/part/delete",
        dataType: "json",
        data: {
            imageID: imgID,
        },
        error: function () {
            alert("Network warning for deleting part tag");
        }
    })

    for (var i = 0; i < labelList.length; i++) {

        $.ajax({
            type: "POST",
            url: "/workplace/part/save",
            dataType: "json",
            data: {
                imageID: imgID,
                x1: labelList[i].x,
                x2: labelList[i].x + labelList[i].w,
                y1: labelList[i].y,
                y2: labelList[i].y + labelList[i].h,
                description: labelList[i].tag
            },
            error: function () {
                alert("Network warning for saving part tag");
            }
        })
    }

}

//保存区域
function savePencil() {
    $.ajax({
        type: "POST",
        url: "workplace/save",
        dataType: "json",
        data: {
            imageID: imgID,
            pencilList: pencilList
        }
    })
}

//保存整体
function saveWhole() {
    $.ajax({
        type: "PUT",
        url: "/workplace/whole/update",
        dataType: "json",
        data: {
            imageID: imgID,
            description: $("#label-tag").val()
        },
        success: function (data) {
            if (data.success)
                alert("Success saving whole tag");
            else
                alert("Error saving whole tag");
        },
        error: function () {
            alert("Network error for saving whole tagged");
        }
    })

}

function saveIsTagged() {
    $.ajax({
        type: "POST",
        url: "/picture/tag",
        dataType: "json",
        data: {
            taskId: parseInt(taskID),
            worker: username,
            pictureName: imgID
        },
        error: function () {
            alert("Network error for saving if tagged");
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
            imageID: imgID,
            pencilList: pencilList
        }
    })
}