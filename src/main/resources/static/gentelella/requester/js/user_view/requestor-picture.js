
/**
 * 显示个人的单个任务所包含的所有图片（瀑布流形式展示）
 */

//初始化taskID 和 workerName
var taskID = 999999;
var workerName = "undefined";

var count = 0;
var num = 0;
var imgUrlArr = new Array();


//通过 URL 获得taskID和workerName
function getParasByUrl() {
    var url = window.location.href;
    //var url = location.search;               //获取url中"?"符后的字串

    url = url.substring(url.indexOf("?") + 1);
    taskID = url.substring(0, url.indexOf("&"));
    taskID = taskID.substring(taskID.indexOf("=") + 1);

    workerName = url.substring(url.indexOf("&") + 1);
    workerName = workerName.substring(workerName.indexOf("=") + 1);
}
getParasByUrl();


//根据用户名和任务id获得对应的已标注的图片集
function getImgSetUrl(taskID, workerName){
    alert("looking for the img set:taskID=" + taskID + "workerName=" + workerName);

    $.ajax({
        url: "/picture/showTagged",
        type: "GET",
        dataType: "json",
        data: {taskID: parseInt(taskID), worker:workerName},
        success: function(data){
            if(data.success){
                var imglist = data.data;
                var len = imglist.length;
                for(var i = 0;i < len;i++){
                    imgUrlArr[i] = imglist[i];
                }
                num = imgUrlArr.length;
                if(num == 0){
                    document.getElementById("waterfall").innerHTML = "<p><b>未开始此图片标注任务</b></p>";
                }

            }
            else{
                alert("Error getting the tagged pictures!");
            }
        },
        error: function () {
            alert("Network warning for getting worker-picture table!");
        }
    });
}
getImgSetUrl(taskID, workerName);


//展示图片
/*
imgUrlArr[0] = "images/inbox.png";
imgUrlArr[1] = "images/cropper.jpg";
imgUrlArr[2] = "images/media.jpg";
imgUrlArr[3] = "images/img.jpg";
imgUrlArr[4] = "images/picture.jpg";

imgUrlArr[5] = "images/cropper.jpg";
imgUrlArr[6] = "images/inbox.png";
imgUrlArr[7] = "images/media.jpg";
imgUrlArr[8] = "images/img.jpg";
imgUrlArr[9] = "images/picture.jpg";

imgUrlArr[10] = "images/cropper.jpg";
imgUrlArr[11] = "images/inbox.png";
imgUrlArr[12] = "images/media.jpg";
imgUrlArr[13] = "images/img.jpg";
imgUrlArr[14] = "images/picture.jpg";

imgUrlArr[15] = "images/cropper.jpg";
imgUrlArr[16] = "images/inbox.png";
imgUrlArr[17] = "images/media.jpg";
imgUrlArr[18] = "images/img.jpg";
imgUrlArr[19] = "images/picture.jpg";
*/

var img = null;                  //临时的图片对象
var box = document.getElementById("waterfall");         //容器对象
var lastS = 0;                   //上一次的scrollTop
var nowS = 0;                    //现在的scrollTop
var delS = 0;                    //scrollTop间隔
var sum = 0;                     //用来测试一共加载了多少张图片

//用来计算什么时候适合加载图片
box.onscroll = function(){
    nowS = box.scrollTop;
    delS = nowS - lastS;
    if(delS >= 100){
        lastS = box.scrollTop;
        insertImg();
    }
}

function insertImg(){
    //alert("success:call insertImg function");

    img = new Image();
    if(count < num){
        img.src = imgUrlArr[count];
        count++;
    }

    img.onload = function(){
        box.appendChild(this);
        sum++;
        console.log(sum);
        //用来判断停止加载的时机
        if(this.offsetTop < box.offsetHeight + box.scrollTop){
            insertImg();
        }
    }
}
insertImg();