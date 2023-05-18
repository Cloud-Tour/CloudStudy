

package top.cloudtour.cloudstudy.base.execption;


/**
 * @description 云端易学项目异常类
 * @author cloudtour
 * @date 2023/3/17 23:29
 * @version 1.0
 */
public class CloudStudyException extends RuntimeException {

	private static final long serialVersionUID = 5565760508056698922L;

	private String errMessage;

	public CloudStudyException() {
		super();
	}

	public CloudStudyException(String errMessage) {
		super(errMessage);
		this.errMessage = errMessage;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public static void cast(CommonError commonError){
		 throw new CloudStudyException(commonError.getErrMessage());
	}
	public static void cast(String errMessage){
		 throw new CloudStudyException(errMessage);
	}

}
