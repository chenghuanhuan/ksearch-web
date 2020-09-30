/**
 * youqianhuan.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.home.freemarker;

import freemarker.cache.TemplateLoader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * freemarker防止xss
 * @author chenghuanhuan@youqianhuan.com
 * @version $Id: HtmlTemplateLoader.java, v 0.1 2016年5月4日 上午9:33:20 user Exp $
 */
public class HtmlTemplateLoader implements TemplateLoader {
    private static final String  HTML_ESCAPE_PREFIX = "<#escape x as x?html>";
    private static final String  HTML_ESCAPE_SUFFIX = "</#escape>";

    private final TemplateLoader delegate;

    public HtmlTemplateLoader(TemplateLoader delegate) {
        this.delegate = delegate;
    }

    /** 
     * @see freemarker.cache.TemplateLoader#closeTemplateSource(Object)
     */
    @Override
    public void closeTemplateSource(Object arg0) throws IOException {
        delegate.closeTemplateSource(arg0);
    }

    /**
     * @see freemarker.cache.TemplateLoader#findTemplateSource(String)
     */
    @Override
    public Object findTemplateSource(String arg0) throws IOException {
        return delegate.findTemplateSource(arg0);
    }

    /**
     * @see freemarker.cache.TemplateLoader#getLastModified(Object)
     */
    @Override
    public long getLastModified(Object arg0) {
        return delegate.getLastModified(arg0);
    }

    /**
     * @see freemarker.cache.TemplateLoader#getReader(Object, String)
     */
    @Override
    public Reader getReader(Object arg0, String arg1) throws IOException {
        Reader reader = delegate.getReader(arg0, arg1);
        String templateText = IOUtils.toString(reader);
        return new StringReader(HTML_ESCAPE_PREFIX + templateText + HTML_ESCAPE_SUFFIX);
    }

}
