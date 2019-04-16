package response;

/**
 * Created by sdbus on 2018-7-25.
 */
public enum ResultEnum {
    UNKNOW_ERROE(-2, "未知错误"),
    RUNTIME_ERROR(-1, "运行错误"),
    SUCCESS(0, "成功"),
    UNAUTHENIICATED_ERROR(2, "无权限"),
    UNLOGIN_ERROR(401, "未登录"),
    LOGIN_FAULURE(401, "登陆失败"),
    TOKEN_INVALID_ERROR(401, "登陆凭证失效"),
    UNKNOWACCOUNT_ERROR(101, "用户不存在"),
    INCORRECTCREDENTIALS_ERROR(102, "密码错误"),
    ACCOUNTDELETED_ERROR(103, "账号已失效"),
    REPEATACCOUNT_ERROR(105, "账号已存在"),
    VALID_ERROR(201, "验证出错"),
    PAY_ERROR(301, "支付失败"),
    REFUND_ERROR(302, "退款失败"),
    QUERY_ERROR(501, "查询出错")

    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
