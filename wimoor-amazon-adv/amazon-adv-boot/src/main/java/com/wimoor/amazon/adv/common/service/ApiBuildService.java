package com.wimoor.amazon.adv.common.service;

import java.util.Date;
import java.util.Map;

import com.wimoor.amazon.adv.common.pojo.AmzAdvAuth;
import com.wimoor.amazon.adv.common.pojo.AmzAdvProfile;
import com.wimoor.amazon.adv.exports.service.IAmzAdvSnapshotHandlerService;
import com.wimoor.amazon.adv.report.pojo.AmzAdvRequest;
import com.wimoor.amazon.adv.report.pojo.AmzAdvSnapshot;

public interface ApiBuildService {
	

	public String amzAdvAssetsPost(AmzAdvProfile profile ,String url, Map<String, Object> jsonparam);
	
	public String amzAdvGet_V2(AmzAdvProfile profile ,String url);
	
	public String amzAdvGet(AmzAdvProfile profile, String url,Map<String, String> map);
	
	public String amzAdvGet(AmzAdvProfile profile ,String url);
	
	public String amzAdvPost_V2(AmzAdvProfile profile ,String url, String jsonparam); 
	
	public String amzAdvPost(AmzAdvProfile profile ,String url, String jsonparam); 
	
	public String amzAdvPost(AmzAdvProfile profile, String url, String jsonparam, Map<String, String> headerExt);
	
	public String amzAdvPut_V2(AmzAdvProfile profile ,String url, String jsonparam);
	
	public String amzAdvPut(AmzAdvProfile profile ,String url, String jsonparam);
	
	public String amzAdvPut(AmzAdvProfile profile ,String url, String jsonparam, Map<String, String> headerExt);
	
	public String amzAdvDelete_V2(AmzAdvProfile profile ,String url);
	
	public String amzAdvDelete(AmzAdvProfile profile ,String url);
	
	public String amzAdvDelete(AmzAdvProfile profile ,String url, Map<String, String> headerExt);
	
	public String amzAdvDownload(AmzAdvProfile profile ,String url);
	
	public boolean amzAdvDownloadFile(AmzAdvProfile profile ,AmzAdvRequest request,IAmzAdvHttpClientResponseHandler responseHandler);
	
	public boolean amzAdvDownloadGZIP(AmzAdvProfile profile, AmzAdvSnapshot record, IAmzAdvSnapshotHandlerService handler);

	public String adzAdvInvoicesGet(AmzAdvAuth amzadvauth, AmzAdvProfile profile, String jsonparam);

	public void setBusyTime(Date date);

	public boolean isBusy();
}
