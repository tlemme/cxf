/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.tools.java2wsdl.processor.internal.simple.generator;

import java.util.Map;

import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.common.model.JavaInterface;
import org.apache.cxf.tools.common.model.JavaModel;

public class SimpleClientGenerator extends AbstractSimpleGenerator {

    private static final String CLIENT_TEMPLATE = TEMPLATE_BASE + "/client.vm";

    public SimpleClientGenerator() {
        this.name = ToolConstants.CLT_GENERATOR;
    }

    public boolean passthrough() {
        return !env.optionSet(ToolConstants.CFG_CLIENT);
    }


    public void generate(ToolContext penv) throws ToolException {
        this.env = penv;
        JavaModel javaModel = env.get(JavaModel.class);

        if (passthrough()) {
            return;
        }

        Map<String, JavaInterface> interfaces = javaModel.getInterfaces();

        for (JavaInterface intf : interfaces.values()) {
            clearAttributes();
            setAttributes("intf", intf);
            setAttributes("seiClass", env.get(ToolConstants.SEI_CLASS));
            setAttributes("address", env.get(ToolConstants.CFG_ADDRESS));
            setCommonAttributes();
            doWrite(CLIENT_TEMPLATE, parseOutputName(intf.getPackageName(), intf.getName() + "Client"));
            env.put(ToolConstants.CLIENT_CLASS, intf.getFullClassName() + "Client");
        }
    }
}