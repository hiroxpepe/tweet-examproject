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
 * @author hiroxpepe
 */
public class IsContainOfEmoticonWords implements Predicate {
    
    @Override
    public boolean evaluate(Object object) {
        String str = (String) object;

        if (str.indexOf(":)") >= 0) {
            return true;
        }
        if (str.indexOf(":D") >= 0) {
            return true;
        }
        if (str.indexOf(":d") >= 0) {
            return true;
        }
        if (str.indexOf(":(") >= 0) {
            return true;
        }
        if (str.indexOf(":|") >= 0) {
            return true;
        }
        if (str.indexOf(":}") >= 0) {
            return true;
        }
        if (str.indexOf(":]") >= 0) {
            return true;
        }
        if (str.indexOf(":O") >= 0) {
            return true;
        }
        if (str.indexOf(":o") >= 0) {
            return true;
        }
        if (str.indexOf(";)") >= 0) {
            return true;
        }
        if (str.indexOf(":p") >= 0) {
            return true;
        }
        return false;
    }
    
}