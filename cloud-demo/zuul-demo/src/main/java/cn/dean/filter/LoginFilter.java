package cn.dean.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @ProjectName: cloud-demo
 * @Package: cn.dean.filter
 * @ClassName: LoginFilter
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/13 0:52
 * @Version: 1.0
 */

@Component
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {

//        导入静态包引用
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = context.getRequest();
        // 获取请求参数
        String parameter = request.getParameter("access-token");
        // 判断是否存在
        if (StringUtils.isBlank(parameter)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(403);

        }
        return null;
    }
}
