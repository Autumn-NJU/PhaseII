/**
 *  usage:
 *  1-显示发布者已经发布的任务：
 *  总任务数
 *  任务编号、任务名（及描述）、标注类型、起止时间、人数、完成进度、任务状态（已完成/未完成）、奖励、详情统计（参与者）、任务操作（修改、删除）
 *  // 任务操作中可以增加一个“查看”按钮，以允许发布者查看自己发布的任务的图片
 *  P.S.“查看详情”按钮
 *      “修改”按钮
 *      “删除”按钮
 *
 *  2-显示单个任务的详情（统计信息等）：
 * 序号、参与者、工人进度、标注情况
 *  P.S.“查看标注情况”按钮
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


var requester = getCookieValue("username");

//上面注释里的方法-2
function viewDetail(taskID){
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);

    $.ajax({
        url: "/task/getID",
        type: "GET",
        dataType: "json",
        data: {id: taskID},
        success: function(data){
            if(data.success){
                var task = data.data;

                var str = "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#</th>" + "<th style=\"width: 20%\">参与者<br><small>Worker</small></br></th>" +
                    "<th>工人进度<br><small>Process</small></br></th>" +
                    "<th>标注详情<br><small>Tag Details</small></br></th></tr></thead>" + "<tbody>";

                if(task == null){

                }
                else{
                    var i;
                    var num = task.workerList.length;
                    var tmp = 0.0;
                    for(i = 0;i < num;i++){
                        tmp = task.processMap[(task.workerList[i])] * 100;
                        str += "<tr><td>" + (i + 1) + "</td><td>" + task.workerList[i] +
                            "</td><td class=\"project_progress\"><div class=\"progress progress_sm\">" +
                            "<div class=\"progress-bar bg-green\" role=\"progressbar\" data-transitiongoal=" + tmp +
                            "></div><small>" + tmp + "% Complete</small>" + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-success btn-xs\" id=\"tagView-" + task.workerList[i] + "\" onclick=\"viewTagging(taskID, this.id)\">查看标注情况</button>" +
                            "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                if(task == null){
                    str += "<p><b>当前没有工人参与此任务</b></p>";
                }
                document.getElementById("requestor_task_list").innerHTML = str;

            }
            else{
                alert("Error getting task detail table!");
            }
        },
        error: function () {
            alert("Network warning for getting user table!");
        }
    });
}

//上面注释里的方法-1
function taskListView(){

    $.ajax({
        url: "/task/find/requester",
        type: "GET",
        dataType: "json",
        data: {requester: requester},
        success: function(data){
            if(data.success){
                var rlist = data.data;
                var num = rlist.length;

                var str = "<p><b>已发布" + num + "个任务</b></p>";
                str += "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#任务编号</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>标注类型<br><small>Tag Type</small></br></th>" + "<th>起止时间<br><small>Date</small></br></th>" +
                    "<th>参与人数/总人数<br><small>Participant</small></br></th>" +
                    "<th>完成进度<br><small>Process</small></br></th>" +
                    "<th>任务状态<br><small>Status</small></br></th>" +
                    "<th>奖励<br><small>Reward</small></br></th>" +
                    "<th>详情统计<br><small>Details</small></br></th>" +
                    "<th>任务操作<br><small>Operation</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){

                }
                else{
                    var i;
                    var workerNum = 0;
                    var workerLimit = 0;
                    var processSum = 0.0;
                    var taskStatus = "未知";

                    for(i = num - 1;i >= 0;i--){
                        workerLimit = rlist[i].workerLimit;
                        workerNum = rlist[i].workerList.length;
                        for(var key in rlist[i].processMap){
                            processSum += rlist[i].processMap[key];
                        }
                        processSum = (processSum / workerLimit) * 100;
                        if(processSum < 100){
                            taskStatus = "未完成";
                        }
                        else{
                            taskStatus = "已完成";
                        }
                        str += "<tr><td>" + rlist[i].id + "</td><td>" + rlist[i].name + "<br><small>" + rlist[i].description + "</small></br>" + "</td><td>" +
                            rlist[i].tagType + "</td><td>" + rlist[i].beginDate + " - " + rlist[i].ddl + "</td><td>" +
                            workerNum + "/" + workerLimit + "</td><td class=\"project_progress\"><div class=\"progress progress_sm\">" +
                            "<div class=\"progress-bar bg-green\" role=\"progressbar\" data-transitiongoal=" + processSum + "></div><small>" + processSum + "% Complete</small>" + "</td><td>" +
                            taskStatus + "</td><td>" +
                            rlist[i].reward + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-success btn-xs\" id=\"taskDetailView-" + rlist[i].id + "\" onclick=\"viewDetail(this.id)\">查看详情</button>"+ "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-info btn-xs\" id=\"taskUpdate-" + rlist[i].id + "\" onclick=\"jumpToUploadTask(this.id)\">修改</button>"+
                            "<button type=\"button\" class=\"btn btn-danger btn-xs\" id=\"taskDeletion-" + rlist[i].id + "\" onclick=\"taskDelete(this.id)\">删除</button>"+ "</td></tr>";
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

taskListView();


function jumpToUploadTask(taskID) {
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);
    window.location.href = "projects-taskUpdate.html?taskID=" + taskID;   //待统一
}


function taskDelete(taskID) {
    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);

    $.ajax({
        url: "/task/delete",
        type: "DELETE",
        dataType: "json",
        data: {id: parseInt(taskID)},
        success: function(data){
            if(data.success){
                alert("提示：删除任务成功！");
                window.location.href = "projects-requestor.html";  //刷新页面
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



function viewTagging(taskID, workerName) {
    workerName = (workerName + "").substring((workerName + "").indexOf("-") + 1);

    window.location.href = "projects-picture.html?taskID=" + taskID + "&worker=" + workerName;

    //展示某个工人的图片标注成果-Tagged
    /*$.ajax({
        url: "/picture/showTagged",
        type: "GET",
        dataType: "json",
        data: {taskID: taskID, worker:workerName},
        success: function(data){
            if(data.success){
                var imglist = data.data;

                //add some code here
            }
            else{
                alert("Error getting task images!");
            }
        },
        error: function () {
            alert("Network warning for getting user table!");
        }
    });
    */

}