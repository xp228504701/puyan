package com.py.service;

import com.py.bean.Master;
import com.py.bean.MasterCertification;
import com.py.bean.MasterQuote;
import com.py.dao.MasterCertificationMapper;
import com.py.dao.MasterMapper;
import com.py.dao.MasterQuoteMapper;
import com.py.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MasterService {
    @Autowired
    private MasterMapper masterMapper;
    @Autowired
    private MasterQuoteMapper masterQuoteMapper;
    @Autowired
    private MasterCertificationMapper certificationMapper;




    public List<Master> selectByExample(Master master) {
        return masterMapper.selectByExample(master);
    }

    public Master selectByPrimary(Master master) {
        return masterMapper.selectByPrimary(master);
    }

    public void update(Master master) {
        masterMapper.updateByPrimaryKeySelective(master);
    }

    public void deleteMasterByid(int id) {
        masterMapper.deleteByPrimaryKey(id);
    }








    /*******************  师傅接单  *********************/


    public void insertMasterQuote(MasterQuote mq) {
        masterQuoteMapper.insertSelective(mq);
    }


    /**
     * 找回密码
     * @param phonenum
     * @param password
     * @return
     */
    public String retrievePassword(String phonenum,String password) {
        Master master = new Master();
        master.setAccount(phonenum);
        Master findMaster = masterMapper.selectByPrimary(master);
        if(findMaster == null) {
            return "该号码未注册";
        }else {
            findMaster.setPassword(MD5.string2MD5(password));
        }
        masterMapper.updateByPrimaryKeySelective(findMaster);
        return null;
    }

    /**
     * 登录
     * @param phonenum
     * @param password
     * @return
     */
    public Msg login(String phonenum, String password) {
        String type = null;//登录类型
        Integer id = null;//登录者id
        SMSBean smsBean = (SMSBean) CommonUtil.MSG_MAP.get(phonenum);
        if(smsBean != null) {
            CommonUtil.MSG_MAP.remove(phonenum);
        }
        Master master = new Master();
        master.setAccount(phonenum);
        Master master1 = masterMapper.selectByPrimary(master);
        if(master1==null){
            return Msg.fail().add("msg", "账号不存在");
        }
        if(!MD5.string2MD5(password).equals(master1.getPassword())){
            return Msg.fail().add("msg", "密码不正确");
        }
        SMSBean bean = new SMSBean(id,phonenum, UUIDUtils.getUUID(),type,null);
        CommonUtil.MSG_MAP.put(phonenum, bean);

        //写入token
        master.setToken(bean.getValue());
        masterMapper.updateByPrimaryKey(master);

        Msg msg = Msg.success();
        msg.add("token", bean.getValue());
        msg.add("masterName", master1.getName());       //师傅名称
        msg.add("masterId", master1.getId());           //师傅id
        msg.add("masterAvatar", master1.getAvatar());   //师傅头像
        msg.add("officeType", master1.getOfficeType()); //工作状态  0:下班 1:上班
        msg.add("phonenum", phonenum);                  //手机号
        msg.add("page", type);
        return msg;
    }

    /*
     *//**
     * 注册
     * @param master
     * @return
     */
    public int register(Master master) {
		/*User findUser = userMapper.selectByPrimary(user);
		if(findUser != null) {
			return "该号码已注册过";
		}*/
        master.setTime(new Date());
        return masterMapper.insertSelective(master);
    }




    //查找数据里面有没有存在的账号
    public int selectMasterCount(String phonenum) {
        return masterMapper.selectMasterCount(phonenum);
    }

    public Master selectByPrimaryKey(Integer id) {
        return masterMapper.selectByPrimaryKey(id);
    }

    public void updateByPrimaryKeySelective(Master findMaster) {
        masterMapper.updateByPrimaryKeySelective(findMaster);
    }











    /*****************  师傅需求关联  ***********************/

    public List<MasterCertification> selectCertificationByMasterId(Integer masterId) {
        return certificationMapper.selectByMasterId(masterId);
    }





}
