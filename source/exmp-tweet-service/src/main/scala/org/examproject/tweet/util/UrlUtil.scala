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

package org.examproject.tweet.util

/**
 * @author hiroxpepe
 */
object UrlUtil {
    
    def getDomain(url: String): String = {
        val urlArray = url.split("/")
        var siteDomain: String = ""
        if (url.contains(".appspot.com")) {
            // GAE??
            siteDomain = urlArray(0) + "/" + urlArray(1) + "/" + urlArray(2) + "/"
        }
//        else {
//            // not GAE
//            siteDomain = urlArray(0) + "/" + urlArray(1) + "/" + urlArray(2) + "/" + urlArray(3) + "/"
//        }
//      
        // TODO: what' this??
        siteDomain = urlArray(0) + "/" + urlArray(1) + "/" + urlArray(2) + "/"
        return siteDomain
    }
}
