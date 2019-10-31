package com.krzyszkowski.sandbox.flowcontrol.core.controllers;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.services.TimeCardService;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.TaskState;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskStateDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.services.TaskService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

    private final TaskService taskService;
    private final TimeCardService timeCardService;

    public DashboardController(TaskService taskService, TimeCardService timeCardService) {
        this.taskService = taskService;
        this.timeCardService = timeCardService;
    }

    @GetMapping("tasks")
    public String tasks(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                        @RequestParam(value = "completed", required = false, defaultValue = "false") boolean completed,
                        @RequestParam(value = "assignedToMe",
                                      required = false,
                                      defaultValue = "false") boolean assignedToMe,
                        Model model,
                        Principal principal) {
        model.addAttribute("tasks",
                           taskService.getPagedTasks(principal.getName(), page - 1, size, completed, assignedToMe));
        return "dashboard/tasks";
    }

    @GetMapping("tasks/add")
    public String addTask(Model model) {
        model.addAttribute("task", new TaskDto());
        return "dashboard/tasks-add";
    }

    @PostMapping("tasks/add")
    public ModelAndView addTask(@ModelAttribute("task") @Valid TaskDto taskDto,
                                BindingResult bindingResult,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("dashboard/tasks-add");
            response.addObject("task", taskDto);
            return response;
        }

        taskService.addTask(taskDto, principal.getName());

        return new ModelAndView("redirect:/dashboard/tasks/add?success");
    }

    @GetMapping("tasks/edit/{id}")
    public String editTask(@PathVariable long id, Model model, Principal principal) {
        model.addAttribute("task", taskService.getTask(id, principal.getName()));
        return "dashboard/tasks-edit";
    }

    @PostMapping("tasks/edit/{id}")
    public ModelAndView editTask(@PathVariable long id,
                                 @ModelAttribute("task") @Valid TaskDto taskDto,
                                 BindingResult bindingResult,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("dashboard/tasks-edit");
            response.addObject("task", taskDto);
            return response;
        }

        taskService.updateTask(taskDto, principal.getName());

        return new ModelAndView("redirect:/dashboard/tasks");
    }

    @GetMapping("tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return "redirect:/dashboard/tasks";
    }

    @PostMapping("tasks/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("id") long id,
                         @RequestParam("name") String name) {
        taskService.addTaskFile(file, id, name);
        return "redirect:/dashboard/tasks?uploaded";
    }

    @GetMapping("tasks/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        var file = taskService.getTaskFile(filename);
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION,
                                     "attachment; filename=\"" + file.getFilename() + "\"")
                             .body(file);
    }

    @GetMapping("tasks/state/{id}")
    public String taskState(@PathVariable long id, Model model) {
        model.addAttribute("states", TaskState.values());
        model.addAttribute("task", taskService.getTaskState(id));
        return "dashboard/tasks-state";
    }

    @PostMapping("tasks/state/{id}")
    public ModelAndView taskState(@PathVariable long id,
                                  @ModelAttribute("document") @Valid TaskStateDto taskStateDto,
                                  BindingResult bindingResult,
                                  Principal principal) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("dashboard/tasks-state");
            response.addObject("states", TaskState.values());
            response.addObject("task", taskService.getTaskState(id));
            return response;
        }

        taskService.updateTaskState(taskStateDto, principal.getName());

        return new ModelAndView("redirect:/dashboard/tasks");
    }

    @GetMapping("time")
    public String time(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                       Model model,
                       Principal principal) {
        model.addAttribute("timeCards", timeCardService.getPagedTimeCards(principal.getName(), page - 1, size));
        return "dashboard/time";
    }

    @GetMapping("time/add")
    public String addTime(Model model) {
        model.addAttribute("timeCard", new TimeCardDto());
        return "dashboard/time-add";
    }

    @PostMapping("time/add")
    public ModelAndView addTime(@ModelAttribute("timeCard") @Valid TimeCardDto timeCardDto,
                                BindingResult bindingResult,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            var response = new ModelAndView("dashboard/time-add");
            response.addObject("timeCard", timeCardDto);
            return response;
        }

        timeCardService.addTimeCard(timeCardDto, principal.getName());

        return new ModelAndView("redirect:/dashboard/time/add?success");
    }

    @GetMapping("time/delete/{id}")
    public String deleteTime(@PathVariable long id) {
        timeCardService.deleteTimeCard(id);
        return "redirect:/dashboard/time";
    }
}
