package com.onlineshop.theshop.minio;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        super.beforeSpringSecurityFilterChain(servletContext);

        // CSRF for multipart form data filter:
        FilterRegistration.Dynamic springMultipartFilter;
        springMultipartFilter = servletContext.addFilter(
                "springMultipartFilter", new MultipartFilter());
        springMultipartFilter.addMappingForUrlPatterns(null, false, "/*");

    }
}
