package com.lsu.controller.user;

import com.lsu.common.R;
import com.lsu.entity.User;
import com.lsu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zt
 * @create 2023-01-19 23:50
 * 管理账号
 */
@RestController()
public class UserController {
    @Resource
    private UserService userService;


    /**
     *  登录接口
     * @param user 用户类
     * @return  登录结果
     * 任何人
     */
    @PostMapping("/login")
    public R<String> userLogin(@RequestBody User user, @RequestParam(defaultValue = "false") Boolean rememberMe,
                               HttpSession session, HttpServletResponse response){
        User userX = userService.getUserInfoByName(user.getUserName());
        //System.out.println(userX);
        if(userX == null)
            return R.err("用户名不存在");               //用户名不存在错误
        if (!userX.getStatus().equals(user.getStatus()))           //判断身份是否正确
            return R.err("身份错误");
        //1 获取 Subject 对象
        Subject subject = SecurityUtils.getSubject();
        //2 封装请求数据到 token 对象中
        AuthenticationToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword(),rememberMe);
        //3 调用 login 方法进行登录认证
        try {
            subject.login(token);
            //获取shiro的会话
            Session shiroSession = subject.getSession();
            String sessionId = shiroSession.getId().toString();
            //保存身份、id
            session.setAttribute("userId",userX.getId());
            session.setAttribute("role",userService.getStatusByName(user.getUserName()));
            //保存sessionId返回
            response.setHeader("sessionId", sessionId);
            return R.success(""+userX.getId());               //登录成功
        } catch (AuthenticationException e) {
            return R.err("密码错误");                   //密码错误
        }
    }

    /**
     * 检测身份信息
     * @param userName 账号
     * @return 身份信息
     * 任何人
     */
    @PostMapping("/test")
    public R<String> userTest(@RequestParam String userName){
        String status = userService.getStatusByName(userName);
        if(status == null)
            return R.err("未检验到用户信息");
        switch (status){
            case"normal": status = "普通职员";break;
            case"admin": status = "经理";break;
            case"shopowner": status="店长";break;
            case"client": status = "客户";break;
        }
        return R.success(status);
    }

    /**
     *  注册接口
     * @param user 用户类
     * @return  注册结果
     * 管理员、店长
     */
    @RequiresRoles(value={"shopowner","admin"},logical= Logical.OR)
    @PostMapping("/logon")
    public R<String> userAdd(@RequestBody User user){
        User userX = userService.getUserInfoByName(user.getUserName());
        if(userX != null)
            return R.err("用户名已存在");               //错误1
        if (userService.insertUser(user)>0)
            return R.success("" + user.getId());                   //成功1
        return R.err("注册失败");
    }

    /**
     * 注销账号
     * @param userId 用户id
     * @return 注销结果
     * 管理员、店长本店
     */
    @RequiresRoles(value={"shopowner","admin"},logical= Logical.OR)
    @DeleteMapping("/user")
    public R<String> destroyUser(@RequestParam Integer userId){
        if (userService.logoutUser(userId)>0)              //注销账号
            return R.success("删除成功");
        return R.err("删除失败");
    }

    /**
     * 修改用户密码
     * @param user 用户类
     * @return 修改结果
     * 任何人
     */
    @PutMapping("/user")
    public R<String> updateUser(@RequestBody User user){
        if (userService.updatePassword(user)>0)
            return R.success("修改成功");
        return R.err("修改失败");
    }
}
