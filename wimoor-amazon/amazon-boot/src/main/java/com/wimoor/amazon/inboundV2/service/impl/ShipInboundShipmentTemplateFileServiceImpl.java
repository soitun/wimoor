package com.wimoor.amazon.inboundV2.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wimoor.amazon.inboundV2.mapper.ShipInboundShipmentTemplateFileMapper;
import com.wimoor.amazon.inboundV2.pojo.entity.ShipInboundShipmentTemplateFile;
import com.wimoor.amazon.inboundV2.service.IShipInboundShipmentTemplateFileService;
import com.wimoor.common.pojo.entity.Picture;
import com.wimoor.common.service.IPictureService;
import com.wimoor.common.service.impl.PictureServiceImpl;
import com.wimoor.common.service.impl.StorageService;
import com.wimoor.common.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("shipInboundShipmentTemplateFileService")
@RequiredArgsConstructor
public class ShipInboundShipmentTemplateFileServiceImpl extends ServiceImpl<ShipInboundShipmentTemplateFileMapper, ShipInboundShipmentTemplateFile> implements IShipInboundShipmentTemplateFileService {
	
	final ShipInboundShipmentTemplateFileMapper shipInboundShipmentTemplateFileMapper;
	final IPictureService pictureService;
	final StorageService storageService;
	

	@Override
	public List<Map<String, Object>> getShipmentCustomsRecord(String shopid) {
		List<Map<String,Object>> list = shipInboundShipmentTemplateFileMapper.selectCustomsRecordByShop(shopid);
		return list;
	}
	
	
	public Map<String,Object> uploadShipmentcustomsFile(UserInfo user, InputStream inputStream, String filename) {
		Map<String,Object> map=new HashedMap<String, Object>();
		String shopid=user.getCompanyid();
		try {
			ShipInboundShipmentTemplateFile shipcustoms = shipInboundShipmentTemplateFileMapper.findCustomsByNameAndShopid(shopid,filename);
			String filePath = PictureServiceImpl.customsImgPath + shopid;
			String iamge=null;
			if(shipcustoms!=null && shipcustoms.getFilepath()!=null) {
				iamge=shipcustoms.getFilepath();
			}
			Picture picture = pictureService.uploadPicture(inputStream, null, filePath, filename, iamge);
			if(picture!=null) {
				if(shipcustoms!=null) {
					shipcustoms.setFilepath(picture.getId());
					shipcustoms.setFilename(filename);
					shipcustoms.setOperator(new BigInteger(user.getId()));
					shipcustoms.setOpttime(new Date());
					shipInboundShipmentTemplateFileMapper.updateById(shipcustoms);
				}else {
					shipcustoms = new ShipInboundShipmentTemplateFile();
					shipcustoms.setCreatedate(new Date());
					shipcustoms.setCreator(new BigInteger(user.getId()));
					shipcustoms.setFilepath(picture.getId());
					shipcustoms.setFilename(filename);
					shipcustoms.setOperator(new BigInteger(user.getId()));
					shipcustoms.setOpttime(new Date());
					shipcustoms.setShopid(new BigInteger(shopid));
					shipInboundShipmentTemplateFileMapper.insert(shipcustoms);
				}
				map.put("msg", "文件上传成功！");
				map.put("code", "ok");
			}else{
				map.put("msg", "文件上传失败！请重试！");
				map.put("code", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteCustomsFile(String uploadid) {
		Map<String, Object> maps=new HashedMap<String, Object>();
		ShipInboundShipmentTemplateFile shipfile = shipInboundShipmentTemplateFileMapper.selectById(uploadid);
		if(shipfile!=null && StrUtil.isNotEmpty(shipfile.getFilepath())) {
			try {
				Picture picture = pictureService.getById(shipfile.getFilepath());
				if(picture!=null && picture.getLocation()!=null) {
					storageService.removeObject(storageService.getBucketName(), picture.getLocation());
					shipInboundShipmentTemplateFileMapper.deleteById(shipfile);
					maps.put("code", "ok");
					maps.put("msg", "删除成功！");
				}else {
					maps.put("code", "fail");
					maps.put("msg", "删除失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			maps.put("code", "fail");
			maps.put("msg", "删除失败！");
		}
		return maps;
	}

}
