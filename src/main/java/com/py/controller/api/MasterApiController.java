package com.py.controller.api;

import com.py.bean.Demand;
import com.py.bean.Master;
import com.py.bean.MasterCertification;
import com.py.service.DemandService;
import com.py.service.MasterService;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api")
@RestController
public class MasterApiController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private DemandService demandService;


    /**
     * 修改上下班的状态
     * @return
     */
    @RequestMapping(value = "/officeStutus",method = RequestMethod.POST)
    public Msg officeStutus(HttpServletRequest request){

        String masterId = request.getParameter("masterId");
        Master m = masterService.selectByPrimaryKey(Integer.parseInt(masterId));

        if (m.getStatus() != 1){
            return Msg.fail().add("msg","请先进行认证!");
        }
        List<MasterCertification> certificationList = masterService.selectCertificationByMasterId(m.getId());
        if (certificationList.size() == 0 || certificationList == null){
            return Msg.fail().add("msg","请先入驻");
        }
        if (m.getOfficeType()  ==  0){
            m.setOfficeType(1);
        }else{
            m.setOfficeType(0);
        }
        masterService.update(m);
        return Msg.success();
    }



    /**
     * 师傅这边的需求
     */
    @RequestMapping(value = "/getMasterNeedByMajorClassId",method = RequestMethod.POST)
    public Msg masterDemand(HttpServletRequest request){
        String majorClassId = request.getParameter("majorClassId");

        Demand demand = new Demand();
        demand.setSmallClass(Integer.parseInt(majorClassId));
        demand.setType(2);
        Map<String, Object> list = demandService.selectByMasterDemandExample(demand);
        return Msg.success().add("map",list);
    }











}
