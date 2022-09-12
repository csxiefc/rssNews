package net.mingsoft.cms.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import net.mingsoft.base.biz.impl.BaseBizImpl;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.cms.biz.IRssItemBiz;
import net.mingsoft.cms.dao.IRssItemDao;
import net.mingsoft.cms.entity.RssItemEntity;
import net.mingsoft.cms.entity.RssSeedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * RSS对象行记录 服务实现类
 * </p>
 *
 * @author xiefc
 * @since 2022-08-27
 */
@Slf4j
@Service("cmsRssItemBizImpl")
public class RssItemBizImpl  extends BaseBizImpl<IRssItemDao, RssItemEntity> implements IRssItemBiz {

    @Autowired
    private IRssItemDao dao;


    @Override
    protected IBaseDao getDao() {
        return dao;
    }

    @Override
    public boolean isExist(String seedId, String title, Date pubDate) {
        QueryWrapper query = new QueryWrapper(new RssItemEntity().setSeedId(seedId).setTitle(title).setPubdate(pubDate));
        int count = this.dao.selectCount(query);
        return count > 0 ? true :false;
    }

    @Override
    public boolean isExist(String url) {
        QueryWrapper query = new QueryWrapper(new RssItemEntity().setLink(url));
        int count = this.dao.selectCount(query);
        return count > 0 ? true :false;
    }

    public int convertBySyndEntry(String batchNo, RssSeedEntity seedObj, List<SyndEntry> syndList) {
        int result = 0;
        log.info("syndList.size()="+syndList.size());
        for(int s=0;s<syndList.size();s++) {
            try {
                int index = (s+1);
                log.info("SyndEntry Execute index ="+index);
                SyndEntry syndObj = syndList.get(s);
                // 判断URL是否已经存在
                if(this.isExist(syndObj.getLink())) {
                    log.info("exist url:"+syndObj.getLink());
                    continue;
                }
                // 记录RSS行数据
                RssItemEntity newItem = new RssItemEntity();
                newItem.setSiteId(seedObj.getSiteId());
                newItem.setSeedId(seedObj.getId());
                newItem.setCategoryId(seedObj.getCategoryId());
                newItem.setCategoryName(seedObj.getCategoryName());
                newItem.setBatchNo(batchNo);
                newItem.setBizId("");
                newItem.setTitle(syndObj.getTitle().trim().length() > 250 ? syndObj.getTitle().trim().substring(0,250) : syndObj.getTitle().trim());
                newItem.setLink(syndObj.getLink() != null ? syndObj.getLink().trim() : null);
                newItem.setAuthor(syndObj.getAuthor() != null ? syndObj.getAuthor().trim() : null);
                newItem.setDescription(syndObj.getDescription().getValue() != null ? syndObj.getDescription().getValue().trim() : null);
                newItem.setPubdate(syndObj.getPublishedDate());
                // newItem.setFromJson(JSON.toJSONString(syndObj));
                newItem.setIsBuild("N");
                newItem.setIsIndex("N");
                newItem.setCreateBy("sys");
                newItem.setCreateDate(new Date());
                this.save(newItem);
                result++;
            } catch(Exception e) {
                log.error("convertBySyndEntry异常",e);
            }
        }
        return result;
    }

    @Override
    public List<RssItemEntity> getData(String categoryId, Date pubDate, int limit) {
        QueryWrapper query = new QueryWrapper(new RssItemEntity());
        query.eq("del",0);
        query.eq("categoryId",categoryId);
        if(pubDate != null) {
            query.gt("pubdate", pubDate); // DateUtil.stringToDate(pubDate,"yyyy-MM-dd HH:mm:ss")
        }
        query.orderByDesc("pubdate");
        if(limit > 0) {
            query.last("limit "+limit);
        }
        List<RssItemEntity> result = this.list(query);
        return result;
    }
}
