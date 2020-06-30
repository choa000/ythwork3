package cn.edu.nenu.controller;

import cn.edu.nenu.domain.User;
import cn.edu.nenu.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController Class
 *
 * @author <b>Oxidyc</b>, Copyright &#169; 2003
 * @version 1.0, 2020-03-04 22:54
 */
@CommonsLog
@Controller
@RequestMapping("/user")
public class UserController {

    private static final int PAGE_SIZE = 20;

    @Autowired
    public UserService userService;

    /**
     * 列表页面，涉及到分页
     *
     * @param pageNumber
     * @param model
     * @param request
     * @return
     */
    @GetMapping()
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       Model model, ServletRequest request) {
        String param = request.getParameter("param");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("param", param);
        Page<User> users = userService.getPage(pageNumber, PAGE_SIZE, map);
        model.addAttribute("param", param);
        model.addAttribute("users", users);
        return "user/list"; //视图名，视图路径
    }

    /**
     * 根据主键ID获取实体，获取详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    /**
     * +
     * 删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/user";
    }


    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return "user/userForm"; //视图名，视图路径
    }

    @PostMapping("/update")
    public String update(@Valid User newUser, RedirectAttributes attributes) {
        newUser = userService.save(newUser);
        attributes.addAttribute("message", "修改成功");
        attributes.addAttribute("user", newUser);
        return "redirect:/user";
    }


    /**
     * 进入创建用户页面
     *
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        return "user/form";
    }
    //@GetMapping("/create")
    //public ModelAndView createFrm(Model model){
    //    return new ModelAndView("user/form");
    //}

    /**
     * 填写用户信息后保存信息到数据库
     * <p>
     * form表单标签属性name的值
     * user.username
     * user.password
     * user.createdAt
     * <input type="text" name="user.username" value=""/>
     *
     * @param attributes
     * @return
     */
    @PostMapping("/create")
    public String create(@Valid User newUser, RedirectAttributes attributes) {
        //第一：request接收参数而来
        //第二：采用自动绑定接收参数而来
        //第三：接收字符串类型的JSON数据，反序列为对象
        newUser = userService.save(newUser);
        attributes.addAttribute("message", "保存成功");
        attributes.addAttribute("user", newUser);
        return "redirect:/user";
    }

    /*@PostMapping("/userInfo")
    public String createJSON(@RequestBody String json, RedirectAttributes attributes){
        //对json进行发序列化，变成参数对象
        return "redirect:/user"; //视图路径
    }
    @PostMapping
    public String createRequest(HttpServletRequest request, RedirectAttributes attributes){
        String username = request.getParameter("username");
        User newUser = new User();
        newUser.setUsername(username);

        userService.save(newUser);
        return "redirect:/user"; //视图路径
    }*/


}
