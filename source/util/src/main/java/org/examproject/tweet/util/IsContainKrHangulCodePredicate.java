/* 
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

package org.examproject.tweet.util;

import org.apache.commons.collections.Predicate;

/**
 * @author h.adachi
 */
public class IsContainKrHangulCodePredicate implements Predicate {
    
    @Override
    public boolean evaluate(Object object) {
        String str = (String) object;
        for (int i =0; i < str.length(); i++) {
            char c = str.charAt(i);
            int code = (int) c;
            // hangul 
            // range: 0xAC00 ~ 0xD7AF
            if ((code >= 44032) && (code <= 55215)) {
                return true;
            }
        }
        return false;
    }
    
}