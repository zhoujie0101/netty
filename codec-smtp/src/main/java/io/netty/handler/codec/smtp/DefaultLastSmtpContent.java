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

import io.netty.buffer.ByteBuf;

/**
 * Default implementation of {@link LastSmtpContent} that does no validation of the passed in raw data.
 */
public class DefaultLastSmtpContent extends DefaultSmtpContent implements LastSmtpContent {
    public DefaultLastSmtpContent(ByteBuf data) {
        super(data);
    }

    @Override
    public LastSmtpContent copy() {
        return new DefaultLastSmtpContent(content().copy());
    }

    @Override
    public LastSmtpContent duplicate() {
        return new DefaultLastSmtpContent(content().duplicate());
    }

    @Override
    public LastSmtpContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastSmtpContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public LastSmtpContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastSmtpContent touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
