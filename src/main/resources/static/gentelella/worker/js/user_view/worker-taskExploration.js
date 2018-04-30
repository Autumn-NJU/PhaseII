/**
 *  usage:
 *  显示所有当前可被领取的任务：
 *  编号、任务名、任务描述、标注类型、起止时间、限额、奖励、接受任务
 *
 */

//var userName = "admin";
var userName = getCookieValue("username");

//显示所有当前可被领取的任务
function getTaskMarket(){
    $.ajax({
        url: "/task/listAvailableTask",
        type: "GET",
        dataType: "json",
        data: {},
        success: function(data){
            if(data.success){

                var tlist = data.data;
                var num = tlist.length;

                var str = "<table class=\"table table-striped projects\">";
                str += "<thead><tr><th style=\"width: 1%\">#任务编号</th>" + "<th style=\"width: 20%\">任务名<br><small>Task Name</small></br></th>" +
                    "<th>任务描述<br><small>Description</small></br></th>" + "<th>标注类型<br><small>Tag Type</small></br></th>" +
                    "<th>起止时间<br><small>Date</small></br></th>" +
                    "<th>限额<br><small>Limit</small></br></th>" + "<th>奖励<br><small>Reward</small></br></th>" +
                    "<th>接受任务<br><small>Claim</small></br></th></tr></thead>" + "<tbody>";

                if(num == 0){
                    str = "<p>当前没有可领取的任务</p>" + str;
                }
                else{
                    var i;
                    for(i = num - 1;i >= 0;i--){
                        str += "<tr><td>" + tlist[i].id + "</td><td>" + tlist[i].name + "</td><td>" +
                            tlist[i].description + "</td><td>" + tlist[i].tagType + "</td><td>" +
                            tlist[i].beginDate + " - " + tlist[i].ddl + "</td><td>" +
                            tlist[i].workerLimit + "</td><td>" + tlist[i].reward + "</td><td>" +
                            "<button type=\"button\" class=\"btn btn-success btn-xs\" id=\"claimTask-" + tlist[i].id + "\" onclick=\"claimTask(this.id,userName)\">领任务</button>" +
                            "</td></tr>";
                    }
                }

                str += "</tbody></table>";
                document.getElementById("taskExplore").innerHTML = str;

            }
            else{
                alert("Error getting task table!");
            }
        },
        error: function () {
            alert("Network warning for getting user table/worker-taskExploration1!");
        }
    });
}
getTaskMarket();


//“领任务”按钮的响应
function claimTask(taskID, worker) {

    taskID = (taskID + "").substring((taskID + "").indexOf("-") + 1);

    //跳转到工人个人的任务中心（领取成功：不重复领 and 当前人数未达到限额）
    //window.location.href = "projects-worker.html";


    $.ajax({
        url: "/task/claim",
        type: "POST",
        dataType: "json",
        data: {id: parseInt(taskID), worker: worker},
        success: function(data){
            if(data.success){
                //跳转到工人个人的任务中心（领取成功：不重复领 and 当前人数未达到限额 and 已在claim方法中更新了工人和任务情况）
                window.location.href = "projects-worker.html";
            }
            else{
                alert("您已领取过此任务！");
            }
        },
        error: function () {
            alert("Network warning for getting user table/worker-taskExploration2!");
        }
    });


}
