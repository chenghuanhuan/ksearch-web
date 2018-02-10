/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.youqianhuan.ksearch.biz.es;

import com.google.gson.Gson;
import io.searchbox.action.AbstractAction;
import io.searchbox.action.AbstractMultiTypeActionBuilder;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月03日 上午11:56 $
 */
public class SearchExt extends AbstractAction<SearchResult> {

    private String query;

    private String URI;

    private String restMethod;

    protected SearchExt(Builder builder) {
        super(builder);
        query = builder.query;
        URI = builder.URI;
        restMethod = builder.restMethod;
        setURI(URI);
    }

    protected SearchExt(Search.TemplateBuilder templatedBuilder) {
        super(templatedBuilder);
        setURI(URI);
    }


    @Override
    public String getRestMethodName() {
        return this.restMethod;
    }

    @Override
    public SearchResult createNewElasticSearchResult(String responseBody, int statusCode, String reasonPhrase, Gson gson) {
        return createNewElasticSearchResult(new SearchResult(gson), responseBody, statusCode, reasonPhrase, gson);
    }


    public static class Builder extends AbstractMultiTypeActionBuilder<SearchExt, SearchExt.Builder> {

        /**
         * 查询语句
         */
        protected String query;

        /**
         * 查询地址
         */
        private String URI;

        /**
         * 请求类型
         */
        private String restMethod;



        public Builder(String query) {
            this.query = query;
        }

        public Builder setURI(String URI){
            this.URI = URI;
            return this;
        }

        public Builder setRestMethod(String restMethod) {
            this.restMethod = restMethod;
            return this;
        }

        @Override
        public SearchExt build() {
            return new SearchExt(this);
        }
    }

}
