package top.cloudtour.cloudstudy.search.dto;


import lombok.Data;
import lombok.ToString;
import top.cloudtour.cloudstudy.base.model.PageResult;

import java.util.List;

/**
 * @author cloudtour
 * @version 1.0
 * @description TODO
 * @date 2023/3/27 18:44
 */
@Data
@ToString
public class SearchPageResultDto<T> extends PageResult {

    //大分类列表
    List<String> mtList;
    //小分类列表
    List<String> stList;

    public SearchPageResultDto(List<T> items, long counts, long page, long pageSize) {
        super(items, counts, page, pageSize);
    }

}
