/*
 * Copyright 2017 floragunn GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.floragunn.searchguard.ssl.util;

import org.elasticsearch.ElasticsearchException;

public class ExceptionUtils {
    
    public static Throwable getRootCause(final Throwable e) {
        
        if(e == null) {
            return null;
        }
        
        final Throwable cause = e.getCause();
        if(cause == null) {
            return e;
        }
        return getRootCause(cause);
    }
    
    public static Throwable findMsg(final Throwable e, String msg) {
        
        if(e == null) {
            return null;
        }
        
        if(e.getMessage() != null && e.getMessage().contains(msg)) {
            return e;
        }
        
        final Throwable cause = e.getCause();
        if(cause == null) {
            return null;
        }
        return findMsg(cause, msg);
    }

    public static ElasticsearchException createBadHeaderException() {
        
        return new ElasticsearchException("bad header found. "
                + "This typically means that one node tried to connect to another with "
                + "a non-node certificate (it had no OID or the searchguard.nodes_dn setting was incorrectly configured) or that someone"
                + "is spoofing requests. See https://github.com/floragunncom/search-guard-docs/blob/master/tls_node_certificates.md");
    }
}
