package com.cubicpark.mechanic.domain.dto.design;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Auther: WZBSDB
 * @Date: 2018/8/31 16:27
 * @Description:
 */

@Data
public class DesignDTO {

    private Integer id;

    private String designCode;

    private String fileName;

    private String drawingNumber;

    private String material;

    private String technicalRequirement;

    private Integer picId;

    private Integer status;

    private DesignPic designPic;

    private List<DesignReview> designReviewList;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
