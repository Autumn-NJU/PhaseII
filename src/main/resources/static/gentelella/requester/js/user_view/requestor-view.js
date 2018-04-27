/**
 *  usage:
 *  1-显示发布者已经发布的任务：
 *  总任务数
 *  编号、任务名（及描述？）、标注类型、起止时间、人数、完成进度、任务状态（已完成/未完成）、奖励、详情统计（参与者）、Edit
 *
 *  2-显示单个任务的详情（统计信息等）：
 *  编号、参与者、工人进度、标注情况
 *
 */

/*
//仅显示表头
function showListTable(){
    var str = "<table class=\"table table-striped projects\">";
    str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">参与者<br><small>Worker</small></br></th>" +
                    "<th>工人进度<br><small>Process</small></br></th>" +
                    "<th>标注情况<br><small>TagDetails</small></br></th></tr></thead>" + "<tbody>";

    document.getElementById("requestor_task_list").innerHTML = str;

}
showListTable();
*/

//上面注释里的方法-2
function viewDetail(requester, name){
    $.ajax({
        url: "/task/find/requester",
        type: "GET",
        dataType: "json",
        data: {requester: requester},
        success: function(data){
            if(data.success){
                var rlist = data.data;
                var num = rlist.length;

                var str = "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">参与者<br><small>Worker</small></br></th>" +
                    "<th>工人进度<br><small>Process</small></br></th>" +
                    "<th>标注情况<br><small>Tag Details</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else{
                    var i = 0;
                    var task;
                    for( ;i < num;i++){
                        if(rlist[i].name == name) {
                            task = rlist[i];
                            break;
                        }
                    }
                    num = task.workerList.length;
                    var tmp = 0.0;
                    for(i = 0;i < num;i++){
                        tmp = task.processMap.get(task.workerList[i]) * 100;
                        str += "<tr><td>" + (i + 1) + "</td><td>" + task.workerList[i] +
                            "</td><td class=\"project_progress\"><div class=\"progress progress_sm\">" +
                            "<div class=\"progress-bar bg-green\" role=\"progressbar\" data-transitiongoal=" + tmp +
                            "></div><small>" + tmp + "% Complete</small>" + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-success btn-xs\" id=\"tagView\">Details</button>" +
                            "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                document.getElementById("requestor_task_list").innerHTML = str;

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

//上面注释里的方法-1
var requestor = "unknown";         //初始化参数

function taskListView(requester){

    $.ajax({
        url: "/task/find/requester",
        type: "GET",
        dataType: "json",
        data: {requester: requester},
        success: function(data){
            if(data.success){
                var rlist = data.data;
                var num = rlist.length;
                /*var str = "<table class=\"table table-striped projects\"><thead><tr><th>总任务数<br><small>Task Amount</small></br></th></tr></thead>" +
                    "<tbody><tr><td>" + num + "</td></tr></tbody></table>";
                */
                var str = "<p>已发布" + num + "个任务</p>";
                str += "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>标注类型<br><small>Tag Type</small></br></th>" + "<th>起止时间<br><small>Date</small></br></th>" +
                    "<th>参与人数/总人数<br><small>Participant</small></br></th>" +
                    "<th>完成进度<br><small>Process</small></br></th>" + "<th>任务状态<br><small>Status</small></br></th>" +
                    "<th>奖励<br><small>Reward</small></br></th>" + "<th>详情统计<br><small>Details</small></br></th>" +
                    "<th style=\"width: 20%\">#Edit</th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else{
                    var i = 0;
                    var workerNum = 0;
                    var workerLimit = 0;
                    for(i = 1;i <= num;i++){
                        workerLimit = rlist[i].workerLimit;
                        workerNum = rlist[i].workerList.length;
                        str += "<tr><td>" + i + "</td><td>" + rlist[i].name + "</td><td>" +
                            rlist[i].tagType + "</td><td>" + rlist[i].beginDate + "-" + rlist[i].ddl + "</td><td>" +
                            workerNum + "/" + workerLimit + "</td><td>" +
                            rlist[i].description + "</td><td>" + rlist[i].description + "</td><td>" +
                            rlist[i].reward + "</td><td>" + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-success btn-xs\" id=\"taskDetailView\" onclick=\"viewDetail(requester, rlist[i].name)\">Details</button>"+ "</td><td>" +
                            "<a href=\"#\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-folder\"></i> View </a>" +
                            "<a href=\"#\" class=\"btn btn-info btn-xs\"><i class=\"fa fa-pencil\"></i> Edit </a>" +
                            "<a href=\"#\" class=\"btn btn-danger btn-xs\"><i class=\"fa fa-trash-o\"></i> Delete </a>" + "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                document.getElementById("requestor_task_list").innerHTML = str;

                //alert("succeed in getting the requester's data and num:" + num);

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

taskListView(requestor);

