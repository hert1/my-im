package com.im.service.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author viruser
 * @create 2019/1/17
 * @since 1.0.0
 */
@Component
public class SolrConfig {

    private String solrHost = "http://localhost:8983/solr/blog";
    ;
    public final SolrClient server = new HttpSolrClient.Builder(solrHost).build();

    /**
     * 如果page和pageSize都为-1的话默认搜索全部
     *
     * @param key
     * @param clazz
     * @param page
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> List<T> query(String key, Class<T> clazz, int page, int pageSize) {

        SolrQuery query = new SolrQuery();
        if (page != -1 && pageSize != -1) {
            query.setStart(page);
            query.setRows(pageSize);
        }
        query.setQuery(key);
        query.set("df", "keywords");
        QueryResponse resp = null;
        List<T> convent = null;
        try {
            resp = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = resp.getResults();
        try {

            convent = convent(results, clazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return convent;
    }


    private <T> List<T> convent(SolrDocumentList results, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Iterator<SolrDocument> iterator = results.iterator();
        List<T> list = new LinkedList();
        while (iterator.hasNext()) {
            T obj = clazz.newInstance();
            SolrDocument next = iterator.next();
            Collection<String> fieldNames = next.getFieldNames();
            for (String fieldName : fieldNames) {
                Object value = next.get(fieldName);
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    if (name.equals(fieldName)) {
                        declaredField.setAccessible(true);
                        if ("id".equals(name)) {
                            declaredField.set(obj, value);
                        } else {
                            if (value instanceof List) {
                                declaredField.set(obj, ((List) value).get(0));
                            }
                            if (value instanceof String) {
                                declaredField.set(obj, value);
                            }

                        }
                    }
                }
            }
            list.add(obj);
        }
        return list;

    }


}
