package com.leyou.filter;

import com.leyou.config.JwtProperties;
import com.leyou.task.PrivilegeTokenHolder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author 虎哥
 */
@Component
public class PrivilegeFilter extends ZuulFilter {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private PrivilegeTokenHolder tokenHolder;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * PRE_DECORATION_FILTER 是Zuul默认的处理请求头的过滤器，我们放到这个之后执行
     * @return 顺序
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 将token存入请求头中
        ctx.addZuulRequestHeader(prop.getApp().getHeaderName(), tokenHolder.getToken());
        return null;
    }
}