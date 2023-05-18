
package top.cloudtour.cloudstudy.base.execption;

/**
 * @description 通用错误信息
 * @author cloudtour
 * @date 2023/3/17 23:29
 * @version 1.0
 */
public enum CommonError {

	UNKOWN_ERROR("执行过程异常，请重试。"),
	PARAMS_ERROR("非法参数"),
	OBJECT_NULL("对象为空"),
	QUERY_NULL("查询结果为空"),
	REQUEST_NULL("请求参数为空");

	private String errMessage;

	public String getErrMessage() {
		return errMessage;
	}

	private CommonError( String errMessage) {
		this.errMessage = errMessage;
	}

}
