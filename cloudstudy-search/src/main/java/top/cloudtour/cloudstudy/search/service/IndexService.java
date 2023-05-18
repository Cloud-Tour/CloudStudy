package top.cloudtour.cloudstudy.search.service;

/**
 * @author cloudtour
 * @version 1.0
 * @description 课程索引service
 * @date 2023/3/27 19:33
 */
public interface IndexService {

    /**
     * @param indexName 索引名称
     * @param id 主键
     * @param object 索引对象
     * @return Boolean true表示成功,false失败
     * @description 添加索引
     * @author cloudtour
     * @date 2023/3/27 20:23
     */
    public Boolean addCourseIndex(String indexName,String id,Object object);


    /**
     * @description 更新索引
     * @param indexName 索引名称
     * @param id 主键
     * @param object 索引对象
     * @return Boolean true表示成功,false失败
     * @author cloudtour
     * @date 2023/3/27 21:43
    */
    public Boolean updateCourseIndex(String indexName,String id,Object object);

    /**
     * @description 删除索引
     * @param indexName 索引名称
     * @param id  主键
     * @return java.lang.Boolean
     * @author cloudtour
     * @date 2023/3/27 19:55
    */
    public Boolean deleteCourseIndex(String indexName,String id);

}
