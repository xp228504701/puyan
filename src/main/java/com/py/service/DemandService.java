package com.py.service;

import com.py.bean.Demand;
import com.py.bean.DemandItems;
import com.py.bean.SmallClass;
import com.py.dao.CertificationMapper;
import com.py.dao.DemandMapper;
import com.py.dao.SmallClassMapper;
import com.py.dao.UserNeedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandService {
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private UserNeedMapper userNeedMapper;
    @Autowired
    private CertificationMapper certificationMapper;
    @Autowired
    private SmallClassMapper smallClassMapper;



    public  Map<Integer, List<Demand>> selectByuserDemandExample(Demand demand) {

        List<Demand> list = demandMapper.selectByExample(demand);
        Map<Integer, List<Demand>> map = new HashMap<>();

        List<DemandItems> radioItems = null;
        List<DemandItems> selectionItems = null;

        int a = 0;
        for (Demand d: list ) {
            a = 0;
            if (d.getLabel() == 2){
                radioItems = new ArrayList<>();
                String[] strings = d.getRemark().split(",");
                for (String name:strings ) {
                    a++;
                    DemandItems dd = new DemandItems();
                    dd.setId(d.getLabelId());
                    dd.setName(d.getLabelName());
                    if(a == 1){
                        dd.setChecked(true);
                    }else{
                        dd.setChecked(false);
                    }
                    dd.setValues(name);
                    radioItems.add(dd);
                }
                d.setRadioItems(radioItems);
            }

            if (d.getLabel() == 3){
                selectionItems = new ArrayList<>();
                String[] strings = d.getRemark().split(",");

                for (String name:strings ) {
                    DemandItems dd = new DemandItems();
                    dd.setId(d.getLabelId());
                    dd.setName(d.getLabelName());
                    dd.setValues(name);
                    dd.setSelected(false);
                    selectionItems.add(dd);
                }
                d.setSelectionItems(selectionItems);
            }


            if(map.get(d.getLabel()) == null){
                List<Demand> deList = new ArrayList<>();

                deList.add(d);
                map.put(d.getLabel(),deList);
            }else{
                List<Demand> deList = map.get(d.getLabel());
                deList.add(d);
                map.put(d.getLabel(),deList);
            }
        }

        return  map;
    }

    public int selectDemandName(String name) {
        return demandMapper.selectDemandName(name);
    }

    public void saveDemand(Demand sc) {
        demandMapper.insertSelective(sc);
    }

    public void updateDemand(Demand sc) {
        demandMapper.updateByPrimaryKeySelective(sc);
    }

    public void deleteDemandByid(int id) {
         demandMapper.deleteByPrimaryKey(id);
    }

    public int selectDemandLabelId(String labelId) {
        return demandMapper.selectDemandLabelId(labelId);
    }

    public Demand selectByPrimaryKey(int id) {
        return demandMapper.selectByPrimaryKey(id);
    }


    public List<Demand> selectByExample(Demand demand) {
        return demandMapper.selectByExample(demand);
    }


    public Map<String, Object> selectByMasterDemandExample(Demand demand) {

        List<Demand> list = demandMapper.selectByExample(demand);
        Map<String, Object> map = new HashMap<>();

        List<DemandItems> radioItems = null;
        List<DemandItems> selectionItems = null;

        int a = 0;
        for (Demand d: list ) {
            a = 0;
            if (d.getLabel() == 2){
                radioItems = new ArrayList<>();
                String[] strings = d.getRemark().split(",");
                for (String name:strings ) {
                    a++;
                    DemandItems dd = new DemandItems();
                    dd.setId(d.getLabelId());
                    dd.setName(d.getLabelName());
                    if(a == 1){
                        dd.setChecked(true);
                    }else{
                        dd.setChecked(false);
                    }
                    dd.setValues(name);
                    radioItems.add(dd);
                }
                d.setRadioItems(radioItems);
            }

            if (d.getLabel() == 3){
                selectionItems = new ArrayList<>();
                String[] strings = d.getRemark().split(",");

                for (String name:strings ) {
                    DemandItems dd = new DemandItems();
                    dd.setId(d.getLabelId());
                    dd.setName(d.getLabelName());
                    dd.setValues(name);
                    dd.setSelected(false);
                    selectionItems.add(dd);
                }
                d.setSelectionItems(selectionItems);
            }


            if(map.get(String.valueOf(d.getLabel())) == null){
                List<Demand> deList = new ArrayList<>();

                deList.add(d);
                map.put(String.valueOf(d.getLabel()),deList);
            }else{
                List<Demand> deList = (List<Demand>) map.get(String.valueOf(d.getLabel()));
                deList.add(d);
                map.put(String.valueOf(d.getLabel()),deList);
            }
        }
        List<SmallClass> smallClasses = smallClassMapper.selectSmallClassByMajorId(demand.getSmallClass());
        map.put("smallClasses",smallClasses);
        return  map;
    }







}
