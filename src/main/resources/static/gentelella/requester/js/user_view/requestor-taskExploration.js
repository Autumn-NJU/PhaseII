/**
 *  usage:
 *  1-发布一个新任务
 *
 *  2-查看用户自己已发布的任务
 *
 *  3-显示所有人已发布的任务：
 *  编号、任务名、任务描述、发布者、标注类型、起止时间、限额、奖励
 *
 */

var userName = getCookieValue("username");

function getTaskMarket(){
    $.ajax({
        url: "/task/find/all",
        type: "GET",
        dataType: "json",
        data: {},
        success: function(data){
            if(data.success){

                var tlist = data.data;
                var num = tlist.length;

                var str = "<div><button type=\"button\" class=\"btn btn-success btn-xs\" id=\"uploadTask\" onclick=\"jumpToUploadTask()\">发布一个新任务</button></div>" + "<br>";
                str += "<div><button type=\"button\" class=\"btn btn-success btn-xs\" id=\"viewTask\" onclick=\"jumpToViewTask()\">我发布的任务</button></div>";

                str += "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#任务编号</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>任务描述<br><small>Description</small></br></th>" +
                    "<th>发布者<br><small>Requester</small></br></th>" +
                    "<th>标注类型<br><small>Tag Type</small></br></th>" +
                    "<th>起止时间<br><small>Date</small></br></th>" +
                    "<th>限额<br><small>Limit</small></br></th>" + "<th>奖励<br><small>Reward</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else {
                    var i;
                    for (i = num - 1; i >= 0; i--) {
                        str += "<tr><td>" + tlist[i].id + "</td><td>" + tlist[i].name + "</td><td>" +
                            tlist[i].description + "</td><td>" +
                            tlist[i].requester + "</td><td>" +
                            tlist[i].tagType + "</td><td>" +
                            tlist[i].beginDate + " - " + tlist[i].ddl + "</td><td>" +
                            tlist[i].workerLimit + "</td><td>" + tlist[i].reward + "</td></tr>";
                    }
                }
                str += "</tbody></table>";
                if(num == 0){
                    str = "<p><b>没有任务被发布</b></p>" + str;
                }
                document.getElementById("taskExplore-requester").innerHTML = str;
            }
            else{
                alert("Error getting task table!");
            }
        },
        error: function () {
            alert("Network warning for getting user table!");
        }
    });
}
getTaskMarket();


//“发布一个新任务”按钮的响应
function jumpToUploadTask() {
    window.location.href = "projects-taskNew.html";
}


//“我发布的任务”按钮的响应
function jumpToViewTask() {
    window.location.href = "projects-requestor.html";
}

