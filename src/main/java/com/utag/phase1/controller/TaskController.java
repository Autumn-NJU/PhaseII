
/**
 * 任务的controller，控制复杂逻辑，http request
 */

package com.utag.phase1.controller;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.TaskVO;
import com.utag.phase1.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("task/")
public class TaskController {
    String fileName = "";

    @Autowired
    private TaskService taskService;

    private static String UPLOADED_FOLDER = "src/main/resources/static/task/zip/";

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit, String ddl,
                                      String description, String folderName, TagType tagType){

        return taskService.saveTask(name, reward, requester, workerLimit, ddl,
                description, fileName, tagType);

    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<Boolean> deleteTask(int id){
        return taskService.deleteTask(id);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl, String description){
        return taskService.updateTask(id, reward, workerLimit, ddl, description);
    }

    @RequestMapping(value = "claim", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> claimTask(int id, String worker){
        return taskService.claimTask(id, worker);
    }


    @RequestMapping(value = "abandon", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> abandonTask(int id, String worker){
        return taskService.abandonTask(id, worker);
    }

    @RequestMapping(value = "find/requester", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listTaskByRequester(String requester){
        return taskService.listTaskByRequester(requester);
    }

    @RequestMapping(value = "find/worker", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listTaskByWorker(String worker){
        return taskService.listTaskByWorker(worker);
    }

    @RequestMapping(value = "find/all", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listAllTask(){
        return taskService.listAllTask();
    }

    @RequestMapping(value = "/getID", method = RequestMethod.GET)
    @ResponseBody
    public Response<TaskVO> getTaskById(int id){
        return taskService.getTaskById(id);
    }

    @RequestMapping(value = "/max", method = RequestMethod.GET)
    @ResponseBody
    public Response<Double> getMaxProcess(int id){
        TaskVO taskVO = taskService.getTaskById(id).getData();
        if(taskVO == null)
            return new Response<>(true, .0, "No task!");
        double max = .0;
        Map<String, Double> m = taskVO.getProcessMap();
        for(Double val: m.values()){
            if(val > max)
                max = val;
        }
        return new Response<>(true, max, "Succeed to get max process!");
    }

    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public Response<String> fileUpload(MultipartFile file){
        String name = "";
        try {
            saveUploadedFiles(Arrays.asList(file));

        } catch (IOException e) {
            return new Response<>(false, null, "Fail to submit...");
        }
        return new Response<>(true, name,"Succeed to submit...");
    }

    /**
     * 得到任务数
     * @return
     */
    @RequestMapping(value = "number", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getTaskNum() {
        return taskService.getTaskNum();
    }

    @RequestMapping(value = "top5Woker", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<UserVO>> getTop5Worker(){
        return taskService.listTop5Woker();
    }

    @RequestMapping(value = "top5Requester", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<UserVO>> getTop5Requester(){
        return taskService.listTop5Requester();
    }

    @RequestMapping(value = "monthFinishTask", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<Integer>> lisMonthFinishTask(){
        return taskService.listMonthFinishTask();
    }

    @RequestMapping(value = "monthRequestTask", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<Integer>> listMonthRequestTask(){
        return taskService.listMonthRequestTask();
    }

    @RequestMapping(value = "listAvailableTask", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listAvailableTask(){
        return taskService.listAvailbleTask();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "listPartNum", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<Integer>> listPartNum(){
        return taskService.listPartNum();
    }

    /**
     * 给出所有路径
     * @param taskId "../task/files/"
     * @return
     */
    @RequestMapping(value = "/listPicturePath", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> listPicturePath(int taskId){
        List<String> list = FileTool.listPictureName(taskId + "");
        try {
            return new Response<>(true, list, "Succeed to list picture path!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list picture path!");
        }
    }

    @RequestMapping(value = "/partTaskNum", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getPartTaskNum(){
        return taskService.getPartTaskNum();
    }

    @RequestMapping(value = "/wholeTaskNum", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getWholeTaskNum(){
        return taskService.getWholeTaskNum();
    }

    @RequestMapping(value = "/regTaskNum", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getRegTaskNum(){
        return taskService.getRegTaskNum();
    }


    /**
     * 根据id给出标注类型
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/getTagType", method = RequestMethod.GET)
    @ResponseBody
    public Response<TagType> getTagType(int taskId){
        return taskService.getTagType(taskId);
    }

    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        String pathSum = "";
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            System.out.println(path);
            Files.write(path, bytes);
            pathSum += file.getOriginalFilename();
        }
        System.out.println(pathSum);
        fileName = pathSum;
    }
}
