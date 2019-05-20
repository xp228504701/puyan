package com.py.service;

import com.py.bean.Banner;
import com.py.dao.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;


    public List<Banner> selectUserFirstLevelBanner() {

        Banner b = new Banner();
        b.setType("1");
        //b.setLevel("1");
        return bannerMapper.selectByExample(b);
    }

    public Banner selectUserTwoLevelBanner() {
        Banner b = new Banner();
        b.setType("1");
        b.setLevel("2");
        return bannerMapper.selectByPrimary(b);
    }

    public List<Banner> selectMasterLevelBanner() {

        Banner b = new Banner();
        b.setType("2");
        b.setLevel("1");

        return bannerMapper.selectByExample(b);
    }

    public Banner selectByPrimaryKey(Integer id) {
        return bannerMapper.selectByPrimaryKey(id);
    }

    public void saveBanner(Banner banner) {
       bannerMapper.insertSelective(banner);
    }

    public void update(Banner banner) {
        bannerMapper.updateByPrimaryKeySelective(banner);
    }

    public void deleteBannerByid(int id) {
        bannerMapper.deleteByPrimaryKey(id);
    }





    /****************   H5页面的banner  *************************/



    public List<Banner> selectH5Banner() {

        Banner b = new Banner();
        b.setType("3");
        return bannerMapper.selectByExample(b);

    }
}
