package net.mingsoft.cms.action.search.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class FilterParam {
	private String name;
	private String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String filter="";
		if(this.name.equals("idx_parent_id")){
			String idx_parent_id = this.value.replaceAll("\\|\\|"," OR idx_parent_id:"); 
			filter="(idx_parent_id:"+idx_parent_id+")";
		}else{
			filter=this.name + ":" + this.value;
		}
		
		if(this.name.equals("idx_date")){
			String[]  dates=this.value.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date start=sdfs.parse(dates[0]);
				Date endDate=sdfs.parse(dates[1]);
				
				filter="("+this.name + ":" + "["+sdf.format(start)+" TO "+sdf.format(endDate)+"])";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return filter;
	}
}
