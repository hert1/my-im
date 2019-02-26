package com.im.web.bean;

import com.im.api.dto.article.DateAndNumBean;
import lombok.Data;

import java.util.List;

/**
 * @author viruser
 * @create 2019/2/25
 * @since 1.0.0
 */
@Data
public class ArchivesResp {

    private Long num;
    private Long year;
    private List<DateAndNumBean> mons;
}

