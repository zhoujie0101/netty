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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SmtpResponseDecoder extends LineBasedFrameDecoder {

    private List<CharSequence> details;

    public SmtpResponseDecoder(int maxLength) {
        super(maxLength);
    }

    @Override
    protected SmtpResponse decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
        if (frame == null) {
            // No full line received yet.
            return null;
        }
        try {
            int readable = frame.readableBytes();
            int readerIndex = frame.readerIndex();
            if (readable < 3) {
                throw newDecoderException(buffer, readerIndex, readable);
            }
            int code = parseCode(frame);
            int separator = frame.readByte();
            List<CharSequence> details = this.details;
            CharSequence detail = frame.isReadable() ? frame.toString(CharsetUtil.US_ASCII) : null;

            switch (separator) {
            case ' ':
                // Marks the end of a response.
                this.details = null;
                if (details != null) {
                    if (detail != null) {
                        details.add(detail);
                    }
                } else {
                    details = Collections.singletonList(detail);
                }
                return new DefaultSmtpResponse(code, details);
            case '-':
                // Multi-line response.
                if (detail != null) {
                    if (details == null) {
                        // Using initial capacity as it is very unlikely that we will receive a multi-line response
                        // with more then 3 lines.
                        this.details = details = new ArrayList<CharSequence>(4);
                    }
                    details.add(detail);
                }
                break;
            default:
                throw newDecoderException(buffer, readerIndex, readable);
            }
        } finally {
            frame.release();
        }
        return null;
    }

    private static DecoderException newDecoderException(ByteBuf buffer, int readerIndex, int readable) {
        return new DecoderException(
                "Received invalid line: '" + buffer.toString(readerIndex, readable, CharsetUtil.US_ASCII) + '\'');
    }

    /**
     * Parses the io.netty.handler.codec.smtp code without any allocation, which is three digits.
     */
    private static int parseCode(ByteBuf buffer) {
        int first = parseNumber(buffer.readByte()) * 100;
        int second = parseNumber(buffer.readByte()) * 10;
        int third = parseNumber(buffer.readByte());
        return first + second + third;
    }

    private static int parseNumber(byte b) {
        return Character.digit((char) b, 10);
    }
}
