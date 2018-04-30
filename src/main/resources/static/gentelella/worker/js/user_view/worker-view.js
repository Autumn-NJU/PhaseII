
/**
 *  usage:
 *  显示工人已接受的个人任务
 *  编号、任务名（及描述）、发布者、标注类型、日期、个人进度、任务状态（已完成/未完成）、预期奖励、任务操作
 *  P.S.任务操作包括：
 *      “查看”：查看该任务中已经标注的图片，并且可以修改
 *      “标注”：继续标注剩下的图片
 *      “删除”：删除或放弃该任务
 *
 */

/*
//仅显示表头
function showListTable(){
    var str = "<table class=\"table table-striped projects\">";
    str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
        "<th>发布者<br><small>Requestor</small></br></th>" + "<th>标注类型<br><small>Tag Type</small></br></th>" +
        "<th>起止时间<br><small>Date</small></br></th>" + "<th>个人进度<br><small>Process</small></br></th>" +
        "<th>任务状态<br><small>Status</small></br></th>" + "<th>预期奖励<br><small>Reward</small></br></th>" +
        "<th style=\"width: 20%\">#Edit</th></tr></thead>" + "<tbody></tbody>" + "</table>";

    document.getElementById("worker_task_list").innerHTML = str;

    alert("success to call func");

}
showListTable();
*/


//var userName = "admin";
var userName = getCookieValue("username");

/* 缺少jquery文件
//通过 URL 获得当前的用户名 + 任务的id
$(function(){
    getUserNameByUrl();
});
function getUserNameByUrl(){
    userName = $.query.get("user");
}
*/

//显示工人已接受的个人任务
function taskView(worker){
    $.ajax({
        url: "/task/find/worker",
        type: "GET",
        dataType: "json",
        data: {worker: worker},
        success: function(data){
            if(data.success){

                var wlist = data.data;
                var num = wlist.length;
                var str = "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#任务编号</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>发布者<br><small>Requestor</small></br></th>" + "<th>标注类型<br><small>Tag Type</small></br></th>" +
                    "<th>起止时间<br><small>Date</small></br></th>" + "<th>个人进度<br><small>Process</small></br></th>" +
                    "<th>任务状态<br><small>Status</small></br></th>" + "<th>预期奖励<br><small>Reward</small></br></th>" +
                    "<th>任务操作<br><small>Operation</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else{
                    var i = 0;
                    var tmp = 0.0;
                    var status = "未知";
                    for(i = num - 1;i >= 0;i--){
                        tmp = wlist[i].processMap[worker] * 100;
                        if(wlist[i].isFinishedMap[worker]){
                            status = "已完成";
                        }
                        else{
                            status = "待完成";
                        }
                        str += "<tr><td>" + wlist[i].id + "</td><td>" + wlist[i].name + "<br><small>" + wlist[i].description + "</small></br>" + "</td><td>" +
                            wlist[i].requester + "</td><td>" + wlist[i].tagType + "</td><td>" +
                            wlist[i].beginDate + " - " + wlist[i].ddl + "</td><td class=\"project_progress\"><div class=\"progress progress_sm\">" +
                            "<div class=\"progress-bar bg-green\" role=\"progressbar\" data-transitiongoal=" + tmp + "></div><small>" + tmp + "% Complete</small>" + "</td><td>" +
                            status + "</td><td>" + wlist[i].reward + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-primary btn-xs\" id=\"view-" + wlist[i].id + "\" onclick=\"pictureView(this.id)\"><i class=\"fa fa-folder\"></i> 查看 </button>" +
                            "<button type=\"button\" class=\"btn btn-info btn-xs\" id=\"draw-" + wlist[i].id + "\" onclick=\"jumpToDraw(this.id)\"><i class=\"fa fa-pencil\"></i> 标注 </button>" +
                            "<button type=\"button\" class=\"btn btn-danger btn-xs\" id=\"delete-" + wlist[i].id + "\" onclick=\"taskAbandonment(this.id)\"><i class=\"fa fa-trash-o\"></i> 删除 </button>" + "</td></tr>";
                            //"<a href=\"#\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-folder\"></i> 查看 </a>" +
                            //"<a href=\"../../draw/index.html?taskID=" + wlist[i].id + "\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil\"></i> 标注 </a>" +
                            //"<a href=\"#\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash-o\"></i> 删除 </a>" + "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                if(num == 0){
                    str += "<p><b>您当前没有领取任何任务</b></p>";
                }
                document.getElementById("worker_task_list").innerHTML = str;

                //alert("succeed in getting user: " + userName);

            }
            else{
                alert("Error getting task table!");
            }
        },
        error: function () {
            alert("Network warning for getting user table/worker-view!");
        }
    });
}

taskView(userName);

function jumpToDraw(taskID) {
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);
    //alert("success:call jumpToDraw and taskID=" + taskID);

    window.location.href = "../../draw/index.html?taskID=" + taskID;
}

function taskAbandonment(taskID) {
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);

    $.ajax({
        url: "/task/abandon",
        type: "POST",
        dataType: "json",
        data: {id: parseInt(taskID)},
        success: function(data){
            if(data.success){
                alert("提示：您已放弃此任务！");
                window.location.href = "projects-worker.html";  //刷新页面
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


//查看已经标注的图片-Tagged
function pictureView(taskID) {
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);

    window.location.href = "../../draw/index.html?taskID=" + taskID;
    /*
    $.ajax({
        url: "/picture/showTagged",
        type: "GET",
        dataType: "json",
        data: {taskID: taskID, worker:userName},
        success: function(data){
            if(data.success){
                var imglist = data.data;

                //add some code here

            }
            else{
                alert("Error getting tagged task images!");
            }
        },
        error: function () {
            alert("Network warning for getting user table!");
        }
    });
    */
}