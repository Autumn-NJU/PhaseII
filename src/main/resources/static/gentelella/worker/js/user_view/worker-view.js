
/**
 *  usage:
 *  显示工人已接受的个人任务
 *  编号、任务名（及描述？）、发布者、标注类型、日期、个人进度、任务状态（已完成/未完成）、预期奖励、任务操作
 *  P.S.任务操作包括 - View 查看该任务的所有图片（？） / Edit 继续标注剩下的图片 / Delete 删除或放弃该任务（？）
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


var userName = "unknown";

/* 缺少jquery文件
//通过 URL 获得当前的用户名 + 任务的id
$(function(){
    getUserNameByUrl();
});
function getUserNameByUrl(){
    userName = $.query.get("user");
}
*/

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
                str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>发布者<br><small>Requestor</small></br></th>" + "<th>标注类型<br><small>Tag Type</small></br></th>" +
                    "<th>起止时间<br><small>Date</small></br></th>" + "<th>个人进度<br><small>Process</small></br></th>" +
                    "<th>任务状态<br><small>Status</small></br></th>" + "<th>预期奖励<br><small>Reward</small></br></th>" +
                    "<th>任务操作<br><small>Operation</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else{
                    var i = 0;
                    var tmp = 0.0;
                    for(i = 0;i < num;i++){
                        tmp = wlist[i].processMap.get(worker) * 100;
                        str += "<tr><td>" + (i + 1) + "</td><td>" + wlist[i].name + "</td><td>" +
                            wlist[i].requester + "</td><td>" + wlist[i].tagType + "</td><td>" +
                            wlist[i].beginDate + "-" + wlist[i].ddl + "</td><td class=\"project_progress\"><div class=\"progress progress_sm\">" +
                            "<div class=\"progress-bar bg-green\" role=\"progressbar\" data-transitiongoal=" + tmp + "></div><small>" + tmp + "% Complete</small>" + "</td><td>" +
                            wlist[i].isFinishedMap.get(worker) + "</td><td>" + wlist[i].reward + "</td><br>" +
                            "<br><a href=\"#\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-folder\"></i> View </a></br>" +
                            "<br><a href=\"projects-picture.html?user=" + worker + "&taskID=" + wlist[i].id + "\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil\"></i> Edit </a></br>" +
                            "<br><a href=\"#\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash-o\"></i> Delete </a></br>" + "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                document.getElementById("worker_task_list").innerHTML = str;

                //alert("succeed in getting user: " + userName);

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

taskView(userName);

