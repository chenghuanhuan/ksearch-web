package la.kaike.ksearch.home.base;

import com.baomidou.mybatisplus.plugins.Page;
import la.kaike.ksearch.model.Response;
import la.kaike.ksearch.model.page.PageInfoBT;
import la.kaike.ksearch.util.support.HttpKit;
import la.kaike.ksearch.util.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class BaseController {

    private static final Logger logger       = LoggerFactory.getLogger(BaseController.class);


    private static final String SYSTEM_ERROR = "系统异常";

    private static final String SUCCESS_MSG  = "处理成功";

    private static final String FAILED_MSG   = "处理失败";

    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }

    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 返回前台文件流
     *
     * @author chenghuanhuan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
        byte[] bytes = FileUtil.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }

    /**
     * 返回前台文件流
     *
     * @author chenghuanhuan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }



    /**
     * @return
     * @date: 2016年8月16日 下午4:22:06
     */
    public static Response succeed() {
        return getResponseData(true, null, SUCCESS_MSG);
    }

    /**
     * @param returnData
     * @return
     * @date: 2016年8月16日 下午4:22:09
     */
    public static Response succeed(Object returnData) {
        return getResponseData(true, returnData, SUCCESS_MSG);
    }

    /**
     * @return
     * @date: 2016年8月16日 下午4:22:11
     */
    public static Response failed() {
        return getResponseData(false, null, FAILED_MSG);
    }

    /**
     * @param msg
     * @return
     * @date: 2016年8月16日 下午4:22:14
     */
    public static Response failed(String msg) {
        return getResponseData(false, null, msg);
    }

    /**
     * @param msg
     * @return
     * @date: 2016年8月16日 下午4:22:14
     */
    public static Response failed(String msg, Object data) {
        return getResponseData(false, data, msg);
    }


    private static Response getResponseData(boolean status, Object data, String message) {
        Response obj = new Response();
        obj.setStatus(status);
        obj.setData(data);
        obj.setMsg(message);
        return obj;
    }

    /**
     * @param ex
     * @return
     * @date: 2016年8月16日 下午4:16:05
     */
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception ex) {
        logger.error("system error", ex);
        return failed(SYSTEM_ERROR + "，原因:" + ex.getMessage());
    }
}
