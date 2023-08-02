package com.lsu.service.impl.sign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.SignInForm;
import com.lsu.mapper.sign.SignInFormMapper;
import com.lsu.service.SignInFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【sign_in_form】的数据库操作Service实现
* @createDate 2023-03-17 20:24:10
*/
@Service
public class SignInFormServiceImpl extends ServiceImpl<SignInFormMapper, SignInForm>
    implements SignInFormService{

    @Resource
    private SignInFormMapper signInFormMapper;

    /**
     * 更新签到信息
     * @param signInForm 签到信息
     */
    public Integer updateSign(SignInForm signInForm){
        return signInFormMapper.updateById(signInForm);
    }

    /**
     * 添加或更新签到信息
     * @param signInForm 签到信息
     * @return
     */
    @Override
    public Boolean insertOrUpdateSign(SignInForm signInForm) {
        QueryWrapper<SignInForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id",signInForm.getStaffId());
        queryWrapper.eq("schedule_id",signInForm.getScheduleId());
        return this.saveOrUpdate(signInForm,queryWrapper);
    }

    /**
     * 未存在则添加
     * @param signInForm 签到信息
     * @return
     */
    @Override
    public void insertIfNull(SignInForm signInForm) {
        QueryWrapper<SignInForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id",signInForm.getStaffId());
        queryWrapper.eq("schedule_id",signInForm.getScheduleId());
        if (signInFormMapper.selectOne(queryWrapper)==null)
            signInFormMapper.insert(signInForm);
    }

}




