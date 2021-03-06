/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cominvent.lucene.analysis;

import java.io.IOException;
import java.util.LinkedList;
import org.apache.commons.codec.Encoder;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * Filter for DoubleMetaphone (supporting secondary codes)
 */
public final class ScandinavianPhoneticFilter extends TokenFilter {

    private final LinkedList<State> remainingTokens = new LinkedList<State>();
    private Encoder encoder = new DoubleMetaphone();
    protected String name = null;
    private final boolean inject;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posAtt = addAttribute(PositionIncrementAttribute.class);

    /**
     * Creates a DoubleMetaphoneFilter with the specified maximum code length,
     * and either adding encoded forms as synonyms (
     * <code>inject=true</code>) or replacing them.
     */
    public ScandinavianPhoneticFilter(TokenStream input, Encoder encoder, String name, boolean inject) {
        super(input);
        this.encoder = encoder;
        this.name = name;
        this.inject = inject;
    }

    @Override
    public boolean incrementToken() throws IOException {
        for (;;) {

            if (!remainingTokens.isEmpty()) {
                // clearAttributes();  // not currently necessary
                restoreState(remainingTokens.removeFirst());
                return true;
            }

            if (!input.incrementToken()) {
                return false;
            }

            int len = termAtt.length();
            if (len == 0) {
                return true; // pass through zero length terms
            }
            int firstAlternativeIncrement = inject ? 0 : posAtt.getPositionIncrement();

            String v = termAtt.toString();
            String primaryPhoneticValue = ((ScandinavianDoubleMetaphone) encoder).doubleMetaphone(v);
            String alternatePhoneticValue = ((ScandinavianDoubleMetaphone) encoder).doubleMetaphone(v, true);

            // a flag to lazily save state if needed... this avoids a save/restore when only
            // one token will be generated.
            boolean saveState = inject;

            if (primaryPhoneticValue != null && primaryPhoneticValue.length() > 0 && !primaryPhoneticValue.equals(v)) {
                if (saveState) {
                    remainingTokens.addLast(captureState());
                }
                posAtt.setPositionIncrement(firstAlternativeIncrement);
                firstAlternativeIncrement = 0;
                termAtt.setEmpty().append(primaryPhoneticValue);
                saveState = true;
            }

            if (alternatePhoneticValue != null && alternatePhoneticValue.length() > 0
                    && !alternatePhoneticValue.equals(primaryPhoneticValue)
                    && !primaryPhoneticValue.equals(v)) {
                if (saveState) {
                    remainingTokens.addLast(captureState());
                    saveState = false;
                }
                posAtt.setPositionIncrement(firstAlternativeIncrement);
                termAtt.setEmpty().append(alternatePhoneticValue);
                saveState = true;
            }

            // Just one token to return, so no need to capture/restore
            // any state, simply return it.
            if (remainingTokens.isEmpty()) {
                return true;
            }

            if (saveState) {
                remainingTokens.addLast(captureState());
            }
        }
    }

    @Override
    public void reset() throws IOException {
        input.reset();
        remainingTokens.clear();
    }
}
