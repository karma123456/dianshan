package com.web.service.impl;

import com.web.dao.ImagesDaoMapper;
import com.web.dbuitl.ImagesDao;
import com.web.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesDaoMapper imagesDaoMapper;

    @Override
    public ImagesDao insertSelective(ImagesDao imagesDao) {
        imagesDaoMapper.insertSelective(imagesDao);
        return this.selectId(imagesDao.getId());
    }

    public ImagesDao selectId(Integer id){
        ImagesDao imagesDao = imagesDaoMapper.selectByPrimaryKey(id);
        if(imagesDao == null){
            return null;
        }
        return imagesDao;
    }

    @Override
    public List<ImagesDao> list() {
        List<ImagesDao> imagesDaoList = imagesDaoMapper.list();
        return imagesDaoList;
    }


}
