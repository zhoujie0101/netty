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

import java.util.Collections;
import java.util.List;

/**
 * Default {@limk SmtpResponse} implementation.
 */
public class DefaultSmtpResponse implements SmtpResponse {

    private final int code;
    private final List<CharSequence> details;

    public DefaultSmtpResponse(int code) {
        this(code, (List<CharSequence>) null);
    }

    public DefaultSmtpResponse(int code, CharSequence... details) {
        this(code, SmtpUtils.toUnmodifiableList(details));
    }

    DefaultSmtpResponse(int code, List<CharSequence> details) {
        if (code < 100 || code > 599) {
            throw new IllegalArgumentException("code must be 100 <= code <= 599");
        }
        this.code = code;
        if (details == null) {
            this.details = Collections.emptyList();
        } else {
            this.details = Collections.unmodifiableList(details);
        }
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public List<CharSequence> details() {
        return details;
    }
}
