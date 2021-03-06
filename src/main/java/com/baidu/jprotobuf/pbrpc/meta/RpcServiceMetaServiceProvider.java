/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baidu.jprotobuf.pbrpc.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.baidu.jprotobuf.pbrpc.ProtobufRPCService;
import com.baidu.jprotobuf.pbrpc.RpcHandler;
import com.baidu.jprotobuf.pbrpc.server.RpcServiceRegistry;


/**
 * {@link RpcServiceMetaServiceProvider} service
 *
 * @author xiemalin
 * @since 2.1
 */
public class RpcServiceMetaServiceProvider {
    
    
    public static final String RPC_META_SERVICENAME = "__rpc_meta_watch_service__";
    
    private RpcServiceRegistry rpcServiceRegistry;

    /**
     * @param rpcServiceRegistry
     */
    public RpcServiceMetaServiceProvider(RpcServiceRegistry rpcServiceRegistry) {
        super();
        this.rpcServiceRegistry = rpcServiceRegistry;
    }

    @ProtobufRPCService(serviceName = RPC_META_SERVICENAME)
    public RpcServiceMetaList getRpcServiceMetaInfo() {
        
        Collection<RpcHandler> services = rpcServiceRegistry.getServices();
        List<RpcServiceMeta> list = new ArrayList<RpcServiceMeta>(services.size());
        for (RpcHandler rpcHandler : services) {
            if (rpcHandler instanceof RpcMetaAware) {
                RpcMetaAware meta = (RpcMetaAware) rpcHandler;
                
                RpcServiceMeta rpcServiceMeta = new RpcServiceMeta();
                rpcServiceMeta.setServiceName(rpcHandler.getServiceName());
                rpcServiceMeta.setMethodName(rpcHandler.getMethodName());
                
                rpcServiceMeta.setInputProto(meta.getInputMetaProto());
                rpcServiceMeta.setOutputProto(meta.getOutputMetaProto());
                list.add(rpcServiceMeta);
            }
        }
        
        RpcServiceMetaList ret = new RpcServiceMetaList();
        ret.setRpcServiceMetas(list);
        return ret;
    }
}
