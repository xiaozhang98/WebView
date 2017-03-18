package webview.xiaozhang.com.webview.bean;

/**
 * Created by Administrator on 2017/3/15.
 */

public class NetBean {
    /**
     * reason : 应用未审核，请提交认证
     * result : null
     * error_code : 10005
     */

    private String reason;
    private Object result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
