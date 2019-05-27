package com.web.controller;

import com.web.controller.viewobject.ItemVO;
import com.web.error.BusinessException;
import com.web.error.EmBusinessError;
import com.web.response.CommonReturnType;
import com.web.service.ItemService;
import com.web.service.model.ItemModel;
import com.web.utils.UploadUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;
    //创建商品
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name="title")String title,
                                       @RequestParam(name="desciption")String desciption,
                                       @RequestParam(name="price")BigDecimal price,
                                       @RequestParam(name="stock")Integer stock,
                                       @RequestParam(name="imgurl")String imgurl,
                                       @RequestParam(name="sales")Integer sales) throws BusinessException {
        //封住service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDesciption(desciption);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImage(imgurl);
        itemModel.setSales(sales);

        ItemModel itemModelForReturn = itemService.creatItem(itemModel);
        //返回前端
        ItemVO itemVO = convertVOFormModel(itemModelForReturn);
        return CommonReturnType.creat(itemVO);
    }

    //商品详情页浏览
    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name="id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFormModel(itemModel);
        return CommonReturnType.creat(itemVO);
    }

    //商品列表项浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();
        //使用stream api将list内的itemmodel转化为itemVO
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFormModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.creat(itemVOList);
    }
    //删除商品
    @RequestMapping(value = "/delete",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType deleteItem(@RequestParam(name = "id") Integer id){
        itemService.delete(id);
        return CommonReturnType.creat(null);
    }
//更新商品
    @RequestMapping(value = "/update",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType updateItem(@RequestParam(name = "id")Integer id,
                                       @RequestParam(name="title")String title,
                                       @RequestParam(name="desciption")String desciption,
                                       @RequestParam(name="price")BigDecimal price,
                                       @RequestParam(name="stock")Integer stock,
                                       @RequestParam(name="imgurl")String imgurl,
                                       @RequestParam(name="sales")Integer sales) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setId(id);
        itemModel.setTitle(title);
        itemModel.setDesciption(desciption);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImage(imgurl);
        itemModel.setSales(sales);
        ItemModel itemModel1 = itemService.update(itemModel);

        ItemVO itemVO = this.convertVOFormModel(itemModel1);

        return CommonReturnType.creat(itemVO);
    }


    private ItemVO convertVOFormModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);

        //(秒杀活动用)
        if(itemModel.getPromoModel() != null){
            //有正在进行的或即将进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }

    //图片上传
    @RequestMapping(value = "/upfile")
    @ResponseBody
    public CommonReturnType upfile(HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessException {
        Map<String,Object> resultes = new HashMap<String,Object>();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        //判断文件是否为空
        if(file.isEmpty()){
            return null;
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        // 存放上传图片的文件夹
        File fileDir = UploadUtils.getImgDirFile();
        // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
        //System.out.println(fileDir.getAbsolutePath());
        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
          //  System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -> “绝对路径”
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"上传失败");
        }
        String url="http:/localhost:8090/images/"+fileName;
       return CommonReturnType.creat(url);
    }
}
