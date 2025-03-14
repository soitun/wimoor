package com.wimoor.amazon.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wimoor.amazon.api.ErpClientOneFeignManager;
import com.wimoor.amazon.auth.pojo.entity.AmazonGroup;
import com.wimoor.amazon.auth.pojo.entity.Marketplace;
import com.wimoor.amazon.auth.service.IAmazonGroupService;
import com.wimoor.amazon.auth.service.IMarketplaceService;
import com.wimoor.amazon.product.mapper.AmzProductSalesPlanShipItemMapper;
import com.wimoor.amazon.product.pojo.dto.PlanDTO;
import com.wimoor.amazon.product.pojo.dto.PlanDetailDTO;
import com.wimoor.amazon.product.pojo.entity.AmzProductSalesPlanShipItem;
import com.wimoor.amazon.product.service.IAmzProductSalesPlanShipItemService;
import com.wimoor.common.mvc.BizException;
import com.wimoor.common.result.Result;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wimoor team
 * @since 2022-12-10
 */
@Service
@RequiredArgsConstructor
public class AmzProductSalesPlanShipItemServiceImpl extends ServiceImpl<AmzProductSalesPlanShipItemMapper, AmzProductSalesPlanShipItem> implements IAmzProductSalesPlanShipItemService {
    final ErpClientOneFeignManager erpClientOneFeign;
    final IMarketplaceService iMarketplaceService;
    final IAmazonGroupService iAmazonGroupService;
	public Map<String,Object> getSummary(String shopid,String groupid,String warehouseid){
    	return this.baseMapper.getSummary(shopid, groupid,warehouseid);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getList(String shopid, String groupid, String warehouseid,String batchnumber) {
		LambdaQueryWrapper<AmzProductSalesPlanShipItem> query=new LambdaQueryWrapper<AmzProductSalesPlanShipItem>();
		query.eq(AmzProductSalesPlanShipItem::getShopid, shopid);
		query.eq(AmzProductSalesPlanShipItem::getWarehouseid, warehouseid);
		query.eq(AmzProductSalesPlanShipItem::getGroupid, groupid);
		query.gt(AmzProductSalesPlanShipItem::getAmount, 0);
		if(StrUtil.isNotBlank(batchnumber)) {
			query.eq(AmzProductSalesPlanShipItem::getBatchnumber, batchnumber);
		}
		List<AmzProductSalesPlanShipItem> list = this.baseMapper.selectList(query);
		PlanDTO dto=new PlanDTO();
		List<String> skulist=new ArrayList<String>();
		dto.setWarehouseid(warehouseid);
		dto.setShopid(shopid);
		dto.setGroupid(groupid);
		dto.setPlantype("ship");
		Map<String, Marketplace> marketMap = iMarketplaceService.findMapByMarketplaceId();
		for(AmzProductSalesPlanShipItem item:list) {
			skulist.add(item.getMsku());
		}
		try {
			dto.setMskulist(skulist);
			Map<String,Map<String,Object>> map=new HashMap<String,Map<String,Object>>();
			if(skulist.size()==0) {
				List<Map<String,Object>> resultdata=new ArrayList<Map<String,Object>>();
		    	return resultdata;
			}
		    Result<List<Map<String, Object>>> result=erpClientOneFeign.getMskuInventory(dto);
		    if(Result.isSuccess(result)&&result.getData()!=null) {
		    	List<Map<String, Object>> data = result.getData();
		    	for(Map<String, Object> item:data) {
		    		map.put(item.get("sku").toString(), item);
		    	}
		    	Map<String,Map<String, Object>> summaryMap=new HashMap<String,Map<String, Object>>();
		    	for(AmzProductSalesPlanShipItem item:list) {
					 String  key=item.getMarketplaceid();
//					 BigDecimal price=new BigDecimal("0");
					 BigDecimal boxcm3=new BigDecimal("0");
					 Integer boxnum=1;
					 BigDecimal pkgcm3=new BigDecimal("0");
					 BigDecimal pkgweight=new BigDecimal("0");
					 BigDecimal pkgvolume=new BigDecimal("0");
					 String sku=item.getSku();
					 String msku=item.getMsku();
					 Map<String, Object> mskuParam = map.get(msku);
					 if(mskuParam!=null) {
						 if(mskuParam.get("pkgcm3")!=null) {
							 pkgcm3=new BigDecimal(mskuParam.get("pkgcm3").toString());
						 }
//						 if(mskuParam.get("price")!=null) {
//							 price=new BigDecimal(mskuParam.get("price").toString());
//						 }
						 if(mskuParam.get("boxnum")!=null) {
							int num=  Integer.parseInt(mskuParam.get("boxnum").toString());
							if(num>0) {
								boxnum=num;
							}
						 }
						 if(mskuParam.get("pkgweight")!=null) {
							 pkgweight=new BigDecimal(mskuParam.get("pkgweight").toString());
						 }
						 if(mskuParam.get("pkgcm3")!=null) {
							 pkgcm3=new BigDecimal(mskuParam.get("pkgcm3").toString());
							 if(pkgcm3.floatValue()>0.0000001) {
								 pkgvolume=pkgcm3.divide(new BigDecimal(5000));
							 }
						 }
						 if(mskuParam.get("boxcm3")!=null) {
							 boxcm3=new BigDecimal(mskuParam.get("boxcm3").toString());
							 if(boxcm3.equals(new BigDecimal("0.0"))) {
								 boxcm3=pkgcm3;
							 }
						 }else {
							 boxcm3=pkgcm3;
						 }
						 mskuParam.put("boxcm3", boxcm3);
						 mskuParam.put("boxnum", boxnum);
						 mskuParam.put("pkgweight", pkgweight);
						 mskuParam.put("pkgvolume", pkgvolume);
						 mskuParam.put("pkgcm3", pkgcm3);
					 }else {
						 throw new BizException(item.getSku()+"本地SKU【"+msku+"】无法找到");
					 }
					 if(item.getTranstype()!=null) {
						 key=key+item.getTranstype();
					 }else {
						 key=key+"#";
					 }
					 if(item.getOverseaid()!=null) {
						 key=key+item.getOverseaid();
					 }else {
						 key=key+"#";
					 }
					 Map<String, Object> summary = summaryMap.get(key);
					 if(summary==null) {
						  summary = new HashMap<String,Object>();
						  summary.put("key", key);
						  summary.put("marketplaceid", item.getMarketplaceid());
						  if(item.getMarketplaceid().equals("EU")) {
							 summary.put("marketname", "FBA-EU-欧洲");
						  }else {
							  Marketplace market = marketMap.get(item.getMarketplaceid());
							  summary.put("marketname", "FBA-"+market.getMarket()+"-"+market.getName());
						  }
						  summary.put("transtype", item.getTranstype());
						  summary.put("overseaid", item.getOverseaid());
						  summaryMap.put(key, summary);
					 }
					 List<Map<String,Object>> skuMaplist=null;
					 if(summary.get("list")!=null) {
						 skuMaplist=(List<Map<String, Object>>) summary.get("list");
					 }else {
						 skuMaplist=new ArrayList<Map<String,Object>>();
						 summary.put("list",skuMaplist);
					 }
					 Map<String, Object> skumap = BeanUtil.beanToMap(item);
					 if(mskuParam!=null) {
						 skumap.putAll(mskuParam);
						 skumap.put("sku", sku);
						 skumap.put("msku", msku);
						 skumap.put("materialid",skumap.get("id"));
						 skumap.put("id", item.getId());
					 }
					 skuMaplist.add(skumap);
					 
				}
		    	List<Map<String,Object>> resultdata=new ArrayList<Map<String,Object>>();
		    	for(Entry<String, Map<String, Object>> entry:summaryMap.entrySet()) {
		    		resultdata.add(entry.getValue());
		    	}
		    	return resultdata;
		    }
		}catch(FeignException e) {
			e.printStackTrace();
          	throw new BizException(BizException.getMessage(e, "本地产品信息处理异常请联系管理员"));
		}
		return null;
	}
   public String getValue(Object o) {
	   if(o==null)return null;
	   else return o.toString();
   }
   public Workbook downloadList(List<Map<String, Object>> list) {
	    Workbook workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow(0);
		Cell cell=row.createCell(0);
		cell.setCellValue("店铺");
		cell=row.createCell(1);
		cell.setCellValue("站点");
		cell=row.createCell(2);
		cell.setCellValue("平台SKU");
		cell=row.createCell(3);
		cell.setCellValue("本地仓库");
		cell=row.createCell(4);
		cell.setCellValue("本地SKU");
		cell=row.createCell(5);
		cell.setCellValue("名称");
		cell=row.createCell(6);
		cell.setCellValue("采购成本");
		cell=row.createCell(7);
		cell.setCellValue("本地库存");
		cell=row.createCell(8);
		cell.setCellValue("产品附加费");
		cell=row.createCell(9);
		cell.setCellValue("产品材质");
		cell=row.createCell(10);
		cell.setCellValue("实际发货总量");
		cell=row.createCell(11);
		cell.setCellValue("本地上架库存");
		cell=row.createCell(12);
		cell.setCellValue("FBA可用库存");
		cell=row.createCell(13);
		cell.setCellValue("FBA待入库库存");
		cell=row.createCell(14);
		cell.setCellValue("备货周期");
		cell=row.createCell(15);
		cell.setCellValue("计划备注");
		AmazonGroup group =null;
		Marketplace market = null;
		for(int i=0;list!=null&&i<list.size();i++) {
			Map<String, Object> item = list.get(i);
			  row = sheet.createRow(i + 1);
			  cell = row.createCell(0); // 在索引0的位置创建单元格(左上端) {100-9.9},{500-9.5}
			  Object groupid=item.get("groupid");
			  if(groupid!=null&&group==null) {
				  group = iAmazonGroupService.getById(groupid.toString());
				  cell.setCellValue(group.getName());
			  }else if(group!=null){
				  cell.setCellValue(group.getName());
			  }
			  cell = row.createCell(1);
			  Object marketplaceid= item.get("marketplaceid");
			  if(marketplaceid!=null&&market==null) {
				  market = this.iMarketplaceService.getById(marketplaceid.toString());
				  cell.setCellValue(market.getName());
			  }else if(market!=null) {
				  cell.setCellValue(market.getName());
			  }
			  cell = row.createCell(2);
			  cell.setCellValue(getValue(item.get("sku")));
			  cell = row.createCell(3);
			  cell.setCellValue(getValue(item.get("warehousename")));
			  cell = row.createCell(4);
			  cell.setCellValue(getValue(item.get("msku")));
			  cell = row.createCell(5);
			  cell.setCellValue(getValue(item.get("name")));
			  cell = row.createCell(6);
			  cell.setCellValue(getValue(item.get("price")));
			  cell = row.createCell(7);
			  cell.setCellValue(getValue(item.get("quantity")));
			  cell = row.createCell(8);
			  cell.setCellValue(getValue(item.get("addfee")));
			  cell = row.createCell(9);
			  cell.setCellValue(getValue(item.get("material")));
			  cell = row.createCell(10);
			  cell.setCellValue(getValue(item.get("amount")));
			  cell = row.createCell(11);
			  cell.setCellValue(getValue(item.get("quantityShelf")));
			  cell = row.createCell(12);
			  cell.setCellValue(getValue(item.get("fulfillable")));
			  cell = row.createCell(13);
			  cell.setCellValue(getValue(item.get("inbound")));
			  cell = row.createCell(14);
			  cell.setCellValue(getValue(item.get("delivery_cycle")));
			  cell = row.createCell(15);
			  cell.setCellValue(getValue(item.get("notice")));
		}
		return workbook;
   }
  public int updateBatch( String id, String batchnumber) {
	  return this.baseMapper.updateBatch(id, batchnumber);
  }
  public int moveBatch(String shopid,String batchnumber) {
	  return this.baseMapper.moveBatch(shopid,batchnumber);
  }
  
  public List<Map<String, Object>> getPlanedItem(PlanDTO dto) {
	return   this.baseMapper.getPlanedItem(dto);
  }
  
  public List<Map<String,Object>> hasplanItem(PlanDetailDTO dto){
	  return this.baseMapper.hasplanItem(dto);
  }
  
  public List<Map<String,Object>> hasplanItemEu(PlanDetailDTO dto){
	  return this.baseMapper.hasplanItemEu(dto);
  }
 
}
