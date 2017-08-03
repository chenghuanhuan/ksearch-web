/**
 * kaike.la Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package la.kaike.ksearch.biz.es;

import io.searchbox.action.BulkableAction;
import io.searchbox.action.SingleResultAbstractDocumentTargetedAction;
import io.searchbox.core.DocumentResult;
import io.searchbox.params.Parameters;

import java.util.Collection;

/**
 * @author chenghuanhuan@kaike.la
 * @since $Revision:1.0.0, $Date: 2017年08月03日 下午4:43 $
 */
public class IndexExt extends SingleResultAbstractDocumentTargetedAction implements BulkableAction<DocumentResult> {
    public IndexExt(Builder builder) {
        super(builder);
        this.payload = builder.source;
        setURI(builder.URI);
    }

    @Override
    public String getPathToResult() {
        return "ok";
    }

    @Override
    public String getRestMethodName() {
        return (id != null) ? "PUT" : "POST";
    }

    @Override
    public String getBulkMethodName() {
        Collection<Object> opType = getParameter(Parameters.OP_TYPE);
        if (opType != null) {
            if (opType.size() > 1) {
                throw new IllegalArgumentException("Expecting a single value for OP_TYPE parameter, you provided: " + opType.size());
            }
            return (opType.size() == 1 && ((opType.iterator().next()).toString().equalsIgnoreCase("create"))) ? "create" : "index";
        } else {
            return "index";
        }
    }


    public static class Builder extends SingleResultAbstractDocumentTargetedAction.Builder<IndexExt, IndexExt.Builder> {
        private Object source;

        private String URI;

        public Builder(Object source) {
            this.source = source;
            this.id(getIdFromSource(source)); // set the default for id if it exists in source
        }

        public Builder setURI (String URI){
            this.URI = URI;
            return this;
        }

        public IndexExt build() {
            return new IndexExt(this);
        }
    }
}
