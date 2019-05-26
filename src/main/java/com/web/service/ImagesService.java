package com.web.service;

import com.web.dbuitl.ImagesDao;

import java.util.List;

public interface ImagesService {
    ImagesDao insertSelective(ImagesDao imagesDao);
    List<ImagesDao> list();
}
