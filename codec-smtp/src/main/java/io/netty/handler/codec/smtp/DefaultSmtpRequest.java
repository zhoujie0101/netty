/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.smtp;

import io.netty.util.internal.ObjectUtil;

import java.util.List;

/**
 * Default {@link SmtpRequest} implementation.
 */
public class DefaultSmtpRequest implements SmtpRequest {
    private final SmtpCommand cmd;
    private final List<CharSequence> parameters;

    public DefaultSmtpRequest(SmtpCommand cmd, CharSequence... parameters) {
        this.cmd = ObjectUtil.checkNotNull(cmd, "cmd");
        this.parameters = SmtpUtils.toUnmodifiableList(parameters);
    }

    public DefaultSmtpRequest(CharSequence cmd, CharSequence... parameters) {
        this(SmtpCommand.valueOf(cmd), parameters);
    }

    @Override
    public SmtpCommand cmd() {
        return cmd;
    }

    @Override
    public List<CharSequence> parameters() {
        return parameters;
    }
}
